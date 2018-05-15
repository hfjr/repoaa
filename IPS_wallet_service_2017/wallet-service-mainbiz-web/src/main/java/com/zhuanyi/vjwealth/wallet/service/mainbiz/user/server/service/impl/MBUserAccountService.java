package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.impl;

import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.fab.server.remote.hessian.HessianContext;
import com.google.common.collect.Maps;
import com.hf.comm.utils.GenerateMerBillNoUtil;
import com.hf.comm.utils.GenerateOrderNoUtil;
import com.hf.comm.utils.HttpClientUtils;
import com.hf.comm.utils.IpsRequestUtils;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.beans.IPSResponse;
import com.rqb.ips.depository.platform.beans.IpsFreeze;
import com.rqb.ips.depository.platform.beans.IpsPacketFrozen;
import com.rqb.ips.depository.platform.beans.IpsPacketFrozenBid;
import com.rqb.ips.depository.platform.beans.IpsPacketFrozenRedPacket;
import com.rqb.ips.depository.platform.beans.IpsTransferAccountStranferAccDetail;
import com.rqb.ips.depository.platform.beans.IpsTransferAccounts;
import com.rqb.ips.depository.platform.beans.IpsUnfreeze;
import com.rqb.ips.depository.platform.beans.IpsUserInfoDTO;
import com.rqb.ips.depository.platform.beans.WithDraw;
import com.rqb.ips.depository.platform.faced.IPSQueryUserInfoService;
import com.rqb.ips.depository.platform.faced.IPsFreezeService;
import com.rqb.ips.depository.platform.faced.IWithDrawService;
import com.rqb.ips.depository.platform.faced.IpsPacketFrozenService;
import com.rqb.ips.depository.platform.faced.IpsTransferAccountsService;
import com.rqb.ips.depository.platform.faced.UnfreezeServices;
import com.rqb.ips.depository.platform.mapper.CheckCommParamMapper;
import com.rqb.ips.depository.platform.mapper.ExcuteIpsPlatLogMapper;
import com.rqb.ips.depository.platform.menu.Define;
import com.zhuanyi.vjwealth.loan.credit.webservice.ITaskDetailsInfoDubboService;
import com.zhuanyi.vjwealth.loan.order.webservice.IApplyFinancingLoanDubboService;
import com.zhuanyi.vjwealth.loan.product.webservice.IProductInfoDubboService;
import com.zhuanyi.vjwealth.loan.util.StringUtil;
import com.zhuanyi.vjwealth.trade.mainbiz.center.dto.SinglePayoutParameterDTO;
import com.zhuanyi.vjwealth.trade.mainbiz.center.dto.TpsClientResponse;
import com.zhuanyi.vjwealth.trade.mainbiz.center.server.interfaces.IMerchantBalanceOperationRemoteService;
import com.zhuanyi.vjwealth.wallet.service.balance.b2c.server.service.IB2CGetawayImplService;
import com.zhuanyi.vjwealth.wallet.service.balance.b2c.server.service.IB2CPayGatewayService;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.PayNoticeDTO;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.PayResultDTO;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.SmsResultDTO;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.UnionMobilePayDTO;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.WjEbatongTradeHistoryDTO;
import com.zhuanyi.vjwealth.wallet.service.coupon.server.service.IUserCouponService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.service.ICheckUserAccountBalanceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.bo.TradeRecordBO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.SingleStatusResult;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.enums.BizStatusEnum;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.enums.TradeStatusEnum;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.facade.IWithholdServiceFacade;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.integration.mapper.IMBTradeRecordMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.phoneMessage.server.service.IMBUserPhoneMessageService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.constant.UserConstant;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.BankInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.ConfirmShareModelDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.EarlyExpireRepayPlanDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBRechargeDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBRfReturnDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBTransReturnDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.PreExpireRfPaymentResp;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RfRepayPlanDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RfResponseDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.TradeAccountCardDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserTradeAccountInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.ICouponMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBQueryOrderDetailMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBThirdPartyLogMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountLfMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountRfMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserRechargeQuestionMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBProductProcess;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserAccountService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IUserAccountTransactionService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.util.CheckIdCardUtils;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.service.IYingZTInvestService;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;

@Component(value = "mBUserAccountService")
@SuppressWarnings("deprecation")
@Service
public class MBUserAccountService implements IMBUserAccountService {

    
	@Autowired
	private IpsPacketFrozenService packetFrozenService;
	@Autowired
	private IMBUserAccountMapper userAccountMapper;

	@Autowired
	private IUserAccountTransactionService userAccountTransactionService;

	@Autowired
	private IMBUserAccountRfMapper userAccountRfMapper;

	@Autowired
	private IMBUserAccountLfMapper userAccountLfMapper;

	@Autowired
	private IMBUserMapper userMapper;

	@Autowired
	private IMBThirdPartyLogMapper thirdPartyLogMapper;
	
	@Autowired
    private IPSQueryUserInfoService iPSQueryUserInfoService;

	@Remote
	ISendEmailService sendEmailService;

	@Remote
	private IMerchantBalanceOperationRemoteService merchantBalanceOperationRemoteService;

	@Remote
	private IB2CGetawayImplService b2CGetawayImplService;

	@Remote
	private IB2CGetawayImplService unionMobileB2CService;

	@Autowired
	private IYingZTInvestService yingZTInvestService;

	@Autowired
	private ISequenceService sequenceService;

	@Autowired
	@Qualifier("MBRemoteProductProcess")
	private IMBProductProcess mBProductProcess;

	@Autowired
	@Qualifier("MBWjProductProcess")
	private IMBProductProcess mBWjProductProcess;

	@Autowired
	private ICheckUserAccountBalanceService checkUserAccountBalanceService;

	@Autowired
	private IUserCouponService userCouponService;

	@Autowired
	private IWithholdServiceFacade withholdServiceFacade;

	@Autowired
	private IMBTradeRecordMapper tradeRecordMapper;
	
	@Autowired
	private ICouponMapper couponMapper;
	
    @Autowired
    private IMBUserService mBUserService;

    @Autowired
    private CheckCommParamMapper checkCommParamMapper;
        
    @Autowired
    private IPsFreezeService excuteIpsFreezeService;
    
	@Value("${trade_merchant_no}")
	private String merchantNo;

	@Value("${yingzt.partner}")
	private String partner;

	@Value("${yingzt.email}")
	private String email;

	@Value("${yingzt.isSendEmail}")
	private String isSendEmail;

	@Value("${yingzt.isSendSms}")
	private String isSendSms;
	@Value(value = "${local_server_ip}")
	private String localServerIp;
	@Autowired
	private IWithDrawService withDrawService;
	
	/*@Value(value = "${trade.withdraw.webUrl}")
	private String  tradeWithdrawWebUrl;
	
	@Value(value = "${trade.withdraw.s2SUrl}")
	private String  tradeWithdrawS2SUrl;
	
	@Value(value = "${trade.freeze.s2SUrl}")
	private String  tradeFreezeS2SUrl;
	
	@Value(value = "${trade.freeze.webUrl}")
	private String  tradeFreezeWebUrl;

	
	@Value(value = "${trade.transfer.s2SUrl}")
	private String transferS2sUrl;*/
	
	/*@Value(value = "trade.transfer.inIpsAcctNo")
	private String transferInIpsAcctNo;*/
	
	@Autowired
	private IpsTransferAccountsService ipsTransferAccountsService;
	
	@Autowired
	private ExcuteIpsPlatLogMapper excuteIpsPlatLogMapper;
	

	@Autowired
	private UnfreezeServices unfreezeServices;

	  
	
	/*
	 * @Value("${tfax.openAccount}") private String tfaxOpenAccountUrl;
	 * 
	 * @Value("${tfax.version}") private String tfaxVersion;
	 * 
	 * @Value("${tfax.terminalType}") private String tfaxTerminalType;
	 * 
	 * @Value("${tfax.chnlId}") private String tfaxChnlId;
	 * 
	 * @Value("${tfax.businessCode}") private String tfaxBusinessCode;
	 * 
	 * @Value("${tfax.certType}") private String tfaxCertType;
	 * 
	 * @Value("${tfax.openAccountSuccess}") private String
	 * taOpenAccountSuccessFlag;
	 * 
	 * @Value("${tfax.alreadyOpenAccountFlag}") private String
	 * taAlreadyOpenAccountFlag;
	 */

	private static final String comments = "融桥宝";
	private static final String RECHARGE_INTERFACE_RESULT_SUCCESS = "0000";// 充值接口返回调用成功
	private static final int NOTICE_TYPE_UNION_MOBILE = 10;// 充值回调类型:10:联动优势,20:易宝充值，40:京东充值
	private static final int NOTICE_TYPE_YEE_PAY = 20;
	private static final int NOTICE_TYPE_JING_DONG = 40;
    private static final int NOTICE_TYPE_JD_PAY = 60;//京东代扣

	@Autowired
	private IMBQueryOrderDetailMapper queryOrderDetailMapper;

	@Remote
	private IB2CPayGatewayService b2CPayGatewayService;

	@Autowired
	private IMBUserRechargeQuestionMapper userRechargeQuestionMapper;

	@Autowired
	private IMBUserPhoneMessageService userPhoneMessageService;

	@Autowired
	private IApplyFinancingLoanDubboService applyFinancingLoanDubboService;

	@Autowired
	private ITaskDetailsInfoDubboService taskDetailsInfoDubboService;

	@Autowired
	private IProductInfoDubboService productInfoDubboService;

	private static final String SEND_MESSAGE_TYPE = "ea_real_time_withdraw";
	private static final String SEND_MESSAGE_TYPE_TA = "ta_real_time_withdraw";
	private static final String SEND_MESSAGE_SYSTEM = "wallet-mainbiz:MBUserAccountService-->withdrawEaToBank";
	private static final String SEND_MESSAGE_SYSTEM_TA = "wallet-mainbiz:MBUserAccountService-->withdrawTaToBank";
	private static final String SEND_MESSAGE_DATA_001 = "_amount";

