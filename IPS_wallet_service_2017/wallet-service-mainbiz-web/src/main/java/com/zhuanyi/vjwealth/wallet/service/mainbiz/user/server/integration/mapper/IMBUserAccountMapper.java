package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.*;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.WjEbatongTradeHistoryDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.RfPrincipalAndInterestDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.BankInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.ConfirmShareModelDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBRechargeDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.TradeAccountCardDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserTradeAccountCardDTO;

/**
 * @author xuejie
 */
@Mapper
public interface IMBUserAccountMapper {

	int updateUserWithdrawLock(@Param("userId") String userId);

	int updateUserWithdrawUnlock(@Param("userId") String userId);

	BigDecimal queryAccountAvailableAmount(@Param("userId")String userId, @Param("tradeType")String tradeType);

	void updateLessEaAmount(@Param("userId")String userId, @Param("amount")BigDecimal amount);

	/**
	 * @param userId
	 * @param amount
     * @author zhangyingxuan
     * @date 20160708
     */
	void updateLessTaAmount(@Param("userId")String userId, @Param("amount")BigDecimal amount);

	void updateAddMaAmount(@Param("userId")String userId, @Param("amount")BigDecimal amount);
	//生成红包订单号
	int addtOrderInfo(MBOrderInfoDTO transferEaToMaConfirmOrder);

	int addtOrderPacketInfoIps(IPSOrderInfoDTO ipsOrderInfoDTO);
	void addtOrderBidInfoIps(IPSOrderInfoDTO rechargeRpOrder);
	void updateFreezeV1Amount(@Param("userId")String userId, @Param("amount")BigDecimal amount);
	
	int queryEaAvavilableWithDrawTimes(@Param("userId")String userId);

	/**
     * @param userId
     * @return
	 * @author zhangyingxuan
	 * @date 20160712
	 * Ta账户查询提现次数
     */
	int queryTaAvavilableWithDrawTimes(@Param("userId") String userId);
	
	int queryMaAvavilableWithDrawTimes(@Param("userId")String userId);
	
	BigDecimal querysingleWithdrawUpperLimit();

	void updateFreezeMaAmount(@Param("userId")String userId, @Param("amount")BigDecimal amount);
	//ma可用到冻结
	void updateFreezeMaAmountIps(@Param("rqbUserId")String rqbUserId,@Param("frozenAmount") BigDecimal frozenAmount);
	
	void cancelFreezeMaAmount(@Param("userId")String userId, @Param("amount")BigDecimal amount);
	
	ConfirmShareModelDTO queryConfirmShareModel(@Param("paramKey")String paramKey, @Param("ymdInt")Integer ymdInt);

	List<String> queryNextTwoWorkdaysByBuyday(@Param("ymdInt")Integer ymdInt);

	int queryBindRepeatCardCountBycardNo(@Param("userId")String userId, @Param("cardNo")String cardNo);

	int queryCardCountByUserId(@Param("userId")String userId);

	void addUserSecurityCardInfo(MBRechargeDTO rechargeDTO);

	void updateUserRealNameInfo(MBRechargeDTO rechargeDTO);

	void addUserRechargeCardInfo(MBRechargeDTO rechargeDTO);

	MBRechargeDTO queryRealNameUserInfo(@Param("userId")String userId);
	
	//充值卡一定存在
	int checkRechargeCardExit(@Param("userId")String userId, @Param("cardId")String cardId);
	
	//充值卡银行是否在维护中
	int checkRechargeCardRunHealth(@Param("cardId")String cardId);

	//充值卡银行是否在维护中
	int checkRechargeCardRunHealthV32(@Param("cardId")String cardId);

	//充值金额在维护银行范围内
	int checkRechargeAmountValid(@Param("cardId")String cardId,@Param("amount")String amount);

	//充值金额在维护银行范围内
	int checkRechargeAmountValidV32(@Param("cardId")String cardId,@Param("amount")String amount);

	// 累积当天充值是否上限
	String checkSumRechargeBalanceDayLimitValid(@Param("userId")String userId, @Param("cardId")String cardId);

