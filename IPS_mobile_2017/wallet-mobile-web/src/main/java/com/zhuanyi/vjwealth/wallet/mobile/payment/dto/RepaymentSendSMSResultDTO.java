package com.zhuanyi.vjwealth.wallet.mobile.payment.dto;

import java.io.Serializable;

public class RepaymentSendSMSResultDTO {

	private String vaildeTime;
	private String tradeNo;

	public RepaymentSendSMSResultDTO() {
	}

	public RepaymentSendSMSResultDTO(String vaildeTime, String tradeNo) {
		this.vaildeTime = vaildeTime;
		this.tradeNo = tradeNo;
	}

	public String getVaildeTime() {
		return vaildeTime;
	}

	public void setVaildeTime(String vaildeTime) {
		this.vaildeTime = vaildeTime;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
}
