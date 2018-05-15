package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:理财贷-投资产品与信贷产品关系映射
 * @author: wangzf
 * @date: 2016-06-08 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class FinancialLoanCreditTrialDTO {
	
	private String repaymentMethod; //还款方式
	private String periods; //贷款期限（30天）
	private String lastRepaymentDateStr; //还款时间（最后一期）
	private String principalTotal; //应还本金（总）
	private String loanInterestTotal; // 应还利息（总）
	private String financialInterestTotal; // 投资收益
	
	
	public String getRepaymentMethod() {
		return repaymentMethod;
	}
	public void setRepaymentMethod(String repaymentMethod) {
		this.repaymentMethod = repaymentMethod;
	}
	public String getPeriods() {
		return periods;
	}
	public void setPeriods(String periods) {
		this.periods = periods;
	}
	public String getLastRepaymentDateStr() {
		return lastRepaymentDateStr;
	}
	public void setLastRepaymentDateStr(String lastRepaymentDateStr) {
		this.lastRepaymentDateStr = lastRepaymentDateStr;
	}
	public String getPrincipalTotal() {
		return principalTotal;
	}
	public void setPrincipalTotal(String principalTotal) {
		this.principalTotal = principalTotal;
	}
	public String getLoanInterestTotal() {
		return loanInterestTotal;
	}
	public void setLoanInterestTotal(String loanInterestTotal) {
		this.loanInterestTotal = loanInterestTotal;
	}
	public String getFinancialInterestTotal() {
		return financialInterestTotal;
	}
	public void setFinancialInterestTotal(String financialInterestTotal) {
		this.financialInterestTotal = financialInterestTotal;
	}
	
}
