package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:理财贷-投资记录中的产品信息
 * @author: wangzf
 * @date: 2016-06-08 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class FinancialLoanInvestmentProductDetailDTO {
	
	private String productId;//产品ID
	private String productCode;//产品编号 (产品名称和ID的组合)
	private String productName;//产品名称
	private String annualYield;//年化收益 0.1 (便于页面计算)
	private String projectTerm;//产品期限
	private String paymentWay;//还款方式
	private String fromInterestTime;//起息时间
	private String expirationDate;//到期时间

	
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
	public String getPaymentWay() {
		return paymentWay;
	}
	public void setPaymentWay(String paymentWay) {
		this.paymentWay = paymentWay;
	}
	public String getFromInterestTime() {
		return fromInterestTime;
	}
	public void setFromInterestTime(String fromInterestTime) {
		this.fromInterestTime = fromInterestTime;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	
    
}
