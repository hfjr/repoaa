package com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * e账户转余额账单详情
 * @author wangzf
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class EaToMaDetailDTO extends BillDetailCommonDTO{
	
	private String timeLabel;//"充值卡",
	private String time;//"招商银行(尾号5944)"
	
	
	public String getTimeLabel() {
		return timeLabel;
	}
	public void setTimeLabel(String timeLabel) {
		this.timeLabel = timeLabel;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
