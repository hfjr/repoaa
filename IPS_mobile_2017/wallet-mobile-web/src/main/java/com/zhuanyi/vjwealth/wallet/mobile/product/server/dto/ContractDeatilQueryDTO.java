package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class ContractDeatilQueryDTO {
	
	private String loanerName;//甲方贷款人
	private String loanerUserName;//甲方贷款人 融桥宝用户名
	private String borrowFor;//借款用途
	private String repayType;//还款方式
	private String preRepayDue;//提前还款的约定
	private String contractNo;//合同编号
	private String borrowerName;//借款人
	private String dedit;//逾期违约金
	
	private String contractFixJson;//合同固定值json串
	private String productId;//产品编号
	
	private String investName;//投资人
	private String investUserName;//投资人 融桥宝用户名
	private String investmentAmount;//投资金额
	private String investmentAmount2;//投资金额（万元）
	private String projectTerm;//项目期限
	private String receiveRate;//年利率
	private String policyNo;//保单号
	private String gzqbNo;//融桥宝编号
	private String createDate;//签订日期
	private String totalAmount;//产品总金额
	
	public ContractDeatilQueryDTO(){
		
	}

	public ContractDeatilQueryDTO(String loanerName, String loanerUserName,
			String borrowFor, String repayType, String preRepayDue,
			String contractNo, String borrowerName, String dedit,
			String contractFixJson, String productId, String investName,
			String investUserName, String investmentAmount,
			String investmentAmount2, String projectTerm, String receiveRate,
			String policyNo, String gzqbNo, String createDate,
			String totalAmount) {
		this.loanerName = loanerName;
		this.loanerUserName = loanerUserName;
		this.borrowFor = borrowFor;
		this.repayType = repayType;
		this.preRepayDue = preRepayDue;
		this.contractNo = contractNo;
		this.borrowerName = borrowerName;
		this.dedit = dedit;
		this.contractFixJson = contractFixJson;
		this.productId = productId;
		this.investName = investName;
		this.investUserName = investUserName;
		this.investmentAmount = investmentAmount;
		this.investmentAmount2 = investmentAmount2;
		this.projectTerm = projectTerm;
		this.receiveRate = receiveRate;
		this.policyNo = policyNo;
		this.gzqbNo = gzqbNo;
		this.createDate = createDate;
		this.totalAmount = totalAmount;
	}

	public String getLoanerName() {
		return loanerName;
	}

	public void setLoanerName(String loanerName) {
		this.loanerName = loanerName;
	}

	public String getLoanerUserName() {
		return loanerUserName;
	}

	public void setLoanerUserName(String loanerUserName) {
		this.loanerUserName = loanerUserName;
	}

	public String getBorrowFor() {
		return borrowFor;
	}

	public void setBorrowFor(String borrowFor) {
		this.borrowFor = borrowFor;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	public String getPreRepayDue() {
		return preRepayDue;
	}

	public void setPreRepayDue(String preRepayDue) {
		this.preRepayDue = preRepayDue;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getDedit() {
		return dedit;
	}

	public void setDedit(String dedit) {
		this.dedit = dedit;
	}

	public String getContractFixJson() {
		return contractFixJson;
	}

	public void setContractFixJson(String contractFixJson) {
		this.contractFixJson = contractFixJson;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getInvestName() {
		return investName;
	}

	public void setInvestName(String investName) {
		this.investName = investName;
	}

	public String getInvestUserName() {
		return investUserName;
	}

	public void setInvestUserName(String investUserName) {
		this.investUserName = investUserName;
	}

	public String getInvestmentAmount() {
		return investmentAmount;
	}

	public void setInvestmentAmount(String investmentAmount) {
		this.investmentAmount = investmentAmount;
	}

	public String getInvestmentAmount2() {
		return investmentAmount2;
	}

	public void setInvestmentAmount2(String investmentAmount2) {
		this.investmentAmount2 = investmentAmount2;
	}

	public String getProjectTerm() {
		return projectTerm;
	}

	public void setProjectTerm(String projectTerm) {
		this.projectTerm = projectTerm;
	}

	public String getReceiveRate() {
		return receiveRate;
	}

	public void setReceiveRate(String receiveRate) {
		this.receiveRate = receiveRate;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getGzqbNo() {
		return gzqbNo;
	}

	public void setGzqbNo(String gzqbNo) {
		this.gzqbNo = gzqbNo;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
}
