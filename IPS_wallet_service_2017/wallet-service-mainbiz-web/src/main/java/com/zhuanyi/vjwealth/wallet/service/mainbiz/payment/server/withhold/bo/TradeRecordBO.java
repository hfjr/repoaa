package com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 交易记录表DTO
 * @author jiangkaijun
 *
 */
public class TradeRecordBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 主键 */
	private String id;

	/** 第三方给定的交易号 */
	private String tradeNo;

	/** 总金额 */
	private BigDecimal totalPrice;

	/** 单价 */
	private BigDecimal price;

	/** 手续费 */
	private BigDecimal fee;

	/** 交易状态 */
	private String tradeStatus;
	
	/** 业务订单回调状态*/
	private String relBizOrderStatus;

	/** 关联业务订单号 */
	private String relBizOrderNo;
	
	/** 消息(主要是异常信息)*/
	private String relBizMessage;
	
	private String createBy;
	
	private Timestamp createDate;
	
	private String updateBy;
	
	private Timestamp updateDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getRelBizOrderNo() {
		return relBizOrderNo;
	}

	public void setRelBizOrderNo(String relBizOrderNo) {
		this.relBizOrderNo = relBizOrderNo;
	}


	public String getRelBizOrderStatus() {
		return relBizOrderStatus;
	}

	public void setRelBizOrderStatus(String relBizOrderStatus) {
		this.relBizOrderStatus = relBizOrderStatus;
	}

	public String getRelBizMessage() {
		return relBizMessage;
	}

	public void setRelBizMessage(String relBizMessage) {
		this.relBizMessage = relBizMessage;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

}
