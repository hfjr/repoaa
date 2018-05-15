package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;




/**
 * Created by wangzhangfei on 16/7/12.
 */
public class LoanRecordDetailReapyListDTO {

	private String repayDate;
	private String repayTime;
	private String repayTotal;
	private String repayPrincipal;
	private String repayPrincipalTitle;
	private String repayInterest;
	private String repayInterestTitle;
	private String counterFee;
	private String counterFeeTitle;
	private String penaltyTitle;
	private String penalty;

	public String getPenaltyTitle() {
		return penaltyTitle;
	}

	public void setPenaltyTitle(String penaltyTitle) {
		this.penaltyTitle = penaltyTitle;
	}

	public String getPenalty() {
		return penalty;
	}

	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}

	public String getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}
	public String getRepayTime() {
		return repayTime;
	}
	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}
	public String getRepayTotal() {
		return repayTotal;
	}
	public void setRepayTotal(String repayTotal) {
		this.repayTotal = repayTotal;
	}
	public String getRepayPrincipal() {
		return repayPrincipal;
	}
	public void setRepayPrincipal(String repayPrincipal) {
		this.repayPrincipal = repayPrincipal;
	}
	public String getRepayPrincipalTitle() {
		return repayPrincipalTitle;
	}
	public void setRepayPrincipalTitle(String repayPrincipalTitle) {
		this.repayPrincipalTitle = repayPrincipalTitle;
	}
	public String getRepayInterest() {
		return repayInterest;
	}
	public void setRepayInterest(String repayInterest) {
		this.repayInterest = repayInterest;
	}
	public String getRepayInterestTitle() {
		return repayInterestTitle;
	}
	public void setRepayInterestTitle(String repayInterestTitle) {
		this.repayInterestTitle = repayInterestTitle;
	}
	public String getCounterFee() {
		return counterFee;
	}
	public void setCounterFee(String counterFee) {
		this.counterFee = counterFee;
	}
	public String getCounterFeeTitle() {
		return counterFeeTitle;
	}
	public void setCounterFeeTitle(String counterFeeTitle) {
		this.counterFeeTitle = counterFeeTitle;
	}
	
}
