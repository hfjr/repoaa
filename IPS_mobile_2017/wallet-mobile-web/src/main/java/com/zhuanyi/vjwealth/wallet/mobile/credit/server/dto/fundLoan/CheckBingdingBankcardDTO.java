package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

import java.util.List;
import java.util.Map;

/**
 * Created by wzf on 2016/10/31.
 */
public class CheckBingdingBankcardDTO {

    private String userId;
    private String borrowCode;
    private String ownerName;// 持卡人姓名
    private String ownerIdentity;// 身份证号
    private String bankCode;//银行编号
    private String bankProvince;// 银行所属省份
    private String bankCity;//银行所属城市
    private String bankCardNo;// 银行卡号
    private String bankReservationPhone;// 预留手机号
    private String messageCode;// 验证码
    private String innerBankCode;
    private String isSendSMS;//是否需要校验验证码

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerIdentity() {
        return ownerIdentity;
    }

    public void setOwnerIdentity(String ownerIdentity) {
        this.ownerIdentity = ownerIdentity;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankProvince() {
        return bankProvince;
    }

    public void setBankProvince(String bankProvince) {
        this.bankProvince = bankProvince;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankReservationPhone() {
        return bankReservationPhone;
    }

    public void setBankReservationPhone(String bankReservationPhone) {
        this.bankReservationPhone = bankReservationPhone;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBorrowCode() {
        return borrowCode;
    }

    public void setBorrowCode(String borrowCode) {
        this.borrowCode = borrowCode;
    }

    public String getInnerBankCode() {
        return innerBankCode;
    }

    public void setInnerBankCode(String innerBankCode) {
        this.innerBankCode = innerBankCode;
    }

    public String getIsSendSMS() {
        return isSendSMS;
    }

    public void setIsSendSMS(String isSendSMS) {
        this.isSendSMS = isSendSMS;
    }
}
