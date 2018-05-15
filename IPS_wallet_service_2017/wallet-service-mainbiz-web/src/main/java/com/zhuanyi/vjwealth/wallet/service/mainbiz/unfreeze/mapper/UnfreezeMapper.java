package com.zhuanyi.vjwealth.wallet.service.mainbiz.unfreeze.mapper;


import java.math.BigDecimal;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.rqb.ips.depository.platform.beans.wj_orderIPS;

/**
 * @author 刘发涛
 * 
 * */

@Mapper
public interface UnfreezeMapper {
		
	public void unfreezeMoney(@Param("trdAmt") BigDecimal trdAmt ,@Param("IPSIDdeposit") String IPSIDdeposit);
	void saveTradehistory(@Param("iPSSendJson")String iPSSendJson,  @Param("IPSReturnJson")String IPSReturnJson,@Param("port")String port);

	public Map<Object, Object> selectIPSMa(@Param("ipsAcctNo")String ipsAcctNo);
	
	public Map<Object, Object> selectTrdAmt(@Param("UnFreezeBillNo") String UnFreezeBillNo);
	
	public void insertOrder(wj_orderIPS wj_orderIPS);
	
	public void updateOrder(wj_orderIPS wj_orderIPS);
	
	public Map<Object, Object> IPSselectUser_id(@Param("ipsAcctNo2")String ipsAcctNo2);

	
	public Map<Object, Object> selectFreezeId(@Param("freezeId") String freezeId);
	
	/**
	 * investmentManageMoneys
	 * */
	public void investmentManageMoney(@Param("ipsAcctNoQueryChannel_trade_id")String ipsAcctNoQueryChannel_trade_id);
	/**
	 * updateRepayment
	 * */
	public void updateRepayment(@Param("updateKeyFreezeId")String updateKeyFreezeId);
	
	
	
		
	public void insertWj_payment_trade_record(wj_orderIPS wj_orderIPS);
	/**简单的查询user_id*/
	public Map<Object, Object> selectUserId(@Param("ipsAcctNo")  String ipsAcctNo);
	/**查询的是理财的订单ID不一致的*/	
	public Map<Object, Object> selectOrder_no(@Param("freezeId") String freezeId);
	
	/**本金回款order表记录*/
	public void returnMoney(@Param("returnMoneyId")String returnMoneyId);
	/**用户收益表记录*/
	public void investmentEarningsManageMoney(@Param("earningsId") String earningsId);
	
}