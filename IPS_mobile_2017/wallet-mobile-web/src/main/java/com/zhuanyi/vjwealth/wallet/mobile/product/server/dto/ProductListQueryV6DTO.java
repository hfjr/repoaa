package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * 产品列表DTO
 * @author cuidz
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class ProductListQueryV6DTO {

	private String productId;//产品ID
	private String productCode;//产品编号 (产品名称和ID的组合)
	private String productName;//产品名称
	private String projectTermLabel = "项目期限";
	private String projectTerm;//项目期限 120 (天数)
	private String totalFinancing;
	private String annualYieldLabel = "年化收益";
	private String annualYield;//年化收益 0.1 (便于页面计算)
	private String remainingInvestmentLabel = "剩余可投";
	private String remainingInvestment;//剩余可投资
	private String schedule;//进度 0.7 (标识70%)
	private String newPersonMark;//新人标识
	private String newPersonMarkUrl;//新人表示图标URL
	private String goodChoiceMark;//优选标
	private String couponMark; //是否可用卡券标识
	private String packageMark; //是否可用红包标识
	private String productSalesMarkURL; // 产品销售状态图标UR［募集中：sales｜抢光了：soldOut ｜下架：shelves］
	private String interestDate;//计息方式
	private String repayment;//还款方式
	private String fromInvestmentAmount;//100元起投
	private String zhongAnTip;//众安保险承保

	
	public String getGoodChoiceMark() {
		return goodChoiceMark;
	}

	public void setGoodChoiceMark(String goodChoiceMark) {
		this.goodChoiceMark = goodChoiceMark;
	}

	public String getCouponMark() {
		return couponMark;
	}

	public void setCouponMark(String couponMark) {
		this.couponMark = couponMark;
	}

	public String getPackageMark() {
		return packageMark;
	}

	public void setPackageMark(String packageMark) {
		this.packageMark = packageMark;
	}

	public String getTotalFinancing() {
		return totalFinancing;
	}

	public void setTotalFinancing(String totalFinancing) {
		this.totalFinancing = totalFinancing;
	}

	public String getZhongAnTip() {
		return zhongAnTip;
	}

	public void setZhongAnTip(String zhongAnTip) {
		this.zhongAnTip = zhongAnTip;
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
	public String getProjectTermLabel() {
		return projectTermLabel;
	}
	public void setProjectTermLabel(String projectTermLabel) {
		this.projectTermLabel = projectTermLabel;
	}
	public String getAnnualYieldLabel() {
		return annualYieldLabel;
	}
	public void setAnnualYieldLabel(String annualYieldLabel) {
		this.annualYieldLabel = annualYieldLabel;
	}
	public String getRemainingInvestmentLabel() {
		return remainingInvestmentLabel;
	}
	public void setRemainingInvestmentLabel(String remainingInvestmentLabel) {
		this.remainingInvestmentLabel = remainingInvestmentLabel;
	}
	public String getProductSalesMarkURL() {
		return productSalesMarkURL;
	}
	public void setProductSalesMarkURL(String productSalesMarkURL) {
		this.productSalesMarkURL = productSalesMarkURL;
	}
	public String getNewPersonMark() {
		return newPersonMark;
	}
	public void setNewPersonMark(String newPersonMark) {
		this.newPersonMark = newPersonMark;
	}
	public String getNewPersonMarkUrl() {
		return newPersonMarkUrl;
	}
	public void setNewPersonMarkUrl(String newPersonMarkUrl) {
		this.newPersonMarkUrl = newPersonMarkUrl;
	}
	public String getInterestDate() {
		return interestDate;
	}
	public void setInterestDate(String interestDate) {
		this.interestDate = interestDate;
	}
	public String getRepayment() {
		return repayment;
	}
	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}
	public String getFromInvestmentAmount() {
		return fromInvestmentAmount;
	}
	public void setFromInvestmentAmount(String fromInvestmentAmount) {
		this.fromInvestmentAmount = fromInvestmentAmount;
	}
}
