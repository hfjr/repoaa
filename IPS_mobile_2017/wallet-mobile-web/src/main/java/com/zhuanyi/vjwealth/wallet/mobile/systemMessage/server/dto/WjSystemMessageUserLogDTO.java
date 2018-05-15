package com.zhuanyi.vjwealth.wallet.mobile.systemMessage.server.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 系统消息
 * @author ce
 * @since 3.2
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class WjSystemMessageUserLogDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id; //id
	private String messageNo; //消息编号
	private String userId; 
	private String readType; //阅读类型:read,unread
	private String serviceType;//消息类型:maintenance:升级维护,activity:活动推广,advertisement:广告
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
	public String getMessageNo() {
		return messageNo;
	}
	public void setMessageNo(String messageNo) {
		this.messageNo = messageNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReadType() {
		return readType;
	}
	public void setReadType(String readType) {
		this.readType = readType;
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
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	
	
	
}
