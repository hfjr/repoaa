package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.DeviceDetailInfo;

/**
 * Created by wzf on 2016/10/27.
 */
public class FundInfoSaveDTO extends DeviceDetailInfo {

    private String userId;
    private String borrowCode;
    private String cityCode;
    private String loginType;

    private String fundAccount;
    private String fundAccountPassword;
    private String idCard;
    private String otherParam;
    private String realName;

    private String fundAccountValidator;
    private String fundAccountPasswordValidator;
    private String idCardValidator;
    private String otherParamValidator;
    private String realNameValidator;

    private String equipmentNumber;
    private String osType;
    private String osVersion;
    
    private String taskId;
    private String verificationCode;

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

    @Override
    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    @Override
    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    @Override
    public String getOsType() {
        return osType;
    }

    @Override
    public void setOsType(String osType) {
        this.osType = osType;
    }

    @Override
    public String getOsVersion() {
        return osVersion;
    }

    @Override
    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getFundAccountValidator() {
        return fundAccountValidator;
    }

    public void setFundAccountValidator(String fundAccountValidator) {
        this.fundAccountValidator = fundAccountValidator;
    }

    public String getFundAccountPasswordValidator() {
        return fundAccountPasswordValidator;
    }

    public void setFundAccountPasswordValidator(String fundAccountPasswordValidator) {
        this.fundAccountPasswordValidator = fundAccountPasswordValidator;
    }

    public String getIdCardValidator() {
        return idCardValidator;
    }

    public void setIdCardValidator(String idCardValidator) {
        this.idCardValidator = idCardValidator;
    }

    public String getOtherParamValidator() {
        return otherParamValidator;
    }

    public void setOtherParamValidator(String otherParamValidator) {
        this.otherParamValidator = otherParamValidator;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealNameValidator() {
        return realNameValidator;
    }

    public void setRealNameValidator(String realNameValidator) {
        this.realNameValidator = realNameValidator;
    }

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
    
}
