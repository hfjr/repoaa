package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.DueRepaymentInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.EarlyRepaymentInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBRechargeDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RfResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 事务交易处理业务类
 * @author xuejie
 *
 */
public interface IUserAccountTransactionService {

	String getId(String prefix, String sequenceName);

	public void transferEaToMa(String userId, BigDecimal transferMoney);

	/**
	 * @author zhangyingxuan
	 * @date 20160708
	 * @param userId
	 * @param transferMoney
	 */
	public void transferTaToMa(String userId, BigDecimal transferMoney);

	public void transferV1ToMa(String userId, BigDecimal amount);

	public void withdrawEaToBank(String userId, String orderNo, BigDecimal amount, String transStatus);

	/**
	 * ta账户提现到银行卡
	 * @author zhangyingxuan
	 * @date 20160712
	 * @param userId
	 * @param orderNo
	 * @param amount
	 * @param transStatus
	 */
	void withdrawTaToBank(String userId, String orderNo, BigDecimal amount, String transStatus);

	void withdrawMaToBank(String userId, BigDecimal amount,String orderId);

	void applyMaToV1(String userId, BigDecimal amount);

	void applyMaToEa(String userId, BigDecimal amount);

	/**
	 * @author zhangyingxuan
	 * @date 20160708
	 * 转账到T金所账户
	 * @param userId
	 * @param amount
	 */
	void applyMaToTa(String userId, BigDecimal amount);
	void applyMaToTaNoTrans(String userId, BigDecimal amount);
	void doRechargeToMa(MBRechargeDTO rechargeDTO);

	void doRechargeToMaAlreadyBind(MBRechargeDTO rechargeDTO);

	String applyMaToRf(String userId, String productId, BigDecimal amount, int lockVersion, String token, String tradeId);
	
	void ipsApplyMaToRf(String userId, String productId, BigDecimal amount, int lockVersion);

	//红包订单号
	void applyMaToRfPacketIps(String redBillno, String userId, String rpId, String productId, BigDecimal amount,
			BigDecimal rpValue, int lockVersion, String token, String tradeId);
	//其他冻结订单号
	
	
	/**
	 * @author sunxiaolei
	 * @date 20160721
	 * 采用红包购买理财产品
	 * @param userId
	 * @param productId
	 * @param amount
	 * @param
	 * @param lockVersion
	 * @param token
	 * @param tradeId
	 * @return
	 */
	/**
	 * @author zhangyingxuan
	 * @date 20160721
	 * 采用红包购买理财产品
	 * @param userId
	 * @param productId
	 * @param amount
	 * @param
	 * @param lockVersion
	 * @param token
	 * @param tradeId
	 * @return
	 */
	String applyMaToRf(String userId, String productId, BigDecimal amount, BigDecimal rpValue, int lockVersion, String token, String tradeId);

	void processRfOrder(MBUserInfoDTO userinfo, RfResponseDTO dto, String token);
	
	void processRfIpsOrder(MBUserInfoDTO userinfo, RfResponseDTO dto, String token);
	
	
	void processRfIpsOrder(MBUserInfoDTO userinfo, RfResponseDTO dto, String inIpsAcctNo,String money);

    /**
     * @author zhangyingxuan
     * @date 20160807
     * 采用红包购买定期理财
     * @param userinfo
     * @param dto
     * @param token
     */
	void processRfOrderWithRp(MBUserInfoDTO userinfo, RfResponseDTO dto, String token);

	void cancelMaToRf(String userId, String orderNo, String productId, BigDecimal amount, int lockVersion);

	void doRechargeToMaToDispose(MBRechargeDTO rechargeDTO);

	void doBindCard(MBRechargeDTO rechargeDTO);

	void doRechargeToMaAlreadyBindToDisposeForWithhold(MBRechargeDTO rechargeDTO);

	void doRechargeToMaAlreadyBindToDispose(MBRechargeDTO rechargeDTO);

	boolean doRechargeToMaCallback(MBOrderInfoDTO orderInfoDTO);

	boolean doRechargeToNotice(MBOrderInfoDTO orderInfoDTO);

	String applyLaToLf(String userId, String productId, BigDecimal amount, int lockVersion, String token, String tradeId);

	void processLfOrder(MBUserInfoDTO userinfo, RfResponseDTO dto, String token);



	/**
	 * 提前还款
	 * @param userId
	 * @param amount
	 * @param list
	 * @return
	 */
	public List<String> processEarlyRepayment(String userId,BigDecimal amount, List<EarlyRepaymentInfoDTO> list);

	/**
	 * cana
	 * @param userId
	 * @param amount
	 * @param orderNos
	 * @param orderStatus
	 */
	public void processRepaymentDeductionFrozenAmount(String userId,BigDecimal amount,List<String> orderNos,String orderStatus);

