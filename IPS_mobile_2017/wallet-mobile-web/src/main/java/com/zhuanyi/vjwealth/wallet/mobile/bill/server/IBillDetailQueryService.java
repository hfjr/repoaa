package com.zhuanyi.vjwealth.wallet.mobile.bill.server;

public interface IBillDetailQueryService {
	
	/**
	 * 
	 * @author wangzhangfei@vjwealth.com        
	 * @title 根据账单编号和账单类型，查询账单详细信息
	 * @param orderId 账单编号
	 * @param billType 账单类型
	 * @return 
	 *        e账户提现
	 *        余额提现，
	 *        充值，
	 *        余额申购ea,
	 *        ea转到余额，
	 *        工资，
	 *        余额申购v+,
	 *        v+转到余额，
	 *        定期理财
	 * @since v3.0
	 */
	
	Object getBillDetailByOrderIdAndOrderType(String orderId,String billType);

}
