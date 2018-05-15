package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 工资单列表
 * @author wangzhangfei
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class SalaryBillListQueryDTO {
	
	private String payRollId;
	private String periodName;
	
	public String getPayRollId() {
		return payRollId;
	}
	public void setPayRollId(String payRollId) {
		this.payRollId = payRollId;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	
	

}
