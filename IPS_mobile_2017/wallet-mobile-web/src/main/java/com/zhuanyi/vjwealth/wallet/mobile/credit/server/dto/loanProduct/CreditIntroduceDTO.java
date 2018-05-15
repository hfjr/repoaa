package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct;

import java.io.Serializable;

/**
 *
 * Created by hexy on 16/8/26.
 */
public class CreditIntroduceDTO implements Serializable {

    private String introducePictureURL;
    private String introducePictureWebpURL;
    private String  buttonTextMessage;

    private String protocolAgreenmentTip;//提示
    private String protocolDesc;//协议描述
    private String protocolUrl;// 协议地址
    private String protocolUrlTitle;//协议页标题


    public String getIntroducePictureWebpURL() {
        return introducePictureWebpURL;
    }

    public void setIntroducePictureWebpURL(String introducePictureWebpURL) {
        this.introducePictureWebpURL = introducePictureWebpURL;
    }

    public String getIntroducePictureURL() {
        return introducePictureURL;
    }

    public void setIntroducePictureURL(String introducePictureURL) {
        this.introducePictureURL = introducePictureURL;
    }

    public String getButtonTextMessage() {
        return buttonTextMessage;
    }

    public void setButtonTextMessage(String buttonTextMessage) {
        this.buttonTextMessage = buttonTextMessage;
    }

    public String getProtocolAgreenmentTip() {
        return protocolAgreenmentTip;
    }

    public void setProtocolAgreenmentTip(String protocolAgreenmentTip) {
        this.protocolAgreenmentTip = protocolAgreenmentTip;
    }

    public String getProtocolDesc() {
        return protocolDesc;
    }

    public void setProtocolDesc(String protocolDesc) {
        this.protocolDesc = protocolDesc;
    }

    public String getProtocolUrl() {
        return protocolUrl;
    }

    public void setProtocolUrl(String protocolUrl) {
        this.protocolUrl = protocolUrl;
    }

    public String getProtocolUrlTitle() {
        return protocolUrlTitle;
    }

    public void setProtocolUrlTitle(String protocolUrlTitle) {
        this.protocolUrlTitle = protocolUrlTitle;
    }
}
