package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc：理财贷-投资还款计划列表信息
 * @author： wangzf
 * @date： 2016-06-08 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class FinancialLoanRepayPlanListDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private String paymentInstallments;// 还款,
    private String paymentDate;// 2016-01-16,
    private String principalAndInterestReceivable;// 500.81,
    private String principal;// 500.00,
    private String interest;// 0.81,
    private String principalAndInterestDesc;// 


	private String paymentPrincipalAndInterestLabel	;//	应回本息Label
	private String isShowAdvancePaymentDesc	;//	是否显示提前回款描述(true:显示 false:不显示)
	private String advancePaymentDesc;//	提前回款描述
	private String isHighlight;//	是否高亮(true:高亮false:置灰)

	public String getPaymentInstallments() {
		return paymentInstallments;
	}
	public void setPaymentInstallments(String paymentInstallments) {
		this.paymentInstallments = paymentInstallments;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getPrincipalAndInterestReceivable() {
		return principalAndInterestReceivable;
	}
	public void setPrincipalAndInterestReceivable(String principalAndInterestReceivable) {
		this.principalAndInterestReceivable = principalAndInterestReceivable;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getPrincipalAndInterestDesc() {
		return principalAndInterestDesc;
	}
	public void setPrincipalAndInterestDesc(String principalAndInterestDesc) {
		this.principalAndInterestDesc = principalAndInterestDesc;
	}

	public String getPaymentPrincipalAndInterestLabel() {
		return paymentPrincipalAndInterestLabel;
	}

	public void setPaymentPrincipalAndInterestLabel(String paymentPrincipalAndInterestLabel) {
		this.paymentPrincipalAndInterestLabel = paymentPrincipalAndInterestLabel;
	}

	public String getIsShowAdvancePaymentDesc() {
		return isShowAdvancePaymentDesc;
	}

	public void setIsShowAdvancePaymentDesc(String isShowAdvancePaymentDesc) {
		this.isShowAdvancePaymentDesc = isShowAdvancePaymentDesc;
	}

	public String getAdvancePaymentDesc() {
		return advancePaymentDesc;
	}

	public void setAdvancePaymentDesc(String advancePaymentDesc) {
		this.advancePaymentDesc = advancePaymentDesc;
	}

	public String getIsHighlight() {
		return isHighlight;
	}

	public void setIsHighlight(String isHighlight) {
		this.isHighlight = isHighlight;
	}
}
