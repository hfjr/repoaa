package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 还款计划列表
 * @author wangzf
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class RepaymentPlanListDTO {
	
	private String investmentPrincipal; //投资本金
	private String interestReceived;//待收利息
	private String repaymentType;//还款方式
	private List<RepaymentPlanDTO> repaymentPlanList;//还款计划列表
	
	public String getInvestmentPrincipal() {
		return investmentPrincipal;
	}
	public void setInvestmentPrincipal(String investmentPrincipal) {
		this.investmentPrincipal = investmentPrincipal;
	}
	public String getInterestReceived() {
		return interestReceived;
	}
	public void setInterestReceived(String interestReceived) {
		this.interestReceived = interestReceived;
	}
	public String getRepaymentType() {
		return repaymentType;
	}
	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}
	public List<RepaymentPlanDTO> getRepaymentPlanList() {
		return repaymentPlanList;
	}
	public void setRepaymentPlanList(List<RepaymentPlanDTO> repaymentPlanList) {
		this.repaymentPlanList = repaymentPlanList;
	}
	
}
