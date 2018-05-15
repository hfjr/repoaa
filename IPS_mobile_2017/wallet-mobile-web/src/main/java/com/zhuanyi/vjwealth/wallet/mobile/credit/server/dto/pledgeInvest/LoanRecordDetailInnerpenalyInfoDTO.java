package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;

import java.util.List;
import java.util.Map;


/**
 * Created by wangzhangfei on 16/7/12.
 */
public class LoanRecordDetailInnerpenalyInfoDTO {

	private String penaltyTitle;//罚息
	private String penalty;
	private String penaltyDayTitle;
	private String penaltyDay;
	private String tip;

	public String getPenaltyTitle() {
		return penaltyTitle;
	}

	public void setPenaltyTitle(String penaltyTitle) {
		this.penaltyTitle = penaltyTitle;
	}

	public String getPenalty() {
		return penalty;
	}

	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}

	public String getPenaltyDayTitle() {
		return penaltyDayTitle;
	}

	public void setPenaltyDayTitle(String penaltyDayTitle) {
		this.penaltyDayTitle = penaltyDayTitle;
	}

	public String getPenaltyDay() {
		return penaltyDay;
	}

	public void setPenaltyDay(String penaltyDay) {
		this.penaltyDay = penaltyDay;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}
}
