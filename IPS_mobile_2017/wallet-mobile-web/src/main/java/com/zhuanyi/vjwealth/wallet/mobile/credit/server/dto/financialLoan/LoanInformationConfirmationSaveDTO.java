package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by hexy on 16/6/12.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class LoanInformationConfirmationSaveDTO {

    private String code;//返回状态码
    private String message;//返回消息
    private String feedbackInformation;//
    private String iconURL;//图标

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



}
