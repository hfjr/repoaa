package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.easyLoan;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 工资易贷初始化  create by wangzf 2016\09\05
 */
public class EasyLoanInitDTO {

    private String userId;
    private String userName;
    private String phone;
    private String topIntroducePic;
    private String contactPhonePic;
    private String contactPhone;
    private Map<String,String> fundPeriodDefault;
    private List<Map<String,String>> fundPeriodSelection;
    private Map<String,String> cityDefault;

    private Map<String,String> houseTypeDefault;
    private List<Map<String,String>> houseTypeSelection;

    private Map<String,String> loanPeriodDefault;
    private List<Map<String,String>> loanPeriodSelection;

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

    public String getTopIntroducePic() {
        return topIntroducePic;
    }

    public void setTopIntroducePic(String topIntroducePic) {
        this.topIntroducePic = topIntroducePic;
    }

    public String getContactPhonePic() {
        return contactPhonePic;
    }

    public void setContactPhonePic(String contactPhonePic) {
        this.contactPhonePic = contactPhonePic;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Map<String, String> getFundPeriodDefault() {
        return fundPeriodDefault;
    }

    public void setFundPeriodDefault(Map<String, String> fundPeriodDefault) {
        this.fundPeriodDefault = fundPeriodDefault;
    }

    public List<Map<String, String>> getFundPeriodSelection() {
        return fundPeriodSelection;
    }

    public void setFundPeriodSelection(List<Map<String, String>> fundPeriodSelection) {
        this.fundPeriodSelection = fundPeriodSelection;
    }

    public Map<String, String> getCityDefault() {
        return cityDefault;
    }

    public void setCityDefault(Map<String, String> cityDefault) {
        this.cityDefault = cityDefault;
    }


    public Map<String, String> getLoanPeriodDefault() {
        return loanPeriodDefault;
    }

    public void setLoanPeriodDefault(Map<String, String> loanPeriodDefault) {
        this.loanPeriodDefault = loanPeriodDefault;
    }

    public List<Map<String, String>> getLoanPeriodSelection() {
        return loanPeriodSelection;
    }

    public void setLoanPeriodSelection(List<Map<String, String>> loanPeriodSelection) {
        this.loanPeriodSelection = loanPeriodSelection;
    }

    public Map<String, String> getHouseTypeDefault() {
        return houseTypeDefault;
    }

    public void setHouseTypeDefault(Map<String, String> houseTypeDefault) {
        this.houseTypeDefault = houseTypeDefault;
    }

    public List<Map<String, String>> getHouseTypeSelection() {
        return houseTypeSelection;
    }

    public void setHouseTypeSelection(List<Map<String, String>> houseTypeSelection) {
        this.houseTypeSelection = houseTypeSelection;
    }
}
