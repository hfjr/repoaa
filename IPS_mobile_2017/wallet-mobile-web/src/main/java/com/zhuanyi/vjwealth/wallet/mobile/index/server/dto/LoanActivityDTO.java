package com.zhuanyi.vjwealth.wallet.mobile.index.server.dto;

/**
 * Created by wzf on 2016/11/4.
 */
public class LoanActivityDTO {

    private String loanProductName;
    private String conditionDesc;
    private String detailDesc;
    private String loanAmount;
    private String loanMinRate;
    private String loanRateDesc;

    public String getLoanProductName() {
        return loanProductName;
    }

    public void setLoanProductName(String loanProductName) {
        this.loanProductName = loanProductName;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    public void setConditionDesc(String conditionDesc) {
        this.conditionDesc = conditionDesc;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanMinRate() {
        return loanMinRate;
    }

    public void setLoanMinRate(String loanMinRate) {
        this.loanMinRate = loanMinRate;
    }

    public String getLoanRateDesc() {
        return loanRateDesc;
    }

    public void setLoanRateDesc(String loanRateDesc) {
        this.loanRateDesc = loanRateDesc;
    }
}

