package com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.bo;

import java.io.Serializable;

/**
 * 回调业务系统函数必要参数
 * @author jiangkaijun
 * 
 */
public class PaymentCallBackParamBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tradeNo;
	private String userId;
	private String orderNo;
	private String orderType;
	private String relBizOrderStatus;
	//交易状态
	private String tradeStatus;

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

	public String getRelBizOrderStatus() {
		return relBizOrderStatus;
	}

	public void setRelBizOrderStatus(String relBizOrderStatus) {
		this.relBizOrderStatus = relBizOrderStatus;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

}
