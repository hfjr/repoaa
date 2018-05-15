package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
@Mapper
public interface IPCPayMapper {

	//插入未成功充值订单
	public void saveRechargeOrder(@Param("orderNo")String orderNo,@Param("amt")String amt,@Param("userId")String userId);
	
	//保存交易记录
	public void saveTradeRecord(@Param("tradeNo")String tradeNo,@Param("orderNo")String orderNo,@Param("amt")String amt);

	//交接记录
	public void saveEbatongRecord(@Param("tradeNo")String tradeNo,@Param("userId")String userId,@Param("amt")String amt);
	
	//获取订单信息
	public Map<String,Object> getOrderInfo(@Param("tradeNo")String tradeNo);
	
	//校验订单是否已经支付
	public Integer countOrderRechargeStatus(@Param("orderNo")String orderNo,@Param("userId")String userId);

	//更新订单状态
	public void updatePCRechargeOrderStatus(@Param("orderNo")String orderNo,@Param("userId")String userId);
	
	//更新订单金额
	public void addUserMaAvailableAmount(@Param("amt")String amt,@Param("userId")String userId);
	
	//更新交易状态
	public void updatePaymentTradeStatus(@Param("orderNo")String orderNo);
	
}
