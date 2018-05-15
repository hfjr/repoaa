package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:任务详情
 * @author: wangzf
 * @date: 2016-06-08 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class TaskDetailDTO {
	
	
	private String cityLabel;//社保公积金所在地Label
	private String cityName;//社保公积金所在地
	private String cityCode;//社保公积金所在地编号
//	private List<Map<String,String>> citySelection;//社保公积金所在地项(beijing,shanghai,shenzhen, guangzhou)
	private String fundAccountQueryTitle;//公积金账号标题
	private String fundAccountLabel;//公积金账号Label
	private String fundAccount;//公积金账号
	private String fundAccountPasswordLabel;//公积金账号密码Label
	private String fundAccountPassword;//公积金账号密码
	private String socialSecurityAccountQueryTitle;//社保帐号标题
	
	private String socialSecurityAccountLabel;//社保帐号Label
	private String socialSecurityAccount;//社保帐号
	private String socialSecurityAccountPasswordLabel;//社保帐号密码Label
	private String socialSecurityAccountPassword;//社保帐号查询密码
	private String helpLabel;//公积金&社保帮助Label
	private String helpMessage;//公积金&社保帮助提示
	
	private String fundAccountInputTip;
	private String fundAccountPasswordInputTip;
	private String socialSecurityAccountInputTip;
	private String socialSecurityAccountPasswordInputTip;
	private Object fundAccountTipInfo;
	private JSONArray fundAccountHelpInfo;
	private Object socialSecurityAccountTipInfo;
	private JSONArray socialSecurityAccountHelpInfo;
	
	public String getCityLabel() {
		return cityLabel;
	}
	public void setCityLabel(String cityLabel) {
		this.cityLabel = cityLabel;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getFundAccountQueryTitle() {
		return fundAccountQueryTitle;
	}

	public void setFundAccountQueryTitle(String fundAccountQueryTitle) {
		this.fundAccountQueryTitle = fundAccountQueryTitle;
	}

	public String getFundAccountLabel() {
		return fundAccountLabel;
	}
	public void setFundAccountLabel(String fundAccountLabel) {
		this.fundAccountLabel = fundAccountLabel;
	}
	public String getFundAccount() {
		return fundAccount;
	}
	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}
	public String getFundAccountPasswordLabel() {
		return fundAccountPasswordLabel;
	}
	public void setFundAccountPasswordLabel(String fundAccountPasswordLabel) {
		this.fundAccountPasswordLabel = fundAccountPasswordLabel;
	}
	public String getFundAccountPassword() {
		return fundAccountPassword;
	}
	public void setFundAccountPassword(String fundAccountPassword) {
		this.fundAccountPassword = fundAccountPassword;
	}

	public String getSocialSecurityAccountQueryTitle() {
		return socialSecurityAccountQueryTitle;
	}

	public void setSocialSecurityAccountQueryTitle(String socialSecurityAccountQueryTitle) {
		this.socialSecurityAccountQueryTitle = socialSecurityAccountQueryTitle;
	}

	public String getSocialSecurityAccountLabel() {
		return socialSecurityAccountLabel;
	}
	public void setSocialSecurityAccountLabel(String socialSecurityAccountLabel) {
		this.socialSecurityAccountLabel = socialSecurityAccountLabel;
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
	public String getSocialSecurityAccountPassword() {
		return socialSecurityAccountPassword;
	}
	public void setSocialSecurityAccountPassword(String socialSecurityAccountPassword) {
		this.socialSecurityAccountPassword = socialSecurityAccountPassword;
	}
	public String getHelpLabel() {
		return helpLabel;
	}
	public void setHelpLabel(String helpLabel) {
		this.helpLabel = helpLabel;
	}
	public String getHelpMessage() {
		return helpMessage;
	}
	public void setHelpMessage(String helpMessage) {
		this.helpMessage = helpMessage;
	}
	public String getFundAccountInputTip() {
		return fundAccountInputTip;
	}
	public void setFundAccountInputTip(String fundAccountInputTip) {
		this.fundAccountInputTip = fundAccountInputTip;
	}
	public String getFundAccountPasswordInputTip() {
		return fundAccountPasswordInputTip;
	}
	public void setFundAccountPasswordInputTip(String fundAccountPasswordInputTip) {
		this.fundAccountPasswordInputTip = fundAccountPasswordInputTip;
	}
	public String getSocialSecurityAccountInputTip() {
		return socialSecurityAccountInputTip;
	}
	public void setSocialSecurityAccountInputTip(String socialSecurityAccountInputTip) {
		this.socialSecurityAccountInputTip = socialSecurityAccountInputTip;
	}
	public String getSocialSecurityAccountPasswordInputTip() {
		return socialSecurityAccountPasswordInputTip;
	}
	public void setSocialSecurityAccountPasswordInputTip(String socialSecurityAccountPasswordInputTip) {
		this.socialSecurityAccountPasswordInputTip = socialSecurityAccountPasswordInputTip;
	}
	public Object getFundAccountTipInfo() {
		return fundAccountTipInfo;
	}
	public void setFundAccountTipInfo(Object fundAccountTipInfo) {
		this.fundAccountTipInfo = fundAccountTipInfo;
	}
	public JSONArray getFundAccountHelpInfo() {
		return fundAccountHelpInfo;
	}
	public void setFundAccountHelpInfo(JSONArray fundAccountHelpInfo) {
		this.fundAccountHelpInfo = fundAccountHelpInfo;
	}
	public Object getSocialSecurityAccountTipInfo() {
		return socialSecurityAccountTipInfo;
	}
	public void setSocialSecurityAccountTipInfo(Object socialSecurityAccountTipInfo) {
		this.socialSecurityAccountTipInfo = socialSecurityAccountTipInfo;
	}
	public JSONArray getSocialSecurityAccountHelpInfo() {
		return socialSecurityAccountHelpInfo;
	}
	public void setSocialSecurityAccountHelpInfo(
			JSONArray socialSecurityAccountHelpInfo) {
		this.socialSecurityAccountHelpInfo = socialSecurityAccountHelpInfo;
	}
	
	
}
