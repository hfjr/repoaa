package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;




/**
 * Created by wangzhangfei on 16/7/12.
 */
public class DueRepaymentInitiInnerRecordDTO {

	private String loanCode;
	private String toRepaymentMoneyDate;
	private String toRepaymentLabel;
	private String surplusCapital;
	private String loanDateAndMoney;
	private String surplusInterest;

	private String noRepaymentPeriod;//剩余期数
	private String loanStatus;//订单状态
	private String principal;
	private String interest;

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getToRepaymentMoneyDate() {
		return toRepaymentMoneyDate;
	}
	public void setToRepaymentMoneyDate(String toRepaymentMoneyDate) {
		this.toRepaymentMoneyDate = toRepaymentMoneyDate;
	}
	public String getSurplusCapital() {
		return surplusCapital;
	}
	public void setSurplusCapital(String surplusCapital) {
		this.surplusCapital = surplusCapital;
	}
	public String getLoanDateAndMoney() {
		return loanDateAndMoney;
	}
	public void setLoanDateAndMoney(String loanDateAndMoney) {
		this.loanDateAndMoney = loanDateAndMoney;
	}
	public String getSurplusInterest() {
		return surplusInterest;
	}
	public void setSurplusInterest(String surplusInterest) {
		this.surplusInterest = surplusInterest;
	}
	public String getToRepaymentLabel() {
		return toRepaymentLabel;
	}
	public void setToRepaymentLabel(String toRepaymentLabel) {
		this.toRepaymentLabel = toRepaymentLabel;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getNoRepaymentPeriod() {
		return noRepaymentPeriod;
	}

	public void setNoRepaymentPeriod(String noRepaymentPeriod) {
		this.noRepaymentPeriod = noRepaymentPeriod;
	}
}
