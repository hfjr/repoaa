package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto;

public class TradeAccountCardDTO {

	private String tradeAccountCardId;
	private String cardOwer;
	private String userId;
	private String bankCardNo;
	private String asideBankPhone;
	private String bankName;
	private String bankCode;
	private String cardType;
	
	private String phone;

	public String getCardOwer() {
		return cardOwer;
	}

	public void setCardOwer(String cardOwer) {
		this.cardOwer = cardOwer;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getAsideBankPhone() {
		return asideBankPhone;
	}

	public void setAsideBankPhone(String asideBankPhone) {
		this.asideBankPhone = asideBankPhone;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "TradeAccountCardDTO [tradeAccountCardId=" + tradeAccountCardId + ", cardOwer=" + cardOwer + ", userId=" + userId + ", bankCardNo=" + bankCardNo + ", asideBankPhone=" + asideBankPhone + ", bankName=" + bankName + ", bankCode=" + bankCode + ", cardType=" + cardType
				+ "]";
	}

}
