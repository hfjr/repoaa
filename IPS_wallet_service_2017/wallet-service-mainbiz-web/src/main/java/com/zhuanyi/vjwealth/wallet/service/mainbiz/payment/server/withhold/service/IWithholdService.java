package com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.service;

import java.math.BigDecimal;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.BasePreparePaymentDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.CallbackBizResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.TradeSearchResultDTO;

/**
 * 1.扣款前业务准备
 * 2.扣款结束业务回调接口
 * 	2.1业务成功回调
 * 	2.2业务失败回调
 * 3.查询业务执行结果
 * @author jiangkaijun
 *
 */
public interface IWithholdService{
	
    //1.判断是否支持此订单类型
    public boolean isSupportOrderType(String orderType);
    
    /**
     * 2.前置业务操作
     * 用户调用付款前，做初始化业务操作
     * 1.实现业务下单
     * 2.返回扣款所需的参数
     * @return
     */
    public Object preBizOperateParameter(Object parameter);    
    
    /** 增加处理中逻辑(用来区分初始化和失败)
     * 
     * @param preParmeter
     */
    public Object processOperator(String userId,String bizNo);

    //2.回调操作-成功(返回执行结果)
    public CallbackBizResultDTO callBackBizOperateSuccess(String userId,String bizNo,BigDecimal amount);
    
  

    //3.回调操作-失败
    public CallbackBizResultDTO callBackBizOperateFail(String userId,String bizNo,BigDecimal amount);

    
    /**
    //4.查询回调，表示业务是否成功 code message、(需要业务的自己来实现)
     * code:success
     * message:xxx业务成功
     * 
     * @param bizNo --业务单号
     * @return
     */
    public TradeSearchResultDTO queryBizOperateResult(String userId,String bizNo);
    
}

