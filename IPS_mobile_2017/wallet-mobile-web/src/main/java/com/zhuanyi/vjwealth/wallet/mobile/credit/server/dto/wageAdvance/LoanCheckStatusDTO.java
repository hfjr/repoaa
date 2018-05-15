package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.wageAdvance;

import java.io.Serializable;

/**
 * Created by wangzf on 16/8/26.
 */
public class LoanCheckStatusDTO {

    private String  code;//状态code
    private String  message ;//提示信息
    private String  feedbackInformation ;//提示
    private String  borrowAmountLabel ;//借款额度
    private String  borrowAmount ;//借款额度
    private String  buttonTextMessage ;//按钮信息
    private String  iconURL ;//icon url

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

    public String getBorrowAmountLabel() {
        return borrowAmountLabel;
    }

    public void setBorrowAmountLabel(String borrowAmountLabel) {
        this.borrowAmountLabel = borrowAmountLabel;
    }

    public String getBorrowAmount() {
        return borrowAmount;
    }

    public void setBorrowAmount(String borrowAmount) {
        this.borrowAmount = borrowAmount;
    }

    public String getButtonTextMessage() {
        return buttonTextMessage;
    }

    public void setButtonTextMessage(String buttonTextMessage) {
        this.buttonTextMessage = buttonTextMessage;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }
}
