package com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.integration.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.bo.PaymentCallBackParamBO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.bo.TradeRecordBO;

/**
 * 支付单记录Mapper
 * 
 * @author jiangkaijun
 * 
 */
@Mapper
public interface IMBTradeRecordMapper {

	/**
	 * 写入交易记录表
	 * 
	 * @param tradeRecordDTO
	 */
	public int insertTradeRecord(@Param("tradeNo") String tradeNo, @Param("amount") BigDecimal totalPrice, @Param("relBizOrderNo") String relBizOrderNo,@Param("bankCardNo")String bankCardNo, @Param("platformId")String platformId);
	
	/**
	 * 写入交易记录表(用于充值补单，成功单)
	 * 
	 * @param tradeRecordDTO
	 */
	public int insertTradeSuccessRecord(@Param("tradeNo") String tradeNo, @Param("amount") BigDecimal totalPrice, @Param("relBizOrderNo") String relBizOrderNo,@Param("bankCardNo")String bankCardNo, @Param("platformId")String platformId);
	
	/**
	 * 填充bizOrderNo和platformId
	 * @param platformId
	 * @param bizOrderNo
	 */
	public void updateTradePlatformAndBizOrder(@Param("tradeNo") String tradeNo,@Param("platformId")String platformId,@Param("relBizOrderNo")String bizOrderNo);
	/**
	 * 校验交易是否存在 交易表和订单表关联查询
	 * 
	 * @param tradeNo
	 * @return
	 */
	int countTradeRecordExitsByTradeNo(@Param("tradeNo")String tradeNo);

	/**
	 * 查询回调参数必要参数 m.user_id as userId, m.order_no as orderNo, m.order_type as
	 * orderType, p.rel_biz_order_status as relBizOrderStatus, p.trade_stauts as
	 * tradeStatus
	 * 
	 * @param tradeNo
	 * @return
	 */
	public PaymentCallBackParamBO queryCallBackParamByTradeNo(@Param("tradeNo")String tradeNo);

	/**
	 * 根据tradeNo查询交易记录
	 * @param tradeNo
	 * @return
	 */
	public TradeRecordBO queryTradeRecordByTradeNo(@Param("tradeNo") String tradeNo);
	/**
	 * 根据业务订单号,查询交易流水
	 * 
	 * @param orderNo
	 * @return
	 */
	public TradeRecordBO queryTradeRecordByOrderNo(@Param("relBizOrderNo") String orderNo);

	/**
	 * 查询相差多少分钟以上的,处于交易中的状态的交易流水信息
	 * 
	 * @param minute
	 * @return
	 */
	public List<PaymentCallBackParamBO> queryForBatchChangeStatus(@Param("minute")int minute);

	/**
	 * 更新交易成功
	 * 
	 * @param tradeNo
	 * @param tradeStatus
	 */
	public int updateTradeRecordSuccess(@Param("tradeNo") String tradeNo, @Param("relOrderStatus") String relOrderStatus, @Param("relBizMessage") String message);

	// 更新交易失败
	public void updateTradeRecordFail(@Param("tradeNo") String tradeNo, @Param("relOrderStatus") String relOrderStatus, @Param("relBizMessage") String message);

}