	// 累积当天充值是否上限
	String checkSumRechargeBalanceDayLimitValidV32(@Param("userId")String userId, @Param("cardId")String cardId);
		
	MBRechargeDTO queryBindCardInfoAndUserInfo(@Param("userId")String userId, @Param("cardId")String cardId);

	MBRechargeDTO queryBindCardInfoAndUserInfoWithHold(@Param("userId")String userId, @Param("cardId")String cardId, @Param("orderType") String orderType);

	int updatePurchaseV1Flag(@Param("userId")String userId);

	void addPurchaseV1Flag(@Param("userId")String userId);
	
	void updateRemainTotalAmount(@Param("amount")BigDecimal amount);

	String querySecurityCardIdByUserId(@Param("userId")String userId);
	
	int queryBindRepeatUser(@Param("userId")String userId,@Param("indentityNo")String indentityNo);

	//查询安全卡信息 必须是有效的
	TradeAccountCardDTO queryVaildSecurityCardInfoByUserId(@Param("userId")String userId);

	//查询定期理财产品可申购余额
	BigDecimal queryProductAvailableAmount(@Param("userId")String userId, @Param("productId")String productId);
	
	//更新用户的小赢理财预约购买标记
	int updatePurchaseRfFlag(@Param("userId")String userId);

	//新增用户的小赢理财预约购买标记
	void addPurchaseRfFlag(@Param("userId")String userId);
	
	//更新用户的小赢理财是否已经购买标记
	int updateIfPurchaseRfFlag(@Param("userId")String userId);

	//新增用户的小赢理财是否已经购买标记
	void addIfPurchaseRfFlag(@Param("userId")String userId);	
	
	//充值补单-查询充值历史记录
	WjEbatongTradeHistoryDTO queryWjEbatongTradeHistoryToRepair(@Param("outTradeNo")String outTradeNo);
	
	//修改充值订单状态
	int updateOrderStatus(MBOrderInfoDTO orderInfoDTO);
	
	//查询订单信息
	MBOrderInfoDTO queryOrderInfoByOrderNo(MBOrderInfoDTO orderInfoDTO);
	
	//修改充值历史表,充值状态
	void updateWjEbatongTradeHistoryStatus(@Param("orderNo")String orderNo);
	
	//查询银行卡号与银行号是否匹配
	int findCountWithBankCode(@Param("bankCode")String bankCode,@Param("cardNo")String cardNo);
	
	//用于查询用户账户是否锁定
	int findCountWithUserDrawLock(@Param("userId")String userId);
	
	//查询银行卡bin信息
	List<BankInfoDTO> findBankInfoList(@Param("bankCode")String bankCode);
	
	//借款账户增加余额
	void updateAddLnAmount(@Param("userId")String userId, @Param("amount")BigDecimal amount);
	
	//借款现金账户增加余额
	void updateAddLaAmount(@Param("userId")String userId, @Param("amount")BigDecimal amount);
	
	//借款现金账户锁定资金
	void updateFreezeLaAmount(@Param("userId")String userId, @Param("amount")BigDecimal amount);

	//借款现金账户解锁资金
	void cancelFreezeLaAmount(@Param("userId")String userId, @Param("amount")BigDecimal amount);
	
	int selectUserActionFlagByUserId(@Param("userId")String userId);

	void updateTAProductFinace(@Param("amount")BigDecimal amount) ;
	
	//解锁冻结资金
	void deductionFreezeMaAmount(@Param("userId")String userId, @Param("amount")BigDecimal amount);

	//更新订单状态
	void updateOrderStatusByOrderNos(@Param("userId")String userId,@Param("orderNos")List<String> orderNos,@Param("orderStatus")String orderStatus);

	//根据借款编号，查询对应的v理财订单编号
	List<String> queryOrderNoByTradeChannelIds(@Param("userId")String userId, @Param("loanIds")String[] loanIds);
	
    //将冻结的v理财的本金和收益，从rf冻结金额中扣除
	void cancelUserRfFrozenAmount(@Param("userId")String userId, @Param("orderNos")List<String> rfOrderNos);

    //将冻结的v理财的本金和收益，转入到ma账户
	void updateUserRfAmountToMa(@Param("userId")String userId, @Param("orderNos")List<String> rfOrderNos);

