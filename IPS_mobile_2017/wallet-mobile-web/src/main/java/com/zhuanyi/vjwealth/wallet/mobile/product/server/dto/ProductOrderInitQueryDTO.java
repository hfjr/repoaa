package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * 下订单初始化 OrderInitDTO 的内嵌产品详情DTO
 * @author wangzf
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class ProductOrderInitQueryDTO {

	private String productId;//产品ID
	private String productCode;//产品编号 (产品名称和ID的组合)
	private String productName;//产品名称
	private String annualYield;//年化收益 0.1 (便于页面计算)
	private String remainingInvestment;//剩余可投资
	private String projectTerm;//产品期限
	private String paymentWay;//回款方式

	
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
	public String getRemainingInvestment() {
		return remainingInvestment;
	}
	public void setRemainingInvestment(String remainingInvestment) {
		this.remainingInvestment = remainingInvestment;
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
}
