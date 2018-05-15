package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.wageAdvance;

import java.io.Serializable;

/**
 * Created by hexy on 16/8/26.
 */
public class CreditInitiDTO implements Serializable {

   private String  borrowAmountLabel;
    private String  borrowAmountInputTip ;
    private String  borrowAmountMin ;
    private String  borrowAmountMax ;
    private String  borrowAmountAdd ;
    private String  borrowAmountDesc ;

    public String getBorrowAmountLabel() {
        return borrowAmountLabel;
    }

    public void setBorrowAmountLabel(String borrowAmountLabel) {
        this.borrowAmountLabel = borrowAmountLabel;
    }

    public String getBorrowAmountInputTip() {
        return borrowAmountInputTip;
    }

    public void setBorrowAmountInputTip(String borrowAmountInputTip) {
        this.borrowAmountInputTip = borrowAmountInputTip;
    }

    public String getBorrowAmountMin() {
        return borrowAmountMin;
    }

    public void setBorrowAmountMin(String borrowAmountMin) {
        this.borrowAmountMin = borrowAmountMin;
    }

    public String getBorrowAmountMax() {
        return borrowAmountMax;
    }

    public void setBorrowAmountMax(String borrowAmountMax) {
        this.borrowAmountMax = borrowAmountMax;
    }

    public String getBorrowAmountAdd() {
        return borrowAmountAdd;
    }

    public void setBorrowAmountAdd(String borrowAmountAdd) {
        this.borrowAmountAdd = borrowAmountAdd;
    }

    public String getBorrowAmountDesc() {
        return borrowAmountDesc;
    }

    public void setBorrowAmountDesc(String borrowAmountDesc) {
        this.borrowAmountDesc = borrowAmountDesc;
    }

    @Override
    public String toString() {
        return "CreditInitiDTO{" +
                "borrowAmountLabel='" + borrowAmountLabel + '\'' +
                ", borrowAmountInputTip='" + borrowAmountInputTip + '\'' +
                ", borrowAmountMin='" + borrowAmountMin + '\'' +
                ", borrowAmountMax='" + borrowAmountMax + '\'' +
                ", borrowAmountAdd='" + borrowAmountAdd + '\'' +
                ", borrowAmountDesc='" + borrowAmountDesc + '\'' +
                '}';
    }
}
