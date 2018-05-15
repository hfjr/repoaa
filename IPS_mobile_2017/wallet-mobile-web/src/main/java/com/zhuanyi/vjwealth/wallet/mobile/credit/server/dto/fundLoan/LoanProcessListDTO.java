package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

/**
 * Created by wzf on 2016/10/26.
 */
public class LoanProcessListDTO {

    private String loanProductName;//公积金贷款,
    private String loanStatus;//G,
    private String loanStatusDesc;//借款审核中,
    private String loanApplyTip;//申请额度,
    private String loanAmountDesc;//2000元，日息费率0.035%,
    private String loanApplyDateDesc;//申请时间 2016-08-22,
    private String borrowCode;//01415456456
    private String isChanged;

    public String getLoanProductName() {
        return loanProductName;
    }

    public void setLoanProductName(String loanProductName) {
        this.loanProductName = loanProductName;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getLoanStatusDesc() {
        return loanStatusDesc;
    }

    public void setLoanStatusDesc(String loanStatusDesc) {
        this.loanStatusDesc = loanStatusDesc;
    }

    public String getLoanApplyTip() {
        return loanApplyTip;
    }

    public void setLoanApplyTip(String loanApplyTip) {
        this.loanApplyTip = loanApplyTip;
    }

    public String getLoanAmountDesc() {
        return loanAmountDesc;
    }

    public void setLoanAmountDesc(String loanAmountDesc) {
        this.loanAmountDesc = loanAmountDesc;
    }

    public String getLoanApplyDateDesc() {
        return loanApplyDateDesc;
    }

    public void setLoanApplyDateDesc(String loanApplyDateDesc) {
        this.loanApplyDateDesc = loanApplyDateDesc;
    }

    public String getBorrowCode() {
        return borrowCode;
    }

    public void setBorrowCode(String borrowCode) {
        this.borrowCode = borrowCode;
    }

    public String getIsChanged() {
        return isChanged;
    }

    public void setIsChanged(String isChanged) {
        this.isChanged = isChanged;
    }
}
