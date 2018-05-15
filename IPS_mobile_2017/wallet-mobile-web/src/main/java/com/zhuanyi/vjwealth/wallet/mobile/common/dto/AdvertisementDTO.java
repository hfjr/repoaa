package com.zhuanyi.vjwealth.wallet.mobile.common.dto;






import java.io.Serializable;
import java.util.Map;

public class AdvertisementDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String imgUrl;
	private String isClick;
	private String webpImgUrl;
	private String linkUrl;
	private String type;
	private String actionType;
	private String linkTitle;
	private String content;

	private String markStr;

	private Map<String,String> mark;
	
	
	
	
	public String getWebpImgUrl() {
		return webpImgUrl;
	}
	public void setWebpImgUrl(String webpImgUrl) {
		this.webpImgUrl = webpImgUrl;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getIsClick() {
		return isClick;
	}
	public void setIsClick(String isClick) {
		this.isClick = isClick;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getLinkTitle() {
		return linkTitle;
	}
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getMarkStr() {
		return markStr;
	}

	public void setMarkStr(String markStr) {
		this.markStr = markStr;
	}

	public Map<String, String> getMark() {
		return mark;
	}

	public void setMark(Map<String, String> mark) {
		this.mark = mark;
	}
}
