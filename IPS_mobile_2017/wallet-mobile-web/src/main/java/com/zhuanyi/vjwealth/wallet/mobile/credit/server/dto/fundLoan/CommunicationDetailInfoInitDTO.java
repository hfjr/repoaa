package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

/**
 * Created by wzf on 2016/11/2.
 */
public class CommunicationDetailInfoInitDTO {

    private String phoneLabel;
    private String phone;
    private String messageCodeLabel;
    private String pictureCodeLabel;
    private String pictureCodeUrl;
    private String warmTip;
    private String tipContent;
    private String waitTime;

    public String getPhoneLabel() {
        return phoneLabel;
    }

    public void setPhoneLabel(String phoneLabel) {
        this.phoneLabel = phoneLabel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessageCodeLabel() {
        return messageCodeLabel;
    }

    public void setMessageCodeLabel(String messageCodeLabel) {
        this.messageCodeLabel = messageCodeLabel;
    }

    public String getPictureCodeLabel() {
        return pictureCodeLabel;
    }

    public void setPictureCodeLabel(String pictureCodeLabel) {
        this.pictureCodeLabel = pictureCodeLabel;
    }

    public String getPictureCodeUrl() {
        return pictureCodeUrl;
    }

    public void setPictureCodeUrl(String pictureCodeUrl) {
        this.pictureCodeUrl = pictureCodeUrl;
    }

    public String getWarmTip() {
        return warmTip;
    }

    public void setWarmTip(String warmTip) {
        this.warmTip = warmTip;
    }

    public String getTipContent() {
        return tipContent;
    }

    public void setTipContent(String tipContent) {
        this.tipContent = tipContent;
    }

    public String getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
    }
}
