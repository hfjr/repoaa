package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.account;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class UserTAccountHomeDTO {
	private String totalAccountAmount;//e账户金额
	private String amountFrozen;//e账户冻结金额
	private String allAreadyReceive;//累计收益
	private String yesterdayReceive;//昨日收益
	private String isSign;//是否是工资的用户（wj_user_channel_ref  中usertype 为wallet或salary_bill）
	private String everyReceiveRate;//万分收益
	private String weekReceiveRate;//七日年化收益
	private String productInfo;//产品信息，包括页面静态的标签等等
    private String taAvailableAmount; //持有金额
    private String maAvailableAmount; //剩余可投
	private String frozenAmount;//冻结金额，取自ma账户

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

	public String getEveryReceiveRate() {
		return everyReceiveRate;
	}
	public void setEveryReceiveRate(String everyReceiveRate) {
		this.everyReceiveRate = everyReceiveRate;
	}


	public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

    public String getTaAvailableAmount() {
        return taAvailableAmount;
    }

    public void setTaAvailableAmount(String taAvailableAmount) {
        this.taAvailableAmount = taAvailableAmount;
    }

    public String getMaAvailableAmount() {
        return maAvailableAmount;
    }

    public void setMaAvailableAmount(String maAvailableAmount) {
        this.maAvailableAmount = maAvailableAmount;
    }

    public String getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(String frozenAmount) {
        this.frozenAmount = frozenAmount;
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

	public String getIsSign() {
		return isSign;
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}

	public String getWeekReceiveRate() {
		return weekReceiveRate;
	}

	public void setWeekReceiveRate(String weekReceiveRate) {
		this.weekReceiveRate = weekReceiveRate;
	}
}
