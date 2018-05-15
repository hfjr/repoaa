package com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.facade.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.google.common.collect.Maps;
import com.zhuanyi.vjwealth.wallet.service.balance.b2c.server.service.IB2CPayGatewayService;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.PayResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.CallbackBizResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.bo.PaymentCallBackParamBO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.constant.WithholdConstant;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.BindingCardDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.TradeSearchResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.enums.BizStatusEnum;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.enums.TradeStatusEnum;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.facade.IWithholdServiceFacade;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.integration.mapper.IMBTradeRecordMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.integration.mapper.IUserBindingCardMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.service.IWithholdExcuteService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBRechargeDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserRechargeQuestionMapper;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;

@Service("withholdServiceFacade")
public class WithholdServiceFacadeImpl implements IWithholdServiceFacade {

	private static final String RECHARGE_INTERFACE_RESULT_SUCCESS = "0000";// 充值接口返回调用成功

	@Autowired
	private IWithholdExcuteService withholdExcuteService;

	@Autowired
	private IMBUserAccountMapper userAccountMapper;

	@Autowired
	private IMBTradeRecordMapper tradeRecordMapper;

	@Autowired
	private ISequenceService sequenceService;

	@Remote
	ISendEmailService sendEmailService;

	@Remote
	private IB2CPayGatewayService b2CPayGatewayService;

	@Autowired
	private IUserBindingCardMapper userBindingCardMapper;
	
	@Autowired
	private IMBUserRechargeQuestionMapper userRechargeQuestionMapper;

	@Override
	public List<BindingCardDTO> queryWithholeCardList(String userId, String orderType) {
		// TODO Auto-generated method stub
		// 这里已经在数据库中做了银行卡的bank_code来判断
		Map<String, String> dataMap = Maps.newHashMap();
		dataMap.put("userId", userId);
		dataMap.put("bizType", orderType);
		List<BindingCardDTO> lists = userBindingCardMapper.queryUserbindingCardList(dataMap);
		return lists;
	}

	@Override
    public List<BindingCardDTO> querySecurityWithholdCard(String userId, String bizType) {
        return userBindingCardMapper.querySecurityWithholdCard(userId, bizType);
    }

    @Override
	public List<BindingCardDTO> queryWithholdBankList(String orderType) {
		// TODO Auto-generated method stub
		return userBindingCardMapper.queryWithholdBankList(orderType);
	}

	/**
	 * 
	 */
	@Override
	public Object preBizOperateParameter(String bizType, Object parameter) {
		return withholdExcuteService.excutePreBizOperateParameter(bizType, parameter);
	}

