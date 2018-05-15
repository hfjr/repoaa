package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest;

import java.util.List;
import java.util.Map;




/**
 * Created by wangzhangfei on 16/7/12.
 */
public class LoanRecordDetailInnerrepaymentInfoDTO {

	private String repayRecordLabel;
	private String repayRecord;
	private String repayTitle;
	private List<Map<String,String>> repaymentDetail;
	public String getRepayRecordLabel() {
		return repayRecordLabel;
	}
	public void setRepayRecordLabel(String repayRecordLabel) {
		this.repayRecordLabel = repayRecordLabel;
	}
	public String getRepayRecord() {
		return repayRecord;
	}
	public void setRepayRecord(String repayRecord) {
		this.repayRecord = repayRecord;
	}
	public String getRepayTitle() {
		return repayTitle;
	}
	public void setRepayTitle(String repayTitle) {
		this.repayTitle = repayTitle;
	}
	public List<Map<String, String>> getRepaymentDetail() {
		return repaymentDetail;
	}
	public void setRepaymentDetail(List<Map<String, String>> repaymentDetail) {
		this.repaymentDetail = repaymentDetail;
	}
	
}
