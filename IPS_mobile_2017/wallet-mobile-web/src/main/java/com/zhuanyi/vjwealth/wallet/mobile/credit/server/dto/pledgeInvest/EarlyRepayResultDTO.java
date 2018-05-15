package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;



/**
 * Created by wangzhangfei on 16/7/12.
 */
public class EarlyRepayResultDTO {

	private String code;//成功：203200;失败：203201；其他业务失败提示：203202
	private String message;
	private String feedbackInformation;
	private String iconURL;
	
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
	public String getIconURL() {
		return iconURL;
	}
	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}
}
