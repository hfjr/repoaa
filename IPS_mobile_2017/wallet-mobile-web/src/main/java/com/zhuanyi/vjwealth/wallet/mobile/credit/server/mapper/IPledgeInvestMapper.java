package com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper;

import java.util.List;

import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct.InverstOrder;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest.PledgeInvestBorrowInitiDTO;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest.PledgeInvestInitiDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest.PledgeInvestInitiInnerOrderInfoDTO;

/**
 * Created by wangzhangfei on 16/7/12.
 */
@Mapper
public interface IPledgeInvestMapper {

	/**
	 * 查询v理财投资汇总信息
	 * @param userId
	 * @return
	 */
	PledgeInvestInitiDTO queryPledgeInvestIniti(@Param("userId")String userId);

	/**
	 * 查询v理财投资汇总信息
	 * @param userId
	 * @return
	 */
	PledgeInvestInitiDTO queryPledgeInvestInitiV2(@Param("userId")String userId,@Param("loanAmount")String loanAmount);

	/**
	 * 查询v理财投资记录信息（未领过奖品）
	 * @param userId
	 * @param page
	 * @return
	 */
	List<PledgeInvestInitiInnerOrderInfoDTO> queryPledgeInvestInitiInnerOrderList(@Param("userId")String userId, @Param("page")Integer page);

	/**
	 * 查询v理财投资记录信息（未领过奖品）新版本
	 * @param userId
	 * @return
	 */
	List<InverstOrder> queryPledgeInvestInitiInnerOrderListV2(@Param("userId")String userId, @Param("borrowAmount")String borrowAmount);


	/**
	 * 查询v理财投资记录信息（领过奖品）
	 * @param userId
	 * @param page
	 * @return
	 */
	List<PledgeInvestInitiInnerOrderInfoDTO> queryPledgeInvestInitiInnerOrderListLimit(@Param("userId")String userId, @Param("page")Integer page);


	/**
	 * 查询v理财投资记录信息（领过奖品）新版本
	 * @param userId
	 * @return
	 */
	List<InverstOrder> queryPledgeInvestInitiInnerOrderListLimitV2(@Param("userId")String userId,@Param("borrowAmount")String borrowAmount);

	/**
	 * 查询用户手机号
	 * @param userId
	 * @return
	 */
	String queryUserPhoneByUserId(@Param("userId")String userId);


    /**
	 * 查询流通宝借款初始化
	 * @param userId
	 * @param orderId
	 * @return
     */
	PledgeInvestBorrowInitiDTO queryPledgeInvestBorrowIniti(@Param("userId")String userId, @Param("orderId")String orderId);


	/**
	 * 查询v理财投资订单信息
	 * @param userId
	 * @param orderId
     * @return
     */
	PledgeInvestInitiInnerOrderInfoDTO queryPledgeInvestBorrowOrderInfo(@Param("userId")String userId, @Param("orderId")String orderId);

	/**
	 * 计算: 1. 到期还本付息: 所有的收益总和 2. 按月付息: 剩余的收益总和
	 * @param userId
	 * @param orderId
	 * @return
	 */
	String queryIncomeSumByUserIdAndOrderId(@Param("userId")String userId, @Param("orderId")String orderId);

	/**
	 * 查询用户余额
	 * @param userId
	 * @return
	 */
	String queryUserMaAmountByUserId(@Param("userId")String userId);

	/**
	 * 查看该用户是否领取过奖励
	 * @param userId
     */
	boolean queryFinacialRewardSendInfo(@Param("userId")String userId);


}
