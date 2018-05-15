package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto;

import java.io.Serializable;

/**
 * Created by hexy on 16/5/12.
 */
public class DynamicallyGeneratedRepaymentPlanDTO implements Serializable {

    private  String  userId ;
    private  String  repaymentPeriod  ; //还款期数
    private  String  repaymentMethod   ; // 还款方式 (默认：等额本息)
    private  String  applyAmount  ;// 申请金额
    private  String  borrowCode  ;// 借款申请编号

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRepaymentPeriod() {
        return repaymentPeriod;
    }

    public void setRepaymentPeriod(String repaymentPeriod) {
        this.repaymentPeriod = repaymentPeriod;
    }

    public String getRepaymentMethod() {
        return repaymentMethod;
    }

    public void setRepaymentMethod(String repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
    }

    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getBorrowCode() {
        return borrowCode;
    }

    public void setBorrowCode(String borrowCode) {
        this.borrowCode = borrowCode;
    }
}
