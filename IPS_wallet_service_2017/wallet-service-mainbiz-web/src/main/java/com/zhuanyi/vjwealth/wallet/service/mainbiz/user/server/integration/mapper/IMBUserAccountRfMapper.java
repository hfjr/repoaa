package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.rqb.ips.depository.platform.beans.IpsTransferAccounts;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RfRepayPlanDTO;

/**
 * @author xuejie
 *
 */
@Mapper
public interface IMBUserAccountRfMapper {
	
	MBUserInfoDTO queryUserInfoByUserid(@Param("userId") String userId, @Param("productId") String productId);
	
	MBUserInfoDTO queryUserInfoByUseridIps(@Param("userId") String userId, @Param("productId") String productId);
	
	void addUserRfInvestRecord(@Param("userId") String userId, @Param("amount") BigDecimal amount, @Param("orderNo") String orderNo, @Param("productId") String productId);

	void addUserRfInvestRepaymentPlan(List<RfRepayPlanDTO> planList);

	void addUserForzenInvestRepaymentPlan(List<RfRepayPlanDTO> planList);

	void addUserPolicyInfo(@Param("userId") String userId, @Param("orderNo") String orderNo, @Param("productId") String productId, @Param("policyNo") String policyNo, @Param("loanId") String loanId, @Param("tradeId") String tradeId, @Param("amount") String amount, @Param("name") String name, @Param("identifyNo") String identifyNo);

	void addUserOrderProductDetail(@Param("userId") String userId, @Param("orderNo") String orderNo, @Param("productId") String productId, @Param("repaymentPlanId") String repaymentPlanId, @Param("orderDetailNo") String orderDetailNo, @Param("amount") BigDecimal amount, @Param("productEndTime") Date productEndTime, @Param("contractTemplateNo") String contractTemplateNo, @Param("token") String token  );

	void addUserRfTradeAcountDetail(@Param("rfAcountDetailId") String rfAcountDetailId, @Param("userId") String userId, @Param("orderNo") String orderNo, @Param("productId") String productId);
	
	void updateMaLessFrozenAmountByApplyMaToRf(@Param("userId") String userId, @Param("orderNo") String orderNo);

	void updateMaLessFrozenAmountByApplyMaToRfWithRp(@Param("userId") String userId, @Param("orderNo") String orderNo);

	void updateUserRfAcountAmount(@Param("userId") String userId, @Param("orderNo") String orderNo);

	void updateConfirmShareOrderStatus(@Param("orderNo") String orderNo, @Param("orderStatus") String orderStatus);

	//更新定期理财产品，可申购余额
	int updateProductFinace(@Param("productId") String productId, @Param("amount") BigDecimal amount, @Param("lockVersion") int lockVersion);

	//更新定期理财产品，可申购余额
	int updateProductFinaceIps(@Param("productId")String productId,@Param("frozenAmount") BigDecimal frozenAmount,@Param("lockVersion")  int lockVersion);
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
	//是否异步更新保单🆗
	Boolean isAsyncUpdatePolicyNo();

	//根据token查询订单数量
	int queryProductOrderByToken(@Param("token") String token);

	//根据订单No查询订单
	MBOrderInfoDTO queryOrderByOrderNo(@Param("orderNo") String orderNo);
	//根据userId查询ipsacctno
	Map<String, Object> queryUserIpsAcctNo(@Param("userId")String userId);
	String queryUserIdByIpsAcctNo(@Param(value = "outIpsAcctNo") String outIpsAcctNo);

	void addOrderTransferRfToIps(@Param(value = "userId") String userId,@Param(value = "ipsTransferAccounts") IpsTransferAccounts ipsTransferAccounts);

	Map<String, String> queryOrderByFreezeId(@Param(value = "merBillNo") String merBillNo);

	void updateOrderTransferRfToIps(@Param(value = "userId")  String userId,@Param(value = "merBillNo") String merBillNo, @Param(value = "batchNo") String batchNo,  @Param(value = "ipsBillNo") String ipsBillNo, @Param(value = "trdStatus") String trdStatus);

	Boolean queryFreezeStatus(@Param(value = "merBillNo") String merBillNo, @Param(value = "projectNo") String projectNo);

	Map<String, BigDecimal> queryAmountByOrder( @Param(value = "userId") String userId,@Param(value = "orderNo") String orderNo,@Param(value = "productId") String productId);

	void addOrderTransferRfToIps(@Param(value = "userId") String userId,@Param(value = "orderNo") String orderNo,@Param(value = "totalPrice") String totalPrice, @Param(value = "price") String price,@Param(value = "productId") String productId,
			@Param(value = "batchNo") String batchNo,@Param(value = "freezeId") String freezeId);

	Map<String, BigDecimal> queryAmountByOrderBatchNoProjectNo(@Param(value = "merBillNo") String merBillNo,@Param(value = "batchNo")  String batchNo, @Param(value = "productId") String productId);

	Boolean queryTransferByOrderBatchNoProjectNo(@Param(value = "merBillNo") String merBillNo,@Param(value = "batchNo")  String batchNo,@Param(value = "projectNo") String projectNo);

	void updateAgentMaAcountAmount(@Param(value = "inIpsAcctNo") String inIpsAcctNo, @Param(value = "money") String money);

	

	//校验后管冻结同步处理结果
	boolean queryFreezeRepaymentOrderStatus(@Param(value = "merBillNo") String merBillNo);
	 
	void addOrderTransferRfToIps(@Param(value = "userId") String userId,@Param(value = "orderNo") String orderNo,@Param(value = "totalPrice") BigDecimal totalPrice, @Param(value = "price") BigDecimal price,@Param(value = "productId") String productId,
			@Param(value = "batchNo") String batchNo,@Param(value = "freezeId") String freezeId);

	
	
	
	
	
	

}
