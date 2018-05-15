package com.zhuanyi.vjwealth.wallet.mobile.user.dto;

import java.io.Serializable;

public class HelpCenterTypeSubDetailDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String typeIconUrl;//类别图标
	private String typeSubName;//条目名称
	private String typeSubDetail;//条目明细
	
	public String getTypeIconUrl() {
		return typeIconUrl;
	}
	public void setTypeIconUrl(String typeIconUrl) {
		this.typeIconUrl = typeIconUrl;
	}
	public String getTypeSubName() {
		return typeSubName;
	}
	public void setTypeSubName(String typeSubName) {
		this.typeSubName = typeSubName;
	}
	public String getTypeSubDetail() {
		return typeSubDetail;
	}
	public void setTypeSubDetail(String typeSubDetail) {
		this.typeSubDetail = typeSubDetail;
	}

}
