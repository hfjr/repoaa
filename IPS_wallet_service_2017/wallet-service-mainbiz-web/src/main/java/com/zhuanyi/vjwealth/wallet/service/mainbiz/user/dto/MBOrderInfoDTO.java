package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MBOrderInfoDTO {

	private String orderNo;// 订单号
	private String userId;// 用户id

	private String title; // 标题
	private BigDecimal totalPrice; // 总金额
	private BigDecimal price; // 实际金额
	private String tradeAccountCardId; // 交易银行ID
	private String productId; // 交易银行ID
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	private String orderType; // 订单类型
	private String orderStatus; // 订单状态
	private String orderToken; // 订单状态
	private String ipsNo;// ips存管账号
	private String tradeId; // 订单状态
	private String batchNo;//批次号
	public String getIpsNo() {
		return ipsNo;
	}

	public void setIpsNo(String ipsNo) {
		this.ipsNo = ipsNo;
	}

	private Date timeFinancialApproval; // 确认资金时间
	private String relOrderNo;//关联订单号
    private BigDecimal activityAmount;//活动奖励金额， 红包金额
    private BigDecimal virtualAmount;//代金（虚拟额度）
    private String couponsId; // 加息券id
	private Date createDate;
	private String createBy;
	private Date updateDate;
	private String updateBy;
	private BigDecimal fee;
	 


	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getCouponsId() {
		return couponsId;
	}

	public void setCouponsId(String couponsId) {
		this.couponsId = couponsId;
	}

	public static final String ORDER_TYPE_TRANSFER_EA_TO_MA = "transfer_ea_to_ma";
	public static final String ORDER_TYPE_TRANSFER_TA_TO_MA = "transfer_ta_to_ma";
	public static final String ORDER_TYPE_TRANSFER_V1_TO_MA = "transfer_v1_to_ma";
	public static final String ORDER_TYPE_TRANSFER_RF_TO_MA = "transfer_rf_to_ma";

	public static final String ORDER_TYPE_APPLY_MA_TO_V1 = "apply_ma_to_v1";
	public static final String ORDER_TYPE_APPLY_MA_TO_EA = "apply_ma_to_ea";
	public static final String ORDER_TYPE_APPLY_MA_TO_TA = "apply_ma_to_ta";
	public static final String ORDER_TYPE_APPLY_MA_TO_RF = "apply_ma_to_rf";

    public static final String ORDER_TYPE_RECHARGE_RP = "recharge_rp";

	

	public static final String ORDER_TYPE_RECHARGE_MA = "recharge_ma";

	public static final String ORDER_TYPE_WITHDRAW_EA = "withdraw_ea";
	public static final String ORDER_TYPE_WITHDRAW_TA = "withdraw_ta";
	public static final String ORDER_TYPE_WITHDRAW_MA = "withdraw_ma";
	
	
	public static final String ORDER_STATUS_OPEN_ACCOUNT_CONFIRM = "open_account_confirm";
	public static final String ORDER_STATUS_OPEN_ACCOUNT_NOCONFIRM = "open_account_noconfirm";
	public static final String ORDER_STATUS_OPEN_ACCOUNT_FAIl = "open_account_fail";
	public static final String ORDER_STATUS_OPEN_ACCOUNT_CHECK = "open_account_check";

	public static final String ORDER_STATUS_TRANSFER_EA_TO_MA_CONFIRM = "transfer_ea_to_ma_confirm";
	public static final String ORDER_STATUS_TRANSFER_TA_TO_MA_CONFIRM = "transfer_ta_to_ma_confirm";
	public static final String ORDER_STATUS_TRANSFER_V1_TO_MA_NOCONFIRM = "transfer_v1_to_ma_noconfirm";
	
	public static final String ORDER_STATUS_TRANSFER_RF_TO_MA_CONFIRM = "transfer_rf_to_ma_confirm";
	
	public static final String ORDER_STATUS_WITHDRAW_EA_ = "withdraw_ea_";
	public static final String ORDER_STATUS_WITHDRAW_TA_ = "withdraw_ta_";
	public static final String ORDER_STATUS_WITHDRAW_MA_NOCONFIRM = "withdraw_ma_noconfirm";
	public static final String ORDER_STATUS_WITHDRAW_MA_CONFIRM = "withdraw_ma_confirm";

	public static final String ORDER_STATUS_APPLY_MA_TO_V1_NOCONFIRM = "apply_ma_to_v1_noconfirm";
	public static final String ORDER_STATUS_APPLY_MA_TO_EA_NOCONFIRM = "apply_ma_to_ea_noconfirm";
	public static final String ORDER_STATUS_APPLY_MA_TO_TA_NOCONFIRM = "apply_ma_to_ta_noconfirm";
	public static final String ORDER_STATUS_APPLY_MA_TO_RF_NOCONFIRM = "apply_ma_to_rf_noconfirm";

	public static final String ORDER_STATUS_APPLY_MA_TO_RF_CONFIRM = "apply_ma_to_rf_confirm";

	public static final String ORDER_STATUS_APPLY_MA_TO_RF_CANCEL = "apply_ma_to_rf_cancel";

	public static final String ORDER_STATUS_RECHARGE_MA_CONFIRM = "recharge_ma_confirm";
    public static final String ORDER_STATUS_RECHARGE_RP_CONFIRM = "recharge_rp_confirm";

    public static final String ORDER_STATUS_RECHARGE_MA_CONFIRM_DISPOSE = "recharge_ma_confirm_dispose";
	
	public static final String ORDER_STATUS_RECHARGE_MA_CONFIRM_FAIL = "recharge_ma_confirm_fail";

	public static final String ORDER_TYPE_TRANSFER_LF_TO_LA = "transfer_lf_to_la";
	public static final String ORDER_TYPE_APPLY_LA_TO_LF = "apply_la_to_lf";
	public static final String ORDER_STATUS_TRANSFER_LF_TO_LA_CONFIRM = "transfer_lf_to_la_confirm";
	public static final String ORDER_STATUS_APPLY_LA_TO_LF_NOCONFIRM = "apply_la_to_lf_noconfirm";
	public static final String ORDER_STATUS_APPLY_LA_TO_LF_CONFIRM = "apply_la_to_lf_confirm";
	public static final String ORDER_STATUS_APPLY_LA_TO_LF_CANCEL = "apply_la_to_lf_cancel";
	
	public static final String ORDER_TYPE_APPLY_LN = "apply_ln";
	public static final String ORDER_TYPE_APPLY_LA = "apply_la";
	public static final String ORDER_STATUS_APPLY_LN_CONFIRM = "apply_ln_confirm";
	public static final String ORDER_STATUS_APPLY_LA_CONFIRM = "apply_la_confirm";
	
	public static final String ORDER_TYPE_XY_LOAN = "apply_loan";
	public static final String ORDER_TYPE_XY_REPAY = "repay_loan";
	public static final String ORDER_TYPE_XY_LOAN_CONFIRM = "apply_loan_confirm";
	public static final String ORDER_TYPE_XY_REPAY_CONFIRM = "repay_loan_confirm";
	


	//cana到期还款
	public static final String ORDER_TYPE_PLEDGE_DUE_REPAY_CANA = "due_repay_cana_loan"; //cana还款
	public static final String ORDER_TYPE_CANA_AUTO_REPAY_PROCESS = "early_auto_repay_cana_loan_process";//cana还款处理中
	public static final String ORDER_TYPE_CANA_AUTO_REPAY_CONFIRM = "early_auto_repay_cana_loan_confirm";//cana还款确认

	//流通宝贷款申请
	public static final String ORDER_TYPE_PLEDGE_LOAN = "apply_ltb_loan";//流通宝-申请贷款
	public static final String ORDER_STATUS_PLEDGE_LOAN_CONFIRM = "apply_ltb_loan_confirm";//流通宝-申请贷款确认
	public static final String ORDER_STATUS_PLEDGE_LOAN_PROCESS = "apply_ltb_loan_process";//流通宝-申请贷款中
	public static final String ORDER_STATUS_PLEDGE_LOAN_FAIL = "apply_ltb_loan_fail";//流通宝-申请贷款中
	//流通宝提前还款
	public static final String ORDER_TYPE_PLEDGE_REPAY = "early_repay_ltb_loan"; //流通宝-提前还款
	public static final String ORDER_STATUS_PLEDGE_REPAY_PROCESS = "early_repay_ltb_loan_process";//流通宝-提前还款处理中
	public static final String ORDER_STATUS_PLEDGE_REPAY_CONFIRM = "early_repay_ltb_loan_confirm"; //流通宝-提前还款确认
	//流通宝到期还款
	public static final String ORDER_TYPE_PLEDGE_DUE_REPAY = "due_repay_ltb_loan"; //流通宝到期还款


	public static final String ORDER_STATUS_PLEDGE_DUE_REPAY_PROCESS = "due_repay_ltb_loan_process";//流通宝到期还款处理中
	public static final String ORDER_STATUS_PLEDGE_DUE_REPAY_CONFIRM = "due_repay_ltb_loan_confirm";//流通宝到期还款确认

	//从ma还款到ln账户
	public static final String ORDER_TYPE_MAFROZEN_TO_LN = "transfer_maFrozen_to_ln"; //从ma的冻结金额转账到ln账户
	public static final String ORDER_TYPE_SUBSIDY_TO_LN = "subsidy_to_ln";         //公司补贴还款利息
	public static final String ORDER_STATUS_MAFROZEN_TO_LN_CONFIRM = "transfer_maFrozen_to_ln_confirm"; //从ma的冻结金额转账到ln账户 确认
	public static final String ORDER_STATUS_SUBSIDY_TO_LN_CONFIRM = "subsidy_to_ln_confirm";//公司补贴还款利息 确认
	


	//工资先享 - 贷款申请
	public static final String ORDER_TYPE_WAGE_ADVANCE_LOAN = "apply_wage_loan";//工资先享-申请贷款
	public static final String ORDER_STATUS_WAGE_ADVANCE_LOAN_CONFIRM = "apply_wage_loan_confirm";//工资先享-申请贷款确认
	public static final String ORDER_STATUS_WAGE_ADVANCE_LOAN_PROCESS = "apply_wage_loan_process";//工资先享-申请贷款中
	public static final String ORDER_STATUS_WAGE_ADVANCE_LOAN_FAIL = "apply_wage_loan_fail";//工资先享-申请贷款失败

	//工资先享 - 提前还款（余额）
	public static final String ORDER_TYPE_WAGE_REPAY = "early_repay_wage_loan"; //工资先享-提前还款(余额)
	public static final String ORDER_STATUS_WAGE_REPAY_PROCESS = "early_repay_wage_loan_process";//工资先享-提前还款处理中
	public static final String ORDER_STATUS_WAGE_REPAY_CONFIRM = "early_repay_wage_loan_confirm"; //工资先享-提前还款确认

	//工资先享 - 提前还款(银行卡)
	public static final String ORDER_TYPE_WAGE_REPAY_BANKCARD = "early_repay_wage_loan_withhold";//工资先享-提前还款(银行卡)
	public static final String ORDER_STATUS_WAGE_REPAY_BANKCARD_INIT = "early_repay_wage_loan_withhold_init";//工资先享-提前还款初始化(银行卡)
	public static final String ORDER_STATUS_WAGE_REPAY_BANKCARD_PROCESS = "early_repay_wage_loan_withhold_process";//工资先享-提前还款处理中(银行卡)
	public static final String ORDER_STATUS_WAGE_REPAY_BANKCARD_CONFIRM = "early_repay_wage_loan_withhold_confirm";//工资先享-提前还款成功(银行卡)
	public static final String ORDER_STATUS_WAGE_REPAY_BANKCARD_FAIL = "early_repay_wage_loan_withhold_fail";//工资先享-提前还款失败(银行卡)

	//工资先享 - 提前还款银行卡扣款(银行卡一次性扣款汇总记录，在页面不展示)
	public static final String ORDER_TYPE_EARLY_BANKCARD_WITHHOLD = "early_repay_bankcard_withhold";//工资先享-银行卡扣款
	public static final String ORDER_STATUS_EARLY_BANKCARD_WITHHOLD_INIT = "early_repay_bankcard_withhold_init";//工资先享-银行卡扣款初始化
	public static final String ORDER_STATUS_EARLY_BANKCARD_WITHHOLD_PROCESS = "early_repay_bankcard_withhold_process";//工资先享-银行卡扣款初始化
	public static final String ORDER_STATUS_EARLY_BANKCARD_WITHHOLD_CONFIRM = "early_repay_bankcard_withhold_confirm";//工资先享-银行卡扣款成功
	public static final String ORDER_STATUS_EARLY_BANKCARD_WITHHOLD_FAIL = "early_repay_bankcard_withhold_fail";//工资先享-银行卡扣款失败

	//工资先享 - 到期还款(余额)
	public static final String ORDER_TYPE_WAGE_DUE_REPAY = "due_repay_wage_loan"; //工资先享-到期还款
	public static final String ORDER_STATUS_WAGE_DUE_REPAY_PROCESS = "due_repay_wage_loan_process";//工资先享-到期还款处理中
	public static final String ORDER_STATUS_WAGE_DUE_REPAY_CONFIRM = "due_repay_wage_loan_confirm"; //工资先享-到期还款确认


	//工资先享 - 到期还款(银行卡)
	public static final String ORDER_TYPE_WAGE_DUE_REPAY_BANKCARD = "due_repay_wage_loan_withhold";//工资先享-提前还款(银行卡)
	public static final String ORDER_STATUS_WAGE_DUE_REPAY_BANKCARD_INIT = "due_repay_wage_loan_withhold_init";//工资先享-提前还款初始化(银行卡)
	public static final String ORDER_STATUS_WAGE_DUE_REPAY_BANKCARD_PROCESS = "due_repay_wage_loan_withhold_process";//工资先享-提前还款处理中(银行卡)
	public static final String ORDER_STATUS_WAGE_DUE_REPAY_BANKCARD_CONFIRM = "due_repay_wage_loan_withhold_confirm";//工资先享-提前还款成功(银行卡)
	public static final String ORDER_STATUS_WAGE_DUE_REPAY_BANKCARD_FAIL = "due_repay_wage_loan_withhold_fail";//工资先享-提前还款失败(银行卡)

	//工资先享 - 到期还款银行卡扣款(银行卡一次性扣款汇总记录，在页面不展示)
	public static final String ORDER_TYPE_DUE_BANKCARD_WITHHOLD = "due_repay_bankcard_withhold";//工资先享-银行卡扣款
	public static final String ORDER_STATUS_DUE_BANKCARD_WITHHOLD_INIT = "due_repay_bankcard_withhold_init";//工资先享-银行卡扣款初始化
	public static final String ORDER_STATUS_DUE_BANKCARD_WITHHOLD_PROCESS = "due_repay_bankcard_withhold_process";//工资先享-银行卡扣款初始化
	public static final String ORDER_STATUS_DUE_BANKCARD_WITHHOLD_CONFIRM = "due_repay_bankcard_withhold_confirm";//工资先享-银行卡扣款成功
	public static final String ORDER_STATUS_DUE_BANKCARD_WITHHOLD_FAIL = "due_repay_bankcard_withhold_fail";//工资先享-银行卡扣款失败



	//工资计划 - 跑批扣款(银行卡)
	public static final String ORDER_TYPE_SALARY_PLAN_BANKCARD_WITHHOLD = "salary_plan_bankcard_withhold";//工资计划-代扣(银行卡)
	public static final String ORDER_STATUS_SALARY_PLAN_BANKCARD_WITHHOLD_INIT = "salary_plan_bankcard_withhold_init";//工资计划-银行卡扣款初始化
	public static final String ORDER_STATUS_SALARY_PLAN_BANKCARD_WITHHOLD_PROCESS = "salary_plan_bankcard_withhold_process";//工资计划-银行卡扣款初始化
	public static final String ORDER_STATUS_SALARY_PLAN_BANKCARD_WITHHOLD_CONFIRM = "salary_plan_bankcard_withhold_confirm";//工资计划-银行卡扣款成功
	public static final String ORDER_STATUS_SALARY_PLAN_BANKCARD_WITHHOLD_FAIL = "salary_plan_bankcard_withhold_fail";//工资计划-银行卡扣款失败





	//vj自营信贷（京东） - 贷款申请
	public static final String ORDER_TYPE_VJ_LOAN = "apply_vj_loan";//vj自营信贷（京东）-申请贷款
	public static final String ORDER_STATUS_VJ_LOAN_CONFIRM = "apply_vj_loan_confirm";//vj自营信贷（京东）-申请贷款确认
//	public static final String ORDER_STATUS_VJ_LOAN_PROCESS = "apply_vj_loan_process";//vj自营信贷（京东）-申请贷款中
//	public static final String ORDER_STATUS_VJ_LOAN_FAIL = "apply_vj_loan_fail";//vj自营信贷（京东）-申请贷款失败

	//vj自营信贷（京东） - 到期还款(银行卡)
	public static final String ORDER_TYPE_VJ_DUE_REPAY_BANKCARD = "due_repay_vj_loan_withhold";//vj自营信贷（京东）-到期还款(银行卡)
	public static final String ORDER_STATUS_VJ_DUE_REPAY_BANKCARD_INIT = "due_repay_vj_loan_withhold_init";//vj自营信贷（京东）-到期还款初始化(银行卡)
	public static final String ORDER_STATUS_VJ_DUE_REPAY_BANKCARD_PROCESS = "due_repay_vj_loan_withhold_process";//vj自营信贷（京东）-到期还款处理中(银行卡)
	public static final String ORDER_STATUS_VJ_DUE_REPAY_BANKCARD_CONFIRM = "due_repay_vj_loan_withhold_confirm";//vj自营信贷（京东）-到期还款成功(银行卡)
	public static final String ORDER_STATUS_VJ_DUE_REPAY_BANKCARD_FAIL = "due_repay_vj_loan_withhold_fail";//vj自营信贷（京东）-到期还款失败(银行卡)

	//vj自营信贷（京东） - 到期还款银行卡扣款(银行卡一次性扣款汇总记录，在页面不展示)
	public static final String ORDER_TYPE_VJ_DUE_BANKCARD_WITHHOLD = "due_repay_vj_bankcard_withhold";//vj自营信贷（京东）-银行卡扣款
	public static final String ORDER_STATUS_VJ_DUE_BANKCARD_WITHHOLD_INIT = "due_repay_vj_bankcard_withhold_init";//vj自营信贷（京东）-银行卡扣款初始化
	public static final String ORDER_STATUS_VJ_DUE_BANKCARD_WITHHOLD_PROCESS = "due_repay_vj_bankcard_withhold_process";//vj自营信贷（京东）-银行卡扣款初始化
	public static final String ORDER_STATUS_VJ_DUE_BANKCARD_WITHHOLD_CONFIRM = "due_repay_vj_bankcard_withhold_confirm";//vj自营信贷（京东）-银行卡扣款成功
	public static final String ORDER_STATUS_VJ_DUE_BANKCARD_WITHHOLD_FAIL = "due_repay_vj_bankcard_withhold_fail";//vj自营信贷（京东）-银行卡扣款失败
	
	//PICC自营信贷 - 贷款申请
	public static final String ORDER_TYPE_PICC_LOAN = "apply_picc_loan";//picc自营信贷-申请贷款
	public static final String ORDER_STATUS_PICC_LOAN_CONFIRM = "apply_picc_loan_confirm";//picc自营信贷-申请贷款确认
	//PICC自营信贷 - 到期还款(银行卡)
	public static final String ORDER_TYPE_PICC_DUE_REPAY_BANKCARD = "picc_due_repay_loan_withhold";//picc自营信贷-到期还款(银行卡)
	public static final String ORDER_STATUS_PICC_DUE_REPAY_BANKCARD_INIT = "picc_due_repay_loan_withhold_init";//picc自营信贷-到期还款初始化(银行卡)
	public static final String ORDER_STATUS_PICC_DUE_REPAY_BANKCARD_PROCESS = "picc_due_repay_loan_withhold_process";//picc自营信贷-到期还款处理中(银行卡)
	public static final String ORDER_STATUS_PICC_DUE_REPAY_BANKCARD_CONFIRM = "picc_due_repay_loan_withhold_confirm";//picc自营信贷-到期还款成功(银行卡)
	public static final String ORDER_STATUS_PICC_DUE_REPAY_BANKCARD_FAIL = "picc_due_repay_loan_withhold_fail";//picc自营信贷-到期还款失败(银行卡)
	//vj自营信贷 - 到期还款银行卡扣款(银行卡一次性扣款汇总记录，在页面不展示)
	public static final String ORDER_TYPE_PICC_DUE_BANKCARD_WITHHOLD = "picc_due_repay_bankcard_withhold";//picc自营信贷-银行卡扣款
	public static final String ORDER_STATUS_PICC_DUE_BANKCARD_WITHHOLD_INIT = "picc_due_repay_bankcard_withhold_init";//picc自营信贷-银行卡扣款初始化
	public static final String ORDER_STATUS_PICC_DUE_BANKCARD_WITHHOLD_PROCESS = "picc_due_repay_bankcard_withhold_process";//picc自营信贷-银行卡扣款初始化
	public static final String ORDER_STATUS_PICC_DUE_BANKCARD_WITHHOLD_CONFIRM = "picc_due_repay_bankcard_withhold_confirm";//picc自营信贷-银行卡扣款成功
	public static final String ORDER_STATUS_PICC_DUE_BANKCARD_WITHHOLD_FAIL = "picc_due_repay_bankcard_withhold_fail";//picc自营信贷-银行卡扣款失败
	
	
	public static MBOrderInfoDTO getTransferEaToMaConfirmOrder(String userId, String orderNo, BigDecimal amount) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_TRANSFER_EA_TO_MA);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_TRANSFER_EA_TO_MA_CONFIRM);
		orderInfoDTO.setTitle("转出到余额(存钱罐)");
		return orderInfoDTO;
	}

    public static MBOrderInfoDTO getTransferTaToMaConfirmOrder(String userId, String orderNo, BigDecimal amount) {
        MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
        orderInfoDTO.setOrderNo(orderNo);
        orderInfoDTO.setPrice(amount);
        orderInfoDTO.setUserId(userId);
        orderInfoDTO.setTotalPrice(amount);
        orderInfoDTO.setOrderType(ORDER_TYPE_TRANSFER_TA_TO_MA);
        orderInfoDTO.setOrderStatus(ORDER_STATUS_TRANSFER_TA_TO_MA_CONFIRM);
		orderInfoDTO.setTimeFinancialApproval(new Date());
        orderInfoDTO.setTitle("转出到余额(存钱罐)");
        return orderInfoDTO;
    }

	public static MBOrderInfoDTO getTransferV1ToMaConfirmOrder(String userId, String orderNo, BigDecimal amount) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_TRANSFER_V1_TO_MA);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_TRANSFER_V1_TO_MA_NOCONFIRM);
		orderInfoDTO.setTitle("v+账户转出到余额");
		return orderInfoDTO;
	}

	public static MBOrderInfoDTO getWithdrawEaOrder(String userId, String orderNo, BigDecimal amount, String transStatus,String accountCardId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setTradeAccountCardId(accountCardId);
		orderInfoDTO.setOrderType(ORDER_TYPE_WITHDRAW_EA);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_WITHDRAW_EA_ + transStatus);
		orderInfoDTO.setTitle("提现(存钱罐)");
		return orderInfoDTO;
	}

	public static MBOrderInfoDTO getWithdrawTaOrder(String userId, String orderNo, BigDecimal amount, String transStatus,String accountCardId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setTradeAccountCardId(accountCardId);
		orderInfoDTO.setOrderType(ORDER_TYPE_WITHDRAW_TA);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_WITHDRAW_TA_ + transStatus);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("提现(存钱罐)");
		return orderInfoDTO;
	}

	public static MBOrderInfoDTO getWithdrawMaOrder(String userId, String orderNo, BigDecimal amount,String accountCardId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setTradeAccountCardId(accountCardId);
		orderInfoDTO.setOrderType(ORDER_TYPE_WITHDRAW_MA);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_WITHDRAW_MA_NOCONFIRM);
		orderInfoDTO.setTitle("提现(余额)");
		return orderInfoDTO;
	}
	
	/**
	 * 
	 * @param userId
	 * @param orderNo
	 * @param amount
	 * @param accountCardId
	 * @return
	 */
	public static MBOrderInfoDTO withdrawIpsToCard(String userId, String orderNo, BigDecimal amount,BigDecimal fee) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount.subtract(fee));
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setFee(fee);
       
		orderInfoDTO.setOrderType(ORDER_TYPE_WITHDRAW_MA);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_WITHDRAW_MA_NOCONFIRM);
		orderInfoDTO.setTitle("提现(余额)");
		return orderInfoDTO;
	}

	public static MBOrderInfoDTO getApplyMaToV1Order(String userId, String orderNo, BigDecimal amount) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_APPLY_MA_TO_V1);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_APPLY_MA_TO_V1_NOCONFIRM);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("余额申购V+");
		return orderInfoDTO;
	}

	public static MBOrderInfoDTO getApplyMaToEaOrder(String userId, String orderNo, BigDecimal amount) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_APPLY_MA_TO_EA);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_APPLY_MA_TO_EA_NOCONFIRM);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("余额申购(存钱罐)");
		return orderInfoDTO;
	}

	public static MBOrderInfoDTO getApplyMaToTaOrder(String userId, String orderNo, BigDecimal amount) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_APPLY_MA_TO_TA);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_APPLY_MA_TO_TA_NOCONFIRM);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("余额申购(存钱罐)");
		return orderInfoDTO;
	}

	public static MBOrderInfoDTO getDoRechargeToMa(String userId, String orderNo, BigDecimal amount, String cardId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setTradeAccountCardId(cardId);
		orderInfoDTO.setOrderType(ORDER_TYPE_RECHARGE_MA);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_RECHARGE_MA_CONFIRM);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("充值");
		return orderInfoDTO;
	}
	
	public static MBOrderInfoDTO getDoRechargeToMaToDispose(String userId, String orderNo, BigDecimal amount, String cardId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setTradeAccountCardId(cardId);
		orderInfoDTO.setOrderType(ORDER_TYPE_RECHARGE_MA);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_RECHARGE_MA_CONFIRM_DISPOSE);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("充值中");
		return orderInfoDTO;
	}

	public static MBOrderInfoDTO getRechargeRpOrder(String userId, String orderNo, BigDecimal amount, String productId, String token, String tradeId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_RECHARGE_RP);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_RECHARGE_RP_CONFIRM);
		orderInfoDTO.setOrderToken(token);
		orderInfoDTO.setTradeId(tradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("红包充值");
		orderInfoDTO.setProductId(productId);
		return orderInfoDTO;
	}

	public static MBOrderInfoDTO getApplyMaToRfOrder(String userId, String orderNo, BigDecimal amount, String productId, String token, String tradeId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_APPLY_MA_TO_RF);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_APPLY_MA_TO_RF_NOCONFIRM);
		orderInfoDTO.setOrderToken(token);
		
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("余额申购(定期理财)");
		orderInfoDTO.setProductId(productId);
		return orderInfoDTO;
	}
	
	/**
	 * 此处添加加息券的id
	 * @param userId
	 * @param orderNo
	 * @param amount
	 * @param productId
	 * @param token
	 * @param tradeId
	 * @param couponsId
	 * @return
	 */
	public static MBOrderInfoDTO getApplyMaToRfOrderIps(String userId, String orderNo, BigDecimal amount, String productId, String token, String tradeId,String couponsId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_APPLY_MA_TO_RF);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_APPLY_MA_TO_RF_NOCONFIRM);
		orderInfoDTO.setOrderToken(token);
		orderInfoDTO.setCouponsId(couponsId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("余额申购(定期理财)");
		orderInfoDTO.setProductId(productId);
		return orderInfoDTO;
	}

    public static MBOrderInfoDTO getApplyMaToRfOrder(String userId, String orderNo, BigDecimal amount, BigDecimal activityAmount, String productId, String token, String tradeId) {
        MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
        orderInfoDTO.setActivityAmount(activityAmount);
        orderInfoDTO.setOrderNo(orderNo);
        orderInfoDTO.setPrice(amount.subtract(activityAmount));
        orderInfoDTO.setUserId(userId);
        orderInfoDTO.setTotalPrice(amount);
        orderInfoDTO.setOrderType(ORDER_TYPE_APPLY_MA_TO_RF);
        orderInfoDTO.setOrderStatus(ORDER_STATUS_APPLY_MA_TO_RF_NOCONFIRM);
        orderInfoDTO.setOrderToken(token);
        orderInfoDTO.setTradeId(tradeId);
        orderInfoDTO.setTimeFinancialApproval(new Date());
        orderInfoDTO.setTitle("余额申购(定期理财)");
        orderInfoDTO.setProductId(productId);
        return orderInfoDTO;
    }
	
	public static MBOrderInfoDTO getTransferRfToMaConfirmOrder(String userId, String orderNo, BigDecimal amount) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_TRANSFER_RF_TO_MA);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_TRANSFER_RF_TO_MA_CONFIRM);
		orderInfoDTO.setTitle("回款本金(定期理财)");
		return orderInfoDTO;
	}
	
	public static MBOrderInfoDTO getApplyLnOrder(String userId, String orderNo, BigDecimal amount, String productId, String token, String tradeId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_APPLY_LN);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_APPLY_LN_CONFIRM);
		orderInfoDTO.setOrderToken(token);
		orderInfoDTO.setTradeId(tradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("投资小金鱼-借款");
		orderInfoDTO.setProductId(productId);
		return orderInfoDTO;
	}

	public static MBOrderInfoDTO getApplyLaOrder(String userId, String orderNo, BigDecimal amount, String productId, String token, String tradeId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_APPLY_LA);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_APPLY_LA_CONFIRM);
		orderInfoDTO.setOrderToken(token);
		orderInfoDTO.setTradeId(tradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("投资小金鱼-从ln账户转到la账户");
		orderInfoDTO.setProductId(productId);
		return orderInfoDTO;
	}
	
	public static MBOrderInfoDTO getApplyLaToLfOrder(String userId, String orderNo, BigDecimal amount, String productId, String token, String tradeId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_APPLY_LA_TO_LF);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_APPLY_LA_TO_LF_NOCONFIRM);
		orderInfoDTO.setOrderToken(token);
		orderInfoDTO.setTradeId(tradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("使用小金鱼");
		orderInfoDTO.setProductId(productId);
		return orderInfoDTO;
	}
	
	public static MBOrderInfoDTO getTransferLfToLaConfirmOrder(String userId, String orderNo, BigDecimal amount) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_TRANSFER_LF_TO_LA);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_TRANSFER_LF_TO_LA_CONFIRM);
		orderInfoDTO.setTitle("回款本金(小金鱼)");
		return orderInfoDTO;
	}	
	
	public static MBOrderInfoDTO getXyLoanOrder(String userId, String orderNo, BigDecimal amount, String channelTradeId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_XY_LOAN);
		orderInfoDTO.setOrderStatus(ORDER_TYPE_XY_LOAN_CONFIRM);
