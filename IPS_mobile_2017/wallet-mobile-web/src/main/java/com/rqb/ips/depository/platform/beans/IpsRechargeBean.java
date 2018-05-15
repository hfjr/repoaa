package com.rqb.ips.depository.platform.beans;

import java.io.Serializable;

/**
 * 
* <p>Title: IpsRechargeBean.java</p>
* <p>Description: 充值</p>
* @author sunxiaolei
* @date 2017年12月5日
* @version 1.0
 */


public class IpsRechargeBean implements Serializable{
  
	private static final long serialVersionUID = 3589023498852875280L;

	private String merBillNo;

    private String merDate;

    private String depositType;

    private String channelType;

    private String bankCode;

    private String userType;

    private String ipsAcctNo;

    private String trdAmt;

    private String ipsFeeType;

    private String merFee;

    private String merFeeType;

    private String taker;

    private String webUrl;
    
    private String s2SUrl;

	public String getMerBillNo() {
		return merBillNo;
	}

	public void setMerBillNo(String merBillNo) {
		this.merBillNo = merBillNo;
	}

	public String getMerDate() {
		return merDate;
	}

	public void setMerDate(String merDate) {
		this.merDate = merDate;
	}

	public String getDepositType() {
		return depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getIpsAcctNo() {
		return ipsAcctNo;
	}

	public void setIpsAcctNo(String ipsAcctNo) {
		this.ipsAcctNo = ipsAcctNo;
	}

	public String getTrdAmt() {
		return trdAmt;
	}

	public void setTrdAmt(String trdAmt) {
		this.trdAmt = trdAmt;
	}

	public String getIpsFeeType() {
		return ipsFeeType;
	}

	public void setIpsFeeType(String ipsFeeType) {
		this.ipsFeeType = ipsFeeType;
	}

	public String getMerFee() {
		return merFee;
	}

	public void setMerFee(String merFee) {
		this.merFee = merFee;
	}

	public String getMerFeeType() {
		return merFeeType;
	}

	public void setMerFeeType(String merFeeType) {
		this.merFeeType = merFeeType;
	}

	public String getTaker() {
		return taker;
	}

	public void setTaker(String taker) {
		this.taker = taker;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getS2SUrl() {
		return s2SUrl;
	}

	public void setS2SUrl(String s2sUrl) {
		s2SUrl = s2sUrl;
	}
  
    
   
}