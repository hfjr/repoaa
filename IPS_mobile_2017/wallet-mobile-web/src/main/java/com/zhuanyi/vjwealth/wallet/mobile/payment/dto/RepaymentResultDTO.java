package com.zhuanyi.vjwealth.wallet.mobile.payment.dto;

import java.io.Serializable;

public class RepaymentResultDTO implements Serializable   {
	private static final long serialVersionUID = 1L;

	private String code;
	private String message;
	private String feedbackInformation;

	public RepaymentResultDTO(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public RepaymentResultDTO(String code, String message, String feedbackInformation) {
		this.code = code;
		this.message = message;
		this.feedbackInformation = feedbackInformation;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
