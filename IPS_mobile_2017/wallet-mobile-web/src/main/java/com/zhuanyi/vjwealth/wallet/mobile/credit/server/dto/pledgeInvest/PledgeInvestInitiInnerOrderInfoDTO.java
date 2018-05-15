package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;



/**
 * Created by wangzhangfei on 16/7/12.
 */
public class PledgeInvestInitiInnerOrderInfoDTO {

	private String orderId;
	private String productCode;
	private String investmentAmountLabel;
	private String investmentAmount;
	
	private String annualYield;
	private String investmentTermLabel;
	private String investmentTerm;
	private String expirationTermLabel;
	private String expirationTerm;
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getInvestmentAmountLabel() {
		return investmentAmountLabel;
	}
	public void setInvestmentAmountLabel(String investmentAmountLabel) {
		this.investmentAmountLabel = investmentAmountLabel;
	}
	public String getInvestmentAmount() {
		return investmentAmount;
	}
	public void setInvestmentAmount(String investmentAmount) {
		this.investmentAmount = investmentAmount;
	}
	public String getAnnualYield() {
		return annualYield;
	}
	public void setAnnualYield(String annualYield) {
		this.annualYield = annualYield;
	}
	public String getInvestmentTermLabel() {
		return investmentTermLabel;
	}
	public void setInvestmentTermLabel(String investmentTermLabel) {
		this.investmentTermLabel = investmentTermLabel;
	}
	public String getInvestmentTerm() {
		return investmentTerm;
	}
	public void setInvestmentTerm(String investmentTerm) {
		this.investmentTerm = investmentTerm;
	}
	public String getExpirationTermLabel() {
		return expirationTermLabel;
	}
	public void setExpirationTermLabel(String expirationTermLabel) {
		this.expirationTermLabel = expirationTermLabel;
	}
	public String getExpirationTerm() {
		return expirationTerm;
	}
	public void setExpirationTerm(String expirationTerm) {
		this.expirationTerm = expirationTerm;
	}
	
   
}
