package com.rqb.ips.depository.platform.beans;

import java.io.Serializable;

public class IPSRequierParamsBean implements Serializable{

	private static final long serialVersionUID = -3631066441989550350L;

	private String operationType;
	private String merchantID;
	private String sign;
	private String request;
	private String url;
	

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
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
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
}
