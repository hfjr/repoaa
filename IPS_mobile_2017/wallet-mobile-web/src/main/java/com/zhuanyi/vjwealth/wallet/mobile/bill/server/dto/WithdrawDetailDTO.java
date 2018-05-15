package com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 余额/e账户提现账单详情
 * @author wangzf
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class WithdrawDetailDTO extends BillDetailCommonDTO{
	
	
	private String withdrawLabel;//"充值卡",
	private String withdrawCard;//"招商银行(尾号5944)",
	private String bankLogoURL;//银行图标链接
	private String createDate;//账单日期
	private String orderStatus;//账单状态
	
	List<Map<String,String>> operateStateInfos;//操作流程
	
	public String getWithdrawLabel() {
		return withdrawLabel;
	}
	public void setWithdrawLabel(String withdrawLabel) {
		this.withdrawLabel = withdrawLabel;
	}
	public String getWithdrawCard() {
		return withdrawCard;
	}
	public void setWithdrawCard(String withdrawCard) {
		this.withdrawCard = withdrawCard;
	}
	public String getBankLogoURL() {
		return bankLogoURL;
	}
	public void setBankLogoURL(String bankLogoURL) {
		this.bankLogoURL = bankLogoURL;
	}
	public List<Map<String, String>> getOperateStateInfos() {
		return operateStateInfos;
	}
	public void setOperateStateInfos(List<Map<String, String>> operateStateInfos) {
		this.operateStateInfos = operateStateInfos;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

}
