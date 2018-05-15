package com.zhuanyi.vjwealth.wallet.mobile.account.server.service;

import java.util.Map;

/**
 * V理财账户查询
 * @author Eric
 *
 */
public interface IVAccountQueryService {
	
	
	/**
	 * @title 获取V+理财资产和累计收益    
	 * @param 用户ID
	 * @return  allReceive: 累计收益
                investmentAmount: v1可用余额
                amountFrozenAmount:v1冻结金额
	 */			
	Map<String,String> queryVAccountInfo(String userId);
	
	/**
	 * @title v+理财账户界面初始化
	 * @param 用户ID
	 * @return  "waitForSureAmount": 待确定份额
                "totalAmount": 总金额
                "allReceive": 累计收益
                "yesterdayReceive": 昨日收益
		        "amountFrozenAmount":冻结金额
                "haveBeenSureAmount": 已确认份额    
	 */
	Map<String,String> queryVplusAccount(String userId);
	
	
	/**
	 * @title V+账户提现(转账)前查询,可用余额
	 * @param 用户ID
	 * @return "amountAvailable": 可用余额
	 */
	Map<String,String> queryVAccountToBalance(String userId);
	
	/**
	 * @title V+理财万分收益与七日年化利率
	 * @param 用户ID
	 * @return "weekReceiveRate": "8.000",   //7日年年化收益
               "everyReceiveRate": "1.12",   //万分收益
               "remainTotalAmount": "2958621"      //剩余总份额
	 */
	Map<String,String> queryVAccountFundWanInfo();
	
	
	/**
	 * @title v+理财进出账
	 * @param 用户ID,显示页
	 * @return  "amount": 金额
                "title": 标题
                "date": 时间("2015-07-26 09:20")
	 */
	Map<String,Object> queryVAcountBill(String userId,String page);
	
	
	/**
	 * @title v+理财累计收益列表
	 * @param 用户ID,类型(转入:income;转出:outcome),显示页
	 * @return  "amount": 金额
                "title": 标题
                "date": 时间("2015-07-26 09:20")
	 */
	Map<String,Object> queryVAccountReceiveDetail(String userId,String page);

	/**
	 * @title v账户的冻结金额详细
	 * @param userId
	 * @param page
	 * @return "amount": 金额
                "title": 标题
                "date": 时间("2015-07-26")
	*/
	Map<String,Object> queryVAccountFrozenDetail(String userId,String page);

	
	/**
	 * @title 查询V+可申购份额
	 * @param 用户ID 
	 * @return  tipInput   当前最多可购买
	 *          tipContent 最低投资额度不少于
	 *          maxBalance --最多申购
	 *       	minBalance --起投金额
	 */
	Map<String,String> applyMaToV1Limit(String userId);

	
	/**
	 * @title v理财初始化页面
	 * @param userId
	 * @return
	 */
	Map<String,String> queryVAccountInitPage(String userId);

	
	/**
	 * @titile v理财转入初始化页面
	 * @param userId
	 * @return
	 */
	Map<String,String> applyMaToV1InitPage(String userId);
	
	
	
	
}