	public MBTransReturnDTO withdrawEaToBank(String userId, String withdrawMoney) {
		BaseLogger.audit("start withdrawEaToBank userId[" + userId + "] withdrawMoney[" + withdrawMoney + "]");
		// 校验参数
		BigDecimal amount = confirmUserIsExitAndMoneyIsValid(userId, withdrawMoney);
		// 先判断资金是否足够
		checkAccountIsEnoughToTransfer(userId, UserConstant.TRADETYPE_EA, amount);
		// 1.2 次数是否足够
		if (userAccountMapper.queryEaAvavilableWithDrawTimes(userId) <= 0) {
			throw new AppException("您已超过当日提现次数，此次提现取消");
		}

		// 平账
		if (!checkUserAccountBalanceService.checkUserAccountBalance(userId)) {
			throw new AppException("账户异常，请稍后重试");
		}
		// 1.2 1 查询安全账号信息
		TradeAccountCardDTO tradeAccountCardDTO = userAccountMapper.queryVaildSecurityCardInfoByUserId(userId);
		BaseLogger.audit(String.format("查询实时代付安全卡信息tradeAccountCardDTO[%s]", tradeAccountCardDTO));
		if (tradeAccountCardDTO == null || StringUtils.isBlank(tradeAccountCardDTO.getCardOwer()) || StringUtils.isBlank(tradeAccountCardDTO.getBankCardNo())) {
			throw new AppException("账户信息有异常，请联系客服人员");
		}

		// 1. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}
		Boolean needUnlock = Boolean.TRUE;
		TpsClientResponse<SinglePayoutParameterDTO> tpsClientResponse = null;
		try {
			// 主业务逻辑
			// 1.1 检测余额是否够转账
			checkAccountIsEnoughToTransfer(userId, UserConstant.TRADETYPE_EA, amount);
			// 1.2 次数是否足够
			if (userAccountMapper.queryEaAvavilableWithDrawTimes(userId) <= 0) {
				throw new AppException("您已超过当日提现次数，此次提现取消");
			}
			// 1.2 提现
			String orderNo = userAccountTransactionService.getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
			// 实际发起交易
			BaseLogger.audit(String.format("准备发起代发 userId[%s] amount[%s] orderNo[%s] ", userId, amount, orderNo));
			try {
				String[] baks = new String[] { userId, getClientRealIp() };
				SinglePayoutParameterDTO singlePayoutParameterDTO = new SinglePayoutParameterDTO(orderNo, tradeAccountCardDTO.getCardOwer(), tradeAccountCardDTO.getBankCardNo(), amount.toString(), comments, baks);
				tpsClientResponse = merchantBalanceOperationRemoteService.singleWithdrawPlaceAndPay(merchantNo, singlePayoutParameterDTO);
			} catch (Exception e) {
				// 错误了 处理成处理中
				BaseLogger.error(String.format("代付系统异常"), e);
				if (tpsClientResponse == null) {
					tpsClientResponse = new TpsClientResponse<SinglePayoutParameterDTO>();
				}
				tpsClientResponse.setDealProcess("银行处理中");
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("代付系统异常，需要运营人工届入 userId[%s] amount[%s] orderNo[%s]", userId, amount, orderNo)));
			}
			BaseLogger.audit(String.format("代发结束 userId[%s] tpsClientResponse[%s]", userId, tpsClientResponse));
			if (!tpsClientResponse.isDealFail()) {
				// 1.3 更新 下单
				try {
					userAccountTransactionService.withdrawEaToBank(userId, orderNo, amount, tpsClientResponse.getTransStatus());
				} catch (Exception e) {
					BaseLogger.error(String.format("实时代发更新异常 userId[%s] amount[%s] orderNo[%s] ", userId, amount, orderNo), e);
					BaseLogger.audit(String.format("实时代发更新异常 userId[%s] amount[%s] orderNo[%s] ", userId, amount, orderNo));
					throw new AppException(String.format("实时代发更新异常 userId[%s] amount[%s] orderNo[%s] ", userId, amount, orderNo), e);
				}
			}
		} catch (Exception e) {
			BaseLogger.error("存钱罐提现异常", e);
			BaseLogger.audit("error withdrawEaToBank userId[" + userId + "] withdrawMoney[" + withdrawMoney + "]");
			// 只要是失败都走人工运营处理
			needUnlock = Boolean.FALSE; // 不能解锁，需要人工解决异常问题
		} finally {
			// 2. 解锁
			if (needUnlock) {
				int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
				BaseLogger.audit("unlock withdrawEaToBank userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]");

				if (unlockUpdateCount != 1) {
					sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("transferEaToMa 异常，userId[" + userId + "]"));
				}
			}
			if (!needUnlock) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("transferEaToMa 解锁异常，需要运营人工届入 userId[%s] amount[%s] tpsClientResponse[%s]", userId, amount, tpsClientResponse)));
				throw new AppException("系统保护程序启动：账户提现异常被锁定，请联系客服");
			}
		}

		if (tpsClientResponse.isDealFail()) {
			throw new AppException(tpsClientResponse.getTransMessage());
		}

		String title2 = tpsClientResponse.isDealSuccess() ? "交易完成" : "存钱罐已经提现到银行卡";
		String tip2 = tpsClientResponse.isDealSuccess() ? null : "预计资金2小时内到账";
		String title3 = tpsClientResponse.isDealProcess() ? "交易完成" : null;
		BaseLogger.audit("end withdrawEaToBank userId[" + userId + "] withdrawMoney[" + withdrawMoney + "] tpsClientResponse :" + tpsClientResponse);

		// 调用发送短信
		try {
			// 发送短信
			Map<String, Object> defineMessageData = new HashMap<String, Object>();
			defineMessageData.put(SEND_MESSAGE_DATA_001, withdrawMoney);

			userPhoneMessageService.sendTextMessage(SEND_MESSAGE_SYSTEM, tradeAccountCardDTO.getPhone(), SEND_MESSAGE_TYPE, defineMessageData);
		} catch (Exception e) {
			BaseLogger.error("MBUserAccountService-->withdrawEaToBank-->sendTextMessage-->is error![" + userId + "]", e);
		}

		return new MBTransReturnDTO("提现申请成功", "申请提现" + amount + "元", "今天", title2, tip2, title3, null);
	}

	public MBTransReturnDTO withdrawTaToBank(String userId, String withdrawMoney) {
		BaseLogger.audit("start withdrawTaToBank userId[" + userId + "] withdrawMoney[" + withdrawMoney + "]");
		// 校验参数
		BigDecimal amount = confirmUserIsExitAndMoneyIsValid(userId, withdrawMoney);
		// 先判断资金是否足够
		checkAccountIsEnoughToTransfer(userId, UserConstant.TRADETYPE_TA, amount);
		// 1.2 次数是否足够
		if (userAccountMapper.queryTaAvavilableWithDrawTimes(userId) <= 0) {
			throw new AppException("您已超过当日提现次数，此次提现取消");
		}

		if (!checkUserAccountBalanceService.checkUserAccountBalance(userId)) {
			throw new AppException("账户异常，请稍后重试");
		}
		// 1.2 1 查询安全账号信息
		TradeAccountCardDTO tradeAccountCardDTO = userAccountMapper.queryVaildSecurityCardInfoByUserId(userId);
		BaseLogger.audit(String.format("TA查询实时代付安全卡信息tradeAccountCardDTO[%s]", tradeAccountCardDTO));
		if (tradeAccountCardDTO == null || StringUtils.isBlank(tradeAccountCardDTO.getCardOwer()) || StringUtils.isBlank(tradeAccountCardDTO.getBankCardNo())) {
			throw new AppException("账户信息有异常，请联系客服人员");
		}
		// 1. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}
		Boolean needUnlock = Boolean.TRUE;
		TpsClientResponse<SinglePayoutParameterDTO> tpsClientResponse = null;
		try {
			// 主业务逻辑
			// 1.1 检测余额是否够转账
			checkAccountIsEnoughToTransfer(userId, UserConstant.TRADETYPE_TA, amount);
			// 1.2 次数是否足够
			if (userAccountMapper.queryTaAvavilableWithDrawTimes(userId) <= 0) {
				throw new AppException("您已超过当日提现次数，此次提现取消");
			}
			// 1.2 提现
			String orderNo = userAccountTransactionService.getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
			// 实际发起交易
			BaseLogger.audit(String.format("TA准备发起代发 userId[%s] amount[%s] orderNo[%s] ", userId, amount, orderNo));
			try {
				String[] baks = new String[] { userId, getClientRealIp() };
				SinglePayoutParameterDTO singlePayoutParameterDTO = new SinglePayoutParameterDTO(orderNo, tradeAccountCardDTO.getCardOwer(), tradeAccountCardDTO.getBankCardNo(), amount.toString(), comments, baks);
				tpsClientResponse = merchantBalanceOperationRemoteService.singleWithdrawPlaceAndPay(merchantNo, singlePayoutParameterDTO);
			} catch (Exception e) {
				// 错误了 处理成处理中
				BaseLogger.error(String.format("TA代付系统异常"), e);
				if (tpsClientResponse == null) {
					tpsClientResponse = new TpsClientResponse<SinglePayoutParameterDTO>();
				}
				tpsClientResponse.setDealProcess("银行处理中");
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("代付系统异常，需要运营人工届入 userId[%s] amount[%s] orderNo[%s]", userId, amount, orderNo)));
			}
			BaseLogger.audit(String.format("TA代发结束 userId[%s] tpsClientResponse[%s]", userId, tpsClientResponse));
			if (!tpsClientResponse.isDealFail()) {
				// 1.3 更新 下单
				try {
					userAccountTransactionService.withdrawTaToBank(userId, orderNo, amount, tpsClientResponse.getTransStatus());
				} catch (Exception e) {
					BaseLogger.error(String.format("TA实时代发更新异常 userId[%s] amount[%s] orderNo[%s] ", userId, amount, orderNo), e);
					BaseLogger.audit(String.format("TA实时代发更新异常 userId[%s] amount[%s] orderNo[%s] ", userId, amount, orderNo));
					throw new AppException(String.format("TA实时代发更新异常 userId[%s] amount[%s] orderNo[%s] ", userId, amount, orderNo), e);
				}
			}
		} catch (Exception e) {
			BaseLogger.error("存钱罐提现异常", e);
			BaseLogger.audit("error withdrawTaToBank userId[" + userId + "] withdrawMoney[" + withdrawMoney + "]");
			// 只要是失败都走人工运营处理
			needUnlock = Boolean.FALSE; // 不能解锁，需要人工解决异常问题
		} finally {
			// 2. 解锁
			if (needUnlock) {
				int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
				BaseLogger.audit("unlock withdrawTaToBank userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]");

				if (unlockUpdateCount != 1) {
					sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("transferTaToMa 异常，userId[" + userId + "]"));
				}
			}
			if (!needUnlock) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("transferTaToMa 解锁异常，需要运营人工届入 userId[%s] amount[%s] tpsClientResponse[%s]", userId, amount, tpsClientResponse)));
				throw new AppException("系统保护程序启动：账户提现异常被锁定，请联系客服");
			}
		}

		if (tpsClientResponse.isDealFail()) {
			throw new AppException(tpsClientResponse.getTransMessage());
		}

		String title2 = tpsClientResponse.isDealSuccess() ? "交易完成" : "存钱罐已经提现到银行卡";
		String tip2 = tpsClientResponse.isDealSuccess() ? null : "预计资金2小时内到账";
		String title3 = tpsClientResponse.isDealProcess() ? "交易完成" : null;
		BaseLogger.audit("end withdrawTaToBank userId[" + userId + "] withdrawMoney[" + withdrawMoney + "] tpsClientResponse :" + tpsClientResponse);

		// 调用发送短信
		try {
			// 发送短信
			Map<String, Object> defineMessageData = new HashMap<String, Object>();
			defineMessageData.put(SEND_MESSAGE_DATA_001, withdrawMoney);

			userPhoneMessageService.sendTextMessage(SEND_MESSAGE_SYSTEM_TA, tradeAccountCardDTO.getPhone(), SEND_MESSAGE_TYPE_TA, defineMessageData);
		} catch (Exception e) {
			BaseLogger.error("MBUserAccountService-->withdrawTaToBank-->sendTextMessage-->is error![" + userId + "]", e);
		}

		return new MBTransReturnDTO("提现申请成功", "申请提现" + amount + "元", "今天", title2, tip2, title3, null);
	}

	public Object withdrawMaToBank(String userId,String source, String withdrawMoney) {
		
		Map<String, String> parammap = new HashMap<String,String>();
		//String source="APP";
		
		BaseLogger.audit("start withdrawMaToBank userId[" + userId + "] withdrawMoney[" + withdrawMoney + "]");
		// 校验参数
		String amount = ipsConfirmUserIsExitAndMoneyIsValid(userId, withdrawMoney);
		
		BigDecimal money = new BigDecimal(amount);
		// 1. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}
		
		IPSResponse ips =null;
		
		try {
			// 主业务逻辑
			// 1.1 检测余额是否够转账  此处可以不校验  环迅 ips 校验
			checkAccountIsEnoughToTransfer(userId, UserConstant.TRADETYPE_MA, money);

			// 1.2 次数是否足够  环迅 ips 校验
			/*if (userAccountMapper.queryMaAvavilableWithDrawTimes(userId) <= 0) {
				throw new AppException("您已超过当日提现次数，此次提现取消");
			}*/

			// 1.3单笔提现限额校验   环迅 ips 校验
			/*if (userAccountMapper.querysingleWithdrawUpperLimit().compareTo(amount) < 0) {
				throw new AppException("单笔提现金额超限,请重新输入金额");
			}*/
			/*if (new BigDecimal(10).compareTo(amount) > 0) {
				throw new AppException("最低提现10元,请重新输入金额");
			}*/
			
			//查询commom_params表 获取参数
			Map<String,String>  paramList=checkCommParamMapper.queryWithDrawParam();
			
			if (paramList==null||paramList.isEmpty()) {
				throw new AppException("系统繁忙请联系管理员");
			}
			
			//用户类型 1：个人 2：企业
			String userType = paramList.get("userType");
			//平台手续费
			String merFee = paramList.get("merFee");
			
			//平台手续费类型  平台手续内外扣类型 内扣 1、外扣 2
			String merFeeType = paramList.get("merFeeType");
			
			//IPS 手续费承担方 1：平台承担，2：用户承担
			String ipsFeeType = paramList.get("ipsFeeType");
			
			
			//根据用户id获取 存管账号ipsAcctNo 
			String ipsAccountNo=checkCommParamMapper.queryIpsAccountByUserId(userId);
			
			WithDraw withDraw = new WithDraw();
			

			String orderId = GenerateMerBillNoUtil.getBillNoGenerate().substring(0, 30);

		

			withDraw.setMerBillNo(orderId);
			withDraw.setMerDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			withDraw.setUserType(userType);
			withDraw.setTrdAmt(withdrawMoney);
			withDraw.setMerFee(merFee);
			withDraw.setMerFeeType(merFeeType);
			withDraw.setIpsFeeType(ipsFeeType);
			withDraw.setIpsAcctNo(ipsAccountNo);
			//withDraw.setIpsAcctNo(ipsAccountNo);
//			withDraw.setWebUrl(localServerIp+"/api/ips/withdraw/webCallBack?source=");
			//withDraw.setS2SUrl(localServerIp+"/api/ips/withdraw/s2sCallBack");
			withDraw.setWebUrl(localServerIp+"/api/ips/withdraw/webCallBack?source="+source);
			withDraw.setS2SUrl(localServerIp+"/api/ips/withdraw/s2sCallBack");

			
			
			//封装参数给前段HTML表单提交
			parammap = IpsRequestUtils.getRequestMap(Define.OperationType.WITHDRAW, JSONUtils.obj2json(withDraw));
			
			parammap.put("url", HttpClientUtils.ips_url);
			
			
			//==============此处调ips接口 返回ips頁面給 APP端 暂不用不用注释=====
			ips = withDrawService.withDraw(orderId,withDraw,source);
			
			

			 
			 //手续费
			 BigDecimal fee = new BigDecimal(0);
			 
			 if("2".equals(ipsFeeType)){
				 fee=new BigDecimal(merFee);
			 }
			 
			 
			// 1.3 提现申请
			userAccountTransactionService.withdrawIpsToCard(userId, money,fee,orderId);
			
			
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			// 2. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
			BaseLogger.audit("unlock withdrawMaToBank userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]");
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("transferEaToMa 异常，userId[" + userId + "]"));
			}
		}
		// 获取下一个自然日
		//String nextDay = getNextMoonDay(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		// 获取日期是星期几
		//String weekDay = getWeekDay(nextDay);
		//BaseLogger.audit("end withdrawMaToBank userId[" + userId + "] withdrawMoney[" + withdrawMoney + "]");
		// 预计资金在1-2个工作日到账

		// 余额提现,是否在这发短信

		return parammap;
	}

	public MBTransReturnDTO transferEaToMa(String userId, String transferMoney) {
		BaseLogger.audit("start transferEaToMa userId[" + userId + "] withdrawMoney[" + transferMoney + "]");
		// 校验参数
		BigDecimal amount = confirmUserIsExitAndMoneyIsValid(userId, transferMoney);
		// 1. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}
		try {
			// 主业务逻辑
			// 1.1 检测余额是否够转账
			checkAccountIsEnoughToTransfer(userId, UserConstant.TRADETYPE_EA, amount);

			// 1.2 转账
			userAccountTransactionService.transferEaToMa(userId, amount);
		} finally {
			// 2. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
			BaseLogger.audit("unlock transferEaToMa userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]");
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("transferEaToMa 异常，userId[" + userId + "]"));
			}
		}
		BaseLogger.audit("end transferEaToMa userId[" + userId + "] withdrawMoney[" + transferMoney + "]");
		return new MBTransReturnDTO("转出成功", "成功转出" + transferMoney + "元", "今天", "交易完成", null, null, null);
	}

	public MBTransReturnDTO transferTaToMa(String userId, String transferMoney) {
		BaseLogger.audit("start transferTaToMa userId[" + userId + "] withdrawMoney[" + transferMoney + "]");
		// 校验参数
		BigDecimal amount = confirmUserIsExitAndMoneyIsValid(userId, transferMoney);
		// 1. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}
		try {
			// 主业务逻辑
			// 1.1 检测余额是否够转账
			checkAccountIsEnoughToTransfer(userId, UserConstant.TRADETYPE_TA, amount);

			// 1.2 转账
			userAccountTransactionService.transferTaToMa(userId, amount);
		} finally {
			// 2. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
			BaseLogger.audit("unlock transferTaToMa userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]");
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("transferTaToMa 异常，userId[" + userId + "]"));
			}
		}
		BaseLogger.audit("end transferTaToMa userId[" + userId + "] withdrawMoney[" + transferMoney + "]");
		return new MBTransReturnDTO("转出成功", "成功转出" + transferMoney + "元", "今天", "交易完成", null, null, null);
	}

	public MBTransReturnDTO transferV1ToMa(String userId, String transferMoney) {
		BaseLogger.audit("start transferV1ToMa userId[" + userId + "] withdrawMoney[" + transferMoney + "]");
		// 校验参数
		BigDecimal amount = confirmUserIsExitAndMoneyIsValid(userId, transferMoney);
		// 1. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}
		try {
			// 主业务逻辑
			// 1.1 检测余额是否够转账
			checkAccountIsEnoughToTransfer(userId, UserConstant.TRADETYPE_V1, amount);

			// 1.2 转账
			userAccountTransactionService.transferV1ToMa(userId, amount);
		} finally {
			// 2. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
			BaseLogger.audit("unlock transferV1ToMa userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]");
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("transferV1ToMa 异常，userId[" + userId + "]"));
			}
		}

		// 获取下一个工作日
		String nextDay = getNextDay(new Date());
		// 获取日期是星期几
		String weekDay = getWeekDay(nextDay);
		BaseLogger.audit("end transferV1ToMa userId[" + userId + "] withdrawMoney[" + transferMoney + "]");
		return new MBTransReturnDTO("申请转出成功", "成功转出" + transferMoney + "元", "今天", "转出到余额账户", nextDay + " " + weekDay, "交易完成", null);
	}

	public MBTransReturnDTO applyMaToV1(String userId, String applyMoney) {
		BaseLogger.audit("start applyMaToV1 userId[" + userId + "] withdrawMoney[" + applyMoney + "]");
		// 校验参数
		BigDecimal amount = confirmUserIsExitAndMoneyIsValid(userId, applyMoney);
		// 1. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}
		try {
			// 主业务逻辑
			// 1.1 检测余额是否够转账
			checkAccountIsEnoughToTransfer(userId, UserConstant.TRADETYPE_MA, amount);

			// 1.2 转账
			userAccountTransactionService.applyMaToV1(userId, amount);
		} finally {
			// 2. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
			BaseLogger.audit("unlock applyMaToV1 userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]");
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("transferV1ToMa 异常，userId[" + userId + "]"));
			}
		}

		String confirmDay = getConfirmAmountDate(UserConstant.TRADETYPE_V1, new Date());
		// 获取日期是星期几
		String weekDay2 = getWeekDay(confirmDay);
		String tip2 = confirmDay + " " + weekDay2;

		// 获取日期是星期几
		// String weekDay3=getWeekDay(getNextDay(confirmDay));
		// String tip3 = getNextDay(confirmDay)+" "+weekDay3;
		BaseLogger.audit("end applyMaToV1 userId[" + userId + "] withdrawMoney[" + applyMoney + "]");
		// 计算下确认份额日期，收益到账日期
		return new MBTransReturnDTO("转入成功", "成功转入" + applyMoney + "元", "今天", "开始计算收益", tip2, "交易完成", null);
	}

	public MBTransReturnDTO applyMaToEa(String userId, String applyMoney) {
		BaseLogger.audit("start applyMaToEa userId[" + userId + "] withdrawMoney[" + applyMoney + "]");
		// 校验参数
		BigDecimal amount = confirmUserIsExitAndMoneyIsValid(userId, applyMoney);
		// 1. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}
		try {
			// 主业务逻辑
			// 1.1 检测余额是否够转账
			checkAccountIsEnoughToTransfer(userId, UserConstant.TRADETYPE_MA, amount);

			// 1.2 转账
			userAccountTransactionService.applyMaToEa(userId, amount);
		} finally {
			// 2. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
			BaseLogger.audit("unlock applyMaToEa userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]");
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("transferV1ToMa 异常，userId[" + userId + "]"));
			}
		}
		// 计算下确认份额日期，收益到账日期
		String confirmDay = getConfirmAmountDate(UserConstant.TRADETYPE_EA, new Date());

		// 获取日期是星期几
		String weekDay2 = getWeekDay(confirmDay);
		String tip2 = confirmDay + " " + weekDay2;

		// 获取日期是星期几
		// String weekDay3=getWeekDay(getNextDay(confirmDay));
		// String tip3 = getNextDay(confirmDay)+" "+weekDay3;
		BaseLogger.audit("end applyMaToEa userId[" + userId + "] withdrawMoney[" + applyMoney + "]");
		return new MBTransReturnDTO("转入成功", "成功转入" + amount + "元", "今天", "开始计算收益", tip2, "交易完成", null);
	}

	public MBTransReturnDTO applyMaToTa(final String userId, String applyMoney) {
		BaseLogger.audit("start applyMaToTa userId[" + userId + "] withdrawMoney[" + applyMoney + "]");
		// 校验参数
		BigDecimal amount = confirmUserIsExitAndMoneyIsValid(userId, applyMoney);
		if (amount.compareTo(new BigDecimal("100")) < 0) {
			throw new AppException("金额不合法");
		}

		if (applyMoney.lastIndexOf(".") != -1 && (StringUtil.isNullString(applyMoney.substring(applyMoney.lastIndexOf(".") + 1)) || Integer.valueOf(applyMoney.substring(applyMoney.lastIndexOf(".") + 1)) > 0)) {
			throw new AppException("金额不合法");
		}

		// 1.0 检测T金所剩余投资金额是否足够
		checkTfaxIsEnough(userId, applyMoney);

		// 1. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}
		try {
			// 主业务逻辑
			// 1.1 检测余额是否够转账
			checkAccountIsEnoughToTransfer(userId, UserConstant.TRADETYPE_MA, amount);
			// 1.2 转账
			userAccountTransactionService.applyMaToTa(userId, amount);
			// 1.3 T金所开户
			// String serialOpenAccountNo =
			// userAccountTransactionService.getId("TA",
			// ISequenceService.SEQ_NAME_TRADE_SEQ_TA);
			// //serialNo
			// String now = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new
			// Date());
			//
			// MBUserDTO userDTO = userMapper.queryUserById(userId);
			// if (userDTO.getIsOpenTa().equals("0")) {//已经在T金所开户过了，不需要再次开户
			// TradeAccountCardDTO tradeAccountCardDTO =
			// userAccountMapper.queryVaildSecurityCardInfoByUserId(userId);
			// Map<String, String> paramMap = new HashMap<>();
			// paramMap.put("version", tfaxVersion);//版本号
			// paramMap.put("serialNo", serialOpenAccountNo);//流水号
			// paramMap.put("terminalType", tfaxTerminalType);//终端类型
			// paramMap.put("chnlId", tfaxChnlId);//渠道标识
			// paramMap.put("date", now);//交易日期和时间
			// paramMap.put("businessCode", tfaxBusinessCode);//业务代码
			// paramMap.put("protocolNo",
			// tradeAccountCardDTO.getBankCardNo());//银行卡号
			// paramMap.put("fpAcct", "");// 理财账户
			// paramMap.put("fpTxnAcct", "");//理财交易账号
			// paramMap.put("name", userDTO.getRealName());//客户姓名
			// paramMap.put("certType", tfaxCertType);//证件类型
			// paramMap.put("certNo", userDTO.getIndentityNo());// 证件号码
			// paramMap.put("mobilePhone", userDTO.getPhone());//手机号
			// paramMap.put("email", userDTO.getEmail());// 邮箱
			// paramMap.put("extension", "");// 消息扩展
			//
			// String paramJsonStr = JSON.toJSONString(paramMap);
			// ThirdPartyLogDTO thirdPartyLogDTO = new ThirdPartyLogDTO();
			// String logId = UUID.randomUUID().toString().replace("-","");
			// thirdPartyLogDTO.setId(logId);
			// thirdPartyLogDTO.setThirdParty("TFax");
			// thirdPartyLogDTO.setInterfaceUrl(tfaxOpenAccountUrl);
			// thirdPartyLogDTO.setInterfaceDesc("开户");
			// thirdPartyLogDTO.setInParameter(paramJsonStr);
			// thirdPartyLogDTO.setCreateBy("wj");
			// thirdPartyLogDTO.setCreateDate(new Date());
			// thirdPartyLogMapper.insert(thirdPartyLogDTO);
			// try {
			// String openAccountResult =
			// HttpClientUtil.post(tfaxOpenAccountUrl, paramMap);
			//
			// JSONObject jsonObject = JSON.parseObject(openAccountResult);
			// if (null != jsonObject && null != jsonObject.getString("code") &&
			// (jsonObject.getString("code").equals(taOpenAccountSuccessFlag) ||
			// jsonObject.getString("code").equals(taAlreadyOpenAccountFlag))) {
			// userMapper.updateTaAccountStatus(userId);
			// }
			//
			// thirdPartyLogDTO = new ThirdPartyLogDTO();
			// thirdPartyLogDTO.setId(logId);
			// thirdPartyLogDTO.setOutParameter(openAccountResult);
			// thirdPartyLogDTO.setUpdateDate(new Date());
			// thirdPartyLogMapper.update(thirdPartyLogDTO);
			// } catch (Exception e) {
			// BaseLogger.audit("tfax open account error, detail as : " +
			// e.getMessage());
			// thirdPartyLogDTO = new ThirdPartyLogDTO();
			// thirdPartyLogDTO.setId(logId);
			// thirdPartyLogDTO.setErrMsg(e.getMessage());
			// thirdPartyLogDTO.setUpdateDate(new Date());
			// thirdPartyLogMapper.update(thirdPartyLogDTO);
			// }
			// }

		} finally {
			// 2. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
			BaseLogger.audit("unlock applyMaToEa userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]");
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("transferV1ToMa 异常，userId[" + userId + "]"));
			}
		}
		// 计算下确认份额日期，收益到账日期
		String confirmDay = getTAConfirmAmountDate(new Date());

		// 获取日期是星期几
		String weekDay2 = getWeekDay(confirmDay);
		String tip2 = confirmDay + " " + weekDay2;

		// 获取日期是星期几
		// String weekDay3=getWeekDay(getNextDay(confirmDay));
		// String tip3 = getNextDay(confirmDay)+" "+weekDay3;
		BaseLogger.audit("end applyMaToTa userId[" + userId + "] withdrawMoney[" + applyMoney + "]");
		return new MBTransReturnDTO("转入成功", "成功转入" + amount + "元", "今天", "开始计算收益", tip2, "交易完成", null);
	}

	// 获取T金所确认份额的时间， 下一个工作日
	private String getTAConfirmAmountDate(Date buyDay) {
		Integer ymdInt = new Integer(new SimpleDateFormat("yyyyMMdd").format(buyDay));
		// 查询buyday 能确认份额的两个工作日
		List<String> workdays = userAccountMapper.queryNextTwoWorkdaysByBuyday(ymdInt);
		String confirmDay = workdays.get(0);
		try {
			return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(confirmDay));
		} catch (ParseException e) {
			BaseLogger.error("getTAConfirmAmountDate 获取下一确认份额天异常：" + buyDay);
		}
		return "";
	}

	// 获取T金所确认份额的时间， 下一个工作日
	public Date queryTAConfirmAmountDate(Date buyDay) {
		Integer ymdInt = new Integer(new SimpleDateFormat("yyyyMMdd").format(buyDay));
		// 查询buyday 能确认份额的两个工作日
		List<String> workdays = userAccountMapper.queryNextTwoWorkdaysByBuyday(ymdInt);
		String confirmDay = workdays.get(0);
		try {
			return new SimpleDateFormat("yyyyMMdd").parse(confirmDay);
		} catch (ParseException e) {
			BaseLogger.error("queryTAConfirmAmountDate 获取下一确认份额天异常：" + buyDay);
		}
		return buyDay;
	}

	private void checkTfaxIsEnough(String userId, String applyMoney) {
		BigDecimal tfax = userAccountMapper.queryProductAvailableAmount(userId, "tfax");
		if (tfax.compareTo(new BigDecimal(applyMoney)) < 0) {
			throw new AppException("金额不合法");
		}
	}

	// public MBRechargeDTO rechargeToMaSendCode(MBRechargeDTO rechargeDTO) {
	// // 验证
	// if (!rechargeDTO.validateSendCodeParameter()) {
	// throw new AppException(rechargeDTO.getMessage());
	// }
	// // 验证 主账户的姓名身份证是否一致
	// this.validateSameCardInfo(rechargeDTO);
	// // 1. 查询是否卡号是否已经绑定过
	// int bindRepeatCardCount =
	// userAccountMapper.queryBindRepeatCardCountBycardNo(rechargeDTO.getUserId(),
	// rechargeDTO.getCardNo());
	// if (bindRepeatCardCount > 0) {
	// throw new AppException("此卡号已经绑定，请勿重复绑卡");
	// }
	// // 1. 生成 card id
	// String cardId = userAccountTransactionService.getId("AC",
	// ISequenceService.SEQ_NAME_CARD_SEQ);
	// // 2. 发送验证短信
	// try {
	// B2CGetawayParameterDTO b2cGetawayParameterDTO = new
	// B2CGetawayParameterDTO();
	// BeanUtils.copyProperties(b2cGetawayParameterDTO, rechargeDTO); // copy 属性
	// b2cGetawayParameterDTO.setCustomerId(cardId);
	// b2cGetawayParameterDTO.setBankCode(rechargeDTO.getEbtongBankCode());
	// B2CGetawayDTO b2cGetawayDTO =
	// b2CGetawayImplService.getBindingCardMessageCode(b2cGetawayParameterDTO);
	// if (!b2cGetawayDTO.getSuccessful()) {
	// throw new AppException("请检查姓名,身份证,卡号与预留银行手机号是否正确");
	// }
	// if (b2cGetawayDTO.getSuccessful()) {
	// MBRechargeDTO MBRechargeDTO = new MBRechargeDTO();
	// MBRechargeDTO.setCardId(cardId);
	// MBRechargeDTO.setOrderNo(b2cGetawayDTO.getOutTradeNo());
	// MBRechargeDTO.setCertType(null);
	// //新增短信有效时间 update by Eric $.2015-09-29 14:41:12
	// MBRechargeDTO.setRemainTime("120");
	// return MBRechargeDTO;
	// }
	// } catch (Exception e) {
	// if(e instanceof AppException){
	// String msg=((AppException)e).getMessage();
	// if(StringUtils.isBlank(msg)){
	// throw new AppException("请核对银行卡与身份证信息是否匹配");
	// }
	// throw (AppException)e;
	// }else {
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("rechargeToMaSendCode 充值发短信异常，userId[" +
	// rechargeDTO.getUserId() + "]" + e.getMessage()));
	// }
	// throw new AppException("银行系统繁忙，请稍后再试");
	// }
	// throw new AppException("银行系统繁忙，请稍后再试！");
	// }

	// public Boolean doRechargeToMa(MBRechargeDTO rechargeDTO) {
	// // 验证
	// if (!rechargeDTO.validateDoRechargeToMaParameter()) {
	// throw new AppException(rechargeDTO.getMessage());
	// }
	// // 验证 主账户的姓名身份证是否一致
	// this.validateSameCardInfo(rechargeDTO);
	// int lockUpdateCount =
	// userAccountMapper.updateUserWithdrawLock(rechargeDTO.getUserId());
	// // 不等于说明已经上锁 没更新到记录 或者有其他异常情况
	// if (lockUpdateCount != 1) {
	// throw new AppException("此账户其他交易正在处理中，请稍后再试");
	// }
	// try {
	// // 1.1 检验卡号是否重复
	// int bindRepeatCardCount =
	// userAccountMapper.queryBindRepeatCardCountBycardNo(rechargeDTO.getUserId(),
	// rechargeDTO.getCardNo());
	// if (bindRepeatCardCount > 0) {
	// throw new AppException("此卡号已经绑定，请勿重复绑卡");
	// }
	// // 1.2 开始扣钱
	// B2CGetawayDTO b2cGetawayDTO =
	// b2CGetawayImplService.QuickPay(buildB2CGetawayParameterDTO(rechargeDTO));
	// if (!b2cGetawayDTO.getSuccessful()) {
	// //sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("doRechargeToMa 充值发逻辑异常，userId[" + rechargeDTO.getUserId() +
	// "] 错误信息[" + b2cGetawayDTO.getErrorMessage()));
	// throw new AppException("银行系统繁忙，请重新尝试充值");
	// }
	// if (b2cGetawayDTO.getSuccessful()) {
	// // 1.3 充值成功后逻辑
	// userAccountTransactionService.doRechargeToMa(rechargeDTO);
	// return Boolean.TRUE;
	// }
	// } catch(Exception e){
	// if(e instanceof AppException){
	// if(StringUtils.isBlank(((AppException)e).getMessage()))
	// throw new AppException("银行系统繁忙，请重新尝试充值");
	// }else {
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("doRechargeToMa 充值发逻辑异常，userId[" + rechargeDTO.getUserId() +
	// "] exception :" + e.getMessage()));
	// }
	// throw new AppException(e.getMessage());
	// }finally {
	// // 2. 解锁
	// int unlockUpdateCount =
	// userAccountMapper.updateUserWithdrawUnlock(rechargeDTO.getUserId());
	// if (unlockUpdateCount != 1) {
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("doRechargeToMa 异常，userId[" + rechargeDTO.getUserId() +
	// "]"));
	// }
	// }
	// throw new AppException("银行系统繁忙，请稍后再试！");
	// }

	/**
	 * 校验实名同人信息
	 *
	 * @param rechargeDTO
	 */
	private void validateSameCardInfo(MBRechargeDTO rechargeDTO) {
		// 1. 查询用户信息
		MBRechargeDTO queryRechargeDTO = userAccountMapper.queryRealNameUserInfo(rechargeDTO.getUserId());
		if (queryRechargeDTO == null) {
			throw new AppException("用户信息被锁定，请联系客服");
		}
		// 2. 对比信息
		if (StringUtils.isNotBlank(queryRechargeDTO.getCertNo()) || StringUtils.isNotBlank(queryRechargeDTO.getRealName())) {
			if (!rechargeDTO.getRealName().equals(queryRechargeDTO.getRealName()) || !rechargeDTO.getCertNo().equals(queryRechargeDTO.getCertNo())) {
				throw new AppException("用户实名信息与原始不符，请联系客服");
			}
		}
	}

	// private B2CGetawayParameterDTO buildB2CGetawayParameterDTO(MBRechargeDTO
	// rechargeDTO) {
	// B2CGetawayParameterDTO b2cGetawayParameterDTO = new
	// B2CGetawayParameterDTO();
	// b2cGetawayParameterDTO.setDynamicCode(rechargeDTO.getDynamicCode());
	// b2cGetawayParameterDTO.setOutTradeNo(rechargeDTO.getOrderNo());
	// b2cGetawayParameterDTO.setTotalFee(rechargeDTO.getAmount());
	//
	// b2cGetawayParameterDTO.setCustomerId(rechargeDTO.getCardId());
	// b2cGetawayParameterDTO.setRealName(rechargeDTO.getRealName());
	// b2cGetawayParameterDTO.setCertType(rechargeDTO.getCertType());
	// b2cGetawayParameterDTO.setCertNo(rechargeDTO.getCertNo());
	// b2cGetawayParameterDTO.setCardBindMobilePhoneNo(rechargeDTO.getCardBindMobilePhoneNo());
	//
	// b2cGetawayParameterDTO.setDefaultBank(rechargeDTO.getEbtongBankCode());
	// b2cGetawayParameterDTO.setBankCardNo(rechargeDTO.getCardNo());
	// b2cGetawayParameterDTO.setSubject("充值");
	// b2cGetawayParameterDTO.setExterInvokeIp("127.0.0.1");
	// b2cGetawayParameterDTO.setExtendParam("");
	// b2cGetawayParameterDTO.setNotifyUrl("http://www.vjwealth.com/");
	// b2cGetawayParameterDTO.setBody("");
	// b2cGetawayParameterDTO.setShowUrl("");
	// b2cGetawayParameterDTO.setPayMethod("");
	// b2cGetawayParameterDTO.setExtraCommonParam("");
	//
	// b2cGetawayParameterDTO.setUserId(rechargeDTO.getUserId());
	//
	// return b2cGetawayParameterDTO;
	// }

	// public MBRechargeDTO rechargeToMaAlreadyBindCardSendCode(String userId,
	// String cardId, String amount) {
	// // 1. 校验参数合法性
	// this.checkRechargeParamValidate(userId, cardId, amount);
	//
	// // 2. 组装充值参数
	// MBRechargeDTO rechargeDTO =
	// userAccountMapper.queryBindCardInfoAndUserInfo(userId, cardId);
	// rechargeDTO.setAmount(amount);
	//
	// // // 1.1参数自校验合法性 //废弃此校验方法 update by xuewentao $.2016-01-13 15:00:29
	// // if(!rechargeDTO.validateSendCodeParameter()){
	// // sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡充值发逻辑异常，userId[" +
	// rechargeDTO.getUserId() + "]"));
	// // throw new AppException(rechargeDTO.getMessage());
	// // }
	// // 3. 充值
	// try {
	// B2CGetawayParameterDTO b2cGetawayParameterDTO = new
	// B2CGetawayParameterDTO();
	// BeanUtils.copyProperties(b2cGetawayParameterDTO, rechargeDTO); // copy 属性
	// b2cGetawayParameterDTO.setCustomerId(rechargeDTO.getCardId());
	// b2cGetawayParameterDTO.setBankCode(rechargeDTO.getEbtongBankCode());
	// B2CGetawayDTO b2cGetawayDTO =
	// b2CGetawayImplService.getBindingCardMessageCode(b2cGetawayParameterDTO);
	// if (!b2cGetawayDTO.getSuccessful()) {
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("rechargeToMaAlreadyBindCardSendCode 充值发短信异常，userId[" +
	// rechargeDTO.getUserId() + "]"));
	// throw new AppException("账户信息不正确，请联系客服");
	// }
	// if (b2cGetawayDTO.getSuccessful()) {
	// MBRechargeDTO MBRechargeDTO = new MBRechargeDTO();
	// MBRechargeDTO.setCardId(cardId);
	// MBRechargeDTO.setOrderNo(b2cGetawayDTO.getOutTradeNo());
	// MBRechargeDTO.setCertType(null);
	// //#.新增有效时间 add by eric $.2015-09-29 15:44:24
	// MBRechargeDTO.setRemainTime("120");
	// return MBRechargeDTO;
	// }
	// } catch (Exception e) {
	// if(e instanceof AppException){
	// String msg=((AppException)e).getMessage();
	// if(StringUtils.isBlank(msg)){
	// throw new AppException("请核对银行卡与身份证信息是否匹配");
	// }
	//
	// throw (AppException)e;
	// }else {
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("rechargeToMaAlreadyBindCardSendCode 充值发短信异常，userId[" +
	// rechargeDTO.getUserId() + "]" + e.getMessage()));
	// }
	// throw new AppException("银行系统繁忙，请稍后再试");
	// }
	// throw new AppException("银行系统繁忙，请稍后再试!");
	// }

	// 校验充值参数合法性
	private void checkRechargeParamValidate(String userId, String cardId, String amount) {
		// 1. 卡信息一定存在
		if (userAccountMapper.checkRechargeCardExit(userId, cardId) == 0) {
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡充值发逻辑异常，充值卡不存在 cardId[" + cardId + "]userId[" + userId + "]"));
			throw new AppException("信息异常,工程师正在努力维护中...");
		}
		// 2. 校验银行是否维护中
		if (userAccountMapper.checkRechargeCardRunHealth(cardId) == 0) {
			throw new AppException("土豪抱歉,银行正在维护中");
		}
		// 3. 金额是否在单笔范围内
		if (StringUtils.isBlank(amount) || !NumberUtils.isNumber(amount)) {
			throw new AppException("请输入正确金额");
		}

		// 3.1 单笔金额是否合法
		if (userAccountMapper.checkRechargeAmountValid(cardId, amount) == 0) {
			throw new AppException("土豪,充值金额单笔超限啦");
		}

		// 3.2 当日累积金额是否满足
		if (userAccountMapper.checkSumRechargeBalanceDayLimitValid(userId, cardId).equals("no")) {
			throw new AppException("土豪,当日累积充值超限啦,换张卡试试");
		}

	}

	// 贝付充值切换
	// public Boolean doRechargeToMaAlreadyBind(String userId, String cardId,
	// String orderNo, String amount, String code) {
	// // 校验参数合法性
	// MBRechargeDTO rechargeDTO =
	// userAccountMapper.queryBindCardInfoAndUserInfo(userId, cardId);
	// if(rechargeDTO == null){
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡充值发逻辑异常，userId[" +
	// userId + "]"));
	// throw new AppException("数据异常，请联系客服");
	// }
	// rechargeDTO.setOrderNo(orderNo);
	// rechargeDTO.setAmount(amount);
	// rechargeDTO.setDynamicCode(code);
	// if (!rechargeDTO.validateDoRechargeToMaParameter()) {
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡充值发逻辑异常，userId[" +
	// rechargeDTO.getUserId() + "]"));
	// throw new AppException(rechargeDTO.getMessage());
	// }
	// int lockUpdateCount =
	// userAccountMapper.updateUserWithdrawLock(rechargeDTO.getUserId());
	// // 不等于说明已经上锁 没更新到记录 或者有其他异常情况
	// if (lockUpdateCount != 1) {
	// throw new AppException("此账户其他交易正在处理中，请稍后再试");
	// }
	// try {
	// // 1.1 开始扣钱
	// B2CGetawayDTO b2cGetawayDTO =
	// b2CGetawayImplService.QuickPay(buildB2CGetawayParameterDTO(rechargeDTO));
	// if (!b2cGetawayDTO.getSuccessful()) {
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡充值发逻辑异常，userId[" +
	// rechargeDTO.getUserId() + "] 错误信息[" + b2cGetawayDTO.getErrorMessage()));
	// throw new AppException("银行系统繁忙，请重新尝试充值");
	// }
	// if (b2cGetawayDTO.getSuccessful()) {
	// // 1.2 充值成功后逻辑
	// userAccountTransactionService.doRechargeToMaAlreadyBind(rechargeDTO);
	// return Boolean.TRUE;
	// }
	// } catch (Exception e) {
	// if(e instanceof AppException){
	// if(StringUtils.isBlank(((AppException)e).getMessage()))
	// throw new AppException("银行系统繁忙，请重新尝试充值");
	// }else {
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("doRechargeToMaAlreadyBind 充值发逻辑异常，userId[" +
	// rechargeDTO.getUserId() + "] exception:" + e.getMessage()));
	// }
	// throw new AppException(e.getMessage());
	// } finally {
	// // 2. 解锁
	// int unlockUpdateCount =
	// userAccountMapper.updateUserWithdrawUnlock(rechargeDTO.getUserId());
	// if (unlockUpdateCount != 1) {
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("doRechargeToMaAlreadyBind 异常，userId[" +
	// rechargeDTO.getUserId() + "]"));
	// }
	// }
	// throw new AppException("银行系统繁忙，请稍后再试！");
	// }

	/**
	 * 校验交易金额参数是否合法
	 *
	 * @param userId
	 * @param money
	 */
	private BigDecimal confirmUserIsExitAndMoneyIsValid(String userId, String money) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(money)) {
			throw new AppException("参数不完整");
		}

		if (!NumberUtils.isNumber(money) || new BigDecimal(money).compareTo(new BigDecimal(0)) <= 0) {
			throw new AppException("金额不合法");
		}

		return new BigDecimal(money).setScale(2, BigDecimal.ROUND_FLOOR);
	}

	private String ipsConfirmUserIsExitAndMoneyIsValid(String userId, String money) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(money)) {
			throw new AppException("参数不完整");
		}
		
		if (!NumberUtils.isNumber(money) || new BigDecimal(money).compareTo(new BigDecimal(0)) <= 0) {
			throw new AppException("金额不合法");
		}
		
		return String.format("%.2f",Double.valueOf(money));
	}
	
	
	private void checkAccountIsEnoughToTransfer(String userId, String tradeType, BigDecimal amount) {
		BigDecimal availableAmount = userAccountMapper.queryAccountAvailableAmount(userId, tradeType);
		if (availableAmount == null) {
			BaseLogger.error("checkAccountIsEnoughToTransfer 账户信息异常，userId[" + userId + "]");
			// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
			// pageEmailMap("checkAccountIsEnoughToTransfer 账户信息异常，userId[" +
			// userId + "]"));
			throw new AppException("账户信息异常，请联系客服");
		}
		if (availableAmount.compareTo(amount) < 0) {
			BaseLogger.error("checkAccountIsEnoughToTransfer 账户信息异常 需要操作资金比原始资金还超，userId[" + userId + "]");
			// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
			// pageEmailMap("checkAccountIsEnoughToTransfer 账户信息异常 需要操作资金比原始资金还超，userId["
			// + userId + "]"));
			throw new AppException("账户可用余额不足");
		}
	}

	private Map<String, Object> pageEmailMap(String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", content);
		return map;
	}

	/**
	 * 获取确认份额时间
	 *
	 * @return 2015-09-26
	 */
	public String getConfirmAmountDate(String transType, Date buyDay) {
		String confirmDay = getConfirmAmountDate2(transType, buyDay);
		try {
			return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(confirmDay));
		} catch (ParseException e) {
			BaseLogger.error("getConfirmAmountDate 获取下一确认份额天异常：" + buyDay);
		}
		return "";
	}

	/**
	 * 获取下一个确认份额时间
	 *
	 * @param transType
	 * @param buyDay
	 * @return 20150926
	 */
	private String getConfirmAmountDate2(String transType, Date buyDay) {
		String paramKey = UserConstant.TRADETYPE_V1.equals(transType) ? "v1_confirm_share_detail_time" : "confirm_share_detail_time";

		Integer hmsInt = new Integer(new SimpleDateFormat("HHmmss").format(buyDay));
		Integer ymdInt = new Integer(new SimpleDateFormat("yyyyMMdd").format(buyDay));
		// 1. 查询公共日期 以及 今天是否是休假日
		ConfirmShareModelDTO confirmShareModelDTO = userAccountMapper.queryConfirmShareModel(paramKey, ymdInt);

		// 查询buyday 能确认份额的两个工作日
		List<String> workdays = userAccountMapper.queryNextTwoWorkdaysByBuyday(ymdInt);

		// 不是工作日 或者 大于需要确认份额的时间
		if ("N".equals(confirmShareModelDTO.getWorkingDayFlag()) || hmsInt >= new Integer(confirmShareModelDTO.getConfirmShareDetailTime())) {
			return workdays.get(1); // 取下一天
		}
		return workdays.get(0);
	}

	/**
	 * 下一个中午
	 *
	 * @param day
	 * @return 2015-12-05
	 */
	private String getNextMoonDay(String day) {
		try {
			// 1. 传入时间当天12点
			Calendar calNoonTime = Calendar.getInstance();
			Date dateMoonTime = new SimpleDateFormat("yyyy-MM-dd").parse(day);
			calNoonTime.setTime(dateMoonTime);
			calNoonTime.add(Calendar.HOUR, 24);

			// 2.原始时间
			Calendar cal = Calendar.getInstance();
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(day);
			cal.setTime(date);

			// 3. 比较
			if (cal.after(calNoonTime)) {
				// 3.1 大于当天12点的D+2
				cal.add(Calendar.DATE, 2);
				return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			}

			// 3.2 小于当天12点的,D+1
			cal.add(Calendar.DATE, 1);
			return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		} catch (ParseException e) {
			BaseLogger.error("getNextMoonDay 获取下一天异常：" + day);
		}

		return "";
	}

	// public static void main(String[] args) throws ParseException {
	// System.out.println(getNextMoonDay( new
	// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Format.stringToDate("2016-08-11 24:00:00",
	// "yyyy-MM-dd HH:mm:ss"))));
	// }
	//

	/**
	 * @return 2015-09-27
	 */
	private String getNextDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

	}

	/**
	 * @return "星期一"
	 * @throws ParseException
	 */
	private String getWeekDay(String dateStr) {
		Date date = null;
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (w < 0)
				w = 0;

			return weekDays[w];
		} catch (ParseException e) {
			BaseLogger.error(" 获取星期异常：" + dateStr);
		}

		return "";
	}

	private String getClientRealIp() {
		try {
			HttpServletRequest request = (HttpServletRequest) HessianContext.getRequest();
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			// 取第一个
			if (ip != null && ip.length() > 0 && ip.indexOf(",") > 0) {
				ip = ip.split(",")[0];
			}
			return ip;
		} catch (Exception e) {
			BaseLogger.error("getRealIp()获取IP异常", e);
			return "";
		}
	}

	// /**
	// * 联动优速-未绑卡下单并发验证码
	// * @param rechargeDTO
	// * @return
	// */
	// public MBRechargeDTO rechargeToMaSendCode(MBRechargeDTO rechargeDTO) {
	//
	// if (!rechargeDTO.validateSendCodeParameter()) {
	// throw new AppException(rechargeDTO.getMessage());
	// }
	// // 验证 主账户的姓名身份证是否一致
	// this.validateSameCardInfo(rechargeDTO);
	// // 1. 查询是否卡号是否已经绑定过
	// int bindRepeatCardCount =
	// userAccountMapper.queryBindRepeatCardCountBycardNo(rechargeDTO.getUserId(),
	// rechargeDTO.getCardNo());
	//
	// if (bindRepeatCardCount > 0) {
	// throw new AppException("此卡号已经绑定，请勿重复绑卡");
	// }
	//
	// // 2. 查询身份证是否已经绑定过
	// int
	// bindRepeatUserCount=userAccountMapper.queryBindRepeatUser(rechargeDTO.getUserId(),rechargeDTO.getCertNo());
	// if(bindRepeatUserCount>0){
	// throw new AppException("该证件号已经绑定,请勿重复绑定");
	// }
	//
	// // 1. 生成 card id
	// String cardId = userAccountTransactionService.getId("AC",
	// ISequenceService.SEQ_NAME_CARD_SEQ);
	// // 2. 发送验证短信
	// try {
	// UnionMobilePayDTO unionMobilePayDto = copyProperties(rechargeDTO);
	// unionMobilePayDto.setCustomerId(cardId);
	//
	// RiskExpandInfoDTO riskExpandInfoDTO = new RiskExpandInfoDTO();
	// riskExpandInfoDTO.setUserId(rechargeDTO.getUserId());
	// // riskExpandInfoDto.setRegisterDate(registerDate);
	// // riskExpandInfoDto.setMobile(mobile);
	//
	// PayResultDTO payResultDTO =
	// unionMobileB2CService.placeOrderAndValidate(unionMobilePayDto,
	// riskExpandInfoDTO);
	//
	// if (!payResultDTO.getResult()) {
	// throw new AppException("请检查姓名,身份证,卡号与预留银行手机号是否正确");//结果解析出错也同样提示
	// }
	// if(payResultDTO.getResult()&&"0000".equals(payResultDTO.getResultCode())){
	// MBRechargeDTO MBRechargeDTO = new MBRechargeDTO();
	// MBRechargeDTO.setCardId(cardId);
	// MBRechargeDTO.setOrderNo(payResultDTO.getOrderNo());
	// MBRechargeDTO.setCertType(null);
	// //新增短信有效时间 update by Eric $.2015-09-29 14:41:12
	// MBRechargeDTO.setRemainTime("120");
	// return MBRechargeDTO;
	// }else{
	// BaseLogger.error("下发短信验证码出错："+payResultDTO.getResultMessage());
	// throw new AppException(payResultDTO.getResultMessage());
	// }
	// } catch (Exception e) {
	// if(e instanceof AppException){
	// String msg=((AppException)e).getMessage();
	// if(StringUtils.isBlank(msg)){
	// throw new AppException("请核对银行卡与身份证信息是否匹配");
	// }
	// throw (AppException)e;
	// }else {
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("rechargeToMaSendCode 充值发短信异常，userId[" +
	// rechargeDTO.getUserId() + "]" + e.getMessage()));
	// }
	// BaseLogger.error("银行系统繁忙，请稍后再试",e);
	// throw new AppException("银行系统繁忙，请稍后再试");
	// }
	// }
	//
	// /**
	// * 联动优速-未绑卡确认付款
	// * @param rechargeDTO
	// * @return
	// */
	// public Boolean doRechargeToMa(MBRechargeDTO rechargeDTO) {
	// //1.验证前台数据
	// if (!rechargeDTO.validateDoRechargeToMaParameter()) {
	// throw new AppException(rechargeDTO.getMessage());
	// }
	// //2.验证 主账户的姓名身份证是否一致，是否并发
	// this.validateSameCardInfo(rechargeDTO);
	// //3.锁定账户，防并发
	// int lockUpdateCount =
	// userAccountMapper.updateUserWithdrawLock(rechargeDTO.getUserId());
	// // 不等于说明已经上锁 没更新到记录 或者有其他异常情况
	// if (lockUpdateCount != 1) {
	// throw new AppException("此账户其他交易正在处理中，请稍后再试");
	// }
	// try {
	// // 5.检验卡号是否重复（未绑卡充值）
	// int bindRepeatCardCount =
	// userAccountMapper.queryBindRepeatCardCountBycardNo(rechargeDTO.getUserId(),
	// rechargeDTO.getCardNo());
	// if (bindRepeatCardCount > 0) {
	// throw new AppException("此卡号已经绑定，请勿重复绑卡");
	// }
	// //6.校验是否已经成功交易过.如果有,则不能再次发送交易;校验支付时所传的参数和获取验证码时发送的同字段参数是否相同,如果不相同,则不能进行支付交易、(支付接口中处理)
	// //7.扣钱调用支付接口（保存支付历史（流水））
	// UnionMobilePayDTO unionMobilePayDto =
	// buildUnionMobilePayDto(rechargeDTO);
	// PayResultDTO payResultDTO =
	// unionMobileB2CService.debitCardPayConfirm(unionMobilePayDto);
	// //8.支付后,不管成功失败,更新wjEbatongTradeHistory表中相应字段(支付接口中处理)
	// //9.充值成功后，业务逻辑
	// if (!payResultDTO.getResult()) {
	// //sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("doRechargeToMa 充值发逻辑异常，userId[" + rechargeDTO.getUserId() +
	// "] 错误信息[" + b2cGetawayDTO.getErrorMessage()));
	// throw new AppException("银行系统繁忙，请重新尝试充值");
	// }
	//
	// if
	// (payResultDTO.getResult()&&"0000".equals(payResultDTO.getResultCode())) {
	// // 充值成功后逻辑
	// userAccountTransactionService.doRechargeToMa(rechargeDTO);
	// return Boolean.TRUE;
	// }else{
	// BaseLogger.error("确认付款出错："+payResultDTO.getResultMessage());
	// throw new AppException(payResultDTO.getResultMessage());
	// }
	// }catch(Exception e){
	// if(e instanceof AppException){
	// if(StringUtils.isBlank(((AppException)e).getMessage()))
	// throw new AppException("银行系统繁忙，请重新尝试充值");
	// }else {
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("doRechargeToMa 充值发逻辑异常，userId[" + rechargeDTO.getUserId() +
	// "] exception :" + e.getMessage()));
	// }
	// throw new AppException(e.getMessage());
	// }finally {
	// // 2. 解锁
	// int unlockUpdateCount =
	// userAccountMapper.updateUserWithdrawUnlock(rechargeDTO.getUserId());
	// if (unlockUpdateCount != 1) {
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("doRechargeToMa 异常，userId[" + rechargeDTO.getUserId() +
	// "]"));
	// }
	// }
	// }
	//
	// /**
	// * 联动优速-绑定卡下单并发验证码
	// * @param userId
	// * @param cardId
	// * @param amount
	// * @return
	// */
	// public MBRechargeDTO rechargeToMaAlreadyBindCardSendCode(String userId,
	// String cardId, String amount) {
	// // 1. 校验参数合法性
	// this.checkRechargeParamValidate(userId, cardId, amount);
	//
	// MBRechargeDTO rechargeDTO =
	// userAccountMapper.queryBindCardInfoAndUserInfo(userId, cardId);
	// if(rechargeDTO == null){
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡充值发逻辑异常，userId[" +
	// userId + "]"));
	// throw new AppException("数据异常，请联系客服");
	// }
	// if(rechargeDTO.getCertType()==null||!"01".equals(rechargeDTO.getCertType())){//证件类型不是身份证，联动支付只支持身份证
	// throw new AppException("证件类型不是身份证，请更换！");
	// }
	// rechargeDTO.setAmount(amount);
	// if(!rechargeDTO.validateSendCodeParameter()){
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡充值发逻辑异常，userId[" +
	// rechargeDTO.getUserId() + "]"));
	// throw new AppException(rechargeDTO.getMessage());
	// }
	// try {
	// UnionMobilePayDTO unionMobilePayDto = copyProperties(rechargeDTO);
	// unionMobilePayDto.setCustomerId(cardId);
	//
	// RiskExpandInfoDTO riskExpandInfoDTO = new RiskExpandInfoDTO();
	// riskExpandInfoDTO.setUserId(rechargeDTO.getUserId());
	//
	// PayResultDTO payResultDTO =
	// unionMobileB2CService.placeOrderAndValidate(unionMobilePayDto,
	// riskExpandInfoDTO);
	// if (!payResultDTO.getResult()) {
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("rechargeToMaAlreadyBindCardSendCode 充值发短信异常，userId[" +
	// rechargeDTO.getUserId() + "]"));
	// throw new AppException("账户信息不正确，请联系客服");
	// }
	// if(payResultDTO.getResult()&&"0000".equals(payResultDTO.getResultCode())){
	// MBRechargeDTO MBRechargeDTO = new MBRechargeDTO();
	// MBRechargeDTO.setCardId(cardId);
	// MBRechargeDTO.setOrderNo(payResultDTO.getOrderNo());//---------是我方no还是平台返回的no
	// MBRechargeDTO.setCertType(null);
	// //新增短信有效时间 update by Eric $.2015-09-29 14:41:12
	// MBRechargeDTO.setRemainTime("120");
	// return MBRechargeDTO;
	// }else{
	// BaseLogger.error("下发短信验证码出错："+payResultDTO.getResultMessage());
	// throw new AppException(payResultDTO.getResultMessage());
	// }
	// } catch (Exception e) {
	// if(e instanceof AppException){
	// String msg=((AppException)e).getMessage();
	// if(StringUtils.isBlank(msg)){
	// throw new AppException("请核对银行卡与身份证信息是否匹配");
	// }
	//
	// throw (AppException)e;
	// }else {
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("rechargeToMaAlreadyBindCardSendCode 充值发短信异常，userId[" +
	// rechargeDTO.getUserId() + "]" + e.getMessage()));
	// }
	// throw new AppException("银行系统繁忙，请稍后再试");
	// }
	// }
	//
	// /**
	// * 联动优速-绑定卡确认付款
	// * @param userId
	// * @param cardId
	// * @param orderNo
	// * @param amount
	// * @param code
	// * @return
	// */
	// public Boolean doRechargeToMaAlreadyBind(String userId, String cardId,
	// String orderNo, String amount, String code) {
	// // 校验参数合法性
	// MBRechargeDTO rechargeDTO =
	// userAccountMapper.queryBindCardInfoAndUserInfo(userId, cardId);
	// if(rechargeDTO == null){
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡充值发逻辑异常，userId[" +
	// userId + "]"));
	// throw new AppException("数据异常，请联系客服");
	// }
	// rechargeDTO.setOrderNo(orderNo);
	// rechargeDTO.setAmount(amount);
	// rechargeDTO.setDynamicCode(code);
	// if (!rechargeDTO.validateDoRechargeToMaParameter()) {
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡充值发逻辑异常，userId[" +
	// rechargeDTO.getUserId() + "]"));
	// throw new AppException(rechargeDTO.getMessage());
	// }
	// int lockUpdateCount =
	// userAccountMapper.updateUserWithdrawLock(rechargeDTO.getUserId());
	// // 不等于说明已经上锁 没更新到记录 或者有其他异常情况
	// if (lockUpdateCount != 1) {
	// throw new AppException("此账户其他交易正在处理中，请稍后再试");
	// }
	// try {
	// //扣钱调用支付接口（保存支付历史（流水））
	// UnionMobilePayDTO unionMobilePayDto =
	// buildUnionMobilePayDto(rechargeDTO);
	// PayResultDTO payResultDTO =
	// unionMobileB2CService.debitCardPayConfirm(unionMobilePayDto);
	// if (!payResultDTO.getResult()) {
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡充值发逻辑异常，userId[" +
	// rechargeDTO.getUserId() + "] 错误信息[" + payResultDTO.getResultMessage()));
	// throw new AppException("银行系统繁忙，请重新尝试充值");
	// }
	//
	// if
	// (payResultDTO.getResult()&&"0000".equals(payResultDTO.getResultCode())) {
	// // 充值成功后逻辑
	// userAccountTransactionService.doRechargeToMaAlreadyBind(rechargeDTO);
	// return Boolean.TRUE;
	// }else{
	// BaseLogger.error("确认付款出错："+payResultDTO.getResultMessage());
	// throw new AppException(payResultDTO.getResultMessage());
	// }
	// } catch (Exception e) {
	// if(e instanceof AppException){
	// if(StringUtils.isBlank(((AppException)e).getMessage()))
	// throw new AppException("银行系统繁忙，请重新尝试充值");
	// }else {
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("doRechargeToMaAlreadyBind 充值发逻辑异常，userId[" +
	// rechargeDTO.getUserId() + "] exception:" + e.getMessage()));
	// }
	// throw new AppException(e.getMessage());
	// } finally {
	// // 2. 解锁
	// int unlockUpdateCount =
	// userAccountMapper.updateUserWithdrawUnlock(rechargeDTO.getUserId());
	// if (unlockUpdateCount != 1) {
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("doRechargeToMaAlreadyBind 异常，userId[" +
	// rechargeDTO.getUserId() + "]"));
	// }
	// }
	// }

	// 联动支付，未绑卡确认支付
	private UnionMobilePayDTO buildUnionMobilePayDto(MBRechargeDTO rechargeDTO) {
		UnionMobilePayDTO unionMobilePayDto = new UnionMobilePayDTO();
		unionMobilePayDto.setMediaId(rechargeDTO.getCardBindMobilePhoneNo());
		unionMobilePayDto.setCardHolder(rechargeDTO.getRealName());
		unionMobilePayDto.setIdentityCode(rechargeDTO.getCertNo());
		unionMobilePayDto.setCardId(rechargeDTO.getCardNo());
		unionMobilePayDto.setVerifyCode(rechargeDTO.getDynamicCode());
		unionMobilePayDto.setOrderId(rechargeDTO.getOrderNo());
		unionMobilePayDto.setAmount(rechargeDTO.getAmount());

		return unionMobilePayDto;
	}

	// 联动支付，未绑卡下发验证码
	private UnionMobilePayDTO copyProperties(MBRechargeDTO rechargeDTO) {
		UnionMobilePayDTO unionMobilePayDto = new UnionMobilePayDTO();
		unionMobilePayDto.setMerDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		unionMobilePayDto.setAmount(rechargeDTO.getAmount());
		unionMobilePayDto.setMediaId(rechargeDTO.getCardBindMobilePhoneNo());
		unionMobilePayDto.setCardHolder(rechargeDTO.getRealName());
		unionMobilePayDto.setCardId(rechargeDTO.getCardNo());
		unionMobilePayDto.setIdentityCode(rechargeDTO.getCertNo());
		// unionMobilePayDto.setBankCode(rechargeDTO.getEbtongBankCode());
		unionMobilePayDto.setBankCode(rechargeDTO.getBankCode());
		unionMobilePayDto.setUserId(rechargeDTO.getUserId());
		return unionMobilePayDto;
	}

	// //联动支付，通知回调
	// public String payNotice(Map<String, Object> map) {
	// // 如验证平台签名正确，即应响应UMPAY平台返回码为0000。【响应返回码代表通知是否成功，和通知的交易结果（支付失败、支付成功）无关】
	// // 验签支付结果通知 如验签成功，则返回ret_code=0000
	// PayNoticeDTO payNoticeDTO;
	// try {
	// payNoticeDTO = unionMobileB2CService.validateSign(map);
	// } catch (Exception e1) {
	// BaseLogger.error("payNotice 充值通知回调发生异常 exception:" + e1.getMessage());
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("payNotice 充值通知回调发生异常 exception:" + e1.getMessage()));
	// throw new AppException(e1.getMessage());
	// }
	// //业务处理
	// //1.查询
	// if(PayNoticeDTO.SUCCESS.equals(payNoticeDTO.getComparisonResult())){//成功不做处理
	//
	//
	//
	//
	//
	//
	// }else
	// if(PayNoticeDTO.ERROR_OUR.equals(payNoticeDTO.getComparisonResult())){
	// try {
	// //我方失败,第三方成功
	// //防并发
	// // int lockUpdateCount =
	// userAccountMapper.updateUserWithdrawLock(payNoticeDTO.getUserId());
	// // // 不等于说明已经上锁 没更新到记录 或者有其他异常情况
	// // if (lockUpdateCount != 1) {
	// // throw new AppException("此账户其他交易正在处理中，请稍后再试");
	// // }
	// // //不区分是否绑卡,增加订单处理
	// // MBRechargeDTO rechargeDTO = new MBRechargeDTO();
	// // rechargeDTO.setUserId(payNoticeDTO.getUserId());
	// // rechargeDTO.setCardId(payNoticeDTO.getCardId());
	// // rechargeDTO.setAmount(payNoticeDTO.getAmount());
	// // rechargeDTO.setOrderNo(payNoticeDTO.getOrderId());
	// //
	// // //新增订单
	// // userAccountTransactionService.doRechargeToMaAlreadyBind(rechargeDTO);
	//
	// BaseLogger.error("充值通知异常,我方失败,第三方成功，orderId[" + payNoticeDTO.getOrderId()
	// + "]");
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("充值通知异常,我方失败,第三方成功，orderId[" + payNoticeDTO.getOrderId() +
	// "]"));
	//
	//
	// } catch (Exception e) {
	// // BaseLogger.error("payNotice 联动优势通知回调,充值发逻辑异常，userId[" +
	// payNoticeDTO.getUserId() + "] exception:" + e.getMessage());
	// // sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("payNotice 联动优势通知回调,充值发逻辑异常，userId[" +
	// payNoticeDTO.getUserId() + "] exception:" + e.getMessage()));
	// } finally {
	// // 2. 解锁
	// // int unlockUpdateCount =
	// userAccountMapper.updateUserWithdrawUnlock(payNoticeDTO.getUserId());
	// // if (unlockUpdateCount != 1) {
	// // sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("payNotice 异常，userId[" + payNoticeDTO.getUserId() + "]"));
	// // }
	// }
	// }else
	// if(PayNoticeDTO.ERROR_THIRDPARTY.equals(payNoticeDTO.getComparisonResult())){
	// //我方成功,第三方失败,抛异常,发邮件
	// BaseLogger.error("充值通知异常,联动优势失败,我方支付成功，orderId[" +
	// payNoticeDTO.getOrderId() + "]");
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("充值通知异常,联动优势失败,我方支付成功，orderId[" + payNoticeDTO.getOrderId()
	// + "]"));
	// }else
	// if(PayNoticeDTO.ERROR_OUR_NOT_AMOUNT.equals(payNoticeDTO.getComparisonResult())){
	// //我方失败,联动成功,且金额不一致!,抛异常,发邮件
	// BaseLogger.error("充值通知异常,我方失败,联动成功,且金额不一致，orderId[" +
	// payNoticeDTO.getOrderId() + "]");
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("充值通知异常,我方失败,联动成功,且金额不一致，orderId[" +
	// payNoticeDTO.getOrderId() + "]"));
	// }else{
	// BaseLogger.error("充值通知异常,未知错误，orderId[" + payNoticeDTO.getOrderId() +
	// "]");
	// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
	// pageEmailMap("充值通知异常,未知错误，orderId[" + payNoticeDTO.getOrderId() + "]"));
	// }
	//
	// //响应结果返回
	// return setResponseParam(payNoticeDTO);
	// }

	// 联动通知回调,封装参数
	private String setResponseParam(PayNoticeDTO payNoticeDTO) {
		Map<String, String> resData = new HashMap<String, String>();
		resData.put("mer_id", payNoticeDTO.getMerId());
		resData.put("sign_type", payNoticeDTO.getSignType());
		resData.put("version", payNoticeDTO.getVersion());
		resData.put("order_id", payNoticeDTO.getOrderId());
		resData.put("mer_date", payNoticeDTO.getMerDate());
		if(payNoticeDTO.isLocalResult()){
			resData.put("ret_msg", "success");
		}else{
			resData.put("ret_msg", "fail");
		}
		
		resData.put("ret_code", payNoticeDTO.getRetCode());

		return unionMobileB2CService.addSign(resData);
	}

	/**
	 * @param userId
	 * @param applyMoney
	 * @param productId
	 * @return
	 * @throws Exception
     * @title 用户申购定期理财产品，从主账户到rf账户
	 */
	public MBRfReturnDTO applyMaToRf(String userId, String applyMoney, String productId, String token) {
		BaseLogger.audit("start applyMaToRf userId[" + userId + "] withdrawMoney[" + applyMoney + "] productId["+productId+"]");
		// 校验参数
		BigDecimal amount = confirmUserIsExitAndMoneyIsValid(userId, applyMoney).setScale(0, BigDecimal.ROUND_FLOOR);

		// 1. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}

		MBRfReturnDTO dto = new MBRfReturnDTO("200100", "投资成功", null, "");

		try {
			// 主业务逻辑
			// 查询用户产品相关数据
			MBUserInfoDTO userinfo = userAccountRfMapper.queryUserInfoByUserid(userId, productId);
			
			
			

			if (userinfo == null) {
				throw new AppException("产品不存在 请重新选择");
			}
			if (userinfo.getProductFlag().equals("greenhorn") && userinfo.getIfPurchaseProduct().equals("yes")) {
				throw new AppException("该产品仅限新手用户购买");
			}
			int tokencount = userAccountRfMapper.queryProductOrderByToken(token);
			if (tokencount > 0) {
				throw new AppException("请勿重复下单");
			}
			// 校验购买金额是否符合产品startmoney，increasemoney标准
			if (userinfo.getProductStartmoney() != null && userinfo.getProductIncreaseMoney() != null) {
				confirmRfApplyMoneyIsValid(amount, userinfo.getProductStartmoney(), userinfo.getProductIncreaseMoney());
			}
			// 校验
			if (userinfo.getIndentityType() == null || !userinfo.getIndentityType().equals("01")) {
				throw new AppException(200104, "定期理财产品须使用身份证购买");
			}
			if (userinfo.getProductSoldOut() == null || !getNextDay().before(new SimpleDateFormat("yyyy-MM-dd").parse(userinfo.getProductEndTime()))) {
				throw new AppException("产品已过期 请重新选择");
			}
			if (userinfo.getMaAvailableAmount() == null || userinfo.getMaAvailableAmount().compareTo(amount) < 0) {
				throw new AppException(200103, "账户可用余额不足 请充值");
			}
			if (userinfo.getProductSoldOut() == null || userinfo.getProductSoldOut().equals("yes")) {
				throw new AppException(200101, "产品份额不足，请选择其他产品");
			}
			if (userinfo.getProductRemainBalance() == null || userinfo.getProductRemainBalance().compareTo(amount) < 0) {
				throw new AppException(200102, "产品份额不足，请选择其他产品");
			}
			if (userinfo.getProductRaiseProgress().compareTo(BigDecimal.ONE) >= 0) {
				throw new AppException(200101, "产品份额不足，请选择其他产品");
			}
			if (userinfo.getPurchasedAmount() == null || userinfo.getProductCumulativePurchaseAmount() == null || userinfo.getProductCumulativePurchaseAmount().compareTo(userinfo.getPurchasedAmount()) < 0) {
				throw new AppException(200106, "申购金额已超过该产品累计金额上限");
			}
			if (userinfo.getProductSinglePurchaseAmount() == null || userinfo.getProductSinglePurchaseAmount().compareTo(amount) < 0) {
				throw new AppException(200105, "申购金额超过该产品单笔金额上限");
			}
			if (userinfo.getPurchasedAmount() == null || userinfo.getProductCumulativePurchaseAmount() == null || userinfo.getProductCumulativePurchaseAmount().compareTo(userinfo.getPurchasedAmount().add(amount)) < 0) {
				throw new AppException(200106, "申购金额已超过该产品累计金额上限");
			}

			String tradeId = userAccountTransactionService.getId("01", ISequenceService.SEQ_NAME_TRADE_SEQ);// 生成小赢唯一交易流水号
			// 冻结ma资金， 扣除product可投资余额，生成转账到rf的订单，设定unconfirmed状态
			String orderNo = userAccountTransactionService.applyMaToRf(userId, productId, amount, userinfo.getLockVersion(), token, tradeId);
			dto.setOrderNo(orderNo);
			BaseLogger.audit("推荐人理财模式 orderNo:"+orderNo);
			// 调用小赢接口，返回还款计划、保险凭证等数据
			RfResponseDTO resdto = null;

			if (userAccountRfMapper.isRepayPlanMockMode()) {
				BaseLogger.audit("调用小赢接口，返回还款计划 MBWjProductProcess.processOrder");
				resdto = mBWjProductProcess.processOrder(amount, userinfo, orderNo, productId, tradeId);
			} else {
				BaseLogger.audit("调用小赢接口，返回还款计划 MBRemoteProductProcess.processOrder");
				resdto = mBProductProcess.processOrder(amount, userinfo, orderNo, productId, tradeId);
			}

			// 处理小赢理财订单
			userAccountTransactionService.processRfOrder(userinfo, resdto, token);

			// 异步调用生成保单号
			if (userAccountRfMapper.isAsyncUpdatePolicyNo()) { // 添加是否异步更新保单开关
				BaseLogger.audit("申购小赢理财-异步更新保单打开");
				String strJson = getRfInvestParams(amount, tradeId, userinfo);
				this.apiUpdatePolicyNoByInvestAsync(strJson, orderNo);
			}

			// 返回内容
			dto.setRemainingInvestment(userinfo.getProductRemainBalance().subtract(amount).toString());
			StringBuilder result = new StringBuilder();
			result.append("每月").append(resdto.getRepayplans().get(0).getPlanRepayDateTime().getDate()).append("日还款，");
			result.append(userinfo.getProductEndTime()).append("到期");
			dto.setPaymentInformation(result.toString());

			BaseLogger.audit("申购小赢理财产品成功  userId[" + userId + "] applyMoney[" + applyMoney + "] productId[" + productId + "]");

			updateLnAvailableAmount(userId);
		} catch (Exception e) {
			if (e instanceof AppException) {
				throw (AppException) e;
			} else {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("applyMaToRf 异常，userId[" + userId + "] exception:" + e.getMessage()));
			}
			throw new AppException("applyMaToRf 异常，userId[" + userId + "] exception:" + e.getMessage());
		} finally {
			// 2. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
			BaseLogger.audit("unlock applyMaToRf userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]");
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("applyMaToRf 异常，userId[" + userId + "]"));
			}
		}

		// 输出日志
		BaseLogger.audit("end applyMaToRf userId[" + userId + "] applyMoney[" + applyMoney + "]");
		// 计算下确认份额日期，收益到账日期
		return dto;
	}
	
	
	
	/**
	 *   ips下单无红包此处调用 ips 冻结接口
	 */  
	public Object ipsApplyMaToRf(String userId, String applyMoney, String productId, String token,String couponId,String source) {
		BaseLogger.audit("start applyMaToRf userId[" + userId + "] withdrawMoney[" + applyMoney + "] productId["+productId+"]");
		// 校验参数
		BigDecimal amount = confirmUserIsExitAndMoneyIsValid(userId, applyMoney).setScale(0, BigDecimal.ROUND_FLOOR);
		Map<String, String> returnMap = new HashMap<String, String>();
		// 1. 判断是否已经上锁 
		 int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}
		
		//MBRfReturnDTO dto = new MBRfReturnDTO("200100", "投资成功", null, "");
		
		 
		 IPSResponse response=null;
		try {
			// 主业务逻辑
			// 查询用户产品相关数据
			MBUserInfoDTO userinfo = userAccountRfMapper.queryUserInfoByUseridIps(userId, productId);
			
			
			
			
			if (userinfo == null) {
				throw new AppException("产品不存在 请重新选择");
			}
			
			if(userinfo.getIsIpsUser()==null||"no".equals(userinfo.getIsIpsUser())){
				throw new AppException("该用户未开户");
			}
			
			if(userinfo.getIpsAcctNo()==null){
				throw new AppException("该用户未开户");
			}
			
			if (userinfo.getProductFlag().equals("greenhorn") && userinfo.getIfPurchaseProduct().equals("yes")) {
				throw new AppException("该产品仅限新手用户购买");
			}
			int tokencount = userAccountRfMapper.queryProductOrderByToken(token);
			if (tokencount > 0) {
				throw new AppException("请勿重复下单");
			}
			// 校验购买金额是否符合产品startmoney，increasemoney标准
			if (userinfo.getProductStartmoney() != null && userinfo.getProductIncreaseMoney() != null) {
				confirmRfApplyMoneyIsValid(amount, userinfo.getProductStartmoney(), userinfo.getProductIncreaseMoney());
			}
			// 校验
			if (userinfo.getIndentityType() == null || !userinfo.getIndentityType().equals("01")) {
				throw new AppException(200104, "定期理财产品须使用身份证购买");
			}
			if (userinfo.getProductSoldOut() == null || !getNextDay().before(new SimpleDateFormat("yyyy-MM-dd").parse(userinfo.getProductEndTime()))) {
				throw new AppException("产品已过期 请重新选择");
			}
			if (userinfo.getMaAvailableAmount() == null || userinfo.getMaAvailableAmount().compareTo(amount) < 0) {
				throw new AppException(200103, "账户可用余额不足 请充值");
			}
			if (userinfo.getProductSoldOut() == null || userinfo.getProductSoldOut().equals("yes")) {
				throw new AppException(200101, "产品份额不足，请选择其他产品");
			}
			if (userinfo.getProductRemainBalance() == null || userinfo.getProductRemainBalance().compareTo(amount) < 0) {
				throw new AppException(200102, "产品份额不足，请选择其他产品");
			}
			if (userinfo.getProductRaiseProgress().compareTo(BigDecimal.ONE) >= 0) {
				throw new AppException(200101, "产品份额不足，请选择其他产品");
			}
			if (userinfo.getPurchasedAmount() == null || userinfo.getProductCumulativePurchaseAmount() == null || userinfo.getProductCumulativePurchaseAmount().compareTo(userinfo.getPurchasedAmount()) < 0) {
				throw new AppException(200106, "申购金额已超过该产品累计金额上限");
			}
			if (userinfo.getProductSinglePurchaseAmount() == null || userinfo.getProductSinglePurchaseAmount().compareTo(amount) < 0) {
				throw new AppException(200105, "申购金额超过该产品单笔金额上限");
			}
			if (userinfo.getPurchasedAmount() == null || userinfo.getProductCumulativePurchaseAmount() == null || userinfo.getProductCumulativePurchaseAmount().compareTo(userinfo.getPurchasedAmount().add(amount)) < 0) {
				throw new AppException(200106, "申购金额已超过该产品累计金额上限");
			}
			
			
			//此处调用环迅ips冻结接口
			IpsFreeze ipf = new IpsFreeze();
			String merBillNo = GenerateMerBillNoUtil.getBillNoGenerate();
			//String source="APP";
			
			
			ipf.setMerBillNo(merBillNo);
			ipf.setMerDate( new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			ipf.setProjectNo(productId);
			ipf.setBizType("1");
			ipf.setRegType("1");
			ipf.setTrdAmt(applyMoney);
			//平台手续费
			ipf.setMerFee("0");
			ipf.setFreezeMerType("1");
			ipf.setIpsAcctNo(userinfo.getIpsAcctNo());
			ipf.setWebUrl(merBillNo);
			ipf.setS2SUrl(localServerIp+"/api/ips/freeze/s2sCallBack");
			ipf.setWebUrl(localServerIp+"/api/ips/freeze/webCallBack?source=?"+source);
			
			
			
			returnMap=IpsRequestUtils.getRequestMap(Define.OperationType.FREEZE, JSONUtils.obj2json(ipf));
			
			response = excuteIpsFreezeService.freezeAccount(merBillNo,ipf,source);
			
			returnMap.put("url", HttpClientUtils.ips_url);
			/*returnMap.put("msg", response.getMsg());
			returnMap.put("data", response.getData());*/
			
			String tradeId = userAccountTransactionService.getId("01", ISequenceService.SEQ_NAME_TRADE_SEQ);// 生成小赢唯一交易流水号
			//生成订单,订单状态为 apply_ma_to_rf_noconfirm 注意此处添加加息券id不用判断id 是否为null  用于转账回调判断 加息
			userAccountMapper.addtOrderInfoIps(MBOrderInfoDTO.getApplyMaToRfOrderIps(userId, merBillNo, amount, productId, token, tradeId, couponId));
			
			
			
			
			// 冻结ma资金， 扣除product可投资余额，生成转账到rf的订单，设定unconfirmed状态
			//dto.setOrderNo(orderNo);
			//String orderNo = userAccountTransactionService.applyMaToRfIps(userId, productId, amount, userinfo.getLockVersion(), token, tradeId);
			// userAccountTransactionService.ipsApplyMaToRf(userId, productId, amount, userinfo.getLockVersion(), token, tradeId);
			//BaseLogger.audit("推荐人理财模式 orderNo:"+orderNo);
			// 调用小赢接口，返回还款计划、保险凭证等数据   到ips 转账回调成功之后执行 
			/*RfResponseDTO resdto = null;
			
			if (userAccountRfMapper.isRepayPlanMockMode()) {
				BaseLogger.audit("调用小赢接口，返回还款计划 MBWjProductProcess.processOrder");
				resdto = mBWjProductProcess.processOrder(amount, userinfo, orderNo, productId, tradeId);
			} else {
				BaseLogger.audit("调用小赢接口，返回还款计划 MBRemoteProductProcess.processOrder");
				resdto = mBProductProcess.processOrder(amount, userinfo, orderNo, productId, tradeId);
			}*/
			
			// 处理小赢理财订单  到ips 转账回调成功之后执行 
			//userAccountTransactionService.processRfOrder(userinfo, resdto, token);
			
			// 异步调用生成保单号
			/*if (userAccountRfMapper.isAsyncUpdatePolicyNo()) { // 添加是否异步更新保单开关
				BaseLogger.audit("申购小赢理财-异步更新保单打开");
				String strJson = getRfInvestParams(amount, tradeId, userinfo);
				this.apiUpdatePolicyNoByInvestAsync(strJson, orderNo);
			}*/
			
			// 返回内容
			/*dto.setRemainingInvestment(userinfo.getProductRemainBalance().subtract(amount).toString());
			StringBuilder result = new StringBuilder();
			result.append("每月").append(resdto.getRepayplans().get(0).getPlanRepayDateTime().getDate()).append("日还款，");
			result.append(userinfo.getProductEndTime()).append("到期");
			dto.setPaymentInformation(result.toString());*/
			
			//BaseLogger.audit("申购小赢理财产品成功  userId[" + userId + "] applyMoney[" + applyMoney + "] productId[" + productId + "]");
			
			//updateLnAvailableAmount(userId);
		} catch (Exception e) {
			if (e instanceof AppException) {
				throw (AppException) e;
			} else {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("applyMaToRf 异常，userId[" + userId + "] exception:" + e.getMessage()));
			}
			throw new AppException("applyMaToRf 异常，userId[" + userId + "] exception:" + e.getMessage());
		} finally {
			// 2. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
			BaseLogger.audit("unlock applyMaToRf userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]");
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("applyMaToRf 异常，userId[" + userId + "]"));
			}
		}
		
		// 输出日志
		BaseLogger.audit("end applyMaToRf userId[" + userId + "] applyMoney[" + applyMoney + "]");
		// 计算下确认份额日期，收益到账日期
		return returnMap;
	}

	
	@Override
	public MBRfReturnDTO applyMaToRf(String userId, String applyMoney,String productId, String rpId, String couponId, String clientType,String token) {

		BaseLogger.audit("start applyMaToRf userId[" + userId + "] withdrawMoney[" + applyMoney + "] rpId["+rpId+"]");
		// 校验参数
		BigDecimal amount = confirmUserIsExitAndMoneyIsValid(userId, applyMoney).setScale(0, BigDecimal.ROUND_FLOOR);

		// 调用红包服务，判断红包是否可用
		// 1、判断红包是否可用接口
		// 1.1、判断红包是否有效
		// 1.2、判断红包是否归属这个人
		// 1.3、判断红包是否可以投此理财产品
		// 入参：userId、productId、rpId
		// 出参：红包详细信息或者异常
		//  取出红包,做校验使用
//		Map<String, Object> rpInfo = userCouponService.queryCashCouponCanUse(userId, productId, rpId, clientType, applyMoney);
		if(StringUtils.isNotBlank(rpId))
			this.getPackage(userId, applyMoney, rpId, productId);
		//  取出加息券,做校验使用
		if(StringUtils.isNotBlank(couponId))
			this.getCoupons(userId, couponId, productId);
		// 1. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}

		MBRfReturnDTO dto = new MBRfReturnDTO("200100", "投资成功", null, "");

		try {
			// 加锁后，再查一次红包的状态，避免并发问题
			// TODO ..在次调用
//			rpInfo = userCouponService.queryCashCouponCanUse(userId, productId, rpId, clientType, applyMoney);
			
			// 主业务逻辑
			// 查询用户产品相关数据
			MBUserInfoDTO userinfo = userAccountRfMapper.queryUserInfoByUserid(userId, productId);

			if (userinfo == null) {
				throw new AppException("产品不存在 请重新选择");
			}
			if (userinfo.getProductFlag().equals("greenhorn") && userinfo.getIfPurchaseProduct().equals("yes")) {
				throw new AppException("该产品仅限新手用户购买");
			}
			int tokencount = userAccountRfMapper.queryProductOrderByToken(token);
			if (tokencount > 0) {
				throw new AppException("请勿重复下单");
			}
			// 校验购买金额是否符合产品startmoney，increasemoney标准
			if (userinfo.getProductStartmoney() != null && userinfo.getProductIncreaseMoney() != null) {
				confirmRfApplyMoneyIsValid(amount, userinfo.getProductStartmoney(), userinfo.getProductIncreaseMoney());
			}
			// 校验
			if (userinfo.getIndentityType() == null || !userinfo.getIndentityType().equals("01")) {
				throw new AppException(200104, "定期理财产品须使用身份证购买");
			}
			if (userinfo.getProductSoldOut() == null || !getNextDay().before(new SimpleDateFormat("yyyy-MM-dd").parse(userinfo.getProductEndTime()))) {
				throw new AppException("产品已过期 请重新选择");
			}
			if (userinfo.getMaAvailableAmount() == null || userinfo.getMaAvailableAmount().compareTo(amount) < 0) {
				throw new AppException(200103, "账户可用余额不足 请充值");
			}
			if (userinfo.getProductSoldOut() == null || userinfo.getProductSoldOut().equals("yes")) {
				throw new AppException(200101, "产品份额不足，请选择其他产品");
			}
			if (userinfo.getProductRemainBalance() == null || userinfo.getProductRemainBalance().compareTo(amount) < 0) {
				throw new AppException(200102, "产品份额不足，请选择其他产品");
			}
			if (userinfo.getProductRaiseProgress().compareTo(BigDecimal.ONE) >= 0) {
				throw new AppException(200101, "产品份额不足，请选择其他产品");
			}
			if (userinfo.getPurchasedAmount() == null || userinfo.getProductCumulativePurchaseAmount() == null || userinfo.getProductCumulativePurchaseAmount().compareTo(userinfo.getPurchasedAmount()) < 0) {
				throw new AppException(200106, "申购金额已超过该产品累计金额上限");
			}
			if (userinfo.getProductSinglePurchaseAmount() == null || userinfo.getProductSinglePurchaseAmount().compareTo(amount) < 0) {
				throw new AppException(200105, "申购金额超过该产品单笔金额上限");
			}
			if (userinfo.getPurchasedAmount() == null || userinfo.getProductCumulativePurchaseAmount() == null || userinfo.getProductCumulativePurchaseAmount().compareTo(userinfo.getPurchasedAmount().add(amount)) < 0) {
				throw new AppException(200106, "申购金额已超过该产品累计金额上限");
			}

			String tradeId = userAccountTransactionService.getId("01", ISequenceService.SEQ_NAME_TRADE_SEQ);// 生成小赢唯一交易流水号
			// 冻结ma资金， 扣除product可投资余额，生成转账到rf的订单，设定unconfirmed状态
			
			String orderNo="";
			if(StringUtils.isNotBlank(rpId)){
				BigDecimal rpValue = this.getPackage(userId, applyMoney, rpId, productId);
				// 红包部分，冻结金额的去掉，具体在applyMaToRf里面处理
				 orderNo = userAccountTransactionService.applyMaToRf(userId, productId, amount, rpValue, userinfo.getLockVersion(), token, tradeId);
				 try {
						// TODO .扣除红包,更新红包状态为已用
						couponMapper.updatePackageIsUse(rpId,orderNo);
					} catch (Exception e) {
						BaseLogger.error("update coupon status failure, userId: " + userId + ", rpId: " + rpId + ", detail is : " + e);
					}
			}else{
				// 非红包部分，冻结金额的去掉，具体在applyMaToRf里面处理
				 orderNo = userAccountTransactionService.applyMaToRf(userId, productId, amount, userinfo.getLockVersion(), token, tradeId);
				 BaseLogger.audit("非红包购买理财orderNo"+orderNo);
			}
			dto.setOrderNo(orderNo);
			

			// 调用小赢接口，返回还款计划、保险凭证等数据
			RfResponseDTO resdto = null;

			if (userAccountRfMapper.isRepayPlanMockMode()) {
				// 加入加息券处理 ,把利息加进去
				if(StringUtils.isNotBlank(couponId)){
					resdto = mBWjProductProcess.processOrder(amount, this.getCoupons(userId, couponId, productId),userinfo, orderNo, productId, tradeId);
					//扣除加息券
					couponMapper.updateCouponsIsUse(couponId,orderNo);
				}else{
					resdto = mBWjProductProcess.processOrder(amount, userinfo, orderNo, productId, tradeId);
					
				}
				
			} else {
				
				resdto = mBProductProcess.processOrder(amount, userinfo, orderNo, productId, tradeId);
			}

			// 处理小赢理财订单
			userAccountTransactionService.processRfOrderWithRp(userinfo, resdto, token);

			// 异步调用生成保单号
//			if (userAccountRfMapper.isAsyncUpdatePolicyNo()) { // 添加是否异步更新保单开关
//				BaseLogger.audit("申购小赢理财-异步更新保单打开");
//				String strJson = getRfInvestParams(amount, tradeId, userinfo);
////				this.apiUpdatePolicyNoByInvestAsync(strJson, orderNo);
//			}

			// 返回内容
			dto.setRemainingInvestment(userinfo.getProductRemainBalance().subtract(amount).toString());
			StringBuilder result = new StringBuilder();
			result.append("每月").append(resdto.getRepayplans().get(0).getPlanRepayDateTime().getDate()).append("日还款，");
			result.append(userinfo.getProductEndTime()).append("到期");
			dto.setPaymentInformation(result.toString());

			BaseLogger.audit("申购小赢理财产品成功  userId[" + userId + "] applyMoney[" + applyMoney + "] productId[" + productId + "]");

			updateLnAvailableAmount(userId);
		} catch (Exception e) {
			if (e instanceof AppException) {
				throw (AppException) e;
			} else {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("applyMaToRf 异常，userId[" + userId + "] exception:" + e.getMessage()));
			}
			throw new AppException("applyMaToRf 异常，userId[" + userId + "] exception:" + e.getMessage());
		} finally {
			// 2. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
			BaseLogger.audit("unlock applyMaToRf userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]");
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("applyMaToRf 异常，userId[" + userId + "]"));
			}
		}

		// 输出日志
		BaseLogger.audit("end applyMaToRf userId[" + userId + "] applyMoney[" + applyMoney + "]");
		// 计算下确认份额日期，收益到账日期
		return dto;
	}

	
	// 获取红包
	public BigDecimal getPackage(String userId,String investmentAmt,String packageId,String productId){
		Map<String,Object> packageInfo=couponMapper.checkRedPackageValid(userId, investmentAmt, packageId, productId);
		
		if(packageInfo==null){
			throw new AppException("该红包不存在");
		}
		String packageMark=(String)packageInfo.get("packageMark");
		if(packageMark.equals("no")){
			throw new AppException("该产品不支持红包");
		}
		Double validateAmount=(Double)packageInfo.get("validateAmount");
		if(validateAmount<0){
			throw new AppException("不符合红包起投金额");
		}
		Integer validateTime=Integer.valueOf(packageInfo.get("validateTime").toString());
		if(validateTime<0){
			throw new AppException("红包已过期");
		}
		String isUse=(String)packageInfo.get("isUse");
		if(isUse.equals("Y")){
			throw new AppException("红包已经使用");
		}
		Integer packageAmount=(Integer)packageInfo.get("packageAmount");
		
		return new BigDecimal(packageAmount);
	} 
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
	public MBRfReturnDTO applyMaToRf(String userId, String applyMoney, String productId, String rpId, String clientType, String token) {
		BaseLogger.audit("start applyMaToRf userId[" + userId + "] withdrawMoney[" + applyMoney + "] rpId["+rpId+"]");
		// 校验参数
		BigDecimal amount = confirmUserIsExitAndMoneyIsValid(userId, applyMoney).setScale(0, BigDecimal.ROUND_FLOOR);

		// 调用红包服务，判断红包是否可用
		// 1、判断红包是否可用接口
		// 1.1、判断红包是否有效
		// 1.2、判断红包是否归属这个人
		// 1.3、判断红包是否可以投此理财产品
		// 入参：userId、productId、rpId
		// 出参：红包详细信息或者异常
		// TODO 取出红包
		Map<String, Object> rpInfo = userCouponService.queryCashCouponCanUse(userId, productId, rpId, clientType, applyMoney);

		// TODO 取出加息券
		
		// 1. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}

		MBRfReturnDTO dto = new MBRfReturnDTO("200100", "投资成功", null, "");

		try {
			// 加锁后，再查一次红包的状态，避免并发问题
			// TODO ..在次调用
			rpInfo = userCouponService.queryCashCouponCanUse(userId, productId, rpId, clientType, applyMoney);
			// 主业务逻辑
			// 查询用户产品相关数据
			MBUserInfoDTO userinfo = userAccountRfMapper.queryUserInfoByUserid(userId, productId);

			if (userinfo == null) {
				throw new AppException("产品不存在 请重新选择");
			}
			if (userinfo.getProductFlag().equals("greenhorn") && userinfo.getIfPurchaseProduct().equals("yes")) {
				throw new AppException("该产品仅限新手用户购买");
			}
			int tokencount = userAccountRfMapper.queryProductOrderByToken(token);
			if (tokencount > 0) {
				throw new AppException("请勿重复下单");
			}
			// 校验购买金额是否符合产品startmoney，increasemoney标准
			if (userinfo.getProductStartmoney() != null && userinfo.getProductIncreaseMoney() != null) {
				confirmRfApplyMoneyIsValid(amount, userinfo.getProductStartmoney(), userinfo.getProductIncreaseMoney());
			}
			// 校验
			if (userinfo.getIndentityType() == null || !userinfo.getIndentityType().equals("01")) {
				throw new AppException(200104, "定期理财产品须使用身份证购买");
			}
			if (userinfo.getProductSoldOut() == null || !getNextDay().before(new SimpleDateFormat("yyyy-MM-dd").parse(userinfo.getProductEndTime()))) {
				throw new AppException("产品已过期 请重新选择");
			}
			if (userinfo.getMaAvailableAmount() == null || userinfo.getMaAvailableAmount().compareTo(amount) < 0) {
				throw new AppException(200103, "账户可用余额不足 请充值");
			}
			if (userinfo.getProductSoldOut() == null || userinfo.getProductSoldOut().equals("yes")) {
				throw new AppException(200101, "产品份额不足，请选择其他产品");
			}
			if (userinfo.getProductRemainBalance() == null || userinfo.getProductRemainBalance().compareTo(amount) < 0) {
				throw new AppException(200102, "产品份额不足，请选择其他产品");
			}
			if (userinfo.getProductRaiseProgress().compareTo(BigDecimal.ONE) >= 0) {
				throw new AppException(200101, "产品份额不足，请选择其他产品");
			}
			if (userinfo.getPurchasedAmount() == null || userinfo.getProductCumulativePurchaseAmount() == null || userinfo.getProductCumulativePurchaseAmount().compareTo(userinfo.getPurchasedAmount()) < 0) {
				throw new AppException(200106, "申购金额已超过该产品累计金额上限");
			}
			if (userinfo.getProductSinglePurchaseAmount() == null || userinfo.getProductSinglePurchaseAmount().compareTo(amount) < 0) {
				throw new AppException(200105, "申购金额超过该产品单笔金额上限");
			}
			if (userinfo.getPurchasedAmount() == null || userinfo.getProductCumulativePurchaseAmount() == null || userinfo.getProductCumulativePurchaseAmount().compareTo(userinfo.getPurchasedAmount().add(amount)) < 0) {
				throw new AppException(200106, "申购金额已超过该产品累计金额上限");
			}

			String tradeId = userAccountTransactionService.getId("01", ISequenceService.SEQ_NAME_TRADE_SEQ);// 生成小赢唯一交易流水号
			// 冻结ma资金， 扣除product可投资余额，生成转账到rf的订单，设定unconfirmed状态
			BigDecimal rpValue = new BigDecimal(String.valueOf(rpInfo.get("value")));
			// 红包部分，冻结金额的去掉，具体在applyMaToRf里面处理
			String orderNo = userAccountTransactionService.applyMaToRf(userId, productId, amount, rpValue, userinfo.getLockVersion(), token, tradeId);
			BaseLogger.audit("老接口购买理财orderNo:"+orderNo);
			dto.setOrderNo(orderNo);
			try {
				// TODO .扣除红包,更新红包状态为已用
				userCouponService.updateCashCouponUsed(rpId);
			} catch (Exception e) {
				BaseLogger.error("update coupon status failure, userId: " + userId + ", rpId: " + rpId + ", detail is : " + e);
			}

			// 调用小赢接口，返回还款计划、保险凭证等数据
			RfResponseDTO resdto = null;

			if (userAccountRfMapper.isRepayPlanMockMode()) {
				resdto = mBWjProductProcess.processOrder(amount, userinfo, orderNo, productId, tradeId);
			} else {
				
				// TODO 加入加息券处理 ,把利息加进去
				resdto = mBProductProcess.processOrder(amount, userinfo, orderNo, productId, tradeId);
				//TODO 扣除加息券
			}

			// 处理小赢理财订单
			userAccountTransactionService.processRfOrderWithRp(userinfo, resdto, token);

			// 异步调用生成保单号
//			if (userAccountRfMapper.isAsyncUpdatePolicyNo()) { // 添加是否异步更新保单开关
//				BaseLogger.audit("申购小赢理财-异步更新保单打开");
//				String strJson = getRfInvestParams(amount, tradeId, userinfo);
////				this.apiUpdatePolicyNoByInvestAsync(strJson, orderNo);
//			}

			// 返回内容
			dto.setRemainingInvestment(userinfo.getProductRemainBalance().subtract(amount).toString());
			StringBuilder result = new StringBuilder();
			result.append("每月").append(resdto.getRepayplans().get(0).getPlanRepayDateTime().getDate()).append("日还款，");
			result.append(userinfo.getProductEndTime()).append("到期");
			dto.setPaymentInformation(result.toString());

			BaseLogger.audit("申购小赢理财产品成功  userId[" + userId + "] applyMoney[" + applyMoney + "] productId[" + productId + "]");

			updateLnAvailableAmount(userId);
		} catch (Exception e) {
			if (e instanceof AppException) {
				throw (AppException) e;
			} else {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("applyMaToRf 异常，userId[" + userId + "] exception:" + e.getMessage()));
			}
			throw new AppException("applyMaToRf 异常，userId[" + userId + "] exception:" + e.getMessage());
		} finally {
			// 2. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
			BaseLogger.audit("unlock applyMaToRf userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]");
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("applyMaToRf 异常，userId[" + userId + "]"));
			}
		}

		// 输出日志
		BaseLogger.audit("end applyMaToRf userId[" + userId + "] applyMoney[" + applyMoney + "]");
		// 计算下确认份额日期，收益到账日期
		return dto;
	}

	private String getRfInvestParams(BigDecimal amount, String tradeId, MBUserInfoDTO userinfo) {
		// email 改为统一邮件地址
		String strJson = "{\"amount\":\"" + amount.multiply(new BigDecimal(100)) + "\",\"email\":\"" + email + "\",\"identifyNo\":\"" + userinfo.getIndentityNo() + "\",\"isSendEmail\":\"" + isSendEmail + "\",\"isSendSms\":\"" + isSendSms + "\",\"loanId\":\"" + userinfo.getProductLoanId() + "\",\"mobile\":\"" + userinfo.getPhone() + "\",\"name\":\"" + userinfo.getName() + "\",\"partnerId\":\"" + partner + "\",\"tradeId\":\"" + tradeId + "\"}";
		return strJson;
	}

	public void replayApplyMaToRf(String orderNo) {

		MBOrderInfoDTO orderdto = userAccountRfMapper.queryOrderByOrderNo(orderNo);

		if (orderdto == null) {
			throw new AppException("订单信息不存在");
		}
		if (!orderdto.getOrderType().equals(MBOrderInfoDTO.ORDER_TYPE_APPLY_MA_TO_RF)) {
			throw new AppException("只有定期理财订单才能重新申购");
		}
		if (!orderdto.getOrderStatus().equals(MBOrderInfoDTO.ORDER_STATUS_APPLY_MA_TO_RF_NOCONFIRM)) {
			throw new AppException("只有定期理财订单状态在申购中才能重新申购");
		}
		if (orderdto.getProductId() == null) {
			throw new AppException("定期理财订单中产品id不存在");
		}

		BaseLogger.audit("start replayApplyMaToRf userId[" + orderdto.getUserId() + "] withdrawMoney[" + orderdto.getTotalPrice().toString() + "]");

		// 1. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(orderdto.getUserId());
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}

		try {
			// 查询用户产品相关数据
			MBUserInfoDTO userinfo = userAccountRfMapper.queryUserInfoByUserid(orderdto.getUserId(), orderdto.getProductId());

			// 调用小赢接口，返回还款计划、保险凭证等数据
			RfResponseDTO resdto = mBProductProcess.processOrder(orderdto.getTotalPrice(), userinfo, orderNo, orderdto.getProductId(), orderdto.getTradeId());

			// 处理小赢理财订单
			userAccountTransactionService.processRfOrder(userinfo, resdto, orderdto.getOrderToken());

		} catch (Exception e) {
			if (e instanceof AppException) {
				throw (AppException) e;
			} else {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("replayApplyMaToRf 异常，userId[" + orderdto.getUserId() + "] exception:" + e.getMessage()));
			}
			throw new AppException("applyMaToRf 异常，userId[" + orderdto.getUserId() + "] exception:" + e.getMessage());
		} finally {
			// 2. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(orderdto.getUserId());
			BaseLogger.audit("unlock replayApplyMaToRf userId[" + orderdto.getUserId() + "] unlockUpdateCount[" + unlockUpdateCount + "]");
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("replayApplyMaToRf 异常，userId[" + orderdto.getUserId() + "]"));
			}
		}
		// 输出日志
		BaseLogger.audit("end replayApplyMaToRf userId[" + orderdto.getUserId() + "] applyMoney[" + orderdto.getTotalPrice().toString() + "]");
	}

	// 小赢保单功能 Start

	@Override
	public void replayUpdatePolicyNoForApplyMaToRf(String orderNo) {

		BaseLogger.audit(String.format("replayUpdatePolicyNoForApplyMaToRf   失败订单号：[%s]-重新获取保单编号开始", orderNo));

		MBOrderInfoDTO orderdto = userAccountRfMapper.queryOrderByOrderNo(orderNo);

		if (orderdto == null) {
			throw new AppException("订单信息不存在");
		}
		if (!orderdto.getOrderType().equals(MBOrderInfoDTO.ORDER_TYPE_APPLY_MA_TO_RF) && !orderdto.getOrderType().equals(MBOrderInfoDTO.ORDER_TYPE_APPLY_LA_TO_LF)) {
			throw new AppException("只有定期理财/小金鱼订单才能重新申购");
		}
		if (orderdto.getProductId() == null) {
			throw new AppException("定期理财订单中产品id不存在");
		}

		try {
			// 查询用户产品相关数据
			MBUserInfoDTO userinfo = userAccountRfMapper.queryUserInfoByUserid(orderdto.getUserId(), orderdto.getProductId());
			// 异步调用生成保单号失败，手动重新获取保单号
			String strJson = getRfInvestParams(orderdto.getTotalPrice(), orderdto.getTradeId(), userinfo);

			this.apiUpdatePolicyNoByInvest(strJson, orderNo);
		} catch (Exception e) {
			BaseLogger.error(String.format("replayUpdatePolicyNoForApplyMaToRf  失败订单号：[%s]-重新获取保单编号失败", orderNo));
		}
		BaseLogger.audit(String.format("replayUpdatePolicyNoForApplyMaToRf   失败订单号：[%s]-重新获取保单编号结束", orderNo));
	}

	@Override
	public void apiUpdatePolicyNoByInvest(String strJson, String orderNo) {
		BaseLogger.audit(String.format("获取保单编号开始-下单参数：[%s] , 下单号：[%s] ", strJson, orderNo));
		String resultjson = updatePolicyNoAndNotSendFailEmail(strJson, orderNo);
		BaseLogger.audit(String.format("获取保单编号结束-下单参数：[%s] , 下单号：[%s], 小赢接口响应：[%s] ", strJson, orderNo, resultjson));
	}

	@Override
	@Async
	public void apiUpdatePolicyNoByInvestAsync(String strJson, String orderNo) {
		BaseLogger.audit(String.format("异步更新保单号-获取保单编号开始 下单号：[%s] , 下单参数：[%s] ", orderNo, strJson));
		String resultjson = updatePolicyNoAndSendFailEmail(strJson, orderNo);
		BaseLogger.audit(String.format("异步更新保单号-获取保单编号结束 下单号：[%s] , 下单参数：[%s] , 小赢接口响应：[%s] ", orderNo, strJson, resultjson));
	}

	// 通过投资接口更新保单号不发送异常邮件
	private String updatePolicyNoAndNotSendFailEmail(String strJson, String orderNo) {
		String investResultJson = null;
		try {
			// 调用投资接口获取保单号信息
			investResultJson = yingZTInvestService.apiInvest(strJson);
			updatePolicyNo(orderNo, investResultJson);
		} catch (RuntimeException e) {
			BaseLogger.error(String.format("下单号：[%s]-获取保单编号失败, 下单参数：[%s] ,失败信息: [%s]", orderNo, strJson, e.getMessage()), e);
		}
		return investResultJson;
	}

	// 通过投资接口更新保单号不发送异常邮件
	private String updatePolicyNoAndSendFailEmail(String strJson, String orderNo) {
		String investResultJson = null;
		try {
			// 调用投资接口获取保单号信息
			investResultJson = yingZTInvestService.apiInvest(strJson);
			String log = String.format("小赢理财-下单号：[%s]-获取保单编号返回信息: [%s]", orderNo, investResultJson);
			BaseLogger.audit(log);
			updatePolicyNo(orderNo, investResultJson);
		} catch (RuntimeException e) {
			String log = String.format("小赢理财-下单号：[%s]-获取保单编号失败, 下单参数：[%s] ,失败信息: [%s]", orderNo, strJson, e.getMessage());
			BaseLogger.error(log, e);
			// 发送Email
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("小赢理财-下单号[%s]-获取保单编号失败! 失败信息: [%s]", orderNo, e.getMessage())));
		}
		return investResultJson;
	}

	/**
	 * 估计投资接口更新保单号
	 *
	 * @param orderNo
	 * @param investResultJson
	 */
	private void updatePolicyNo(String orderNo, String investResultJson) {
		if (null != investResultJson) {
			JSONObject json = JSONObject.parseObject(investResultJson);
			if ("0".equals(json.getString("tradeResult"))) {
				// 更新保单号 ,更新数据库【wj_policy_info】
				String policyNo = json.getString("policyNo");
				userAccountRfMapper.updateUserPolicyInfoByOrderNo(orderNo, policyNo);
				BaseLogger.audit(String.format("下单号[%s]-获取保单编号成功 ", orderNo));
			} else {
				// 小赢接口返回交易失败
				BaseLogger.audit(String.format("下单号[%s]-获取保单编号- 小赢接口返回交易失败 ", orderNo));
			}
		}
	}

	// 小赢保单功能 End

	public void cancelApplyMaToRf(String orderNo) {
		MBOrderInfoDTO orderdto = userAccountRfMapper.queryOrderByOrderNo(orderNo);

		if (orderdto == null) {
			throw new AppException("订单信息不存在");
		}
		if (!orderdto.getOrderType().equals(MBOrderInfoDTO.ORDER_TYPE_APPLY_MA_TO_RF)) {
			throw new AppException("只有定期理财订单才能取消申购");
		}
		if (!orderdto.getOrderStatus().equals(MBOrderInfoDTO.ORDER_STATUS_APPLY_MA_TO_RF_NOCONFIRM)) {
			throw new AppException("只有定期理财订单状态在申购中才能取消申购");
		}
		if (orderdto.getProductId() == null) {
			throw new AppException("定期理财订单中产品id不存在");
		}

		if (mBProductProcess.queryOrder(orderNo, orderdto.getProductId(), orderdto.getTradeId()) == true) {
			throw new AppException("定期理财订单已经下单成功，不能取消");
		}

		BaseLogger.audit("start calcelApplyMaToRf userId[" + orderdto.getUserId() + "] withdrawMoney[" + orderdto.getTotalPrice().toString() + "]");

		// 1. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(orderdto.getUserId());
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}

		try {
			// 查询用户产品相关数据
			MBUserInfoDTO userinfo = userAccountRfMapper.queryUserInfoByUserid(orderdto.getUserId(), orderdto.getProductId());

			// 处理小赢理财订单
			userAccountTransactionService.cancelMaToRf(userinfo.getUserId(), orderNo, orderdto.getProductId(), orderdto.getTotalPrice(), userinfo.getLockVersion());

		} catch (Exception e) {
			if (e instanceof AppException) {
				throw (AppException) e;
			} else {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("calcelApplyMaToRf 异常，userId[" + orderdto.getUserId() + "] exception:" + e.getMessage()));
			}
			throw new AppException("calcelApplyMaToRf 异常，userId[" + orderdto.getUserId() + "] exception:" + e.getMessage());
		} finally {
			// 2. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(orderdto.getUserId());
			BaseLogger.audit("unlock replayApplyMaToRf userId[" + orderdto.getUserId() + "] unlockUpdateCount[" + unlockUpdateCount + "]");
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("calcelApplyMaToRf 异常，userId[" + orderdto.getUserId() + "]"));
			}
		}
		// 输出日志
		BaseLogger.audit("end calcelApplyMaToRf userId[" + orderdto.getUserId() + "] applyMoney[" + orderdto.getTotalPrice().toString() + "]");

	}

	private Date getNextDay() {
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	private void confirmRfApplyMoneyIsValid(BigDecimal money, BigDecimal startmoney, BigDecimal increasemoney) {
		if (money == null || startmoney == null || increasemoney == null) {
			throw new AppException("参数不完整");
		}

		if (money.compareTo(startmoney) < 0 || money.subtract(startmoney).divideAndRemainder(increasemoney)[1].compareTo(BigDecimal.ZERO) != 0) {
			throw new AppException(200107, "金额不合法");
		}
	}

	/**
	 * 易宝充值回调
	 */
	@Override
	public String yeePayNotice(String data, String encryptkey) {
		BaseLogger.audit("---->易宝充值回调-->yeePayNotice-->in--");
		// PayNoticeDTO payNoticeDTO ;
		// 收到通知就返回SUCCESS
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", data);
		map.put("encryptkey", encryptkey);
		map.put("type", "20");// 易宝支付

		PayNoticeDTO payNoticeDTO = payNoticeResolve(map, NOTICE_TYPE_YEE_PAY);

		// 响应结果返回
		if(payNoticeDTO.isLocalResult()){
			return "SUCCESS";
		}else{
			return "FAIL";
		}
	}

	/**
	 * 充值补单
	 */
	@Override
	public String repairRechargeOrder(String tradeNo) {
	
		try {
		
			BaseLogger.audit("tradeNo[" + tradeNo + "]充值补单开始!");
            if (StringUtils.isBlank(tradeNo)){
				throw new AppException("订单号不能为空");
		    }

			MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
			MBOrderInfoDTO order=new MBOrderInfoDTO();
			orderInfoDTO.setOrderNo(tradeNo);
			order = userAccountMapper.queryOrderInfoByOrderNo(orderInfoDTO);
			if (order == null) {
				BaseLogger.error("tradeNo[" + tradeNo + "]该订单不存在历史!");
				throw new AppException("该订单不存在历史!");
			}
			// 验证订单是否已经存在
			else if (order != null && MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM.equals(order.getOrderStatus())) {
				BaseLogger.error("tradeNo[" + tradeNo + "]订单已存在!");
				throw new AppException("订单已存在!");
			}
			/**
			 * 查询环迅交易信息
			 */
			
			IpsUserInfoDTO  ipsUserInfoDTO=new IpsUserInfoDTO(); 
			ipsUserInfoDTO.setQuerytype("02");
			ipsUserInfoDTO.setMerBillNo(tradeNo);
			
		    
            try {
            	ipsUserInfoDTO=iPSQueryUserInfoService.IpsQueryUserInfo(ipsUserInfoDTO);
			} catch (Exception e) {
				BaseLogger.error(e.getMessage());
				return e.getMessage();
			}
		    if (!"1".equals(ipsUserInfoDTO.getAcctStatus())) {
				// 订单失败,不做补单处理
				BaseLogger.error("tradeNo[" + tradeNo + "]该订单没有充值成功,不能补单!");
				throw new AppException("该订单没有充值成功,不能补单!");
			}
			
//			BigDecimal amount = new BigDecimal(ebatongTradeHistoryDTO.getAmount()).multiply(new BigDecimal("100"));
//			// 与订单金额不等,以分为单位作比较
//			if (amount.compareTo(new BigDecimal(rechargeDTO.getAmount())) != 0) {
//				BaseLogger.error("orderNo[" + orderNo + "]与订单金额不等,补单失败!");
//				throw new AppException("与订单金额不等,补单失败!");
//			}

			String userId = order.getUserId();
			orderInfoDTO.setOrderToken("orderNoIsNotnull");
			orderInfoDTO.setUserId(userId);
			orderInfoDTO.setTradeAccountCardId(order.getTradeAccountCardId());
            orderInfoDTO.setPrice(order.getPrice().setScale(2, BigDecimal.ROUND_FLOOR));

			orderInfoDTO.setOrderNo(order.getOrderNo());
			orderInfoDTO.setOrderStatus(MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM);
			orderInfoDTO.setTitle("充值");

			// 防并发
			int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
			// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
			if (lockUpdateCount != 1) {
				throw new AppException("此账户其他交易正在处理中，请稍后再试");
			}
			try {
				if (!userAccountTransactionService.doRechargeToMaCallback(orderInfoDTO)) {
					return "error";
				}
			} catch (Exception e) {
				BaseLogger.error("tradeNo[" + tradeNo + "]充值补单失败!", e);
				throw new AppException("系统异常,补单失败");
			} finally {
				// 2. 解锁
				int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
				if (unlockUpdateCount != 1) {
					sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("充值回调轮询解锁异常，userId[" + userId + "],tradeNo[" + tradeNo + "]"));
				}
			}

			BaseLogger.audit("tradeNo[" + tradeNo + "]充值补单结束!");
			return "success";
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			BaseLogger.error("tradeNo[" + tradeNo + "]充值补单失败!", e);
			throw new AppException("补单失败");
		}
	}

	/**
	 * 未绑卡下单并发验证码
	 *
	 * @param rechargeDTO
	 * @return
	 */
	public MBRechargeDTO rechargeToMaSendCode(MBRechargeDTO rechargeDTO) {
		BaseLogger.audit("---->未绑卡下单并发验证码-->rechargeToMaSendCode-->in--");
		// 校验参数
		if (!rechargeDTO.validateSendCodeParameter()) {
			throw new AppException(rechargeDTO.getMessage());
		}
		// 验证 主账户的姓名身份证是否一致
		this.validateSameCardInfo(rechargeDTO);
		// 1. 查询是否卡号是否已经绑定过
		int bindRepeatCardCount = userAccountMapper.queryBindRepeatCardCountBycardNo(rechargeDTO.getUserId(), rechargeDTO.getCardNo());

		if (bindRepeatCardCount > 0) {
			throw new AppException("此卡号已经绑定，请勿重复绑卡");
		}

		// 2. 查询身份证是否已经绑定过
		int bindRepeatUserCount = userAccountMapper.queryBindRepeatUser(rechargeDTO.getUserId(), rechargeDTO.getCertNo());
		if (bindRepeatUserCount > 0) {
			throw new AppException("该证件号已经绑定,请勿重复绑定");
		}

		// 校验卡bin(银行卡号与银行名是否匹配)
		this.validateBankCardBin(rechargeDTO.getCardNo(), rechargeDTO.getBankCode());

		// 1. 生成 card id
		String cardId = userAccountTransactionService.getId("AC", ISequenceService.SEQ_NAME_CARD_SEQ);
		// 2. 发送验证短信
		try {
			com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO paramDTO = new com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO();
			BeanUtils.copyProperties(paramDTO, rechargeDTO);

			paramDTO.setCustomerId(cardId);
			PayResultDTO payResultDTO = b2CPayGatewayService.applyPaySendMessageCode(paramDTO);

			if (!payResultDTO.getResult()) {
				throw new AppException("请检查身份信息是否正确");// 结果解析出错也同样提示
			}
			if (payResultDTO.getResult() && RECHARGE_INTERFACE_RESULT_SUCCESS.equals(payResultDTO.getResultCode())) {
				MBRechargeDTO MBRechargeDTO = new MBRechargeDTO();
				MBRechargeDTO.setCardId(cardId);
				MBRechargeDTO.setOrderNo(payResultDTO.getOrderNo());
				MBRechargeDTO.setCertType(paramDTO.getCertType());
				// 新增短信有效时间 update by Eric $.2015-09-29 14:41:12
				MBRechargeDTO.setRemainTime("120");

				BaseLogger.audit("---->未绑卡充值下单并发验证码调用结束-->rechargeToMaSendCode");
				return MBRechargeDTO;
			} else {
				BaseLogger.error("下发短信验证码出错：" + payResultDTO.getResultMessage());

				// 错误提示信息转换
				convertErrorMessage(payResultDTO);

				throw new AppException(payResultDTO.getResultMessage());
			}
		} catch (SocketTimeoutException e) {
			BaseLogger.error("请求响应超时;", e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_WARN", pageEmailMap("rechargeToMaSendCode 响应超时:充值下单发验证码异常，userId[" + rechargeDTO.getUserId() + "]exception:" + e.getMessage()));
			throw new AppException("银行系统繁忙，请稍后再试");
		} catch (AppException e) {
			BaseLogger.error("充值下单发验证码异常", e);
			String msg = ((AppException) e).getMessage();
			if (StringUtils.isBlank(msg)) {
				throw new AppException("请核对银行卡与身份证信息");
			}
			if ("httpRequestTimeOut".equals(msg)) {
				BaseLogger.error("请求响应超时;", e);
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_WARN", pageEmailMap("rechargeToMaSendCode 响应超时:充值下单发验证码异常，userId[" + rechargeDTO.getUserId() + "]exception:" + e.getMessage()));
				throw new AppException("银行系统繁忙，请稍后再试");
			}
			throw e;
		} catch (Exception e) {
			BaseLogger.error("充值下单发验证码异常", e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("rechargeToMaSendCode 充值下单发验证码异常，userId[" + rechargeDTO.getUserId() + "]exception:" + e.getMessage()));
			throw new AppException("银行系统繁忙，请稍后再试");
		}
	}

	private void validatePlaceAnOrderInfo(MBRechargeDTO rechargeDTO){
		WjEbatongTradeHistoryDTO wjEbatongTradeHistoryDTO = 
				userAccountMapper.queryWjEbatongTradeHistoryToRepair(rechargeDTO.getOrderNo());
		
		if (wjEbatongTradeHistoryDTO == null) {
			throw new AppException("请先获取验证码,再确认提交充值");
		}
		// 1. 对比信息
		try {
			if(!wjEbatongTradeHistoryDTO.getCardNo().equals(rechargeDTO.getCardNo())
					||!wjEbatongTradeHistoryDTO.getCertNo().equals(rechargeDTO.getCertNo())
					||!wjEbatongTradeHistoryDTO.getCardBindMobilePhoneNo().equals(rechargeDTO.getCardBindMobilePhoneNo())
					||!wjEbatongTradeHistoryDTO.getRealName().equals(rechargeDTO.getRealName())){
				throw new AppException("与下单数据不符，请重新下单获取验证码");
			}
		} catch (AppException e) {
			BaseLogger.error("未绑卡确认充值，验证下单信息不符,rechargeDTO="+JSONObject.toJSONString(rechargeDTO)
					+"wjEbatongTradeHistoryDTO="+JSONObject.toJSONString(wjEbatongTradeHistoryDTO));
			throw e;
		} catch (Exception e) {
			BaseLogger.error("未绑卡确认充值，验证下单信息异常，rechargeDTO="+JSONObject.toJSONString(rechargeDTO)
					+"wjEbatongTradeHistoryDTO="+JSONObject.toJSONString(wjEbatongTradeHistoryDTO));
			throw new AppException("数据异常，请重新下单获取验证码");
		}
	}
	
	/**
	 * 未绑卡确认付款
	 *
	 * @param rechargeDTO
	 * @return
	 */
	public Boolean doRechargeToMa(MBRechargeDTO rechargeDTO) {
		BaseLogger.audit("未绑卡确认付款-->doRechargeToMa-->in--");
		// 1.验证前台数据
		if (!rechargeDTO.validateDoRechargeToMaParameter()) {
			throw new AppException(rechargeDTO.getMessage());
		}
		// 2.1验证 主账户的姓名身份证是否一致，是否并发
		this.validateSameCardInfo(rechargeDTO);
		//2.2验证与下单信息是否一致
		this.validatePlaceAnOrderInfo(rechargeDTO);
		
		// 3.因为本方法,不直接操作用户账户,只是增加处理中的订单,所以不锁定账户
		// 查询下账户是否锁定,锁定不处理
		int lockCount = userAccountMapper.findCountWithUserDrawLock(rechargeDTO.getUserId());
		// 大于0说明已经上锁
		if (lockCount > 0) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}
		try {
			// 5.检验卡号是否重复（未绑卡充值）
			int bindRepeatCardCount = userAccountMapper.queryBindRepeatCardCountBycardNo(rechargeDTO.getUserId(), rechargeDTO.getCardNo());
			if (bindRepeatCardCount > 0) {
				throw new AppException("此卡号已经绑定，请勿重复绑卡");
			}
			// 6.校验是否已经成功交易过.如果有,则不能再次发送交易;校验支付时所传的参数和获取验证码时发送的同字段参数是否相同,如果不相同,则不能进行支付交易、(支付接口中处理)
			// 7.扣钱调用支付接口（保存支付历史（流水））

			com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO paramDTO = new com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO();
			BeanUtils.copyProperties(paramDTO, rechargeDTO);

			PayResultDTO payResultDTO = b2CPayGatewayService.confirmPay(paramDTO);

			// 8.支付后,不管成功失败,更新wjEbatongTradeHistory表中相应字段(支付接口中处理)
			// 9.充值成功后，业务逻辑
			if (!payResultDTO.getResult()) {
				// sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
				// pageEmailMap("doRechargeToMa 充值发逻辑异常，userId[" +
				// rechargeDTO.getUserId() + "] 错误信息[" +
				// b2cGetawayDTO.getErrorMessage()));
				throw new AppException("银行系统繁忙，请重新尝试充值");
			}

			if (payResultDTO.getResult() && RECHARGE_INTERFACE_RESULT_SUCCESS.equals(payResultDTO.getResultCode())) {
				// 充值成功后逻辑
				// 银行名称查询
				List<BankInfoDTO> bankInfo = userAccountMapper.findBankInfoList(rechargeDTO.getBankCode());
				if (bankInfo != null && bankInfo.size() > 0) {
					rechargeDTO.setBankName(bankInfo.get(0).getBankNameShow());
				} else {
					BaseLogger.error("未绑卡确认充值异常:银行名称为空!bankCode[" + rechargeDTO.getBankCode() + "]");
					sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("未绑卡确认充值异常:银行名称为空!bankCode[" + rechargeDTO.getBankCode() + "],userId[" + rechargeDTO.getUserId() + "]"));
				}
				// 判断性别
				if (StringUtils.isNotBlank(rechargeDTO.getCertType()) && rechargeDTO.getCertType().equals("01"))// 身份证
					rechargeDTO.setSex(CheckIdCardUtils.getGenderByIdCard(rechargeDTO.getCertNo()));

				//判断是否生成过充值流水，如果已经有了，不可重复生成
				boolean existTradeRecord = tradeRecordMapper.countTradeRecordExitsByTradeNo(rechargeDTO.getOrderNo())>0?true:false;
				if(!existTradeRecord){
					String bizOrderNo = userAccountTransactionService.getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);;
					tradeRecordMapper.insertTradeRecord(rechargeDTO.getOrderNo(), new BigDecimal(rechargeDTO.getAmount()),  bizOrderNo,rechargeDTO.getCardNo(),payResultDTO.getPlatformId());
					BigDecimal amount = new BigDecimal(rechargeDTO.getAmount()).setScale(2, BigDecimal.ROUND_FLOOR);
					// 增加一个充值中订单
					userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getDoRechargeToMaToDispose(rechargeDTO.getUserId(), bizOrderNo, amount, rechargeDTO.getCardId()));
				}
				// 补充充值卡信息
				userAccountTransactionService.doRechargeToMaToDispose(rechargeDTO);

				// 绑卡成功后，通知信贷系统，增加信用额度
				try {
					taskDetailsInfoDubboService.bindCardTaskFinish(rechargeDTO.getUserId());
				} catch (Exception e) {
					sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("doRechargeToMa 绑卡成功后，调用信贷系统增加用户信用额度异常，userId[" + rechargeDTO.getUserId() + "]exception :" + e.getMessage()));
				}

				BaseLogger.audit("未绑卡确认充值成功-->doRechargeToMa-->end--");
				return Boolean.TRUE;
			} else {
				BaseLogger.error("确认付款出错：" + payResultDTO.getResultMessage());

				// 补充充值卡信息
				// userAccountTransactionService.doRechargeToMaToDispose(rechargeDTO);
				// 错误提示信息转换
				convertErrorMessage(payResultDTO);

				throw new AppException(payResultDTO.getResultMessage());
			}
		} catch (SocketTimeoutException e) {
			BaseLogger.error("请求响应超时;", e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_WARN", pageEmailMap("doRechargeToMa 响应超时:未绑卡确认充值异常，userId[" + rechargeDTO.getUserId() + "]exception:" + e.getMessage()));
			throw new AppException("银行系统繁忙，请稍后再试");
		} catch (AppException e) {
			BaseLogger.error("未绑卡确认充值异常;", e);
			String msg = ((AppException) e).getMessage();
			if (StringUtils.isBlank(msg)) {
				throw new AppException("银行系统繁忙，请重新尝试充值");
			}
			if ("httpRequestTimeOut".equals(msg)) {
				BaseLogger.error("请求响应超时;", e);
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_WARN", pageEmailMap("doRechargeToMa 响应超时:未绑卡确认充值异常，userId[" + rechargeDTO.getUserId() + "]exception:" + e.getMessage()));
				throw new AppException("银行系统繁忙，请稍后再试");
			}
			throw e;
		} catch (Exception e) {
			BaseLogger.error("未绑卡确认充值异常", e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("doRechargeToMa 未绑卡确认充值异常，userId[" + rechargeDTO.getUserId() + "]exception :" + e.getMessage()));
			throw new AppException("银行系统繁忙，请稍后再试");
		}
	}

	@Override
	public Boolean confirmBindCardSendCode(MBRechargeDTO rechargeDTO) {
		BaseLogger.audit("未绑卡确认-->confirmBindCardSendCode-->in--");
		// 1.验证前台数据
		if (!rechargeDTO.validateDoBindCardParameter()) {
			throw new AppException(rechargeDTO.getMessage());
		}
        Boolean status = userAccountMapper.queryWithholdBankStatusByBankCode(rechargeDTO.getBankCode(), rechargeDTO.getOrderType());
        if (status == null || !status) {//银行不可用或维护
            throw new AppException("银行不可用或维护中");
        }
		// 2.验证 主账户的姓名身份证是否一致，是否并发
		this.validateSameCardInfo(rechargeDTO);
		try {
            // 3.检验该渠道下是否已绑卡
//            int bindRepeatCardCount = userAccountMapper.queryBindRepeatCardCountBycardNo(rechargeDTO.getUserId(), rechargeDTO.getCardNo());
            int withholdBindRepeatCardNum = userAccountMapper.queryWithholdBindRepeatCardNumByBizType(rechargeDTO.getUserId(), rechargeDTO.getCardNo(), rechargeDTO.getOrderType());
            if (withholdBindRepeatCardNum > 0) {
				throw new AppException("此卡号已经绑定，请勿重复绑卡");
			}

			com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO paramDTO = new com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO();
			BeanUtils.copyProperties(paramDTO, rechargeDTO);

			SmsResultDTO smsResultDTO = b2CPayGatewayService.confirmBindCard(paramDTO);

			// 银行名称查询
			List<BankInfoDTO> bankInfo = userAccountMapper.findBankInfoList(rechargeDTO.getBankCode());
			if (bankInfo != null && bankInfo.size() > 0) {
				rechargeDTO.setBankName(bankInfo.get(0).getBankNameShow());
			} else {
				BaseLogger.error("未绑卡确认异常:银行名称为空!bankCode[" + rechargeDTO.getBankCode() + "]");
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("未绑卡确认异常:银行名称为空!bankCode[" + rechargeDTO.getBankCode() + "],userId[" + rechargeDTO.getUserId() + "]"));
			}
			// 判断性别
            if (StringUtils.isNotBlank(rechargeDTO.getCertType()) && rechargeDTO.getCertType().equals("01")) {// 身份证
				rechargeDTO.setSex(CheckIdCardUtils.getGenderByIdCard(rechargeDTO.getCertNo()));
            }
            int bindRepeatCardCount = userAccountMapper.queryBindRepeatCardCountBycardNo(rechargeDTO.getUserId(), rechargeDTO.getCardNo());
            if (bindRepeatCardCount <= 0) {
			userAccountTransactionService.doBindCard(rechargeDTO);
			// 绑卡成功后，通知信贷系统，增加信用额度
			try {
				taskDetailsInfoDubboService.bindCardTaskFinish(rechargeDTO.getUserId());
			} catch (Exception e) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("confirmBindCardSendCode 绑卡成功后，调用信贷系统增加用户信用额度异常，userId[" + rechargeDTO.getUserId() + "]exception :" + e.getMessage()));
			}
            }

			BaseLogger.audit("未绑卡确认成功-->confirmBindCardSendCode-->end--");
			return Boolean.TRUE;
		} catch (AppException e) {
			BaseLogger.error("未绑卡确认充值异常;", e);
            String msg = e.getMessage();
			if (StringUtils.isBlank(msg)) {
				throw new AppException("银行系统繁忙，请重新尝试充值");
			}
			throw e;
		} catch (Exception e) {
			BaseLogger.error("未绑卡确认异常", e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("confirmBindCardSendCode 未绑卡确认异常，userId[" + rechargeDTO.getUserId() + "]exception :" + e.getMessage()));
			throw new AppException("银行系统繁忙，请稍后再试");
		}
	}

	/**
	 * 绑定卡下单并发验证码
	 *
	 * @param userId
	 * @param cardId
	 * @param amount
	 * @return
	 */
	public MBRechargeDTO rechargeToMaAlreadyBindCardSendCode(String userId, String cardId, String amount) {
		BaseLogger.audit("绑定卡下单并发验证码-->rechargeToMaAlreadyBindCardSendCode-->in--");
		// 1. 校验参数合法性
		this.checkRechargeParamValidate(userId, cardId, amount);

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

			PayResultDTO payResultDTO = b2CPayGatewayService.applyPaySendMessageCode(paramDTO);

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

	/**
	 * 绑定卡确认付款
	 *
	 * @param userId
	 * @param cardId
	 * @param orderNo
	 * @param amount
	 * @param code
	 * @return
	 */
	public Boolean doRechargeToMaAlreadyBind(String userId, String cardId, String orderNo, String amount, String code) {
		BaseLogger.audit("绑定卡确认付款-->doRechargeToMaAlreadyBind-->in--");
		// 校验参数合法性
		if (StringUtils.isBlank(code)) {
			throw new AppException("请输入您的短信验证码");
		}

		MBRechargeDTO rechargeDTO = userAccountMapper.queryBindCardInfoAndUserInfo(userId, cardId);
		if (rechargeDTO == null) {
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡确认充值发逻辑异常，userId[" + userId + "]"));
			throw new AppException("数据异常，请联系客服");
		}
		rechargeDTO.setOrderNo(orderNo);
		rechargeDTO.setAmount(amount);
		rechargeDTO.setDynamicCode(code);

		if (!rechargeDTO.validateDoRechargeToMaParameter()) {
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡确认充值发逻辑异常，userId[" + rechargeDTO.getUserId() + "]"));
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

			PayResultDTO payResultDTO = b2CPayGatewayService.confirmPay(paramDTO);

			// //扣钱调用支付接口（保存支付历史（流水））
			if (!payResultDTO.getResult()) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡确认充值发逻辑异常，userId[" + rechargeDTO.getUserId() + "] 错误信息[" + payResultDTO.getResultMessage()));
				throw new AppException("银行系统繁忙，请重新尝试充值");
			}

			if (payResultDTO.getResult() && RECHARGE_INTERFACE_RESULT_SUCCESS.equals(payResultDTO.getResultCode())) {
				//判断是否生成过充值流水，如果已经有了，不可重复生成
				boolean existTradeRecord = tradeRecordMapper.countTradeRecordExitsByTradeNo(orderNo)>0?true:false;
				// TODO 初始化交易流水信息tradeNo bizOrderNo
				if(!existTradeRecord){
					String bizOrderNo = userAccountTransactionService.getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);;
					rechargeDTO.setBizOrderNo(bizOrderNo);

					tradeRecordMapper.insertTradeRecord(rechargeDTO.getOrderNo(), new BigDecimal(rechargeDTO.getAmount()),  bizOrderNo,rechargeDTO.getCardNo(),payResultDTO.getPlatformId());
					userAccountTransactionService.doRechargeToMaAlreadyBindToDispose(rechargeDTO);
				}
				// 充值成功后逻辑
				BaseLogger.audit("已经绑卡确认充值成功-->doRechargeToMaAlreadyBind-->end--");
				return Boolean.TRUE;
			} else {
				//callback 回调业务失败..
				BaseLogger.error("已经绑卡确认充值出错：" + payResultDTO.getResultMessage());
				// 错误提示信息转换
				convertErrorMessage(payResultDTO);

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
		}
	}

	/**
	 * 支付渠道充值通知回调处理
	 *
	 * @param map
	 * @param type
	 * @return
	 */
	private PayNoticeDTO payNoticeResolve(Map<String, Object> map, int type) {
		BaseLogger.audit("支付渠道充值通知回调-->payNoticeResolve-->in--");
		// 1.请求报文解析
		PayNoticeDTO payNoticeDTO = new PayNoticeDTO();
		try {
			payNoticeDTO = b2CPayGatewayService.validateSign(map, type);
		} catch (Exception e1) {
			BaseLogger.error("payNotice 充值通知回调发生异常 map:" + map.toString(), e1);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("payNoticeResolve 充值通知回调,请求报文解析发生异常 exception:" + e1.getMessage() + "---map:" + map.toString()));
			throw new AppException(e1.getMessage());
		}
		
		// 基础校验
		if (StringUtils.isBlank(payNoticeDTO.getOrderId())) {
			throw new AppException("无效的交易单号;");
		}
		// 查询本地交易状态 SF -->返回交易状态
		TradeRecordBO tradeRecordBO = tradeRecordMapper.queryTradeRecordByTradeNo(payNoticeDTO.getOrderId());
		if (tradeRecordBO == null) {
			BaseLogger.error("orderNo[" + payNoticeDTO.getOrderId() + "]订单不存在!");
			throw new AppException("订单信息不存在!");
		}
		// 成功
		if (TradeStatusEnum.TRADE_SUCCESS.getValue().equals(tradeRecordBO.getTradeStatus())) {
			payNoticeDTO.setLocalResult(true);
		}else{
			payNoticeDTO.setLocalResult(false);
		}
		BaseLogger.audit("支付渠道充值通知回调-->payNoticeResolve-->end-->"+
				"orderNo[" + payNoticeDTO.getOrderId() + "],tradeStatus["+tradeRecordBO.getTradeStatus()+"]");
		
		//京东代扣开发处理业务
		if(NOTICE_TYPE_JD_PAY == type){
			// 执行facade 做后续业务处理
			try {
				BigDecimal amount = new BigDecimal(payNoticeDTO.getAmount()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_FLOOR);
				withholdServiceFacade.confirmPaymentTradeStatusAndCallbackWithholdOperate(payNoticeDTO.getOrderId(), amount, PayNoticeDTO.SUCCESS.equals(payNoticeDTO.getComparisonResult()));
			} catch (Exception ex) {
				String message = String.format("payNoticeResolve 通知回调,发生未知异常:userId[%s],orderId[%s];", payNoticeDTO.getUserId(), payNoticeDTO.getOrderId());
				BaseLogger.error(message, ex);			
				if(!ex.getMessage().equals("正在交易中,请稍后再试;")){
					sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(message + ",exception:" + ex.getMessage()));				
				}
				throw ex;
			}
		}

		return payNoticeDTO;
	}

	/**
	 * 第三方解绑银行卡
	 */
	@Override
	public String unbundlingCard(MBRechargeDTO rechargeDTO) {
		BaseLogger.audit("第三方解绑银行卡开始!");
		// 校验
		if (StringUtils.isBlank(rechargeDTO.getUserId())) {
			throw new AppException("用户id不能为空!");
		}
		if (StringUtils.isBlank(rechargeDTO.getCardNo())) {
			throw new AppException("银行卡号不能为空!");
		}
		if (StringUtils.isBlank(rechargeDTO.getBindType())) {
			throw new AppException("绑卡类型不能为空!");
		}

		com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO paramDTO = new com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO();
		try {
			BeanUtils.copyProperties(paramDTO, rechargeDTO);

			PayResultDTO payResultDTO = b2CPayGatewayService.unbundlingCard(paramDTO);

			if (!payResultDTO.getResult() || StringUtils.isNotBlank(payResultDTO.getResultMessage())) {
				throw new AppException(payResultDTO.getResultMessage());
			}
		} catch (AppException e) {
			BaseLogger.error("第三方解绑银行卡,程序错误;", e);
			throw new AppException(e.getMessage());
		} catch (Exception e) {
			BaseLogger.error("第三方解绑银行卡,程序错误;", e);
		}

		BaseLogger.audit("第三方解绑银行卡结束!");
		return null;
	}

	// 联动支付，通知回调
	public String payNotice(Map<String, Object> map) {
		BaseLogger.audit("---联动优势,充值通知回调-->payNotice-->in--");
		// 如验证平台签名正确，即应响应UMPAY平台返回码为0000。【响应返回码代表通知是否成功，和通知的交易结果（支付失败、支付成功）无关】
		// 验签支付结果通知 如验签成功，则返回ret_code=0000
		PayNoticeDTO payNoticeDTO = payNoticeResolve(map, NOTICE_TYPE_UNION_MOBILE);

		// 响应结果返回
		return setResponseParam(payNoticeDTO);
	}

	// 银行卡号与银行名称校验
	private void validateBankCardBin(String cardNo, String bankCode) {
		if (cardNo.length() < 12) {
			throw new AppException("请输入正确的银行卡号!");
		}
		int count = userAccountMapper.findCountWithBankCode(bankCode, cardNo);
		if (count < 1) {
			throw new AppException("请选择正确的银行!");
		}
	}

	// 充值回调查询
	@Override
	public Map<String, String> queryRechargeNotice(String userId, String tradeNo) {
		BaseLogger.audit("回调查询-->queryTradeResultNotice-->in--");
		// 是否需要执行回调业务(应该是查询第三方,更新状态后才需要回调)
		Map<String, String> resultMap = Maps.newHashMap();

		// 1 检查参数不能为空
		queryRechargeNoticeValidate(userId, tradeNo);
		// 1.查询本地交易状态 SF -->返回交易状态
		TradeRecordBO tradeRecordBO = tradeRecordMapper.queryTradeRecordByTradeNo(tradeNo);
		if (tradeRecordBO == null) {
			BaseLogger.error("orderNo[" + tradeNo + "]订单不存在!");
			throw new AppException("订单信息不存在!");
		}

		if(StringUtils.isBlank(tradeRecordBO.getRelBizOrderNo())){
			BaseLogger.error("交易单号[" + tradeNo + "],业务订单号为空不存在!");
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap(String.format("支付系统异常,存在交易单号,不存在业务单号，需要运营人工届入 tradeNo[%s],userId[%s] amount[%s] orderNo[%s]", tradeNo,userId, tradeRecordBO.getPrice(), tradeRecordBO.getRelBizOrderNo())));
			throw new AppException("无效的订单信息!");
		}

		// 成功
		if (TradeStatusEnum.TRADE_SUCCESS.getValue().equals(tradeRecordBO.getTradeStatus())) {
			resultMap.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM);
			resultMap.put("price", tradeRecordBO.getTotalPrice().toString());
			resultMap.put("message", "充值成功!");
		}

		if (TradeStatusEnum.TRADE_FAILED.getValue().equals(tradeRecordBO.getTradeStatus())) {
			resultMap.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_FAIL);
			resultMap.put("message", "充值失败!");
		}

		// 1.2process-->调用查询
		try{
			SingleStatusResult result = modifyTransStatus(tradeNo, tradeRecordBO.getRelBizOrderNo());

			if (result.getResultCode().equals(SingleStatusResult.SUCCESS)) {
				resultMap.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM);
				resultMap.put("price", tradeRecordBO.getTotalPrice().toString());
				resultMap.put("message", "充值成功!");
			} else if(result.getResultCode().equals(SingleStatusResult.FAILED)){
				resultMap.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_FAIL);
				resultMap.put("message", "充值失败!");
			}else if(result.getResultCode().equals(SingleStatusResult.PROCESS)){
				resultMap.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_DISPOSE);
				resultMap.put("message", "充值处理中...");
			}
		}catch(Exception ex){
			resultMap.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_DISPOSE);
			resultMap.put("message", "充值处理中...");
		}
		return resultMap;
	}

	@Override
	public MBRechargeDTO rechargeToMaSendCodeV32(MBRechargeDTO rechargeDTO) {
		BaseLogger.audit("---->未绑卡充值下单并发验证码-->rechargeToMaSendCodeV32-->in--");
		// 校验参数
		if (!rechargeDTO.validateSendCodeParameter()) {
			throw new AppException(rechargeDTO.getMessage());
		}
		// 验证 主账户的姓名身份证是否一致
		this.validateSameCardInfo(rechargeDTO);
		// 1. 查询是否卡号是否已经绑定过
		int bindRepeatCardCount = userAccountMapper.queryBindRepeatCardCountBycardNo(rechargeDTO.getUserId(), rechargeDTO.getCardNo());

		if (bindRepeatCardCount > 0) {
			throw new AppException("此卡号已经绑定，请勿重复绑卡");
		}

		// 2. 查询身份证是否已经绑定过
		int bindRepeatUserCount = userAccountMapper.queryBindRepeatUser(rechargeDTO.getUserId(), rechargeDTO.getCertNo());
		if (bindRepeatUserCount > 0) {
			throw new AppException("该证件号已经绑定,请勿重复绑定");
		}

		// 校验卡bin(银行卡号与银行名是否匹配)
		this.validateBankCardBin(rechargeDTO.getCardNo(), rechargeDTO.getBankCode());

		// 1. 生成 card id
		String cardId = userAccountTransactionService.getId("AC", ISequenceService.SEQ_NAME_CARD_SEQ);
		// 2. 发送验证短信
		try {
			com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO paramDTO = new com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO();
			BeanUtils.copyProperties(paramDTO, rechargeDTO);

			paramDTO.setCustomerId(cardId);
			PayResultDTO payResultDTO = b2CPayGatewayService.applyPaySendMessageCodeV32(paramDTO);

			if (!payResultDTO.getResult()) {
				throw new AppException("请检查身份信息是否正确");// 结果解析出错也同样提示
			}
			if (payResultDTO.getResult() && RECHARGE_INTERFACE_RESULT_SUCCESS.equals(payResultDTO.getResultCode())) {
				MBRechargeDTO MBRechargeDTO = new MBRechargeDTO();
				MBRechargeDTO.setCardId(cardId);
				MBRechargeDTO.setOrderNo(payResultDTO.getOrderNo());
				MBRechargeDTO.setCertType(paramDTO.getCertType());
				// 新增短信有效时间 update by Eric $.2015-09-29 14:41:12
				MBRechargeDTO.setRemainTime("120");

				BaseLogger.audit("---->未绑卡充值下单并发验证码调用结束-->rechargeToMaSendCode");
				return MBRechargeDTO;
			} else {
				BaseLogger.error("下发短信验证码出错：" + payResultDTO.getResultMessage());

				// 错误提示信息转换
				convertErrorMessage(payResultDTO);

				throw new AppException(payResultDTO.getResultMessage());
			}
		} catch (SocketTimeoutException e) {
			BaseLogger.error("请求响应超时;", e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_WARN", pageEmailMap("rechargeToMaSendCode 响应超时:充值下单发验证码异常，userId[" + rechargeDTO.getUserId() + "]exception:" + e.getMessage()));
			throw new AppException("银行系统繁忙，请稍后再试");
		} catch (AppException e) {
			BaseLogger.error("充值下单发验证码异常", e);
			String msg = ((AppException) e).getMessage();
			if (StringUtils.isBlank(msg)) {
				throw new AppException("请核对银行卡与身份证信息");
			}
			if ("httpRequestTimeOut".equals(msg)) {
				BaseLogger.error("请求响应超时;", e);
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_WARN", pageEmailMap("rechargeToMaSendCode 响应超时:充值下单发验证码异常，userId[" + rechargeDTO.getUserId() + "]exception:" + e.getMessage()));
				throw new AppException("银行系统繁忙，请稍后再试");
			}
			throw e;
		} catch (Exception e) {
			BaseLogger.error("充值下单发验证码异常", e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("rechargeToMaSendCode 充值下单发验证码异常，userId[" + rechargeDTO.getUserId() + "]exception:" + e.getMessage()));
			throw new AppException("银行系统繁忙，请稍后再试");
		}
	}

	@Override
	public MBRechargeDTO bindCardSendCode(MBRechargeDTO rechargeDTO) {
		BaseLogger.audit("---->绑卡发验证码-->bindCardSendCode-->in--");
		// 校验参数
		if (!rechargeDTO.validateBindCardSendCodeParameter()) {
			throw new AppException(rechargeDTO.getMessage());
		}

        Boolean status = userAccountMapper.queryWithholdBankStatusByBankCode(rechargeDTO.getBankCode(), rechargeDTO.getOrderType());
        if (status == null || !status) {//银行不可用或维护
        	//根据卡号,查询是否是安全卡
        	TradeAccountCardDTO cardDTO = userAccountMapper.queryVaildSecurityCardInfoByUserId(rechargeDTO.getUserId());
        	if(cardDTO != null && cardDTO.getBankCardNo().equals(rechargeDTO.getCardNo())){
        		//如果是安全卡卡号,特殊定义术语(主要应用于安全卡号绑定代扣业务)
        		throw new AppException("该卡不支持此业务,如有疑问请联系客服");
        	}else{
            throw new AppException("银行不可用或维护中");
        }
        }
		// 验证 主账户的姓名身份证是否一致
		this.validateSameCardInfo(rechargeDTO);
		// 1. 查询是否卡号是否已经绑定过
        //这里是否重复绑卡应该判断wj_pay_bind_card_ref这张表
//        int bindRepeatCardCount = userAccountMapper.queryBindRepeatCardCountBycardNo(rechargeDTO.getUserId(), rechargeDTO.getCardNo());
        int bindRepeatCardCount = userAccountMapper.queryWithholdBindRepeatCardNumByBizType(rechargeDTO.getUserId(), rechargeDTO.getCardNo(), rechargeDTO.getOrderType());
		if (bindRepeatCardCount > 0) {
			throw new AppException("此卡号已经绑定，请勿重复绑卡");
		}

		// 2. 查询身份证是否已经绑定过
        //上面（this.validateSameCardInfo(rechargeDTO)）已经验证过，无需重复验证
//        int bindRepeatUserCount = userAccountMapper.queryBindRepeatUser(rechargeDTO.getUserId(), rechargeDTO.getCertNo());
//        if (bindRepeatUserCount > 0) {
//            throw new AppException("该证件号已经绑定,请勿重复绑定");
//        }

		// 校验卡bin(银行卡号与银行名是否匹配)
		this.validateBankCardBin(rechargeDTO.getCardNo(), rechargeDTO.getBankCode());

		// 1. 生成 card id
		String cardId = userAccountTransactionService.getId("AC", ISequenceService.SEQ_NAME_CARD_SEQ);
		// 2. 发送验证短信
		try {
			com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO paramDTO = new com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO();
			BeanUtils.copyProperties(paramDTO, rechargeDTO);
			paramDTO.setCustomerId(cardId);

            //mock数据
			SmsResultDTO smsResultDTO = b2CPayGatewayService.bindCard(paramDTO);

			MBRechargeDTO mBRechargeDTO = new MBRechargeDTO();
			mBRechargeDTO.setCardId(cardId);
			mBRechargeDTO.setOrderNo(smsResultDTO.getOrderNo());
			mBRechargeDTO.setCertType(paramDTO.getCertType());
//            mBRechargeDTO.setRemainTime(StringUtils.isBlank(smsResultDTO.getRemainTime()) ? "120" : smsResultDTO.getRemainTime());
            mBRechargeDTO.setRemainTime("60");
			BaseLogger.audit("---->绑卡并发验证码调用结束-->bindCardSendCode");
			return mBRechargeDTO;
		} catch (AppException e) {
			BaseLogger.error("绑卡发验证码异常", e);
            String msg = e.getMessage();
			if (StringUtils.isBlank(msg)) {
				throw new AppException("请核对银行卡与身份证信息");
			}
			throw e;
		} catch (Exception e) {
			BaseLogger.error("绑卡发验证码异常", e);
			throw new AppException("银行系统繁忙，请稍后再试");
		}
	}

	@Override
	public MBRechargeDTO rechargeToMaAlreadyBindCardSendCodeV32(String userId, String cardId, String amount) {
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

			PayResultDTO payResultDTO = b2CPayGatewayService.applyPaySendMessageCodeV32(paramDTO);

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

	private boolean queryRechargeNoticeValidate(String userId, String orderId) {
		if (StringUtils.isBlank(userId)) {
			throw new AppException("用户信息异常!");
		}
		if (StringUtils.isBlank(orderId)) {
			throw new AppException("订单不能为空!");
		}
		return true;
	}

	private boolean noticeToQueryOrderStatus(Map<String, String> result, String userId, String orderId) {
		MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		orderInfoDTO.setOrderNo(orderId);
		MBOrderInfoDTO order = null;
		try {
			order = userAccountMapper.queryOrderInfoByOrderNo(orderInfoDTO);
		} catch (Exception e) {
			BaseLogger.error("orderNo[" + orderId + "]订单查询出错!");
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("userId[" + userId + "],orderNo[" + orderId + "]该订单充值回调轮询,查询订单异常!"));
			throw new AppException("系统繁忙,请稍后查看");
		}

		if (order == null) {
			BaseLogger.error("orderNo[" + orderId + "]订单不存在!");
			throw new AppException("订单不存在!");
		}

		BaseLogger.audit("充值回调查询-->queryRechargeNotice-->order.orderStatus[" + order.getOrderStatus() + "]");
		if (MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM.equals(order.getOrderStatus())) {
			result.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM);
			result.put("price", order.getPrice().toPlainString());
			result.put("message", "充值成功!");

			return true;
		}
		if (MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_FAIL.equals(order.getOrderStatus())) {
			// 查询失败原因
			PayResultDTO payResultDTO = noticeChannelQueryOrder(orderId);
			// 查询接口调用失败
			if (!payResultDTO.getResult()) {
				result.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_DISPOSE);
				result.put("message", "充值处理中...");

				return true;
			}
			String message = "充值失败!";
			// 错误提示转换
			convertErrorMessage(payResultDTO);
			if (StringUtils.isNotBlank(payResultDTO.getResultMessage())) {
				message = payResultDTO.getResultMessage();
			}

			result.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_FAIL);
			result.put("message", message);

			return true;
		}
		return false;
	}

	private PayResultDTO noticeChannelQueryOrder(String orderId) {
		WjEbatongTradeHistoryDTO ebatongTradeHistoryDTO = userAccountMapper.queryWjEbatongTradeHistoryToRepair(orderId);
		if (ebatongTradeHistoryDTO == null) {
			BaseLogger.error("orderNo[" + orderId + "]该订单不存在充值历史!");
			throw new AppException("系统异常!");
		}
		// 查询订单查询接口
		com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO rechargeDTO = new com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO();
		rechargeDTO.setOrderNo(orderId);

		try {
			return b2CPayGatewayService.queryOrderWithHold(rechargeDTO);
		} catch (Exception e) {
			BaseLogger.error("查询订单出错", e);
			return null;
		}
	}

	private boolean noticeChannelQueryResultToJudge(Map<String, String> result, PayResultDTO payResultDTO) {
		// 查询接口调用失败
		if (!payResultDTO.getResult()) {
			result.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_DISPOSE);
			result.put("message", "充值处理中...");

			return true;
		}
		com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO rechargeDTO = payResultDTO.getRechargeDTO();

		String status = rechargeDTO.getStatus();
		BaseLogger.audit("充值回调查询-->queryRechargeNotice-->status[" + status + "],'10'为成功,'20'为失败");
		// 2.1.1为充值处理中状态,返回结果
		if (!"20".equals(status) && !"10".equals(status)) {
			result.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_DISPOSE);
			result.put("message", "充值处理中...");

			return true;
		}
		return false;
	}

	private boolean noticeLockUserAccount(String userId, String orderId) {
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		for (int i = 0; i < 3; i++) {
			if (lockUpdateCount != 1) {
				try {
					Thread.sleep(1000L);
					BaseLogger.audit("充值回调查询-->queryRechargeNotice-->等待一秒!");
					lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
				} catch (InterruptedException e) {
					BaseLogger.error("orderNo[" + orderId + "]充值回调轮询,等待一秒失败!");
				}
			} else {
				return true;
			}
		}
		return false;
	}

	private boolean noticeToQueryOrderStatusAgain(PayResultDTO payResultDTO, Map<String, String> result, String userId, String orderId, MBOrderInfoDTO newOrder) {
		BaseLogger.audit("充值回调查询-->queryRechargeNotice-->newOrder.orderStatus[" + newOrder.getOrderStatus() + "]");
		if (MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM.equals(newOrder.getOrderStatus())) {
			result.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM);
			result.put("price", newOrder.getPrice().toPlainString());
			result.put("message", "充值成功!");

			return true;
		}
		if (MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_FAIL.equals(newOrder.getOrderStatus())) {
			String message = "充值失败!";
			// 错误提示转换
			convertErrorMessage(payResultDTO);
			if (StringUtils.isNotBlank(payResultDTO.getResultMessage())) {
				message = payResultDTO.getResultMessage();
			}

			result.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_FAIL);
			result.put("message", message);

			return true;
		}

		BigDecimal amount = newOrder.getPrice().multiply(new BigDecimal("100"));
		// 与订单金额不等,以分为单位作比较
		if (amount.compareTo(new BigDecimal(payResultDTO.getRechargeDTO().getAmount())) != 0) {
			result.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_FAIL);
			result.put("message", "充值金额异常,充值失败!");

			return true;
		}
		return false;
	}

	private Map<String, String> noticeOrderResultHandle(PayResultDTO payResultDTO, MBOrderInfoDTO newOrder, String userId, String orderId) {
		Map<String, String> result = new HashMap<String, String>();
		com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO rechargeDTO = payResultDTO.getRechargeDTO();
		if ("20".equals(rechargeDTO.getStatus())) {// 20:订单失败
			// 2.1.2.1为充值失败状态,修改订单
			newOrder.setOrderStatus(MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_FAIL);
			newOrder.setTitle("充值失败");

			String message = "充值失败!";
			// 错误提示转换
			convertErrorMessage(payResultDTO);
			if (StringUtils.isNotBlank(payResultDTO.getResultMessage())) {
				message = payResultDTO.getResultMessage();
			}

			result.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_FAIL);
			result.put("message", message);

			// 失败:修改订单状态
			int count = userAccountMapper.updateOrderStatus(newOrder);
			BaseLogger.audit(String.format("充值回调轮询,结果为[失败]修改订单orderId[%s],userId[%s],修改记录数[%s]", orderId, userId, String.valueOf(count)));
			// 修改历史表记录为异常
			userAccountMapper.updateWjEbatongTradeHistoryStatus(orderId);

			return result;
		} else if ("10".equals(rechargeDTO.getStatus())) {// 10:订单成功
			// 2.1.2.2为充值成功状态,修改订单并给账户加钱
			newOrder.setOrderStatus(MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM);
			newOrder.setTitle("充值");

			if (!userAccountTransactionService.doRechargeToNotice(newOrder)) {
				BaseLogger.error("orderNo[" + orderId + "]该订单充值回调轮询,修改为成功订单操作异常!");
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("userId[" + userId + "],orderNo[" + orderId + "]该订单充值回调轮询,修改为成功订单操作异常!"));

				result.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_FAIL);
				result.put("message", "充值失败,请联系客服!");

				return result;
			}

			result.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM);
			result.put("price", newOrder.getPrice().toPlainString());
			result.put("message", "充值成功!");

			BaseLogger.audit("-->充值回调轮询-->queryRechargeNotice-->结果为成功,并对订单和账户做处理--");

			return result;
		} else {// 不会出现
			throw new AppException("系统异常!");
		}
	}

	/**
	 * @param userId
	 * @param applyMoney
	 * @param productId
	 * @return
	 * @throws Exception
     * @title 用户申购定期理财产品，从La到Lf账户
	 */
	public MBRfReturnDTO applyLaToLf(String userId, String applyMoney, String productId, String token) {
		BaseLogger.audit("start applyLaToLf userId[" + userId + "] withdrawMoney[" + applyMoney + "]");
		// 校验参数
		BigDecimal amount = confirmUserIsExitAndMoneyIsValid(userId, applyMoney).setScale(0, BigDecimal.ROUND_FLOOR);

		// 1. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}

		MBRfReturnDTO dto = new MBRfReturnDTO("200100", "投资成功", null, "");

		try {
			// 主业务逻辑

			// 查询用户产品相关数据
			MBUserInfoDTO userinfo = userAccountLfMapper.queryUserInfoByUserid(userId, productId);

			if (userinfo == null) {
				throw new AppException("产品不存在 请重新选择");
			}
			int tokencount = userAccountLfMapper.queryProductOrderByToken(token);
			if (tokencount > 0) {
				throw new AppException("请勿重复下单");
			}

			// 校验购买金额是否符合产品startmoney，increasemoney标准
			if (userinfo.getProductStartmoney() != null && userinfo.getProductIncreaseMoney() != null) {
				confirmRfApplyMoneyIsValid(amount, userinfo.getProductStartmoney(), userinfo.getProductIncreaseMoney());
			}
			// 校验
			if (userinfo.getIndentityType() == null || !userinfo.getIndentityType().equals("01")) {
				throw new AppException(200104, "定期理财产品须使用身份证购买");
			}
			if (userinfo.getProductSoldOut() == null || !getNextDay().before(new SimpleDateFormat("yyyy-MM-dd").parse(userinfo.getProductEndTime()))) {
				throw new AppException("产品已过期 请重新选择");
			}
			if (userinfo.getProductSoldOut() == null || userinfo.getProductSoldOut().equals("yes")) {
				throw new AppException(200101, "产品份额不足，请选择其他产品");
			}
			if (userinfo.getProductRemainBalance() == null || userinfo.getProductRemainBalance().compareTo(amount) < 0) {
				throw new AppException(200102, "产品份额不足，请选择其他产品");
			}
			if (userinfo.getProductRaiseProgress().compareTo(BigDecimal.ONE) >= 0) {
				throw new AppException(200101, "产品份额不足，请选择其他产品");
			}
			if (userinfo.getPurchasedAmount() == null || userinfo.getProductCumulativePurchaseAmount() == null || userinfo.getProductCumulativePurchaseAmount().compareTo(userinfo.getPurchasedAmount()) < 0) {
				throw new AppException(200106, "申购金额已超过该产品累计金额上限");
			}
			if (userinfo.getProductSinglePurchaseAmount() == null || userinfo.getProductSinglePurchaseAmount().compareTo(amount) < 0) {
				throw new AppException(200105, "申购金额超过该产品单笔金额上限");
			}
			if (userinfo.getPurchasedAmount() == null || userinfo.getProductCumulativePurchaseAmount() == null || userinfo.getProductCumulativePurchaseAmount().compareTo(userinfo.getPurchasedAmount().add(amount)) < 0) {
				throw new AppException(200106, "申购金额已超过该产品累计金额上限");
			}

			String rateresult = productInfoDubboService.selectProductRateById(userinfo.getLoanProductId());
			if (!NumberUtils.isNumber(rateresult) || new BigDecimal(rateresult).compareTo(new BigDecimal(0)) <= 0) {
				throw new AppException("利率不合法");
			}
			String startTimeStr= DateUtils.formatDate(new Date(),"yyyy-MM-dd");
			List<RfRepayPlanDTO> planresult = mBWjProductProcess.calcRepayPlanByMonthInteresting(amount, userinfo.getProductFirstMonthDay(), new BigDecimal(rateresult).divide(new BigDecimal(10000)),startTimeStr,userinfo.getProductEndTime(), userinfo.getProductRepaymentType());
			JSONArray ja = new JSONArray();
			BigDecimal prinipal = BigDecimal.ZERO;
			BigDecimal interest = BigDecimal.ZERO;
			for (int i = 0; i < planresult.size(); i++) {
				RfRepayPlanDTO item = planresult.get(i);
				JSONObject jo = new JSONObject();
				// 期数
				jo.put("planTerm", item.getPeriod());
				// 还款日 Date
				jo.put("repaymentDate", item.getPlanRepayDateTime());
				// 月供金额
				jo.put("monthTotal", item.getPlanRepayPrincipal().multiply(new BigDecimal(100)).add(item.getPlanRepayInterest().multiply(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
				// 归还本金
				jo.put("shouldPrincipal", item.getPlanRepayPrincipal().multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).longValue());
				// 归还利息
				jo.put("shouldInterest", item.getPlanRepayInterest().multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).longValue());
				ja.add(jo);

				prinipal = prinipal.add(item.getPlanRepayPrincipal().multiply(new BigDecimal(100)));
				interest = interest.add(item.getPlanRepayInterest().multiply(new BigDecimal(100)));
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("principalTotal", prinipal);
			jsonObject.put("loanInterestTotal", interest);
			jsonObject.put("plan", ja);

			// 调用赵峰接口，获取信用额度
			String loanresult = applyFinancingLoanDubboService.applyLoan(userId, userinfo.getLoanProductId(), amount.multiply(new BigDecimal(100)).longValue(), jsonObject.toJSONString());

			String creditOrderId = "";// 信贷系统订单号

			JSONObject json = JSONObject.parseObject(loanresult);
			if (json.getString("code").equals("200")) {
				creditOrderId = json.getJSONObject("data").getString("orderId");
			} else {
				throw new AppException("授信失败:" + json.getString("data"));
			}

			// 冻结la资金， 扣除product可投资余额，生成转账到lf的订单，设定unconfirmed状态
			String orderNo = userAccountTransactionService.applyLaToLf(userId, productId, amount, userinfo.getLockVersion(), token, creditOrderId);

			// 数据
			RfResponseDTO resdto = null;
			resdto = mBWjProductProcess.processOrder(amount, userinfo, orderNo, productId, creditOrderId);
			for (RfRepayPlanDTO item : resdto.getRepayplans()) {
				item.setProductFlag("loan_financial");
			}

			if (resdto.getRepayplans() != null && planresult != null) {
				for (RfRepayPlanDTO item : resdto.getRepayplans()) {
					for (RfRepayPlanDTO item2 : planresult) {
						if (item.getPeriod() == item2.getPeriod()) {
							item.setPlanRepayLoanInterest(item2.getPlanRepayInterest());
							item.setRepayLoanInterest(item2.getRepayInterest());
							break;
						}
					}
				}
			}

			// 处理信贷理财订单
			userAccountTransactionService.processLfOrder(userinfo, resdto, token);

			// 返回内容
			dto.setRemainingInvestment(userinfo.getProductRemainBalance().subtract(amount).toString());
			StringBuilder result = new StringBuilder();
			result.append("每月").append(resdto.getRepayplans().get(0).getPlanRepayDateTime().getDate()).append("日还款，");
			result.append(userinfo.getProductEndTime()).append("到期");
			dto.setPaymentInformation(result.toString());

			BaseLogger.audit("申购信贷理财产品成功  userId[" + userId + "] applyMoney[" + applyMoney + "] productId[" + productId + "]");
		} catch (Exception e) {
			if (e instanceof AppException) {
				throw (AppException) e;
			} else {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("applyLaToLf 异常，userId[" + userId + "] exception:" + e.getMessage()));
			}
			throw new AppException("applyLaToLf 异常，userId[" + userId + "] exception:" + e.getMessage());
		} finally {
			// 2. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
			BaseLogger.audit("unlock applyLaToLf userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]");
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("applyLaToLf 异常，userId[" + userId + "]"));
			}
		}

		// 输出日志
		BaseLogger.audit("end applyLaToLf userId[" + userId + "] applyMoney[" + applyMoney + "]");
		// 计算下确认份额日期，收益到账日期
		return dto;
	}

	/**
	 * 更新理财金授信额度
	 *
	 * @param userId
	 */
	public void updateLnAvailableAmount(String userId) {
//		try {
//			// 主业务逻辑
//			BigDecimal remaincount = userAccountLfMapper.selectCreditRemainCount(userId);
//			Date lastUpdateTime = userAccountLfMapper.selectCreditIncreasingTimeFlag(userId);
//			Calendar ca = Calendar.getInstance();
//			ca.add(Calendar.MONTH, -1);
//			Date lastMonthTime = ca.getTime();
//
//			if (lastUpdateTime == null || lastUpdateTime.before(lastMonthTime)) {
//				lastUpdateTime = lastMonthTime;
//				remaincount = BigDecimal.ZERO;
//			}
//
//			BigDecimal amount = userAccountLfMapper.selectAddUpInvestmentAmount(userId, lastUpdateTime);
//
//			if (amount != null) {
//
//				if (remaincount != null) {
//					amount = amount.add(remaincount);
//				}
//				String loanresult = applyFinancingLoanDubboService.investmentTargetValue();
//				JSONObject json = JSONObject.parseObject(loanresult);
//				String const1 = json.getString("investment_target_value");
//
//				if (!NumberUtils.isNumber(const1)) {
//					throw new AppException("数据不合法");
//				}
//
//				BigDecimal const10 = new BigDecimal(const1).divide(new BigDecimal(100));
//				while (amount.compareTo(const10) >= 0) {
//					// 调用赵峰接口，增加额度
//					taskDetailsInfoDubboService.financialQuotaTaskFinish(userId);
//					userAccountLfMapper.updateCreditIncreasingTimeFlag(userId, amount.subtract(const10));
//					System.out.println(userId + "---" + amount.subtract(const10));
//					amount = amount.subtract(const10);
//
//				}
//			}
//		} catch (Exception e) {
//			throw new AppException("updateLnAvailableAmount 异常，userId[" + userId + "] exception:" + e.getMessage());
//		}
	}

	@Override
	public BigDecimal calProductGains(String productId, String applyMoney) {
		// TODO Auto-generated method stub
		MBUserInfoDTO userinfo = new MBUserInfoDTO();
		userinfo.setProductReceiveRate(new BigDecimal(0.068));
		userinfo.setProductEndTime("2016-04-19");
		userinfo.setProductRepaymentType("monthly_interest");

		if (!NumberUtils.isNumber(applyMoney) || new BigDecimal(applyMoney).compareTo(new BigDecimal(0)) <= 0) {
			throw new AppException("金额不合法");
		}

		BigDecimal amount = new BigDecimal(applyMoney).setScale(2, BigDecimal.ROUND_FLOOR);

		RfResponseDTO resdto = null;
		resdto = mBWjProductProcess.processOrder(amount, userinfo, "", productId, "");

		BigDecimal result = BigDecimal.ZERO;// ONE.add(monthRating)

		if (resdto.getRepayplans() != null) {
			for (RfRepayPlanDTO item : resdto.getRepayplans()) {
				result = result.add(item.getPlanRepayInterest()).add(item.getGreenhornInterest()).add(item.getOtherFee());
			}
		}
		return result;
	}

	@Override
	public String trialRepayPlan(String productId, Long applyAmount) {
		BaseLogger.info("产品：" + productId);
		BaseLogger.info("金额：" + applyAmount);

		BigDecimal amount = new BigDecimal(applyAmount).divide(new BigDecimal(100));

		// 查询用户产品相关数据
		MBUserInfoDTO userinfo = userAccountLfMapper.queryProductInfoByProductid(productId);

		String rateresult = productInfoDubboService.selectProductRateById(userinfo.getLoanProductId());
		if (!NumberUtils.isNumber(rateresult) || new BigDecimal(rateresult).compareTo(new BigDecimal(0)) <= 0) {
			throw new AppException("利率不合法");
		}
		String startTimeStr= DateUtils.formatDate(new Date(),"yyyy-MM-dd");
		List<RfRepayPlanDTO> planresult = mBWjProductProcess.calcRepayPlanByMonthInteresting(amount, userinfo.getProductFirstMonthDay(),
				new BigDecimal(rateresult).divide(new BigDecimal(10000)),startTimeStr, userinfo.getProductEndTime(), userinfo.getProductRepaymentType());
		BigDecimal prinipal = BigDecimal.ZERO;
		BigDecimal interest = BigDecimal.ZERO;
		for (int i = 0; i < planresult.size(); i++) {
			RfRepayPlanDTO item = planresult.get(i);
			prinipal = prinipal.add(item.getPlanRepayPrincipal().multiply(new BigDecimal(100)));
			interest = interest.add(item.getPlanRepayInterest().multiply(new BigDecimal(100)));
		}

		List<RfRepayPlanDTO> planresult2 = mBWjProductProcess.calcRepayPlanByMonthInteresting(amount, userinfo.getProductFirstMonthDay(),
				userinfo.getProductReceiveRate(),startTimeStr, userinfo.getProductEndTime(), userinfo.getProductRepaymentType());
		BigDecimal prinipal2 = BigDecimal.ZERO;
		BigDecimal interest2 = BigDecimal.ZERO;
		for (int i = 0; i < planresult2.size(); i++) {
			RfRepayPlanDTO item = planresult2.get(i);
			prinipal2 = prinipal2.add(item.getPlanRepayPrincipal().multiply(new BigDecimal(100)));
			interest2 = interest2.add(item.getPlanRepayInterest().multiply(new BigDecimal(100)));
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("financialInterestTotal", interest2.setScale(0, BigDecimal.ROUND_DOWN).longValue());
		jsonObject.put("lastRepaymentDateStr", planresult.get(planresult.size() - 1).getPlanRepayDateTime());
		jsonObject.put("loanInterestTotal", interest.setScale(0, BigDecimal.ROUND_DOWN).longValue());
		jsonObject.put("periods", planresult.size());
		jsonObject.put("principalTotal", prinipal.setScale(0, BigDecimal.ROUND_DOWN).longValue());
		jsonObject.put("repaymentMethod", userinfo.getProductRepaymentType());

		return jsonObject.toJSONString();
	}

	public String rfInvestProgress(String userId) {
		try {
			// 主业务逻辑
			BigDecimal remaincount = userAccountLfMapper.selectCreditRemainCount(userId);
			Date lastUpdateTime = userAccountLfMapper.selectCreditIncreasingTimeFlag(userId);
			Calendar ca = Calendar.getInstance();
			ca.add(Calendar.MONTH, -1);
			Date lastMonthTime = ca.getTime();

			if (lastUpdateTime == null || lastUpdateTime.before(lastMonthTime)) {
				lastUpdateTime = lastMonthTime;
				remaincount = BigDecimal.ZERO;
			}

			BigDecimal amount = userAccountLfMapper.selectAddUpInvestmentAmount(userId, lastUpdateTime);

			String loanresult = applyFinancingLoanDubboService.investmentTargetValue();
			JSONObject json = JSONObject.parseObject(loanresult);
			String const1 = json.getString("investment_target_value");

			if (!NumberUtils.isNumber(const1)) {
				throw new AppException("数据不合法");
			}

			if (amount != null) {

				if (remaincount != null) {
					amount = amount.add(remaincount);
				}

				BigDecimal const10 = new BigDecimal(const1).divide(new BigDecimal(100));
				while (amount.compareTo(const10) >= 0) {
					amount = amount.subtract(const10);
				}

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("schedule", amount.divide(const10).setScale(2, BigDecimal.ROUND_DOWN));

				long needInvestmentAmount = const10.subtract(amount).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).longValue();
				if (needInvestmentAmount == 0) {
					jsonObject.put("needInvestmentAmount", const10);
				} else {
					jsonObject.put("needInvestmentAmount", needInvestmentAmount);
				}

				return jsonObject.toJSONString();
			} else {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("code", "200");
				jsonObject.put("schedule", "0");
				jsonObject.put("needInvestmentAmount", new BigDecimal(const1).divide(new BigDecimal(100)).subtract(remaincount).multiply(new BigDecimal(100)).longValue());
				return jsonObject.toJSONString();
			}
		} catch (Exception e) {
			throw new AppException("updateLnAvailableAmount 异常，userId[" + userId + "] exception:" + e.getMessage());
		}

	}

	@Override
	public MBRechargeDTO rechargeSendMsgForWithhold(String userId, String cardId, String amount) {
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

	@Override
	public String doRechargeToMaAlreadyBindForWithhold(String userId, String cardId,String tradeNo, String bizOrderNo, String amount, String code) {
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

			//判断是否生成过充值流水，如果已经有了，不可重复生成
			boolean existTradeRecord = tradeRecordMapper.countTradeRecordExitsByTradeNo(tradeNo)>0?true:false;
			// TODO 初始化交易流水信息tradeNo bizOrderNo
			if(!existTradeRecord){
				tradeRecordMapper.insertTradeRecord(tradeNo, new BigDecimal(amount),  bizOrderNo,rechargeDTO.getCardNo(),null);
			}
			// TODO 初始化交易流水信息tradeNo bizOrderNo
			PayResultDTO payResultDTO = b2CPayGatewayService.confirmPay(paramDTO);

			// //扣钱调用支付接口（保存支付历史（流水））
			if (!payResultDTO.getResult()) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("rechargeToMaAlreadyBindCardSendCode 已经绑卡确认充值发逻辑异常，userId[" + rechargeDTO.getUserId() + "] 错误信息[" + payResultDTO.getResultMessage()));
				throw new AppException("银行系统繁忙，请重新尝试充值");
			}

			tradeRecordMapper.updateTradePlatformAndBizOrder(tradeNo,payResultDTO.getPlatformId(), null);

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

	/**
	 * App端主动轮训请求结果
	 */
	@Override
	public Map<String, String> queryTradeResultNotice(String userId, String tradeNo) {
		BaseLogger.audit("回调查询-->queryTradeResultNotice-->in--");
		// 是否需要执行回调业务(应该是查询第三方,更新状态后才需要回调)
		Map<String, String> resultMap = Maps.newHashMap();

		// 1 检查参数不能为空
		queryRechargeNoticeValidate(userId, tradeNo);
		// 1.查询本地交易状态 SF -->返回交易状态
		TradeRecordBO tradeRecordBO = tradeRecordMapper.queryTradeRecordByTradeNo(tradeNo);
		if (tradeRecordBO == null) {
			BaseLogger.error("orderNo[" + tradeNo + "]订单不存在!");
			throw new AppException("订单信息不存在!");
		}
		// 成功
		if (TradeStatusEnum.TRADE_SUCCESS.getValue().equals(tradeRecordBO.getTradeStatus())) {
			resultMap.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM);
			resultMap.put("message", "充值成功!");
		}

		if (TradeStatusEnum.TRADE_FAILED.getValue().equals(tradeRecordBO.getTradeStatus())) {
			resultMap.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_FAIL);
			resultMap.put("message", "充值失败!");
		}

		// 1.2process-->调用查询
		try{
			SingleStatusResult result = modifyTransStatus(tradeNo, tradeRecordBO.getRelBizOrderNo());

			if (result.getResultCode().equals(SingleStatusResult.SUCCESS)) {
				resultMap.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM);
				resultMap.put("message", "充值成功!");
			} else if(result.getResultCode().equals(SingleStatusResult.FAILED)){
				resultMap.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_FAIL);
				resultMap.put("message", "充值失败!");
			}else if(result.getResultCode().equals(SingleStatusResult.PROCESS)){
				resultMap.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_DISPOSE);
				resultMap.put("message", "充值处理中...");
			}
		}catch(Exception ex){
			resultMap.put("code", MBOrderInfoDTO.ORDER_STATUS_RECHARGE_MA_CONFIRM_FAIL);
			resultMap.put("message", "充值失败!");
		}
		return resultMap;
	}

	public Object queryWithholdTradeResult() {
		// 1. 查询本地交易状态

		// 1.2 S -->调用回调业务系统查询接口

		// 1.3 F--->返回失败

		// 1.4 P--->返回交易处理中
		return null;
	}

	/**
	 * 修改交易状态
	 *
     * @param tradeNo 交易流水号
	 * @return
	 */
	@Override
	public SingleStatusResult modifyTransStatus(String tradeNo, String orderNo) {
		SingleStatusResult singleStatusResult = new SingleStatusResult();
		if (StringUtils.isBlank(tradeNo)) {
			singleStatusResult.setResultCode(SingleStatusResult.FAILED);
			singleStatusResult.setResultCode("tradeNo为空!");
			return singleStatusResult;
		}
		try {
			// 1.查询本地交易process
			// 1.查询本地交易状态 SF -->返回交易状态
			TradeRecordBO tradeRecordBO = tradeRecordMapper.queryTradeRecordByOrderNo(orderNo);
			if(!tradeRecordBO.getTradeStatus().equals(TradeStatusEnum.TRADE_PROCESS.getValue())){
				singleStatusResult.setResultCode(tradeRecordBO.getTradeStatus().equals(TradeStatusEnum.TRADE_SUCCESS.getValue())?SingleStatusResult.SUCCESS:SingleStatusResult.FAILED);
				singleStatusResult.setResultMsg(tradeRecordBO.getTradeStatus().equals(TradeStatusEnum.TRADE_SUCCESS.getValue())?"处理成功":"处理失败");
				return singleStatusResult;
			}

			// 查询第三方
			PayResultDTO payResultDTO = noticeChannelQueryOrder(tradeNo);
			// 查询接口调用失败
			if (!payResultDTO.getResult()) { // 请求失败
				singleStatusResult.setResultCode(SingleStatusResult.PROCESS);
				singleStatusResult.setResultMsg("银行系统接口繁忙");
				return singleStatusResult;
			}
			com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO rechargeDTO = payResultDTO.getRechargeDTO();

			String status = rechargeDTO.getStatus();
			BaseLogger.audit("回调查询-->queryRechargeNotice-->status[" + status + "],'10'为成功,'20'为失败");
			// 2.1.1为充值处理中状态,返回结果
			if (!"20".equals(status) && !"10".equals(status)) {
				singleStatusResult.setResultCode(SingleStatusResult.PROCESS);
				singleStatusResult.setResultMsg("第三方系统返回结果为处理中...");
				return singleStatusResult;
			} else if ("20".equals(status) || "10".equals(status)) { // 不是成功就是失败
				boolean invokeResult = withholdServiceFacade.confirmPaymentTradeStatusAndCallbackWithholdOperate(tradeNo, tradeRecordBO.getTotalPrice(), "10".equals(status) ? true : false);
				singleStatusResult.setResultCode(invokeResult ? SingleStatusResult.SUCCESS : SingleStatusResult.FAILED);
				singleStatusResult.setPrice(tradeRecordBO.getPrice().toString());
				singleStatusResult.setResultMsg(invokeResult ? "业务处理成功" : "业务处理失败");
				return singleStatusResult;
			} else {
				BaseLogger.error("modifyTransStatus非法的请求状态:[" + status + "]");
				singleStatusResult.setResultCode(SingleStatusResult.FAILED);
				singleStatusResult.setResultMsg("不合法的状态[" + status + "]");
				return singleStatusResult;
			}
		} catch (Exception e) {
			singleStatusResult.setResultCode(SingleStatusResult.FAILED);
			singleStatusResult.setResultMsg("系统异常[" + e.getMessage() + "]");
			return singleStatusResult;
		}
	}

	/**
	 * @param params
	 * @return
	 * @throws Exception
     * @title 用户已购定期理财产品，提前到期生成计划
	 */
	public List<PreExpireRfPaymentResp> preExpireRfPayment(List<EarlyExpireRepayPlanDTO> params){
		BaseLogger.audit("start preExpireRfPayment start.");
		if(params==null||params.isEmpty()){
			BaseLogger.audit("start preExpireRfPayment params is empty.");
			return new ArrayList<PreExpireRfPaymentResp>();
		}
		List<PreExpireRfPaymentResp> rests=new ArrayList<PreExpireRfPaymentResp>();
		for(EarlyExpireRepayPlanDTO dto:params){
			PreExpireRfPaymentResp preExpireRfPaymentResp=new PreExpireRfPaymentResp();
			preExpireRfPaymentResp.setPreExpireStatus(PreExpireRfPaymentResp.EXPIRE_STATUS_SUCCESS);
			preExpireRfPaymentResp.setOrderNo(dto.getOrderNo());
			preExpireRfPaymentResp.setProductId(dto.getProductId());
			preExpireRfPaymentResp.setUserId(dto.getUserId());
			preExpireRfPaymentResp.setRepaymentPlanId(dto.getRepaymentPlanId());
			try {
				preExpireRfPayment(dto);
			} catch (Exception e) {
				BaseLogger.error("preExpireRfPayment 异常，userId[" + dto.getUserId() +"]",e);
				preExpireRfPaymentResp.setPreExpireStatus(PreExpireRfPaymentResp.EXPIRE_STATUS_FAIL);
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("preExpireRfPayment 异常，userId[" + dto.getUserId() + "] exception:" + e.getMessage()));
			}
			rests.add(preExpireRfPaymentResp);
		}
		BaseLogger.audit("start preExpireRfPayment end.");
		return  rests;
	}

	/**
	 * @param param
	 * @return
	 * @throws Exception
     * @title 用户已购定期理财产品，提前到期生成计划
	 */
	@Transactional
	private void preExpireRfPayment(EarlyExpireRepayPlanDTO param) {
		BaseLogger.audit("start preExpireRfPayment start. param:"+param.toString());
		// 查询用户产品相关数据
		MBUserInfoDTO userinfo=null;
		Date endDate;
		try {
			endDate = DateUtils.parseDate(param.getRepaymentTime(), new String[]{"yyyy-MM-dd"});
		} catch (DateParseException e) {
			throw new AppException("日期报错");
		}
		BigDecimal planRepayLoanInterest=BigDecimal.ZERO;
		if("loan_financial".equals(param.getProductCategory())){
			userinfo = userAccountLfMapper.queryUserInfoByUserid(param.getUserId(), param.getProductId());
			BaseLogger.audit("start preExpireRfPayment start. loanProductId:"+userinfo.getLoanProductId());
			String rateResult = productInfoDubboService.selectProductRateById(userinfo.getLoanProductId());
			if (!NumberUtils.isNumber(rateResult) || new BigDecimal(rateResult).compareTo(new BigDecimal(0)) <= 0) {
				throw new AppException("利率不合法");
			}
			List<RfRepayPlanDTO> planResult = mBWjProductProcess.calcRepayPlanByMonthInteresting(new BigDecimal(param.getAmount()), userinfo.getProductFirstMonthDay(),
					new BigDecimal(rateResult).divide(new BigDecimal(10000)),param.getInterestStartTime(),param.getInterestEndTime(), userinfo.getProductRepaymentType());

			BigDecimal shouldPrincipal=BigDecimal.ZERO;
			BigDecimal shouldInterest=BigDecimal.ZERO;
			for(RfRepayPlanDTO dto:planResult){
				planRepayLoanInterest=planRepayLoanInterest.add(dto.getPlanRepayInterest());
				shouldPrincipal=shouldPrincipal.add(dto.getPlanRepayPrincipal().multiply(new BigDecimal(100)));
				shouldInterest=shouldInterest.add(dto.getPlanRepayInterest().multiply(new BigDecimal(100)));
			}
			shouldPrincipal=shouldPrincipal.subtract(new BigDecimal(param.getRepayPrincipal()).multiply(new BigDecimal(100)));
			shouldInterest= shouldInterest.subtract(new BigDecimal(param.getRepayLoanInterest()).multiply(new BigDecimal(100)));
			JSONArray ja = new JSONArray();
			JSONObject jo = new JSONObject();
			// 期数
			jo.put("planTerm",param.getTerm());
			// 还款日 Date
			jo.put("repaymentDate",endDate);
			// 月供金额
			jo.put("monthTotal",shouldPrincipal.add(shouldInterest).setScale(0, BigDecimal.ROUND_HALF_UP).longValue());
			// 归还本金
			jo.put("shouldPrincipal", shouldPrincipal.setScale(0, BigDecimal.ROUND_DOWN).longValue());
			// 归还利息
			jo.put("shouldInterest", shouldInterest.setScale(0, BigDecimal.ROUND_DOWN).longValue());
			jo.put("advExpireTime",endDate);
			ja.add(jo);
			JSONObject planJo = new JSONObject();
			planJo.put("plan",ja);
			String loanOrderId=userAccountLfMapper.queryLoanOrderByOrderNo(param.getOrderNo());
			applyFinancingLoanDubboService.rebuildRepayPlan(loanOrderId,planJo.toJSONString());
		}else{
			userinfo = userAccountRfMapper.queryUserInfoByUserid(param.getUserId(), param.getProductId());
		}
		RfRepayPlanDTO rfRepayPlanDTO = mBWjProductProcess.generateAdvExpireOrder(new BigDecimal(param.getAmount()), userinfo, param.getOrderNo(),
				param.getInterestStartTime(), param.getInterestEndTime());

		rfRepayPlanDTO.setIfForzen(param.getIfForzen());
		rfRepayPlanDTO.setProductCategory(param.getProductCategory());
		rfRepayPlanDTO.setProductFlag(param.getProductFlag());
		rfRepayPlanDTO.setPeriod(param.getTerm());
		rfRepayPlanDTO.setTotalperiod(param.getTerm());
		rfRepayPlanDTO.setRepaymentPlanId(param.getRepaymentPlanId());
		rfRepayPlanDTO.setPlanRepayDateTime(endDate);
		rfRepayPlanDTO.setPlanRepayInterest(rfRepayPlanDTO.getPlanRepayInterest().subtract(new BigDecimal(param.getRepayInterest())));
		rfRepayPlanDTO.setPlanRepayPrincipal(rfRepayPlanDTO.getPlanRepayPrincipal().subtract(new BigDecimal(param.getRepayPrincipal())));
		if(planRepayLoanInterest.compareTo(BigDecimal.ZERO)>0){
			rfRepayPlanDTO.setPlanRepayLoanInterest(planRepayLoanInterest.subtract(new BigDecimal(param.getRepayLoanInterest())));
		}else{
			rfRepayPlanDTO.setPlanRepayLoanInterest(BigDecimal.ZERO);
		}
		//生成还款计划
		List<RfRepayPlanDTO> rfRepayPlanDTOs=new ArrayList<RfRepayPlanDTO>();
		rfRepayPlanDTOs.add(rfRepayPlanDTO);
		userAccountRfMapper.addUserForzenInvestRepaymentPlan(rfRepayPlanDTOs);
		BaseLogger.audit("end preExpireRfPayment userId[" + param.getUserId() + "] applyMoney[" + param.getRepayPrincipal() + "]");
	}

	/**
	 * 代扣转入余额再转入存钱罐
     *
	 * @param orderNo
	 * @param userId
	 * @param applyMoney
     */
	@Override
	public void applyWithholdToTa(String orderNo, String userId, String applyMoney){
		BaseLogger.audit("start applyWithholdToTa userId[" + userId + "] withdrawMoney[" + applyMoney + "]");
		// 校验参数
		BigDecimal amount = confirmUserIsExitAndMoneyIsValid(userId, applyMoney);
		if (amount.compareTo(new BigDecimal("100")) < 0) {
			throw new AppException("金额不合法");
		}
		if (applyMoney.lastIndexOf(".") != -1 && (StringUtil.isNullString(applyMoney.substring(applyMoney.lastIndexOf(".") + 1)) || Integer.valueOf(applyMoney.substring(applyMoney.lastIndexOf(".") + 1)) > 0)) {
			throw new AppException("金额不合法");
		}
		// 1.0 检测T金所剩余投资金额是否足够
		checkTfaxIsEnough(userId, applyMoney);
		// 主业务逻辑
		// 2 检测余额是否够转账
		checkAccountIsEnoughToTransfer(userId, UserConstant.TRADETYPE_MA,amount);
		// 3 转账
		userAccountTransactionService.applyMaToTa(userId, amount);
		BaseLogger.audit("end applyWithholdToTa userId[" + userId + "] withdrawMoney[" + applyMoney + "]");
	}

	/**
	 * 京东充值回调
	 */
	@Override
	public String jingdongNotice(String resp) {
		BaseLogger.audit("-->jingdongNoticeCallBack-->start--");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", resp);
		map.put("type", "40");// 京东支付
		
		PayNoticeDTO payNoticeDTO = payNoticeResolve(map, NOTICE_TYPE_JING_DONG);

		BaseLogger.audit("-->jingdongNoticeCallBack-->end--");
		// 响应结果返回
		if(payNoticeDTO.isLocalResult()){
			return "success";
		}else{
			return "fail";
		}
	}

	@Override
	public List<UserTradeAccountInfoDTO> queryUserIdAndAvailableAmountList(String tradeType,String amount) {
		return userAccountMapper.queryUserIdAndAvailableAmountList(tradeType,amount);
	}

    @Override
    public String jdPayWithholdNotice(String resp) {
        BaseLogger.audit("---->【京东支付】代扣回调-->jdPayWithholdNotice-->in--");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", resp);
        map.put("type", NOTICE_TYPE_JD_PAY + "");//京东代扣60

        payNoticeResolve(map, NOTICE_TYPE_JD_PAY);

        // 响应结果返回（必须以success开头）
        return "success";
    }

	@Override
	public Boolean isBindCardForWithHoldInLocal(String userId, String cardNo, String channelCode) {
		// TODO Auto-generated method stub
		try{
			return b2CPayGatewayService.isCardBindingForLocal(userId,cardNo,channelCode);
		}catch(Exception ex){
			BaseLogger.error("查询是否在本地绑卡接口错误-"+ex.getMessage());
			throw new AppException("查询是否在本地绑卡接口错误-"+ex.getMessage());
		}
    }
	
	
	
	
	public MBRechargeDTO bindCardSendCodeNoRegister(MBRechargeDTO rechargeDTO) {
		BaseLogger.audit("-->MBUserAccountService-->bindCardSendCodeNoRegister-->start--");
		// 校验参数
//		if (!rechargeDTO.validateNoRegisterBindCardSendCodeParameter()) {
//			throw new AppException(rechargeDTO.getMessage());
//		}

        Boolean status = userAccountMapper.queryWithholdBankStatusByBankCode(rechargeDTO.getBankCode(), rechargeDTO.getOrderType());
        if (status == null || !status) {//银行不可用或维护
            throw new AppException("银行不可用或维护中");
        }

		// 校验卡bin(银行卡号与银行名是否匹配)
		this.validateBankCardBin(rechargeDTO.getCardNo(), rechargeDTO.getBankCode());

		// 1. 生成 card id
		String cardId = userAccountTransactionService.getId("AC", ISequenceService.SEQ_NAME_CARD_SEQ);
		String userId = rechargeDTO.getUserId();
		if(StringUtils.isBlank(rechargeDTO.getUserId())){
			userId = "US" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequenceService.getNextStringValue(ISequenceService.SEQ_NAME_USER_ID_SEQ, 10);
		}
	    
		// 2. 发送验证短信
		try {
			com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO paramDTO = new com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO();
			BeanUtils.copyProperties(paramDTO, rechargeDTO);
			paramDTO.setCustomerId(cardId);
			paramDTO.setUserId(userId);

            //mock数据
			SmsResultDTO smsResultDTO = b2CPayGatewayService.bindCard(paramDTO);

			MBRechargeDTO mBRechargeDTO = new MBRechargeDTO();
			mBRechargeDTO.setCardId(cardId);
			mBRechargeDTO.setOrderNo(smsResultDTO.getOrderNo());
			mBRechargeDTO.setCertType(paramDTO.getCertType());
			mBRechargeDTO.setUserId(userId);
//            mBRechargeDTO.setRemainTime(StringUtils.isBlank(smsResultDTO.getRemainTime()) ? "120" : smsResultDTO.getRemainTime());
            mBRechargeDTO.setRemainTime("60");
			BaseLogger.audit("-->MBUserAccountService-->bindCardSendCodeNoRegister-->end--");
			return mBRechargeDTO;
		} catch (AppException e) {
			BaseLogger.error("绑卡发验证码异常", e);
            String msg = e.getMessage();
			if (StringUtils.isBlank(msg)) {
				throw new AppException("请核对银行卡与身份证信息");
			}
			throw e;
		} catch (Exception e) {
			BaseLogger.error("绑卡发验证码异常", e);
			throw new AppException("银行系统繁忙，请稍后再试");
		}
	}

	public Boolean confirmBindCardSendCodeNoRegister(MBRechargeDTO rechargeDTO) {
		BaseLogger.audit("-->MBUserAccountService-->confirmBindCardSendCodeNoRegister-->start--");
		// 1.验证前台数据
		if (!rechargeDTO.validateDoBindCardParameter()) {
			throw new AppException(rechargeDTO.getMessage());
		}
        Boolean status = userAccountMapper.queryWithholdBankStatusByBankCode(rechargeDTO.getBankCode(), rechargeDTO.getOrderType());
        if (status == null || !status) {//银行不可用或维护
            throw new AppException("银行不可用或维护中");
        }
		
		try {
			com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO paramDTO = new com.zhuanyi.vjwealth.wallet.service.balance.common.dto.MBRechargeDTO();
			BeanUtils.copyProperties(paramDTO, rechargeDTO);

			//确认绑卡，失败抛异常
			SmsResultDTO smsResultDTO = b2CPayGatewayService.confirmBindCard(paramDTO);
			
            BaseLogger.audit("-->MBUserAccountService-->confirmBindCardSendCodeNoRegister-->end--");
			return Boolean.TRUE;
		} catch (AppException e) {
			BaseLogger.error("未绑卡确认充值异常;", e);
            String msg = e.getMessage();
			if (StringUtils.isBlank(msg)) {
				throw new AppException("银行系统繁忙，请重新尝试绑卡");
			}
			throw e;
		} catch (Exception e) {
			BaseLogger.error("未绑卡确认异常", e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("confirmBindCardSendCode 未绑卡确认异常，userId[" + rechargeDTO.getUserId() + "]exception :" + e.getMessage()));
			throw new AppException("银行系统繁忙，请稍后再试");
		}
	}  
	@Override
	@Transactional
	public Object applyMaToRfIps(String userId, String applyMoney, String productId, String rpId, String couponId,
			String clientType, String token,String source) {

		BaseLogger
				.audit("start applyMaToRf userId[" + userId + "] withdrawMoney[" + applyMoney + "] rpId[" + rpId + "]");
		// 校验参数
		BigDecimal amount = confirmUserIsExitAndMoneyIsValid(userId, applyMoney).setScale(0, BigDecimal.ROUND_FLOOR);
		// 调用红包服务，判断红包是否可用
		// 1、判断红包是否可用接口
		// 1.1、判断红包是否有效
		// 1.2、判断红包是否归属这个人
		// 1.3、判断红包是否可以投此理财产品
		// 入参：userId、productId、rpId
		// 出参：红包详细信息或者异常
		// 取出红包,做校验使用
		Map ipsRes = new HashMap();

		if (StringUtils.isNotBlank(rpId))
			this.getPackage(userId, applyMoney, rpId, productId);

		// 取出加息券,做校验使用
		/*
		 * if(StringUtils.isNotBlank(couponId)) this.getCoupons(userId,
		 * couponId, productId);
		 */
		// 1. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}

		try {
			// 加锁后，再查一次红包的状态，避免并发问题
			// TODO ..在次调用
			// rpInfo = userCouponService.queryCashCouponCanUse(userId,
			// productId, rpId, clientType, applyMoney);

			// 主业务逻辑
			// 查询用户产品相关数据
			MBUserInfoDTO userinfo = userAccountRfMapper.queryUserInfoByUserid(userId, productId);

			if (userinfo == null) {
				throw new AppException("产品不存在 请重新选择");
			}
			if (userinfo.getProductFlag().equals("greenhorn") && userinfo.getIfPurchaseProduct().equals("yes")) {
				throw new AppException("该产品仅限新手用户购买");
			}
			int tokencount = userAccountRfMapper.queryProductOrderByToken(token);
			if (tokencount > 0) {
				throw new AppException("请勿重复下单");
			}
			// 校验购买金额是否符合产品startmoney，increasemoney标准
			if (userinfo.getProductStartmoney() != null && userinfo.getProductIncreaseMoney() != null) {
				confirmRfApplyMoneyIsValid(amount, userinfo.getProductStartmoney(), userinfo.getProductIncreaseMoney());
			}
			// 校验
			if (userinfo.getIndentityType() == null || !userinfo.getIndentityType().equals("01")) {
				throw new AppException(200104, "定期理财产品须使用身份证购买");
			}
			if (userinfo.getProductSoldOut() == null
					|| !getNextDay().before(new SimpleDateFormat("yyyy-MM-dd").parse(userinfo.getProductEndTime()))) {
				throw new AppException("产品已过期 请重新选择");
			}
			if (userinfo.getMaAvailableAmount() == null || userinfo.getMaAvailableAmount().compareTo(amount) < 0) {
				throw new AppException(200103, "账户可用余额不足 请充值");
			}
			if (userinfo.getProductSoldOut() == null || userinfo.getProductSoldOut().equals("yes")) {
				throw new AppException(200101, "产品份额不足，请选择其他产品");
			}
			if (userinfo.getProductRemainBalance() == null
					|| userinfo.getProductRemainBalance().compareTo(amount) < 0) {
				throw new AppException(200102, "产品份额不足，请选择其他产品");
			}
			if (userinfo.getProductRaiseProgress().compareTo(BigDecimal.ONE) >= 0) {
				throw new AppException(200101, "产品份额不足，请选择其他产品");
			}
			if (userinfo.getPurchasedAmount() == null || userinfo.getProductCumulativePurchaseAmount() == null
					|| userinfo.getProductCumulativePurchaseAmount().compareTo(userinfo.getPurchasedAmount()) < 0) {
				throw new AppException(200106, "申购金额已超过该产品累计金额上限");
			}
			if (userinfo.getProductSinglePurchaseAmount() == null
					|| userinfo.getProductSinglePurchaseAmount().compareTo(amount) < 0) {
				throw new AppException(200105, "申购金额超过该产品单笔金额上限");
			}
			if (userinfo.getPurchasedAmount() == null || userinfo.getProductCumulativePurchaseAmount() == null
					|| userinfo.getProductCumulativePurchaseAmount()
							.compareTo(userinfo.getPurchasedAmount().add(amount)) < 0) {
				throw new AppException(200106, "申购金额已超过该产品累计金额上限");
			}

			String tradeId = userAccountTransactionService.getId("01", ISequenceService.SEQ_NAME_TRADE_SEQ);// 生成小赢唯一交易流水号

			if (StringUtils.isNotBlank(rpId)) {
				BigDecimal rpValue = this.getPackage(userId, applyMoney, rpId, productId);
				// 红包部分，冻结金额的去掉，具体在applyMaToRf里面处理

				// ips介入
				IpsPacketFrozen ipsp = new IpsPacketFrozen();
				IpsPacketFrozenBid bid = new IpsPacketFrozenBid();
				IpsPacketFrozenRedPacket pack = new IpsPacketFrozenRedPacket();

				SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
				String date = sim.format(new Date());

				// 根据userId得到ipsAcctNo
				Map<String, Object> ipsAcct = userAccountRfMapper.queryUserIpsAcctNo(userId);
				String ipsAcctNo = (String) ipsAcct.get("ipsAcctNo");
				// 主参数
				ipsp.setProjectNo(productId);// 前台
				ipsp.setMerDate(date);
				ipsp.setRegType("1");
				// 到时候改

				ipsp.setWebUrl(localServerIp+"/api/ips/freeze/IpsfreezeWebCallBack?source="+source);
				ipsp.setS2SUrl(localServerIp+"/api/ips/packetFrozen/s2sCallBackCallBack");

				
				// 红包参数
				String redBillNo = GenerateMerBillNoUtil.getBillNoGenerate();
				pack.setMerBillNo(redBillNo);
				pack.setBizType("9");
				pack.setTrdAmt(rpValue.toString());// 红包的钱
				pack.setFreezeMerType("1");
				pack.setIpsAcctNo(ipsAcctNo);
				// 其他参数
				String bidBillNo = GenerateMerBillNoUtil.getBillNoGenerate();
				bid.setMerBillNo(bidBillNo);
				bid.setBizType("1");
				bid.setTrdAmt(applyMoney);// 前台
				bid.setFreezeMerType("1");
				bid.setIpsAcctNo(ipsAcctNo);
				ipsp.setRedPacket(pack);
				ipsp.setBid(bid);
				// 调用ips
				ipsRes = packetFrozenService.packetFrozen(ipsp);
				ipsRes.put("url",HttpClientUtils.ips_url );
				
				// mapss.put("response", da);
				// 生成红包订单号 数据落地
				userAccountTransactionService.applyMaToRfPacketIps(redBillNo, userId, rpId, productId, amount, rpValue,
						userinfo.getLockVersion(), token, tradeId);
				// 生成其他冻结定单号
				userAccountTransactionService.applyMaToRfBidIps(bidBillNo, userId, productId, amount, rpValue, couponId,
						userinfo.getLockVersion(), token, tradeId);

			}
		} catch (Exception e) {
			if (e instanceof AppException) {
				throw (AppException) e;
			} else {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
						pageEmailMap("applyMaToRf 异常，userId[" + userId + "] exception:" + e.getMessage()));
			}
			throw new AppException("applyMaToRf 异常，userId[" + userId + "] exception:" + e.getMessage());
		} finally {
			// 2. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
			BaseLogger.audit("unlock applyMaToRf userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]");
			/*
			 * if (unlockUpdateCount != 1) {
			 * sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
			 * pageEmailMap("applyMaToRf 异常，userId[" + userId + "]"));
			 * 
			 * // 计算下确认份额日期，收益到账日期
			 * 
			 * }
			 */
		}
		// 输出日志
		BaseLogger.audit("end applyMaToRf userId[" + userId + "] applyMoney[" + applyMoney + "]");
		return ipsRes;
	}



	@Override
	public IPSResponse freezeAccount(String orderId, IpsFreeze ipsFreeze) {
		// TODO Auto-generated method stub
		IPSResponse response = new IPSResponse();

		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("source", "APP");
		hashMap.put("operationType", "trade.freeze");
		hashMap.put("orderId", orderId);

		try {

			String sendJson = JSONUtils.obj2json(ipsFreeze);
			System.out.println(sendJson);
			String ipsRes = HttpClientUtils.ipsPostMethod(Define.OperationType.FREEZE, sendJson);
			hashMap.put("returnJson", ipsRes);

			if (ipsRes != null) {
				response.setCode(IPSResponse.ErrCode.SUCCESS);
				response.setData(ipsRes);
				response.setMsg("成功");
				System.out.println(ipsRes);
				hashMap.put("status", "success");
			} else {
				response.setCode(IPSResponse.ErrCode.UNKNOW);
				response.setMsg("失败");
				hashMap.put("status", "error");
			}

			System.out.println(ipsRes);
		} catch (Exception e) {
			// TODO: handle exception
			response.setCode(IPSResponse.ErrCode.TIME_OUT);
			response.setMsg("连接超时");
			hashMap.put("status", "time_out");
		}
		excuteIpsPlatLogMapper.insertIpsPlatformLogInfor(hashMap);

		System.out.println(response);
		return response;
	}

	
	/**
	 * 处理IPS冻结回调
	 */
	@Override
	@Transactional
	public void freezeCallBack(Map<String, Object> map) {
		// TODO Auto-generated method stub
		//投资理财产品的订单号
			/*	String orderNo = (String) map.get("merBillNo");
				String productId = (String) map.get("projectNo");
				String money = (String) map.get("ipsTrdAmt");
				// 表wj_user_trade_account的主键
				String ipsAcctNo = (String) map.get("ipsAcctNo");
				String ipsBillNo = (String) map.get("ipsBillNo");
				String trdStatus = (String) map.get("trdStatus");
				map.put("title", "下单冻结");
				//下单金额
				BigDecimal amount = new BigDecimal(0);
				
				if(!StringUtils.isBlank(money)){
					amount=new BigDecimal(money);
				}
				
				// 失败
				if ("0".equals(trdStatus)) {
					map.put("trdStatus", "apply_ma_to_rf_failed");

					BaseLogger.audit("=======IPS冻结状态失败=========");
				} else {
					
					BaseLogger.audit("=======IPS冻结状态成功=========");
					// 冻结下单成功
					map.put("trdStatus", "apply_ma_to_rf_confirm");
					
					//根据ipsAcctNo查询用户id 注=====当考虑满标时应该根据productId获取所有投标人的userId
					String userId= userAccountRfMapper.queryUserIdByIpsAcctNo(ipsAcctNo);
					
					//根据订单号查询下单冻结时的金额,校验
					Map<String,String> amountMap=userAccountRfMapper.queryAmountByOrder(userId,orderNo,productId);
					
					//总金额
					String total_Price = amountMap.get("totalPrice");
					
					BigDecimal totalPrice = new BigDecimal(0);
					
					if(!StringUtils.isBlank(total_Price)){
						totalPrice=new BigDecimal(total_Price);
					}
					
					
					//实际交易金额
					String price1 = amountMap.get("price");
					
					BigDecimal price = new BigDecimal(0);
					
					if(!StringUtils.isBlank(price1)){
						price=new BigDecimal(price1);
					}
					
					//无红包状态校验金额 下单金额与IPS返回金额校验
					if(amount.compareTo(totalPrice)!=0&&amount.compareTo(price)!=0){
						BaseLogger.audit("==========ISP冻结回调返回金额不正确=========");
						throw new AppException("ISP冻结回调返回金额不正确");
					}
					
					
					
					int version = userAccountMapper.queryProductInfor(productId);
					
					userAccountTransactionService.ipsApplyMaToRf(userId, productId, amount, version);
					//  ma账户资金冻结，产品份额减少
					
					// 以下调用IPS转账接口===投标即起息
					IpsTransferAccounts transferAccounts = new IpsTransferAccounts();
					String batchNo = "bactno" + GenerateOrderNoUtil.generate();
					transferAccounts.setBatchNo(batchNo);
					transferAccounts.setMerDate(new SimpleDateFormat("yyyy-mm-dd").format(new Date()));
					transferAccounts.setProjectNo(productId);
					transferAccounts.setTransferType("1");
					transferAccounts.setIsAutoRepayment("3");
					transferAccounts.setTransferMode("2");
					transferAccounts.setS2SUrl(localServerIp+"/api/ips/transfer/s2sCallBack");
					//转账明细
					IpsTransferAccountStranferAccDetail accountStranferAccDetail = new IpsTransferAccountStranferAccDetail();
					String merBillNo = GenerateMerBillNoUtil.getBillNoGenerate();
					//每个投标人的商户订单号
					accountStranferAccDetail.setMerBillNo(merBillNo);
					//每个投标人的IPS原冻结订单号
					accountStranferAccDetail.setFreezeId(ipsBillNo);
					//每个投标人的IPS存管账户号
					accountStranferAccDetail.setOutIpsAcctNo(ipsAcctNo);//转出方ips存管账号
					accountStranferAccDetail.setOutMerFee("0");
					
					// ===转入方ips存管账号每次相同  
					//查询代理人的存管账号
					
					String transferInIpsAcctNo = userAccountMapper.queryAgentIpsAcctNo(productId);
					
					
					accountStranferAccDetail.setInIpsAcctNo(transferInIpsAcctNo);
					accountStranferAccDetail.setInMerFee("0");
					accountStranferAccDetail.setTrdAmt(money);
					//转账明细集合
					ArrayList<IpsTransferAccountStranferAccDetail> list = new ArrayList<IpsTransferAccountStranferAccDetail>();
					list.add(accountStranferAccDetail);
					
					transferAccounts.setTransferAccDetail(list);
					//开始调用ips转账接口 
					ipsTransferAccountsService.transferAccounts(userId,transferAccounts);
					BaseLogger.audit("==============开始调用IPS转账接口==================");

				}

				// 更新订单状态
				userAccountMapper.updateIpsOrderStatus(map);
				//
				userAccountMapper.saveFreezeInfor(map);*/
	}

	@Override
	public boolean queryFreezeStatus(String merBillNo, String projectNo) {
		// TODO Auto-generated method stub
          boolean flage= userAccountRfMapper.queryFreezeStatus(merBillNo,projectNo);
		 
		
	    return flage;
		
	}

	
	
	/**
	 * ============处理后管冻结============
	 */
	
	@Override
	public void freezeRepayment(Map<String, Object> map) {
		// TODO Auto-generated method stub
		/*String orderNo = (String) map.get("merBillNo");
		//环迅返回的金额(本息)
		String money = (String) map.get("ipsTrdAmt");
		// 表wj_user_trade_account的主键
		String ipsAcctNo = (String) map.get("ipsAcctNo");
		String ipsBillNo = (String) map.get("ipsBillNo");
		String trdStatus = (String) map.get("trdStatus");
		//后管调用转账接口时，传入的
		String productId = (String) map.get("projectNo");
		map.put("title", "后管红包冻结");
		BigDecimal amount = new BigDecimal(0);
		
		if(!StringUtils.isBlank(money)){
			amount=new BigDecimal(money);
		}
		
		// 失败
		if ("0".equals(trdStatus)) {
			map.put("trdStatus", "manager_ips_freeze_failed");

			BaseLogger.audit("=======后管IPS冻结状态失败=========");
		} else {
			BaseLogger.audit("=======后管IPS冻结状态成功=========");
			map.put("trdStatus", "manager_ips_freeze_confirm");
			
			String userId= userAccountRfMapper.queryUserIdByIpsAcctNo(ipsAcctNo);
			

			//查询买理财时的订单号
			HashMap<String, String> queryProductOrderNo = userAccountMapper.queryProductOrderNo(orderNo);
			
			String ProductOrderNo = queryProductOrderNo.get("ProductOrderNo");
			String planId = queryProductOrderNo.get("planId");
			

			
			
			//根据订单号查询后管调用冻结时的金额,校验,理财产品id，后管调用冻结接口时，也传入productId
			Map<String,String> amountMap=userAccountRfMapper.queryAmountByOrder(userId,orderNo,productId);

			//本息
			String total_Price = amountMap.get("totalPrice");
			
			BigDecimal totalPrice = new BigDecimal(0);
			
			if(!StringUtils.isBlank(total_Price)){
				totalPrice=new BigDecimal(total_Price);
			}
			
			
			//本金
			String price1 = amountMap.get("price");
			
			BigDecimal price = new BigDecimal(0);
			
			if(!StringUtils.isBlank(price1)){
				price=new BigDecimal(price1);
			}
			
			//后管调用冻结，发红包的形式没有手续费
			if(amount.compareTo(totalPrice)!=0&&amount.compareTo(price)!=0){
				BaseLogger.audit("==========后管ISP冻结回调返回金额不正确=========");
				throw new AppException("后管ISP冻结回调返回金额不正确");
			}
			
			
			//后管冻结回调成功
			//1更新用户可用余额ma，叠加用户收益和回款本金 ----- 冻结回调成功处理（本息）
			userAccountMapper.updateUserRepaymentPlanReceive(userId,totalPrice);
			
			//2 更新用户rf帐户，扣减还款计划到期本金----冻结回调成功后处理（本金）
			 userAccountMapper.updateUserRfAccount(userId,planId);
			
			//3.更新用户rf明细帐户，扣减还款计划到期本金----

			userAccountMapper.updateUserRfAccountDetail(userId,ProductOrderNo, productId, planId);


			
			BaseLogger.audit("==============开始调用解冻接口=====================");
			
			IpsUnfreeze ipsUnfreeze = new IpsUnfreeze();
			
			ipsUnfreeze.setFreezeId(ipsBillNo);
			ipsUnfreeze.setIpsAcctNo(ipsAcctNo);
			ipsUnfreeze.setTrdAmt(money);
			
			//========解冻==========
			unfreezeServices.Unfreeze(ipsUnfreeze);
			
		}
		
		
		userAccountMapper.updateFreezeRepaymentOrderStatus(map);
		userAccountMapper.saveFreezeInfor(map);*/
	}
    
	/**
	 * 校验后管同步还款状态
	 */
	@Override
	public boolean queryFreezeRepaymentStatus(String merBillNo) {
		// TODO Auto-generated method stub
		
		boolean flage= userAccountRfMapper.queryFreezeRepaymentOrderStatus(merBillNo);
		return flage;
	}

	
	/**
	 * 提现补单
	 */
	@Override
	public String withdrawDepositOrder(String tradeNo) {
	
		try {
		    BaseLogger.audit("tradeNo[" + tradeNo + "]提现补单开始!");
            if (StringUtils.isBlank(tradeNo))
				throw new AppException("订单号不能为空");
			MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
			MBOrderInfoDTO order = new MBOrderInfoDTO();
			orderInfoDTO.setOrderNo(tradeNo);
			order = userAccountMapper.queryOrderInfoByOrderNo(orderInfoDTO);

			if (order == null) {
				BaseLogger.error("tradeNo[" + tradeNo + "]该订单不存在历史!");
				throw new AppException("该订单不存在历史!");
			} else if (order != null&& MBOrderInfoDTO.ORDER_STATUS_WITHDRAW_MA_CONFIRM.equals(order.getOrderStatus())) {
				BaseLogger.error("tradeNo[" + tradeNo + "]订单已存在!");
				throw new AppException("订单已存在!");
			}
			
			/**
			 * 查询环迅交易信息
			 */
			IpsUserInfoDTO  ipsUserInfoDTO=new IpsUserInfoDTO(); 
			ipsUserInfoDTO.setQuerytype("02");
			ipsUserInfoDTO.setMerBillNo(tradeNo);
			try {
				ipsUserInfoDTO=iPSQueryUserInfoService.IpsQueryUserInfo(ipsUserInfoDTO);
			} catch (Exception e) {
				BaseLogger.error(e.getMessage());
				return e.getMessage();
			}
			
            if ((!"1".equals(ipsUserInfoDTO.getAcctStatus())) || (!"0".equals(ipsUserInfoDTO.getAcctStatus()))) {
				// 订单失败,不做补单处理
				BaseLogger.error("tradeNo[" + tradeNo + "]该订单状态没有改变,不能补单!");
				throw new AppException("该订单状态没有改变,不能补单!");
			}

			String userId = order.getUserId();
			orderInfoDTO.setUserId(userId);
			orderInfoDTO.setTradeAccountCardId(order.getTradeAccountCardId());
			orderInfoDTO.setPrice(order.getPrice().setScale(2, BigDecimal.ROUND_FLOOR));
            orderInfoDTO.setOrderNo(order.getOrderNo());
			orderInfoDTO.setOrderStatus(MBOrderInfoDTO.ORDER_STATUS_WITHDRAW_MA_CONFIRM);
			orderInfoDTO.setTitle("提现");

			// 防并发
			int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
			// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
			if (lockUpdateCount != 1) {
				throw new AppException("此账户其他交易正在处理中，请稍后再试");
			}
			try {
				if (!userAccountTransactionService.doWithdrawToMaCallback(orderInfoDTO)) {
					return "error";
				}
			} catch (Exception e) {
				BaseLogger.error("tradeNo[" + tradeNo + "]提现补单失败!", e);
				throw new AppException("系统异常,补单失败");
			} finally {
				// 2. 解锁
				int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
				if (unlockUpdateCount != 1) {
					sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH",
							pageEmailMap("提现回调轮询解锁异常，userId[" + userId + "],tradeNo[" + tradeNo + "]"));
				}
			}
			BaseLogger.audit("tradeNo[" + tradeNo + "]提现补单结束!");
			return "success";
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			BaseLogger.error("tradeNo[" + tradeNo + "]提现补单失败!", e);
			throw new AppException("补单失败");
		}

	}

	@Override
	public String transferS2sCallBackOrder(String tradeNo) {
		try {
			BaseLogger.audit("tradeNo[" + tradeNo + "]提现补单开始!");
            if (StringUtils.isBlank(tradeNo)){
				throw new AppException("订单号不能为空");
		    }
            MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		    orderInfoDTO.setOrderNo(tradeNo);
	        MBOrderInfoDTO order = userAccountMapper.queryOrderInfoByOrderNo(orderInfoDTO);
	        if (order == null) {
				BaseLogger.error("tradeNo[" + tradeNo + "]该订单不存在历史!");
				throw new AppException("该订单不存在历史!");
			}
	        else if (order != null && MBOrderInfoDTO.ORDER_STATUS_TRANSFER_RF_TO_MA_CONFIRM.equals(order.getOrderStatus())) {
				BaseLogger.error("tradeNo[" + tradeNo + "]订单已存在!");
				throw new AppException("订单已存在!");
			}
	        /**
			 * 查询环迅交易信息
			 */
			IpsUserInfoDTO  ipsUserInfoDTO=new IpsUserInfoDTO(); 
			ipsUserInfoDTO.setQuerytype("02");
			ipsUserInfoDTO.setMerBillNo(tradeNo);
			try {
				ipsUserInfoDTO=iPSQueryUserInfoService.IpsQueryUserInfo(ipsUserInfoDTO);
			} catch (Exception e) {
				BaseLogger.error(e.getMessage());
				return e.getMessage();
			}
			
	        
		    if (!"1".equals(ipsUserInfoDTO.getAcctStatus())) {
				// 订单失败,不做补单处理
				BaseLogger.error("tradeNo[" + tradeNo + "]该订单没有提现成功,不能补单!");
				throw new AppException("该订单没有提现成功,不能补单!");
			}
			String userId=order.getUserId();
			orderInfoDTO.setUserId(userId);
			orderInfoDTO.setTradeAccountCardId(order.getTradeAccountCardId());
			orderInfoDTO.setBatchNo(order.getBatchNo());
			orderInfoDTO.setIpsNo(order.getIpsNo());
			orderInfoDTO.setPrice(order.getPrice().setScale(2, BigDecimal.ROUND_FLOOR));
            orderInfoDTO.setOrderNo(order.getOrderNo());
			orderInfoDTO.setOrderStatus(MBOrderInfoDTO.ORDER_TYPE_TRANSFER_RF_TO_MA);
			orderInfoDTO.setTitle("转账");
            // 防并发
			int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
			// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
			if (lockUpdateCount != 1) {
				throw new AppException("此账户其他交易正在处理中，请稍后再试");
			}
			try {
				if (!userAccountTransactionService.dotransferS2sCallBack(orderInfoDTO)) {
					return "error";
				}
			} catch (Exception e) {
				BaseLogger.error("tradeNo[" + tradeNo + "]提现补单失败!", e);
				throw new AppException("系统异常,补单失败");
			} finally {
				// 2. 解锁
				int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
				if (unlockUpdateCount != 1) {
					sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("提现回调轮询解锁异常，userId[" + userId + "],tradeNo[" + tradeNo + "]"));
				}
			}
				
			BaseLogger.audit("tradeNo[" + tradeNo + "]提现补单结束!");
			return "success";
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			BaseLogger.error("tradeNo[" + tradeNo + "]提现补单失败!", e);
			throw new AppException("补单失败");
		}
	 
	
	}
	
	/**
	 *开户补单
	 */
	@Override
	public String repairOpenaccountOrder(String tradeNo) {
		try {
			BaseLogger.audit("tradeNo[" + tradeNo + "]开户补单开始!");
            if (StringUtils.isBlank(tradeNo)){
				throw new AppException("订单号不能为空");
		    }
            MBOrderInfoDTO orderInfoDTO = new MBOrderInfoDTO();
		    orderInfoDTO.setOrderNo(tradeNo);
	        MBOrderInfoDTO order = userAccountMapper.queryOrderInfoByOrderNo(orderInfoDTO);
	        if (order == null) {
				BaseLogger.error("tradeNo[" + tradeNo + "]该订单不存在历史!");
				throw new AppException("该订单不存在历史!");
			}
	        else if (order != null && MBOrderInfoDTO.ORDER_STATUS_OPEN_ACCOUNT_CONFIRM.equals(order.getOrderStatus())) {
				BaseLogger.error("tradeNo[" + tradeNo + "]订单已存在!");
				throw new AppException("订单已存在!");
			}
	        /**
			 * 查询环迅交易信息
			 */
			IpsUserInfoDTO  ipsUserInfoDTO=new IpsUserInfoDTO(); 
			ipsUserInfoDTO.setQuerytype("01");
			ipsUserInfoDTO.setMerBillNo(tradeNo);
			try {
				ipsUserInfoDTO=iPSQueryUserInfoService.IpsQueryUserInfo(ipsUserInfoDTO);
			} catch (Exception e) {
				BaseLogger.error(e.getMessage());
				return e.getMessage();
			}
			if (!"1".equals(ipsUserInfoDTO.getAcctStatus())) {
				// 订单失败,不做补单处理
				BaseLogger.error("tradeNo[" + tradeNo + "]该订单没有开户成功,不能补单!");
				throw new AppException("该订单没有开户成功,不能补单!");
			}
			String userId=order.getUserId();
			orderInfoDTO.setUserId(userId);
			orderInfoDTO.setIpsNo(ipsUserInfoDTO.getIpsBillNo());
			orderInfoDTO.setOrderStatus(ipsUserInfoDTO.getAcctStatus());
			orderInfoDTO.setTitle("开户");
			if(!userAccountTransactionService.doOpenaccountOrder(orderInfoDTO)){
			   return "error";	
			}
			BaseLogger.audit("tradeNo[" + tradeNo + "]开户补单结束!");
			return "success";
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {
			BaseLogger.error("tradeNo[" + tradeNo + "]开户补单失败!", e);
			throw new AppException("补单失败");
		}
	 
	}

}
	
			
