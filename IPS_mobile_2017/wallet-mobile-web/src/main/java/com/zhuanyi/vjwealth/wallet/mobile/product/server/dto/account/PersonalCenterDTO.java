package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.account;

public class PersonalCenterDTO {
	private String availableBalance;//ma余额
	private String totalAssets;//总资产（ma,ea,rf,v1账户总和）
	private String freezingAmount;//冻结金额
	private String totalRevenue;//累计收益
	private String regularAmount;//我的定期
	private String currentAmount;//我的活期

	public String getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}

	public String getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(String totalAssets) {
		this.totalAssets = totalAssets;
	}

	public String getFreezingAmount() {
		return freezingAmount;
	}

	public void setFreezingAmount(String freezingAmount) {
		this.freezingAmount = freezingAmount;
	}

	public String getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(String totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public String getRegularAmount() {
		return regularAmount;
	}

	public void setRegularAmount(String regularAmount) {
		this.regularAmount = regularAmount;
	}

	public String getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(String currentAmount) {
		this.currentAmount = currentAmount;
	}

}
