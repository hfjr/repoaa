package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by wangzf on 16/6/7.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class FinancialLoanInitiInnerProductDTO {

    private String productId; //  产品ID
    private String productCode; //  产品编号
    private String productName; //  产品名称
    private String projectTerm; // 项目期限
    private String annualYield; //  年化收益  0.1
    private String schedule; //  进度  0.1
    private String productSalesOrNewPersonMarkURL; //  产品销售状态及新手标识图片URL
    private String insuranceCompanyURL; // 保险公司URL
    private String repayment; //  还款方式
    private String interestDate; //  计息方式
    private String remainTermAndAmount; //  剩余期限及剩余可投资金额
	private String remainBalance;//剩余可投金额
	private String isSoldOut;//是否售罄


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProjectTerm() {
        return projectTerm;
    }

    public void setProjectTerm(String projectTerm) {
        this.projectTerm = projectTerm;
    }

    public String getAnnualYield() {
        return annualYield;
    }

    public void setAnnualYield(String annualYield) {
        this.annualYield = annualYield;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getProductSalesOrNewPersonMarkURL() {
        return productSalesOrNewPersonMarkURL;
    }

    public void setProductSalesOrNewPersonMarkURL(String productSalesOrNewPersonMarkURL) {
        this.productSalesOrNewPersonMarkURL = productSalesOrNewPersonMarkURL;
    }

    public String getInsuranceCompanyURL() {
        return insuranceCompanyURL;
    }

    public void setInsuranceCompanyURL(String insuranceCompanyURL) {
        this.insuranceCompanyURL = insuranceCompanyURL;
    }

    public String getRepayment() {
        return repayment;
    }

    public void setRepayment(String repayment) {
        this.repayment = repayment;
    }

    public String getInterestDate() {
        return interestDate;
    }

    public void setInterestDate(String interestDate) {
        this.interestDate = interestDate;
    }

    public String getRemainTermAndAmount() {
        return remainTermAndAmount;
    }

    public void setRemainTermAndAmount(String remainTermAndAmount) {
        this.remainTermAndAmount = remainTermAndAmount;
    }

    public String getRemainBalance() {
        return remainBalance;
    }

    public void setRemainBalance(String remainBalance) {
        this.remainBalance = remainBalance;
    }

    public String getIsSoldOut() {
        return isSoldOut;
    }

    public void setIsSoldOut(String isSoldOut) {
        this.isSoldOut = isSoldOut;
    }

    @Override
    public String toString() {
        return "FinancialLoanInitiInnerProductDTO{" +
                "productId='" + productId + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", projectTerm='" + projectTerm + '\'' +
                ", annualYield='" + annualYield + '\'' +
                ", schedule='" + schedule + '\'' +
                ", productSalesOrNewPersonMarkURL='" + productSalesOrNewPersonMarkURL + '\'' +
                ", insuranceCompanyURL='" + insuranceCompanyURL + '\'' +
                ", repayment='" + repayment + '\'' +
                ", interestDate='" + interestDate + '\'' +
                ", remainTermAndAmount='" + remainTermAndAmount + '\'' +
                ", remainBalance='" + remainBalance + '\'' +
                ", isSoldOut='" + isSoldOut + '\'' +
                '}';
    }
}
