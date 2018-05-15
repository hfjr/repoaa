package com.zhuanyi.vjwealth.wallet.mobile.account.server.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface VAccountQueryMapper {
	
	
	//获取V+理财资产(余额:investmentAmount,冻结金额:amountFrozenAmount)
	Map<String,String> queryVAccountInfo(@Param("userId")String userId);
		
	//获取V+理财的总收益(allReceive)
	Map<String,String> queryVAccountTotalReceive(@Param("userId")String userId);
	
	//查询v+账户的待确定份额waitForSureAmount
	Map<String,String> queryVAccountWaitForSureAmount(@Param("userId")String userId);
	
	//查询v+账户的已确定份额haveBeenSureAmount
	Map<String,String> queryVAccountAlreadySureAmount(@Param("userId")String userId);

	//查询v+账户的昨日收益yesterdayReceive
	Map<String,String> queryVAccountYestodayReceive(Map<String,String> paramMap);
	
	//获取V+理财资产(总金额:totalAmount,冻结金额:amountFrozenAmount)
	Map<String,String> queryVAccountOtherInfo(@Param("userId")String userId);
	
	//获取V+理财的万份收益及七日年化利率
	Map<String,String> queryVAccountFundWanInfo();
	
	// V+账户提现(转账)前查询,可用余额
	String queryVAccountCanUseAmount(@Param("userId")String userId);
	
	
	//v+账户最小申购额度
	BigDecimal queryVAccountMinApplyAmount();
	
	//v理财剩余份额
	Map<String,String> queryVProductLeft();
	
	
	//v+理财账单明细
	List<Map<String,String>> queryVAccountBillDetail(Map<String,Object> paramMap);

	//查询v+的累计收益列表
	List<Map<String,String>> queryVAccountReciveDetail(Map<String,Object> paramMap);
	
	//获取V+理财资产总转出(确认过的)
	Map<String,BigDecimal> queryVAccountOutcomeAmount(@Param("userId")String userId);
	//获取V+理财资产总转入(未确认和已确认)
	Map<String,BigDecimal> queryVAccountIncomeAmount(@Param("userId")String userId);
	
	
	//v账户冻结金额详细
	List<Map<String,String>> queryVAccountFrozenDetail(Map<String,Object> paramMap);
	
	
	//查询v理财盘子剩余总份额
	BigDecimal queryVAccountRealRemianTotalAmount();
	
	
	
	//查询个人V理财剩余可购买份额
	BigDecimal queryVAccountCanApplyRemainAmount(@Param("userId")String userId);
	
	
	
	
	
}
