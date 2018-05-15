package com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.service.impl.support;

import com.alibaba.fastjson.JSONObject;
import com.fab.core.logger.BaseLogger;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.ExpireRepayResDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.dto.CheckMaBalanceResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.DueRepaymentBankCardWithholdDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.payment.DueRepaymentCommonDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.payment.WithholdResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.service.IDueRepaymentService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.BindingCardDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.TradeSearchResultDTO;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 到期还款的模板方法
 * Created by wzf on 2016/9/14.
 */
public abstract class AbstractDueRepaymentService extends DueRepaymentToolService {

    /**
     * 模板方法 : 到期扣款 （子类不能实现）
     *
     * @param paramDto
     */
    public final TradeSearchResultDTO doRepay(DueRepaymentCommonDTO paramDto){
        BaseLogger.info("到期还款入参："+ JSONObject.toJSONString(paramDto));

        //1.校验参数是否合法
        validatorParams(paramDto);

        //2.获取公共基本参数
        String userId = paramDto.getUserId();
        String loanCode = paramDto.getLoanCode();
        String planId = paramDto.getPlanId();
        String repayAmount = paramDto.getRepaymentTotalMoney();
        String orderType = paramDto.getOrderType();

        //3.校验业务
        validatorBusiness(paramDto);

        //4.锁用户账户
        super.lockUser(userId);
        //5.校验余额是否充足(没有加用户锁，所以在做接下来扣款时会再次查询用户余额)
        CheckMaBalanceResultDTO checkResult = checkMaBalanceResult(userId,new BigDecimal(repayAmount));
        BaseLogger.info(String.format("到期还款，查询用户余额结果:",JSONObject.toJSONString(checkResult)));

        if(checkResult.isEnough()){
            try{
                //6.余额充足，则走余额扣款(包括从余额扣款、生成订单)
                return repayFromAmountBalance(userId,loanCode,planId,repayAmount);
            }catch(Exception e){
                BaseLogger.error("到期还款余额扣款-异常",e);
                sendEmail("到期还款余额扣款repayFromAmountBalance-异常["+userId+"]");
                return TradeSearchResultDTO.buildFailed("到期还款余额扣款失败");
            }finally {
                super.unlockUser(userId);
            }
        }else{
            //7.余额不充足，则走余额/银行卡/存钱罐组合扣款的方式
            return repayFromFixType(userId,loanCode,planId,repayAmount,orderType,checkResult);
        }

    }

    /**
     * 通过余额还款
     *
     * @param userId 用户编号
     * @param loanCode 贷款编号
     * @param planId 还款计划编号
     * @param repayAmount 还款总额
     * @return
     */
    private TradeSearchResultDTO repayFromAmountBalance(String userId,String loanCode,String planId,String repayAmount){

        //1.从余额扣款放入冻结金额
            String orderNo = doCutMaAmountBalance(userId,loanCode,planId,repayAmount);
            //3.更新信贷系统
        ExpireRepayResDTO result = updateLoanInfo(userId,loanCode,planId,repayAmount);
        if(!result.getCode().equals("200")){
            BaseLogger.info("到期还款余额扣款-从余额中扣钱到冻结金额后，更新信贷系统时返回code:"+result.getCode());
            sendEmail("到期还款余额扣款-从余额中扣钱到冻结金额后，更新信贷系统时异常["+userId+"]："+result.getMsg());
            return TradeSearchResultDTO.buildProcess("到期还款余额扣款-从余额中扣钱到冻结金额后，更新信贷系统时返回code:"+result.getCode());
        }
            //4.更新还款订单
        updateRepayOrderInfo(userId,loanCode,planId,repayAmount,orderNo,result);
        //5.TODO 调用发短信通知用户已扣钱
        sendSMS(userId,"金额");
        //6.返回结果
            return TradeSearchResultDTO.buildSuccess("余额扣款成功");

        }

