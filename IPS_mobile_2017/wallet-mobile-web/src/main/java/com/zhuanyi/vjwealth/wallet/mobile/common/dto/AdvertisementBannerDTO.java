package com.zhuanyi.vjwealth.wallet.mobile.common.dto;


public class AdvertisementBannerDTO {


	private String imgUrl;  //图片url(IOS使用.png类型)
	private String webpImgUrl;//图片url(android使用.webp类型)
	private String isClick; //是否可点击
	private String linkUrl; //点击后的链接地址
	private String actionType; //展现方式(redirect_page,alert_tip,open_html,open_security_html)
	private String linkTitle;//标题
	
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
	

}
