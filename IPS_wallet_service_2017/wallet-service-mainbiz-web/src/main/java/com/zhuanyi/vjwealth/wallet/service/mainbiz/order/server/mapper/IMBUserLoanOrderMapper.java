package com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.mapper;

import java.util.Map;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.DueRepaymentBankCardWithholdDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.OrderInfoQueryDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.OrderRepayHistory;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MyBankCardInfoDTO;
import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.WjOrderDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;

/**
 * @author wangzhangfei
 */
@Mapper
public interface IMBUserLoanOrderMapper {

   
    /**
     * 查询用户基本信息 
     * @param userId
     * @return
     * @since 3.5
     */
    Map<String,String> queryUserBasicInfoById(@Param("userId") String userId);
    
    /**
     * 
     * @param transferEaToMaConfirmOrder
     * @return
     */
    int addPledgeLoanOrder(MBOrderInfoDTO transferEaToMaConfirmOrder);
	/**
	 *
	 * @param transferEaToMaConfirmOrder
	 * @return
	 */
	int addSalaryPlanOrder(MBOrderInfoDTO transferEaToMaConfirmOrder);


    /**
     * 更新v理财
     * @param orderId
     */
	int updateRelOrderInfo(@Param("orderNo")String orderId,@Param("oldFrozenStatus")String oldFrozenStatus,@Param("newFrozenStatus")String newFrozenStatus);


	/**
	 * 更新还款计划,冻结
	 * @param orderId
	 */
	void updateRfRepayPlanToFrozen(@Param("orderNo")String orderId);

	/**
	 * 更新还款计划,解冻
	 * @param orderId
	 */
	void updateRfRepayPlanToUnFrozen(@Param("orderNo")String orderId);

	/**
	 * 检查理财是否到期
	 * @param userId
	 * @param loanId
	 * @return
	 */
	boolean checkFinancialIsDue(@Param("userId")String userId,@Param("loanId") String loanId);

	/**
	 * 检查是否进行过到期还款（处理中，及处理完成的）
	 * @param userId
	 * @param planId
     * @return
     */
	boolean checkIsRepeatRepay(@Param("userId")String userId,@Param("planId") String planId);
	
	
	/**
	 * 检查cana是否已还款
	 * @param userId
	 * @param planId
	 * @return
	 */
	boolean checkIsRepeatRepayForCana(@Param("userId")String userId,@Param("planId") String planId);

	/**
	 * 检查cana是否已还款（自动还款）
	 * @param userId
	 * @param planId
	 * @return
	 */
	boolean checkEarlyRepaymentIsRepeatRepayForCana(@Param("userId")String userId,@Param("planId") String planId);
	
	/**
	 * 
	 * @param wjOrderDTO
	 * @return
	 */
	boolean checkIsRepeatRepayComm(WjOrderDTO wjOrderDTO);
	

	/**
	 * 检查是否进行过提前还款（处理中，及处理完成的）
	 * @param userId
	 * @param planId
	 * @return
	 */
	boolean checkIsRepeatEarlyRepay(@Param("userId")String userId,@Param("planId") String planId);

	/**
	 * 增加贷款利息记录
	 */
	void addInterestRecord(@Param("userId")String userId,@Param("loanId")String loanId,@Param("planId")String planId,@Param("currentDayInterest")String currentDayInterest);

	/**
	 * 检查该笔订单是否抵押过
	 * @param userId
	 * @param orderId
     * @return
     */
	boolean checkIsRepeatApplyLoan(@Param("userId")String userId,@Param("orderId") String orderId);

	/**
	 * 更新贷款申请订单的信息
	 * @param userId 用户编号
	 * @param applyOrderNo 贷款申请订单的编号
	 * @param orderStatus 订单状态
	 * @param loanId 借款订单编号
     * @return
     */
	int updateApplyLoanOrder(@Param("userId")String userId,@Param("applyOrderNo")String applyOrderNo,@Param("orderStatus")String orderStatus,@Param("title")String title,@Param("loanId")String loanId);


