package com.zhuanyi.vjwealth.wallet.mobile.account.server.service;

import java.util.Map;

/**
 * 主账户(余额账户)查询接口
 * @author Eric
 *
 */
public interface IMAccountQueryService {
	
	/**
	 * @title 总账户详情
	 * @param 用户ID
	 * @return "allAreadyReceive": 累计收益
               "amountFrozen": 冻结金额
               "investmentAmount": 投资金额
               "yesterdayReceive": 昨日收益
               "amountAvailable": 账户余额
               "totalAccountAmount": 总金额
	 */
	Map<String,String> queryMAccountInfo(String userId);
	
	/**
	 * @title 总账户账单查询
	 * @param 用户ID,显示页
	 * @return  "amount": 金额
                "title": 标题
                "date": 时间("2015-07-26 09:20")
	 */
	Map<String,Object> queryTotalAcountBill(String userId,String page);
	
	/**
	 * @title 总账户收益查询
	 * @param 用户ID,显示页
	 * @return  "amount": 金额
                "title": 标题
                "date": 时间("2015-07-26 09:20")
	 */
	Map<String,Object> queryTotalAcountReceive(String userId,String page);
	
	/**
	 * @title 总账户提现到银行卡前余额查询
	 * @param 用户ID
	 * @return "amountAvailable": 可用余额
               "tip": 提示语(单笔提现5万,每天提现3笔)
               "canUseCount": 可提现次数,注意控制("3")
               "singleLimit": 单笔最多提现("50000")
	 */
	Map<String,String> queryTotalAccountBalance(String userId);
	
	/**
	 * 账户的冻结金额(包括主账户,e账户,v账户) 接口到2.0以后过期
	 * @param userId
	 * @return
	 *        mAccountFrozen: 主账户冻结金额
	 *        eAccountFrozen: e账户冻结金额
	 *        vAccountFrozen: v账户冻结金额
	 */
    @Deprecated
	Map<String,String> queryAccountFrozenBalance(String userId);
	
	/**
	 * @title 账户的冻结金额详细(包括主账户,e账户,v账户)
	 * @param userId
	 * @param page
	 * @return "amount": 金额
                "title": 标题
                "date": 时间("2015-07-26")
	 */
	Map<String,Object> queryAccountFrozenBalanceDetail(String userId,String page);
	
	
	
	/**
	 * 财富界面,提现初始化
	 * @param userId
	 * @return
	 */
	Map<String,Object> queryWithdrawMaAccountAndEAccountInfo(String userId);

	/**
	 * App提现页面初始化
	 * @param userId
	 * @return
	 */
	Map<String,Object> queryWithdrawMaAccountAndTAccountInfo(String userId);
	
}
