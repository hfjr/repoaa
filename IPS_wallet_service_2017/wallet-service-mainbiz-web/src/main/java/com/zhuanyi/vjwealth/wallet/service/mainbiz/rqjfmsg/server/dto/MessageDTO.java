package com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjfmsg.server.dto;


public class MessageDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private String subTitle;
	private String subTitleType;
	private String contentType;
	private String content;
	private String cornerUrl;
	private String messageType;
	
	public MessageDTO(String title, String subTitle, String subTitleType, String contentType, String content, String cornerUrl, String messageType) {
		super();
		this.title = title;
		this.subTitle = subTitle;
		this.subTitleType = subTitleType;
		this.contentType = contentType;
		this.content = content;
		this.cornerUrl = cornerUrl;
		this.messageType = messageType;
	}

	public MessageDTO() {
		// TODO Auto-generated constructor stub
	}	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getSubTitleType() {
		return subTitleType;
	}
	public void setSubTitleType(String subTitleType) {
		this.subTitleType = subTitleType;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCornerUrl() {
		return cornerUrl;
	}
	public void setCornerUrl(String cornerUrl) {
		this.cornerUrl = cornerUrl;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	
	

}
