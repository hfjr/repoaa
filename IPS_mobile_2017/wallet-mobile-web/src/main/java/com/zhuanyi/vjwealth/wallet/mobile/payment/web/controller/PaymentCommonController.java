package com.zhuanyi.vjwealth.wallet.mobile.payment.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants.LoanBizTypeConstant;
import com.zhuanyi.vjwealth.wallet.mobile.payment.dto.PreBizOperateParamDTO;
import com.zhuanyi.vjwealth.wallet.mobile.payment.dto.TradeResultDTO;
import com.zhuanyi.vjwealth.wallet.mobile.payment.server.IPaymentCommonService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.payment.WageAdvancePreparePaymentDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.TradeSearchResultDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by wzf on 2016/9/13.
 */

@Controller
public class PaymentCommonController {

    @Autowired
    private IPaymentCommonService paymentCommonService;

    /**
     * 1.查询用户银行卡列表
     * @param userId
     * @return
     */
    @RequestMapping("/api/v3.6/app/paymentCommon/querySupportBankcardList.security")
    @AppController
    public Object querySupportBankcardList(String userId) {
        return paymentCommonService.querySupportBankcardList(userId);
    }


    /**
     * 2.还款确认的前置接口
     * 参考WageAdvancePreparePaymentDTO 实体字段
     * @param
     * @return
     */
    @RequestMapping("/api/v3.6/app/paymentCommon/preBizOperate.security")
    @AppController
    public Object preBizOperate(PreBizOperateParamDTO paramDTO) {
       final String bizOrderNo = paymentCommonService.preBizOperate(paramDTO);
        return new HashMap<String,String>(){
            private static final long serialVersionUID = 1L;
            {put("bizOrderNo",bizOrderNo);}
        };
    }

    /**
     * 3.发送验证码接口
     * @param userId
     * @return
     */
    @RequestMapping("/api/v3.6/app/paymentCommon/sendSMSNotice.security")
    @AppController
    public Object sendSMSNotice(String userId,String cardId,String amount) {
        return paymentCommonService.sendSMSNotice(userId,cardId,amount);
    }

    /**
     * 4.还款确认
     * @param userId
     * @param isSendMsg  (Y：表示发过验证码，不需要再校验；N：需要校验验证码)
     * @return RepaymentResultDTO
     */
    @RequestMapping("/api/v3.6/app/paymentCommon/repaymentConfirm.security")
    @AppController
    public Object repaymentConfirm(String userId,String cardId,String code,String tradeNo,String bizOrderNo,String amount,String isSendMsg) {
        if(isSendMsg.equals("N")){
            return paymentCommonService.confirmSMSNotice(userId,cardId,code,tradeNo,bizOrderNo,amount);
        }
        return paymentCommonService.bankCardWithhold(userId,cardId,bizOrderNo,amount, LoanBizTypeConstant.WAGE_BIND_CARD);
    }


    /**
     * 5.确认验证码(验证验证码+扣款)
     * @param userId
     * @return
     */
    @RequestMapping("/api/v3.6/app/paymentCommon/confirmSMSNotice.security")
    @AppController
    public Object confirmSMSNotice(String userId,String cardId,String code,String tradeNo,String bizOrderNo,String amount) {
        return paymentCommonService.confirmSMSNotice(userId,cardId,code,tradeNo,bizOrderNo,amount);
    }

    /**
     * 6.银行卡扣款
     * @param userId
     * @return
     */
    @RequestMapping("/api/v3.6/app/paymentCommon/bankCardWithhold.security")
    @AppController
    public Object bankCardWithhold(String userId,String cardId,String bizOrderNo,String amount) {
        return paymentCommonService.bankCardWithhold(userId,cardId,bizOrderNo,amount, LoanBizTypeConstant.WAGE_BIND_CARD);
    }

    /**
     * 7.扣款结果查询
     * @param userId
     * @return RepaymentResultDTO
     */
    @RequestMapping("/api/v3.6/app/paymentCommon/queryWithholdResult.security")
    @AppController
    public Object queryWithholdResult(String userId,String tradeNo,String bizType) {
        TradeSearchResultDTO tradeResult = paymentCommonService.queryWithholdResult(userId,tradeNo,bizType);

        TradeResultDTO resultDTO = new TradeResultDTO();
        BeanUtils.copyProperties(tradeResult,resultDTO);

        return resultDTO;
    }
}
