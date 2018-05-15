package com.zhuanyi.vjwealth.wallet.mobile.credit.server;

import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct.CreditIntroduceDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct.CreditInvestigationWayDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.wageAdvance.*;

import java.util.Map;

/**
 * 工资先享服务接口
 * Created by hexy on 16/8/25.
 */
public interface IWageAdvanceService {

    /**
     *
     * @param userId 用户编号
     * @param loanProductId 贷款产品编号
     * @return 用户的可借额度，总额度，以及默认的还款信息等
     * @since 3.6
     */
    WageAdvanceInitiDTO wageAdvanceIniti(String userId, String loanProductId,String borrowAmount);

    /**
     * 查询还款计划
     * @param userId  用户编号
     * @param borrowMode 还款方式
     * @param borrowAmount 借款金额
     * @param borrowPeriod 借款期限
     * @return 一次性还回几种还款计划的数据集
     * @since 3.6
     */
    ScanRepaymentPlanDTO dynamicallyGeneratedRepaymentPlan(String userId, String borrowMode, String borrowAmount, String borrowPeriod);
    /**
     * 1.借款介绍接口
     * @return
     */
    CreditIntroduceDTO creditIntroduce();

    /**
     * 2.借款初始化接口
     * @return
     */
    CreditInitiDTO creditIniti();


    /**
     * 3.借款征信方式接口
     * @param userId
     * @param borrowAmount
     * @return
     */
    CreditInvestigationWayDTO creditInvestigationWay(String userId, String borrowAmount);

    /**
     * 3. 利息计算
     * @param userId 用户编号
     * @param loanProductId 贷款产品编号
     * @param borrowMode 还款方式
     * @param borrowAmount  借款金额
     * @param borrowPeriod 借款期限
     * @return
     */
    CalculateInterestDTO dynamicallyGeneratedInterest(String userId, String loanProductId, String borrowMode, String borrowAmount, String borrowPeriod);

    /**
     * 5.1 发送文字验证码
     * @param userId
     * @return
     */
    String informationConfirmationSendSMSNotice(String userId);

    /**
     * 5.2 发送语音验证码
     * @param userId
     * @return
     */
    String informationConfirmationSendToneNotice(String userId);

    /**
     * 工资先享下单接口
     * @param userId 用户编号
     * @param code 验证码
     * @param borrowMode 还款方式
     * @param loanProductId 产品编号
     * @param borrowAmount 借款金额
     * @param borrowPeriod 借款期限
     * @return
     */
    Object applySMSVerificationConfirm(String userId, String code, String borrowMode, String loanProductId, String borrowAmount, String borrowPeriod);

    /**
     * 还款计划初始化
     * @param userId
     * @return
     */
    Object repaymentIniti(String userId);

    /**
     * 到期还款初始化
     * @param userId
     * @param page
     * @return
     */
    Object dueRepaymentIniti(String userId, String page);

    /**
     * 到期还款订单详情
     * @param userId
     * @param loanCode
     * @return
     */
    Object dueRepaymentDetail(String userId, String loanCode);

    /**
     * 提前还款初始化
     * @param userId
     * @param page
     * @return
     */
    Object earlyRepaymentIniti(String userId, String page);

    /**
     * 提前还款计算
     * @param userId
     * @param loanCodes
     * @param repaymentMoney
     * @return
     */
    Object earlyRepaymentDetail(String userId, String loanCodes, String repaymentMoney);

    /**
     * 提前还款确认
     * @param userId
     * @param loanCodes
     * @param repaymentMoney
     * @return
     */
    Object earlyRepaymentConfirmIniti(String userId, String loanCodes, String repaymentMoney);

    /**
     * 借款记录列表
     * @param userId
     * @param page
     * @return
     */
    Object queryLoanRecordList(String userId, String page);

    /**
     * 查询借款记录详情
     * @param userId
     * @param loanCode
     * @return
     */
    Object queryLoanRecordDetail(String userId, String loanCode);

    /**
     * 查询还款记录详情
     * @param userId
     * @param loanCode
     * @param page
     * @return
     */
    Object queryrepayRecordList(String userId, String loanCode, String page);

    /**
     * 提前还款确认
     * @param userId
     * @param loanCodes
     * @param principal
     * @param repaymentMoney
     * @param repaymentType
     * @param repaymentWay
     * @return
     */
    Object earlyRepaymentConfirm(String userId, String loanCodes, String principal, String repaymentMoney, String repaymentType, String repaymentWay);

    /**
     * 银行卡扣款
     * @param userId
     * @param cardNo
     * @param bankCode
     * @param amount
     * @return
     */
    Object withhold(String userId, String cardNo, String bankCode, String amount);

    /**
     * 银行卡扣款结果查询
     * @param userId
     * @param amount
     * @param orderNo
     * @return
     */
    Object queryWithholdResult(String userId,String amount, String orderNo);

    /**
     * 查询用户工资先享贷款申请情况（审核中，审核失败，审核成功）
     * @param userId
     * @return
     */
    Object queryUserLoanCheckStatus(String userId);

    /**
     * 获取借款合同内容
     * @param userId
     * @param loanCode
     * @param loanProductId
     * @return
     */
    Map<String,String> queryWageAdvanceContractContent(String userId, String loanCode, String loanProductId);
}
