package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * 产品列表DTO
 * @author wangzf
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class ProductListQueryOldDTO {

	private String productId;//产品ID
	private String productCode;//产品编号 (产品名称和ID的组合)
	private String productName;//产品名称
	private String projectTerm;//项目期限 120 (天数)
	private String annualYield;//年化收益 0.1 (便于页面计算)
	private String remainingTerm;//剩余期限 90 (天数)
	private String remainingInvestment;//剩余可投资
	private String schedule;//进度 0.7 (标识70%)
	private String newPersonMark;//新人标识
	private String productSalesMark;//产品销售标识
	private String productSalesOrNewPersonMarkURL;//产品销售状态及新手标识图片URL
	private String insuranceCompany;//保险公司
	private String repayment;//还款方式
	private String remainTermAndAmount;//剩余期限及剩余可投金额的拼接字符串（）
	
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
	public String getRemainingTerm() {
		return remainingTerm;
	}
	public void setRemainingTerm(String remainingTerm) {
		this.remainingTerm = remainingTerm;
	}
	public String getRemainingInvestment() {
		return remainingInvestment;
	}
	public void setRemainingInvestment(String remainingInvestment) {
		this.remainingInvestment = remainingInvestment;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getNewPersonMark() {
		return newPersonMark;
	}
	public void setNewPersonMark(String newPersonMark) {
		this.newPersonMark = newPersonMark;
	}
	public String getProductSalesMark() {
		return productSalesMark;
	}
	public void setProductSalesMark(String productSalesMark) {
		this.productSalesMark = productSalesMark;
	}
	public String getProductSalesOrNewPersonMarkURL() {
		return productSalesOrNewPersonMarkURL;
	}
	public void setProductSalesOrNewPersonMarkURL(String productSalesOrNewPersonMarkURL) {
		this.productSalesOrNewPersonMarkURL = productSalesOrNewPersonMarkURL;
	}
	
	public String getRepayment() {
		return repayment;
	}
	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}
	public String getRemainTermAndAmount() {
		return remainTermAndAmount;
	}
	public void setRemainTermAndAmount(String remainTermAndAmount) {
		this.remainTermAndAmount = remainTermAndAmount;
	}

	public String getInsuranceCompany() {
		return insuranceCompany;
	}

	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}
}
