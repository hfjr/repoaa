package com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.service;

import java.math.BigDecimal;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.CallbackBizResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.TradeSearchResultDTO;

/**
 * 接口用于扣款相关业务 1.扣款前接口初始化 2.发送扣款短信 3.确认短信扣款 4.回调 5.查询扣款结果 6.代扣（免短信扣款）
 * 
 * @author jiangkaijun
 * 
 */
public interface IWithholdExcuteService {
	Object excutePreBizOperateParameter(String bizType,Object parameter);
	
	CallbackBizResultDTO excuteCallBackBizOperateSuccess(String userId,String bizNo, String orderType, BigDecimal amount);
	
	CallbackBizResultDTO excuteCallBackBizOperateFail(String userId,String bizNo, String orderType, BigDecimal amount);
	
	void excuteCallBackProcess(String userId,String orderType,String bizNo);
	
	TradeSearchResultDTO excuteQueryBizOperateResult(String userId,String orderType,String bizNo);
}
