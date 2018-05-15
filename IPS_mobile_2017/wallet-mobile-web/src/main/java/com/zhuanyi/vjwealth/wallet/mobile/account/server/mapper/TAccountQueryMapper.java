package com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper;

import com.fab.server.annotation.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyingxuan
 * @date 20160714
 */
@Mapper
public interface TAccountQueryMapper {
	
	//获取ta账户信息("amountFrozen": ta账户冻结金额  "totalAccountAmount": ta账户总金额)
	Map<String,String> queryTAccountInfo(@Param("userId") String userId);
	
    //获取ta账户昨日收益"yesterdayReceive": ta账户昨日收益
	Map<String,String> queryTAccountYestodayReceive(@Param("userId") String userId, @Param("receiveDate") String receiveDate);
	
	//获取ta账户累计收益("allAreadyReceive": ta账户累计收益)
	Map<String,String> queryTAccountTotalReceive(@Param("userId") String userId);
	
	//ta账户 转入明细
	List<Map<String,String>> queryTAccountBillDetail(Map<String, Object> paramMap);
	
	//ta账户 历史收益列表
	List<Map<String,String>> queryTAccountReciveDetail(Map<String, Object> paramMap);
	
	//查询ta账户的万分收益及七日年化利率
	Map<String,String> queryTAccountFundWanInfo();
	
	// ta账户提现前查询,可用余额amountAvailable
	String queryTAccountCanUseAmount(@Param("userId") String userId);
	// ta账户提现前查询,可用余额amountAvailable
	String queryTAccountCanUseAmountV36(@Param("userId") String userId);
	
	//查询ta账户可提现次数canUseCount
	Map<String,String> queryTAccountCanWithdrawTimes(@Param("userId") String userId);
	
	//ta账户冻结金额详细
	List<Map<String,String>> queryTAccountFrozenDetail(Map<String, Object> paramMap);
	
	//查询ta账户提现提示
	Map<String,String> queryTAccountWithdrawTips();
	
	
}
