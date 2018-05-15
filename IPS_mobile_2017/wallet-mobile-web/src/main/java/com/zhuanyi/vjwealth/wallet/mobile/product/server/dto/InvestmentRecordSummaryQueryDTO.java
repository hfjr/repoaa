package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * 投资汇总
 * @author wangzf
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class InvestmentRecordSummaryQueryDTO {

	private String inPrincipalCast;//在投本金
	private String interestReceived;//待收利息
	private String accumulatedEarnings;//累计收益
	
	public String getInPrincipalCast() {
		return inPrincipalCast;
	}
	public void setInPrincipalCast(String inPrincipalCast) {
		this.inPrincipalCast = inPrincipalCast;
	}
	public String getInterestReceived() {
		return interestReceived;
	}
	public void setInterestReceived(String interestReceived) {
		this.interestReceived = interestReceived;
	}
	public String getAccumulatedEarnings() {
		return accumulatedEarnings;
	}
	public void setAccumulatedEarnings(String accumulatedEarnings) {
		this.accumulatedEarnings = accumulatedEarnings;
	}
	
}
