package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:理财贷-还款计划信息
 * @author: wangzf
 * @date: 2016-06-08 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class FinancialLoanCreditDetailDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String borrowAmountLabel;//"借款金额"
	private String borrowAmount;//借款总金额
	private List<Map<String,String>> loanInfos;// 贷款信息汇总：{"label":"还款期数","value":"30天"},{"label":"利息","value":"20.00"}
	private String paymentRecordsLabel;//还款记录
	private String contractLabel;//合同及相关协议
	private List<FinancialLoanCreditRepayPlanDetailDTO> paymentRecords;//还款记录
	
	private String contractTitle;
    private String contractURL;

	private String contractCode;
	
	@JsonIgnore
	private List<FinancialLoanCreditRepayPlanDetailDTO> paymentPlanRecords;//还款记录
	@JsonIgnore
	private String periods;//贷款期限
	@JsonIgnore
	private String sumInterest;//贷款利息
	
	private String borrowCode;
	
	public String getBorrowAmountLabel() {
		return borrowAmountLabel;
	}
	public void setBorrowAmountLabel(String borrowAmountLabel) {
		this.borrowAmountLabel = borrowAmountLabel;
	}
	public String getBorrowAmount() {
		return borrowAmount;
	}
	public void setBorrowAmount(String borrowAmount) {
		this.borrowAmount = borrowAmount;
	}
	
	public List<Map<String, String>> getLoanInfos() {
		return loanInfos;
	}
	public void setLoanInfos(List<Map<String, String>> loanInfos) {
		this.loanInfos = loanInfos;
	}
	public String getPaymentRecordsLabel() {
		return paymentRecordsLabel;
	}
	public void setPaymentRecordsLabel(String paymentRecordsLabel) {
		this.paymentRecordsLabel = paymentRecordsLabel;
	}
	public List<FinancialLoanCreditRepayPlanDetailDTO> getPaymentRecords() {
		return paymentRecords;
	}
	public void setPaymentRecords(List<FinancialLoanCreditRepayPlanDetailDTO> paymentRecords) {
		this.paymentRecords = paymentRecords;
	}
	public String getContractLabel() {
		return contractLabel;
	}
	public void setContractLabel(String contractLabel) {
		this.contractLabel = contractLabel;
	}
	public List<FinancialLoanCreditRepayPlanDetailDTO> getPaymentPlanRecords() {
		return paymentPlanRecords;
	}
	public void setPaymentPlanRecords(List<FinancialLoanCreditRepayPlanDetailDTO> paymentPlanRecords) {
		this.paymentPlanRecords = paymentPlanRecords;
	}
	public String getPeriods() {
		return periods;
	}
	public void setPeriods(String periods) {
		this.periods = periods;
	}
	public String getSumInterest() {
		return sumInterest;
	}
	public void setSumInterest(String sumInterest) {
		this.sumInterest = sumInterest;
	}
	public String getContractTitle() {
		return contractTitle;
	}
	public void setContractTitle(String contractTitle) {
		this.contractTitle = contractTitle;
	}
	public String getContractURL() {
		return contractURL;
	}
	public void setContractURL(String contractURL) {
		this.contractURL = contractURL;
	}


	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getBorrowCode() {
		return borrowCode;
	}
	public void setBorrowCode(String borrowCode) {
		this.borrowCode = borrowCode;
	}
	
	
}
