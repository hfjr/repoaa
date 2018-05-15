package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class UserEAccountHomeDTO {
	private String totalAccountAmount;//e账户金额
	private String amountFrozen;//e账户冻结金额
	private String allAreadyReceive;//累计收益
	private String yesterdayReceive;//昨日收益
	private String isSign;//是否是工资的用户（wj_user_channel_ref  中usertype 为wallet或salary_bill）
	private String everyReceiveRate;//万分收益
	private String weekReceiveRate;//七日年化收益
	
	
	public String getTotalAccountAmount() {
		return totalAccountAmount;
	}
	public void setTotalAccountAmount(String totalAccountAmount) {
		this.totalAccountAmount = totalAccountAmount;
	}
	public String getAmountFrozen() {
		return amountFrozen;
	}
	public void setAmountFrozen(String amountFrozen) {
		this.amountFrozen = amountFrozen;
	}
	public String getAllAreadyReceive() {
		return allAreadyReceive;
	}
	public void setAllAreadyReceive(String allAreadyReceive) {
		this.allAreadyReceive = allAreadyReceive;
	}
	public String getYesterdayReceive() {
		return yesterdayReceive;
	}
	public void setYesterdayReceive(String yesterdayReceive) {
		this.yesterdayReceive = yesterdayReceive;
	}
	public String getIsSign() {
		return isSign;
	}
	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}
	public String getEveryReceiveRate() {
		return everyReceiveRate;
	}
	public void setEveryReceiveRate(String everyReceiveRate) {
		this.everyReceiveRate = everyReceiveRate;
	}
	public String getWeekReceiveRate() {
		return weekReceiveRate;
	}
	public void setWeekReceiveRate(String weekReceiveRate) {
		this.weekReceiveRate = weekReceiveRate;
	}
	
}
