package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:理财贷-投资记录动态信息
 * @author: wangzf
 * @date: 2016-06-08 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class FinancialLoanInvestmentNewFlows implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String productCode; //产品名+产品编号
	private String insurancePolicyNumber;//保单号
	private List<FinancialLoanInvestmentNewestActivityDTO> dynamicList;//动态列表
	
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
	public List<FinancialLoanInvestmentNewestActivityDTO> getDynamicList() {
		return dynamicList;
	}
	public void setDynamicList(List<FinancialLoanInvestmentNewestActivityDTO> dynamicList) {
		this.dynamicList = dynamicList;
	}
	
}
