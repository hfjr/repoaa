package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

import java.io.Serializable;

/**
 * Created by csy on 2016/11/25.
 */
public class SDHouseFundLoanApplyDTO implements Serializable{
    private String name;
    private String sdOrderId;//闪贷订单ID
    private String phone;//手机号
    private String code;//短信验证码
    private String cityCode;//城市编码
    private String loginType;//公积金登录类型
    private String houseFundAccount;//公积金账号
    private String houseFundPassword;//公积金密码
    private String idCard;//公积金登录身份证号码
    private String otherParam;//公积金登录其他元素
    private String realName;//公积金登录姓名

    private String equipmentNumber;
    private String osType;
    private String osVersion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String channelId;//渠道ID

    public String getSdOrderId() {
        return sdOrderId;
    }

    public void setSdOrderId(String sdOrderId) {
        this.sdOrderId = sdOrderId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getHouseFundAccount() {
        return houseFundAccount;
    }

    public void setHouseFundAccount(String houseFundAccount) {
        this.houseFundAccount = houseFundAccount;
    }

    public String getHouseFundPassword() {
        return houseFundPassword;
    }

    public void setHouseFundPassword(String houseFundPassword) {
        this.houseFundPassword = houseFundPassword;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getOtherParam() {
        return otherParam;
    }

    public void setOtherParam(String otherParam) {
        this.otherParam = otherParam;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }
}