	/**
	 * 易宝发送验证码
	 * 这里不调用mBUserAccountService，是解决循环引用的错误
	 */
	@Override
	public MBRechargeDTO sendWithholdMsg(String userId, String cardId, String amount) throws Exception {
		// 调用原有充值发送短信体系
		BaseLogger.audit("已绑卡充值下单发验证码-->rechargeToMaAlreadyBindCardSendCodeV32-->in--");
		// 1. 校验参数合法性
		this.checkRechargeParamValidateV32(userId, cardId, amount);

		MBRechargeDTO rechargeDTO = userAccountMapper.queryBindCardInfoAndUserInfo(userId, cardId);
		if (rechargeDTO == null) {
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡充值下单并发验证码发逻辑异常，userId[" + userId + "]"));
			throw new AppException("数据异常，请联系客服");
		}
		if (rechargeDTO.getCertType() == null || !"01".equals(rechargeDTO.getCertType())) {// 证件类型不是身份证，联动支付只支持身份证
			throw new AppException("证件类型不是身份证，请更换！");
		}
		rechargeDTO.setAmount(amount);

		if (!rechargeDTO.validateSendCodeParameter()) {
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡充值下单并发验证码发逻辑异常，userId[" + rechargeDTO.getUserId() + "]"));
			throw new AppException(rechargeDTO.getMessage());
		}
		try {
			com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO paramDTO = new com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO();
			BeanUtils.copyProperties(paramDTO, rechargeDTO);
			paramDTO.setCustomerId(cardId);

			PayResultDTO payResultDTO = b2CPayGatewayService.applyPaySendMessageCodeByYeep(paramDTO);

			if (!payResultDTO.getResult()) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已绑卡充值下单并发验证码异常，userId[" + rechargeDTO.getUserId() + "]"));
				throw new AppException("账户信息不正确，请联系客服");
			}
			if (payResultDTO.getResult() && RECHARGE_INTERFACE_RESULT_SUCCESS.equals(payResultDTO.getResultCode())) {
				MBRechargeDTO MBRechargeDTO = new MBRechargeDTO();
				MBRechargeDTO.setCardId(cardId);
				MBRechargeDTO.setOrderNo(payResultDTO.getOrderNo());
				MBRechargeDTO.setCertType(rechargeDTO.getCertType());
				// 新增短信有效时间 update by Eric $.2015-09-29 14:41:12
				MBRechargeDTO.setRemainTime("120");
				return MBRechargeDTO;
			} else {
				BaseLogger.error("下发短信验证码出错：" + payResultDTO.getResultMessage());

				// 错误提示信息转换
				convertErrorMessage(payResultDTO);

				throw new AppException(payResultDTO.getResultMessage());
			}
		} catch (SocketTimeoutException e) {
			BaseLogger.error("请求响应超时;", e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_WARN", pageEmailMap("rechargeToMaAlreadyBindCardSendCode 响应超时:充值下单发验证码异常，userId[" + rechargeDTO.getUserId() + "]exception:" + e.getMessage()));
			throw new AppException("银行系统繁忙，请稍后再试");
		} catch (AppException e) {
			BaseLogger.error("已绑卡充值下单发验证码异常", e);
			String msg = ((AppException) e).getMessage();
			if (StringUtils.isBlank(msg)) {
				throw new AppException("请核对银行卡与身份证信息是否匹配");
			}
			if ("httpRequestTimeOut".equals(msg)) {
				BaseLogger.error("请求响应超时;", e);
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_WARN", pageEmailMap("rechargeToMaAlreadyBindCardSendCode 响应超时:充值下单发验证码异常，userId[" + rechargeDTO.getUserId() + "]exception:" + e.getMessage()));
				throw new AppException("银行系统繁忙，请稍后再试");
			}
			throw e;
		} catch (Exception e) {
			BaseLogger.error("已绑卡充值下单发验证码异常", e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("rechargeToMaAlreadyBindCardSendCode 充值下单发验证码异常，userId[" + rechargeDTO.getUserId() + "]exception:" + e.getMessage()));
			throw new AppException("银行系统繁忙，请稍后再试");
		}
	}

