package com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto;

import java.util.List;
import java.util.Map;

/**
 * 有餘的凍結账单详情
 * @author wangzf
 */

public class VFinacialFrozenDetailInnerDTO {
	
	private String principal;
	private String interest;
	private String orderNo;
	private String totalAmount;
	private String productCode;
	private String repaymentType;
	private String investmentPeriods;

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getInvestmentPeriods() {
		return investmentPeriods;
	}

	public void setInvestmentPeriods(String investmentPeriods) {
		this.investmentPeriods = investmentPeriods;
	}
}
