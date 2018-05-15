package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:理财贷-投资页面初始化
 * @author: wangzf
 * @date: 2016-06-08 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class FinancialLoanInvestmentInitDTO {
	
	private List<Map<String,String>> borrowRecordSummary;//借款记录汇总
	private String isMore;//是否有更多记录
	private List<Object> records;//投资记录
	
	public List<Map<String, String>> getBorrowRecordSummary() {
		return borrowRecordSummary;
	}
	public void setBorrowRecordSummary(List<Map<String, String>> borrowRecordSummary) {
		this.borrowRecordSummary = borrowRecordSummary;
	}
	public String getIsMore() {
		return isMore;
	}
	public void setIsMore(String isMore) {
		this.isMore = isMore;
	}
	public List<Object> getRecords() {
		return records;
	}
	public void setRecords(List<Object> records) {
		this.records = records;
	}
	
}
