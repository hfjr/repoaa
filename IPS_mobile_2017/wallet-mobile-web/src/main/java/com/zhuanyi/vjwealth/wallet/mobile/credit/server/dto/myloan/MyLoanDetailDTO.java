package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.myloan;

import java.util.List;
import java.util.Map;

/**
 * Created by wzf on 2016/9/8.
 */
public class MyLoanDetailDTO {

    private String borrowCode;
    private String repaymentStatusDesc;
    private String expirationDateDesc;
    private List<Map<String,String>> repaymentInfos;
    private List<Map<String,String>> borrowProductInfos;
    private List<Map<String,String>> borrowDateInfos;
    private List<String> paymentRecordTitles;
    private List<MyLoanDetailInnerListDTO> paymentRecords;
    private String contractLabel;
    private String contractURL;
    private String contractTitle;
    private String planId;
    private String isRepayDetail;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getBorrowCode() {
        return borrowCode;
    }

    public void setBorrowCode(String borrowCode) {
        this.borrowCode = borrowCode;
    }

    public String getRepaymentStatusDesc() {
        return repaymentStatusDesc;
    }

    public void setRepaymentStatusDesc(String repaymentStatusDesc) {
        this.repaymentStatusDesc = repaymentStatusDesc;
    }

    public String getExpirationDateDesc() {
        return expirationDateDesc;
    }

    public void setExpirationDateDesc(String expirationDateDesc) {
        this.expirationDateDesc = expirationDateDesc;
    }

    public List<Map<String, String>> getRepaymentInfos() {
        return repaymentInfos;
    }

    public void setRepaymentInfos(List<Map<String, String>> repaymentInfos) {
        this.repaymentInfos = repaymentInfos;
    }

    public List<Map<String, String>> getBorrowProductInfos() {
        return borrowProductInfos;
    }

    public void setBorrowProductInfos(List<Map<String, String>> borrowProductInfos) {
        this.borrowProductInfos = borrowProductInfos;
    }

    public List<String> getPaymentRecordTitles() {
        return paymentRecordTitles;
    }

    public void setPaymentRecordTitles(List<String> paymentRecordTitles) {
        this.paymentRecordTitles = paymentRecordTitles;
    }

    public List<MyLoanDetailInnerListDTO> getPaymentRecords() {
        return paymentRecords;
    }

    public void setPaymentRecords(List<MyLoanDetailInnerListDTO> paymentRecords) {
        this.paymentRecords = paymentRecords;
    }

    public String getContractLabel() {
        return contractLabel;
    }

    public void setContractLabel(String contractLabel) {
        this.contractLabel = contractLabel;
    }

    public String getContractURL() {
        return contractURL;
    }

    public void setContractURL(String contractURL) {
        this.contractURL = contractURL;
    }

    public String getContractTitle() {
        return contractTitle;
    }

    public void setContractTitle(String contractTitle) {
        this.contractTitle = contractTitle;
    }

    public List<Map<String, String>> getBorrowDateInfos() {
        return borrowDateInfos;
    }

    public void setBorrowDateInfos(List<Map<String, String>> borrowDateInfos) {
        this.borrowDateInfos = borrowDateInfos;
    }

    public String getIsRepayDetail() {
        return isRepayDetail;
    }

    public void setIsRepayDetail(String isRepayDetail) {
        this.isRepayDetail = isRepayDetail;
    }
}
