package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct.CreditInvestigationWayDTO;

/**
 * Created by wzf on 2016/10/30.
 */
public class LoanProductInitDTO {

    private LoanAmountSelectionDTO loanAmountSelection;
    private CreditInvestigationWayDTO creditWays;
    private LoanProcessCountDTO loanProcessInfo;

    public LoanAmountSelectionDTO getLoanAmountSelection() {
        return loanAmountSelection;
    }

    public void setLoanAmountSelection(LoanAmountSelectionDTO loanAmountSelection) {
        this.loanAmountSelection = loanAmountSelection;
    }

    public CreditInvestigationWayDTO getCreditWays() {
        return creditWays;
    }

    public void setCreditWays(CreditInvestigationWayDTO creditWays) {
        this.creditWays = creditWays;
    }

    public LoanProcessCountDTO getLoanProcessInfo() {
        return loanProcessInfo;
    }

    public void setLoanProcessInfo(LoanProcessCountDTO loanProcessInfo) {
        this.loanProcessInfo = loanProcessInfo;
    }
}
