package com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto;

import java.util.List;
import java.util.Map;

/**
 * 有餘的凍結账单详情
 * @author wangzf
 */

public class VFinacialFrozenDetailDTO {
	private String pageTitle;
	private String amoutTitle;//标题
	private String amount;//金额
	private String subTitle;//副标题
	private String subContent;//内容

	private String finacialTitle;//投資的標題
	private List<Map<String,String>> finacialInfo;//详情

	private String loanTitle;//借款的標題
	private List<Map<String,String>> loanInfo;//详情

	public String getAmoutTitle() {
		return amoutTitle;
	}

	public void setAmoutTitle(String amoutTitle) {
		this.amoutTitle = amoutTitle;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getSubContent() {
		return subContent;
	}

	public void setSubContent(String subContent) {
		this.subContent = subContent;
	}

	public String getFinacialTitle() {
		return finacialTitle;
	}

	public void setFinacialTitle(String finacialTitle) {
		this.finacialTitle = finacialTitle;
	}

	public List<Map<String, String>> getFinacialInfo() {
		return finacialInfo;
	}

	public void setFinacialInfo(List<Map<String, String>> finacialInfo) {
		this.finacialInfo = finacialInfo;
	}

	public String getLoanTitle() {
		return loanTitle;
	}

	public void setLoanTitle(String loanTitle) {
		this.loanTitle = loanTitle;
	}

	public List<Map<String, String>> getLoanInfo() {
		return loanInfo;
	}

	public void setLoanInfo(List<Map<String, String>> loanInfo) {
		this.loanInfo = loanInfo;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
}
