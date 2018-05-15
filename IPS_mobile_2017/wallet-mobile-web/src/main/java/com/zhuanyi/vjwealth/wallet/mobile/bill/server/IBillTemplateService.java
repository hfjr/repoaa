package com.zhuanyi.vjwealth.wallet.mobile.bill.server;

import java.util.Map;

public interface IBillTemplateService {

	/**
	 * 查询账单详情
	 * @param orderId
	 * @param orderType 类型：
	 *        e账户提现(withdraw_ea)
	 *        余额提现(withdraw_ma)，
	 *        充值(recharge_ma)，
	 *        余额申购ea(apply_ma_to_ea),
	 *        ea转到余额(transfer_ea_to_ma)，
	 *        工资(batch_apply)，
	 *        余额申购v+(apply_ma_to_v1),
	 *        v+转到余额(transfer_v1_to_ma)，
	 *        定期理财(apply_ma_to_rf)
	 * @return
	 * @since 3.0
	 */
	public Object getBillDetail(String orderId,String orderType);
	
    /**
     * 查询账单列表
     * @param userId
     * @param page
     * @return
     * @since 3.0
     */
	public  Map<String, Object> getBillList(String userId, String page) ;
}
