package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.dto;


public class MatchfundProductInfoDTO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	// 产品名称
	 private java.lang.String productName; 
	// 配资金额
	 private java.math.BigDecimal matchfundPrice; 
	// 是否上架(yes/no)
	 private java.lang.String isShelves; 
	// 是否售罄
	 private java.lang.String soldOut; 
	// 所在地
	 private java.lang.String location; 
	// 购买方式
	 private java.lang.String buyType; 
	// 拍卖状态
	 private java.lang.String auctionStatus; 
	// 拍品价格
	 private java.math.BigDecimal auctionPrice; 
	// 开始时间
	private String startDate;
	// 结束时间
	private String endDate;
	// 加价幅度
	 private java.lang.String raisePrice; 
	// 保证金
	 private java.lang.String guaranteePrice; 
	// 评估价
	 private java.lang.String evaluatePrice; 
	// 起拍价
	 private java.lang.String startPrice; 
	// 竞拍周期
	 private java.lang.String auctionCycle; 
	// 延时周期
	 private java.lang.String delayedTerm; 
	// 类型(竞拍)
	 private java.lang.String auctionType; 
	// 优先购买权人
	 private java.lang.String preferredPurchaser; 
	// 竞价规则
	 private java.lang.String bidRules; 
	// 处置单位
	 private java.lang.String disposalUnit; 
	// 咨询方式
	 private java.lang.String consultationMethod; 
	// 竞卖公告
	 private java.lang.String bidAnnouncement; 
	// 标的详情
	 private java.lang.String introductionSubjectMatter; 
	
	public java.lang.String getProductName() {
		return productName;
	}
	
	public void setProductName(java.lang.String productName) {
		this.productName=productName;
	}
	public java.math.BigDecimal getMatchfundPrice() {
		return matchfundPrice;
	}
	
	public void setMatchfundPrice(java.math.BigDecimal matchfundPrice) {
		this.matchfundPrice=matchfundPrice;
	}
	public java.lang.String getIsShelves() {
		return isShelves;
	}
	
	public void setIsShelves(java.lang.String isShelves) {
		this.isShelves=isShelves;
	}
	public java.lang.String getSoldOut() {
		return soldOut;
	}
	
	public void setSoldOut(java.lang.String soldOut) {
		this.soldOut=soldOut;
	}
	public java.lang.String getLocation() {
		return location;
	}
	
	public void setLocation(java.lang.String location) {
		this.location=location;
	}
	public java.lang.String getBuyType() {
		return buyType;
	}
	
	public void setBuyType(java.lang.String buyType) {
		this.buyType=buyType;
	}
	public java.lang.String getAuctionStatus() {
		return auctionStatus;
	}
	
	public void setAuctionStatus(java.lang.String auctionStatus) {
		this.auctionStatus=auctionStatus;
	}
	public java.math.BigDecimal getAuctionPrice() {
		return auctionPrice;
	}
	
	public void setAuctionPrice(java.math.BigDecimal auctionPrice) {
		this.auctionPrice=auctionPrice;
	}
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate=startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate=endDate;
	}
	public java.lang.String getRaisePrice() {
		return raisePrice;
	}
	
	public void setRaisePrice(java.lang.String raisePrice) {
		this.raisePrice=raisePrice;
	}
	public java.lang.String getGuaranteePrice() {
		return guaranteePrice;
	}
	
	public void setGuaranteePrice(java.lang.String guaranteePrice) {
		this.guaranteePrice=guaranteePrice;
	}
	public java.lang.String getEvaluatePrice() {
		return evaluatePrice;
	}
	
	public void setEvaluatePrice(java.lang.String evaluatePrice) {
		this.evaluatePrice=evaluatePrice;
	}
	public java.lang.String getStartPrice() {
		return startPrice;
	}
	
	public void setStartPrice(java.lang.String startPrice) {
		this.startPrice=startPrice;
	}
	public java.lang.String getAuctionCycle() {
		return auctionCycle;
	}
	
	public void setAuctionCycle(java.lang.String auctionCycle) {
		this.auctionCycle=auctionCycle;
	}
	public java.lang.String getDelayedTerm() {
		return delayedTerm;
	}
	
	public void setDelayedTerm(java.lang.String delayedTerm) {
		this.delayedTerm=delayedTerm;
	}
	public java.lang.String getAuctionType() {
		return auctionType;
	}
	
	public void setAuctionType(java.lang.String auctionType) {
		this.auctionType=auctionType;
	}
	public java.lang.String getPreferredPurchaser() {
		return preferredPurchaser;
	}
	
	public void setPreferredPurchaser(java.lang.String preferredPurchaser) {
		this.preferredPurchaser=preferredPurchaser;
	}
	public java.lang.String getBidRules() {
		return bidRules;
	}
	
	public void setBidRules(java.lang.String bidRules) {
		this.bidRules=bidRules;
	}
	public java.lang.String getDisposalUnit() {
		return disposalUnit;
	}
	
	public void setDisposalUnit(java.lang.String disposalUnit) {
		this.disposalUnit=disposalUnit;
	}
	public java.lang.String getConsultationMethod() {
		return consultationMethod;
	}
	
	public void setConsultationMethod(java.lang.String consultationMethod) {
		this.consultationMethod=consultationMethod;
	}
	public java.lang.String getBidAnnouncement() {
		return bidAnnouncement;
	}
	
	public void setBidAnnouncement(java.lang.String bidAnnouncement) {
		this.bidAnnouncement=bidAnnouncement;
	}
	public java.lang.String getIntroductionSubjectMatter() {
		return introductionSubjectMatter;
	}
	
	public void setIntroductionSubjectMatter(java.lang.String introductionSubjectMatter) {
		this.introductionSubjectMatter=introductionSubjectMatter;
	}
}
