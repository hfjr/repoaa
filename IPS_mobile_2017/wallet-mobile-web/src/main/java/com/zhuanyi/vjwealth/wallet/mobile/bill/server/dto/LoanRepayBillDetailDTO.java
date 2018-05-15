package com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.Map;

/**
 * 余额申购v+账单详情
 * @author wangzf
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class LoanRepayBillDetailDTO extends BillDetailCommonDTO{
	private String pageTitle;
	private String loanAmountLabel;//“借款本金”
	private String loanAmount;//借款本金
	private String createDate;
	private String orderStatus;

	private String userId;
	private String bizId;
	private String orderType;
	private String relOrderNo;

	List<Map<String,String>> operateStateInfos;//操作流程
	
	public List<Map<String, String>> getOperateStateInfos() {
		return operateStateInfos;
	}
	public void setOperateStateInfos(List<Map<String, String>> operateStateInfos) {
		this.operateStateInfos = operateStateInfos;
	}

	public String getLoanAmountLabel() {
		return loanAmountLabel;
	}

	public void setLoanAmountLabel(String loanAmountLabel) {
		this.loanAmountLabel = loanAmountLabel;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getRelOrderNo() {
		return relOrderNo;
	}

	public void setRelOrderNo(String relOrderNo) {
		this.relOrderNo = relOrderNo;
	}
}
