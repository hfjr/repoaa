package com.zhuanyi.vjwealth.wallet.mobile.common.server.service;

public interface IOrderDateHelperService {

	
	/**
	 * @title 获取工资，余额申购e账户的确认日期
	 * @param orderDate 账单创建日期
	 * @return 确认日期
	 */
	String getEaConfirmDate(String orderDate);

	/**
     * @author zhangyingxuan
     * @date 20160714
	 * @title 获取工资，余额申购e账户的确认日期
	 * @param orderDate 账单创建日期
	 * @return 确认日期
	 */
	String getTaConfirmDate(String orderDate);

	/**
	 * @title 获取余额申购v+的确认日期
	 * @param orderDate 账单创建日期
	 * @return 确认日期
	 */
	String getV1ConfirmDate(String orderDate);

	/**
	 * 下一个中午
	 * @param orderDate
	 * @return 2015-12-05
	 */
	String getNextNoonDay(String orderDate);

	/**
	 * @title 根据账单时间，获取当天的日期（账单时间去掉时分秒,e账户提现）
	 * @param orderDate
	 * @return
	 */
	String getCurrentDay(String orderDate);

	/**
	 * @title 下一个自然日( ma提现,v1->ma)
	 * @param orderDate 订单日期
	 * @return 2015-09-27
	 */
	String getNextDay(String orderDate);

}
