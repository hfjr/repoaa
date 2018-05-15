package com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.vjwealth.event.api.dto.ExcuteServiceRequestDTO;
import com.vjwealth.event.api.service.IExcuteEventService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.CallbackBizResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.bo.RechargeCallbackBO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.TradeSearchResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.integration.mapper.IRechargeCallBackMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.service.IWithholdService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserInviteMapper;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;


@Service("rechargeWithholdService")
public class RechargeWithholdService implements IWithholdService {

	@Autowired
	private IMBUserAccountMapper userAccountMapper;

	@Autowired
	private IRechargeCallBackMapper rechargeCallBackMapper;

	@Remote
	private ISendEmailService sendEmailService;
	@Autowired
	private IExcuteEventService excuteEventService;
	@Autowired
	private IMBUserInviteMapper mbuserImbUserInviteMapper;
	
	

	private static final String ORDER_TYPE = "recharge_ma";

	@Override
	public boolean isSupportOrderType(String orderType) {
		if (RechargeWithholdService.ORDER_TYPE.equals(orderType))
			return true;
		return false;
	}

	@Override
	public Object preBizOperateParameter(Object preParmeter) {

		return null;
	}

	
	
	@Override
	public CallbackBizResultDTO callBackBizOperateSuccess(String userId,String bizNo, BigDecimal amount) {

		// 基础校验
		RechargeCallbackBO order = rechargeCallBackMapper.queryRechargeOrderInfo(bizNo);

		validateRechargeOrder(order,userId, bizNo, amount);
		
		// 1. 充值订单修改状态
		int count = rechargeCallBackMapper.updateRrechargeOrderSuccess(bizNo);
		if (count != 1) {
			throw new AppException(String.format(String.format("[RechargeWithholdService.callBackBizOperateSuccess]更新订单状态失败[recharge_ma_confirm_dispose-->recharge_ma_confirm],orderNo [%s],更新条数为[%s]",bizNo,count)));
		}

		// 2. 更新增加主账户余额
		count=rechargeCallBackMapper.addMaAccountAmountByRecharge(bizNo);
		if (count != 1) {
			throw new AppException(String.format("[RechargeWithholdService.callBackBizOperateSuccess]账户金额更新失败 ,orderNo [%s],更新条数为[%s]",bizNo,count));
		}
		
		// 查询交易号
		String tradeNo=rechargeCallBackMapper.queryTradeNoByOrderNo(bizNo);
		
		// 3.检查银行卡是否存在,如果不存在 , 绑定新的银行卡  
		if(rechargeCallBackMapper.checkBankCardExit(tradeNo)==0){
			// 绑定第二张充值卡
			if(rechargeCallBackMapper.countBankCard(userId)>0){
				rechargeCallBackMapper.insertCardRechargeForNewCard(tradeNo);
			}else{
				// 更新姓名
				
				// 第一次绑定新的银行卡	
				rechargeCallBackMapper.insertCardRecharge(tradeNo);
				rechargeCallBackMapper.insertCardSecurity(tradeNo);
				rechargeCallBackMapper.completeUserInfo(tradeNo);
			}
		}
		
		
		//增加事件
		rechargeSuccessEvent(userId, bizNo, amount.toEngineeringString());
		
		
		return CallbackBizResultDTO.returnCallBackSucces("充值成功");
	}
	

	
	private void rechargeSuccessEvent(String userId,String orderNo,String tradePrice){
		try{
			ExcuteServiceRequestDTO excuteServiceRequestDTO = new ExcuteServiceRequestDTO();
			Map<String,String> paramMap=new HashMap<String,String>();
			paramMap.put("userId", userId);
			paramMap.put("orderNo", orderNo);
			paramMap.put("tradePrice", tradePrice);
			paramMap.putAll(mbuserImbUserInviteMapper.queryEventInfo(userId));
			excuteServiceRequestDTO.setParamJsonObject(excuteServiceRequestDTO.parseObject(paramMap));
			excuteEventService.excuteAsyncEvent("EV_0008", excuteServiceRequestDTO);
		}catch (Exception ex){
			BaseLogger.error("事情平台EV_0008失败",ex);
		}
	}
	
	
	@Override
	public CallbackBizResultDTO callBackBizOperateFail(String userId,String bizNo, BigDecimal amount) {
		// 基础校验
		RechargeCallbackBO order = rechargeCallBackMapper.queryRechargeOrderInfo(bizNo);
		
		validateRechargeOrder(order,userId, bizNo, amount);
		
		// 更新订单状态
		int count =rechargeCallBackMapper.updateRrechargeOrderFail(bizNo);
		if (count != 1) {
			throw new AppException(String.format(String.format("[RechargeWithholdService.callBackBizOperateSuccess]更新订单状态失败[recharge_ma_confirm_dispose-->recharge_ma_fail],orderNo [%s],更新条数为[%s]",bizNo,count)));
		}	
		
		return CallbackBizResultDTO.returnCallBackFail("充值失败[第三方支付失败的回调请求]");
		
		//
		// //失败:修改订单状态
		// BaseLogger.audit(String.format("充值通知状态:trade_state[%s]，orderId[%s]",payNoticeDTO.getTradeState(),payNoticeDTO.getOrderId()));
		//
		// MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		// orderInfoDTO.setUserId(payNoticeDTO.getUserId());
		// orderInfoDTO.setTradeAccountCardId(payNoticeDTO.getCardId());
		//
		// BigDecimal amount = new
		// BigDecimal(payNoticeDTO.getAmount()).divide(new
		// BigDecimal(100)).setScale(2, BigDecimal.ROUND_FLOOR);
		// orderInfoDTO.setPrice(amount);
		//
		// orderInfoDTO.setOrderNo(payNoticeDTO.getOrderId());
		// orderInfoDTO.setOrderStatus(MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_FAIL);
		// orderInfoDTO.setTitle("充值失败");
		//
		// int count = userAccountMapper.updateOrderStatus(orderInfoDTO);
		// BaseLogger.audit(String.format("充值回调,结果为[失败]修改订单orderId[%s],userId[%s]",
		// orderId,userId));
		// //易宝下完单,没有确认付款,也会回调通知,由于订单是在确认下单时生成,所以就无法改状态
		// // if(count==0){
		// // String message =
		// String.format("充值回调,结果为失败,修改订单状态失败;orderId[%s],userId[%s],amount[%s],cardId[%s]",
		// //
		// payNoticeDTO.getOrderId(),payNoticeDTO.getUserId(),payNoticeDTO.getAmount(),payNoticeDTO.getCardId());
		// // BaseLogger.error(message);
		// //
		// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",pageEmailMap(message));
		// // }
		// //修改历史表记录为异常
		// userAccountMapper.updateWjEbatongTradeHistoryStatus(payNoticeDTO.getOrderId());
		// BaseLogger.audit("支付渠道充值通知回调-->payNoticeResolve-->充值结果为失败,-->");
	}

	@Override
	public TradeSearchResultDTO queryBizOperateResult(String userId,String bizNo) {

		return null;
	}

	@Override
	public Object processOperator(String userId,String bizNo) {
		// TODO Auto-generated method stub
		return null;
	}

	private void validateRechargeOrder(RechargeCallbackBO order,String userId,String bizNo,BigDecimal amount){
		if (order == null) {
			throw new AppException("充值通知回调异常,订单号不存在orderId[" + bizNo + "]");
		}
		// 判断user_id是否一致
		if (!order.getUserId().equals(userId)) {
			throw new AppException(String.format("充值通知回调异常,userId不一致，bizNo[%s],查询出的userId[%s]，实际传入的userId[%s]",bizNo,order.getUserId(),userId));
		}
		// 判断金额是否一致
		if (order.getAmount().compareTo(amount) != 0) {
			throw new AppException("充值通知回调异常,金额不一致[" + bizNo + "]");
		}

		if (order != null &&! RechargeCallbackBO.RECHARGE_MA_CONFIRM_DISPOSE.equals(order.getOrderStatus())) {
			
			throw new AppException(String.format("充值状态异常,不是充值中的订单[%s],状态为[%s]",bizNo,order.getOrderStatus()));
		}
	}
	
}
