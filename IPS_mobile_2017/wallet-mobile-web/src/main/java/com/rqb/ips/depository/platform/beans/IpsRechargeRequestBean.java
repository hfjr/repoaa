package com.rqb.ips.depository.platform.beans;

public class IpsRechargeRequestBean extends IpsRechargeBean{
	
	private static final long serialVersionUID = 5569542210375254442L;

	private String userId;
	
	private String uuid;
	
	private String source;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
