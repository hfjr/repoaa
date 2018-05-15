package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:理财贷-投资记录详情
 * @author: wangzf
 * @date: 2016-06-08 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class FinancialLoanInvestmentDetailDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private String orderId;//订单编号
	private String investmentStatusDescription;//投资状态
	private String investmentStatusMarkURL;//投资状态的图片地址
	private String investmentPrincipal;//在投本金
	private String interestReceived;//待收利息
	private String dynamicInformation;//投资动态
	private String dynamicTime;//动态时间
	private String insurancePolicyNumberTitle;//"众安保险承保，保障本息到期兑付",
	private String insurancePolicyNumberLabel;//"保障凭证",
	private String insurancePolicyNumber;//保单编号
	private String insurancePolicyNumberShowActionType;//跳转方式
	private String insurancePolicyNumberShowContent;//内容：可以是提示内容，也可以是跳转的链接地址
	private String insurancePolicyNumberShowTitle;
	private FinancialLoanInvestmentProductDetailDTO product; //产品信息
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getInvestmentStatusDescription() {
		return investmentStatusDescription;
	}
	public void setInvestmentStatusDescription(String investmentStatusDescription) {
		this.investmentStatusDescription = investmentStatusDescription;
	}
	public String getInvestmentStatusMarkURL() {
		return investmentStatusMarkURL;
	}
	public void setInvestmentStatusMarkURL(String investmentStatusMarkURL) {
		this.investmentStatusMarkURL = investmentStatusMarkURL;
	}
	public String getInvestmentPrincipal() {
		return investmentPrincipal;
	}
	public void setInvestmentPrincipal(String investmentPrincipal) {
		this.investmentPrincipal = investmentPrincipal;
	}
	public String getInterestReceived() {
		return interestReceived;
	}
	public void setInterestReceived(String interestReceived) {
		this.interestReceived = interestReceived;
	}
	public String getDynamicInformation() {
		return dynamicInformation;
	}
	public void setDynamicInformation(String dynamicInformation) {
		this.dynamicInformation = dynamicInformation;
	}
	public String getDynamicTime() {
		return dynamicTime;
	}
	public void setDynamicTime(String dynamicTime) {
		this.dynamicTime = dynamicTime;
	}
	public String getInsurancePolicyNumberTitle() {
		return insurancePolicyNumberTitle;
	}
	public void setInsurancePolicyNumberTitle(String insurancePolicyNumberTitle) {
		this.insurancePolicyNumberTitle = insurancePolicyNumberTitle;
	}
	public String getInsurancePolicyNumberLabel() {
		return insurancePolicyNumberLabel;
	}
	public void setInsurancePolicyNumberLabel(String insurancePolicyNumberLabel) {
		this.insurancePolicyNumberLabel = insurancePolicyNumberLabel;
	}
	public String getInsurancePolicyNumber() {
		return insurancePolicyNumber;
	}
	public void setInsurancePolicyNumber(String insurancePolicyNumber) {
		this.insurancePolicyNumber = insurancePolicyNumber;
	}
	public String getInsurancePolicyNumberShowActionType() {
		return insurancePolicyNumberShowActionType;
	}
	public void setInsurancePolicyNumberShowActionType(String insurancePolicyNumberShowActionType) {
		this.insurancePolicyNumberShowActionType = insurancePolicyNumberShowActionType;
	}
	public String getInsurancePolicyNumberShowContent() {
		return insurancePolicyNumberShowContent;
	}
	public void setInsurancePolicyNumberShowContent(String insurancePolicyNumberShowContent) {
		this.insurancePolicyNumberShowContent = insurancePolicyNumberShowContent;
	}
	public String getInsurancePolicyNumberShowTitle() {
		return insurancePolicyNumberShowTitle;
	}
	public void setInsurancePolicyNumberShowTitle(String insurancePolicyNumberShowTitle) {
		this.insurancePolicyNumberShowTitle = insurancePolicyNumberShowTitle;
	}
	public FinancialLoanInvestmentProductDetailDTO getProduct() {
		return product;
	}
	public void setProduct(FinancialLoanInvestmentProductDetailDTO product) {
		this.product = product;
	}
    
}
