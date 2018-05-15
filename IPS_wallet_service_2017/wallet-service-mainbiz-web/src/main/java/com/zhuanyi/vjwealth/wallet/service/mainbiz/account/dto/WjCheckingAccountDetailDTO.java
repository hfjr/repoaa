package com.zhuanyi.vjwealth.wallet.service.mainbiz.account.dto;

import java.io.Serializable;


public class WjCheckingAccountDetailDTO implements Serializable{ //extends LigerGridPageDTO {
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id; 
	private java.lang.String checkNo;//主表checkNo
	private java.lang.String checkDetailNo;//主表明细No
	private java.lang.String merId; //商户号,平台统一分配的商户号
	private java.lang.String goodsId; //商品号,下单时提交的goods_id
	private java.lang.String orderId; //订单号,下单时提交的order_id
	private java.lang.String checkTradeAmount; //对账单交易金额,以分为单位
	private java.lang.String checkMobileId; //用户支付时使用的手机号码
	private java.lang.String checkMerDate; //商户下单时提交的mer_date
	private java.lang.String checkPayDate; //用户确认支付的日期
	private java.lang.String amtType; //付款类型,01：人民币 02：移动话费 03：移动积分
	private java.lang.String checkGateId; //银行类型
	private java.lang.String checkSettleDate; //交易的对账日期
	private java.lang.String checkTransType; //交易类型	01：消费 20：退款
	private java.lang.String checkTransStatus; //交易状态	0：初始 1：成功 -1：失败（一般情况下对账文件只出成功交易）
	private java.lang.String checkBankCheck; //银行对账状态	0：初始 1：对账成功 -1：对账失败
	private java.lang.String checkProductId; //支付产品编号	联动定义的支付产品
	private java.lang.String checkRefundNo; //退款号
	private java.lang.String checkTransTime; //交易成功时间	最后交易时间:HHmmss
	private java.lang.String createBy; 
	private java.lang.String createDate; 
	private java.lang.String updateBy; 
	private java.lang.String updateDate;
	private java.lang.String contrastFlag;//是否比对,'Y'比对过,'N'未比对
	
	private java.lang.String tradeNo ;//交易号
	private java.lang.String counterFee ;//收款方手续费
	
	private java.lang.String remark1 ;//业务类型
	private java.lang.String remark2 ;//订单类型
	
	//分页
	private Integer pageSize ;
	private Integer beginIndex = 0 ;
	
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
	public java.lang.String getMerId() {
		return merId;
	}
	public void setMerId(java.lang.String merId) {
		this.merId = merId;
	}
	public java.lang.String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(java.lang.String goodsId) {
		this.goodsId = goodsId;
	}
	public java.lang.String getOrderId() {
		return orderId;
	}
	public void setOrderId(java.lang.String orderId) {
		this.orderId = orderId;
	}
	public java.lang.String getCheckTradeAmount() {
		return checkTradeAmount;
	}
	public void setCheckTradeAmount(java.lang.String checkTradeAmount) {
		this.checkTradeAmount = checkTradeAmount;
	}
	public java.lang.String getCheckMobileId() {
		return checkMobileId;
	}
	public void setCheckMobileId(java.lang.String checkMobileId) {
		this.checkMobileId = checkMobileId;
	}
	public java.lang.String getCheckMerDate() {
		return checkMerDate;
	}
	public void setCheckMerDate(java.lang.String checkMerDate) {
		this.checkMerDate = checkMerDate;
	}
	public java.lang.String getCheckPayDate() {
		return checkPayDate;
	}
	public void setCheckPayDate(java.lang.String checkPayDate) {
		this.checkPayDate = checkPayDate;
	}
	public java.lang.String getAmtType() {
		return amtType;
	}
	public void setAmtType(java.lang.String amtType) {
		this.amtType = amtType;
	}
	public java.lang.String getCheckGateId() {
		return checkGateId;
	}
	public void setCheckGateId(java.lang.String checkGateId) {
		this.checkGateId = checkGateId;
	}
	public java.lang.String getCheckSettleDate() {
		return checkSettleDate;
	}
	public void setCheckSettleDate(java.lang.String checkSettleDate) {
		this.checkSettleDate = checkSettleDate;
	}
	public java.lang.String getCheckTransType() {
		return checkTransType;
	}
	public void setCheckTransType(java.lang.String checkTransType) {
		this.checkTransType = checkTransType;
	}
	public java.lang.String getCheckTransStatus() {
		return checkTransStatus;
	}
	public void setCheckTransStatus(java.lang.String checkTransStatus) {
		this.checkTransStatus = checkTransStatus;
	}
	public java.lang.String getCheckBankCheck() {
		return checkBankCheck;
	}
	public void setCheckBankCheck(java.lang.String checkBankCheck) {
		this.checkBankCheck = checkBankCheck;
	}
	public java.lang.String getCheckProductId() {
		return checkProductId;
	}
	public void setCheckProductId(java.lang.String checkProductId) {
		this.checkProductId = checkProductId;
	}
	public java.lang.String getCheckRefundNo() {
		return checkRefundNo;
	}
	public void setCheckRefundNo(java.lang.String checkRefundNo) {
		this.checkRefundNo = checkRefundNo;
	}
	public java.lang.String getCheckTransTime() {
		return checkTransTime;
	}
	public void setCheckTransTime(java.lang.String checkTransTime) {
		this.checkTransTime = checkTransTime;
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
	public java.lang.String getContrastFlag() {
		return contrastFlag;
	}
	public void setContrastFlag(java.lang.String contrastFlag) {
		this.contrastFlag = contrastFlag;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getBeginIndex() {
		return beginIndex;
	}
	public void setBeginIndex(Integer beginIndex) {
		this.beginIndex = beginIndex;
	}
	public java.lang.String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(java.lang.String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public java.lang.String getCounterFee() {
		return counterFee;
	}
	public void setCounterFee(java.lang.String counterFee) {
		this.counterFee = counterFee;
	}
	public java.lang.String getRemark1() {
		return remark1;
	}
	public void setRemark1(java.lang.String remark1) {
		this.remark1 = remark1;
	}
	public java.lang.String getRemark2() {
		return remark2;
	}
	public void setRemark2(java.lang.String remark2) {
		this.remark2 = remark2;
	}
	
	

}
