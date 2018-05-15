package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

/**
 * Created by wzf on 2016/10/27.
 */
public class FundLoanRepaymentDTO {

    private String repaymentDate;//2015-05-04,
    private String shouldPrincipal;//￥1000,
    private String shouldInterest;//￥10

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public String getShouldPrincipal() {
        return shouldPrincipal;
    }

    public void setShouldPrincipal(String shouldPrincipal) {
        this.shouldPrincipal = shouldPrincipal;
    }

    public String getShouldInterest() {
        return shouldInterest;
    }

    public void setShouldInterest(String shouldInterest) {
        this.shouldInterest = shouldInterest;
    }
}
