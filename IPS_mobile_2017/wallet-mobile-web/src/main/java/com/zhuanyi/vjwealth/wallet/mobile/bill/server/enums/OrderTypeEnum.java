package com.zhuanyi.vjwealth.wallet.mobile.bill.server.enums;

/**
 * 账户的交易类型
 * @author wangzf
 *
 */
public enum OrderTypeEnum {
	
	BATCH_APPLY("batch_apply","批量申购"),
	WITHDRAW_EA("withdraw_ea","实时提现"),
	WITHDRAW_TA("withdraw_ta","实时提现"),
	WITHDRAW_MA("withdraw_ma","主账户提现"),
	RECHARGE_MA("recharge_ma","主账户充值"),
	APPLY_MA_TO_EA("apply_ma_to_ea","总账户申购e账户"),
	APPLY_MA_TO_TA("apply_ma_to_ta","总账户申购ta账户"),
	APPLY_MA_TO_V1("apply_ma_to_v1","总账户申购v+账户"),
	TRANSFER_EA_TO_MA("transfer_ea_to_ma","e账户转账总账户"),
	TRANSFER_TA_TO_MA("transfer_ta_to_ma","ta账户转账总账户"),
	TRANSFER_V1_TO_MA("transfer_v1_to_ma","v+账户转账总账户"),
	APPLY_MA_TO_RF("apply_ma_to_rf","购买定期理财"),
	TRANSFER_LF_TO_LA("transfer_lf_to_la","归还小金鱼"),
	APPLY_LOAN("apply_loan","白领专享借款"),
	REPAY_LOAN("repay_loan","白领专享还款"),

	DUE_REPAY_LTB_LOAN("due_repay_ltb_loan","流通宝到期还款"),
	EARLY_REPAY_LTB_LOAN("early_repay_ltb_loan","流通宝提前还款"),
	DUE_REPAY_CANA_LOAN("due_repay_cana_loan","锦囊还款"),
	
	RECHARGE_CASHBACK("recharge_cashback","推荐返现"),
	RECHARGE_RP("recharge_rp","红包"),

	DUE_REPAY_WAGE_LOAN("due_repay_wage_loan","工资先享到期还款(余额)"),
	DUE_REPAY_WAGE_LOAN_WITHHOLD("due_repay_wage_loan_withhold","工资先享到期还款(银行卡)"),
	EARLY_REPAY_WAGE_LOAN("early_repay_wage_loan","工资先享提前还款(余额)"),
	EARLY_REPAY_WAGE_LOAN_WITHHOLD("early_repay_wage_loan_withhold","工资先享提前还款(银行卡)"),

	EARLY_REPAY_BANKCARD_WITHHOLD("early_repay_bankcard_withhold","工资先享银行卡提前还款汇总订单");



	
	private String value;
	private String desc;
	
	OrderTypeEnum(String value,String desc){
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
