package com.zhuanyi.vjwealth.wallet.mobile.account.server.service;

import java.util.Map;

/**
 * ta账户查询接口
 * @author zhangyingxuan
 */
public interface ITAccountQueryService {
	
	
	/**
	 * @title ta账户详情
	 * @return "allAreadyReceive": ta账户累计收益
               "amountFrozen": ta账户冻结金额
               "yesterdayReceive": ta账户昨日收益
               "totalAccountAmount": ta账户总金额
	 */
	Map<String,String> queryTAccountInfo(String userId);
	
	/**
	 * @title ta账户 转入,转出
	 * @return "tradeDate":    交易日期("2015-07-19 23:13:20")
                "tradeTitle":  交易title
                "tradeAmount": 交易金额字符串
                "dayOfWeekName": 星期("Sun")
                "day":  交易日("19")
                "tradeAmount": 交易金额
	 */
	Map<String,Object> queryTAccountDetail(String userId, String page);
	
	
	/**
	 * @title ta账户历史收益列表
	 * @return  "reciveAmount": 收益
                "reciveDate": 收益时间("2015-08-12 11:29")
	 */
	Map<String,Object> queryTAccountReciveDetail(String userId, String page);
	
	/**
	 * @title ta账户万分收益与七日年化利率
	 * @return  "weekReceiveRate": 七日年化利率
                "everyReceiveRate": 万分收益
	 */
	Map<String,String> queryTAccountFundWanInfo();
	
	/**
	 * @title ta账户提现到银行卡前余额查询(转出到余额中不受限制)
	 * @return  "amountAvailable": 可用余额
                "tip": 提示语(单笔提现5万,每天提现3笔)
                "canUseCount": 可提现次数,注意控制("3")
                "singleLimit": 单笔最多提现("50000")
                
                
                //TODO 转出提示语
	 */
	Map<String,String> queryTAccountOutComeBalance(String userId);
	
	
	
	/**
	 * @title ta账户转入查询
	 * @param userId
	 * @return
	 */
	Map<String,String> queryTAccountInComeBalance(String userId);
	
	
	/**
	 * @title ta账户的冻结金额详细
	 * @param userId
	 * @param page
	 * @return "amount": 金额
                "title": 标题
                "date": 时间("2015-07-26")
	*/
	Map<String,Object> queryTAccountFrozenDetail(String userId, String page);

	/**
	 * @title ta账户初始化页面
	 * @param userId
	 * @return
	 */
	Map<String,String> queryTAccountInitPage(String userId);
	
	
}
