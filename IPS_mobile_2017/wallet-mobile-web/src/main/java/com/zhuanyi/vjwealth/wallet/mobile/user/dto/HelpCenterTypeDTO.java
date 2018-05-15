package com.zhuanyi.vjwealth.wallet.mobile.user.dto;

import java.io.Serializable;
import java.util.List;

public class HelpCenterTypeDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String typeIconUrl;//类别图标
	private String typeName;//条目名称
	private String typeCode;//条目编码
	private List<HelpCenterTypeSubDTO> typeSubList;//条目明细

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeIconUrl() {
		return typeIconUrl;
	}
	public void setTypeIconUrl(String typeIconUrl) {
		this.typeIconUrl = typeIconUrl;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public List<HelpCenterTypeSubDTO> getTypeSubList() {
		return typeSubList;
	}
	public void setTypeSubList(List<HelpCenterTypeSubDTO> typeSubList) {
		this.typeSubList = typeSubList;
	}
	
	
	
}
