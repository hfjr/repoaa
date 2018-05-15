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
public class WjSystemMessageDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id; //id
	private String messageNo; //消息编号
	private String serviceType; //业务类型:
	private String editType; //编辑类型:TEXT,URL
	private String title; //消息标题
	private String content; //内容
	private String channelType; //渠道:app,wx
	private String contentUrl; //内容URL
	private String status; //状态:draft(草稿),release(发布),revoke(撤销)
	private String releaseType; //发布类型:manual(手动),auto(自动)
	private String revokeType; //撤销类型:manual(手动),auto(自动)
	private String releasePerson; //发布人
	private String releaseTime; //发布时间
	private String revokePerson; //撤销人
	
	private String revokeTime; //撤销时间
	private String interval; //提醒时间间隔
	private String startTime; //有效开始时间
	private String endTime; //有效结束时间
	private String priority; //权重值:值越大越优先
	
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
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getEditType() {
		return editType;
	}
	public void setEditType(String editType) {
		this.editType = editType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getContentUrl() {
		return contentUrl;
	}
	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReleaseType() {
		return releaseType;
	}
	public void setReleaseType(String releaseType) {
		this.releaseType = releaseType;
	}
	public String getRevokeType() {
		return revokeType;
	}
	public void setRevokeType(String revokeType) {
		this.revokeType = revokeType;
	}
	public String getReleasePerson() {
		return releasePerson;
	}
	public void setReleasePerson(String releasePerson) {
		this.releasePerson = releasePerson;
	}
	public String getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	public String getRevokePerson() {
		return revokePerson;
	}
	public void setRevokePerson(String revokePerson) {
		this.revokePerson = revokePerson;
	}
	public String getRevokeTime() {
		return revokeTime;
	}
	public void setRevokeTime(String revokeTime) {
		this.revokeTime = revokeTime;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
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
	
	
	
	
}
