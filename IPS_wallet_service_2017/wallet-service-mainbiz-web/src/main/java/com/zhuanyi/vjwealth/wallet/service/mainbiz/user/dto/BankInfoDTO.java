package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto;

import java.io.Serializable;

public class BankInfoDTO implements Serializable   {
	private static final long serialVersionUID = 1L;

	//银行简称
	private String bankNameShow;
	// 银行名称
	private String bankName;
	// 银行编码
	private String bankCode;
	// 渠道
	private String channel;

	public String getBankNameShow() {
		return bankNameShow;
	}

	public void setBankNameShow(String bankNameShow) {
		this.bankNameShow = bankNameShow;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName=bankName;
	}
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode=bankCode;
	}
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel=channel;
	}

	@Override
	public String toString() {
		return "BankInfoDTO{" +
				"bankNameShow='" + bankNameShow + '\'' +
				", bankName='" + bankName + '\'' +
				", bankCode='" + bankCode + '\'' +
				", channel='" + channel + '\'' +
				'}';
	}
}
