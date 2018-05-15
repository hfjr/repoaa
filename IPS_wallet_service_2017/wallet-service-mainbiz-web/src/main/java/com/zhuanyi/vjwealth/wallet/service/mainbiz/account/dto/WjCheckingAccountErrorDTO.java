package com.zhuanyi.vjwealth.wallet.service.mainbiz.account.dto;

import java.io.Serializable;
import java.sql.Timestamp;


public class WjCheckingAccountErrorDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id; 
	private java.lang.String checkNo;//主表checkNo
	private java.lang.String checkDetailNo;//主表明细No
	private java.lang.String userId; //用户ID
	private java.lang.String tradeNo; //交易号
	private java.lang.String errorMessage; //对账错误结果信息
	private java.lang.String status; //差错处理状态：100：未处理，101：程序自动处理完毕，102：人工处理等状态
	private java.lang.String orderNo; //订单No
	private java.lang.String orderAmount; //订单金额,以分为单位
	private Timestamp errorDisposeData; //差错处理时间
	private java.lang.String checkTradeAmount; //对账单交易金额,以分为单位
	private java.lang.String tradePlatformType; //第三方支付平台类型:10:联动优势
	private java.lang.String createBy; 
	private java.lang.String createDate; 
	private java.lang.String updateBy; 
	private java.lang.String updateDate;
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
	public java.lang.String getCheckDetailNo() {
		return checkDetailNo;
	}
	public void setCheckDetailNo(java.lang.String checkDetailNo) {
		this.checkDetailNo = checkDetailNo;
	}
	public java.lang.String getUserId() {
		return userId;
	}
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	public java.lang.String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(java.lang.String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public java.lang.String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(java.lang.String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public java.lang.String getStatus() {
		return status;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	public java.lang.String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}
	public java.lang.String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(java.lang.String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Timestamp getErrorDisposeData() {
		return errorDisposeData;
	}
	public void setErrorDisposeData(Timestamp errorDisposeData) {
		this.errorDisposeData = errorDisposeData;
	}
	public java.lang.String getCheckTradeAmount() {
		return checkTradeAmount;
	}
	public void setCheckTradeAmount(java.lang.String checkTradeAmount) {
		this.checkTradeAmount = checkTradeAmount;
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
	
	
}
