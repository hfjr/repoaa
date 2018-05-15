package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;

import java.util.List;



/**
 * Created by wangzhangfei on 16/7/12.
 */
public class DueRepaymentInitiDTO {

	private String repaymentTip;
	private String accountBalanceLabel;
	private String accountBalance;
	private String isMore;
	private List<DueRepaymentInitiInnerRecordDTO> records;
	
	public String getRepaymentTip() {
		return repaymentTip;
	}
	public void setRepaymentTip(String repaymentTip) {
		this.repaymentTip = repaymentTip;
	}
	public String getAccountBalanceLabel() {
		return accountBalanceLabel;
	}
	public void setAccountBalanceLabel(String accountBalanceLabel) {
		this.accountBalanceLabel = accountBalanceLabel;
	}
	public String getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getIsMore() {
		return isMore;
	}
	public void setIsMore(String isMore) {
		this.isMore = isMore;
	}
	public List<DueRepaymentInitiInnerRecordDTO> getRecords() {
		return records;
	}
	public void setRecords(List<DueRepaymentInitiInnerRecordDTO> records) {
		this.records = records;
	}
	
}
