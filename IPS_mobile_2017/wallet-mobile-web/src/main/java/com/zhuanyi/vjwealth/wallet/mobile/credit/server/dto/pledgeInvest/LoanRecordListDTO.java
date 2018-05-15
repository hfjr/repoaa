package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;




/**
 * Created by wangzhangfei on 16/7/12.
 */
public class LoanRecordListDTO {

	private String loanCode;
	private String loanAmount;
	private String loanTimeStr;
	private String loanStatus;
	private String loanStatusStr;
	
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getLoanTimeStr() {
		return loanTimeStr;
	}
	public void setLoanTimeStr(String loanTimeStr) {
		this.loanTimeStr = loanTimeStr;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getLoanStatusStr() {
		return loanStatusStr;
	}
	public void setLoanStatusStr(String loanStatusStr) {
		this.loanStatusStr = loanStatusStr;
	}
	
	
}
