package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by hexy on 16/6/7.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class FinancialLoanInitiDTO {

    private String availableCreditLabel; // 可用额度Label
    private String availableCredit; //  可用额度
    private String availableCreditStatus; //  可用额度状态(可用：true，不可用：false)
    private String dailyInterestRate; //  日利率
    private String availableCreditDescription; //  可用额度说明
    private String isHaveInvertmentRecord; //  是否有投资记录
    private String isMore = "false";//是否有更多记录
    
    private List<TaskDTO> taskList ; // 任务列表（参考任务列表）
    private  List<FinancialLoanInitiInnerProductDTO> records;//定向理财产品列表
    
	public String getAvailableCreditLabel() {
		return availableCreditLabel;
	}
	public void setAvailableCreditLabel(String availableCreditLabel) {
		this.availableCreditLabel = availableCreditLabel;
	}
	public String getAvailableCredit() {
		return availableCredit;
	}
	public void setAvailableCredit(String availableCredit) {
		this.availableCredit = availableCredit;
	}
	public String getAvailableCreditStatus() {
		return availableCreditStatus;
	}
	public void setAvailableCreditStatus(String availableCreditStatus) {
		this.availableCreditStatus = availableCreditStatus;
	}
	public String getDailyInterestRate() {
		return dailyInterestRate;
	}
	public void setDailyInterestRate(String dailyInterestRate) {
		this.dailyInterestRate = dailyInterestRate;
	}
	public String getAvailableCreditDescription() {
		return availableCreditDescription;
	}
	public void setAvailableCreditDescription(String availableCreditDescription) {
		this.availableCreditDescription = availableCreditDescription;
	}
	public String getIsHaveInvertmentRecord() {
		return isHaveInvertmentRecord;
	}
	public void setIsHaveInvertmentRecord(String isHaveInvertmentRecord) {
		this.isHaveInvertmentRecord = isHaveInvertmentRecord;
	}
	public String getIsMore() {
		return isMore;
	}
	public void setIsMore(String isMore) {
		this.isMore = isMore;
	}
	public List<TaskDTO> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<TaskDTO> taskList) {
		this.taskList = taskList;
	}
	public List<FinancialLoanInitiInnerProductDTO> getRecords() {
		return records;
	}
	public void setRecords(List<FinancialLoanInitiInnerProductDTO> records) {
		this.records = records;
	}
    
}
