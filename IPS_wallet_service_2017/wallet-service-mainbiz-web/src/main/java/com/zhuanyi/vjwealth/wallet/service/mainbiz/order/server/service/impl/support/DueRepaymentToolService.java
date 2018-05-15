package com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.service.impl.support;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.DueRepaymentBankCardWithholdDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.payment.DueRepaymentCommonDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.BindingCardDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.TradeSearchResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.phoneMessage.server.service.IMBUserPhoneMessageService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IUserAccountTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.dto.CheckMaBalanceResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.payment.WithholdResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.facade.IWithholdServiceFacade;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.constant.UserConstant;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountMapper;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;

/**
 * 到期扣款的工具类
 * Created by wzf on 2016/9/14.
 */
@Component
public class DueRepaymentToolService {

    @Autowired
    private IMBUserAccountMapper userAccountMapper;

    @Remote
    ISendEmailService sendEmailService;

    @Autowired
    private ISequenceService sequenceService;

    @Autowired
    private IWithholdServiceFacade withholdServiceFacade;

    @Autowired
    private IUserAccountTransactionService userAccountTransactionService;

    @Autowired
    private IMBUserPhoneMessageService userPhoneMessageService;


    /**
     * 给用户上锁
     * @param userId
     */
    public void lockUser(String userId){
        // 2. 判断是否已经上锁 并上锁
        int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
        // 不等于说明已经上锁 没更新到记录 或者有其他异常情况
        if (lockUpdateCount != 1) {
            throw new AppException("此账户其他交易正在处理中，请稍后再试");
        }
    }

    /**
     * 给用户解锁
     * @param userId
     */
    public void unlockUser(String userId){
        int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
        BaseLogger.audit("unlock earlyRepaymentConfirm userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]" );
        if (unlockUpdateCount != 1) {
            sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("earlyRepaymentConfirm 异常，userId[" + userId + "]"));
        }
    }

    /**
     * 检查用户余额，如果余额不够，则还差多少
     * @param userId
     * @param amount
     * @return
     */
    public CheckMaBalanceResultDTO checkMaBalanceResult(String userId,BigDecimal amount){

        BigDecimal availableAmount = userAccountMapper.queryAccountAvailableAmount(userId, UserConstant.TRADETYPE_MA);
        if (availableAmount == null) {
            throw new AppException("账户信息异常，请联系客服");
        }

        if (availableAmount.compareTo(amount) < 0) {
            //如果账户余额不足，则还差多少
            return new CheckMaBalanceResultDTO(Boolean.FALSE,availableAmount,amount.subtract(availableAmount));
        }

        return new CheckMaBalanceResultDTO(Boolean.TRUE,availableAmount,new BigDecimal("0"));
    }

    /**
     * 调用银行卡扣款
     * @param paramDto
     * @param orderType
     * @return
     */
    public TradeSearchResultDTO invokingWithhold(final DueRepaymentBankCardWithholdDTO paramDto, String orderType){
        String tradeNo ="";
        try {
        	//1.调用pre方法
        	Object bizOrderNo = withholdServiceFacade.preBizOperateParameter(orderType,paramDto);

            //2.调用扣款方法
            tradeNo = withholdServiceFacade.sendWithholdForAlreadyBindCarded(paramDto.getUserId(),paramDto.getCardId(),paramDto.getRepaymentTotalMoney(),String.valueOf(bizOrderNo), orderType);

            if(StringUtils.isBlank(tradeNo)){
                return TradeSearchResultDTO.buildFailed("扣款失败");
            }
        } catch (Exception e) {
            BaseLogger.error("调用扣款接口sendWithholdForAlreadyBindCarded异常",e);
            return TradeSearchResultDTO.buildFailed("调用扣款借款失败");
        }

        try {
            long startTime = System.currentTimeMillis();//标记查询开始时间
            while (true) {
                BaseLogger.info("查询银行卡"+paramDto.getCardId()+"扣款结果开始时间："+startTime);
                //查询账单状态（在扣款接口的回调方法中，成功或失败之后，会更新账单的状态，所有如果成功：表示扣款成功，失败：扣款失败；处理中：还没有返回结果）
                TradeSearchResultDTO result = withholdServiceFacade.queryWithholdBizResult(orderType, tradeNo);
                BaseLogger.info("查询银行卡"+paramDto.getCardId()+"扣款结果耗时"+System.currentTimeMillis()+";查询扣款结果："+JSONObject.toJSONString(result));
                //如果充值成功，则直接返回true
                if (result.isSuccess()) {
                    return result;
                }else if (result.isFailed()){
                    return result;
                }else if (result.isProcess()) {
                    //当状态为处理中时，则sleep一段时间后再次查询是否有处理结果；如果循环查询总耗时超过设定时间，则退出循环查询,返回处理中的结果
                    if ((System.currentTimeMillis() - startTime) >= 5000) {
                        return TradeSearchResultDTO.buildProcess("还款处理中");
                    }
                    try {
                        Thread.sleep(1500);//等待1.5秒，再去查询(因为银行卡充值扣款接口不是实时返回结果，可能存在延时)
                    } catch (InterruptedException e) {
                        BaseLogger.error("查询银行卡扣款结果时异常：",e);
                    }
                }
            }
        }catch (Exception e){
            BaseLogger.error("调用扣款查询接口queryWithholdBizResult异常",e);
            return TradeSearchResultDTO.buildProcess("调用扣款查询接口异常");
        }

    }


    /**
     * 查询用户的银行卡列表
     * @param userId
     * @return
     */
    public List<BindingCardDTO> queryUserBankCardList(String userId,String orderType){
        return withholdServiceFacade.queryWithholeCardList(userId,orderType);
    }

    /**
     * 存钱罐赎回到余额
     * @param userId
     * @param amount
     * @return
     */
    public boolean transferTaToMa(String userId,BigDecimal amount){
        // 1.1 检测余额是否够转账
        BigDecimal availableAmount = userAccountMapper.queryAccountAvailableAmount(userId, UserConstant.TRADETYPE_TA);
        if (availableAmount == null) {
            throw new AppException("账户信息异常，请联系客服");
        }
        if (availableAmount.compareTo(amount) < 0) {
            //Ta账户余额不足
            return Boolean.FALSE;
        }
        // 1.2 转账
        userAccountTransactionService.transferTaToMa(userId, amount);

        return Boolean.TRUE;
    }

    public void sendSMS(String userId,String message){

//        userPhoneMessageService.sendTextMessage("", vo.getPhone(), vo.getBizType(), smsParamMap);

    }

    public void sendEmail(String message){
        //sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(message));
    }


    /**
     * 生成账单编号
     * @param prefix
     * @param sequenceName
     * @return
     */
    public String getId(String prefix, String sequenceName) {
        return prefix + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequenceService.getNextStringValue(sequenceName, 10);
    }

    private Map<String, Object> pageEmailMap(String content) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("content", content);
        return map;
    }

}
