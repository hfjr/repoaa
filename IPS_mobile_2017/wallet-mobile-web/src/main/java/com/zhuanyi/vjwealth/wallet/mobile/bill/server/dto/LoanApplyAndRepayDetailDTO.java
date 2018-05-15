package com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.Map;

/**
 * 流通宝、锦囊的借款与还款账单详情
 * @author wangzf
 */

public class LoanApplyAndRepayDetailDTO {
	private String pageTitle;//页面标题
	private String amoutTitle;//标题
	private String amount;//金额
	private String subTitle;//副标题
	private String subContent;//内容
	List<Map<String,String>> detailInfo;//详情

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

	public List<Map<String, String>> getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(List<Map<String, String>> detailInfo) {
		this.detailInfo = detailInfo;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
}
