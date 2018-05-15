package com.rqb.ips.depository.platform.beans;

import java.io.Serializable;

public class IPSRechargeResponseBean implements Serializable{
	
	private static final long serialVersionUID = -6351420274200333843L;

	private String resultCode;
	
	private String resultMsg;
	
	private String merchantID;
	
	private String sign;
	
	private String response;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getMerchantID() {
		return merchantID;
	}

	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
