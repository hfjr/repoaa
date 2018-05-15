package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;


import java.io.Serializable;

/**
 * Created by wangzhangfei on 16/7/12.
 */
public class PledgeApplyDTO implements Serializable{

	private String code;
	private String message;
	private String feedbackInformation;
	private String iconURL;
	private String receivableBankCardLabel;
	private String receivableBankCard;
	
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
	public String getReceivableBankCardLabel() {
		return receivableBankCardLabel;
	}
	public void setReceivableBankCardLabel(String receivableBankCardLabel) {
		this.receivableBankCardLabel = receivableBankCardLabel;
	}
	public String getReceivableBankCard() {
		return receivableBankCard;
	}
	public void setReceivableBankCard(String receivableBankCard) {
		this.receivableBankCard = receivableBankCard;
	}
	
}
