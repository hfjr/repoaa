package com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 账单详情的共用DTO
 * @author wangzf
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class BillDetailCommonDTO {
	
	private String billAmountLabel;//"金额"
	private String billAmount;//"100.00"
	private String billTypeLabel;//"类型"
	private String billType;//"2016年1月工资"
	
	public String getBillAmountLabel() {
		return billAmountLabel;
	}
	public void setBillAmountLabel(String billAmountLabel) {
		this.billAmountLabel = billAmountLabel;
	}
	public String getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(String billAmount) {
		this.billAmount = billAmount;
	}
	public String getBillTypeLabel() {
		return billTypeLabel;
	}
	public void setBillTypeLabel(String billTypeLabel) {
		this.billTypeLabel = billTypeLabel;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}

}
