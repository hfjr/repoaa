package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

import java.util.List;

/**
 * Created by wzf on 2016/10/26.
 */
public class LoanProcessDetailDTO {

    private String loanApplyTip;
    private String loanAmountDesc;
    private String loanApplyDateDesc;
    private String loanStatus;
    private List<LoanProcessDetailInnerDTO> loanProcessInfo;

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

    public List<LoanProcessDetailInnerDTO> getLoanProcessInfo() {
        return loanProcessInfo;
    }

    public void setLoanProcessInfo(List<LoanProcessDetailInnerDTO> loanProcessInfo) {
        this.loanProcessInfo = loanProcessInfo;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }
}
