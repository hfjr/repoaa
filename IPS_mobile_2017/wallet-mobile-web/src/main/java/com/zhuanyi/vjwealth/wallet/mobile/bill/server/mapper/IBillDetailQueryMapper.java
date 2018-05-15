package com.zhuanyi.vjwealth.wallet.mobile.bill.server.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.BatchApplyDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.EaToMaDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.LoanRepayBillDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.RechargeDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.RfBillDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.TaToMaDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.V1BillDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.VFinacialFrozenDetailInnerDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.WageRepayOrderDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.WithdrawDetailDTO;

@Mapper
public interface IBillDetailQueryMapper {
	
	/**
	 * 充值
	 * @param orderId
	 * @return
	 */
	RechargeDetailDTO getRechargeBillDetail(@Param("orderId")String orderId);
	
	/**
	 * e账户提现
	 * @param orderId
	 * @return
	 */
	WithdrawDetailDTO getWithdrawEaBillDetail(@Param("orderId")String orderId);

    /**
     * @author zhangyingxuan
     * @date 20160714
     * ta账户提现
     * @param orderId
     * @return
     */
    WithdrawDetailDTO getWithdrawTaBillDetail(@Param("orderId")String orderId);
	
	/**
	 * 余额提现
	 * @param orderId
	 * @return
	 */
	WithdrawDetailDTO getWithdrawMaBillDetail(@Param("orderId")String orderId);
	
	/**
	 * 工资
	 * @param orderId
	 * @return
	 */
	BatchApplyDetailDTO getBatchApplyBillDetail(@Param("orderId")String orderId);
	
	/**
	 * 余额申购e账户
	 * @param orderId
	 * @return
	 */
	BatchApplyDetailDTO getMaToEaBillDetail(@Param("orderId")String orderId);

    /**
     * @author zhangyingxuan
     * @date 20160714
     * 余额申购e账户
     * @param orderId
     * @return
     */
    BatchApplyDetailDTO getMaToTaBillDetail(@Param("orderId")String orderId);
	
	/**
	 * e账户转到余额
	 * @param orderId
	 * @return
	 */
	EaToMaDetailDTO getEaToMaBillDetail(@Param("orderId")String orderId);

	/**
	 * @author zhangyingxuan
	 * @date 20160714
	 * ta账户转到余额
	 * @param orderId
	 * @return
	 */
	TaToMaDetailDTO getTaToMaBillDetail(@Param("orderId")String orderId);

	/**
	 * v1转到余额
	 * @param orderId
	 * @return
	 */
	V1BillDetailDTO getV1ToMaBillDetail(@Param("orderId")String orderId);
	
	/**
	 * 余额转到 v1
	 * @param orderId
	 * @return
	 */
	V1BillDetailDTO getMaToV1BillDetail(@Param("orderId")String orderId);
	
	
	
	/**
	 * 余额申购v理财
	 * @param orderId
	 * @return
	 */
	RfBillDetailDTO getMaToRfBillDetail(@Param("orderId")String orderId);


	/**
	 * 查询流通宝、锦囊还款
	 * @param orderId
	 * @return
     */
	LoanRepayBillDetailDTO getLoanRepayBillDetail(@Param("orderId")String orderId);

	/**
	 * 查询账单的对外业务编号
	 * @param userId 用户编号
	 * @param orderId 账单编号
     * @return
     */
	String queryOrderTradeChannelId(@Param("userId")String userId,@Param("orderId")String orderId);

	/**
	 * 查询定期理财的冻结金额相关信息
	 * @param userId 用户编号
	 * @param planId 还款计划编号
     * @return
     */
	VFinacialFrozenDetailInnerDTO queryOrderPlanInfo(@Param("userId")String userId,@Param("planId")String planId);

	/**
	 * 根据理财订单编号查询流通宝借款的贷款编号
	 * @param userId 用户编号
	 * @param orderNo 理财订单编号
     * @return
     */
	String queryLoanIdByOrderNo(@Param("userId")String userId,@Param("orderNo")String orderNo);

	/**
	 * 查询借款金额
	 * @param relOrderNo
     */
	String queryLoanApplyAmountByOrderNo(@Param("orderNo")String relOrderNo);

	/**
	 * 查询本次之前的还款次数
	 * @param userId
	 * @param orderNo
     * @return
     */
	int queryRepayCountByOrderNo(@Param("userId")String userId,@Param("orderNo")String orderNo);

	/**
	 * 查询工资先享还款历史
	 * @param userId
	 * @param orderId
	 * @return
	 */
	WageRepayOrderDTO queryWageRepayBillDetail(@Param("userId")String userId, @Param("orderId")String orderId);
}
