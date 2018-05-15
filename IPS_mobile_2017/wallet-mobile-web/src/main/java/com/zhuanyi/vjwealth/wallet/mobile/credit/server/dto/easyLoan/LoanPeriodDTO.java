package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.easyLoan;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by wzf on 2017/1/5.
 */
public class LoanPeriodDTO implements Serializable {

    private Map<String,String> loanPeriodDefault;
    private List<Map<String,String>> loanPeriodSelection;

    public Map<String, String> getLoanPeriodDefault() {
        return loanPeriodDefault;
    }

    public void setLoanPeriodDefault(Map<String, String> loanPeriodDefault) {
        this.loanPeriodDefault = loanPeriodDefault;
    }

    public List<Map<String, String>> getLoanPeriodSelection() {
        return loanPeriodSelection;
    }

    public void setLoanPeriodSelection(List<Map<String, String>> loanPeriodSelection) {
        this.loanPeriodSelection = loanPeriodSelection;
    }
}
