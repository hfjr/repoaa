package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;



/**
 * Created by wangzhangfei on 16/7/12.
 */
public class PledgeLoanTrialInterestDTO {

	private String totalInterest;
	private String repaymentDate;
	private String repaymentPrincipalAndInterest;
	
	public String getTotalInterest() {
		return totalInterest;
	}
	public void setTotalInterest(String totalInterest) {
		this.totalInterest = totalInterest;
	}
	public String getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(String repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public String getRepaymentPrincipalAndInterest() {
		return repaymentPrincipalAndInterest;
	}
	public void setRepaymentPrincipalAndInterest(
			String repaymentPrincipalAndInterest) {
		this.repaymentPrincipalAndInterest = repaymentPrincipalAndInterest;
	}
	
}
