package com.zhuanyi.vjwealth.wallet.mobile.payment.dto;

import java.io.Serializable;

/**
 * 查询交易结果DTO
 * 
 * @author jiangkaijun
 * 
 */
public class TradeResultDTO implements Serializable{

	private String resultCode;

	private String message;

	private String feedbackInformation;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFeedbackInformation() {
		return feedbackInformation;
	}

	public void setFeedbackInformation(String feedbackInformation) {
		this.feedbackInformation = feedbackInformation;
	}
}
