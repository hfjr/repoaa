package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:任务提交
 * @author: wangzf
 * @date: 2016-06-08 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class TaskCommitDTO {
	
	private String userId;//用户编号
	private String taskCode;//任务编号
	private String cityCode;//社保公积金所在地编号
	private String fundAccount;//公积金账号
	private String fundAccountPassword;//公积金账号密码
	private String socialSecurityAccount;//社保帐号
	private String socialSecurityAccountPasswordLabel;//社保帐号密码Label
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getFundAccount() {
		return fundAccount;
	}
	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}
	public String getFundAccountPassword() {
		return fundAccountPassword;
	}
	public void setFundAccountPassword(String fundAccountPassword) {
		this.fundAccountPassword = fundAccountPassword;
	}
	public String getSocialSecurityAccount() {
		return socialSecurityAccount;
	}
	public void setSocialSecurityAccount(String socialSecurityAccount) {
		this.socialSecurityAccount = socialSecurityAccount;
	}
	public String getSocialSecurityAccountPasswordLabel() {
		return socialSecurityAccountPasswordLabel;
	}
	public void setSocialSecurityAccountPasswordLabel(String socialSecurityAccountPasswordLabel) {
		this.socialSecurityAccountPasswordLabel = socialSecurityAccountPasswordLabel;
	}
	
}
