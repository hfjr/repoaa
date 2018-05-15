package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:理财贷-投资还款计划
 * @author: wangzf
 * @date: 2016-06-08 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class FinancialLoanRepayPlanDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String investmentPrincipal;//投资本金
    private String interestReceived;//待收利息
    private String repaymentType;//还款方式
    private List<FinancialLoanRepayPlanListDTO> repaymentPlanList;//还款计划列表
    
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
	public List<FinancialLoanRepayPlanListDTO> getRepaymentPlanList() {
		return repaymentPlanList;
	}
	public void setRepaymentPlanList(List<FinancialLoanRepayPlanListDTO> repaymentPlanList) {
		this.repaymentPlanList = repaymentPlanList;
	}
    
}
