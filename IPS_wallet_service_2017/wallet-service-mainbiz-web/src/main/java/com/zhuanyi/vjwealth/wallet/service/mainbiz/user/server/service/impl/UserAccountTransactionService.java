package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.rqb.ips.depository.platform.mapper.IpsOpenUserResultMapper;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.WjEbatongTradeHistoryDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.DueRepaymentInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.EarlyRepaymentInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.OrderRepayHistory;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.RfPrincipalAndInterestDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.mapper.IMBUserLoanOrderMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.enums.BizStatusEnum;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.integration.mapper.IMBTradeRecordMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.IPSOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBRechargeDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RfResponseDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.ICouponMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountLfMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountRfMapper;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IpsWithDrawMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBProductProcess;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IUserAccountTransactionService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.service.IYingZTContractInfoService;
@Service
public class UserAccountTransactionService implements IUserAccountTransactionService {

	@Autowired
	private IMBUserAccountMapper userAccountMapper;
	@Autowired
	private ISequenceService sequenceService;
	
	@Autowired
	@Qualifier("MBWjProductProcess")
	private IMBProductProcess mBWjProductProcess;
	
	@Autowired
	@Qualifier("MBRemoteProductProcess")
	private IMBProductProcess mBProductProcess;
	
	
	@Autowired
	private IMBUserAccountRfMapper userAccountRfMapper;

	@Autowired
	private IMBUserAccountLfMapper userAccountLfMapper;
	
	@Autowired
	private IpsOpenUserResultMapper ipsOpenUserResultMapper;
	
	@Autowired
	private ICouponMapper couponMapper;

	@Autowired
	private IYingZTContractInfoService yingZTContractInfoService;

	@Autowired
	private IMBUserLoanOrderMapper mBUserLoanOrderMapper;

	@Autowired
	private IMBTradeRecordMapper tradeRecordMapper;
	
	@Autowired
	private IpsWithDrawMapper ipsWithDrawMapper;

	@Transactional
	public void transferEaToMa(String userId, BigDecimal amount) {
		// 1. ea 账户余额扣减
		userAccountMapper.updateLessEaAmount(userId, amount);

		// 2. ma 主账户可用余额增加
		userAccountMapper.updateAddMaAmount(userId, amount);

		// 3. 下订单
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getTransferEaToMaConfirmOrder(userId, orderNo, amount));
	}

	@Transactional
	public void transferTaToMa(String userId, BigDecimal amount) {
		// 1. ta 账户余额扣减
		userAccountMapper.updateLessTaAmount(userId, amount);

		// 2. ma 主账户可用余额增加
		userAccountMapper.updateAddMaAmount(userId, amount);

		// 3. 下订单
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getTransferTaToMaConfirmOrder(userId, orderNo, amount));
	}

	public String getId(String prefix, String sequenceName) {
		return prefix + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequenceService.getNextStringValue(sequenceName, 10);
	}

	@Transactional
	public void transferV1ToMa(String userId, BigDecimal amount) {

		// 1. v1 锁定资金到冻结资金去
		userAccountMapper.updateFreezeV1Amount(userId, amount);

		// 2. 下冻结订单
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getTransferV1ToMaConfirmOrder(userId, orderNo, amount));
	}

	@Transactional
	public void withdrawEaToBank(String userId, String orderNo, BigDecimal amount, String transStatus) {
		// 1. 扣减资金
		userAccountMapper.updateLessEaAmount(userId, amount);
		// 2. 下单
		//提现的安全卡ID
		String tradeAccountCardId=userAccountMapper.querySecurityCardIdByUserId(userId);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getWithdrawEaOrder(userId, orderNo, amount, transStatus,tradeAccountCardId));
	}

	@Transactional
	public void withdrawTaToBank(String userId, String orderNo, BigDecimal amount, String transStatus) {
		// 1. 扣减资金
		userAccountMapper.updateLessTaAmount(userId, amount);
		// 2. 下单
		//提现的安全卡ID
		String tradeAccountCardId=userAccountMapper.querySecurityCardIdByUserId(userId);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getWithdrawTaOrder(userId, orderNo, amount, transStatus,tradeAccountCardId));
	}

	@Transactional
	public void withdrawMaToBank(String userId, BigDecimal amount,String orderId) {
		// 1. 主账户 锁定资金到冻结资金去
		userAccountMapper.updateFreezeMaAmount(userId, amount);
		// 2. 下冻结订单
		//String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		//提现的安全卡ID
		//String tradeAccountCardId=userAccountMapper.querySecurityCardIdByUserId(userId);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getWithdrawMaOrder(userId, orderId, amount,null));
	}
	
	/**
	 * 用户ips提现
	 * 
	 * fee 手续费
	 */ 
	@Transactional
	public void withdrawIpsToCard(String userId, BigDecimal amount,BigDecimal fee,String orderId) {
		// 1. 主账户 锁定资金到冻结资金去
		userAccountMapper.updateFreezeMaAmount(userId, amount);
		// 2. 下冻结订单
		//String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		//提现的安全卡ID
		//String tradeAccountCardId=userAccountMapper.querySecurityCardIdByUserId(userId);
		
		userAccountMapper.addtWithOrderInfo(MBOrderInfoDTO.withdrawIpsToCard(userId, orderId, amount,fee));
	}

	
	
	@Transactional
	public void applyMaToV1(String userId, BigDecimal amount) {
		// 1. 从ma 账户扣除金额
		userAccountMapper.updateFreezeMaAmount(userId, amount);

		// add by Speed J 增加v1购买记录
		int count = userAccountMapper.selectUserActionFlagByUserId(userId);
//		int count = userAccountMapper.updatePurchaseV1Flag(userId);
		if(count == 0){
			userAccountMapper.addPurchaseV1Flag(userId);
		}
		else{
			userAccountMapper.updatePurchaseV1Flag(userId);
		}

		//2.v理财剩余总份额减去当前申购份额
		userAccountMapper.updateRemainTotalAmount(amount);

		// 3. 下单申购v1 理财
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getApplyMaToV1Order(userId, orderNo, amount));
	}

	@Transactional
	public void applyMaToEa(String userId, BigDecimal amount) {
		// 1. 从ma 账户扣除金额
		userAccountMapper.updateFreezeMaAmount(userId, amount);
		// 2. 下单申购v1 理财
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getApplyMaToEaOrder(userId, orderNo, amount));
	}

	@Transactional
	public void applyMaToTa(String userId, BigDecimal amount) {
		// 1. 从ma 账户扣除金额
		userAccountMapper.updateFreezeMaAmount(userId, amount);
		// 2. 下单申购v1 理财
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getApplyMaToTaOrder(userId, orderNo, amount));
		//3. T金所产品  剩余金额去除
		userAccountMapper.updateTAProductFinace(amount);
	}
	public void applyMaToTaNoTrans(String userId, BigDecimal amount) {
		// 1. 从ma 账户扣除金额
		userAccountMapper.updateFreezeMaAmount(userId, amount);
		// 2. 下单申购v1 理财
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getApplyMaToTaOrder(userId, orderNo, amount));
		//3. T金所产品  剩余金额去除
		userAccountMapper.updateTAProductFinace(amount);
	}

	@Transactional
	public void doRechargeToMa(MBRechargeDTO rechargeDTO) {
		String cardId = rechargeDTO.getCardId();
		// 1. 查询用户是否是没有卡
		int cardCount = userAccountMapper.queryCardCountByUserId(rechargeDTO.getUserId());
		if(cardCount == 0){
			// 生成新安全卡卡号id
			rechargeDTO.setCardId(getId("AC", ISequenceService.SEQ_NAME_CARD_SEQ));
			// 1.1 增加安全卡
			userAccountMapper.addUserSecurityCardInfo(rechargeDTO);
			// 1.2 更新用户实名信息
			// 性别
			userAccountMapper.updateUserRealNameInfo(rechargeDTO);
		}
		// 2. 增加一个充值卡
		rechargeDTO.setCardId(cardId);
		userAccountMapper.addUserRechargeCardInfo(rechargeDTO);
		// 3. 更新增加主账户余额
		BigDecimal amount = new BigDecimal(rechargeDTO.getAmount()).setScale(2, BigDecimal.ROUND_FLOOR);
		userAccountMapper.updateAddMaAmount(rechargeDTO.getUserId(), amount);
		// 4. 增加一个充值订单
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getDoRechargeToMa(rechargeDTO.getUserId(), rechargeDTO.getOrderNo(), amount, cardId));
	}

	@Transactional
	public void doRechargeToMaAlreadyBind(MBRechargeDTO rechargeDTO) {
		// 1. 更新增加主账户余额
		BigDecimal amount = new BigDecimal(rechargeDTO.getAmount()).setScale(2, BigDecimal.ROUND_FLOOR);
		userAccountMapper.updateAddMaAmount(rechargeDTO.getUserId(), amount);
		// 2. 增加一个充值订单
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getDoRechargeToMa(rechargeDTO.getUserId(), rechargeDTO.getOrderNo(), amount, rechargeDTO.getCardId()));
	}

	@Transactional
	public String applyMaToRf(String userId, String productId, BigDecimal amount, int lockVersion, String token, String tradeId) {
		// 1. 从ma 账户扣除金额到冻结
		userAccountMapper.updateFreezeMaAmount(userId, amount);

		// 2. 下单申购rf
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getApplyMaToRfOrder(userId, orderNo, amount, productId, token, tradeId));

		// 3.定期理财产品剩余总份额减去当前申购份额
		int updatecount = userAccountRfMapper.updateProductFinace(productId, amount, lockVersion);
		if (updatecount < 1){
			throw new AppException("购买失败");//产品更新剩余份额遭遇乐观锁
		}
		return orderNo;
	}
	
	
	 /**
	  * ips 无红包
	  */
	//@Transactional
	public void ipsApplyMaToRf(String userId, String productId, BigDecimal amount, int lockVersion) {
		// 1. 从ma 账户扣除金额到冻结 
		
		userAccountMapper.updateFreezeMaAmount(userId, amount);
		// 2. 下单申购rf
		/*String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getApplyMaToRfOrder(userId, orderNo, amount, productId, token, tradeId));*/
		
		// 3.定期理财产品剩余总份额减去当前申购份额
		int updatecount = userAccountRfMapper.updateProductFinace(productId, amount, lockVersion);
		if (updatecount < 1){
			throw new AppException("购买失败");//产品更新剩余份额遭遇乐观锁
		}
		BaseLogger.audit("============ma账户金额冻结,理财产品份额减少==========");
		/*return orderNo;*/
	}

	
	
	@Transactional
	public String applyMaToRf(String userId, String productId, BigDecimal amount,BigDecimal rpValue, int lockVersion, String token, String tradeId) {
		// 生成充值到红包账户的订单
		String orderNo;
		if (rpValue.compareTo(new BigDecimal(0)) > 0) {
			orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
			userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getRechargeRpOrder(userId, orderNo, rpValue, productId, token, tradeId));
		}

		// 1. 从ma 账户扣除金额到冻结
		BigDecimal frozenAmount = amount;
		if (rpValue.compareTo(new BigDecimal(0)) > 0) {//冻结金额减去红包金额
			frozenAmount = amount.subtract(rpValue);
		}
		userAccountMapper.updateFreezeMaAmount(userId, frozenAmount);

		// 2. 下单申购rf
		orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		if (rpValue.compareTo(new BigDecimal(0)) > 0) {//采用红包购买理财产品，红包金额更新到order的活动金额字段（activity_amount）
			userAccountMapper.insertOrder(MBOrderInfoDTO.getApplyMaToRfOrder(userId, orderNo, amount, rpValue, productId, token, tradeId));
		} else {
			userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getApplyMaToRfOrder(userId, orderNo, amount, productId, token, tradeId));
		}

		// 3.定期理财产品剩余总份额减去当前申购份额
		int updatecount = userAccountRfMapper.updateProductFinace(productId, amount, lockVersion);
		if (updatecount < 1){
			throw new AppException("购买失败");//产品更新剩余份额遭遇乐观锁
		}
		return orderNo;
	}
	/**
	 * 生成其他冻结订单号
	 */
	@Override
	public void applyMaToRfBidIps(String bidBillNo, String userId, String productId, BigDecimal amount,BigDecimal rpValue,String couponId,
			 int lockVersion, String token, String tradeId) {
		// TODO Auto-generated method stub
		// 生成充值到红包账户的订单
		BigDecimal frozenAmount=amount;
		if (rpValue.compareTo(new BigDecimal(0)) > 0) {// 实际=冻结金额减去红包金额
			frozenAmount = (amount).subtract(rpValue);
			
		}

			userAccountMapper.addtOrderBidInfoIps(IPSOrderInfoDTO.getApplyMaToRfOrder(userId, bidBillNo,amount, rpValue,frozenAmount,couponId, productId, token, tradeId));
			
	}
