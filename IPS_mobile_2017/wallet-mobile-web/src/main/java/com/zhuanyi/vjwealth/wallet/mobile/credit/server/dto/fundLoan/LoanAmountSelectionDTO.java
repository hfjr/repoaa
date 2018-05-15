package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

import java.util.List;
import java.util.Map;

/**
 * Created by wzf on 2016/10/30.
 */
public class LoanAmountSelectionDTO {

    private String defaultLoanMinAmount;
    private String defaultLoanMaxAmount;
    private String loanMinAmount;
    private String loanMaxAmount;
    private List<LoanAmountSelectionInnerDTO> amountSelection;


    public String getLoanMinAmount() {
        return loanMinAmount;
    }

    public void setLoanMinAmount(String loanMinAmount) {
        this.loanMinAmount = loanMinAmount;
    }

    public String getLoanMaxAmount() {
        return loanMaxAmount;
    }

    public void setLoanMaxAmount(String loanMaxAmount) {
        this.loanMaxAmount = loanMaxAmount;
    }

    public List<LoanAmountSelectionInnerDTO> getAmountSelection() {
        return amountSelection;
    }

    public void setAmountSelection(List<LoanAmountSelectionInnerDTO> amountSelection) {
        this.amountSelection = amountSelection;
    }

    public String getDefaultLoanMinAmount() {
        return defaultLoanMinAmount;
    }

    public void setDefaultLoanMinAmount(String defaultLoanMinAmount) {
        this.defaultLoanMinAmount = defaultLoanMinAmount;
    }

    public String getDefaultLoanMaxAmount() {
        return defaultLoanMaxAmount;
    }

    public void setDefaultLoanMaxAmount(String defaultLoanMaxAmount) {
        this.defaultLoanMaxAmount = defaultLoanMaxAmount;
    }
}
