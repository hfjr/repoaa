package com.zhuanyi.vjwealth.wallet.service.mainbiz.phoneMessage.dto;

import java.io.Serializable;

public class MessageServerLogDTO implements Serializable   {
	private static final long serialVersionUID = 1L;

	private String id;
	// 业务类型
	private String type;
	// 手机号
	private String phone;
	// 发送参数
	private String sendData;
	// 请求系统
	private String requestSystem;
	// 短信平台id
	private String messagePlateformId;
	private String errorCode;
	private String errorMessage;
	private String createBy;
	private String createDate;
	private String updateBy;
	private String updateDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSendData() {
		return sendData;
	}
	public void setSendData(String sendData) {
		this.sendData = sendData;
	}
	public String getRequestSystem() {
		return requestSystem;
	}
	public void setRequestSystem(String requestSystem) {
		this.requestSystem = requestSystem;
	}
	public String getMessagePlateformId() {
		return messagePlateformId;
	}
	public void setMessagePlateformId(String messagePlateformId) {
		this.messagePlateformId = messagePlateformId;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
