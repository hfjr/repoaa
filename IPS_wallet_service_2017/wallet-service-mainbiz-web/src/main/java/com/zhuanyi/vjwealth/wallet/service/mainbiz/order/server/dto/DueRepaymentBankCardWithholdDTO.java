package com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto;

import java.io.Serializable;

/**
 * Created by wzf on 2016/9/26.
 */
public class DueRepaymentBankCardWithholdDTO implements Serializable{
    private String userId ; //用户编号
    private String loanCode ; //订单编号
    private String planId ; //还款计划编号
    private String repaymentTotalMoney ;//应还总额
    private String cardId;
    private String repayOrderNo;
    private String createDate;//创建时间

    public DueRepaymentBankCardWithholdDTO() {
    }

    public DueRepaymentBankCardWithholdDTO(String userId,String cardId, String loanCode, String planId, String repaymentTotalMoney) {
        this.userId = userId;
        this.loanCode = loanCode;
        this.planId = planId;
        this.repaymentTotalMoney = repaymentTotalMoney;
        this.cardId = cardId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getRepaymentTotalMoney() {
        return repaymentTotalMoney;
    }

    public void setRepaymentTotalMoney(String repaymentTotalMoney) {
        this.repaymentTotalMoney = repaymentTotalMoney;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getRepayOrderNo() {
        return repayOrderNo;
    }

    public void setRepayOrderNo(String repayOrderNo) {
        this.repayOrderNo = repayOrderNo;
    }
}
