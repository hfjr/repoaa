package com.zhuanyi.vjwealth.wallet.mobile.common.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


public class AppVersionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String version; // 版本

	private String url; // 更新url

	private String content;// 更新内容

	private String isUpdate;// 是否更新(S^0:否,1:是$)

	private String isForceUpdate; // 是否强制更新(S^0:否,1:是$)

	// 不更新
	public final static String ACTION_CHECKRESOURCEVERSION_00 = "Action_CheckResourceVersion_00";
	// 更新
	public final static String ACTION_CHECKRESOURCEVERSION_01 = "Action_CheckResourceVersion_01";
	// 强制
	public final static String ACTION_CHECKRESOURCEVERSION_02 = "Action_CheckResourceVersion_02";

	// 获取更新类型
	public String getActionType(String newestUrl, String newestContent) {
		
		//当前版本不存在,需要强制更新
		if(StringUtils.isBlank(this.isUpdate)||StringUtils.isBlank(this.isForceUpdate)){
			this.url = newestUrl;
			this.content = newestContent;
			return ACTION_CHECKRESOURCEVERSION_02;
		}
			
		
		// 强制更新
		if (this.isForceUpdate.equals("1")) {
			this.url = newestUrl;
			this.content = newestContent;
			return ACTION_CHECKRESOURCEVERSION_02;
		}
				
		// 更新
		if (this.isUpdate.equals("1")) {
			this.url = newestUrl;
			this.content = newestContent;
			return ACTION_CHECKRESOURCEVERSION_01;
		}
		
		// 不更新
		return ACTION_CHECKRESOURCEVERSION_00;
	}

	// 获取更新内容
	public Map<String, String> getUpdateContent() {
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("url", this.url==null?"":this.url);
		returnMap.put("content", this.content==null?"":this.content);
		return returnMap;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	public java.lang.String getIsForceUpdate() {
		return isForceUpdate;
	}

	public void setIsForceUpdate(java.lang.String isForceUpdate) {
		this.isForceUpdate = isForceUpdate;
	}

}
