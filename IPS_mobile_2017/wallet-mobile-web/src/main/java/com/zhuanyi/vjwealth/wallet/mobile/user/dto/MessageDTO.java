package com.zhuanyi.vjwealth.wallet.mobile.user.dto;

/**
 * Created by yi on 16/1/29.
 */
public class MessageDTO {

    private String contentType;
    private String messageDate;
    private String title;
    private String content;
    private String cornerUrl;
    private String iconPath;//区别系统消息、个人消息
    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
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

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
}
