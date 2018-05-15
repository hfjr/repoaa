package com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface MAccountQueryMapper {
		
	//获取总账户资产信息"amountFrozen": 冻结金额"amountAvailable": 账户余额 "totalAccountAmount": 总金额
	Map<String,String> queryMAccountInfo(@Param("userId")String userId);
	
	//获取总资产
	Map<String,String> queryAccountTotalAmount(@Param("userId")String userId);
	
	//获取总账户的投资金额"investmentAmount": 投资金额
	Map<String,String> queryMAccountInvestment(@Param("userId")String userId);
	
	//获取总账户的昨日收益"yesterdayReceive": 昨日收益
	Map<String,String> queryMAccountYestodayReceive(Map<String,String> paramMap);
	
	//获取总账户的累计收益"allAreadyReceive": 累计收益
	Map<String,String> queryMAccountTotalReceive(@Param("userId")String userId);
	
	//总账户 历史收益列表
	List<Map<String,String>> queryMAccountReciveDetail(Map<String,Object> paramMap);
	
	//账单明细(询所有成功状态的订单记录)
	List<Map<String,String>> queryAccountBillDetail(Map<String,Object> paramMap);
	
	//账户冻结金额详细(主,e,v账户)
	List<Map<String,String>> queryAccountFrozenBalanceDetail(Map<String,Object> paramMap);
	
	// 总账户提现前查询,可用余额amountAvailable
	Map<String,String> queryMAccountCanUseAmount(@Param("userId")String userId);
	
	
	//查询账户的冻结金额(主,e,v账户)
	Map<String,String> queryAccountFrozenBalance(@Param("userId")String userId);
	
	//查询总账户可提现次数canUseCount
	Map<String,String> queryMAccountCanWithdrawTimes(@Param("userId")String userId);
	
	//查询总账户提现提示
	Map<String,String> queryMAccountWithdrawTips();

	//查询主账户余额
	BigDecimal queryMAccountInvestmentAmount(@Param("userId")String userId);
	
	
}
