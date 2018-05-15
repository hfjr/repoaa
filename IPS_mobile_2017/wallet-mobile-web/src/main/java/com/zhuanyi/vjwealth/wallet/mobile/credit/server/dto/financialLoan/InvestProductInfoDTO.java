package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by hexy on 16/6/14.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class InvestProductInfoDTO {

    private String  productId ; // 产品ID
    private String  productCode  ; // 产品编号
    private String  productName ; // 产品名称
    private String  annualYield  ; // 年化收益
    private String  projectTerm ; // 项目期限
    private String  remainingInvestmentLabel ; // 剩余可投资Label
    private String  remainingInvestment  ; // 剩余可投资
    private String  paymentWay  ; //

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

    public String getAnnualYield() {
        return annualYield;
    }

    public void setAnnualYield(String annualYield) {
        this.annualYield = annualYield;
    }

    public String getProjectTerm() {
        return projectTerm;
    }

    public void setProjectTerm(String projectTerm) {
        this.projectTerm = projectTerm;
    }

    public String getRemainingInvestmentLabel() {
        return remainingInvestmentLabel;
    }

    public void setRemainingInvestmentLabel(String remainingInvestmentLabel) {
        this.remainingInvestmentLabel = remainingInvestmentLabel;
    }

    public String getRemainingInvestment() {
        return remainingInvestment;
    }

    public void setRemainingInvestment(String remainingInvestment) {
        this.remainingInvestment = remainingInvestment;
    }

    public String getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(String paymentWay) {
        this.paymentWay = paymentWay;
    }
}
