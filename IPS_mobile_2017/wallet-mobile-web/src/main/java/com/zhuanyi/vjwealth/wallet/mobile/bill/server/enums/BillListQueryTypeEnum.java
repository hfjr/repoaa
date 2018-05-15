package com.zhuanyi.vjwealth.wallet.mobile.bill.server.enums;

/**
 * 查询账单列表时，筛选条件
 * @author wangzf
 *
 */
public enum BillListQueryTypeEnum {
	
	ALL("all","全部"),
	RECHARGE("recharge","充值"),
	PCRECHARGE("pc_recharge_ma","充值"),
	WAGE("wage","工资"),
	EA("ea","e账户"),
	TA("ta","e账户"),
	WITHDRAW("withdraw","提现"),
	REGULARMONEY("regularMoney","定期理财"),
	INCOME("income","收益"),
	FROZENALL("frozenAll","全部冻结金额"),
	LOAN("loan","借款"),
	REPAY("repay","还款"),
	CASHBACK("cashback","推荐返现"),
	RP("rp","红包"),
	BANKCARDWITHHOLD("bankcardWithhold","工资定存");
	
	private final String value;
	private final String desc;
	
	BillListQueryTypeEnum(String value,String desc){
		this.value = value;
		this.desc = desc;
	}
	public String getValue() {
		return value;
	}
	public String getDesc() {
		return desc;
	}

}
