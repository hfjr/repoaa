package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct;

import java.io.Serializable;

/**
 * 公积金信用贷款产品描述
 * Created by hexy on 16/8/25.
 */
public class FundCreditLoan implements Serializable {

    private String loanProductId;
    private String loanProductName;
    private String rate;
    private String borrowAmount;
    private String desc;

    public String getLoanProductId() {
        return loanProductId;
    }

    public void setLoanProductId(String loanProductId) {
        this.loanProductId = loanProductId;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getBorrowAmount() {
        return borrowAmount;
    }

    public void setBorrowAmount(String borrowAmount) {
        this.borrowAmount = borrowAmount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLoanProductName() {
        return loanProductName;
    }

    public void setLoanProductName(String loanProductName) {
        this.loanProductName = loanProductName;
    }

    @Override
    public String toString() {
        return "FundCreditLoan{" +
                "loanProductId='" + loanProductId + '\'' +
                ", loanProductName='" + loanProductName + '\'' +
                ", rate='" + rate + '\'' +
                ", borrowAmount='" + borrowAmount + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
