package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

import java.util.List;
import java.util.Map;

/**
 * Created by wzf on 2016/10/31.
 */
public class BingdingBankCardInitDTO {

    private String ownerNameLabel;// 持卡人,
    private String ownerName;// ,
    private String ownerIdentityLabel;// 身份证号码,
    private String ownerIdentity;// ,
    private String bankNameLabel;// 所属银行,
    private Map<String,String> defaultBankName;//
    private List<Map<String,String>> bankSelection;//
    private String bankDistrictLabel;// 所属银行地区,
    private String bankDistrict;// ,
    private String bankCardNoLabel;// 银行卡号,
    private String bankCardNo;// ,
    private String bankReservationPhoneLabel;// 银行预留手机号,
    private String bankReservationPhone;// ,
    private String messageCodeLabel;// 验证码,
    private String messageCode;// ，
    private String manageRuleLabel;// 同意并签署《融桥宝资金管理规定》，
    private String manageRuleUrl;// ，
    private String manageRuleUrlTitle;//
    private String cardBindMobilePhoneNoHelpURL;
    private String isSendSMS;
    private String bankCode;

    public String getOwnerNameLabel() {
        return ownerNameLabel;
    }

    public void setOwnerNameLabel(String ownerNameLabel) {
        this.ownerNameLabel = ownerNameLabel;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerIdentityLabel() {
        return ownerIdentityLabel;
    }

    public void setOwnerIdentityLabel(String ownerIdentityLabel) {
        this.ownerIdentityLabel = ownerIdentityLabel;
    }

    public String getOwnerIdentity() {
        return ownerIdentity;
    }

    public void setOwnerIdentity(String ownerIdentity) {
        this.ownerIdentity = ownerIdentity;
    }

    public String getBankNameLabel() {
        return bankNameLabel;
    }

    public void setBankNameLabel(String bankNameLabel) {
        this.bankNameLabel = bankNameLabel;
    }

    public Map<String, String> getDefaultBankName() {
        return defaultBankName;
    }

    public void setDefaultBankName(Map<String, String> defaultBankName) {
        this.defaultBankName = defaultBankName;
    }

    public List<Map<String, String>> getBankSelection() {
        return bankSelection;
    }

    public void setBankSelection(List<Map<String, String>> bankSelection) {
        this.bankSelection = bankSelection;
    }

    public String getBankDistrictLabel() {
        return bankDistrictLabel;
    }

    public void setBankDistrictLabel(String bankDistrictLabel) {
        this.bankDistrictLabel = bankDistrictLabel;
    }

    public String getBankDistrict() {
        return bankDistrict;
    }

    public void setBankDistrict(String bankDistrict) {
        this.bankDistrict = bankDistrict;
    }

    public String getBankCardNoLabel() {
        return bankCardNoLabel;
    }

    public void setBankCardNoLabel(String bankCardNoLabel) {
        this.bankCardNoLabel = bankCardNoLabel;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankReservationPhoneLabel() {
        return bankReservationPhoneLabel;
    }

    public void setBankReservationPhoneLabel(String bankReservationPhoneLabel) {
        this.bankReservationPhoneLabel = bankReservationPhoneLabel;
    }

    public String getBankReservationPhone() {
        return bankReservationPhone;
    }

    public void setBankReservationPhone(String bankReservationPhone) {
        this.bankReservationPhone = bankReservationPhone;
    }

    public String getMessageCodeLabel() {
        return messageCodeLabel;
    }

    public void setMessageCodeLabel(String messageCodeLabel) {
        this.messageCodeLabel = messageCodeLabel;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getManageRuleLabel() {
        return manageRuleLabel;
    }

    public void setManageRuleLabel(String manageRuleLabel) {
        this.manageRuleLabel = manageRuleLabel;
    }

    public String getManageRuleUrl() {
        return manageRuleUrl;
    }

    public void setManageRuleUrl(String manageRuleUrl) {
        this.manageRuleUrl = manageRuleUrl;
    }

    public String getManageRuleUrlTitle() {
        return manageRuleUrlTitle;
    }

    public void setManageRuleUrlTitle(String manageRuleUrlTitle) {
        this.manageRuleUrlTitle = manageRuleUrlTitle;
    }

    public String getCardBindMobilePhoneNoHelpURL() {
        return cardBindMobilePhoneNoHelpURL;
    }

    public void setCardBindMobilePhoneNoHelpURL(String cardBindMobilePhoneNoHelpURL) {
        this.cardBindMobilePhoneNoHelpURL = cardBindMobilePhoneNoHelpURL;
    }

    public String getIsSendSMS() {
        return isSendSMS;
    }

    public void setIsSendSMS(String isSendSMS) {
        this.isSendSMS = isSendSMS;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
