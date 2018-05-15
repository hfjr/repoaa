package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto;

import java.math.BigDecimal;

public class MBUserInfoDTO {
	
	
	// 取消标识
	private String raiseCancelFlag;
	private String phone; // 手机号

	private String userId; // 用户ID
	
	private String indentityType; // 证件类型

	private String indentityNo; // 证件号码

	private String email; //邮件

	private String name; //用户姓名
	
	private BigDecimal maAvailableAmount; //用户可用余额

	private BigDecimal purchasedAmount; //用户已经购买productId产品的金额

	private String productLoanId; //产品小赢标号

	private String productId; //产品标号

	private BigDecimal productReceiveRate; //产品年化利率

	private BigDecimal productGreenhornReceiveRate; //新手标产品补贴年化利率
	
	private String productFlag;//产品标示，普通标、新手标

	private String productPolicyNo;//产品大保单号

	private String productSoldOut; //产品售光
	
	private String productRepaymentType; //产品还款类型
	
	private BigDecimal productRaiseProgress; //产品销售进度

	private BigDecimal productRemainBalance; //产品剩余份额

	private BigDecimal productTotalFinancing; //产品总募集金额

	private BigDecimal productStartmoney; //产品起投金额

	private BigDecimal productIncreaseMoney; //产品投资金额最小单位

	private String productEndTime; //产品结束日期

	private BigDecimal productSinglePurchaseAmount; //产品单次购买限额

	private BigDecimal productCumulativePurchaseAmount; //产品单用户累计购买限额

	private int productFirstMonthDay; //产品首月计息天数

	private int lockVersion; //产品乐观锁version
	
	private String contractTemplateNo; //合同模板编号
	
	private String ifPurchaseProduct; //是否已经购买过定期理财产品
	
	private String loanProductId;//倒挂理财产品对应的信贷产品id
	
	private String isIpsUser;// 用户是否是 ips 用户
	
	private String ipsAcctNo;//用户的IPS存管账户号

	public String getPhone() {
		return phone;
	}

	public String getRaiseCancelFlag() {
		return raiseCancelFlag;
	}

	public void setRaiseCancelFlag(String raiseCancelFlag) {
		this.raiseCancelFlag = raiseCancelFlag;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIndentityType() {
		return indentityType;
	}

	public void setIndentityType(String indentityType) {
		this.indentityType = indentityType;
	}

	public String getIndentityNo() {
		return indentityNo;
	}

	public void setIndentityNo(String indentityNo) {
		this.indentityNo = indentityNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getMaAvailableAmount() {
		return maAvailableAmount;
	}

	public void setMaAvailableAmount(BigDecimal maAvailableAmount) {
		this.maAvailableAmount = maAvailableAmount;
	}

	public BigDecimal getPurchasedAmount() {
		return purchasedAmount;
	}

	public void setPurchasedAmount(BigDecimal purchasedAmount) {
		this.purchasedAmount = purchasedAmount;
	}

	public String getProductLoanId() {
		return productLoanId;
	}

	public void setProductLoanId(String productLoanId) {
		this.productLoanId = productLoanId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public BigDecimal getProductRemainBalance() {
		return productRemainBalance;
	}

	public void setProductRemainBalance(BigDecimal productRemainBalance) {
		this.productRemainBalance = productRemainBalance;
	}

	public BigDecimal getProductTotalFinancing() {
		return productTotalFinancing;
	}

	public void setProductTotalFinancing(BigDecimal productTotalFinancing) {
		this.productTotalFinancing = productTotalFinancing;
	}

	public BigDecimal getProductStartmoney() {
		return productStartmoney;
	}

	public void setProductStartmoney(BigDecimal productStartmoney) {
		this.productStartmoney = productStartmoney;
	}

	public BigDecimal getProductIncreaseMoney() {
		return productIncreaseMoney;
	}

	public void setProductIncreaseMoney(BigDecimal productIncreaseMoney) {
		this.productIncreaseMoney = productIncreaseMoney;
	}

	public String getProductEndTime() {
		return productEndTime;
	}

	public void setProductEndTime(String productEndTime) {
		this.productEndTime = productEndTime;
	}

	public BigDecimal getProductSinglePurchaseAmount() {
		return productSinglePurchaseAmount;
	}

	public void setProductSinglePurchaseAmount(
			BigDecimal productSinglePurchaseAmount) {
		this.productSinglePurchaseAmount = productSinglePurchaseAmount;
	}

	public BigDecimal getProductCumulativePurchaseAmount() {
		return productCumulativePurchaseAmount;
	}

	public void setProductCumulativePurchaseAmount(
			BigDecimal productCumulativePurchaseAmount) {
		this.productCumulativePurchaseAmount = productCumulativePurchaseAmount;
	}

	public int getLockVersion() {
		return lockVersion;
	}

	public void setLockVersion(int lockVersion) {
		this.lockVersion = lockVersion;
	}

	public String getProductSoldOut() {
		return productSoldOut;
	}

	public void setProductSoldOut(String productSoldOut) {
		this.productSoldOut = productSoldOut;
	}

	public String getContractTemplateNo() {
		return contractTemplateNo;
	}

	public void setContractTemplateNo(String contractTemplateNo) {
		this.contractTemplateNo = contractTemplateNo;
	}

	public BigDecimal getProductReceiveRate() {
		return productReceiveRate;
	}

	public void setProductReceiveRate(BigDecimal productReceiveRate) {
		this.productReceiveRate = productReceiveRate;
	}

	public BigDecimal getProductGreenhornReceiveRate() {
		return productGreenhornReceiveRate;
	}

	public void setProductGreenhornReceiveRate(
			BigDecimal productGreenhornReceiveRate) {
		this.productGreenhornReceiveRate = productGreenhornReceiveRate;
	}

	public String getProductFlag() {
		return productFlag;
	}

	public void setProductFlag(String productFlag) {
		this.productFlag = productFlag;
	}

	public String getIfPurchaseProduct() {
		return ifPurchaseProduct;
	}

	public void setIfPurchaseProduct(String ifPurchaseProduct) {
		this.ifPurchaseProduct = ifPurchaseProduct;
	}

	public BigDecimal getProductRaiseProgress() {
		return productRaiseProgress;
	}

	public void setProductRaiseProgress(BigDecimal productRaiseProgress) {
		this.productRaiseProgress = productRaiseProgress;
	}

	public String getProductRepaymentType() {
		return productRepaymentType;
	}

	public void setProductRepaymentType(String productRepaymentType) {
		this.productRepaymentType = productRepaymentType;
	}

	public String getProductPolicyNo() {
		return productPolicyNo;
	}

	public void setProductPolicyNo(String productPolicyNo) {
		this.productPolicyNo = productPolicyNo;
	}

	public int getProductFirstMonthDay() {
		return productFirstMonthDay;
	}

	public void setProductFirstMonthDay(int productFirstMonthDay) {
		this.productFirstMonthDay = productFirstMonthDay;
	}

	public String getLoanProductId() {
		return loanProductId;
	}

	public void setLoanProductId(String loanProductId) {
		this.loanProductId = loanProductId;
	}

	public String getIsIpsUser() {
		return isIpsUser;
	}

	public void setIsIpsUser(String isIpsUser) {
		this.isIpsUser = isIpsUser;
	}

	public String getIpsAcctNo() {
		return ipsAcctNo;
	}

	public void setIpsAcctNo(String ipsAcctNo) {
		this.ipsAcctNo = ipsAcctNo;
	}



}
