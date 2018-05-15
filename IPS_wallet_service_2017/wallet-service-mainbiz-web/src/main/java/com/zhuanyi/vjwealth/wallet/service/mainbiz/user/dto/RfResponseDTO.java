package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto;

import java.math.BigDecimal;
import java.util.List;

public class RfResponseDTO {

	private String policyNo;
	
	private String loanId;

	private String tradeId;

	private BigDecimal amount;

	private String name;

	private String identifyNo;

	private String phone;

	//还款计划
	private List<RfRepayPlanDTO> repayplans;	

	private String orderNo;

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentifyNo() {
		return identifyNo;
	}

	public void setIdentifyNo(String identifyNo) {
		this.identifyNo = identifyNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<RfRepayPlanDTO> getRepayplans() {
		return repayplans;
	}

	public void setRepayplans(List<RfRepayPlanDTO> repayplans) {
		this.repayplans = repayplans;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
