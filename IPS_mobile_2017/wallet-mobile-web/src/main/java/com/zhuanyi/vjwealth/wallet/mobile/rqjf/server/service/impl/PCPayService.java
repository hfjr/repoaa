package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper.IPCPayMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.ICommonSequenceService;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IPCPayService;

@Service
public class PCPayService implements IPCPayService{
	@Autowired
	private IPCPayMapper pcPayMapper;
	@Autowired
	private ICommonSequenceService commonSequenceService;
	
	@Override
	public void saveRechargeOrder(String tradeNo,String userId,String amt) {
		
		//保存交易记录 trade ,  TODO
		String orderNo=commonSequenceService.getNextSequence("OR", "vj_order");
		pcPayMapper.saveTradeRecord(tradeNo,orderNo, amt);
		
		//ebatong
		pcPayMapper.saveEbatongRecord(tradeNo,userId, amt);
		
		//产生未支付成功的订单
		pcPayMapper.saveRechargeOrder(orderNo, amt, userId);
		
	}
	
	
	//支付成功操作
	public void successRechargeOrder(String tradeNo){
		Map<String,Object> order=pcPayMapper.getOrderInfo(tradeNo);
		String userId=order.get("userId").toString();
		String amt= order.get("totalPrice").toString();
		String orderNo=order.get("orderNo").toString();
		// 检查订单是否已交易成功
		if(pcPayMapper.countOrderRechargeStatus(orderNo,userId)>0){
			return ;
		}
		// 上锁 TODO ..
		
		// 更改订单状态
		pcPayMapper.updatePCRechargeOrderStatus(orderNo, userId);
		// 更改账户余额
		pcPayMapper.addUserMaAvailableAmount(amt, userId);
		
		//更新支付订单
		
		pcPayMapper.updatePaymentTradeStatus(orderNo);
		
	}
	
}
