package com.zhuanyi.vjwealth.wallet.mobile.common.dto;

import java.util.Date;


public class LogHttpRequestDTO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String interfaceType;
	private String interfaceName;
	private String interfacePath;
	private String reqContent;
	private String resContent;
	private Date logTime;
	private String userId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getInterfacePath() {
		return interfacePath;
	}
	public void setInterfacePath(String interfacePath) {
		this.interfacePath = interfacePath;
	}
	public String getReqContent() {
		return reqContent;
	}
	public void setReqContent(String reqContent) {
		this.reqContent = reqContent;
	}
	public String getResContent() {
		return resContent;
	}
	public void setResContent(String resContent) {
		this.resContent = resContent;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
