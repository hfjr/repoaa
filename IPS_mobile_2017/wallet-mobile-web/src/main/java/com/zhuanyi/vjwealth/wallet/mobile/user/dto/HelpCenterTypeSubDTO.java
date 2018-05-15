package com.zhuanyi.vjwealth.wallet.mobile.user.dto;

import java.io.Serializable;

public class HelpCenterTypeSubDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String typeSubId;//条目ID
	private String typeSubName;//条目名称
	
	public String getTypeSubId() {
		return typeSubId;
	}
	public void setTypeSubId(String typeSubId) {
		this.typeSubId = typeSubId;
	}
	public String getTypeSubName() {
		return typeSubName;
	}
	public void setTypeSubName(String typeSubName) {
		this.typeSubName = typeSubName;
	}

}
