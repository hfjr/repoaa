package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 投资动态列表
 * @author wangzf
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class NewestActivityListDTO {
	
	private String productCode; //产品名+产品编号
	private String insurancePolicyNumber;//保单号
	private List<NewestActivityDTO> dynamicList;//动态列表
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getInsurancePolicyNumber() {
		return insurancePolicyNumber;
	}
	public void setInsurancePolicyNumber(String insurancePolicyNumber) {
		this.insurancePolicyNumber = insurancePolicyNumber;
	}
	public List<NewestActivityDTO> getDynamicList() {
		return dynamicList;
	}
	public void setDynamicList(List<NewestActivityDTO> dynamicList) {
		this.dynamicList = dynamicList;
	}

}
