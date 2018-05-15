package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:理财贷-投资记录汇总信息
 * @author: wangzf
 * @date: 2016-06-08 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class FinancialLoanInvestmentSummaryDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String inPrincipalCast;//投资总金额
	private String receive;//已还本息
	
	public String getInPrincipalCast() {
		return inPrincipalCast;
	}
	public void setInPrincipalCast(String inPrincipalCast) {
		this.inPrincipalCast = inPrincipalCast;
	}
	public String getReceive() {
		return receive;
	}
	public void setReceive(String receive) {
		this.receive = receive;
	}
	
}
