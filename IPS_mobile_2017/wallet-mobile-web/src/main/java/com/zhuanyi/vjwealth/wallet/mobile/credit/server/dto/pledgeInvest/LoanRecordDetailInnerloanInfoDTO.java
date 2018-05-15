package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;

import java.util.List;
import java.util.Map;




/**
 * Created by wangzhangfei on 16/7/12.
 */
public class LoanRecordDetailInnerloanInfoDTO {

	private String contractLabel;
	private String contract;
	private String contractTitle;
	private String contractURL;
	private String loanTitle;
	private List<Map<String,String>> loanDetail;
	public String getContractLabel() {
		return contractLabel;
	}
	public void setContractLabel(String contractLabel) {
		this.contractLabel = contractLabel;
	}
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public String getContractTitle() {
		return contractTitle;
	}
	public void setContractTitle(String contractTitle) {
		this.contractTitle = contractTitle;
	}
	public String getContractURL() {
		return contractURL;
	}
	public void setContractURL(String contractURL) {
		this.contractURL = contractURL;
	}
	public String getLoanTitle() {
		return loanTitle;
	}
	public void setLoanTitle(String loanTitle) {
		this.loanTitle = loanTitle;
	}
	public List<Map<String, String>> getLoanDetail() {
		return loanDetail;
	}
	public void setLoanDetail(List<Map<String, String>> loanDetail) {
		this.loanDetail = loanDetail;
	}
	
}