/**
 * 生成红包订单号
 */
	@Override
	public void applyMaToRfPacketIps(String redBillNo, String userId,String rpId, String productId, BigDecimal amount,
			BigDecimal rpValue, int lockVersion, String token, String tradeId) {
		
		// 生成充值到红包账户的订单
		if (rpValue.compareTo(new BigDecimal(0)) > 0) {
			userAccountMapper.addtOrderPacketInfoIps(IPSOrderInfoDTO.getRechargeRpOrder(userId, redBillNo, rpValue, productId, token, tradeId));
		}
		//红包标的order_no字段跟新
		userAccountMapper.setPacketNo(redBillNo,userId,rpId);
		
/*		回掉处理
		// 1. 从ma 账户扣除金额到冻结
		BigDecimal frozenAmount = amount;
		if (rpValue.compareTo(new BigDecimal(0)) > 0) {//冻结金额减去红包金额
			frozenAmount = amount.subtract(rpValue);
		}
		userAccountMapper.updateFreezeMaAmount(userId, frozenAmount);

		// 2. 下单申购rf
		orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		if (rpValue.compareTo(new BigDecimal(0)) > 0) {//采用红包购买理财产品，红包金额更新到order的活动金额字段（activity_amount）
			userAccountMapper.insertOrder(MBOrderInfoDTO.getApplyMaToRfOrder(userId, orderNo, amount, rpValue, productId, token, tradeId));
		} else {
			userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getApplyMaToRfOrder(userId, orderNo, amount, productId, token, tradeId));
		}

		// 3.定期理财产品剩余总份额减去当前申购份额
		int updatecount = userAccountRfMapper.updateProductFinace(productId, amount, lockVersion);
		if (updatecount < 1){
			throw new AppException("购买失败");//产品更新剩余份额遭遇乐观锁
		}*/
	}
	
	/**
	 * 处理订单，生成投资记录，插入还款计划，插入保单，插入最新动态，生成订单产品明细
	 * 处理资金，从ma 账户冻结金额中扣除订单金额，rf帐户增加订单金额，rf明细账户增加订单，更新订单状态
	 * @title
	 * @throws ParseException
	 */
	@Transactional
	public void processRfOrder(MBUserInfoDTO userinfo, RfResponseDTO dto, String token) {

		Date productendtime;
		try {
			productendtime = new SimpleDateFormat("yyyy-MM-dd").parse(userinfo.getProductEndTime());
		} catch (ParseException e) {
			throw new AppException("产品结束时间格式错误");
		}

		// add by Speed J 增加rf购买记录
		int count = userAccountMapper.selectUserActionFlagByUserId(userinfo.getUserId());
//		int count = userAccountMapper.updateIfPurchaseRfFlag(userinfo.getUserId());
		if(count == 0){
			userAccountMapper.addIfPurchaseRfFlag(userinfo.getUserId());
		}
		else{
			userAccountMapper.updateIfPurchaseRfFlag(userinfo.getUserId());
		}

		String orderProductId = getId("OP", ISequenceService.SEQ_NAME_ORDER_PRODUCT_SEQ);
		//生成投资记录
		userAccountRfMapper.addUserRfInvestRecord(userinfo.getUserId(), dto.getAmount(), dto.getOrderNo(), userinfo.getProductId());
		//生成还款计划
		userAccountRfMapper.addUserRfInvestRepaymentPlan(dto.getRepayplans());
		//插入保单表-保单号为空
		userAccountRfMapper.addUserPolicyInfo(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId(), "", dto.getLoanId(), dto.getTradeId(), dto.getAmount().toString(), dto.getName(), dto.getIdentifyNo());
		//插入保单表-保单号为大保单号
		//userAccountRfMapper.addUserPolicyInfo(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId(), dto.getPolicyNo(), dto.getLoanId(), dto.getTradeId(), dto.getAmount().toString(), dto.getName(), dto.getIdentifyNo());


		//插入最新动态
		userAccountRfMapper.addRfNewestActivity(userinfo.getUserId(), dto.getOrderNo(), "投资定期理财产品", "下订单", "投资成功，开始计息，投资本金"+dto.getAmount()+"元");
		//生成订单产品明细
		userAccountRfMapper.addUserOrderProductDetail(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId(), dto.getRepayplans().get(0).getRepaymentPlanId(), orderProductId, dto.getAmount(), productendtime, userinfo.getContractTemplateNo(), token);
		//生成合同
		yingZTContractInfoService.saveContractInfo(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId());

		//处理资金
		// 1. 从ma 账户冻结金额中扣除订单金额
		userAccountRfMapper.updateMaLessFrozenAmountByApplyMaToRf(userinfo.getUserId(), dto.getOrderNo());
		// 2. rf子账户增加订单信息  add
		String rfAcountDetailId = getId("RF", ISequenceService.SEQ_NAME_RFACOUNT_SEQ);
		userAccountRfMapper.addUserRfTradeAcountDetail(rfAcountDetailId, userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId());
		// 3. rf帐户增加订单金额
		userAccountRfMapper.updateUserRfAcountAmount(userinfo.getUserId(), dto.getOrderNo());
		// 4. 更新订单状态
		userAccountRfMapper.updateConfirmShareOrderStatus(dto.getOrderNo(), MBOrderInfoDTO.ORDER_STATUS_APPLY_MA_TO_RF_CONFIRM);
	}
	
	
	
	/**
	 * ips处理订单，生成投资记录，插入还款计划，插入保单，插入最新动态，生成订单产品明细
	 * 处理资金，从ma 账户冻结金额中扣除订单金额，rf帐户增加订单金额，rf明细账户增加订单，更新订单状态
	 * @title
	 * @throws ParseException
	 */
	@Transactional
	public void processRfIpsOrder(MBUserInfoDTO userinfo, RfResponseDTO dto, String token) {
		
		Date productendtime;
		try {
			productendtime = new SimpleDateFormat("yyyy-MM-dd").parse(userinfo.getProductEndTime());
		} catch (ParseException e) {
			throw new AppException("产品结束时间格式错误");
		}
		
		// add by Speed J 增加rf购买记录
		int count = userAccountMapper.selectUserActionFlagByUserId(userinfo.getUserId());
//		int count = userAccountMapper.updateIfPurchaseRfFlag(userinfo.getUserId());
		if(count == 0){
			userAccountMapper.addIfPurchaseRfFlag(userinfo.getUserId());
		}
		else{
			userAccountMapper.updateIfPurchaseRfFlag(userinfo.getUserId());
		}
		
		String orderProductId = getId("OP", ISequenceService.SEQ_NAME_ORDER_PRODUCT_SEQ);
		//生成投资记录
		userAccountRfMapper.addUserRfInvestRecord(userinfo.getUserId(), dto.getAmount(), dto.getOrderNo(), userinfo.getProductId());
		//生成还款计划
		userAccountRfMapper.addUserRfInvestRepaymentPlan(dto.getRepayplans());
		//插入保单表-保单号为空
		userAccountRfMapper.addUserPolicyInfo(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId(), "", dto.getLoanId(), dto.getTradeId(), dto.getAmount().toString(), dto.getName(), dto.getIdentifyNo());
		//插入保单表-保单号为大保单号
		//userAccountRfMapper.addUserPolicyInfo(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId(), dto.getPolicyNo(), dto.getLoanId(), dto.getTradeId(), dto.getAmount().toString(), dto.getName(), dto.getIdentifyNo());
		
		
		//插入最新动态
		userAccountRfMapper.addRfNewestActivity(userinfo.getUserId(), dto.getOrderNo(), "投资定期理财产品", "下订单", "投资成功，开始计息，投资本金"+dto.getAmount()+"元");
		//生成订单产品明细
		userAccountRfMapper.addUserOrderProductDetail(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId(), dto.getRepayplans().get(0).getRepaymentPlanId(), orderProductId, dto.getAmount(), productendtime, userinfo.getContractTemplateNo(), token);
		//生成合同
		yingZTContractInfoService.saveContractInfo(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId());
		
		//处理资金
		// 1. 从ma 账户冻结金额中扣除订单金额
		userAccountRfMapper.updateMaLessFrozenAmountByApplyMaToRf(userinfo.getUserId(), dto.getOrderNo());
		// 2. rf子账户增加订单信息  add
		String rfAcountDetailId = getId("RF", ISequenceService.SEQ_NAME_RFACOUNT_SEQ);
		userAccountRfMapper.addUserRfTradeAcountDetail(rfAcountDetailId, userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId());
		// 3. rf帐户增加订单金额
		userAccountRfMapper.updateUserRfAcountAmount(userinfo.getUserId(), dto.getOrderNo());
		// 4. 更新订单状态
		userAccountRfMapper.updateConfirmShareOrderStatus(dto.getOrderNo(), MBOrderInfoDTO.ORDER_STATUS_APPLY_MA_TO_RF_CONFIRM);
		
		
		
		
		
	}

	
	@Transactional
	public void processRfOrderWithRp(MBUserInfoDTO userinfo, RfResponseDTO dto, String token) {
		Date productendtime;
		try {
			productendtime = new SimpleDateFormat("yyyy-MM-dd").parse(userinfo.getProductEndTime());
		} catch (ParseException e) {
			throw new AppException("产品结束时间格式错误");
		}
		int count = userAccountMapper.selectUserActionFlagByUserId(userinfo.getUserId());
		if(count == 0){
			userAccountMapper.addIfPurchaseRfFlag(userinfo.getUserId());
		}
		else {
			userAccountMapper.updateIfPurchaseRfFlag(userinfo.getUserId());
		}

		String orderProductId = getId("OP", ISequenceService.SEQ_NAME_ORDER_PRODUCT_SEQ);
		//生成投资记录
		userAccountRfMapper.addUserRfInvestRecord(userinfo.getUserId(), dto.getAmount(), dto.getOrderNo(), userinfo.getProductId());
		//生成还款计划
		userAccountRfMapper.addUserRfInvestRepaymentPlan(dto.getRepayplans());
		//插入保单表-保单号为空
		userAccountRfMapper.addUserPolicyInfo(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId(), "", dto.getLoanId(), dto.getTradeId(), dto.getAmount().toString(), dto.getName(), dto.getIdentifyNo());

		//插入最新动态
		userAccountRfMapper.addRfNewestActivity(userinfo.getUserId(), dto.getOrderNo(), "投资定期理财产品", "下订单", "投资成功，开始计息，投资本金"+dto.getAmount()+"元");
		//生成订单产品明细
		userAccountRfMapper.addUserOrderProductDetail(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId(), dto.getRepayplans().get(0).getRepaymentPlanId(), orderProductId, dto.getAmount(), productendtime, userinfo.getContractTemplateNo(), token);
		//生成合同
		yingZTContractInfoService.saveContractInfo(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId());

		//处理资金
		// 1. 从ma 账户冻结金额中扣除订单金额
		userAccountRfMapper.updateMaLessFrozenAmountByApplyMaToRfWithRp(userinfo.getUserId(), dto.getOrderNo());
		// 2. rf子账户增加订单信息  add
		String rfAcountDetailId = getId("RF", ISequenceService.SEQ_NAME_RFACOUNT_SEQ);
		userAccountRfMapper.addUserRfTradeAcountDetail(rfAcountDetailId, userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId());
		// 3. rf帐户增加订单金额
		userAccountRfMapper.updateUserRfAcountAmount(userinfo.getUserId(), dto.getOrderNo());
		// 4. 更新订单状态
		userAccountRfMapper.updateConfirmShareOrderStatus(dto.getOrderNo(), MBOrderInfoDTO.ORDER_STATUS_APPLY_MA_TO_RF_CONFIRM);
	}

	/**
	 * 撤销订单，失败回滚
	 * @title
	 * @param userId
	 * @param orderNo
	 * @param productId
	 * @param amount
	 * @param lockVersion
	 */
	@Transactional
	public void cancelMaToRf(String userId, String orderNo, String productId, BigDecimal amount, int lockVersion) {

		// 1. 从ma 账户解除冻结资金
		userAccountMapper.cancelFreezeMaAmount(userId, amount);

		// 3.定期理财产品剩余总份额回滚当前申购份额
		int updatecount = userAccountRfMapper.cancelProductFinace(productId, amount, lockVersion);
		if (updatecount < 1){
			throw new AppException("撤销订单失败");//产品更新剩余份额遭遇乐观锁
		}

		// 4. 更新订单状态
		userAccountRfMapper.updateConfirmShareOrderStatus(orderNo, MBOrderInfoDTO.ORDER_STATUS_APPLY_MA_TO_RF_CANCEL);
	}

	@Override
	@Transactional
	public void doRechargeToMaToDispose(MBRechargeDTO rechargeDTO) {
		String cardId = rechargeDTO.getCardId();
		// 1. 查询用户是否是没有卡
		int cardCount = userAccountMapper.queryCardCountByUserId(rechargeDTO.getUserId());
		if(cardCount == 0){
			// 生成新安全卡卡号id
			rechargeDTO.setCardId(getId("AC", ISequenceService.SEQ_NAME_CARD_SEQ));
			// 1.1 增加安全卡
			userAccountMapper.addUserSecurityCardInfo(rechargeDTO);
			// 1.2 更新用户实名信息
			// 性别
			userAccountMapper.updateUserRealNameInfo(rechargeDTO);
		}
		
		//TODO 判断充值卡是否存在,如果存在跳过
		if(userAccountMapper.queryBindRepeatCardCountBycardNo(rechargeDTO.getUserId(), rechargeDTO.getCardNo())<1){
			// 2. 增加一个充值卡
			rechargeDTO.setCardId(cardId);
			userAccountMapper.addUserRechargeCardInfo(rechargeDTO);			
		}
	}

	@Transactional
	public void doRechargeToMaAlreadyBindToDisposeForWithhold(MBRechargeDTO rechargeDTO) {
		BigDecimal amount = new BigDecimal(rechargeDTO.getAmount()).setScale(2, BigDecimal.ROUND_FLOOR);
		// 1. 增加一个充值订单
		String orderNo= getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		// wj_order
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getDoRechargeToMaToDispose(rechargeDTO.getUserId(),  orderNo	, amount, rechargeDTO.getCardId()));
		// 初始化交易流水信息 ,更新业务单号TODO