    /**
     * 混合扣款  -  余额/银行卡/存钱罐
     * 扣款顺序为：如果余额有钱，但是不够，则从余额扣除部分，然后剩下的钱从银行卡扣，
     *               a.如果银行卡扣款成功则返回；
     *               b.如果处理中，也返回；
     *               c.如果扣款失败，则检查存钱罐的钱是否充足，如果充足则做存钱罐赎回操作，然后再从余额中扣除。
     *
     * @param checkResult
     * @return TradeSearchResultDTO
     */
    private TradeSearchResultDTO repayFromFixType(String userId,String loanCode,String planId,String repayAmount,String orderType,CheckMaBalanceResultDTO checkResult){

        BigDecimal maCutAmount = checkResult.getAmountBalance();//需要从余额中扣除的钱
        BigDecimal otherCutAmount = checkResult.getShortMoney();//需要从银行卡或存钱罐中扣除的钱
        boolean maCutFlag = Boolean.FALSE;

        //1. 当用户余额有钱时，扣除
        try{
        if(maCutAmount.compareTo(new BigDecimal(0)) > 0){
            //余额中只扣除可扣除的钱
                repayFromAmountBalance(userId,loanCode,planId,maCutAmount.toPlainString());

                maCutFlag = Boolean.TRUE;
        }
        }catch (Exception e){
            BaseLogger.error("到期还款组合扣款-从余额中扣款异常",e);
            sendEmail("到期还款组合扣款-从余额中扣款repayFromAmountBalance异常["+userId+"]");
            return TradeSearchResultDTO.buildFailed("余额扣款失败");
        }finally {
            super.unlockUser(userId);
        }

        TradeSearchResultDTO result = null;
        //2. 循环从银行卡扣款

        result = loopRepayFromBankcard(userId, loanCode, planId, otherCutAmount.toPlainString(),orderType);

        boolean isLock = false;//是否锁了用户
        //3. 判断银行卡扣款结果
        try{
        if(result.isSuccess()){
            // 返回成功结果
            return TradeSearchResultDTO.buildSuccess("扣款成功");
        }else if(result.isProcess()){
            //返回处理中结果
            return TradeSearchResultDTO.buildProcess("银行卡扣款处理中");
        }else if(result.isFailed()){
                super.lockUser(userId);
                isLock = true;
                //4.做存钱罐赎回操作,并从余额扣款
                boolean transferResult = cutAmountFromTa(userId,otherCutAmount,loanCode,planId);
                //5.如果赎回失败。并余额中也没有扣款成功，则返回失败
                if(!transferResult && !maCutFlag){
                    return TradeSearchResultDTO.buildSuccess("组合扣款失败");
            }
        }
        }catch (Exception e){
            BaseLogger.error("到期还款组合扣款-从存钱罐赎回到余额还款时异常",e);
            sendEmail("到期还款组合扣款-从存钱罐赎回到余额还款时异常["+userId+"]");
            return TradeSearchResultDTO.buildProcess("到期还款组合扣款-从存钱罐赎回到余额还款时异常");
        }finally {
            //如果开始时，就从余额中扣除了部分钱，则在做完存钱罐赎回操作后，更新后续的信贷系统及账单
           if(isLock){
                //如果锁了用户，那么解锁用户
                super.unlockUser(userId);
            }
        }

        //如果成功后，发送短信通知用户 判断isSuccessCutTa以及是否从余额中扣除部分
        sendSMS(userId,"还的钱");

        return TradeSearchResultDTO.buildSuccess("组合扣款成功");

    }


    /**
     * 基本方法：业务校验(子类必须实现)
     *
     * @param paramDto
     */
    public abstract void validatorBusiness(DueRepaymentCommonDTO paramDto);

    /**
     * 基本方法：余额扣款(子类必须实现)
     *
     * @param userId 用户编号
     * @param loanCode 贷款编号
     * @param planId 还款计划编号
     * @param repayAmount 还款总额
     * @return
     */
    public abstract String doCutMaAmountBalance(String userId,String loanCode,String planId,String repayAmount);

    /**
     * 银行卡扣款方法
     *
     * @param paramObject
     * @return
     */
    public abstract TradeSearchResultDTO doCutBankcardAmount(final DueRepaymentBankCardWithholdDTO paramObject);

    /**
     * 基本方法：更新信贷系统的借款信息(子类必须实现)
     *
     * @param userId 用户编号
     * @param loanCode 贷款编号
     * @param planId 还款计划编号
     * @param repayAmount 还款总额
     */
    public abstract ExpireRepayResDTO updateLoanInfo(String userId, String loanCode, String planId, String repayAmount);

    /**
     * 基本方法：更新还款订单的信息(子类必须实现)
     *
     * @param userId 用户编号
     * @param loanCode 贷款编号
     * @param planId 还款计划编号
     * @param repayAmount 还款总额
     * @param orderNo 已经生成的还款中的订单编号
     */
    public abstract void updateRepayOrderInfo(String userId,String loanCode,String planId,String repayAmount,String orderNo,ExpireRepayResDTO result);


    /*********************************************************************可实现部分*****************************************************/

    /**
     * 通过银行卡还款（循环银行卡扣款）
     *
     * @param
     */
    public TradeSearchResultDTO loopRepayFromBankcard(String userId, String loanCode, String planId, String repayAmount,String orderType) {
        List<BindingCardDTO> cardList = queryUserBankCardList(userId,orderType);
        if(cardList.isEmpty()){
            return TradeSearchResultDTO.buildFailed("扣款失败");
        }
        //组装扣款实体
        DueRepaymentBankCardWithholdDTO paramDto = new DueRepaymentBankCardWithholdDTO(userId,"",loanCode,planId,repayAmount);
        //循环用户银行卡进行还款
        for(BindingCardDTO bcd:cardList){
            //可扣款条件:1.已经绑定了第三方支付(不需要再发送验证码)；2.是渠道支持的银行卡；3.对应银行政策运营
            if(bcd.getIsBindingThird().equals("Y") && bcd.getIsSupportCard().equals("Y") && bcd.getStatus().equals("normal")){
                paramDto.setCardId(bcd.getCardId());
                //调用扣款
        TradeSearchResultDTO bankResult = doCutBankcardAmount(paramDto);
                if(bankResult.isFailed()){
                    continue;
                }else {
        return bankResult;
    }
            }
        }

        return TradeSearchResultDTO.buildFailed("扣款失败");
    }


    /**
     * 基本参数校验(子类可重写)
     *
     * @param paramDto
     */
    public void validatorParams(DueRepaymentCommonDTO paramDto){

    }

    /**
     * 存钱罐赎回(子类可重写)
     *
     * @param userId
     * @param amount
     */
    public boolean cutAmountFromTa(String userId,BigDecimal amount,String loanCode,String planId){
        //赎回到ma
        boolean transferResult = super.transferTaToMa(userId,amount);
        //如果赎回成功。则继续走余额扣款
        if(transferResult){
            repayFromAmountBalance(userId,loanCode,planId,amount.toPlainString());
        }
        return transferResult;
    }


}
