package com.zhuanyi.vjwealth.wallet.mobile.order.dto;

import com.fab.core.entity.dto.BaseDTO;

public class WjOrderInfoDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	//用户id
	private String userId;
	private String orderNo;
	private String orderType;
	private String orderStatus;
	private String relOrderNo;
	private String borrowCode;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getRelOrderNo() {
		return relOrderNo;
	}
	public void setRelOrderNo(String relOrderNo) {
		this.relOrderNo = relOrderNo;
	}
	public String getBorrowCode() {
		return borrowCode;
	}
	public void setBorrowCode(String borrowCode) {
		this.borrowCode = borrowCode;
	}
	
	
}
