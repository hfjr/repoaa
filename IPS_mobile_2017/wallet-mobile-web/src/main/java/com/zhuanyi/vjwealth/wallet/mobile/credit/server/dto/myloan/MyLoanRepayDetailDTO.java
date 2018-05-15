package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.myloan;

import java.util.List;
import java.util.Map;

/**
 * Created by wzf on 2016/9/8.
 */
public class MyLoanRepayDetailDTO {

   private List<MyLoanRepayDueDetailDTO> paymentDetailList;
    private String checkPaymentDetailTitle;
    private String isShowCheckPaymentDetail;
    private List<Map<String,String>> paymentRecords;
    private List<Map<String,String>> borrowInfo;
    private String borrowAmount;
    private String borrowAmountLabel;

    public List<MyLoanRepayDueDetailDTO> getPaymentDetailList() {
        return paymentDetailList;
    }

    public void setPaymentDetailList(List<MyLoanRepayDueDetailDTO> paymentDetailList) {
        this.paymentDetailList = paymentDetailList;
    }

    public String getCheckPaymentDetailTitle() {
        return checkPaymentDetailTitle;
    }

    public void setCheckPaymentDetailTitle(String checkPaymentDetailTitle) {
        this.checkPaymentDetailTitle = checkPaymentDetailTitle;
    }

    public String getIsShowCheckPaymentDetail() {
        return isShowCheckPaymentDetail;
    }

    public void setIsShowCheckPaymentDetail(String isShowCheckPaymentDetail) {
        this.isShowCheckPaymentDetail = isShowCheckPaymentDetail;
    }

    public List<Map<String, String>> getPaymentRecords() {
        return paymentRecords;
    }

    public void setPaymentRecords(List<Map<String, String>> paymentRecords) {
        this.paymentRecords = paymentRecords;
    }

    public List<Map<String, String>> getBorrowInfo() {
        return borrowInfo;
    }

    public void setBorrowInfo(List<Map<String, String>> borrowInfo) {
        this.borrowInfo = borrowInfo;
    }

    public String getBorrowAmount() {
        return borrowAmount;
    }

    public void setBorrowAmount(String borrowAmount) {
        this.borrowAmount = borrowAmount;
    }

    public String getBorrowAmountLabel() {
        return borrowAmountLabel;
    }

    public void setBorrowAmountLabel(String borrowAmountLabel) {
        this.borrowAmountLabel = borrowAmountLabel;
    }
}
