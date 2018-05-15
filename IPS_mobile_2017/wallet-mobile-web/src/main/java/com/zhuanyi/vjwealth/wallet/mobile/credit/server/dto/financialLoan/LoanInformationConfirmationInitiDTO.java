package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by hexy on 16/6/12.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class LoanInformationConfirmationInitiDTO {

    private List<Map<String,String>> personalInformation  ; // 个人信息(key[label,value])
    private List<Map<String,String>> loadDetailInformation  ; // 借款信息(key[label,value])
    private String protocolSpecification ; //  协议说明
    //private String protocolName   ; //  协议名称
    private String buttonTextMessage  ; // 按钮文字信息

    private String contractTitle;
    private String contractURL;
    private String contractLabel;

    public List<Map<String, String>> getPersonalInformation() {
        return personalInformation;
    }

    public void setPersonalInformation(List<Map<String, String>> personalInformation) {
        this.personalInformation = personalInformation;
    }

    public List<Map<String, String>> getLoadDetailInformation() {
        return loadDetailInformation;
    }

    public void setLoadDetailInformation(List<Map<String, String>> loadDetailInformation) {
        this.loadDetailInformation = loadDetailInformation;
    }

    public String getProtocolSpecification() {
        return protocolSpecification;
    }

    public void setProtocolSpecification(String protocolSpecification) {
        this.protocolSpecification = protocolSpecification;
    }

    public String getButtonTextMessage() {
        return buttonTextMessage;
    }

    public void setButtonTextMessage(String buttonTextMessage) {
        this.buttonTextMessage = buttonTextMessage;
    }

	public String getContractTitle() {
		return contractTitle;
	}

	public void setContractTitle(String contractTitle) {
		this.contractTitle = contractTitle;
	}

	public String getContractURL() {
		return contractURL;
	}

	public void setContractURL(String contractURL) {
		this.contractURL = contractURL;
	}

	public String getContractLabel() {
		return contractLabel;
	}

	public void setContractLabel(String contractLabel) {
		this.contractLabel = contractLabel;
	}
    
    
}
