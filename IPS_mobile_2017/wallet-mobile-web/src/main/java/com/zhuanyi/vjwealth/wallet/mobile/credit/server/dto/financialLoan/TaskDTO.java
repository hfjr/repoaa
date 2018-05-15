package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:任务列表
 * @author: wangzf
 * @date: 2016-06-08 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class TaskDTO {
	
	private String taskCode;//任务编号
	private String taskName;//任务名称
	private String taskDescription;//任务说明
	private String taskIcon;//任务图标
	private String taskPrize;//任务奖品（金额等）
	private String taskState;//任务状态 （完成：completion，未完成:notCompletion，完成中：completeMedium，完成失败: completeFail）
	private String failInformation;//任务失败提示信息
	private String isRepeatTask;//任务是否可以重复
	private String taskCompletionTimes;//任务已完成次数
	private String taskCompletionMarkURL;//任务完成图标URL(大，小图标)
	
	private String taskSchedule;//任务进度
	private String taskScheduleDescription;//任务描述
	
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public String getTaskIcon() {
		return taskIcon;
	}
	public void setTaskIcon(String taskIcon) {
		this.taskIcon = taskIcon;
	}
	public String getTaskPrize() {
		return taskPrize;
	}
	public void setTaskPrize(String taskPrize) {
		this.taskPrize = taskPrize;
	}
	public String getTaskState() {
		return taskState;
	}
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	public String getFailInformation() {
		return failInformation;
	}
	public void setFailInformation(String failInformation) {
		this.failInformation = failInformation;
	}
	public String getIsRepeatTask() {
		return isRepeatTask;
	}
	public void setIsRepeatTask(String isRepeatTask) {
		this.isRepeatTask = isRepeatTask;
	}
	public String getTaskCompletionTimes() {
		return taskCompletionTimes;
	}
	public void setTaskCompletionTimes(String taskCompletionTimes) {
		this.taskCompletionTimes = taskCompletionTimes;
	}
	public String getTaskCompletionMarkURL() {
		return taskCompletionMarkURL;
	}
	public void setTaskCompletionMarkURL(String taskCompletionMarkURL) {
		this.taskCompletionMarkURL = taskCompletionMarkURL;
	}
	public String getTaskSchedule() {
		return taskSchedule;
	}
	public void setTaskSchedule(String taskSchedule) {
		this.taskSchedule = taskSchedule;
	}
	public String getTaskScheduleDescription() {
		return taskScheduleDescription;
	}
	public void setTaskScheduleDescription(String taskScheduleDescription) {
		this.taskScheduleDescription = taskScheduleDescription;
	}
	
	
}
