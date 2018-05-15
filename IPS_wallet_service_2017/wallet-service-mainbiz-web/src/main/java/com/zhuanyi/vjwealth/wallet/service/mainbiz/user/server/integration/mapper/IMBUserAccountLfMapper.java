package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RfRepayPlanDTO;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author xuejie
 *
 */
@Mapper
public interface IMBUserAccountLfMapper {
	
	MBUserInfoDTO queryUserInfoByUserid(@Param("userId") String userId, @Param("productId") String productId);
	
	MBUserInfoDTO queryProductInfoByProductid(@Param("productId") String productId);
	
	void addUserRfInvestRecord(@Param("userId") String userId, @Param("amount") BigDecimal amount, @Param("orderNo") String orderNo, @Param("productId") String productId);

	void addUserRfInvestRepaymentPlan(List<RfRepayPlanDTO> planList);
	
	void addUserPolicyInfo(@Param("userId") String userId, @Param("orderNo") String orderNo, @Param("productId") String productId, @Param("policyNo") String policyNo, @Param("loanId") String loanId, @Param("tradeId") String tradeId, @Param("amount") String amount, @Param("name") String name, @Param("identifyNo") String identifyNo);

	void addUserOrderProductDetail(@Param("userId") String userId, @Param("orderNo") String orderNo, @Param("productId") String productId, @Param("repaymentPlanId") String repaymentPlanId, @Param("orderDetailNo") String orderDetailNo, @Param("amount") BigDecimal amount, @Param("productEndTime") Date productEndTime, @Param("contractTemplateNo") String contractTemplateNo, @Param("token") String token  );

	void addUserRfTradeAcountDetail(@Param("rfAcountDetailId") String rfAcountDetailId, @Param("userId") String userId, @Param("orderNo") String orderNo, @Param("productId") String productId);
	
	void updateMaLessFrozenAmountByApplyMaToRf(@Param("userId") String userId, @Param("orderNo") String orderNo);
	
	void updateUserRfAcountAmount(@Param("userId") String userId, @Param("orderNo") String orderNo);
	
	void updateConfirmShareOrderStatus(@Param("orderNo") String orderNo, @Param("orderStatus") String orderStatus);

	//更新定期理财产品，可申购余额
	int updateProductFinace(@Param("productId") String productId, @Param("amount") BigDecimal amount, @Param("lockVersion") int lockVersion);

	//更新 RF 保单编号
	void updateUserPolicyInfoByOrderNo( @Param("orderNo") String orderNo, @Param("policyNo") String policyNo);

	//取消订单后，更新定期理财产品，可申购余额
	int cancelProductFinace(@Param("productId") String productId, @Param("amount") BigDecimal amount, @Param("lockVersion") int lockVersion);

	void addRfNewestActivity(@Param("userId") String userId, @Param("orderNo") String orderNo, @Param("actionTitle") String actionTitle, @Param("actionType") String actionType, @Param("actionDescription") String actionDescription);

	//mock start
	String queryProductRepaymentsTypeByLoanId(@Param("loadId") String loadId);

	String getRepayMaturityMock();

	String getMonthlyInterestMock();

	Boolean isRepayPlanMockMode();
    //mock end

	//根据token查询订单数量
	int queryProductOrderByToken(@Param("token") String token);

	//根据订单No查询订单
	MBOrderInfoDTO queryOrderByOrderNo(@Param("orderNo") String orderNo);

	BigDecimal selectAddUpInvestmentAmount(@Param("userId") String userId, @Param("thattime") Date thattime);
	
	Date selectCreditIncreasingTimeFlag(@Param("userId") String userId);
	
	BigDecimal selectCreditRemainCount(@Param("userId") String userId);
	
	int updateCreditIncreasingTimeFlag(@Param("userId") String userId, @Param("amount") BigDecimal amount);

	String queryLoanOrderByOrderNo(@Param("orderNo")String orderNo);
}
