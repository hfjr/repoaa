package com.zhuanyi.vjwealth.wallet.mobile.account.server.dto;

/**
 * 确认份额 dto
 * 
 * @author xuejie
 *
 */
public class ConfirmShareModelDTO {
	private String confirmShareDetailTime; // 153000 表示 15:30:00

	private String workingDayFlag; // "Y" / "N"

	public String getConfirmShareDetailTime() {
		return confirmShareDetailTime;
	}

	public void setConfirmShareDetailTime(String confirmShareDetailTime) {
		this.confirmShareDetailTime = confirmShareDetailTime;
	}

	public String getWorkingDayFlag() {
		return workingDayFlag;
	}

	public void setWorkingDayFlag(String workingDayFlag) {
		this.workingDayFlag = workingDayFlag;
	}
	
}
