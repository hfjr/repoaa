package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 产品详情DTO
 * @author cuidez
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class ProductDetailQueryV6DTO {

	private String productId;//产品ID
	private String productCode;//产品编号 (产品名称和ID的组合)
	private String productName;//产品名称
	private String projectTerm;//项目期限
	private String totalFinancing;//投资总额
	private String annualYield;//年化收益 0.1 (便于页面计算)
	private String startInvestmentAmount;//起投
	private String remainingInvestment;//剩余可投资
	private String expirationDate;//到期日期
	private String interestDate;//计息日期
	private String repayment;//还款方式
	private String newPersonMark;//新人标识
	private String newPersonExclusiveContent;//新人专享内容
	private String guaranteeContents;//保障介绍内容
	private String projectIntroduction;//项目介绍内容
	private String principalAndInterestGuarantee;//是否有本息保障
	@JsonIgnore
	private String borrowerStr;//借款人json串
	private String investmentRecord;//投资记录数
	private String whetherInvestment;//是否可投资
	private String investmentInformation;//提示信息
	private String borrowerType;//借款类型
	private Map<String,Object>  borrower;//借款人

	private String isPreExpire;//是否提前还款
	private String wjAdvanceDueDate;//蔚杰提前到期时间

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
	public String getTotalFinancing() {
		return totalFinancing;
	}
	public void setTotalFinancing(String totalFinancing) {
		this.totalFinancing = totalFinancing;
	}
	
	public String getAnnualYield() {
		return annualYield;
	}
	public void setAnnualYield(String annualYield) {
		this.annualYield = annualYield;
	}
	public String getStartInvestmentAmount() {
		return startInvestmentAmount;
	}
	public void setStartInvestmentAmount(String startInvestmentAmount) {
		this.startInvestmentAmount = startInvestmentAmount;
	}
	public String getRemainingInvestment() {
		return remainingInvestment;
	}
	public void setRemainingInvestment(String remainingInvestment) {
		this.remainingInvestment = remainingInvestment;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
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
	public String getNewPersonMark() {
		return newPersonMark;
	}
	public void setNewPersonMark(String newPersonMark) {
		this.newPersonMark = newPersonMark;
	}
	public String getNewPersonExclusiveContent() {
		return newPersonExclusiveContent;
	}
	public void setNewPersonExclusiveContent(String newPersonExclusiveContent) {
		this.newPersonExclusiveContent = newPersonExclusiveContent;
	}
	public String getGuaranteeContents() {
		return guaranteeContents;
	}
	public void setGuaranteeContents(String guaranteeContents) {
		this.guaranteeContents = guaranteeContents;
	}
	public String getProjectIntroduction() {
		return projectIntroduction;
	}
	public void setProjectIntroduction(String projectIntroduction) {
		this.projectIntroduction = projectIntroduction;
	}
	public String getPrincipalAndInterestGuarantee() {
		return principalAndInterestGuarantee;
	}
	public void setPrincipalAndInterestGuarantee(String principalAndInterestGuarantee) {
		this.principalAndInterestGuarantee = principalAndInterestGuarantee;
	}
	
	public String getProjectTerm() {
		return projectTerm;
	}
	public void setProjectTerm(String projectTerm) {
		this.projectTerm = projectTerm;
	}
	public String getInvestmentRecord() {
		return investmentRecord;
	}
	public void setInvestmentRecord(String investmentRecord) {
		this.investmentRecord = investmentRecord;
	}
	public String getWhetherInvestment() {
		return whetherInvestment;
	}
	public void setWhetherInvestment(String whetherInvestment) {
		this.whetherInvestment = whetherInvestment;
	}
	public String getInvestmentInformation() {
		return investmentInformation;
	}
	public void setInvestmentInformation(String investmentInformation) {
		this.investmentInformation = investmentInformation;
	}
	public String getBorrowerStr() {
		return borrowerStr;
	}
	public void setBorrowerStr(String borrowerStr) {
		this.borrowerStr = borrowerStr;
	}
	public Map<String, Object> getBorrower() {
		return borrower;
	}
	public void setBorrower(Map<String, Object> borrower) {
		this.borrower = borrower;
	}
	public String getBorrowerType() {
		return borrowerType;
	}
	public void setBorrowerType(String borrowerType) {
		this.borrowerType = borrowerType;
	}

	public String getIsPreExpire() {
		return isPreExpire;
	}

	public void setIsPreExpire(String isPreExpire) {
		this.isPreExpire = isPreExpire;
	}

	public String getWjAdvanceDueDate() {
		return wjAdvanceDueDate;
	}

	public void setWjAdvanceDueDate(String wjAdvanceDueDate) {
		this.wjAdvanceDueDate = wjAdvanceDueDate;
	}
}