	/**
	 * 工资先享-检查是否存在处理中的订单
	 * @param userId
	 * @param loanId
	 * @return
	 */
	boolean checkWageAdvanceIsExistProcessOrder(@Param("userId")String userId,@Param("loanId") String loanId);

	/**
	 * 工资先享-检查是否进行过到期还款（处理中，及处理完成的）
	 * @param userId
	 * @param planId
	 * @return
	 */
	boolean checkWageAdvanceIsDueRepeatRepay(@Param("userId")String userId,@Param("planId") String planId);


	/**
	 * 京东贷款-检查是否进行过到期还款（处理中，及处理完成的）
	 * @param userId
	 * @param planId
     * @return
     */
	boolean checkVjSelfLoanIsDueRepeatRepay(@Param("userId")String userId,@Param("planId") String planId);



	/**
	 * 工资先享 - 查询对应的借款借款订单编号
	 * @param userId
	 * @param loanId
	 * @return
	 */
	String queryApplyLoanOrderId(@Param("userId")String userId,@Param("loanId") String loanId,@Param("orderType") String orderType);

	/**
	 * 查询银行卡详细信息
	 * @param cardNo
	 * @return
     */
	MyBankCardInfoDTO queryCardDetailInfoByCardNo(@Param("userId")String userId,@Param("cardNo")String cardNo);

	/**
	 * 查询工资先享审核中的订单
	 * @param userId
	 * @param loanId
     * @return
     */
	Map<String,String> queryUserWageAdvanceProcessOrderInfo(@Param("userId")String userId,@Param("loanId")String loanId);


	/**
	 * 查询扣款订单对应的还款订单
	 * @param orderNo
	 * @return
     */
	String queryRepayOrderNos(@Param("userId")String userId,@Param("orderNo")String orderNo);

	/**
	 * 查询订单信息
	 * @param userId 用户编号
	 * @param orderNo 订单号
     * @return
     */
	OrderInfoQueryDTO queryOrderInfoByOrderNo(@Param("userId")String userId,@Param("orderNo")String orderNo);

	/**
	 * 查询订单状态
	 * @param userId 用户编号
	 * @param orderNo 订单号
	 * @return
	 */
	String queryOrderStatusByOrderNo(@Param("userId")String userId,@Param("orderNo")String orderNo);

	/**
	 * 根据订单编号，查询用户ID
	 * @param bizNo
	 * @return
     */
	String queryUserIdByBizNo(@Param("bizNo")String bizNo);

	/**
	 * 新增还款历史记录
	 * @param
	 * @return
	 */
	int addOrderRepayHistory(OrderRepayHistory orderRepayHistory);

	/**
	 * 更新还款历史记录
	 * @param
	 * @return
	 */
	int updateOrderRepayHistory(OrderRepayHistory orderRepayHistory);

	/**
	 * 查询还款历史记录
	 * @param orderNo
	 * @return
     */
	OrderRepayHistory queryOrderRepayHistory(@Param("orderNo")String orderNo);


	/**
	 * 查询到期还款的账单信息
	 * @param orderNo
	 * @return
     */
	DueRepaymentBankCardWithholdDTO queryWageAdvanceRepayInfo(@Param("userId")String userId,@Param("orderNo")String orderNo);

	/**
	 * 判断该张银行卡今天是否针对某笔还款计划扣过款
	 * @param cardId
	 * @param planId
     * @return
     */
	boolean validatorBankCardIsWithhold(@Param("cardId")String cardId, @Param("planId")String planId,@Param("orderType")String orderType);

	/**
	 * 查询数据库当前时间
	 * @return
     */
	String queryCurrentTime();


	/**
	 * 判断是否是安全卡
	 * @param cardNo
	 * @return
     */
	boolean checkIsSecurityCard(@Param("cardNo")String cardNo);

	
	/**
	 * picc检查是否进行过到期还款（处理中，及处理完成的）
	 * @param userId
	 * @param planId
	 * @return
	 */
	boolean checkPiccLoanIsDueRepeatRepay(@Param("userId")String userId,@Param("planId") String planId);

}