//		tradeRecordMapper.insertTradeRecord(rechargeDTO.getOrderNo(), amount , orderNo);
	}

	@Override
	@Transactional
	public void doBindCard(MBRechargeDTO rechargeDTO) {
		String cardId = rechargeDTO.getCardId();
		// 1. 查询用户是否是没有卡
		int cardCount = userAccountMapper.queryCardCountByUserId(rechargeDTO.getUserId());
		if(cardCount == 0){
			// 生成新安全卡卡号id
			rechargeDTO.setCardId(getId("AC", ISequenceService.SEQ_NAME_CARD_SEQ));
			// 1.1 增加安全卡
			userAccountMapper.addUserSecurityCardInfo(rechargeDTO);
			// 1.2 更新用户实名信息
			// 性别

			userAccountMapper.updateUserRealNameInfo(rechargeDTO);
		}
		// 2. 增加一个充值卡
		rechargeDTO.setCardId(cardId);
		userAccountMapper.addUserRechargeCardInfo(rechargeDTO);
	}

	@Override
	@Transactional
	public void doRechargeToMaAlreadyBindToDispose(MBRechargeDTO rechargeDTO) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(rechargeDTO.getBizOrderNo());
		
		//不存在订单，增加一笔订单wj_order
		if(userAccountMapper.queryOrderInfoByOrderNo(orderInfoDTO) == null){	
			BigDecimal amount = new BigDecimal(rechargeDTO.getAmount()).setScale(2, BigDecimal.ROUND_FLOOR);
			// 1. 增加一个充值订单
			userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getDoRechargeToMaToDispose(rechargeDTO.getUserId(), rechargeDTO.getBizOrderNo(), amount, rechargeDTO.getCardId()));
		}
	}

	@Override
	@Transactional
	public boolean doRechargeToMaCallback(MBOrderInfoDTO orderInfoDTO) {
		//充值记录不存在,重新生成交易流水和交易订单
		if(StringUtils.isBlank(orderInfoDTO.getOrderToken())){  
			BigDecimal amount = orderInfoDTO.getPrice();
			//生成新的业务订单号
			String relBizOrderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
			WjEbatongTradeHistoryDTO ebatongTradeHistoryDTO = userAccountMapper.queryWjEbatongTradeHistoryToRepair(orderInfoDTO.getOrderNo());
			int count = userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getDoRechargeToMa(orderInfoDTO.getUserId(), relBizOrderNo, amount, orderInfoDTO.getTradeAccountCardId()));
			int insertCount = tradeRecordMapper.insertTradeSuccessRecord(orderInfoDTO.getOrderNo(), amount, relBizOrderNo, ebatongTradeHistoryDTO.getCardNo(), ebatongTradeHistoryDTO.getPlateformId()); //添加交易流水
			
			if(count==0 || insertCount==0){
				return false;
			}
			// 更新增加主账户余额
			userAccountMapper.updateAddMaAmount(orderInfoDTO.getUserId(), orderInfoDTO.getPrice());
		}else{			
			String tradeNo = orderInfoDTO.getOrderNo();
			orderInfoDTO.setOrderNo(orderInfoDTO.getTradeId());
			MBOrderInfoDTO order = userAccountMapper.queryOrderInfoByOrderNo(orderInfoDTO);
			if(MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_DISPOSE.equals(order.getOrderStatus())){
				//充值中则修改
				// 2. 充值订单修改状态
				int count = userAccountMapper.updateOrderStatus(orderInfoDTO);
				int update = tradeRecordMapper.updateTradeRecordSuccess(tradeNo, BizStatusEnum.BIZ_SUCCESS.getValue(), "充值成功");
				if(count==0 || update==0){
					return false;
				}
				// 更新增加主账户余额
				BigDecimal amount = orderInfoDTO.getPrice();
				userAccountMapper.updateAddMaAmount(orderInfoDTO.getUserId(), amount);
			}
		}
		return true;
    }
	
	
    
	/*@Override
	@Transactional
	public boolean doWithdrawToMaCallback(MBOrderInfoDTO orderInfoDTO) {
		String trdStatus=orderInfoDTO.getOrderStatus();
		String userId=orderInfoDTO.getUserId();
		BigDecimal amount=orderInfoDTO.getPrice();
		if("0".equals(trdStatus)){
			
			ipsWithDrawMapper.cancelFreezeMaAmount(userId, amount);
		}else if("1".equals(trdStatus)){
			//从冻结金额中扣除资金
		    ipsWithDrawMapper.deductionFreezeMaAmount(userId, amount);
			
		}
		// TODO Auto-generated method stub
		return false;
	}
	*/
	
