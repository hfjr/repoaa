package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.myloan;

/**
 * Created by wzf on 2016/9/8.
 */
public class MyAllLoanListDTO {

    private String productCode;// O201605060001000007,
    private String borrowCode;// O201605060001000007,
    private String borrowAmountDescription;// 借款5000.00元,
    private String borrowAmount;// 5000.00,
    private String repaymentStatus;// ,
    private String repaymentStatusMarkURL;// http;////open.vj.com/wallet-mobile/static/vcredit/3.3/repaymentStatusMark1.png,
    private String productName;// 理财资产贷款(担保随时贷122394989898),
    private String projectTermLabel;// 期限,
    private String projectTerm;// 120,
    private String yearRateLabel;// 年化利率,
    private String yearRate;// 0.1,
    private String fromInterestTimeLabel;// 起息时间,
    private String fromInterestTime;// 2016-02-16,
    private String expirationDateLabel;//到期时间,
    private String expirationDate;// 2016-02-16
    private String planId;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getBorrowCode() {
        return borrowCode;
    }

    public void setBorrowCode(String borrowCode) {
        this.borrowCode = borrowCode;
    }

    public String getBorrowAmountDescription() {
        return borrowAmountDescription;
    }

    public void setBorrowAmountDescription(String borrowAmountDescription) {
        this.borrowAmountDescription = borrowAmountDescription;
    }

    public String getBorrowAmount() {
        return borrowAmount;
    }

    public void setBorrowAmount(String borrowAmount) {
        this.borrowAmount = borrowAmount;
    }

    public String getRepaymentStatus() {
        return repaymentStatus;
    }

    public void setRepaymentStatus(String repaymentStatus) {
        this.repaymentStatus = repaymentStatus;
    }

    public String getRepaymentStatusMarkURL() {
        return repaymentStatusMarkURL;
    }

    public void setRepaymentStatusMarkURL(String repaymentStatusMarkURL) {
        this.repaymentStatusMarkURL = repaymentStatusMarkURL;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProjectTermLabel() {
        return projectTermLabel;
    }

    public void setProjectTermLabel(String projectTermLabel) {
        this.projectTermLabel = projectTermLabel;
    }

    public String getProjectTerm() {
        return projectTerm;
    }

    public void setProjectTerm(String projectTerm) {
        this.projectTerm = projectTerm;
    }

    public String getYearRateLabel() {
        return yearRateLabel;
    }

    public void setYearRateLabel(String yearRateLabel) {
        this.yearRateLabel = yearRateLabel;
    }

    public String getYearRate() {
        return yearRate;
    }

    public void setYearRate(String yearRate) {
        this.yearRate = yearRate;
    }

    public String getFromInterestTimeLabel() {
        return fromInterestTimeLabel;
    }

    public void setFromInterestTimeLabel(String fromInterestTimeLabel) {
        this.fromInterestTimeLabel = fromInterestTimeLabel;
    }

    public String getFromInterestTime() {
        return fromInterestTime;
    }

    public void setFromInterestTime(String fromInterestTime) {
        this.fromInterestTime = fromInterestTime;
    }

    public String getExpirationDateLabel() {
        return expirationDateLabel;
    }

    public void setExpirationDateLabel(String expirationDateLabel) {
        this.expirationDateLabel = expirationDateLabel;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
