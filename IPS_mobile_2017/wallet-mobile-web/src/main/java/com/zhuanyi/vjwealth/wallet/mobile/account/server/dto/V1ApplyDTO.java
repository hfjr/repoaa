package com.zhuanyi.vjwealth.wallet.mobile.account.server.dto;

import com.fab.core.entity.dto.BaseDTO;

public class V1ApplyDTO extends BaseDTO {

	/**
	 * @Fields serialVersionUID : (用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;
	// v1理财真实剩余份额
	private java.math.BigDecimal v1RealApplyRemainAmount;
	// 申购阀值(低于这个值时,置空mock值为0)
	private java.math.BigDecimal applyThresholdLimit;
	
	
	public java.math.BigDecimal getV1RealApplyRemainAmount() {
		return v1RealApplyRemainAmount;
	}

	public void setV1RealApplyRemainAmount(
			java.math.BigDecimal v1RealApplyRemainAmount) {
		this.v1RealApplyRemainAmount = v1RealApplyRemainAmount;
	}

	public java.math.BigDecimal getApplyThresholdLimit() {
		return applyThresholdLimit;
	}

	public void setApplyThresholdLimit(java.math.BigDecimal applyThresholdLimit) {
		this.applyThresholdLimit = applyThresholdLimit;
	}

}
