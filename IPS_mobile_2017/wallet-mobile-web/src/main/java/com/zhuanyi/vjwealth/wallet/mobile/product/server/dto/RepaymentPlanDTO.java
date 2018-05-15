package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * 还款计划
 * @author wangzf
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class RepaymentPlanDTO {
	
	private String paymentInstallments; //回款期数
	private String paymentDate;//还款日期
	private String principalAndInterestReceivable;//本息合计
	private String principal;//本金
	private String interest;//利息
	private String principalAndInterestDesc;//本金0.00元 |利息0.00元

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
