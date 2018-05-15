package com.zhuanyi.vjwealth.wallet.service.mainbiz.account.dto;

import java.io.Serializable;
import java.sql.Timestamp;


public class WjCheckingAccountDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id; 
	private java.lang.String checkNo; //对账NO
	private java.lang.String fileBusinessNo; //文件NO
	private java.lang.String checkingData; //对账日期
	private java.lang.String totalNum; //对账总数
	private java.lang.String totalAmount; //对账总金额,以分为单位
	private Timestamp startData; //对账开始时间
	private Timestamp finishData; //对账完成时间
	private java.lang.String result; //对账结果:10:成功,20:失败
	private java.lang.String tradePlatformType; //第三方支付平台类型:10:联动优势,20:易宝充值
	private java.lang.String createBy; 
	private java.lang.String createDate; 
	private java.lang.String updateBy; 
	private java.lang.String updateDate;
	
	private java.lang.String totalCounterFee ;//总手续费
	
	public java.lang.Long getId() {
		return id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	
	public java.lang.String getCheckNo() {
		return checkNo;
	}
	public void setCheckNo(java.lang.String checkNo) {
		this.checkNo = checkNo;
	}
	public java.lang.String getFileBusinessNo() {
		return fileBusinessNo;
	}
	public void setFileBusinessNo(java.lang.String fileBusinessNo) {
		this.fileBusinessNo = fileBusinessNo;
	}
	public java.lang.String getCheckingData() {
		return checkingData;
	}
	public void setCheckingData(java.lang.String checkingData) {
		this.checkingData = checkingData;
	}
	public java.lang.String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(java.lang.String totalNum) {
		this.totalNum = totalNum;
	}
	public java.lang.String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(java.lang.String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public Timestamp getStartData() {
		return startData;
	}
	public void setStartData(Timestamp startData) {
		this.startData = startData;
	}
	public Timestamp getFinishData() {
		return finishData;
	}
	public void setFinishData(Timestamp finishData) {
		this.finishData = finishData;
	}
	public java.lang.String getResult() {
		return result;
	}
	public void setResult(java.lang.String result) {
		this.result = result;
	}
	public java.lang.String getTradePlatformType() {
		return tradePlatformType;
	}
	public void setTradePlatformType(java.lang.String tradePlatformType) {
		this.tradePlatformType = tradePlatformType;
	}
	public java.lang.String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}
	public java.lang.String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.lang.String createDate) {
		this.createDate = createDate;
	}
	public java.lang.String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(java.lang.String updateBy) {
		this.updateBy = updateBy;
	}
	public java.lang.String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(java.lang.String updateDate) {
		this.updateDate = updateDate;
	}
	public java.lang.String getTotalCounterFee() {
		return totalCounterFee;
	}
	public void setTotalCounterFee(java.lang.String totalCounterFee) {
		this.totalCounterFee = totalCounterFee;
	}

}
