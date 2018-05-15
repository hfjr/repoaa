package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto;

import java.io.Serializable;

/**
 * Created by wzf on 2016/10/27.
 */
public class CommonCommitResultDTO implements Serializable{

    private String code;// 202000,
    private String message;// 资料提交成功,
    private String feedbackInformation;// 将于一个工作日内完成审核,
    private String iconURL;//
    private String taskId;
    private String imgBytes;

    public CommonCommitResultDTO() {
    }

    public CommonCommitResultDTO(String code, String message, String feedbackInformation, String iconURL) {
        this.code = code;
        this.message = message;
        this.feedbackInformation = feedbackInformation;
        this.iconURL = iconURL;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFeedbackInformation() {
        return feedbackInformation;
    }

    public void setFeedbackInformation(String feedbackInformation) {
        this.feedbackInformation = feedbackInformation;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getImgBytes() {
		return imgBytes;
	}

	public void setImgBytes(String imgBytes) {
		this.imgBytes = imgBytes;
	}
    
    
}
