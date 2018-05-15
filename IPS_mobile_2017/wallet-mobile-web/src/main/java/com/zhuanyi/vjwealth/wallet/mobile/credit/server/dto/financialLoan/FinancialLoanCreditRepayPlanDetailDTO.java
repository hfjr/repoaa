package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:理财贷-还款计划详情
 * @author: wangzf
 * @date: 2016-06-08 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class FinancialLoanCreditRepayPlanDetailDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@JsonIgnore
	private String planTime;
	@JsonIgnore
	private String isRepay;
	
    private String repaymentDate;//还款日期
    private String repaymentAmount;//还款金额
    private String repaymentStatusMarkURL;//状态图标
    private String isRepaymentComplete;//是否完成还款
    
	public String getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(String repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public String getRepaymentAmount() {
		return repaymentAmount;
	}
	public void setRepaymentAmount(String repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}
	public String getRepaymentStatusMarkURL() {
		return repaymentStatusMarkURL;
	}
	public void setRepaymentStatusMarkURL(String repaymentStatusMarkURL) {
		this.repaymentStatusMarkURL = repaymentStatusMarkURL;
	}
	public String getIsRepaymentComplete() {
		return isRepaymentComplete;
	}
	public void setIsRepaymentComplete(String isRepaymentComplete) {
		this.isRepaymentComplete = isRepaymentComplete;
	}
	public String getPlanTime() {
		return planTime;
	}
	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}
	public String getIsRepay() {
		return isRepay;
	}
	public void setIsRepay(String isRepay) {
		this.isRepay = isRepay;
	}
    
}
