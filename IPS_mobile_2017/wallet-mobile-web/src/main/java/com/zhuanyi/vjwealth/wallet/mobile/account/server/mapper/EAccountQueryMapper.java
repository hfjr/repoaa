package com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface EAccountQueryMapper {
	
	//获取e账户信息("amountFrozen": e账户冻结金额  "totalAccountAmount": e账户总金额)
	Map<String,String> queryEAccountInfo(@Param("userId")String userId);
	
    //获取e账户昨日收益"yesterdayReceive": e账户昨日收益
	Map<String,String> queryEAccountYestodayReceive(@Param("userId")String userId,@Param("receiveDate")String receiveDate);
	
	//获取e账户累计收益("allAreadyReceive": e账户累计收益)
	Map<String,String> queryEAccountTotalReceive(@Param("userId")String userId);
	
	//e账户 转入明细
	List<Map<String,String>> queryEAccountBillDetail(Map<String,Object> paramMap);
	
	//e账户 历史收益列表
	List<Map<String,String>> queryEAccountReciveDetail(Map<String,Object> paramMap);
	
	//查询e账户的万分收益及七日年化利率
	Map<String,String> queryEAccountFundWanInfo();
	
	// e账户提现前查询,可用余额amountAvailable
	String queryEAccountCanUseAmount(@Param("userId")String userId);
	
	
	//查询e账户可提现次数canUseCount
	Map<String,String> queryEAccountCanWithdrawTimes(@Param("userId")String userId);
	
	//e账户冻结金额详细
	List<Map<String,String>> queryEAccountFrozenDetail(Map<String,Object> paramMap);
	
	//查询e账户提现提示
	Map<String,String> queryEAccountWithdrawTips();
	
	
}