//		orderInfoDTO.setOrderToken(token);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("提现(白领专享)");
//		orderInfoDTO.setProductId(productId);
		return orderInfoDTO;
	}
	
	public static MBOrderInfoDTO getXyRepaymentOrder(String userId, String orderNo, BigDecimal amount, String channelTradeId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_XY_REPAY);
		orderInfoDTO.setOrderStatus(ORDER_TYPE_XY_REPAY_CONFIRM);
//		orderInfoDTO.setOrderToken(token);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("还款(白领专享)");
//		orderInfoDTO.setProductId(productId);
		return orderInfoDTO;
	}
	
	public static MBOrderInfoDTO getPledgeLoanOrder(String userId, String orderNo, BigDecimal amount, String channelTradeId,String relOrderNo) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_PLEDGE_LOAN);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_PLEDGE_LOAN_PROCESS);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("流通宝借款处理中");
		orderInfoDTO.setRelOrderNo(relOrderNo);
		return orderInfoDTO;
	}
	
	public static MBOrderInfoDTO getPledgeLoanEarlyRepay(String userId, String orderNo, BigDecimal amount, String channelTradeId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_PLEDGE_REPAY);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_PLEDGE_REPAY_PROCESS);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("流通宝提前还款");
		return orderInfoDTO;
	}


	public static MBOrderInfoDTO getEnjoyWithLoanEarlyAutoRepay(String userId, String orderNo, BigDecimal amount, String channelTradeId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_PLEDGE_DUE_REPAY_CANA);
		orderInfoDTO.setOrderStatus(ORDER_TYPE_CANA_AUTO_REPAY_PROCESS);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("锦囊还款");
		return orderInfoDTO;
	}
	
	
	public static MBOrderInfoDTO getPledgeLoanDueRepay(String userId, String orderNo, BigDecimal amount, String channelTradeId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_PLEDGE_DUE_REPAY);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_PLEDGE_DUE_REPAY_PROCESS);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("流通宝到期还款");
		return orderInfoDTO;
	}


	public static MBOrderInfoDTO getMaFrozen2LnOrder(String userId, String orderNo, BigDecimal amount, String channelTradeId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_MAFROZEN_TO_LN);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_MAFROZEN_TO_LN_CONFIRM);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("余额还款转账");
		return orderInfoDTO;
	}
	
	public static MBOrderInfoDTO getMaAutoFrozen2LnOrderForCana(String userId, String orderNo, BigDecimal amount, String channelTradeId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_MAFROZEN_TO_LN);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_MAFROZEN_TO_LN_CONFIRM);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("余额还款转账");
		return orderInfoDTO;
	}

	public static MBOrderInfoDTO getSubsidy2LnOrder(String userId, String orderNo, BigDecimal amount, String channelTradeId,String relOrderNo) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_SUBSIDY_TO_LN);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_SUBSIDY_TO_LN_CONFIRM);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("公司还款利息补贴");
		orderInfoDTO.setRelOrderNo(relOrderNo);
		return orderInfoDTO;
	}
	

	//工资先享 -- 借款中订单
	public static MBOrderInfoDTO getWageAdvanceApplyOrder(String userId, String orderNo, BigDecimal amount, String channelTradeId,String relOrderNo) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_WAGE_ADVANCE_LOAN);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_WAGE_ADVANCE_LOAN_PROCESS);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("工资先享借款处理中");
		orderInfoDTO.setRelOrderNo(relOrderNo);
		return orderInfoDTO;
	}

	//工资先享 -- 提前还款（余额）
	public static MBOrderInfoDTO getWageAdvanceEarlyRepay(String userId, String orderNo, BigDecimal amount, String channelTradeId,String relOrderNo) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_WAGE_REPAY);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_WAGE_REPAY_PROCESS);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("工资先享提前还款(余额)");
		orderInfoDTO.setRelOrderNo(relOrderNo);
		return orderInfoDTO;
	}


	//工资先享 -- 银行卡扣款（提前还款）
	public static MBOrderInfoDTO getWageAdvanceEarlyRepaySingleWithhold(String userId, String orderNo, BigDecimal amount,String tradeAccountCardId, String channelTradeId,String relOrderNo) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setTradeAccountCardId(tradeAccountCardId);
		orderInfoDTO.setOrderType(ORDER_TYPE_WAGE_REPAY_BANKCARD);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_WAGE_REPAY_BANKCARD_INIT);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("工资先享提前还款(银行卡)");
		orderInfoDTO.setRelOrderNo(relOrderNo);
		return orderInfoDTO;
	}

	//工资先享 -- 银行卡扣款汇总(提前还款)
	public static MBOrderInfoDTO getEarlyBankCardWithhold(String userId, String orderNo, BigDecimal principal,BigDecimal amount,String tradeAccountCardId, String channelTradeId,String relOrderNo) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(principal);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setTradeAccountCardId(tradeAccountCardId);
		orderInfoDTO.setOrderType(ORDER_TYPE_EARLY_BANKCARD_WITHHOLD);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_EARLY_BANKCARD_WITHHOLD_INIT);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("银行卡扣款(汇总)");
		orderInfoDTO.setRelOrderNo(relOrderNo);
		return orderInfoDTO;
	}


	//工资先享 -- 到期还款(余额)
	public static MBOrderInfoDTO getWageAdvanceDueRepay(String userId, String orderNo, BigDecimal amount, String channelTradeId,String relOrderNo) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_WAGE_DUE_REPAY);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_WAGE_DUE_REPAY_PROCESS);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("工资先享到期还款(余额)");
		orderInfoDTO.setRelOrderNo(relOrderNo);
		return orderInfoDTO;
	}

	//工资先享 -- 到期还款银行卡扣款（到期还款）
	public static MBOrderInfoDTO getWageAdvanceDueRepaySingleWithhold(String userId, String orderNo, BigDecimal amount,String tradeAccountCardId, String channelTradeId,String relOrderNo) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setTradeAccountCardId(tradeAccountCardId);
		orderInfoDTO.setOrderType(ORDER_TYPE_WAGE_DUE_REPAY_BANKCARD);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_WAGE_DUE_REPAY_BANKCARD_INIT);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("工资先享到期还款(银行卡)");
		orderInfoDTO.setRelOrderNo(relOrderNo);
		return orderInfoDTO;
	}

	//工资先享 -- 银行卡扣款汇总(到期还款)
	public static MBOrderInfoDTO getDueBankCardWithhold(String userId, String orderNo, BigDecimal principal,BigDecimal amount,String tradeAccountCardId, String channelTradeId,String relOrderNo) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(principal);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setTradeAccountCardId(tradeAccountCardId);
		orderInfoDTO.setOrderType(ORDER_TYPE_DUE_BANKCARD_WITHHOLD);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_DUE_BANKCARD_WITHHOLD_INIT);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("银行卡扣款(汇总)");
		orderInfoDTO.setRelOrderNo(relOrderNo);
		return orderInfoDTO;
	}


	//工资计划 -- 银行卡扣款时，生成扣款初始化的订单
	public static MBOrderInfoDTO getSalaryPlanMBOrderInfoWithhold(String userId, String cardId, String planCode, BigDecimal amount,String orderNo) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setTradeAccountCardId(cardId);
		orderInfoDTO.setOrderType(ORDER_TYPE_SALARY_PLAN_BANKCARD_WITHHOLD);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_SALARY_PLAN_BANKCARD_WITHHOLD_INIT);
		orderInfoDTO.setTradeId(planCode);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("工资计划银行卡扣款");
		return orderInfoDTO;
	}




	//vj自营信贷 -- 借款成功订单
	public static MBOrderInfoDTO getVjSelfSupportLoanApplyOrder(String userId, String orderNo, BigDecimal amount, String channelTradeId,String relOrderNo) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setOrderType(ORDER_TYPE_VJ_LOAN);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_VJ_LOAN_CONFIRM);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("vj自营信贷(京东)借款");
		orderInfoDTO.setRelOrderNo(relOrderNo);
		return orderInfoDTO;
	}

	//vj自营信贷  -- 到期还款银行卡扣款（到期还款）
	public static MBOrderInfoDTO getVjSelfLoanDueRepaySingleWithhold(String userId, String orderNo, BigDecimal amount,String tradeAccountCardId, String channelTradeId,String relOrderNo) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(amount);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setTradeAccountCardId(tradeAccountCardId);
		orderInfoDTO.setOrderType(ORDER_TYPE_VJ_DUE_REPAY_BANKCARD);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_VJ_DUE_REPAY_BANKCARD_INIT);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("vj自营贷款到期还款(银行卡)");
		orderInfoDTO.setRelOrderNo(relOrderNo);
		return orderInfoDTO;
	}

	//vj自营信贷 -- 银行卡扣款汇总(到期还款)
	public static MBOrderInfoDTO getVjSelfLoanDueBankCardWithhold(String userId, String orderNo, BigDecimal principal,BigDecimal amount,String tradeAccountCardId, String channelTradeId,String relOrderNo) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderNo);
		orderInfoDTO.setPrice(principal);
		orderInfoDTO.setUserId(userId);
		orderInfoDTO.setTotalPrice(amount);
		orderInfoDTO.setTradeAccountCardId(tradeAccountCardId);
		orderInfoDTO.setOrderType(ORDER_TYPE_VJ_DUE_BANKCARD_WITHHOLD);
		orderInfoDTO.setOrderStatus(ORDER_STATUS_VJ_DUE_BANKCARD_WITHHOLD_INIT);
		orderInfoDTO.setTradeId(channelTradeId);
		orderInfoDTO.setTimeFinancialApproval(new Date());
		orderInfoDTO.setTitle("银行卡扣款(汇总)");
		orderInfoDTO.setRelOrderNo(relOrderNo);
		return orderInfoDTO;
	}
	
	
	//picc自营信贷 -- 借款成功订单
		public static MBOrderInfoDTO getPiccSupportLoanApplyOrder(String userId, String orderNo, BigDecimal amount, String channelTradeId,String relOrderNo) {
			MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
			orderInfoDTO.setOrderNo(orderNo);
			orderInfoDTO.setPrice(amount);
			orderInfoDTO.setUserId(userId);
			orderInfoDTO.setTotalPrice(amount);
			orderInfoDTO.setOrderType(ORDER_TYPE_PICC_LOAN);
			orderInfoDTO.setOrderStatus(ORDER_STATUS_PICC_LOAN_CONFIRM);
			orderInfoDTO.setTradeId(channelTradeId);
			orderInfoDTO.setTimeFinancialApproval(new Date());
			orderInfoDTO.setTitle("PICC自营信贷借款");
			orderInfoDTO.setRelOrderNo(relOrderNo);
			return orderInfoDTO;
		}

		//picc自营信贷  -- 到期还款银行卡扣款（到期还款）
		public static MBOrderInfoDTO getPiccLoanDueRepaySingleWithhold(String userId, String orderNo, BigDecimal amount,String tradeAccountCardId, String channelTradeId,String relOrderNo) {
			MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
			orderInfoDTO.setOrderNo(orderNo);
			orderInfoDTO.setPrice(amount);
			orderInfoDTO.setUserId(userId);
			orderInfoDTO.setTotalPrice(amount);
			orderInfoDTO.setTradeAccountCardId(tradeAccountCardId);
			orderInfoDTO.setOrderType(ORDER_TYPE_PICC_DUE_REPAY_BANKCARD);
			orderInfoDTO.setOrderStatus(ORDER_STATUS_PICC_DUE_REPAY_BANKCARD_INIT);
			orderInfoDTO.setTradeId(channelTradeId);
			orderInfoDTO.setTimeFinancialApproval(new Date());
			orderInfoDTO.setTitle("PICC自营贷款到期还款(银行卡)");
			orderInfoDTO.setRelOrderNo(relOrderNo);
			return orderInfoDTO;
		}

		//picc自营信贷 -- 银行卡扣款汇总(到期还款)
		public static MBOrderInfoDTO getPiccLoanDueBankCardWithhold(String userId, String orderNo, BigDecimal principal,BigDecimal amount,String tradeAccountCardId, String channelTradeId,String relOrderNo) {
			MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
			orderInfoDTO.setOrderNo(orderNo);
			orderInfoDTO.setPrice(principal);
			orderInfoDTO.setUserId(userId);
			orderInfoDTO.setTotalPrice(amount);
			orderInfoDTO.setTradeAccountCardId(tradeAccountCardId);
			orderInfoDTO.setOrderType(ORDER_TYPE_PICC_DUE_BANKCARD_WITHHOLD);
			orderInfoDTO.setOrderStatus(ORDER_STATUS_PICC_DUE_BANKCARD_WITHHOLD_INIT);
			orderInfoDTO.setTradeId(channelTradeId);
			orderInfoDTO.setTimeFinancialApproval(new Date());
			orderInfoDTO.setTitle("银行卡扣款(汇总)");
			orderInfoDTO.setRelOrderNo(relOrderNo);
			return orderInfoDTO;
		}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTradeAccountCardId() {
		return tradeAccountCardId;
	}

	public void setTradeAccountCardId(String tradeAccountCardId) {
		this.tradeAccountCardId = tradeAccountCardId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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

	public Date getTimeFinancialApproval() {
		return timeFinancialApproval;
	}

	public void setTimeFinancialApproval(Date timeFinancialApproval) {
		this.timeFinancialApproval = timeFinancialApproval;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOrderToken() {
		return orderToken;
	}

	public void setOrderToken(String orderToken) {
		this.orderToken = orderToken;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getRelOrderNo() {
		return relOrderNo;
	}

	public void setRelOrderNo(String relOrderNo) {
		this.relOrderNo = relOrderNo;
	}

    public BigDecimal getActivityAmount() {
        return activityAmount;
    }

    public void setActivityAmount(BigDecimal activityAmount) {
        this.activityAmount = activityAmount;
    }

    public BigDecimal getVirtualAmount() {
        return virtualAmount;
    }

    public void setVirtualAmount(BigDecimal virtualAmount) {
        this.virtualAmount = virtualAmount;
    }

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
}
