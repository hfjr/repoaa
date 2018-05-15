package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto;

import java.io.Serializable;

/**
 * Created by hexy on 16/5/12.
 */
public class PersonalInformationDTO implements Serializable {

    private String  userId;
    private String  name;
    private String  identity;
    private String  gender;
    private String  education;
    private String  maritalStatus;
    private String  workCity;
    private String  workUnit;
    private String  monthlyIncome;
    private String  bankCardNumber;
    private String  fundAccount;
    private String  fundAccountPassword;
    private String  socialSecurityAccount;
    private String  socialSecurityAccountPassword;
    private String  borrowCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
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

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public String getBorrowCode() {
        return borrowCode;
    }

    public void setBorrowCode(String borrowCode) {
        this.borrowCode = borrowCode;
    }
}
