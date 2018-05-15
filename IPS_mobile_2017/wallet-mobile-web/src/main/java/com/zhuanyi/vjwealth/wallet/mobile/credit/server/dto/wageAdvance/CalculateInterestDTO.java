package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.wageAdvance;

/**
 * 工资先享-试算还款计划利息总和
 * Created by wangzf on 16/5/20.
 */
public class CalculateInterestDTO {

    private String totalInterest;//利息总额
    private String repaymentDate;//首期还款日期

    public String getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(String totalInterest) {
        this.totalInterest = totalInterest;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }
}