	// 易宝确认支付
	@Override
	public String confirmWithhold(String userId, String cardId, String tradeNo, String bizOrderNo, String amount, String code) throws Exception {
		// 调用原有确认付款体系
		BaseLogger.audit("绑定卡确认付款-->doRechargeToMaAlreadyBind-->in--");
		// 校验参数合法性
		if (StringUtils.isBlank(code)) {
			throw new AppException("请输入您的短信验证码");
		}

		MBRechargeDTO rechargeDTO = userAccountMapper.queryBindCardInfoAndUserInfo(userId, cardId);
		if (rechargeDTO == null) {
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡确认代扣逻辑异常,不存在卡信息，userId[" + userId + "]"));
			throw new AppException("数据异常，请联系客服");
		}
		
		rechargeDTO.setOrderNo(bizOrderNo);
		rechargeDTO.setAmount(amount);
		rechargeDTO.setDynamicCode(code);

		if (!rechargeDTO.validateDoRechargeToMaParameter()) {
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡确认代扣逻辑异常，userId[" + rechargeDTO.getUserId() + "]"));
			throw new AppException(rechargeDTO.getMessage());
		}
		// 3.因为本方法,不直接操作用户账户,只是增加处理中的订单,所以不锁定账户
		// 查询下账户是否锁定,锁定不处理
		int lockCount = userAccountMapper.findCountWithUserDrawLock(rechargeDTO.getUserId());
		// 大于0说明已经上锁
		if (lockCount > 0) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}
		try {

			com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO paramDTO = new com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO();
			BeanUtils.copyProperties(paramDTO, rechargeDTO);
			paramDTO.setOrderNo(tradeNo);
			
			boolean existTradeRecord = tradeRecordMapper.countTradeRecordExitsByTradeNo(tradeNo)>0?true:false;
			if(!existTradeRecord){
				tradeRecordMapper.insertTradeRecord(tradeNo, new BigDecimal(amount),  null,rechargeDTO.getCardNo(), null);
			}
			// TODO 初始化交易流水信息tradeNo bizOrderNo
			PayResultDTO payResultDTO = b2CPayGatewayService.confirmPay(paramDTO);
				
			// //扣钱调用支付接口（保存支付历史（流水））
			if (!payResultDTO.getResult()) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡确认充值发逻辑异常，userId[" + rechargeDTO.getUserId() + "] 错误信息[" + payResultDTO.getResultMessage()));
				throw new AppException("银行系统繁忙，请重新尝试充值");
			}
			
			tradeRecordMapper.updateTradePlatformAndBizOrder(tradeNo,payResultDTO.getPlatformId(), bizOrderNo);
			
			if (RECHARGE_INTERFACE_RESULT_SUCCESS.equals(payResultDTO.getResultCode())) {
				// 充值成功后逻辑
				BaseLogger.audit("代扣-->验证验证码-->代扣成功-->doRechargeToMaAlreadyBindForWithhold-->end--");
				// 不加钱,加的订单为中间状态(不需要再次生成订单了)
				//userAccountTransactionService.doRechargeToMaAlreadyBindToDisposeForWithhold(rechargeDTO);
				return tradeNo;
			}else {
				BaseLogger.error("代扣-->验证验证码-->代扣出错：" + payResultDTO.getResultMessage());
				// 错误提示信息转换
				convertErrorMessage(payResultDTO);
				// 更新状态为失败
				tradeRecordMapper.updateTradeRecordFail(tradeNo, BizStatusEnum.BIZ_FAILED.toString(), payResultDTO.getResultMessage());
				throw new AppException(payResultDTO.getResultMessage());
			}
		} catch (SocketTimeoutException e) {
			BaseLogger.error("请求响应超时;", e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_WARN", pageEmailMap("doRechargeToMaAlreadyBind 响应超时:已绑卡确认充值异常，userId[" + rechargeDTO.getUserId() + "]exception:" + e.getMessage()));
			throw new AppException("银行系统繁忙，请稍后再试");
		} catch (AppException e) {
			BaseLogger.error("已绑卡确认充值异常", e);
			String msg = ((AppException) e).getMessage();
			if (StringUtils.isBlank(msg)) {
				throw new AppException("银行系统繁忙，请重新尝试充值");
			}
			if ("httpRequestTimeOut".equals(msg)) {
				BaseLogger.error("请求响应超时;", e);
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_WARN", pageEmailMap("doRechargeToMaAlreadyBind 响应超时:已绑卡确认充值异常，userId[" + rechargeDTO.getUserId() + "]exception:" + e.getMessage()));
				throw new AppException("银行系统繁忙，请稍后再试");
			}
			throw e;
		} catch (Exception e) {
			BaseLogger.error("已绑卡确认充值异常", e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("doRechargeToMaAlreadyBind 已绑卡确认充值异常，userId[" + rechargeDTO.getUserId() + "]exception:" + e.getMessage()));
			throw new AppException("银行系统繁忙，请稍后再试");
		}finally {
			// 2. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("confirmPaymentTradeStatusAndCallbackWithholdOperate 异常，userId[" + userId + "]"));
			}
		}
	}

	@Override
	public TradeSearchResultDTO queryWithholdBizResult(String bizType, String tradeNo) {
		// 1 先插我本地的交易流水
		if (StringUtils.isBlank(tradeNo)) {
			BaseLogger.error("queryWithholdBizResult-->交易号tradeNo为空了!");
			throw new AppException("错误的交易号...");
		}
		// 查询回调必要参数
		PaymentCallBackParamBO paymentDTO = tradeRecordMapper.queryCallBackParamByTradeNo(tradeNo);
		if (paymentDTO == null) {
			throw new AppException("交易记录不存在...[tradeNo:" + tradeNo + "]");
		}

		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(paymentDTO.getOrderNo());
		// 根据订单,查询出bizType
		MBOrderInfoDTO order = userAccountMapper.queryOrderInfoByOrderNo(orderInfoDTO);

		if (order == null) {
			BaseLogger.error("已绑卡免短信代扣接口sendWithholdForAlreadyBindCarded异常,不存在的订单号");
			throw new AppException("不存在的订单信息;");
		}

		// T 查询业务回调接口
		if (TradeStatusEnum.TRADE_SUCCESS.getValue().equals(paymentDTO.getTradeStatus())) {
			// 如果成功,查询业务回调接口
			return withholdExcuteService.excuteQueryBizOperateResult(order.getUserId(), bizType, paymentDTO.getOrderNo());

		} else if (TradeStatusEnum.TRADE_FAILED.getValue().equals(paymentDTO.getTradeStatus())) {
			return TradeSearchResultDTO.buildFailed("处理失败;");
		} else if (TradeStatusEnum.TRADE_PROCESS.getValue().equals(paymentDTO.getTradeStatus())) {
			return TradeSearchResultDTO.buildProcess("处理中...");
		} else {
			throw new AppException(String.format("错误的订单状态:[%s]", paymentDTO.getTradeStatus()));
		}
	}

	@Override
	public String sendWithholdForAlreadyBindCarded(String userId, String cardId, String amount, String bizNo, String orderType) throws Exception {
		// 易宝确认扣款
		BaseLogger.audit("---->已绑卡,免短信代扣业务开始-->sendWithholdForAlreadyBindCarded-->in--");

		// 检验必要的参数
		checkRequireParameter(userId, cardId, amount, bizNo);
		BaseLogger.audit("---->已绑卡,免短信代扣业务开始-->sendWithholdForAlreadyBindCarded-->in--userId:" + userId + " cardId:" + cardId + " amount:" + amount + " bizNo:" + bizNo);
		MBRechargeDTO rechargeDTO = userAccountMapper.queryBindCardInfoAndUserInfoWithHold(userId, cardId, orderType);
		if (rechargeDTO == null) {
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("withholdNoMsg 已经绑卡扣款下单并发验证码发逻辑异常，userId[" + userId + "]"));
			throw new AppException("数据异常，请联系客服");
		}
		rechargeDTO.setOrderType(orderType);

		// 生成交易单号
		String tradeNo = getId("TR", ISequenceService.SEQ_NAME_TRADE_SEQ);

		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(bizNo);
		// 根据订单,查询出bizType
		MBOrderInfoDTO order = userAccountMapper.queryOrderInfoByOrderNo(orderInfoDTO);
		try {

			if (order == null) {
				BaseLogger.error("已绑卡免短信代扣接口sendWithholdForAlreadyBindCarded异常,不存在的订单号");
				throw new AppException("不存在的订单信息;");
			}

			if (!new BigDecimal(amount).setScale(4, BigDecimal.ROUND_DOWN).equals(order.getPrice())) {
				throw new AppException("金额数据不一致;");
			}

			com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO paramDTO = new com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO();
			BeanUtils.copyProperties(paramDTO, rechargeDTO);
			paramDTO.setCustomerId(cardId);
			paramDTO.setAmount(amount);

			// 先更新为处理中
			withholdExcuteService.excuteCallBackProcess(userId, order.getOrderType(), bizNo);

			paramDTO.setOrderNo(tradeNo);

			// 将交易编号写入到的交易流水记录里面
			tradeRecordMapper.insertTradeRecord(tradeNo, order.getPrice(), null, rechargeDTO.getCardNo(),null);
			// 代扣，得到platformId
			String platformId = b2CPayGatewayService.withhold(paramDTO);

			tradeRecordMapper.updateTradePlatformAndBizOrder(tradeNo, platformId, bizNo);
			BaseLogger.audit("---->已绑卡,免短信代扣业务结束-->sendWithholdForAlreadyBindCarded-->out--");
			return tradeNo;
		} catch (Exception ex) {
			if (ex.getMessage().contains("httpRequestTimeOut")) {
				// 请求超时(因为不确认请求结果，这里先返回tradeNo)
				tradeRecordMapper.updateTradePlatformAndBizOrder(tradeNo, rechargeDTO.getPayChannelId(), bizNo);
				BaseLogger.error(String.format("调动已绑卡扣款接口sendWithholdForAlreadyBindCarded请求第三方接口超时[%s]", ex.getMessage()));
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("sendWithholdForAlreadyBindCarded 已绑卡调用代付接口请求第三方接口超时，userId[" + userId + "],orderNo[" + bizNo + "],tradeNo[" + tradeNo + "]"));
				return tradeNo;
			} else {
				// 更新为失败
				BaseLogger.error(String.format("调动已绑卡扣款接口sendWithholdForAlreadyBindCarded,请求接口异常[%s]", ex.getMessage()));
				withholdExcuteService.excuteCallBackBizOperateFail(userId, order.getOrderNo(), order.getOrderType(), new BigDecimal(amount));
				tradeRecordMapper.updateTradeRecordFail(tradeNo, BizStatusEnum.BIZ_FAILED.getValue(), ex.getMessage());
				return null;
			}
		}
	}

	/**
	 * 检查必要的参数
	 * 
	 * @param userId
	 * @param cardId
	 * @param amount
	 * @param bizNo
	 */
	private void checkRequireParameter(String userId, String cardId, String amount, String bizNo) {
		if (StringUtils.isBlank(userId)) {
			throw new AppException("卡号不能为空;");
		}
		if (StringUtils.isBlank(cardId)) {
			throw new AppException("卡号不能为空;");
		}
		if (StringUtils.isBlank(amount)) {
			throw new AppException("扣款金额错误;");
		}
		try {
			BigDecimal price = new BigDecimal(amount);
			if (price.compareTo(new BigDecimal("0")) < 0) {
				throw new AppException("请输入大于0的金额;");
			}
			// 数值失真校验
			BigDecimal newAmount = price.setScale(2, RoundingMode.FLOOR);
			if (price.compareTo(newAmount) != 0) {
				throw new AppException("请输入其它金额;");
			}
		} catch (Exception e) {
			throw new AppException("交易金额不正确;");
		}
		if (StringUtils.isBlank(bizNo)) {
			throw new AppException("业务订单号不能为空;");
		}

	}

	private Map<String, Object> pageEmailMap(String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", content);
		return map;
	}

	public Boolean confirmPaymentTradeStatusAndCallbackWithholdOperate(String tradeNo, BigDecimal amount, boolean payResultIsSuccess) {

		// 基础校验
		if (StringUtils.isBlank(tradeNo)) {
			throw new AppException("无效的交易单号;");
		}
		// 校验交易是否存在
		if (tradeRecordMapper.countTradeRecordExitsByTradeNo(tradeNo) < 1) {
			BaseLogger.error(String.format("WithholdServiceFacadeImpl.confirmPaymentTradeStatusAndCallbackWithholdOperate交易或业务订单不存在,交易号[%s]", tradeNo));
			throw new AppException("无效的交易单号;");
		}
		// 查询回调必要参数
		PaymentCallBackParamBO paymentDTO = tradeRecordMapper.queryCallBackParamByTradeNo(tradeNo);
		// 做交易操作前,上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(paymentDTO.getUserId());
		if (lockUpdateCount != 1) {
			BaseLogger.error("WithholdServiceFacadeImpl.confirmPaymentTradeStatusAndCallbackWithholdOperate,交易锁已锁定,可能与回调查询不同交易同时发生.");
			throw new AppException("正在交易中,请稍后再试;");
		}
		try {
			
			// 校验交易状态(防止并发成功)TODO .. wj_payment_trade_record
			paymentDTO = tradeRecordMapper.queryCallBackParamByTradeNo(tradeNo);

			// 支付交易状态处理中(trade_process),做回调处理
			if (WithholdConstant.TRADE_PROCESS.equals(paymentDTO.getTradeStatus())) {
				// 1. 支付交易成功
				if (payResultIsSuccess) {
					CallbackBizResultDTO callbackBizResultDTO = withholdExcuteService.excuteCallBackBizOperateSuccess(paymentDTO.getUserId(), paymentDTO.getOrderNo(), paymentDTO.getOrderType(), amount);
					// 更新支付记录表
					tradeRecordMapper.updateTradeRecordSuccess(tradeNo, BizStatusEnum.getBizResult(callbackBizResultDTO.isBizResultSuccess()), callbackBizResultDTO.getResultMsg());
					return true; // TODO..
				}
				// 2. 支付交易失败
				CallbackBizResultDTO callbackBizResultDTO = withholdExcuteService.excuteCallBackBizOperateFail(paymentDTO.getUserId(), paymentDTO.getOrderNo(), paymentDTO.getOrderType(), amount);
				tradeRecordMapper.updateTradeRecordFail(tradeNo, BizStatusEnum.getBizResult(callbackBizResultDTO.isBizResultSuccess()), callbackBizResultDTO.getResultMsg());
				return false;
				// TODO 更新交易流水表为失败
			}
			return true;
		} catch (Exception ex) {
			BaseLogger.error(String.format("支付调用业务回调函数异常,交易编号[%s],交易结果[%b],异常信息[%s]", tradeNo, payResultIsSuccess, ex.getStackTrace()));
			throw new AppException(ex.getMessage());
		} finally {
			// 2. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(paymentDTO.getUserId());
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("confirmPaymentTradeStatusAndCallbackWithholdOperate 异常，userId[" + paymentDTO.getUserId() + "]"));
			}
		}
	}

	public String getId(String prefix, String sequenceName) {
		return prefix + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequenceService.getNextStringValue(sequenceName, 10);
	}

	// 校验充值参数合法性V3.2版
	private void checkRechargeParamValidateV32(String userId, String cardId, String amount) {
		// 1. 卡信息一定存在
		if (userAccountMapper.checkRechargeCardExit(userId, cardId) == 0) {
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡充值发逻辑异常，充值卡不存在 cardId[" + cardId + "]userId[" + userId + "]"));
			throw new AppException("信息异常,工程师正在努力维护中...");
		}
		// 2. 校验银行是否维护中
		if (userAccountMapper.checkRechargeCardRunHealthV32(cardId) == 0) {
			throw new AppException("土豪抱歉,银行正在维护中");
		}
		// 3. 金额是否在单笔范围内
		if (StringUtils.isBlank(amount) || !NumberUtils.isNumber(amount)) {
			throw new AppException("请输入正确金额");
		}

		// 3.1 单笔金额是否合法
		if (userAccountMapper.checkRechargeAmountValidV32(cardId, amount) == 0) {
			throw new AppException("土豪,充值金额单笔超限啦");
		}

		// 3.2 当日累积金额是否满足
		if (userAccountMapper.checkSumRechargeBalanceDayLimitValidV32(userId, cardId).equals("no")) {
			throw new AppException("土豪,当日累积充值超限啦,换张卡试试");
		}

	}

	// 充值错误信息转换为平台自定义的错误提示
	private void convertErrorMessage(PayResultDTO payResultDTO) {
		String errorCode = payResultDTO.getResultCode();
		String platformId = payResultDTO.getPlatformId();

		if (StringUtils.isBlank(errorCode) || StringUtils.isBlank(platformId)) {
			return;
		}
		// 查询错误编码转换关系
		String message = userRechargeQuestionMapper.queryErrorMessageByCodeAndPlateformId(errorCode, platformId);

		if (StringUtils.isNotBlank(message)) {
			payResultDTO.setResultMessage(message);
		}
	}

	public List<BindingCardDTO> queryWithholdCard(String userId,
			String orderType, String bankCardNo) {
		return userBindingCardMapper.queryWithholdCard(userId, orderType,bankCardNo);
	}

	public List<BindingCardDTO> querySecurityWithholdCardV2(String userId,
			String orderType) {
		return userBindingCardMapper.querySecurityWithholdCardV2(userId, orderType);
	}



}
