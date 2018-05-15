package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:理财贷-贷款记录
 * @author: wangzf
 * @date: 2016-06-08 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class FinancialLoanCreditDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String borrowAmount 	;// 	借款汇总
	private String borrowCode 	;// 	借款编号
	private String borrowAmountDescription 	;// 	借款金额描述
	private String repaymentStatusMarkURL 	;// 	还款状态图标URL
	private String investProjectDescription 	;// 	投资项目描述
	private String borrowDateLabel 	;// 	借款时间Label
	private String borrowDate 	;// 	借款时间
	private String paidAmountLabel 	;// 	借款Label
	private String paidAmount 	;// 	已还
	private String allCompleteRepaymentDateLabel 	;// 	全部还完日期Label
	private String allCompleteRepaymentDate 	;// 	全部还完日期
	private String repaymentStateDescription 	;// 	已还清
	@JsonIgnore
	private String repaymentStatus 	;// 	还款状态
	@JsonIgnore
	private String loanProductId;//贷款产品编号
	@JsonIgnore
	private String advExpireDate;

	public String getBorrowAmount() {
		return borrowAmount;
	}
	public void setBorrowAmount(String borrowAmount) {
		this.borrowAmount = borrowAmount;
	}
	public String getBorrowCode() {
		return borrowCode;
	}
	public void setBorrowCode(String borrowCode) {
		this.borrowCode = borrowCode;
	}
	public String getBorrowAmountDescription() {
		return borrowAmountDescription;
	}
	public void setBorrowAmountDescription(String borrowAmountDescription) {
		this.borrowAmountDescription = borrowAmountDescription;
	}
	public String getRepaymentStatusMarkURL() {
		return repaymentStatusMarkURL;
	}
	public void setRepaymentStatusMarkURL(String repaymentStatusMarkURL) {
		this.repaymentStatusMarkURL = repaymentStatusMarkURL;
	}
	public String getInvestProjectDescription() {
		return investProjectDescription;
	}
	public void setInvestProjectDescription(String investProjectDescription) {
		this.investProjectDescription = investProjectDescription;
	}
	public String getBorrowDateLabel() {
		return borrowDateLabel;
	}
	public void setBorrowDateLabel(String borrowDateLabel) {
		this.borrowDateLabel = borrowDateLabel;
	}
	public String getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(String borrowDate) {
		this.borrowDate = borrowDate;
	}
	public String getPaidAmountLabel() {
		return paidAmountLabel;
	}
	public void setPaidAmountLabel(String paidAmountLabel) {
		this.paidAmountLabel = paidAmountLabel;
	}
	public String getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}
	public String getAllCompleteRepaymentDateLabel() {
		return allCompleteRepaymentDateLabel;
	}
	public void setAllCompleteRepaymentDateLabel(String allCompleteRepaymentDateLabel) {
		this.allCompleteRepaymentDateLabel = allCompleteRepaymentDateLabel;
	}
	public String getAllCompleteRepaymentDate() {
		return allCompleteRepaymentDate;
	}
	public void setAllCompleteRepaymentDate(String allCompleteRepaymentDate) {
		this.allCompleteRepaymentDate = allCompleteRepaymentDate;
	}
	public String getRepaymentStateDescription() {
		return repaymentStateDescription;
	}
	public void setRepaymentStateDescription(String repaymentStateDescription) {
		this.repaymentStateDescription = repaymentStateDescription;
	}
	
	public String getRepaymentStatus() {
		return repaymentStatus;
	}
	public void setRepaymentStatus(String repaymentStatus) {
		this.repaymentStatus = repaymentStatus;
	}
	public String getLoanProductId() {
		return loanProductId;
	}
	public void setLoanProductId(String loanProductId) {
		this.loanProductId = loanProductId;
	}

	public String getAdvExpireDate() {
		return advExpireDate;
	}

	public void setAdvExpireDate(String advExpireDate) {
		this.advExpireDate = advExpireDate;
	}
}
