package com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants;

import java.util.HashMap;
import java.util.Map;

public class LoanOrderStatusConstant {

	public static final String REPAY_IN = "R";//还款中
	public static final String REPAY_END = "F";//还清
	public static final String REPAY_CLEAR = "ST";//强制结清
	public static final String REPAY_OVERDUE = "C_O";//逾期
	public static final String REPAY_OVERDUE_END = "C_F";//补缴

	//C:授信审核中；C_F：授信失败；C_S：授信成功；G：放款审核中；G_F：放款失败；R：还款中（放款成功）；C_A：授信本地通过；C_R：授信本地拒绝；Z：资料完善中
	public static final String ORDER_STATUS_A = "A";//白领专享申请初始化
	public static final String ORDER_STATUS_C = "C";//公积金信息本地审核中
	public static final String ORDER_STATUS_C_R = "C_R";//公积金信息本地审核拒绝
	public static final String ORDER_STATUS_C_A = "C_A";//公积金信息本地审核通过（资料交由小赢审核）
	public static final String ORDER_STATUS_C_F = "C_F";//授信额度审核失败
	public static final String ORDER_STATUS_C_S = "C_S";//授信额度审核成功
	public static final String ORDER_STATUS_Z = "Z";//申请贷款后资料完善中
	public static final String ORDER_STATUS_Z_P = "Z_P";//实名认证中
	public static final String ORDER_STATUS_Z_F = "Z_F";//实名认证失败
	public static final String ORDER_STATUS_G = "G";//放款审核中
	public static final String ORDER_STATUS_G_F = "G_F";//放款审核失败
	public static final String ORDER_STATUS_R = "R";//还款中
	public static final String ORDER_STATUS_C_O = "C_O";//逾期
	public static final String ORDER_STATUS_F = "F";//还款完成



	private static final Map<String,String> loanStatusMap = new HashMap<String,String>();

	private static final Map<String,String> loanStatusStrMap = new HashMap<String,String>();

	static {
		loanStatusMap.put(REPAY_IN, "repaymentIn");
		loanStatusMap.put(REPAY_END, "repaymentEnd");
		loanStatusMap.put(REPAY_OVERDUE, "overdue");
		loanStatusMap.put(REPAY_CLEAR, "repaymentEnd");
		loanStatusMap.put(REPAY_OVERDUE_END, "repaymentEnd");


		loanStatusStrMap.put(ORDER_STATUS_A,"授信审核中");
		loanStatusStrMap.put(ORDER_STATUS_C,"公积金审核中");
		loanStatusStrMap.put(ORDER_STATUS_C_R,"公积金审核失败");
		loanStatusStrMap.put(ORDER_STATUS_C_A,"授信审核中");
		loanStatusStrMap.put(ORDER_STATUS_C_F,"授信审核失败");
		loanStatusStrMap.put(ORDER_STATUS_C_S,"授信审核成功");
		loanStatusStrMap.put(ORDER_STATUS_Z,"资料完善中");
		loanStatusStrMap.put(ORDER_STATUS_Z_P,"实名认证中");
		loanStatusStrMap.put(ORDER_STATUS_Z_F,"实名认证失败");
		loanStatusStrMap.put(ORDER_STATUS_G,"放款审核中");
		loanStatusStrMap.put(ORDER_STATUS_G_F,"放款审核失败");
		loanStatusStrMap.put(ORDER_STATUS_R,"放款成功");//放款成功后的状态都展示 申请成功
		loanStatusStrMap.put(ORDER_STATUS_C_O,"放款成功");//放款成功后的状态都展示 申请成功
		loanStatusStrMap.put(ORDER_STATUS_F,"放款成功");//放款成功后的状态都展示 申请成功

	}

	public static String getValue(String key){
		return loanStatusMap.get(key);
	}

	public static String getStatusStr(String key){
		return loanStatusStrMap.get(key);
	}


}
