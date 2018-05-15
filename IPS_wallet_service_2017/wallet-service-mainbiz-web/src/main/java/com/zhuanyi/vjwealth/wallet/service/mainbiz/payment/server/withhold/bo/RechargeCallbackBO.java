package com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.bo;

import java.math.BigDecimal;


public class RechargeCallbackBO {

	public static final String RECHARGE_MA_CONFIRM="recharge_ma_confirm";
	
	public static final String RECHARGE_MA_CONFIRM_DISPOSE="recharge_ma_confirm_dispose";
	

	private String orderStatus; // 订单状态
	private String userId;  //用户Id
	private BigDecimal amount;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	
	
	
	

}
