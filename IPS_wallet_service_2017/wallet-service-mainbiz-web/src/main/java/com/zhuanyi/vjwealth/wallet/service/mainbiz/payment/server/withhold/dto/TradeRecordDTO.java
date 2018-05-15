package com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易记录表DTO
 * @author jiangkaijun
 *
 */
public class TradeRecordDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 主键 */
	private String id;

	/** 第三方给定的交易号 */
	private String tradeNo;

	/** 总金额 */
	private Double totalPrice;

	/** 单价 */
	private Double price;

	/** 手续费 */
	private Double fee;

	/** 交易状态 */
	private String tradeStatus;
	
	/** 业务订单回调状态*/
	private String relBizOrderStatus;

	/** 关联业务订单号 */
	private String relBizOrderNo;

	/** 消息(主要是异常信息)*/
	private String message;

	public TradeRecordDTO() {

	}

	public TradeRecordDTO(String tradeNo, Double totalPrice, Double price,
			Double fee, String tradeStatus, String relBizOrderNo,
			Date createDate, String createBy, Date updateDate, String updateBy,String relBizOrderStatus) {
		this.tradeNo = tradeNo;
		this.totalPrice = totalPrice;
		this.price = price;
		this.fee = fee;
		this.tradeStatus = tradeStatus;
		this.relBizOrderNo = relBizOrderNo;
		this.relBizOrderStatus = relBizOrderStatus;
	}

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

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
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

}
