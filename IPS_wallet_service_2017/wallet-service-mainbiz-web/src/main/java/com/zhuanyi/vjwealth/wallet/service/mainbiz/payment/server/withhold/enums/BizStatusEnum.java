package com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.enums;

/**
 * 交易状态枚举值
 * 
 * @author jiangkaijun
 * 
 */
public enum BizStatusEnum {


	BIZ_FAILED("biz_fail", "交易失败"),

	BIZ_SUCCESS("biz_success", "交易成功");

	// 交易类型
	private final String value;
	// 交易类型描述
	private final String description;

	BizStatusEnum(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
	
	public static String getBizResult(Boolean result){
		
		return result?BIZ_SUCCESS.value:BIZ_FAILED.value;
	}
	
	
}
