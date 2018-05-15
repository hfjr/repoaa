package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * V信贷产品类型
 * Created by hexy on 16/6/7.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class ProductTypeDTO {

    private String  productTypeName; //  产品类型名称
    private String  productTypeIntroduction; //  产品类型介绍
    private String  productTypeCode; //产品编号
    private String  productTypeIntroductionPictureURL; //  产品类型介绍背景图片URL
    private String  isAvailable; //  产品类型是否可以使用（维护中）
    private String  isAvailableTipInformation; //  产品类型不可使用提示信息（维护中）
    private String  buttonTextMessage; //  按钮文字信息
    private String  wizardPictureURL; //  向导图片URL
    private String  wizardDetailURL; // 向导详情URL
    private String  wizardDetailURLTitle; // 向导详情URL标题

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getProductTypeIntroduction() {
        return productTypeIntroduction;
    }

    public void setProductTypeIntroduction(String productTypeIntroduction) {
        this.productTypeIntroduction = productTypeIntroduction;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getIsAvailableTipInformation() {
        return isAvailableTipInformation;
    }

    public void setIsAvailableTipInformation(String isAvailableTipInformation) {
        this.isAvailableTipInformation = isAvailableTipInformation;
    }

    public String getButtonTextMessage() {
        return buttonTextMessage;
    }

    public void setButtonTextMessage(String buttonTextMessage) {
        this.buttonTextMessage = buttonTextMessage;
    }

    public String getWizardPictureURL() {
        return wizardPictureURL;
    }

    public void setWizardPictureURL(String wizardPictureURL) {
        this.wizardPictureURL = wizardPictureURL;
    }

    public String getWizardDetailURL() {
        return wizardDetailURL;
    }

    public void setWizardDetailURL(String wizardDetailURL) {
        this.wizardDetailURL = wizardDetailURL;
    }

    public String getWizardDetailURLTitle() {
        return wizardDetailURLTitle;
    }

    public void setWizardDetailURLTitle(String wizardDetailURLTitle) {
        this.wizardDetailURLTitle = wizardDetailURLTitle;
    }

    public String getProductTypeIntroductionPictureURL() {
        return productTypeIntroductionPictureURL;
    }

    public void setProductTypeIntroductionPictureURL(String productTypeIntroductionPictureURL) {
        this.productTypeIntroductionPictureURL = productTypeIntroductionPictureURL;
    }

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
    
    
}
