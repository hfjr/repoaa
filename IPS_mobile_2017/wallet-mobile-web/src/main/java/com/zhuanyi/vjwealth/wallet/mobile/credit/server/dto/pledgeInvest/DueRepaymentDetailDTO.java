package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;

import java.util.List;
import java.util.Map;



/**
 * Created by wangzhangfei on 16/7/12.
 */
public class DueRepaymentDetailDTO {

	private String noRepaymentCapitalTitle;
	private String noRepaymentCapital;
	private String noSettleLoanDescription;
	private String loanDateDesc;
	private String loanMoney;
	private List<Map<String,String>> loanInfo;
	
	
	public String getLoanDateDesc() {
		return loanDateDesc;
	}
	public void setLoanDateDesc(String loanDateDesc) {
		this.loanDateDesc = loanDateDesc;
	}
	public String getLoanMoney() {
		return loanMoney;
	}
	public void setLoanMoney(String loanMoney) {
		this.loanMoney = loanMoney;
	}
	public List<Map<String, String>> getLoanInfo() {
		return loanInfo;
	}
	public void setLoanInfo(List<Map<String, String>> loanInfo) {
		this.loanInfo = loanInfo;
	}
	public String getNoRepaymentCapitalTitle() {
		return noRepaymentCapitalTitle;
	}
	public void setNoRepaymentCapitalTitle(String noRepaymentCapitalTitle) {
		this.noRepaymentCapitalTitle = noRepaymentCapitalTitle;
	}
	public String getNoRepaymentCapital() {
		return noRepaymentCapital;
	}
	public void setNoRepaymentCapital(String noRepaymentCapital) {
		this.noRepaymentCapital = noRepaymentCapital;
	}
	public String getNoSettleLoanDescription() {
		return noSettleLoanDescription;
	}
	public void setNoSettleLoanDescription(String noSettleLoanDescription) {
		this.noSettleLoanDescription = noSettleLoanDescription;
	}
	
}
