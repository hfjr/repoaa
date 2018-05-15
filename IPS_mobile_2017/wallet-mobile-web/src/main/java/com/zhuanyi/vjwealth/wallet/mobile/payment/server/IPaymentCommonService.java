package com.zhuanyi.vjwealth.wallet.mobile.payment.server;

import com.zhuanyi.vjwealth.wallet.mobile.payment.dto.PreBizOperateParamDTO;
import com.zhuanyi.vjwealth.wallet.mobile.payment.dto.RepaymentSendSMSResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.TradeSearchResultDTO;

/**
 * 银行卡扣款通用方法
 * Created by wzf on 2016/9/13.
 */
public interface IPaymentCommonService {


    /**
     * 查询用户银行卡列表
     * @param userId
     * @return
     */
    Object querySupportBankcardList(String userId);

    /**
     * 还款确认的前置接口
     * @param paramDTO
     * @return
     */
    String preBizOperate(PreBizOperateParamDTO paramDTO);

    /**
     * 发送验证码接口
     * @param userId
     * @param amount
     * @return
     */
    RepaymentSendSMSResultDTO sendSMSNotice(String userId, String cardId, String amount);

    /**
     * 确认验证码(验证验证码+扣款)
     * @param userId
     * @param code
     * @param bizOrderNo
     * @param amount
     * @return
     */
    Object confirmSMSNotice(String userId,String cardId, String code,String tradeNo, String bizOrderNo, String amount);

    /**
     * 银行卡扣款
     * @param userId
     * @param bizOrderNo
     * @param amount
     * @return
     */
    Object bankCardWithhold(String userId,String cardId, String bizOrderNo, String amount,String bizType);

    /**
     * 扣款结果查询
     * @param userId
     * @param payOrderNo
     * @return
     */
    TradeSearchResultDTO queryWithholdResult(String userId, String payOrderNo, String bizType);
}
