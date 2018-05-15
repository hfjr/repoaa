package com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.enums;

/**
 * 交易状态枚举值
 * 
 * @author jiangkaijun
 * 
 */
public enum TradeStatusEnum {

	TRADE_PROCESS("trade_process", "交易处理中"),

	TRADE_SUCCESS("trade_success", "交易成功"),

	TRADE_FAILED("trade_failed", "交易失败");

	// 交易类型
	private final String value;
	// 交易类型描述
	private final String description;

	TradeStatusEnum(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

}
