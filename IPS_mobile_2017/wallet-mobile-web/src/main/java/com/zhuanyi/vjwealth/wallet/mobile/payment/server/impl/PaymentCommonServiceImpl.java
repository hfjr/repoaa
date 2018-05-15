package com.zhuanyi.vjwealth.wallet.mobile.payment.server.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants.LoanBizTypeConstant;
import com.zhuanyi.vjwealth.wallet.mobile.payment.dto.PreBizOperateParamDTO;
import com.zhuanyi.vjwealth.wallet.mobile.payment.dto.RepaymentResultDTO;
import com.zhuanyi.vjwealth.wallet.mobile.payment.dto.RepaymentSendSMSResultDTO;
import com.zhuanyi.vjwealth.wallet.mobile.payment.server.IPaymentCommonService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.TradeSearchResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.facade.IWithholdServiceFacade;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBRechargeDTO;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 银行卡扣款通用方法
 * Created by wzf on 2016/9/13.
 */
@Service
public class PaymentCommonServiceImpl implements IPaymentCommonService{

    @Remote
    private IWithholdServiceFacade withholdServiceFacade;

    @Override
    public Object querySupportBankcardList(String userId) {
        return withholdServiceFacade.queryWithholeCardList(userId, LoanBizTypeConstant.WAGE_BIND_CARD);
    }

    @Override
    public String preBizOperate(PreBizOperateParamDTO paramDTO) {
        BaseLogger.info("银行卡提前还款预处理入参:"+ JSONObject.toJSONString(paramDTO));
        try {
            Object obj = withholdServiceFacade.preBizOperateParameter(paramDTO.getBizType(),JSONObject.parseObject(JSONObject.toJSONString(paramDTO),Map.class));
            BaseLogger.info("银行卡提前还款预处理返回结果："+ JSONObject.toJSONString(obj));
            return String.valueOf(obj);
        } catch (Exception e) {
            BaseLogger.error(e);
            e.printStackTrace();
           throw new AppException("预处理异常");
        }
    }

    @Override
    public RepaymentSendSMSResultDTO sendSMSNotice(String userId, String cardId, String amount) {
        MBRechargeDTO result = null;
        try {
            BaseLogger.info(String.format("银行卡提前还款发送验证码sendWithholdMsg入参：userId:%s,cardId:%s,amount:%s",userId,cardId,amount));
            result = withholdServiceFacade.sendWithholdMsg(userId,cardId,amount);
            BaseLogger.info(String.format("银行卡提前还款发送验证码sendWithholdMsg返回结果：",JSONObject.toJSONString(result)));
        } catch (Exception e) {
            BaseLogger.error(e);
            throw new AppException("发送验证码异常");
        }
        return new RepaymentSendSMSResultDTO(result.getRemainTime(),result.getOrderNo());
    }

    @Override
    public Object confirmSMSNotice(String userId,String cardId, String code,String tradeNo, String bizOrderNo, String amount) {
        try {
            BaseLogger.info(String.format("银行卡提前还款有验证码的扣款confirmSMSNotice入参：userId:%s,cardId:%s,amount:%s,code:%s,tradeNo:%s,bizOrderNo:%s",userId,cardId,amount,code,tradeNo,bizOrderNo));
            tradeNo = withholdServiceFacade.confirmWithhold(userId,cardId,tradeNo,bizOrderNo,amount,code);
            BaseLogger.info(String.format("银行卡提前还款有验证码的扣款confirmSMSNotice返回结果:",tradeNo));
            return new RepaymentResultDTO("204000","调用扣款借款成功",tradeNo);
        }catch (AppException e){
            BaseLogger.error("确认验证码扣款异常",e);
            throw new AppException(e.getMessage());
        }
        catch (Exception e) {
            BaseLogger.error("确认验证码扣款异常",e);
            return new RepaymentResultDTO("204002","还款失败");
        }
    }

    @Override
    public Object bankCardWithhold(String userId, String cardId,String bizOrderNo, String amount,String bizType) {
        try {
            BaseLogger.info(String.format("银行卡提前还款扣款bankCardWithhold入参：userId:%s,cardId:%s,amount:%s,bizOrderNo:%s",userId,cardId,amount,bizOrderNo));
            String tradeNo = withholdServiceFacade.sendWithholdForAlreadyBindCarded(userId,cardId,amount,bizOrderNo,bizType);
            if(StringUtils.isBlank(tradeNo)){
                throw new AppException("银行卡余额不足");
            }
            BaseLogger.info(String.format("银行卡提前还款扣款bankCardWithhold返回结果:",tradeNo));
            return new RepaymentResultDTO("204000","调用银行卡扣款成功",tradeNo);
        }catch(AppException e){
            BaseLogger.error("银行卡扣款异常",e);
            throw new AppException(e.getMessage());
        } catch (Exception e) {
            BaseLogger.error("银行卡扣款异常",e);
            return new RepaymentResultDTO("204002","还款失败");
        }
    }

    @Override
    public TradeSearchResultDTO queryWithholdResult(String userId, String tradeNo,String bizType) {
        BaseLogger.info(String.format("银行卡提前还款扣款结果查询queryWithholdResult入参：userId:%s,tradeNo:%s,bizType:%s",userId,tradeNo,bizType));
        long startTime = System.currentTimeMillis();//标记查询开始时间
        while (true) {
            //查询账单状态（在扣款接口的回调方法中，成功或失败之后，会更新账单的状态，所有如果成功：表示扣款成功，失败：扣款失败；处理中：还没有返回结果）
        TradeSearchResultDTO result = withholdServiceFacade.queryWithholdBizResult(bizType,tradeNo);
        BaseLogger.info(String.format("银行卡提前还款扣款结果查询queryWithholdResult返回结果:",JSONObject.toJSONString(result)));
            //如果充值成功，则直接返回true
            if (result.isSuccess()) {
                result.setMessage("还款成功");
        return result;
            }else if (result.isFailed()){
                result.setMessage("还款失败");
                return result;
            }else if (result.isProcess()) {
                //当状态为处理中时，则sleep一段时间后再次查询是否有处理结果；如果循环查询总耗时超过设定时间，则退出循环查询,返回处理中的结果
                if ((System.currentTimeMillis() - startTime) >= 4000) {
                    return TradeSearchResultDTO.buildProcess("还款处理中");
    }
                try {
                    Thread.sleep(1500);//等待2秒，再去查询(因为银行卡充值扣款接口不是实时返回结果，可能存在延时)
                } catch (InterruptedException e) {
                    BaseLogger.error("查询银行卡扣款结果时异常：",e);
                    throw new AppException("查询银行卡扣款异常，请稍后到账单中查询扣款结果");
                }
            } else {
                throw new AppException("查询银行卡扣款异常，请稍后到账单中查询扣款结果");
            }
        }
    }

}
