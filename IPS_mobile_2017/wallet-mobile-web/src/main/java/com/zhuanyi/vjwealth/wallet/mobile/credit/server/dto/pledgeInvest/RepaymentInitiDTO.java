package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;



/**
 * Created by wangzhangfei on 16/7/12.
 */
public class RepaymentInitiDTO {

	private String noRepaymentCapitalTitle;
	private String noRepaymentCapital;
	private String noSettleLoanDescription;
	private String dueRepaymentLabel;
	private String dueRepayment;
	private String dueRepaymentType;
	private String earlyRepaymentLabel;
	private String earlyRepayment;
	private String earlyRepaymentType;
	private String loanStatus;

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	public String getNoRepaymentCapitalTitle() {
		return noRepaymentCapitalTitle;
	}
	public void setNoRepaymentCapitalTitle(String noRepaymentCapitalTitle) {
		this.noRepaymentCapitalTitle = noRepaymentCapitalTitle;
	}
	public String getNoRepaymentCapital() {
		return noRepaymentCapital;
	}
	public void setNoRepaymentCapital(String noRepaymentCapital) {
		this.noRepaymentCapital = noRepaymentCapital;
	}
	public String getNoSettleLoanDescription() {
		return noSettleLoanDescription;
	}
	public void setNoSettleLoanDescription(String noSettleLoanDescription) {
		this.noSettleLoanDescription = noSettleLoanDescription;
	}
	public String getDueRepaymentLabel() {
		return dueRepaymentLabel;
	}
	public void setDueRepaymentLabel(String dueRepaymentLabel) {
		this.dueRepaymentLabel = dueRepaymentLabel;
	}
	public String getDueRepayment() {
		return dueRepayment;
	}
	public void setDueRepayment(String dueRepayment) {
		this.dueRepayment = dueRepayment;
	}
	public String getDueRepaymentType() {
		return dueRepaymentType;
	}
	public void setDueRepaymentType(String dueRepaymentType) {
		this.dueRepaymentType = dueRepaymentType;
	}
	public String getEarlyRepaymentLabel() {
		return earlyRepaymentLabel;
	}
	public void setEarlyRepaymentLabel(String earlyRepaymentLabel) {
		this.earlyRepaymentLabel = earlyRepaymentLabel;
	}
	public String getEarlyRepayment() {
		return earlyRepayment;
	}
	public void setEarlyRepayment(String earlyRepayment) {
		this.earlyRepayment = earlyRepayment;
	}
	public String getEarlyRepaymentType() {
		return earlyRepaymentType;
	}
	public void setEarlyRepaymentType(String earlyRepaymentType) {
		this.earlyRepaymentType = earlyRepaymentType;
	}
	
}
