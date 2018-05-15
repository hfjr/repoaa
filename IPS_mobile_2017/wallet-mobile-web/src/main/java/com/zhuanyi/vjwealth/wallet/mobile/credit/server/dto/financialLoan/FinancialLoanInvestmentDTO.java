package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:理财贷-投资记录
 * @author: wangzf
 * @date: 2016-06-08 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class FinancialLoanInvestmentDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
	private String isPreExpire;

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

	public String getIsPreExpire() {
		return isPreExpire;
	}

	public void setIsPreExpire(String isPreExpire) {
		this.isPreExpire = isPreExpire;
	}
}
