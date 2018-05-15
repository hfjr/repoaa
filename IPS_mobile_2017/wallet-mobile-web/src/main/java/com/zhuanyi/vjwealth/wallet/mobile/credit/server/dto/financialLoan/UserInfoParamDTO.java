package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc: 请求信贷系统时的入参
 * @author: wangzf
 * @date: 2016-06-08 
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class UserInfoParamDTO {
	
	private String clientId;
	private String clientName;
	private String indentityNo;
	private String phone;
	private String sex;
	private String bindCardStatus;
	private String bertificationStatus;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getIndentityNo() {
		return indentityNo;
	}

	public void setIndentityNo(String indentityNo) {
		this.indentityNo = indentityNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBindCardStatus() {
		return bindCardStatus;
	}

	public void setBindCardStatus(String bindCardStatus) {
		this.bindCardStatus = bindCardStatus;
	}

	public String getBertificationStatus() {
		return bertificationStatus;
	}

	public void setBertificationStatus(String bertificationStatus) {
		this.bertificationStatus = bertificationStatus;
	}
	
}
