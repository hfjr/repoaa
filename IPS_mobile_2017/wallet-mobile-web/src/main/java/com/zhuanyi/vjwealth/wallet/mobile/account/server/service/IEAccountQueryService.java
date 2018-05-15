package com.zhuanyi.vjwealth.wallet.mobile.account.server.service;

import java.util.Map;

/**
 * e账户查询接口
 * @author Eric
 */
public interface IEAccountQueryService {
	
	
	/**
	 * @title e账户详情
	 * @param 用户ID
	 * @return "allAreadyReceive": e账户累计收益
               "amountFrozen": e账户冻结金额  
               "yesterdayReceive": e账户昨日收益
               "totalAccountAmount": e账户总金额
	 */
	Map<String,String> queryEAccountInfo(String userId);
	
	/**
	 * @title e账户 转入,转出
	 * @param 用户ID,显示页
	 * @return "tradeDate":    交易日期("2015-07-19 23:13:20")
                "tradeTitle":  交易title
                "tradeAmount": 交易金额字符串
                "dayOfWeekName": 星期("Sun")
                "day":  交易日("19")
                "tradeAmount": 交易金额
	 */
	Map<String,Object> queryEAccountDetail(String userId,String page);
	
	
	/**
	 * @title e账户历史收益列表
	 * @param 用户ID,显示页
	 * @return  "reciveAmount": 收益
                "reciveDate": 收益时间("2015-08-12 11:29")
	 */
	Map<String,Object> queryEAccountReciveDetail(String userId,String page);
	
	/**
	 * @title e账户万分收益与七日年化利率
	 * @param 用户ID
	 * @return  "weekReceiveRate": 七日年化利率
                "everyReceiveRate": 万分收益
	 */
	Map<String,String> queryEAccountFundWanInfo();
	
	/**
	 * @title e账户提现到银行卡前余额查询(转出到余额中不受限制)
	 * @param 用户ID
	 * @return  "amountAvailable": 可用余额
                "tip": 提示语(单笔提现5万,每天提现3笔)
                "canUseCount": 可提现次数,注意控制("3")
                "singleLimit": 单笔最多提现("50000")
                
                
                //TODO 转出提示语
	 */
	Map<String,String> queryEAccountOutComeBalance(String userId);
	
	
	
	/**
	 * @title e账户转入查询
	 * @param userId
	 * @return
	 */
	Map<String,String> queryEAccountInComeBalance(String userId);
	
	
	/**
	 * @title e账户的冻结金额详细
	 * @param userId
	 * @param page
	 * @return "amount": 金额
                "title": 标题
                "date": 时间("2015-07-26")
	*/
	Map<String,Object> queryEAccountFrozenDetail(String userId,String page);

	/**
	 * @title e账户初始化页面
	 * @param userId
	 * @return
	 */
	Map<String,String> queryEAccountInitPage(String userId);
	
	
}
