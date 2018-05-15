package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.wageAdvance;

import java.util.List;
import java.util.Map;


/**
 * Created by wangzhangfei on 16/7/12.
 */
public class WageAdvanceDueRepaymentDetailDTO {

	private String dueRepaymentDesc;
	private String keepBalanceEnoughDesc;
	private String repaymentTypeDesc;
	private String repaymentTypeSubDesc;
	private List<RepaymentPlanDTO> repayPlanInfo;


	public String getRepaymentTypeSubDesc() {
		return repaymentTypeSubDesc;
	}

	public void setRepaymentTypeSubDesc(String repaymentTypeSubDesc) {
		this.repaymentTypeSubDesc = repaymentTypeSubDesc;
	}

	public String getDueRepaymentDesc() {
		return dueRepaymentDesc;
	}

	public void setDueRepaymentDesc(String dueRepaymentDesc) {
		this.dueRepaymentDesc = dueRepaymentDesc;
	}

	public String getKeepBalanceEnoughDesc() {
		return keepBalanceEnoughDesc;
	}

	public void setKeepBalanceEnoughDesc(String keepBalanceEnoughDesc) {
		this.keepBalanceEnoughDesc = keepBalanceEnoughDesc;
	}

	public String getRepaymentTypeDesc() {
		return repaymentTypeDesc;
	}

	public void setRepaymentTypeDesc(String repaymentTypeDesc) {
		this.repaymentTypeDesc = repaymentTypeDesc;
	}

	public List<RepaymentPlanDTO> getRepayPlanInfo() {
		return repayPlanInfo;
	}

	public void setRepayPlanInfo(List<RepaymentPlanDTO> repayPlanInfo) {
		this.repayPlanInfo = repayPlanInfo;
	}
}
