package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.jd;

import java.util.List;
import java.util.Map;

/**
 * Created by wzf on 2016/12/12.
 */
public class BindCardInitDTO {

    private String userName ;// 用户名
    private String indentityNo ;// 证件号
    private List<Map<String,String>> indentityTypeSelection ;// 证 件类型选择
    private String protocolUrl;
    private String protocolDesc;
    private String protocolTitle;


    private String cardTypeName;// 储蓄卡,
    private String         bankName;// 广发银行,
    private String         bankCode;// 10090
    private String cardNo;
    private String phone;
    private String headTip;
    private String indentityNoShow;//带星号的展示


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIndentityNo() {
        return indentityNo;
    }

    public void setIndentityNo(String indentityNo) {
        this.indentityNo = indentityNo;
    }

    public List<Map<String, String>> getIndentityTypeSelection() {
        return indentityTypeSelection;
    }

    public void setIndentityTypeSelection(List<Map<String, String>> indentityTypeSelection) {
        this.indentityTypeSelection = indentityTypeSelection;
    }

    public String getProtocolUrl() {
        return protocolUrl;
    }

    public void setProtocolUrl(String protocolUrl) {
        this.protocolUrl = protocolUrl;
    }

    public String getProtocolDesc() {
        return protocolDesc;
    }

    public void setProtocolDesc(String protocolDesc) {
        this.protocolDesc = protocolDesc;
    }

    public String getProtocolTitle() {
        return protocolTitle;
    }

    public void setProtocolTitle(String protocolTitle) {
        this.protocolTitle = protocolTitle;
    }

    public String getHeadTip() {
        return headTip;
    }

    public void setHeadTip(String headTip) {
        this.headTip = headTip;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIndentityNoShow() {
        return indentityNoShow;
    }

    public void setIndentityNoShow(String indentityNoShow) {
        this.indentityNoShow = indentityNoShow;
    }
}
