package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class UserMAccountHomeDTO {
	
	private String amountAvailable;//ma余额
	private String totalAccountAmount;//总资产（ma,ea,rf,v1账户总和）
	private String amountFrozen;//冻结金额
	private String allAreadyReceive;//累计收益
	private String yesterdayReceive;//昨日收益
	private String investmentAmount;//投资金额
	private String isSign;//是否是工资的用户（wj_user_channel_ref  中usertype 为wallet或salary_bill）
	private String isShowV1;//是否显示v1菜单模块
	
	public String getAmountAvailable() {
		return amountAvailable;
	}
	public void setAmountAvailable(String amountAvailable) {
		this.amountAvailable = amountAvailable;
	}
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
	public String getInvestmentAmount() {
		return investmentAmount;
	}
	public void setInvestmentAmount(String investmentAmount) {
		this.investmentAmount = investmentAmount;
	}
	public String getIsSign() {
		return isSign;
	}
	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}
	public String getIsShowV1() {
		return isShowV1;
	}
	public void setIsShowV1(String isShowV1) {
		this.isShowV1 = isShowV1;
	}
	
	
}
