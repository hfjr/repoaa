package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto;

/**
 * Created by hexy on 16/6/7.
 */
public class FundAndSocialSecurityAccountTaskDTO{

    String userId;
    String cityCode;
    String taskCode;
    String fundAccount;  //公积金账号
    String fundAccountPassword; //公积金账号密码
    String socialSecurityAccount; //社保帐号
    String socialSecurityAccountPassword; //社保帐号查询密码

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getFundAccount() {
        return fundAccount;
    }

    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    public String getFundAccountPassword() {
        return fundAccountPassword;
    }

    public void setFundAccountPassword(String fundAccountPassword) {
        this.fundAccountPassword = fundAccountPassword;
    }

    public String getSocialSecurityAccount() {
        return socialSecurityAccount;
    }

    public void setSocialSecurityAccount(String socialSecurityAccount) {
        this.socialSecurityAccount = socialSecurityAccount;
    }

    public String getSocialSecurityAccountPassword() {
        return socialSecurityAccountPassword;
    }

    public void setSocialSecurityAccountPassword(String socialSecurityAccountPassword) {
        this.socialSecurityAccountPassword = socialSecurityAccountPassword;
    }
}
