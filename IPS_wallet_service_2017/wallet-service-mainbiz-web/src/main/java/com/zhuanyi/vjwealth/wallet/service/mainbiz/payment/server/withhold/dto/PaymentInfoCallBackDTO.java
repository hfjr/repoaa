package com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto;

import java.io.Serializable;

/**
 * 支付或代扣业务，回调业务数据对象DTO
 * 
 * @author jiangkaijun
 * 
 */
public class PaymentInfoCallBackDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 用户ID */
	private String userId;

	/** 订单编号 */
	private String orderNo;

	/** 订单类型 */
	private String orderType;

	/** 订单金额 */
	private String amount;
	

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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}
