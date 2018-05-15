package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * 下订单初始化DTO
 * 
 * @author wangzf
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class OrderInitQueryDTO {
	private String isCanBuy;//是否可购买
	private String information;//提示信息
	private String availableBalance;//账户余额
	private String availableBalancePlaceholderTip;//可用余额（提示+账户余额）
	private String inputBottomTipContents;//必须为100的整数倍|
	private String fromInvestmentAmount;//起投金额
	private String incrementAmount;//递增金额
	private ProductOrderInitQueryDTO product; //产品信息
	private String token; //防止重复提交
	private String isShowInvestmentIip;//是否提示“需要实名认证”的提示
	
	public String getIsCanBuy() {
		return isCanBuy;
	}
	public void setIsCanBuy(String isCanBuy) {
		this.isCanBuy = isCanBuy;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public String getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}
	public String getAvailableBalancePlaceholderTip() {
		return availableBalancePlaceholderTip;
	}
	public void setAvailableBalancePlaceholderTip(String availableBalancePlaceholderTip) {
		this.availableBalancePlaceholderTip = availableBalancePlaceholderTip;
	}
	public String getInputBottomTipContents() {
		return inputBottomTipContents;
	}
	public void setInputBottomTipContents(String inputBottomTipContents) {
		this.inputBottomTipContents = inputBottomTipContents;
	}
	public ProductOrderInitQueryDTO getProduct() {
		return product;
	}
	public void setProduct(ProductOrderInitQueryDTO product) {
		this.product = product;
	}
	public String getFromInvestmentAmount() {
		return fromInvestmentAmount;
	}
	public void setFromInvestmentAmount(String fromInvestmentAmount) {
		this.fromInvestmentAmount = fromInvestmentAmount;
	}
	public String getIncrementAmount() {
		return incrementAmount;
	}
	public void setIncrementAmount(String incrementAmount) {
		this.incrementAmount = incrementAmount;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	public String getIsShowInvestmentIip() {
		return isShowInvestmentIip;
	}
	public void setIsShowInvestmentIip(String isShowInvestmentIip) {
		this.isShowInvestmentIip = isShowInvestmentIip;
	}
	
}