/*	@Override
	@Transactional
	public 	boolean dotransferS2sCallBack(MBOrderInfoDTO orderInfoDTO) {
		BaseLogger.audit("==========转账状态成功===========");
		//==========校验同步转账金额=============
		// 投标金额
		BigDecimal amount =  orderInfoDTO.getPrice();
	    String merBillNo=orderInfoDTO.getOrderNo();
	    String batchNo=orderInfoDTO.getBatchNo();
	    String productId=orderInfoDTO.getProductId();
	    String userId=orderInfoDTO.getUserId();
	    String inIpsAcctNo=orderInfoDTO.getIpsNo();
		
		//根据返回merBillNo、商户转账批次号batchNo、项目 ID号projectNo查询金额n
		
		Map<String,String> money=userAccountRfMapper.queryAmountByOrderBatchNoProjectNo(merBillNo, batchNo, productId);
		
		String total_price = money.get("totalPrice");
		//总金额
		BigDecimal totalPrice = new BigDecimal(0);
		if(!StringUtils.isBlank(total_price)){
			totalPrice=new BigDecimal(total_price);
		}
		
		
		String price1 = money.get("price");
		//实际交易金额
		BigDecimal price = new BigDecimal(0);
		
		if(!StringUtils.isBlank(price1)){
			price=new BigDecimal(price1);
		}
		
		//=================校验ips返回金额 注意实际金额==========（可能扣除手续费）
		if(amount.compareTo(totalPrice)==0&&amount.compareTo(price)==0){
			BaseLogger.audit("=======转账回调金额校验正确=======");
			
			// 查询用户产品相关数据
			MBUserInfoDTO userinfo = userAccountRfMapper.queryUserInfoByUserid(userId, productId);
			
			// 生成理财产品唯一交易流水号
			String tradeId = userAccountTransactionService.getId("01", ISequenceService.SEQ_NAME_TRADE_SEQ);
			// 获取ips冻结时,理财产品订单号
			Map<String,String> orderNoAndCouponsId = userAccountRfMapper.queryOrderByFreezeId(merBillNo);
			
			String orderNo = orderNoAndCouponsId.get("orderNo");
			
			//取出 加息券id
			String couponId = orderNoAndCouponsId.get("couponId");
			
			RfResponseDTO resdto = null;
    }
	
	
    
	@Override
	@Transactional
	public boolean doWithdrawToMaCallback(MBOrderInfoDTO orderInfoDTO) {
		String trdStatus=orderInfoDTO.getOrderStatus();
		String userId=orderInfoDTO.getUserId();
		BigDecimal amount=orderInfoDTO.getPrice();
		if("0".equals(trdStatus)){
			
			ipsWithDrawMapper.cancelFreezeMaAmount(userId, amount);
		}else if("1".equals(trdStatus)){
			//从冻结金额中扣除资金
		    ipsWithDrawMapper.deductionFreezeMaAmount(userId, amount);
			
		}
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	@Transactional
	public 	boolean dotransferS2sCallBack(MBOrderInfoDTO orderInfoDTO) {
		BaseLogger.audit("==========转账状态成功===========");
		//==========校验同步转账金额=============
		// 投标金额
		BigDecimal amount =  orderInfoDTO.getPrice();
	    String merBillNo=orderInfoDTO.getOrderNo();
	    String batchNo=orderInfoDTO.getBatchNo();
	    String productId=orderInfoDTO.getProductId();
	    String userId=orderInfoDTO.getUserId();
	    String inIpsAcctNo=orderInfoDTO.getIpsNo();
		
		//根据返回merBillNo、商户转账批次号batchNo、项目 ID号projectNo查询金额n
		
		Map<String,String> money=userAccountRfMapper.queryAmountByOrderBatchNoProjectNo(merBillNo, batchNo, productId);
		
		String total_price = money.get("totalPrice");
		//总金额
		BigDecimal totalPrice = new BigDecimal(0);
		if(!StringUtils.isBlank(total_price)){
			totalPrice=new BigDecimal(total_price);
		}
		
		
		String price1 = money.get("price");
		//实际交易金额
		BigDecimal price = new BigDecimal(0);
		
		if(!StringUtils.isBlank(price1)){
			price=new BigDecimal(price1);
		}
		
		//=================校验ips返回金额 注意实际金额==========（可能扣除手续费）
		if(/*amount.compareTo(totalPrice)==0&&*/
	
	// 获取卡券
			public BigDecimal getCoupons(String userId,String couponsId,String productId){
				Map<String,Object> packageInfo=couponMapper.checkCouponsValid(userId, couponsId, productId);
				
				if(packageInfo==null){
					throw new AppException("加息券不存在");
				}
				String couponMark=(String)packageInfo.get("couponMark");
				if(couponMark.equals("no")){
					throw new AppException("该产品不支持卡券");
				}
				Double validateProfit=Double.valueOf(packageInfo.get("validateProfit").toString());
				if(validateProfit<0){
					throw new AppException("不符合加息券使用门槛");
				}
				Double validateTime=Double.valueOf(packageInfo.get("validateTime").toString());
				if(validateTime<0){
					throw new AppException("加息券已过期");
				}
				String isUse=(String)packageInfo.get("isUse");
				if(isUse.equals("Y")){
					throw new AppException("加息券已经使用");
				}
				
				return new BigDecimal(packageInfo.get("addProfit").toString());
			} 
	
	
	
	

	@Override
	@Transactional
	public boolean doRechargeToNotice(MBOrderInfoDTO orderInfoDTO) {
		//1.查询充值订单是否存在
		MBOrderInfoDTO order = userAccountMapper.queryOrderInfoByOrderNo(orderInfoDTO);
		if(order!=null &&
				MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_DISPOSE.equals(order.getOrderStatus())){
			//充值中则修改
			// 2. 充值订单修改状态
			int count = userAccountMapper.updateOrderStatus(orderInfoDTO);
			if(count==0){
				return false;
			}

			// 更新增加主账户余额
			BigDecimal amount = orderInfoDTO.getPrice();
			userAccountMapper.updateAddMaAmount(orderInfoDTO.getUserId(), amount);

			return true;
		}
		return false;

	}

	@Transactional
	public String applyLaToLf(String userId, String productId, BigDecimal amount, int lockVersion, String token, String tradeId) {
		// 0.1 ln账户增加借款
		userAccountMapper.updateAddLnAmount(userId, amount.multiply(new BigDecimal(-1)));
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getApplyLnOrder(userId, orderNo, amount, productId, token, tradeId));

		// 0.2 la账户增加金额
		userAccountMapper.updateAddLaAmount(userId, amount);
		String orderNo2 = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getApplyLaOrder(userId, orderNo2, amount, productId, token, tradeId));

		// 1. 从la 账户扣除金额到冻结
		userAccountMapper.updateFreezeLaAmount(userId, amount);

		// 2. 下单申购rf
		String orderNo3 = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getApplyLaToLfOrder(userId, orderNo3, amount, productId, token, tradeId));

		// 3.定期理财产品剩余总份额减去当前申购份额
		int updatecount = userAccountLfMapper.updateProductFinace(productId, amount, lockVersion);
		if (updatecount < 1){
			throw new AppException("购买失败");//产品更新剩余份额遭遇乐观锁
		}
		return orderNo3;
	}

	/**
	 * 处理订单，生成投资记录，插入还款计划，插入保单，插入最新动态，生成订单产品明细
	 * 处理资金，从ma 账户冻结金额中扣除订单金额，rf帐户增加订单金额，rf明细账户增加订单，更新订单状态
	 * @title
	 * @throws ParseException
	 */
	@Transactional
	public void processLfOrder(MBUserInfoDTO userinfo, RfResponseDTO dto, String token) {

		Date productendtime;
		try {
			productendtime = new SimpleDateFormat("yyyy-MM-dd").parse(userinfo.getProductEndTime());
		} catch (ParseException e) {
			throw new AppException("产品结束时间格式错误");
		}

		String orderProductId = getId("OP", ISequenceService.SEQ_NAME_ORDER_PRODUCT_SEQ);
		//生成投资记录
		userAccountLfMapper.addUserRfInvestRecord(userinfo.getUserId(), dto.getAmount(), dto.getOrderNo(), userinfo.getProductId());
		//生成还款计划
		userAccountLfMapper.addUserRfInvestRepaymentPlan(dto.getRepayplans());
		//插入保单表-保单号为空
		userAccountLfMapper.addUserPolicyInfo(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId(), "", dto.getLoanId(), dto.getTradeId(), dto.getAmount().toString(), dto.getName(), dto.getIdentifyNo());
		//插入保单表-保单号为大保单号
		//userAccountRfMapper.addUserPolicyInfo(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId(), dto.getPolicyNo(), dto.getLoanId(), dto.getTradeId(), dto.getAmount().toString(), dto.getName(), dto.getIdentifyNo());


		//插入最新动态    TODO 名称
		userAccountLfMapper.addRfNewestActivity(userinfo.getUserId(), dto.getOrderNo(), "投资小金鱼", "下订单", "投资成功，开始计息，投资本金"+dto.getAmount()+"元");
		//生成订单产品明细
		userAccountLfMapper.addUserOrderProductDetail(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId(), dto.getRepayplans().get(0).getRepaymentPlanId(), orderProductId, dto.getAmount(), productendtime, userinfo.getContractTemplateNo(), token);

		//生成合同()
		yingZTContractInfoService.saveContractInfo(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId());

		//处理资金
		// 1. 从ma 账户冻结金额中扣除订单金额 
		userAccountLfMapper.updateMaLessFrozenAmountByApplyMaToRf(userinfo.getUserId(), dto.getOrderNo());
		// 2. rf子账户增加订单信息  add
		String rfAcountDetailId = getId("RF", ISequenceService.SEQ_NAME_RFACOUNT_SEQ);
		userAccountLfMapper.addUserRfTradeAcountDetail(rfAcountDetailId, userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId());
		// 3. rf帐户增加订单金额 
		userAccountLfMapper.updateUserRfAcountAmount(userinfo.getUserId(), dto.getOrderNo());
		// 4. 更新订单状态 
		userAccountLfMapper.updateConfirmShareOrderStatus(dto.getOrderNo(), MBOrderInfoDTO.ORDER_STATUS_APPLY_LA_TO_LF_CONFIRM);

	}




	/**
	 * 流通宝-提前还款-生成订单
	 * 提前还款中的订单的channel_trade_id保存的是借款的还款计划ID，这样以后在查询单个提前还款的详情时，可以用到
	 */
	@Transactional
	public List<String> processEarlyRepayment(String userId,BigDecimal amount, List<EarlyRepaymentInfoDTO> list) {
		// 1. 将金额从ma扣除放入冻结金额中
		userAccountMapper.updateFreezeMaAmount(userId, amount);
		List<String> orderNos = new ArrayList<String>();
		for(EarlyRepaymentInfoDTO ed:list){
			String planId = ed.getPlanId();//借款的还款计划编号
			String repayMoneyStr = ed.getTotalAmount();//单个借款订单的还款总额（本金，利息，手续费等）

			// 2. 提前还款生成订单
			String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
			userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getPledgeLoanEarlyRepay(userId, orderNo, new BigDecimal(repayMoneyStr),planId));
			orderNos.add(orderNo);
		}
		return orderNos;
	}


	/**
	 *
	 * 流通宝-提前还款-更新订单，并做结清处理
	 * @param userId 用户编号
	 * @param amount 还款总额
	 * @param orderNos 提前还款的订单编号
	 * @param isEnd 此次提前还款，是否还清
	 * @param loanIds 借款编号
	 * @param  repaymentInfo  还款信息
	 */
	@Transactional
	public void processRepaymentDeductionFrozenAmount(String userId,BigDecimal amount, List<String> orderNos,String isEnd,String[] loanIds,List<EarlyRepaymentInfoDTO> repaymentInfo) {
		// 1. 从ma冻结金额中扣除金额
		userAccountMapper.deductionFreezeMaAmount(userId, amount);

		//2.更新订单状态
		userAccountMapper.updateOrderStatusByOrderNos(userId,orderNos,MBOrderInfoDTO.ORDER_STATUS_PLEDGE_REPAY_CONFIRM);

		//3.1 将ma的冻结金额的钱，转入ln账户（如果有多笔，则分开记账）
		userAccountMapper.updateAddLnAmount(userId,amount);
		for(EarlyRepaymentInfoDTO ed:repaymentInfo){
			String loanId = ed.getLoanId();//借款订单编号
			BigDecimal repayMoney = new BigDecimal(ed.getTotalAmount());//单个借款订单的还款总额（本金，利息，手续费等）

			//3.2 生成ma的冻结金额到ln账户的账单
			String orderNo1 = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
			userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getMaFrozen2LnOrder(userId, orderNo1, repayMoney,loanId));
		}

		//4.判断是否结清，如果结清，则将对应的v理财的收益解冻，并转入ma账户
		if(isEnd.equals("true")){
			//查询借款对应的V理财订单编号
			List<String> rfOrderNos = userAccountMapper.queryOrderNoByTradeChannelIds(userId,loanIds);
			//4.1将用户的所得收益，从rf的冻结金额中扣除
			userAccountMapper.cancelUserRfFrozenAmount(userId, rfOrderNos);
			//4.2将用户的所得收益，转到ma账户
			userAccountMapper.updateUserRfAmountToMa(userId,rfOrderNos);
			//4.3生成rf冻结金额到ma账户的账单
			userAccountMapper.addRfFrozen2MaOrderInfo(rfOrderNos);
			//4.4解冻对应的v理财订单
			userAccountMapper.updateOrderCancelForezenByOrderNos(userId,rfOrderNos);
			//4.5解冻还款计划
			userAccountMapper.updateRepayPlanCancelForezenByOrderNos(userId,rfOrderNos);
		}

	}



	/**
	 *凯拿自动扣款
	 */
	@Transactional
	public List<String> processEnjoyAutoRepaymentMaToFrozen(String userId,BigDecimal amount, List<EarlyRepaymentInfoDTO> list) {
		// 1. 将金额从ma扣除放入冻结金额中
		userAccountMapper.updateFreezeMaAmount(userId, amount);
		List<String> orderNos = new ArrayList<String>();
		for(EarlyRepaymentInfoDTO ed:list){
			String loanId = ed.getLoanId();//借款编号
			String repayMoneyStr = ed.getTotalAmount();//单个借款订单的还款总额（本金，利息，手续费等）

			// 2. 自动还款生成订单
			String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
			userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getEnjoyWithLoanEarlyAutoRepay(userId, orderNo, new BigDecimal(repayMoneyStr),loanId));
			orderNos.add(orderNo);
		}
		return orderNos;
	}


	/**
	 * CANA
	 * @param userId
	 * @param amount
	 * @param orderNos
	 * @param orderStatus
	 */
	@Transactional
	public void processRepaymentDeductionFrozenAmount(String userId,BigDecimal amount,List<String> orderNos,String orderStatus) {
		// 1. 从ma冻结金额中扣除金额
		userAccountMapper.deductionFreezeMaAmount(userId, amount);
		//2.更新订单状态
		userAccountMapper.updateOrderStatusByOrderNos(userId,orderNos,orderStatus);
	}

	/**
	 * CANA
	 * @param userId
	 * @param amount
	 * @param orderNo
	 * @param orderStatus
	 */
	@Transactional
	public void processRepaymentDeductionFrozenAmountForCana(String userId,BigDecimal amount,String loanId,String orderNo,String orderStatus) {
		// 1. 从ma冻结金额中扣除金额
		userAccountMapper.deductionFreezeMaAmount(userId, amount);
		//2.更新订单状态
		List<String> orderNos=new ArrayList<String>();
		orderNos.add(orderNo);
		userAccountMapper.updateOrderStatusByOrderNos(userId,orderNos,orderStatus);
		//3.转ln
		userAccountMapper.updateAddLnAmount(userId,amount);
		String newOrderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getMaAutoFrozen2LnOrderForCana(userId,newOrderNo,amount,loanId));
	}

	/**
	 * CANA自动还款 解冻
	 * @param userId
	 * @param amount
	 * @param orderNos
	 * @param orderStatus
	 */
	@Transactional
	public void processAutoRepaymentDeductionFrozenAmountForCana(String userId,BigDecimal amount,List<String> orderNos,String orderStatus,String planId) {
		// 1. 从ma冻结金额中扣除金额
		userAccountMapper.deductionFreezeMaAmount(userId, amount);
		//2.更新订单状态
		userAccountMapper.updateOrderStatusByOrderNos(userId,orderNos,orderStatus);

		//转ln
		userAccountMapper.updateAddLnAmount(userId,amount);
		String orderNo1 = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);

		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getMaAutoFrozen2LnOrderForCana(userId, orderNo1, amount,planId));
	}




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
	@Transactional
	public DueRepaymentInfoDTO liquidationRfAndLoanAmount(String userId, String loanCode, String planId, BigDecimal princal, BigDecimal interest, BigDecimal repaymentTotalMoney) {
		List<String> rfOrderNos = userAccountMapper.queryOrderNoByTradeChannelIds(userId,new String[]{loanCode});
		//1. 将理财冻结的本金及利息归还的ma账户
		//1.1扣除rf账户上的冻结金额
		userAccountMapper.cancelUserRfFrozenAmount(userId, rfOrderNos);
		//1.2将rf冻结扣除金额加到ma账户
		userAccountMapper.updateUserRfAmountToMa(userId, rfOrderNos);
		//1.3生成rf冻结金额到ma账户的账单
		userAccountMapper.addRfFrozen2MaOrderInfo(rfOrderNos);

		//2.清算(计算应该从ma余额中扣除多少钱)
		//2.1查询对应的到期理财本金 及 收益（从借款开始，之后发放但被冻结的部分）
		RfPrincipalAndInterestDTO rpd = userAccountMapper.queryRfPrincipalAndInterest(userId,rfOrderNos);
		BigDecimal rfPrincipal = new BigDecimal(rpd.getPrincipal()); //理财本金
		BigDecimal rfInterest = new BigDecimal(rpd.getInterest()); //理财收益
		BigDecimal actualPrincipal = new BigDecimal(0); //实还本金（小于等于理财本金）
		BigDecimal actualInterest = new BigDecimal(0);  //实还利息
		BigDecimal cutMoney = new BigDecimal(0); //实还总额
		//BigDecimal subsidyInterest = new BigDecimal(0); //公司补贴利息

		//2.2 如果理财收益（从借款开始，之后发放被冻结的部分）大于贷款利息
		if(rfInterest.compareTo(interest) >= 0){
			actualPrincipal = princal; //实还本金 = 到期应还本金
			actualInterest = interest; //实还利息 = 到期应还利息
		}else{
			//2.3 如果理财收益（从借款开始，之后发放被冻结的部分）小于贷款利息，则用理财收益还贷款利息，还欠的利息，从理财本金中扣除，如果还不够，则通过公司补贴的形式结清掉
			//subsidyInterest = interest.subtract(rfInterest); //补贴利息 = 到期应还利息 - 理财所得收益（从借款开始，之后发放但被冻结的部分）
			actualPrincipal = princal; //实还本金 = 到期应还本金

			BigDecimal leftRfPrincipal = rfPrincipal.subtract(princal);//理财本金减去到期应还本金（正值：贷款时要求贷款金额最大只能为理财金额的90%）
			BigDecimal leftInterest = interest.subtract(rfInterest);//到期应还利息减去理财收益（正值）
			if(leftRfPrincipal.compareTo(leftInterest)>=0){//如果理财收益不够还贷款利息，则利息缺少部分，从理财本金中扣除
				actualInterest = interest; //剩余本金充足，则实还利息 = 应还利息
			}else{
				actualInterest = rfInterest.add(leftRfPrincipal); //如果剩余本金不充足，则实还利息 = 理财收益 + 剩余本金（本金剩多少，还多少，不够公司补）
			}
			BaseLogger.audit(String.format("理财本金：%s,应还本金:%s;理财收益:%s,应还利息：%s,最后实还收益:%s",rfPrincipal.toPlainString(),princal.toPlainString(),rfInterest.toPlainString(),interest.toPlainString(),actualInterest.toPlainString()));
		}
		cutMoney = actualPrincipal.add(actualInterest);//实还金额 = 实还本金 + 实还利息
		BaseLogger.audit(String.format("到期还款userId:%s,planId:%s,实还本金：%s，实还利息:%s,应还总额：%s",userId,planId,actualPrincipal.toPlainString(),actualInterest.toPlainString(),repaymentTotalMoney.toPlainString()));
		//3.将需要还款的金额从ma账户转入到冻结金额
		userAccountMapper.updateFreezeMaAmount(userId,cutMoney);
		BaseLogger.audit("从余额扣款转到冻结金额："+cutMoney.toPlainString());
		//4.生成还款中的订单
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getPledgeLoanDueRepay(userId, orderNo, cutMoney,planId));
		//5.解冻对应的v理财订单
		userAccountMapper.updateOrderCancelForezenByOrderNos(userId,rfOrderNos);
		//6.解冻还款计划
		userAccountMapper.updateRepayPlanCancelForezenByOrderNos(userId,rfOrderNos);

		//7.组装返回结果(实还本金和利息)
		return new DueRepaymentInfoDTO(userId,loanCode,planId,repaymentTotalMoney.toPlainString(),actualPrincipal.toPlainString(),actualInterest.toPlainString(),"0",orderNo,"true");

	}

	/**
	 *
	 * 流通宝-到期还款-更新订单
	 * @param userId 用户编号
	 * @param amount 应还总额
	 * @param actualPrincipal  实还本金
	 * @param actualInterest 实还利息
	 * @param orderNo 提前还款的订单编号
	 */
	@Transactional
	public void processDueRepaymentDeductionFrozenAmount(String userId,BigDecimal amount,BigDecimal actualPrincipal,BigDecimal actualInterest, String orderNo,String loanId) {

		List<String> orderNos = new ArrayList<String>();
		orderNos.add(orderNo);
		BaseLogger.info(String.format("到期还款更新-userId:%s,实还本金：%s，实还利息:%s,应还总额：%s",userId,actualPrincipal.toPlainString(),actualInterest.toPlainString(),amount.toPlainString()));
		BigDecimal actualTotal = actualPrincipal.add(actualInterest);//实还总额(从ma余额中扣除的)
		BigDecimal subsidyInterest = amount.subtract(actualTotal);//公司补贴利息 = 应还总额 - 实还总额

		// 1. 从ma冻结金额中扣除金额
		userAccountMapper.deductionFreezeMaAmount(userId, actualTotal);
		BaseLogger.info("到期还款更新-从余额的冻结金额中扣除金额："+actualTotal.toPlainString());
		//2.更新订单状态
		userAccountMapper.updateOrderStatusByOrderNos(userId,orderNos,MBOrderInfoDTO.ORDER_STATUS_PLEDGE_DUE_REPAY_CONFIRM);
		//3.将ma的冻结金额的钱，转入ln账户
		userAccountMapper.updateAddLnAmount(userId,amount);
		BaseLogger.info("到期还款更新-还款到ln账户金额："+amount.toPlainString());
		BaseLogger.info("到期还款更新-公司补贴利息："+subsidyInterest.toPlainString());
		//4.生成ma的冻结金额到ln账户的账单
		String orderNo1 = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getMaFrozen2LnOrder(userId, orderNo1, actualTotal,loanId));
		//4.如果公司有补贴利息，则记录补贴信息
		if(subsidyInterest.compareTo(new BigDecimal("0"))>0){
			String orderNo2 = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
			mBUserLoanOrderMapper.addPledgeLoanOrder(MBOrderInfoDTO.getSubsidy2LnOrder(userId, orderNo2, subsidyInterest,loanId,orderNo));

		}



	}


	@Transactional
	public String processDueRepayment(String userId, String loanCode,String planId, BigDecimal repaymentTotalMoney) {
		List<String> rfOrderNos = userAccountMapper.queryOrderNoByTradeChannelIds(userId,new String[]{loanCode});
		//1.扣除rf账户上的冻结金额
		userAccountMapper.cancelUserRfFrozenAmount(userId, rfOrderNos);
		//2.将rf冻结扣除金额加到ma账户
		userAccountMapper.updateUserRfAmountToMa(userId, rfOrderNos);
		//3.生成rf冻结金额到ma账户的账单
		userAccountMapper.addRfFrozen2MaOrderInfo(rfOrderNos);
		//4.将需要还款的金额从ma账户转入到冻结金额
		userAccountMapper.updateFreezeMaAmount(userId,repaymentTotalMoney);
		//5.生成还款中的订单
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getPledgeLoanDueRepay(userId, orderNo, repaymentTotalMoney,planId));
		//6.解冻对应的v理财订单
		userAccountMapper.updateOrderCancelForezenByOrderNos(userId,rfOrderNos);
		//7.解冻还款计划
		userAccountMapper.updateRepayPlanCancelForezenByOrderNos(userId,rfOrderNos);

		return orderNo;
	}





	/********************************************************************************工资先享部分************************************************************************/

	/**
	 * 工资先享-提前还款-生成订单
	 * 提前还款中的订单的channel_trade_id保存的是借款的还款计划ID，这样以后在查询单个提前还款的详情时，可以用到
	 */
	@Transactional
	public List<String> processWageAdvanceEarlyRepayment(String userId,BigDecimal amount, List<EarlyRepaymentInfoDTO> list) {
		// 1. 将金额从ma扣除放入冻结金额中
		userAccountMapper.updateFreezeMaAmount(userId, amount);
		List<String> orderNos = new ArrayList<String>();
		for(EarlyRepaymentInfoDTO ed:list){
			String loanId = ed.getLoanId();//借款编号
			String repayMoneyStr = ed.getTotalAmount();//单个借款订单的还款总额（本金，利息，手续费等）

			// 2. 提前还款生成订单
			String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
			String relOrderNo = mBUserLoanOrderMapper.queryApplyLoanOrderId(userId,loanId,MBOrderInfoDTO.ORDER_TYPE_WAGE_ADVANCE_LOAN);
			mBUserLoanOrderMapper.addPledgeLoanOrder(MBOrderInfoDTO.getWageAdvanceEarlyRepay(userId, orderNo, new BigDecimal(repayMoneyStr),loanId,relOrderNo));
			orderNos.add(orderNo);

			//生成还款历史记录
			mBUserLoanOrderMapper.addOrderRepayHistory(new OrderRepayHistory(orderNo,ed.getPrincipal(),ed.getInterest(),ed.getPoundage(),ed.getPenalty(),OrderRepayHistory.IS_VALID_NO));
		}
		return orderNos;
	}


	/**
	 *
	 * 工资先享-提前还款-更新订单
	 * @param userId 用户编号
	 * @param amount 还款总额
	 * @param orderNos 提前还款的订单编号
	 * @param loanIds 借款编号
	 * @param  repaymentInfo  还款信息
	 */
	@Transactional
	public void processWageAdvanceDeductionFrozenAmount(String userId,BigDecimal amount, List<String> orderNos,String[] loanIds,List<EarlyRepaymentInfoDTO> repaymentInfo) {
		// 1. 从ma冻结金额中扣除金额
		userAccountMapper.deductionFreezeMaAmount(userId, amount);

		//2.更新订单状态
		userAccountMapper.updateOrderStatusByOrderNos(userId,orderNos,MBOrderInfoDTO.ORDER_STATUS_WAGE_REPAY_CONFIRM);

		//3.1 将ma的冻结金额的钱，转入ln账户（如果有多笔，则分开记账）
		userAccountMapper.updateAddLnAmount(userId,amount);

		for(EarlyRepaymentInfoDTO ed:repaymentInfo){
			String loanId = ed.getLoanId();//借款订单编号
			BigDecimal repayMoney = new BigDecimal(ed.getTotalAmount());//单个借款订单的还款总额（本金，利息，手续费等）
			//3.2 生成ma的冻结金额到ln账户的账单
			String orderNo1 = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
			userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getMaFrozen2LnOrder(userId, orderNo1, repayMoney,loanId));
		}

		//4.更新还款历史记录状态
		for(String orderNo:orderNos){
			mBUserLoanOrderMapper.updateOrderRepayHistory(new OrderRepayHistory(orderNo));
		}

		BaseLogger.info("工资先享提前还款，将还款金额从余额中扣除到冻结金额成功");


	}

	/**
	 * 工资先享 - 到期还款
	 * @param userId
	 * @param loanCode
	 * @param planId
	 * @param repaymentTotalMoney
	 * @return
	 */
	@Transactional
	public String processWageAdvanceDueRepayment(String userId, String loanCode,String planId, BigDecimal repaymentTotalMoney) {

		//1.将需要还款的金额从ma账户转入到冻结金额
		userAccountMapper.updateFreezeMaAmount(userId,repaymentTotalMoney);
		//2.生成还款中的订单
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		String relOrderNo = mBUserLoanOrderMapper.queryApplyLoanOrderId(userId,loanCode,MBOrderInfoDTO.ORDER_TYPE_WAGE_ADVANCE_LOAN);
		mBUserLoanOrderMapper.addPledgeLoanOrder(MBOrderInfoDTO.getWageAdvanceEarlyRepay(userId, orderNo, repaymentTotalMoney,planId,relOrderNo));

		return orderNo;
	}

	/**
	 *
	 * 工资先享-到期还款-更新订单
	 * @param userId 用户编号
	 * @param amount 应还总额
	 * @param actualPrincipal  实还本金
	 * @param actualInterest 实还利息
	 * @param orderNo 提前还款的订单编号
	 */
	@Transactional
	public void processWageAdvanceDueRepaymentDeductionFrozenAmount(String userId,BigDecimal amount,BigDecimal actualPrincipal,BigDecimal actualInterest, String orderNo,String loanId) {
		List<String> orderNos = new ArrayList<String>();
		orderNos.add(orderNo);

		BaseLogger.info(String.format("工资先享processWageAdvanceDueRepaymentDeductionFrozenAmount到期还款更新-userId:%s,实还本金：%s，实还利息:%s,应还总额：%s",userId,actualPrincipal.toPlainString(),actualInterest.toPlainString(),amount.toPlainString()));
		BigDecimal actualTotal = actualPrincipal.add(actualInterest);//实还总额(从ma余额中扣除的)

		// 1. 从ma冻结金额中扣除金额
		userAccountMapper.deductionFreezeMaAmount(userId, actualTotal);
		BaseLogger.info("工资先享processWageAdvanceDueRepaymentDeductionFrozenAmount到期还款更新-从余额的冻结金额中扣除金额："+actualTotal.toPlainString());
		//2.更新订单状态
		userAccountMapper.updateOrderStatusByOrderNos(userId,orderNos,MBOrderInfoDTO.ORDER_STATUS_WAGE_DUE_REPAY_CONFIRM);
		//3.将ma的冻结金额的钱，转入ln账户
		userAccountMapper.updateAddLnAmount(userId,amount);
		BaseLogger.info("工资先享processWageAdvanceDueRepaymentDeductionFrozenAmount到期还款更新-还款到ln账户金额："+amount.toPlainString());

		//4.生成ma的冻结金额到ln账户的账单
		String orderNo1 = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getMaFrozen2LnOrder(userId, orderNo1, actualTotal,loanId));

	}


	/**
	 * 工资先享 - 生成银行卡扣款中的订单
	 *           如果是多笔一次还，则循环生成还款中的订单orders，并生成一个扣款中的订单orderNo2，该订单的rel_order_no字段保存orders
	 * @param userId
	 * @param repayTotal
	 * @param repaymentInfo
	 * @return
	 */
	@Override
	@Transactional
	public String processWageAdvanceAddWithholdOrder(String userId,String cardId,String loanCodes,BigDecimal principal,BigDecimal repayTotal, List<EarlyRepaymentInfoDTO> repaymentInfo) {

		//查询accountCardId
		//MyBankCardInfoDTO mcd = mBUserLoanOrderMapper.queryCardDetailInfoByCardNo(userId,cardNo);

		String relOrderNos = "";
		for(EarlyRepaymentInfoDTO ed:repaymentInfo){
			String loanId = ed.getLoanId();//借款订单编号
			BigDecimal repayMoney = new BigDecimal(ed.getTotalAmount());//单个借款订单的还款总额（本金，利息，手续费等）
			//生成针对单个的还款中订单
			String orderNo1 = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
			String relLoanOrderNo = mBUserLoanOrderMapper.queryApplyLoanOrderId(userId,loanId,MBOrderInfoDTO.ORDER_TYPE_WAGE_ADVANCE_LOAN);
			mBUserLoanOrderMapper.addPledgeLoanOrder(MBOrderInfoDTO.getWageAdvanceEarlyRepaySingleWithhold(userId, orderNo1, repayMoney,cardId,loanId,relLoanOrderNo));
			relOrderNos =relOrderNos+orderNo1+",";

			//生成一个无效的还款历史记录，如果还款成功后，将更新该条记录
			mBUserLoanOrderMapper.addOrderRepayHistory(new OrderRepayHistory(orderNo1,ed.getPrincipal(),ed.getInterest(),ed.getPoundage(),ed.getPenalty(),OrderRepayHistory.IS_VALID_NO));
		}

		//生成银行卡扣款中的订单
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		relOrderNos = relOrderNos.substring(0,relOrderNos.length()-1);
		mBUserLoanOrderMapper.addPledgeLoanOrder(MBOrderInfoDTO.getEarlyBankCardWithhold(userId, orderNo, repayTotal,repayTotal,cardId,loanCodes,relOrderNos));

		//生成一个无效的还款历史记录，如果还款成功后，将更新该条记录
		mBUserLoanOrderMapper.addOrderRepayHistory(new OrderRepayHistory(orderNo,principal.toPlainString(),"0.0000","0.0000","0.0000",OrderRepayHistory.IS_VALID_NO));

		return orderNo;
	}

	/**
	 * 工资计划 - 生成银行卡扣款中的订单
	 * @param userId
	 * @param cardId
	 * @param planCode
	 * @param amount
	 * @return
	 */
	@Override
	@Transactional
	public String processSalaryPlanAddWithholdOrder(String userId, String cardId, String planCode, BigDecimal amount) {
		BaseLogger.info("工资计划processSalaryPlanAddWithholdOrder生成银行卡扣款中的订单：amount:"+amount.toPlainString());
		//生成银行卡扣款中的订单
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		mBUserLoanOrderMapper.addSalaryPlanOrder(MBOrderInfoDTO.getSalaryPlanMBOrderInfoWithhold(userId,cardId,planCode,amount,orderNo));
		return orderNo;
	}

	/*public static void main(String[] args){
		String relOrderNos = "123,456,";
		System.out.print(relOrderNos.substring(0,relOrderNos.length()-1));
	}*/

	
	/**
	 * inIpsAcctNo 公司代理人ips 账户
	 */
	@Override
	public void processRfIpsOrder(MBUserInfoDTO userinfo, RfResponseDTO dto,
			String inIpsAcctNo, String money) {
		// TODO Auto-generated method stub
		Date productendtime;
		try {
			productendtime = new SimpleDateFormat("yyyy-MM-dd").parse(userinfo.getProductEndTime());
		} catch (ParseException e) {
			throw new AppException("产品结束时间格式错误");
		}
		
		// add by Speed J 增加rf购买记录 
		int count = userAccountMapper.selectUserActionFlagByUserId(userinfo.getUserId());
//		int count = userAccountMapper.updateIfPurchaseRfFlag(userinfo.getUserId());
		if(count == 0){
			userAccountMapper.addIfPurchaseRfFlag(userinfo.getUserId());
		}
		else{
			userAccountMapper.updateIfPurchaseRfFlag(userinfo.getUserId());
		}
		
		String orderProductId = getId("OP", ISequenceService.SEQ_NAME_ORDER_PRODUCT_SEQ);
		//生成投资记录                表wj_investment_record
		userAccountRfMapper.addUserRfInvestRecord(userinfo.getUserId(), dto.getAmount(), dto.getOrderNo(), userinfo.getProductId());
		//生成还款计划                表wj_repayment_plan
		userAccountRfMapper.addUserRfInvestRepaymentPlan(dto.getRepayplans());
		//插入保单表-保单号为空
		userAccountRfMapper.addUserPolicyInfo(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId(), "", dto.getLoanId(), dto.getTradeId(), dto.getAmount().toString(), dto.getName(), dto.getIdentifyNo());
		//插入保单表-保单号为大保单号
		//userAccountRfMapper.addUserPolicyInfo(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId(), dto.getPolicyNo(), dto.getLoanId(), dto.getTradeId(), dto.getAmount().toString(), dto.getName(), dto.getIdentifyNo());
		
		
		//插入最新动态        表wj_newest_activity
		userAccountRfMapper.addRfNewestActivity(userinfo.getUserId(), dto.getOrderNo(), "投资定期理财产品", "下订单", "投资成功，开始计息，投资本金"+dto.getAmount()+"元");
		//生成订单产品明细   表wj_order_product
		userAccountRfMapper.addUserOrderProductDetail(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId(), dto.getRepayplans().get(0).getRepaymentPlanId(), orderProductId, dto.getAmount(), productendtime, userinfo.getContractTemplateNo(), null);
		//生成合同
		yingZTContractInfoService.saveContractInfo(userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId());
		
		//处理资金
		// 1. 从ma 账户冻结金额中扣除订单金额 表wj_user_trade_account
		userAccountRfMapper.updateMaLessFrozenAmountByApplyMaToRf(userinfo.getUserId(), dto.getOrderNo());
		// 2. rf子账户增加订单信息  表wj_user_trade_account_rf
		String rfAcountDetailId = getId("RF", ISequenceService.SEQ_NAME_RFACOUNT_SEQ);
		userAccountRfMapper.addUserRfTradeAcountDetail(rfAcountDetailId, userinfo.getUserId(), dto.getOrderNo(), userinfo.getProductId());
		// 3. rf帐户增加订单金额  表 wj_user_trade_account 
		userAccountRfMapper.updateUserRfAcountAmount(userinfo.getUserId(), dto.getOrderNo());
		// 4. 更新订单状态 表wj_order
		userAccountRfMapper.updateConfirmShareOrderStatus(dto.getOrderNo(), MBOrderInfoDTO.ORDER_STATUS_APPLY_MA_TO_RF_CONFIRM);
		
		//代理人ma账户加钱----- 表wj_user_trade_account
		userAccountRfMapper.updateAgentMaAcountAmount(inIpsAcctNo,money);
		
		
		
	}


	
	@Override
	@Transactional
	public 	boolean dotransferS2sCallBack(MBOrderInfoDTO orderInfoDTO) {
		BaseLogger.audit("==========转账状态成功===========");
		//==========校验同步转账金额=============
		// 投标金额
		BigDecimal amount =  orderInfoDTO.getPrice();
	    String merBillNo=orderInfoDTO.getOrderNo();
	    String batchNo=orderInfoDTO.getBatchNo();
	    String productId=orderInfoDTO.getProductId();
	    String userId=orderInfoDTO.getUserId();
	    String inIpsAcctNo=orderInfoDTO.getIpsNo();
		
		//根据返回merBillNo、商户转账批次号batchNo、项目 ID号projectNo查询金额n
		
		Map<String,BigDecimal> money=userAccountRfMapper.queryAmountByOrderBatchNoProjectNo(merBillNo, batchNo, productId);
		
		BigDecimal totalPrice = money.get("totalPrice");
		//总金额
		/*BigDecimal totalPrice = new BigDecimal(0);
		if(!StringUtils.isBlank(total_price)){
			totalPrice=new BigDecimal(total_price);
		}*/
		
		
		BigDecimal price = money.get("price");
		//实际交易金额
		/*BigDecimal price = new BigDecimal(0);
		
		if(!StringUtils.isBlank(price1)){
			price=new BigDecimal(price1);
		}*/
		
		//=================校验ips返回金额 注意实际金额==========（可能扣除手续费）
		if(/*amount.compareTo(totalPrice)==0&&*/amount.compareTo(price)==0){
			BaseLogger.audit("=======转账回调金额校验正确=======");
			
			// 查询用户产品相关数据
			MBUserInfoDTO userinfo = userAccountRfMapper.queryUserInfoByUserid(userId, productId);
			
			// 生成理财产品唯一交易流水号
			String tradeId = this.getId("01", ISequenceService.SEQ_NAME_TRADE_SEQ);
			// 获取ips冻结时,理财产品订单号
			Map<String,String> orderNoAndCouponsId = userAccountRfMapper.queryOrderByFreezeId(merBillNo);
			
			String orderNo = orderNoAndCouponsId.get("orderNo");
			
			//取出 加息券id
			String couponId = orderNoAndCouponsId.get("couponId");
			
			RfResponseDTO resdto = null;

			if (userAccountRfMapper.isRepayPlanMockMode()) {
				// 加入加息券处理 ,把利息加进去
				if(StringUtils.isNotBlank(couponId)){
					resdto = mBWjProductProcess.processOrder(totalPrice, this.getCoupons(userId, couponId, productId),userinfo, orderNo, productId, tradeId);
					//扣除加息券
					couponMapper.updateCouponsIsUse(couponId,orderNo);
				}else{
					resdto = mBWjProductProcess.processOrder(totalPrice, userinfo, orderNo, productId, tradeId);
				}
				
			} else {
				
				resdto = mBProductProcess.processOrder(totalPrice, userinfo, orderNo, productId, tradeId);
			}
			//处理订单
			//userAccountTransactionService.processRfIpsOrder(userinfo, resdto, null);
			this.processRfIpsOrder(userinfo, resdto, inIpsAcctNo, amount.toString());
		}else {
			BaseLogger.audit("=======转账回调金额校验错误=======");
			
		}
		return true;
	}

	@Override
	@Transactional
	public boolean doWithdrawToMaCallback(MBOrderInfoDTO orderInfoDTO) {
		String trdStatus=orderInfoDTO.getOrderStatus();
		String userId=orderInfoDTO.getUserId();
		BigDecimal amount=orderInfoDTO.getPrice();
		if("0".equals(trdStatus)){
			ipsWithDrawMapper.cancelFreezeMaAmount(userId, amount);
		}else if("1".equals(trdStatus)){
			//从冻结金额中扣除资金
		    ipsWithDrawMapper.deductionFreezeMaAmount(userId, amount);
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional
	public boolean doOpenaccountOrder(MBOrderInfoDTO orderInfoDTO) {
		String ipsBillNo=orderInfoDTO.getOrderNo();
		String status=orderInfoDTO.getOrderStatus();
		String id=orderInfoDTO.getUserId();
		String ipsAcctNo=orderInfoDTO.getIpsNo();
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		String times=sim.format(new Date());
		try {
			ipsOpenUserResultMapper.updateUserOrder("", status, id, ipsBillNo);
			// 根据userid 去存ipsAcctNo
			ipsOpenUserResultMapper.insertUserId(id, ipsAcctNo, status, times);
		} catch (Exception e) {
			BaseLogger.error(e.getMessage());
			return false;
		}
		
		return true;
		
		
	}

	public static void main(String[] args) {
	    SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		String times=sim.format(new Date());
		System.out.println(times);
		
	}
}