	//更新订单是否冻结的状态
	void updateOrderCancelForezenByOrderNos(@Param("userId")String userId, @Param("orderNos")List<String> orderNos);

    //更新还款计划是否冻结的状态
	void updateRepayPlanCancelForezenByOrderNos(@Param("userId")String userId, @Param("orderNos")List<String> orderNos);

    //新增rf冻结金额到ma账户的订单
	void addRfFrozen2MaOrderInfo(@Param("orderNos")List<String> oldOrderNo);

	//查询rf理财的本金及收益
	RfPrincipalAndInterestDTO queryRfPrincipalAndInterest(@Param("userId")String userId, @Param("orderNos")List<String> orderNos);

	/**
     * @param transferEaToMaConfirmOrder
     * @return
	 * @author zhangyingxuan
	 *  @date 20160721
	 *  插入产品信息
     */
	int insertOrder(MBOrderInfoDTO transferEaToMaConfirmOrder);

	/**
	 * 更新工资计划银行卡扣款订单状态
     *
	 * @param orderNo
	 * @param orderStatus
	 * @param oldOrderStatus
	 * @param title
	 */
	void updateSalaryPlanWithholdOrder(@Param("userId")String userId,@Param("orderNo")String orderNo,@Param("orderStatus")String orderStatus,
									   @Param("oldOrderStatus")String oldOrderStatus,@Param("title")String title);
	
	int queryWithholdBindRepeatCardNumByChannel(@Param("userId")String userId, @Param("cardNo")String cardNo, @Param("channelNo") String channelNo);

	/**
	 * 根据用户ID获取安全卡信息
     *
	 * @param userId
	 * @return
	 */
	UserTradeAccountCardDTO querySecurityCardByUserId(@Param("userId") String userId);

	List<UserTradeAccountInfoDTO> queryUserIdAndAvailableAmountList(@Param("tradeType")String tradeType, @Param("amount")String amount);

    /**
     * 根据订单类型查询用户是否已绑定相应渠道
     *
     * @param userId
     * @param cardNo
     * @param bizType
     * @return
     */
    int queryWithholdBindRepeatCardNumByBizType(@Param("userId") String userId, @Param("cardNo") String cardNo, @Param("bizType") String bizType);

    Boolean queryWithholdBankStatusByBankCode(@Param("bankCode") String bankCode, @Param("bizType") String bizType);
    

  
    void ipsUpdateFreezeMaAmount(@Param("ipsAcctNo")String ipsAcctNo, @Param("amount")BigDecimal amount);

	int queryProductInfor(@Param("productId") String productId);

	void updateIpsOrderStatus(Map map);

	void saveFreezeInfor(Map map);


	void addtOrderInfoIps(MBOrderInfoDTO applyMaToRfOrderIps);
	


	void setPacketNo(@Param("redBillNo")String redBillNo, @Param("userId")String userId,@Param("rpId") String rpId);
	
	
	String queryAgentIpsAcctNo(@Param(value = "projectId") String projectId);

	//提现
	void addtWithOrderInfo(MBOrderInfoDTO withdrawIpsToCard);

	void updateFreezeRepaymentOrderStatus(Map<String, Object> map);

	//ma 冻结账户增加（本息）
	void updateUserRepaymentPlanReceive(@Param(value = "userId") String userId, @Param(value = "totalPrice") BigDecimal totalPrice);

	//rf 账户扣除本金
	void updateUserRfAccount(@Param(value = "userId") String userId,@Param(value = "price")String planId);

	//rf 子账户扣除本金
	void updateUserRfAccountDetail(@Param(value = "userId") String userId,@Param(value = "ProductOrderNo")  String productOrderNo,@Param(value = "productId") String productId,@Param(value = "planId") String planId);

	//根据后管冻结成功是的订单号，查询理财产品的订单号
	HashMap<String,String> queryProductOrderNo(@Param(value = "orderNo") String orderNo);

	void updateOrderProduct(@Param(value = "userId") String userId,@Param(value = "productOrderNo") String productOrderNo);

	
	
	
	

    
}
