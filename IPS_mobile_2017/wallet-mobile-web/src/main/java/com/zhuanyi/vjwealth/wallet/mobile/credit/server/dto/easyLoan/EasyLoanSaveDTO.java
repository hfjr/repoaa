package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.easyLoan;

import com.alibaba.fastjson.JSONArray;

/**
 * 工资易贷保存  create by wangzf 2016\09\05
 */
public class EasyLoanSaveDTO {

    private String userId;
    private String userName;
    private String phone;
    private String applyAmount;
    private String userChannelType;
    private String housePlace;
    private String housePlaceDesc;
    private String fundPeriod;
    private String fundPeriodDesc;
    private String addressDetail;
    private String loanProductId;

    private String houseType;
    private String houseTypeDesc;
    private String loanPeriod;
    private String periodUnit;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(String applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getUserChannelType() {
        return userChannelType;
    }

    public void setUserChannelType(String userChannelType) {
        this.userChannelType = userChannelType;
    }

    public String getHousePlace() {
        return housePlace;
    }

    public void setHousePlace(String housePlace) {
        this.housePlace = housePlace;
    }

    public String getHousePlaceDesc() {
        return housePlaceDesc;
    }

    public void setHousePlaceDesc(String housePlaceDesc) {
        this.housePlaceDesc = housePlaceDesc;
    }

    public String getFundPeriod() {
        return fundPeriod;
    }

    public void setFundPeriod(String fundPeriod) {
        this.fundPeriod = fundPeriod;
    }

    public String getLoanProductId() {
        return loanProductId;
    }

    public void setLoanProductId(String loanProductId) {
        this.loanProductId = loanProductId;
    }

    public String getFundPeriodDesc() {
        return fundPeriodDesc;
    }

    public void setFundPeriodDesc(String fundPeriodDesc) {
        this.fundPeriodDesc = fundPeriodDesc;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getHouseTypeDesc() {
        return houseTypeDesc;
    }

    public void setHouseTypeDesc(String houseTypeDesc) {
        this.houseTypeDesc = houseTypeDesc;
    }

    public String getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(String loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public String getPeriodUnit() {
        return periodUnit;
    }

    public void setPeriodUnit(String periodUnit) {
        this.periodUnit = periodUnit;
    }
}
