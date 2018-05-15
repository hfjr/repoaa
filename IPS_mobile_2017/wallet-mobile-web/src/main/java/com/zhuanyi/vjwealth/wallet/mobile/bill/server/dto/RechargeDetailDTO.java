package com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 充值账单详情
 * @author Administrator
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class RechargeDetailDTO extends BillDetailCommonDTO{
	
	
	private String rechargeCardLabel;//"充值卡",
	private String rechargeCard;//"招商银行(尾号5944)",
	private String bankLogoURL;//银行图标链接
	private String rechargeTimeLabel;//"充值时间",
	private String rechargeTime;//"2015-10-14 21:21:58"
	private String rechargeResultLabel;//充值结果
	private String rechargeResult;
	
	public String getRechargeCardLabel() {
		return rechargeCardLabel;
	}
	public void setRechargeCardLabel(String rechargeCardLabel) {
		this.rechargeCardLabel = rechargeCardLabel;
	}
	public String getRechargeCard() {
		return rechargeCard;
	}
	public void setRechargeCard(String rechargeCard) {
		this.rechargeCard = rechargeCard;
	}
	public String getBankLogoURL() {
		return bankLogoURL;
	}
	public void setBankLogoURL(String bankLogoURL) {
		this.bankLogoURL = bankLogoURL;
	}
	public String getRechargeTimeLabel() {
		return rechargeTimeLabel;
	}
	public void setRechargeTimeLabel(String rechargeTimeLabel) {
		this.rechargeTimeLabel = rechargeTimeLabel;
	}
	public String getRechargeTime() {
		return rechargeTime;
	}
	public void setRechargeTime(String rechargeTime) {
		this.rechargeTime = rechargeTime;
	}
	public String getRechargeResultLabel() {
		return rechargeResultLabel;
	}
	public void setRechargeResultLabel(String rechargeResultLabel) {
		this.rechargeResultLabel = rechargeResultLabel;
	}
	public String getRechargeResult() {
		return rechargeResult;
	}
	public void setRechargeResult(String rechargeResult) {
		this.rechargeResult = rechargeResult;
	}

}
