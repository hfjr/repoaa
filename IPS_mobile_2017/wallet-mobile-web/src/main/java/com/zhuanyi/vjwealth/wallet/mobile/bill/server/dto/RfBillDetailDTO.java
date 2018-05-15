package com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 余额申购v+账单详情
 * @author wangzf
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class RfBillDetailDTO extends BillDetailCommonDTO{
	
	private String createDate;//账单日期
	private String orderStatus;//账单状态
	private String tip;//提示
	private String subTip;//提示2
	private String tipPhone;//客服热线
	List<Map<String,String>> operateStateInfos;//操作流程
	
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
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getSubTip() {
		return subTip;
	}
	public void setSubTip(String subTip) {
		this.subTip = subTip;
	}
	public String getTipPhone() {
		return tipPhone;
	}
	public void setTipPhone(String tipPhone) {
		this.tipPhone = tipPhone;
	}

}