	/**
	 * cana解冻 
	 * @param userId
	 * @param amount
	 * @param orderNos
	 * @param orderStatus
	 */
	public void processAutoRepaymentDeductionFrozenAmountForCana(String userId,BigDecimal amount,List<String> orderNos,String orderStatus,String planId);

	/**
	 * 流通宝-提前还款-更新订单，并做结清处理
	 * @param userId
	 * @param amount
	 * @param orderNos
	 * @param isEnd
	 * @param loanIds
	 */
	public void  processRepaymentDeductionFrozenAmount(String userId,BigDecimal amount, List<String> orderNos,String isEnd,String[] loanIds,List<EarlyRepaymentInfoDTO> repaymentInfo);


	/**
	 * Cana
	 * @param userId
	 * @param totalAmount
	 * @param resultList
	 * @return
	 */
	List<String> processEnjoyAutoRepaymentMaToFrozen(String userId, BigDecimal totalAmount, List<EarlyRepaymentInfoDTO> resultList);



	/**
	 * 流通宝--到期还款
	 * @param userId
	 * @param loanCode
	 * @param repayTotal
	 * @return
	 */
	String processDueRepayment(String userId, String loanCode,String planId,BigDecimal repayTotal);


	/**
	 * 流通宝-到期还款-更新订单，并做结清处理
	 * @param userId
	 * @param amount
	 * @param orderNo
	 */
	public void  processDueRepaymentDeductionFrozenAmount(String userId,BigDecimal amount,BigDecimal actualPrincipal,BigDecimal interest, String orderNo,String loanId);

	/**
	 * 流通宝-到期还款-到期清算
	 * @param userId 用户编号
	 * @param loanCode 贷款订单编号
	 * @param planId 贷款还款计划编号
	 * @param princal 应还本金
	 * @param interest 应还利息
	 * @param repaymentTotalMoney 应还总额
	 * @return
	 */
	DueRepaymentInfoDTO liquidationRfAndLoanAmount(String userId, String loanCode, String planId,BigDecimal princal,BigDecimal interest,BigDecimal repaymentTotalMoney);

	/**
	 * cana
	 * @param userId
	 * @param amount
	 * @param orderNo
	 * @param orderTypeCanaRepayConfirm
	 */
	void processRepaymentDeductionFrozenAmountForCana(String userId,BigDecimal amount,String loanId, String orderNo, String orderTypeCanaRepayConfirm);

	/**
	 * 工资先享--提前还款下处理中订单
	 * @param userId
	 * @param repayTotal
	 * @param repaymentInfo
     * @return
     */
	List<String> processWageAdvanceEarlyRepayment(String userId, BigDecimal repayTotal, List<EarlyRepaymentInfoDTO> repaymentInfo);

	/**
	 * 工资先享--提前还款更新还款中的订单
	 * @param userId
	 * @param repayTotal
	 * @param orderNos
	 * @param strings
	 * @param repaymentInfo
     */
	void processWageAdvanceDeductionFrozenAmount(String userId, BigDecimal repayTotal, List<String> orderNos, String[] strings, List<EarlyRepaymentInfoDTO> repaymentInfo);

	/**
	 * 工资先享--到期还款
	 * @param userId
	 * @param loanCode
	 * @param repayTotal
	 * @return
	 */
	String processWageAdvanceDueRepayment(String userId, String loanCode,String planId,BigDecimal repayTotal);

	/**
	 * 工资先享-到期还款-更新订单
	 * @param userId
	 * @param amount
	 * @param orderNo
	 */
	public void  processWageAdvanceDueRepaymentDeductionFrozenAmount(String userId,BigDecimal amount,BigDecimal actualPrincipal,BigDecimal interest, String orderNo,String loanId);


	/**
	 * 工资先享 - 生成银行卡扣款中的订单
	 * @param userId
	 * @param repayTotal
	 * @param repaymentInfo
     * @return
     */
	String processWageAdvanceAddWithholdOrder(String userId, String cardId,String loanCodes,BigDecimal principal, BigDecimal repayTotal, List<EarlyRepaymentInfoDTO> repaymentInfo);
	/**
	 * 工资计划 - 生成银行卡扣款中的订单
	 * @param userId
	 * @param cardId
	 * @param planCode
	 * @param amount
	 * @return
	 */
	String processSalaryPlanAddWithholdOrder(String userId, String cardId, String planCode, BigDecimal amount);

	void applyMaToRfBidIps(String bidBillNo, String userId, String productId, BigDecimal amount, BigDecimal rpValue, String couponId, int lockVersion,
			String token, String tradeId);
	
	void withdrawIpsToCard(String userId, BigDecimal amount,BigDecimal fee,String orderId);

	boolean doWithdrawToMaCallback(MBOrderInfoDTO orderInfoDTO);

	boolean dotransferS2sCallBack(MBOrderInfoDTO orderInfoDTO);

	boolean doOpenaccountOrder(MBOrderInfoDTO orderInfoDTO);
	



	
}
