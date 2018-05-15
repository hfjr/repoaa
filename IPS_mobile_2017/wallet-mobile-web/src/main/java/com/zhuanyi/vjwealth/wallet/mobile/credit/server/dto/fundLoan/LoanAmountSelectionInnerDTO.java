package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

/**
 * Created by wzf on 2016/11/4.
 */
public class LoanAmountSelectionInnerDTO {

    private String amountMax;
    private String amountMin;
    private String amountMinDesc;
    private String amountMaxDesc;

    public LoanAmountSelectionInnerDTO(String amountMax,String amountMaxDesc,String amountMin, String amountMinDesc) {
        this.amountMax = amountMax;
        this.amountMin = amountMin;
        this.amountMinDesc = amountMinDesc;
        this.amountMaxDesc = amountMaxDesc;
    }

    public String getAmountMax() {
        return amountMax;
    }

    public void setAmountMax(String amountMax) {
        this.amountMax = amountMax;
    }

    public String getAmountMin() {
        return amountMin;
    }

    public void setAmountMin(String amountMin) {
        this.amountMin = amountMin;
    }

    public String getAmountMinDesc() {
        return amountMinDesc;
    }

    public void setAmountMinDesc(String amountMinDesc) {
        this.amountMinDesc = amountMinDesc;
    }

    public String getAmountMaxDesc() {
        return amountMaxDesc;
    }

    public void setAmountMaxDesc(String amountMaxDesc) {
        this.amountMaxDesc = amountMaxDesc;
    }
}
