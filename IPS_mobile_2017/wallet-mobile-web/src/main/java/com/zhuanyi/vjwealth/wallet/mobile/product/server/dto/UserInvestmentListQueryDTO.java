package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * 产品的所有投资人记录信息
 * @author wangzf
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class UserInvestmentListQueryDTO {

	
	private String productId;//产品ID
	private String orderId;//订单ID
	private String productCode;//产品编号
	private String productName;//产品名称
	private String investmentAmount;//投资金额
	private String fromInterestTime;//起息时间
	private String expirationDate;//到期时间
	private String amountReceived;//已回
	private String amountReceivable;//应回
	private String annualYield;//年化利率
	private String investmentStatusMarkURL;
	private String investmentStatusIconURL;

	private String amountReceivableLabel;
	private String amountReceivedLabel;
	private String isShowAdvancePaymentDate;
	private String advancePaymentDateLabel;
	private String advancePaymentDate;

	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getInvestmentAmount() {
		return investmentAmount;
	}
	public void setInvestmentAmount(String investmentAmount) {
		this.investmentAmount = investmentAmount;
	}
	public String getFromInterestTime() {
		return fromInterestTime;
	}
	public void setFromInterestTime(String fromInterestTime) {
		this.fromInterestTime = fromInterestTime;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getAmountReceived() {
		return amountReceived;
	}
	public void setAmountReceived(String amountReceived) {
		this.amountReceived = amountReceived;
	}
	public String getAmountReceivable() {
		return amountReceivable;
	}
	public void setAmountReceivable(String amountReceivable) {
		this.amountReceivable = amountReceivable;
	}
	public String getAnnualYield() {
		return annualYield;
	}
	public void setAnnualYield(String annualYield) {
		this.annualYield = annualYield;
	}
	public String getInvestmentStatusMarkURL() {
		return investmentStatusMarkURL;
	}
	public void setInvestmentStatusMarkURL(String investmentStatusMarkURL) {
		this.investmentStatusMarkURL = investmentStatusMarkURL;
	}

	public String getAmountReceivableLabel() {
		return amountReceivableLabel;
	}

	public void setAmountReceivableLabel(String amountReceivableLabel) {
		this.amountReceivableLabel = amountReceivableLabel;
	}

	public String getAmountReceivedLabel() {
		return amountReceivedLabel;
	}

	public void setAmountReceivedLabel(String amountReceivedLabel) {
		this.amountReceivedLabel = amountReceivedLabel;
	}

	public String getIsShowAdvancePaymentDate() {
		return isShowAdvancePaymentDate;
	}

	public void setIsShowAdvancePaymentDate(String isShowAdvancePaymentDate) {
		this.isShowAdvancePaymentDate = isShowAdvancePaymentDate;
	}

	public String getAdvancePaymentDateLabel() {
		return advancePaymentDateLabel;
	}

	public void setAdvancePaymentDateLabel(String advancePaymentDateLabel) {
		this.advancePaymentDateLabel = advancePaymentDateLabel;
	}

	public String getInvestmentStatusIconURL() {
		return investmentStatusIconURL;
	}

	public void setInvestmentStatusIconURL(String investmentStatusIconURL) {
		this.investmentStatusIconURL = investmentStatusIconURL;
	}

	public String getAdvancePaymentDate() {
		return advancePaymentDate;
	}

	public void setAdvancePaymentDate(String advancePaymentDate) {
		this.advancePaymentDate = advancePaymentDate;
	}
}
