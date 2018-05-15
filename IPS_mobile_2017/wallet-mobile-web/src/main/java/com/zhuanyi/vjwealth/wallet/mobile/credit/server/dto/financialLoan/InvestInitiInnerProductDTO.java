package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by hexy on 16/6/12.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class InvestInitiInnerProductDTO {

	    private String  productId ; // 产品ID
	    private String  productCode  ; // 产品编号
	    private String  productName ; // 产品名称
	    private String  annualYield  ; // 年化收益
	    private String  projectTerm ; // 项目期限
	    private String  remainingInvestmentLabel ; // 剩余可投资Label
	    private String  remainingInvestment  ; // 剩余可投资
	    private String  paymentWay ; // 回款方式(按月付息:monthly_interest, 到期还本付息:repay_maturity,等额本息:principal_and_interest_equal,等额本金:principal_equal,等本等息:principal_equal_and_interest_equal)
		
	    
	    public InvestInitiInnerProductDTO(){
	    	
	    }
	    
	    public InvestInitiInnerProductDTO(String productId, String productCode, String productName, String annualYield,
				String projectTerm, String remainingInvestmentLabel, String remainingInvestment, String paymentWay) {
			
			this.productId = productId;
			this.productCode = productCode;
			this.productName = productName;
			this.annualYield = annualYield;
			this.projectTerm = projectTerm;
			this.remainingInvestmentLabel = remainingInvestmentLabel;
			this.remainingInvestment = remainingInvestment;
			this.paymentWay = paymentWay;
		}
	    
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
