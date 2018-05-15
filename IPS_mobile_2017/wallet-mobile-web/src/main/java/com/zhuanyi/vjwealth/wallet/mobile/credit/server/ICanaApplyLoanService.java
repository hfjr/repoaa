package com.zhuanyi.vjwealth.wallet.mobile.credit.server;

import com.zhuanyi.vjwealth.loan.order.vo.RepaymentPlanQueryVo;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.canaLoan.ApplyLoanQueryDTO;

import java.util.Map;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:信贷业务层
 * @author: Tony Tang
 * @date: 2016-05-13 17:08
 */
public interface ICanaApplyLoanService {
    /**
     * 12.借款申请 ［未借款，借款处理中，借款失败，借款成功（还款中）］ 初始化
     * @param queryDto
     * @return
     */
    Object loanApplicationInit(ApplyLoanQueryDTO queryDto);

    /**
     * 13.借款申请［额度，还款期数填写］初始化
     * @param userId
     * @param productTypeCode
     * @return
     */
    Object amountAndRepaymentInstallmentFormInit(String userId, String productTypeCode);
    /**
     * 14.借款申请［额度填写］保存接口
     * @param param
     * @return
     * @since 3.4
     */
    Object amountAndRepaymentInstallmentFormSave(RepaymentPlanQueryVo param);

    /**
     * 15.借款申请［信息确认］初始化
     * @param userId
     * @param borrowCode
     * @return
     * @since 3.4
     */
    Object informationConfirmationIniti(String userId, String borrowCode);
    /**
     * 16.借款申请［信息确认］《合同及相关协议》-借款合同 H5接口
     *
     * @param userId
     * @return
     * @since 3.4
     */
    Map<String,String> investmentContractTemplate(String userId, String borrowCode,String productTypeCode);
    /**
     * 16.1.借款申请 合同下载
     *
     * @param fileId
     * @return
     * @since 3.4
     */
    byte[] downContract(String fileId);
    /**
     * 17.借款申请［信息确认-SMS］初始化
     * @param userId
     * @param borrowCode
     * @return
     * @since 3.4
     */
    Object informationConfirmationSMSVerificationIniti(String userId, String borrowCode);
    /**
     * 18 借款申请［信息确认-SMS］ (18 获取文字验证码)
     *
     * @param userId
     *  @param borrowCode
     * @return
     * @since 3.4
     */
    Object informationConfirmationSMSVerificationSendSMSNotice(String userId, String borrowCode);

    /**
     * 19.借款申请［信息确认-SMS］保存
     * @param userId
     * @param borrowCode
     * @param code
     * @return
     * @since 3.4
     */
    Object informationConfirmationSMSVerificationSave(String userId, String borrowCode, String code);

}
