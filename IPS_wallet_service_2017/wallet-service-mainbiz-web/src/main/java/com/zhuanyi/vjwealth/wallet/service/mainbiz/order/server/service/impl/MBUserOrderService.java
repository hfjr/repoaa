package com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.loan.cana.dto.ExecuteConfirmRepaymentReq;
import com.zhuanyi.vjwealth.loan.cana.webservice.ICanaLoanInterfaceService;
import com.zhuanyi.vjwealth.loan.client.dto.ClientBasicInfoDTO;
import com.zhuanyi.vjwealth.loan.jd.webservice.IJDLoanDubboService;
import com.zhuanyi.vjwealth.loan.liutongbao.webservice.IApplyLoanLtbDubboService;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.DueRepayDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.NoRepayOrderDetailDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.CheckRepayOrdersReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.ExpireRepayReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.LoanOrderReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.SaveRepayOrderReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.CheckRepayOrdersResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.ExpireRepayResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.LoanOrderResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.SaveRepayOrderResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.webservice.IApplyWageAdvanceDubboService;
import com.zhuanyi.vjwealth.wallet.service.balance.b2c.server.service.IB2CPayGatewayService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.service.ICheckUserAccountBalanceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.constant.OrderIfFrozenValue;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.ApplyLoanResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.CanaRepaymentResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.DueRepaymentBankCardWithholdDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.DueRepaymentInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.EarlyAutoRepaymentConfirmForFrozenReq;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.EarlyAutoRepaymentConfirmForFrozenRsp;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.EarlyAutoRepaymentConfirmForThawReq;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.EarlyAutoRepaymentConfirmForThawRsp;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.EarlyRepaymentInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.LoanResultCommonDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.OrderInfoQueryDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.OrderRepayHistory;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.WageAdvanceEarlyRepayDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.payment.DueRepaymentCommonDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.mapper.IMBUserLoanOrderMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.service.IDueRepaymentService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.service.IMBUserOrderService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.BindingCardDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.TradeSearchResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.facade.IWithholdServiceFacade;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.constant.PlanConstant;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.dto.WjSalaryPlanRecordDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.plan.server.mapper.IWjSalaryPlanRecordMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.constant.UserConstant;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserAccountService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IUserAccountTransactionService;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;

@Service
public class MBUserOrderService implements IMBUserOrderService {

	@Autowired
	private IMBUserAccountMapper userAccountMapper;
	@Autowired
	private ISequenceService sequenceService;
	
	@Remote
	ISendEmailService sendEmailService;
	
	@Autowired
	private IMBUserLoanOrderMapper mBUserLoanOrderMapper;
	
	@Remote
	private IB2CPayGatewayService iB2CPayGatewayService;


	@Autowired
	private IApplyLoanLtbDubboService applyLoanLtbDubboService;
	
	@Autowired
	private IUserAccountTransactionService userAccountTransactionService;
	@Autowired
	private IMBUserAccountService mbUserAccountService;
	@Autowired
	private ICanaLoanInterfaceService canaLoanInterfaceService;

	@Autowired
	private ICheckUserAccountBalanceService checkUserAccountBalanceService;

	@Autowired
	private IApplyWageAdvanceDubboService applyWageAdvanceDubboService;

	@Resource(name="wageAdvanceDueRepaymentServiceImpl")
	private IDueRepaymentService dueRepaymentService;

	@Resource(name = "vjSelfLoanDueRepaymentServiceImpl")
	private IDueRepaymentService vjDueRepaymentService;
	
	@Resource(name = "piccLoanDueRepaymentServiceImpl")
	private IDueRepaymentService piccDueRepaymentService;

	@Autowired
	private IWithholdServiceFacade withholdServiceFacade;

	@Autowired
	private IWjSalaryPlanRecordMapper wjSalaryPlanRecordMapper;

	@Autowired
	private IJDLoanDubboService jdLoanDubboService;
	
//	@Remote
//	private IAutoInsuranceLoanService autoInsuranceLoanService;

	/**
	 * 白领专享-贷款记录
	 */
	@Override
	public String addLoanOrder(String userId, String tradeId, String amount) {
		try {
			String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
			userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getXyLoanOrder(userId, orderNo, new BigDecimal(amount).divide(new BigDecimal(100)), tradeId));
		} catch (Exception e) {
			BaseLogger.error("借款订单生成失败",e);
			return "{\"code\":\"600\",\"message\":\"fail\"}";
		}
		
		return "{\"code\":\"200\",\"message\":\"success\"}";
	}

	/**
	 * 白领专享-还款记录
	 */
	@Override
	public String addRepaymentOrder(String userId, String tradeId, String amount) {
		try {
			String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
			userAccountMapper.addtOrderInfo(MBOrderInfoDTO.getXyRepaymentOrder(userId, orderNo, new BigDecimal(amount).divide(new BigDecimal(100)), tradeId));
		} catch (Exception e) {
			BaseLogger.error("还款订单生成失败",e);
			return "{\"code\":\"600\",\"message\":\"fail\"}";
		}
		
		return "{\"code\":\"200\",\"message\":\"success\"}";
	}

	public String getId(String prefix, String sequenceName) {
		return prefix + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequenceService.getNextStringValue(sequenceName, 10);
	}

	
	/**
	 * 流通宝-生成借款订单
	 * @param userId
	 * @param orderId
	 * @param borrowAmount
	 * @param borrowDay
	 * @return
	 */
	@Override
	public ApplyLoanResultDTO addPledgeLoanOrder(String userId, String orderId,
			String borrowAmount, String borrowDay) {
		//1. 校验参数
		validatorAddPledgeLoanOrderParams(userId,orderId,borrowAmount,borrowDay);
        String resultCode = "";
		String orderNo = "";
		try{

			//2.检查用户账户以及相关订单是否匹配
			checkUserAccountBalanceService.checkUserAccountBalance(userId);

			//3.锁定账单，并校验是否是重复下单
			lockRfOrder(userId,orderId);

			//4.生成一个借款中的订单
			orderNo = addApplyingOrder(userId, borrowAmount,orderId);

            //5.信贷系统下单所需参数
			JSONObject paramJson=prepareParams(userId,borrowAmount,borrowDay,orderId);
			//5.1.调用信贷系统，借款下单
			BaseLogger.info("流通宝借款下单入参："+paramJson);
			String jsonResult = applyLoanLtbDubboService.saveLoanOrder(paramJson.toJSONString());
			BaseLogger.info("流通宝借款下单返回结果："+jsonResult);
			
			try{
				JSONObject result = JSONObject.parseObject(jsonResult);
				resultCode = result.getString("code");
				//6 判断借款下单的返回结果
				if(resultCode.equals("200")){
					//3.更新借款账单,并在贷款账户上增加借款金额
					JSONObject dataJson = result.getObject("data", JSONObject.class);
					operatePledgeLoanOrderInfo(userId,borrowAmount,dataJson.getString("loanId"),orderNo);
					return new ApplyLoanResultDTO("203100","流通宝下单成功",paramJson.getString("bankInfo"));
				}else{
					//返回失败
					BaseLogger.error("流通宝信贷系统下单失败");
					return new ApplyLoanResultDTO("203101",result.getString("message"));
				}
			}catch(Exception e){
				BaseLogger.error("流通宝信贷生成订单成功，生成订单时失败userId:"+userId,e);
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("addPledgeLoanOrder 异常，流通宝信贷生成订单成功，生成订单时失败userId[" + userId + "]"));
				return new ApplyLoanResultDTO("203100","流通宝下单成功,稍后可在账单中查询记录",paramJson.getString("bankInfo"));
			}
			
		}catch(Exception e){
			BaseLogger.error("流通宝下单失败:"+userId,e);
			return new ApplyLoanResultDTO("203101","流通宝下单失败");
		}finally {
			//7.如果借款申请失败，
            if(!resultCode.equals("200")){
				//则将锁定的理财订单解锁
				unlockRfOrder(userId,orderId);
				//将借款申请订单状态由“申请中”改成借款失败
				mBUserLoanOrderMapper.updateApplyLoanOrder(userId,orderNo,MBOrderInfoDTO.ORDER_STATUS_PLEDGE_LOAN_FAIL,"流通宝借款失败","");
			}
		}
		
	}

	private void validatorAddPledgeLoanOrderParams(String userId,String orderId,String borrowAmount,String borrowDay){
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(orderId) || StringUtils.isBlank(borrowAmount) || StringUtils.isBlank(borrowDay)){
			throw new AppException("参数不能为空");
		}
		if(!StringUtils.isNumeric(borrowAmount)){
			throw new AppException("借款金额不合法");
		}
		if(!StringUtils.isNumeric(borrowDay)){
			throw new AppException("借款天数不合法");
		}
	}

    //查询相关参数
	private JSONObject prepareParams(String userId,String borrowAmount,String borrowDay,String orderId){
		Map<String,String> userBasicInfo = mBUserLoanOrderMapper.queryUserBasicInfoById(userId);
		JSONObject jsonObj = JSONObject.parseObject(JSONObject.toJSONString(userBasicInfo));
		jsonObj.put("applyAmount", borrowAmount);
		jsonObj.put("loanDay", borrowDay);
		jsonObj.put("outRelationNo", orderId);
		return  jsonObj;
	}

	//锁定理财订单
    @Transactional
	private void lockRfOrder(String userId,String orderId){
		//注：因为流通宝业务要求，在针对一个V理财的投资进行借款操作后，将会把对应的v理财投资记录进行冻结(即发放的收益以及理财到期后的本息不会直接进入ma账户，
		//                             而是冻结到rf账户上，当借款还清或逾期进行清算后，会将收益发放到ma账户)
		//1.冻结理财订单-(wj_order_product的if_frozen字段)
		int count = mBUserLoanOrderMapper.updateRelOrderInfo(orderId, OrderIfFrozenValue.NOT_FROZEN_ORDER,OrderIfFrozenValue.FROZEN_ORDER);

		if(count == 0){
			throw new AppException("不能对同一笔理财订单重复借款");
		}

		//2.更新rf还款计划的if_frozen字段
		mBUserLoanOrderMapper.updateRfRepayPlanToFrozen(orderId);
	}

	//解锁理财订单
	@Transactional
	private void unlockRfOrder(String userId,String orderId){

		//1.解冻理财订单-(wj_order_product的if_frozen字段)
	    mBUserLoanOrderMapper.updateRelOrderInfo(orderId,OrderIfFrozenValue.FROZEN_ORDER, OrderIfFrozenValue.NOT_FROZEN_ORDER);

		//2.更新rf还款计划的if_frozen字段
		mBUserLoanOrderMapper.updateRfRepayPlanToUnFrozen(orderId);
	}


	//生成借款中的订单
	private String addApplyingOrder(String userId,String borrowAmount,String orderId){
		BigDecimal amount = formatMoney(borrowAmount);
		//1.生成账单(wj_order)
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		mBUserLoanOrderMapper.addPledgeLoanOrder(MBOrderInfoDTO.getPledgeLoanOrder(userId, orderNo, amount,"" ,orderId));
		return orderNo;

	}

	//更新借款订单为确认
	@Transactional
	private void operatePledgeLoanOrderInfo(String userId,String borrowAmount,String loanId,String applyOrderNo){
		BigDecimal amount = formatMoney(borrowAmount);

		//1. ln账户增加借款金额
		userAccountMapper.updateAddLnAmount(userId,(amount).negate());
		//2.更新账单(wj_order)
		mBUserLoanOrderMapper.updateApplyLoanOrder(userId,applyOrderNo,MBOrderInfoDTO.ORDER_STATUS_PLEDGE_LOAN_CONFIRM,"流通宝借款",loanId);
		
	}
	
	private Map<String, Object> pageEmailMap(String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", content);
		return map;
	}

	
	/**
     * 流通宝--提前还款
	 *
     * @param userId 用户编号
     * @param loanCodes 贷款编号（数组，可一次选择多笔借款记录进行提前还款）
     * @param principal 还款本金
	 * @param repaymentTotalMoney 还款总额
     * @param repaymentType 付款类型（流通宝还款）
     * @param repaymentWay  付款方式（融桥宝余额）
     * @return
     */
	@Override
	public LoanResultCommonDTO earlyRepaymentConfirm(String userId, String loanCodes,String principal,
			String repaymentTotalMoney, String repaymentType, String repaymentWay) {
		//1.校验
		//1.1校验参数为空以及金额是否合法
		validatorRepayParams(userId, loanCodes,principal,repaymentTotalMoney, repaymentType, repaymentWay);

		//1.2转换金额
		BigDecimal repayTotal = formatMoney(repaymentTotalMoney);
		
		// 1.3 调用信贷系统，查询需还贷款的信息，用来校验应还与实还是否匹配（提前还款时，如果选择了多笔，则查询每个借款订单的还款总额）
		List<EarlyRepaymentInfoDTO> repaymentInfo = queryLoanInfoByLoanIds(userId,loanCodes,principal,repaymentTotalMoney);
		
		// 2. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}
		try {
			// 主业务逻辑
			// 2.1 检测余额是否够还款
			if(!checkAccountIsEnoughToTransfer(userId, UserConstant.TRADETYPE_MA, repayTotal)){
				return new LoanResultCommonDTO("203202","余额不足 | 取消 | 去充值");
			}

			// 2.2 扣款：先将还款金额从ma余额里扣除并放入冻结金额中，订单状态为还款处理中
			List<String> orderNos = userAccountTransactionService.processEarlyRepayment(userId, repayTotal,repaymentInfo);
			BaseLogger.info("流通宝提前还款，将还款金额从余额中扣除到冻结金额，并生成还款中的订单成功");
			try{
				//2.3 调用信贷系统，更新借款还款计划并在spv账户体系中记录回款信息，返回结果
				Map<String,String> repayResult = updateLoanRepayInfo(userId,loanCodes,principal,repaymentTotalMoney);
				String code = repayResult.get("code");
				BaseLogger.info("流通宝提前还款，更新贷款系统还款计划：返回code["+code+"]");
				
				if(code.equals("200")){
					//2.4 根据信贷系统的返回结果，如果成功，则将钱从冻结金额里扣除，将订单状态改为确认，并解冻对应v理财的本金及收益
					//                                  ；失败的话，发送紧急邮件，运维处理
					userAccountTransactionService.processRepaymentDeductionFrozenAmount(userId, repayTotal, orderNos,repayResult.get("isEnd"),formatLoanCodes(loanCodes),repaymentInfo);
					BaseLogger.info("流通宝提前还款，将还款金额从余额中扣除到冻结金额成功");
				}else{
					BaseLogger.info("提前还款扣款成功，调用信贷系统更新记录时返回600");
					sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("earlyRepaymentConfirm 异常，提前还款扣款成功，调用信贷系统更新记录时异常，userId[" + userId + "]"));
					return new LoanResultCommonDTO("203203","还款处理中");
				}
				
			}catch(Exception e){
				throw new AppException("还款处理中",e);
			}
			
		}catch(AppException e){
			BaseLogger.error("earlyRepaymentConfirm 异常，提前还款扣款成功，调用信贷系统更新记录时异常，userId:["+userId+"]",e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("earlyRepaymentConfirm 异常，提前还款扣款成功，调用信贷系统更新记录时异常，userId[" + userId + "]"));
			return new LoanResultCommonDTO("203203","还款处理中");
		}
		catch(Exception e){
			BaseLogger.error("流通宝提前还款失败：",e);
			return new LoanResultCommonDTO("203201","还款失败");
		} finally {
			// 3. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
			BaseLogger.audit("unlock earlyRepaymentConfirm userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]" );
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("earlyRepaymentConfirm 异常，userId[" + userId + "]"));
			}

		}
		return new LoanResultCommonDTO("203200","提前还款成功");
	}

	
	/**
	 * 提前还款(凯拿)一次性扣款
	 * @param userId
	 * @param orderId
	 * @param planId
     * @return
     */
	@Override
	public CanaRepaymentResultDTO earlyRepaymentConfirm(String userId, String orderId, String planId) {
		//1校验是否是重复还款（借款到期还款任务设定为每日循环跑）
		if(mBUserLoanOrderMapper.checkIsRepeatRepayForCana(userId,planId)){
			BaseLogger.info("提前还款(凯拿)一次性扣款，重复调用");
			throw new AppException("请勿重复还款");
		}
		//2.账户上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}
		BigDecimal totalAmount=null;
		try {
			// 3调用信贷系统，查询需还贷款的信息
			JSONObject obj = JSONObject.parseObject(applyLoanLtbDubboService.queryRepayOrdersByOrderIds(orderId, planId));
			totalAmount=new BigDecimal(obj.get("totalAmount").toString());
			//4查询余额
			BigDecimal availableAmount = userAccountMapper.queryAccountAvailableAmount(userId,UserConstant.TRADETYPE_MA);
			BaseLogger.error("cana earlyRepaymentConfirm userId:["+userId+"] availableAmount:"+availableAmount+
					" totalAmount:"+totalAmount);
			if(availableAmount==null||availableAmount.compareTo(totalAmount) <0){
				availableAmount=availableAmount==null?new BigDecimal(0):availableAmount.setScale(2);
				return new CanaRepaymentResultDTO("203601","余额不足",availableAmount.toPlainString()
						,totalAmount.toPlainString(),totalAmount.subtract(availableAmount).setScale(2).toPlainString());
			}
			//5冻结金额
			List<EarlyRepaymentInfoDTO> resultList=new ArrayList<EarlyRepaymentInfoDTO>();
			resultList.add(new EarlyRepaymentInfoDTO(planId,totalAmount.toPlainString()));
			List<String> orderNos = userAccountTransactionService.processEnjoyAutoRepaymentMaToFrozen(userId,totalAmount,resultList);
			//6 调用信贷系统，更新相关记录状态
			BaseLogger.error("调用信贷系统，更新相关记录状态 userId:["+userId+"]");
			canaLoanInterfaceService.executeConfirmRepayment(new ExecuteConfirmRepaymentReq(planId));
			BaseLogger.error("调用信贷系统，更新相关记录状态结束 userId:["+userId+"]");
			//7 ma冻结金额中扣除金额,并转ln、生成订单
			userAccountTransactionService.processAutoRepaymentDeductionFrozenAmountForCana(userId, totalAmount,orderNos, MBOrderInfoDTO.ORDER_TYPE_CANA_AUTO_REPAY_CONFIRM,planId);
			
		}catch(AppException e){
			BaseLogger.error("earlyRepaymentConfirm 异常，提前还款扣款成功，调用信贷系统更新记录时异常，userId:["+userId+"]",e);
			if(totalAmount==null){
				throw new AppException("系统繁忙,请稍收再试!");
			}else {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("earlyRepaymentConfirm 异常，提前还款扣款成功，调用信贷系统更新记录时异常，userId[" + userId + "]"));
				throw new AppException("还款处理中," + totalAmount.toPlainString() + "元,稍后可通过账单查询处理结果");
			}
		}catch(Exception e){
			BaseLogger.error("earlyRepaymentConfirm 异常，提前还款扣款成功，调用信贷系统更新记录时异常，userId:["+userId+"]",e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("earlyRepaymentConfirm 异常，提前还款扣款成功，调用信贷系统更新记录时异常，userId[" + userId + "]"));
			throw new AppException("还款失败!");
		} finally {
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
			BaseLogger.audit("unlock earlyRepaymentConfirm userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]" );
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("凯拿earlyRepaymentConfirm 异常，userId[" + userId + "]"));
			}
		}
		return new CanaRepaymentResultDTO("203600","还款成功");
	}


	/**
	 * 自动还款cana,冻结
	 * @param req
	 * @return
     */
	public EarlyAutoRepaymentConfirmForFrozenRsp earlyAutoRepaymentConfirmForFrozen(EarlyAutoRepaymentConfirmForFrozenReq req) {
		EarlyAutoRepaymentConfirmForFrozenRsp ear=new EarlyAutoRepaymentConfirmForFrozenRsp();
		try {
			//上锁
			int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(req.getUserId());
			if (lockUpdateCount != 1) {
				throw new AppException("此账户其他交易正在处理中，请稍后再试");
			}
			
			
			//1.2校验是否是重复还款（借款到期还款任务设定为每日循环跑）
	        if(mBUserLoanOrderMapper.checkIsRepeatRepayForCana(req.getUserId(),req.getPlanId())){
				BaseLogger.info("已经发起了自动还款，重复调用");
				throw new AppException("已经发起了自动还款，重复调用");
			}
			
	        
	        
			//判断余额够不够
			//调用信贷系统，查询需还贷款的信
			BigDecimal availableAmount = userAccountMapper.queryAccountAvailableAmount(req.getUserId(),UserConstant.TRADETYPE_MA);
			availableAmount=availableAmount==null?new BigDecimal("0"):availableAmount;
			
			BigDecimal totalAmount=new BigDecimal(req.getAmount());			
			if(availableAmount.compareTo(totalAmount) <0){
				throw new AppException("账户余额不足");
			}

			List<EarlyRepaymentInfoDTO> resultList=new ArrayList<EarlyRepaymentInfoDTO>();
			resultList.add(new EarlyRepaymentInfoDTO(req.getPlanId(),totalAmount.toPlainString()));
			
			List<String> orderNos = userAccountTransactionService.processEnjoyAutoRepaymentMaToFrozen(req.getUserId(),totalAmount,resultList);
			ear.setCode("000000");
			ear.setMessage("余额冻结成功");
			ear.setOrderNos(orderNos);
			ear.setTotalAmount(totalAmount.toString());
			return ear;
		}catch(AppException e){
			BaseLogger.error("余额冻结业务异常:",e);
			ear.setCode("000001");
			ear.setMessage("余额冻结业务异常,"+e.getMessage());
			BaseLogger.error("earlyAutoRepaymentConfirmForFrozen 异常，cana自动还款，调用信贷系统更新记录时异常，userId:["+req.getUserId()+"]",e);
			//sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("earlyAutoRepaymentConfirmForFrozen 异常，cana自动还款冻结业务异常，调用信贷系统更新记录时异常，userId[" + req.getUserId() + "]"));			
			return ear;
		}catch(Exception e){
			//sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("earlyAutoRepaymentConfirmForFrozen 异常，cana自动还款冻结系统异常，调用信贷系统更新记录时异常，userId[" + req.getUserId() + "]"));
			BaseLogger.error("余额冻结系统异常:",e);
			ear.setCode("000002");
			ear.setMessage("余额冻结系统异常:"+e.getMessage());
			return ear;
		} finally {
			try{
				int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(req.getUserId());
				//BaseLogger.audit("unlock earlyAutoRepaymentConfirmForFrozen userId[" + req.getUserId() + "] unlockUpdateCount[" + unlockUpdateCount + "]" );
				if (unlockUpdateCount != 1) {
				 sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("cana自动还款 余额冻结  解锁 earlyAutoRepaymentConfirmForFrozen 异常，userId[" + req.getUserId()  + "]"));
				}
			}catch(Exception e){
				BaseLogger.error("余额冻结解锁发送邮件异常:",e);
				throw new AppException("余额冻结解锁发送邮件异常,"+e.getMessage());
			}
		}

	}


	/**
	 * 自动还款cana,解冻
	 * @param req
	 * @return
     */
	public EarlyAutoRepaymentConfirmForThawRsp earlyAutoRepaymentConfirmForThaw(EarlyAutoRepaymentConfirmForThawReq req) {
		EarlyAutoRepaymentConfirmForThawRsp ear=new EarlyAutoRepaymentConfirmForThawRsp();
		try {
			int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(req.getUserId());
			if (lockUpdateCount != 1) {
				throw new AppException("此账户其他交易正在处理中，请稍后再试");
			}
			userAccountTransactionService.processAutoRepaymentDeductionFrozenAmountForCana(req.getUserId(), new BigDecimal(req.getTotalAmount()),req.getOrderNos(), MBOrderInfoDTO.ORDER_TYPE_CANA_AUTO_REPAY_CONFIRM,req.getPlanId());
			
			ear.setCode("000000");
			ear.setMessage("余额解冻成功");
			return ear;
		}catch(AppException e){
			BaseLogger.error("余额解冻业务异常",e);
			ear.setCode("000001");
			ear.setMessage("余额解冻业务异常,"+e.getMessage());
			//sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("earlyAutoRepaymentConfirmForThaw 异常，自动还款扣款业务异常，调用信贷系统更新记录时异常，userId[" + req.getUserId() + "]"));
			return ear;
		}catch(Exception e){
			BaseLogger.error("余额解冻系统异常:",e);
			ear.setCode("000002");
			ear.setMessage("余额解冻系统异常,"+e.getMessage());
			//sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("earlyAutoRepaymentConfirmForThaw 异常，自动还款扣款系统异常，调用信贷系统更新记录时异常，userId[" + req.getUserId() + "]"));
			return ear;
		} finally {
			try{
				int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(req.getUserId());
				//BaseLogger.audit("unlock earlyAutoRepaymentConfirmForThaw userId[" + req.getUserId() + "] unlockUpdateCount[" + unlockUpdateCount + "]" );
				if (unlockUpdateCount != 1) {
					sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("cana 自动还款  余额解冻  解锁  earlyAutoRepaymentConfirmForThaw 异常，userId[" + req.getUserId() + "]"));
				}
			}catch(Exception e){
				BaseLogger.error("余额解冻解锁发送邮件异常:",e);
				throw new AppException("余额解冻解锁发送邮件异常,"+e.getMessage());
			}
			
			
		}
		
	}
	
	
	

    //流通宝--提前还款--更新信贷信息
	private Map<String,String> updateLoanRepayInfo(String userId, String loanCodes, String repayPrincipal, String repayTotal){
		Map<String,String> resultMap =  new HashMap<String,String>();
		String[] loanIds = formatLoanCodes(loanCodes);
		String resultJson = "";
		//一次还款多笔的情况
		BaseLogger.info(String.format("流通宝updateLoanRepayInfo调用信贷系统查询入参userId:%s;loanIds：%s;repayPrincipal:%s;repayTotal:%s",userId,loanCodes,repayPrincipal,repayTotal));
		if(loanIds.length>1){
			resultJson = applyLoanLtbDubboService.saveRepayOrders(userId, loanIds, repayTotal);
		}
		//一次还款一笔
		if(loanIds.length==1){
			resultJson = applyLoanLtbDubboService.saveRepayOrder(userId, loanIds[0], repayTotal, repayPrincipal);
		}
		BaseLogger.info("流通宝updateLoanRepayInfo调用信贷系统查询结果："+resultJson);
		JSONObject obj = JSONObject.parseObject(resultJson);
		resultMap.put("code", obj.getString("code"));
		resultMap.put("isEnd", obj.getString("orderStatus").equals("F")?"true":"false");//是否结清
		return resultMap;
	}




	private void validatorRepayParams(String userId, String loanCodes,
			String principal, String repaymentMoney, String repaymentType,
			String repaymentWay) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(loanCodes)|| StringUtils.isBlank(repaymentMoney)
				|| StringUtils.isBlank(repaymentType)|| StringUtils.isBlank(repaymentWay)) {
			throw new AppException("参数不完整");
		}
		
		if (!NumberUtils.isNumber(principal) || new BigDecimal(principal).compareTo(new BigDecimal(0)) <= 0) {
			throw new AppException("金额不合法");
		}
		if (!NumberUtils.isNumber(repaymentMoney) || new BigDecimal(repaymentMoney).compareTo(new BigDecimal(0)) <= 0) {
			throw new AppException("金额不合法");
		}
		
		
	}

	private void validatorMoney(String repaymentMoney, String totalMoney) {
	    BigDecimal money1 = new BigDecimal(repaymentMoney);
        BigDecimal money2 = new BigDecimal(totalMoney);
		if(!(money1.compareTo(money2)==0)){
			throw new AppException("还款金额错误");
		}
	}


	//流通宝--提前还款--查询还款信息
	@SuppressWarnings("unchecked")
	private List<EarlyRepaymentInfoDTO> queryLoanInfoByLoanIds(String userId,String loanCodes,String repayPrincipal,String repayTotalMoney){
		List<EarlyRepaymentInfoDTO> resultList = new ArrayList<EarlyRepaymentInfoDTO>();
		BigDecimal totalMoney = new BigDecimal(0);
		String[] loanIds = formatLoanCodes(loanCodes);
		//调用信贷系统，获取借款记录列表
		BaseLogger.info(String.format("流通宝queryLoanInfoByLoanIds调用信贷系统查询入参userId:%s;loanCodes：%s;repayTotalMoney:%s",userId,loanCodes,repayTotalMoney));
		String jsonStr = "";
		
		if(loanIds.length==1){
			jsonStr = applyLoanLtbDubboService.queryRepayOrderTotal(userId,loanIds[0],repayTotalMoney,repayPrincipal);
			JSONObject obj = JSONObject.parseObject(jsonStr);
			if(obj.getString("code").equals("200")){
				String dataStr = obj.getString("data");
				EarlyRepaymentInfoDTO ld = JSONObject.parseObject(dataStr,EarlyRepaymentInfoDTO.class);
				resultList.add(ld);
				totalMoney = new BigDecimal(ld.getTotalAmount());
			}else{
				throw new AppException("还款金额错误");
			}
		}else if(loanIds.length>1){
			jsonStr = applyLoanLtbDubboService.queryRepayOrdersTotal(userId,loanIds,repayTotalMoney);
			JSONObject obj = JSONObject.parseObject(jsonStr);
			if(obj.getString("code").equals("200")){
				List<Map<String,String>> list =JSONArray.parseObject(obj.getString("records"),List.class);
				for(Map<String,String> map:list){
					EarlyRepaymentInfoDTO ld = JSONObject.parseObject(JSONObject.toJSONString(map),EarlyRepaymentInfoDTO.class);
					resultList.add(ld);
					totalMoney = totalMoney.add(new BigDecimal(ld.getTotalAmount()));
				}
			}else{
				throw new AppException("还款金额错误");
			}
		}
		 
		BaseLogger.info("流通宝queryLoanInfoByLoanIds调用信贷系统查询结果："+jsonStr);

		//校验是否是重复提前还款
		//1.2校验是否是重复还款（借款到期还款任务设定为每日循环跑）
		if(resultList.size()>0){
			for(EarlyRepaymentInfoDTO erd:resultList){
				if(mBUserLoanOrderMapper.checkIsRepeatEarlyRepay(userId,erd.getPlanId())){
					BaseLogger.info("已经发起了提前还款，重复调用");
					throw new AppException("请勿重复还款");
				}
			}
		}

		// 校验传入金额，是否和从信贷系统中查到的需还金额相匹配
		validatorMoney(repayTotalMoney,totalMoney.toPlainString());

		
		return resultList;
	}
	
	private String[] formatLoanCodes(String loanCodes){
		return loanCodes.split(",");
	}
	
	/**
	 * 校验交易金额参数是否合法
	 * 
	 * @param money
	 */
	private BigDecimal formatMoney(String money) {

		return new BigDecimal(money).setScale(2, BigDecimal.ROUND_FLOOR);
	}

	private boolean checkAccountIsEnoughToTransfer(String userId, String tradeType, BigDecimal amount) {
		BigDecimal availableAmount = userAccountMapper.queryAccountAvailableAmount(userId, tradeType);
		if (availableAmount == null) {
			BaseLogger.error("checkAccountIsEnoughToTransfer 账户信息异常，userId[" + userId + "]");
//			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("checkAccountIsEnoughToTransfer 账户信息异常，userId[" + userId + "]"));
//			throw new AppException("账户信息异常，请联系客服");
			return false;
		}
		if (availableAmount.compareTo(amount) < 0) {
			BaseLogger.error("checkAccountIsEnoughToTransfer 账户信息异常 需要操作资金比原始资金还超(余额不足)，userId[" + userId + "]");
//			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("checkAccountIsEnoughToTransfer 账户信息异常 需要操作资金比原始资金还超，userId[" + userId + "]"));
//			throw new AppException("账户可用余额不足");
			return false;
		}
		return true;
	}



	/**
	 * 流通宝 -- 到期还款
	 * @param userId 用户编号
	 * @param loanCode 贷款编号
	 * @param principal 还款本金
	 * @param repaymentTotalMoney 还款总额
	 * @return
	 */

	public String dueRepaymentForLoanTask(String userId, String loanCode,String planId,String principal,String interest,
													 String repaymentTotalMoney) {BaseLogger.info(String.format("到期还款开始：userId：%s,loanCode:%s,planId:%s,principal:%s,interest:%s,repaymentTotalMoney:%s",userId,loanCode,planId,principal,interest,repaymentTotalMoney));
         JSONObject result = new JSONObject();
		//1.校验
		//1.1校验参数为空以及金额是否合法
		validatorDueRepayParams(userId, loanCode,planId,principal,interest,repaymentTotalMoney);
		BaseLogger.audit("校验是否是重复还款（借款到期还款任务设定为每日循环跑）");
		//1.2校验是否是重复还款（借款到期还款任务设定为每日循环跑）
        if(mBUserLoanOrderMapper.checkIsRepeatRepay(userId,planId)){
			BaseLogger.audit("已经发起了提前还款，重复调用");
			result.put("code","200");
			return result.toJSONString();
		}
		//1.3转换金额
		BigDecimal repayTotal = formatMoney(repaymentTotalMoney);
		BaseLogger.audit("3转换金额");
		// 2. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		// 不等于说明已经上锁 没更新到记录 或者有其他异常情况
		if (lockUpdateCount != 1) {
			BaseLogger.error("此账户其他交易正在处理中，请稍后再试");
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}
		try {
			DueRepaymentInfoDTO dueRepayment;
			//2.1 检查理财是否到期：如果到期了，则直接做清算操作；如果没有到期，则检查余额是否够还款，如果不够还款，则继续逾期；够还款则扣除
			BaseLogger.audit("检查理财是否到期：如果到期了，则直接做清算操作；如果没有到期，则检查余额是否够还款，如果不够还款，则继续逾期；够还款则扣除");
			if(checkFinancialIsDue(userId,loanCode)){
				//2.1.1 清算操作
				BaseLogger.audit("清算操作start---------------------------");
				dueRepayment = userAccountTransactionService.liquidationRfAndLoanAmount(userId,loanCode,planId,new BigDecimal( principal),new BigDecimal( interest),repayTotal);
				BaseLogger.audit("清算操作 end---------------------------");
			}else{
				// 2.1.2 检测余额是否够还款
				BaseLogger.audit("检测余额是否够还款 start---------------------------");
				if(!checkAccountIsEnoughToTransfer(userId, UserConstant.TRADETYPE_MA, repayTotal)){
					result.put("code","600");
					return result.toJSONString();
				}
				BaseLogger.audit("3扣款 start---------------------------");
				//2.1.3扣款
				String orderNo = userAccountTransactionService.processDueRepayment(userId, loanCode,planId,repayTotal);
				dueRepayment = new DueRepaymentInfoDTO(userId,loanCode,planId,repaymentTotalMoney,principal,interest,"0",orderNo,"false");
			}
			BaseLogger.audit("流通宝到期还款，将还款金额从余额中扣除到冻结金额，并生成还款中的订单成功");
			try{
				//2.2 调用信贷系统，更新借款还款计划并在spv账户体系中记录回款信息，返回结果
				String code =updateLoanDueRepayInfo(dueRepayment);
				BaseLogger.audit("流通宝到期还款，更新贷款系统还款计划：返回code["+code+"]");
				if(code.equals("200")){
					//2.3 更新订单状态
					userAccountTransactionService.processDueRepaymentDeductionFrozenAmount(userId, repayTotal, formatMoney(dueRepayment.getPrincipal()),formatMoney(dueRepayment.getInterest()),dueRepayment.getOrderNo(),dueRepayment.getLoanId());
					BaseLogger.audit("流通宝到期还款，将还款金额从余额中扣除到冻结金额成功");
				}else{
					BaseLogger.audit("到期还款扣款成功，调用信贷系统更新记录时返回600");
					sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("earlyRepaymentConfirm 异常，到期还款扣款成功，调用信贷系统更新记录时异常，userId[" + userId + "]"));
					result.put("code","200");
					return result.toJSONString();
				}

			}catch(Exception e){
				BaseLogger.error("还款处理中",e);
				throw new AppException("还款处理中",e);
			}

		}catch(AppException e){
			BaseLogger.error("earlyRepaymentConfirm 异常到期还款扣款成功，调用信贷系统更新记录时异常，userId:["+userId+"]",e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("earlyRepaymentConfirm 异常，到期还款扣款成功，调用信贷系统更新记录时异常，userId[" + userId + "]"));
			result.put("code","200");
			return result.toJSONString();
		}
		catch(Exception e){
			BaseLogger.error("流通宝到期还款失败：",e);
			result.put("code","600");
			return result.toJSONString();
		} finally {
			// 3. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
			BaseLogger.audit("unlock earlyRepaymentConfirm userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]" );
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("earlyRepaymentConfirm 异常，userId[" + userId + "]"));
			}

		}
		result.put("code","200");
		return result.toJSONString();

	}


	private boolean checkFinancialIsDue(String userId,String loanId){

		return mBUserLoanOrderMapper.checkFinancialIsDue(userId,loanId);
	}

	//到期还款更新贷款信息
	private String updateLoanDueRepayInfo(DueRepaymentInfoDTO dueRepayment){
        String params = JSONObject.toJSONString(dueRepayment);
		BaseLogger.audit(String.format("流通宝updateLoanDueRepayInfo调用信贷系统查询入参params:%s",params));
		//一次还款一笔
		String resultJson = applyLoanLtbDubboService.expireRepay(params);
		BaseLogger.audit("流通宝updateLoanDueRepayInfo调用信贷系统查询结果："+resultJson);

		JSONObject obj = JSONObject.parseObject(resultJson);
		return obj.getString("code");
	}

	private void validatorDueRepayParams(String userId, String loanCodes,String planId,
									  String principal,String interest, String repaymentMoney) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(loanCodes)|| StringUtils.isBlank(repaymentMoney)
				|| StringUtils.isBlank(planId)|| StringUtils.isBlank(interest)) {
			throw new AppException("参数不完整");
		}

		if (!NumberUtils.isNumber(principal) || new BigDecimal(principal).compareTo(new BigDecimal(0)) < 0) {
			throw new AppException("金额不合法");
		}
		if (!NumberUtils.isNumber(interest) || new BigDecimal(interest).compareTo(new BigDecimal(0)) < 0) {
			throw new AppException("金额不合法");
		}
		if (!NumberUtils.isNumber(repaymentMoney) || new BigDecimal(repaymentMoney).compareTo(new BigDecimal(0)) < 0) {
			throw new AppException("金额不合法");
		}


	}
	

	/*******************************************************************  我是工资先享分割线  ****************************************************************************************/

	/**
	 * 工资先享-生成借款订单
	 * @param userId 用户编号
	 * @param borrowMode 还款方式
	 * @param borrowAmount 借款金额
	 * @param borrowPeriod 借款期限
	 * @return
	 * @since 3.6
	 */
	@Override
	public ApplyLoanResultDTO addWageAdvanceLoanOrder(String userId, String borrowMode, String borrowAmount, String borrowPeriod) {
		//1. 校验参数
		validatorWageAdvanceLoanOrderParams(userId,borrowMode,borrowAmount,borrowPeriod);
		String resultCode = "";
		String orderNo = "";
		try{

			//2.合账:检查用户账户以及相关订单是否匹配
			checkUserAccountBalanceService.checkUserAccountBalance(userId);

			//3.生成一个借款中的订单
			orderNo = addApplyrWageAdvanceOrder(userId, borrowAmount);

			//4.信贷系统下单所需参数
			Map<String,String> userBasicInfo = mBUserLoanOrderMapper.queryUserBasicInfoById(userId);
			LoanOrderReqDTO paramObj=prepareWageAdvanceApplyParams(userBasicInfo,borrowAmount,borrowMode,borrowPeriod);

			//5.1.调用信贷系统，借款下单
			BaseLogger.info("工资先享借款下单入参："+JSONObject.toJSONString(paramObj));
			LoanOrderResDTO resDTO = applyWageAdvanceDubboService.saveLoanOrder(paramObj);
			BaseLogger.info("工资先享借款下单返回结果："+JSONObject.toJSONString(resDTO));

			try{
				//返回结果码
				resultCode = resDTO.getCode();
				//6 判断借款下单的返回结果
				if(resultCode.equals("200")){
					//6.1.更新借款账单,保存借款订单编号
					//operateWageAdvanceOrderInfo(userId,borrowAmount,resDTO.getOrderId(),orderNo);
					mBUserLoanOrderMapper.updateApplyLoanOrder(userId,orderNo,MBOrderInfoDTO.ORDER_STATUS_WAGE_ADVANCE_LOAN_PROCESS,"工资先享借款审核中",resDTO.getOrderId());
					return new ApplyLoanResultDTO("203900","工资先享下单成功",userBasicInfo.get("bankInfo"));
				}else{
					//6.2 返回失败
					BaseLogger.error("工资先享信贷系统下单失败");
					return new ApplyLoanResultDTO("203901",resDTO.getMsg());
				}
			}catch(Exception e){
				BaseLogger.error("工资先享信贷生成订单成功，生成订单时失败userId:"+userId,e);
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("addWageAdvanceLoanOrder 异常，工资先享信贷生成订单成功，生成订单时失败userId[" + userId + "]"));
				return new ApplyLoanResultDTO("203900","流通宝下单成功,稍后可在账单中查询记录",userBasicInfo.get("bankInfo"));
			}

		}catch(Exception e){
			BaseLogger.error("工资先享下单失败:"+userId,e);
			return new ApplyLoanResultDTO("203901","工资先享下单失败");
		}finally {
			//7.如果借款申请失败
			if(!resultCode.equals("200")){
				//将借款申请订单状态由“申请中”改成借款失败
				mBUserLoanOrderMapper.updateApplyLoanOrder(userId,orderNo,MBOrderInfoDTO.ORDER_STATUS_WAGE_ADVANCE_LOAN_FAIL,"工资先享借款失败","");
			}
		}

	}


	private void validatorWageAdvanceLoanOrderParams(String userId, String borrowMode, String borrowAmount, String borrowPeriod){
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(borrowMode) || StringUtils.isBlank(borrowAmount) || StringUtils.isBlank(borrowPeriod)){
			throw new AppException("参数不能为空");
		}
		if(!StringUtils.isNumeric(borrowAmount)){
			throw new AppException("借款金额不合法");
		}
		if(!StringUtils.isNumeric(borrowPeriod)){
			throw new AppException("借款期限不合法");
		}
	}

	//生成借款中的订单
	private String addApplyrWageAdvanceOrder(String userId,String borrowAmount){
		BigDecimal amount = formatMoney(borrowAmount);
		//1.生成账单(wj_order)
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		mBUserLoanOrderMapper.addPledgeLoanOrder(MBOrderInfoDTO.getWageAdvanceApplyOrder(userId, orderNo, amount,"" ,""));
		return orderNo;

	}

	//查询相关参数
	private LoanOrderReqDTO prepareWageAdvanceApplyParams(Map<String,String> userBasicInfo, String borrowAmount, String borrowMode, String borrowPeriod){
		LoanOrderReqDTO reqDTO = new LoanOrderReqDTO();
		ClientBasicInfoDTO basicInfoDTO = new ClientBasicInfoDTO();

		basicInfoDTO.setClientName(userBasicInfo.get("clientName"));
		basicInfoDTO.setPhone(userBasicInfo.get("phone"));
		basicInfoDTO.setIndentityNo(userBasicInfo.get("indentityNo"));
		basicInfoDTO.setSex(userBasicInfo.get("sex"));
		basicInfoDTO.setId(userBasicInfo.get("userId"));
		reqDTO.setUserInfo(basicInfoDTO);
		reqDTO.setCardNo(userBasicInfo.get("cardNo"));
		reqDTO.setBankName(userBasicInfo.get("bankName"));
		reqDTO.setBorrowType(borrowMode);
		reqDTO.setApplyAmount(borrowAmount);
		reqDTO.setPeriods(borrowPeriod);
		return  reqDTO;
	}

	//更新借款订单为确认或失败
	@Override
	public ApplyLoanResultDTO operateWageAdvanceOrderInfo(String userId,String lendAmount,String loanId,String status){
		BaseLogger.info(String.format("工资先享operateWageAdvanceOrderInfo审核通过后，更新账单状态接收参数：userId:%s,lendAmount:%s,loanId:%s,status:%s",userId,lendAmount,loanId,status));

		//根据订单编号查询借款审核中的订单信息
		Map<String,String> orderInfo = mBUserLoanOrderMapper.queryUserWageAdvanceProcessOrderInfo(userId,loanId);

		//1.1校验订单信息
		if(orderInfo == null){
			return new ApplyLoanResultDTO("600","订单信息不存在");
		}

		//订单编号
		String orderNo = orderInfo.get("orderNo");

		if(status.equals("600")){
			//更新账单(wj_order)为失败
			mBUserLoanOrderMapper.updateApplyLoanOrder(userId,orderNo,MBOrderInfoDTO.ORDER_STATUS_WAGE_ADVANCE_LOAN_FAIL,"工资先享借款失败","");
            //返回
			return new ApplyLoanResultDTO("200","更新账单信息成功");
		}

		//申请金额
		Object amountStr = orderInfo.get("amount");
		//放款金额
		BigDecimal amount = formatMoney(lendAmount);

		//1.2校验放款金额是否和申请金额一致
		if(new BigDecimal(String.valueOf(amountStr)).compareTo(amount)!=0){
			return new ApplyLoanResultDTO("600","放款金额和申请金额不符合，申请金额："+amountStr+";放款金额:"+lendAmount);
		}

		//更新账单到成功状态
		updateWageAdvanceOrderSuccess(userId,amount,orderNo,loanId);

		BaseLogger.info("工资先享operateWageAdvanceOrderInfo审核通过后，更新账单状态成功");
		return new ApplyLoanResultDTO("200","更新账单信息成功");
	}

	@Transactional
	private void updateWageAdvanceOrderSuccess(String userId,BigDecimal amount,String orderNo,String loanId){
		//2. ln账户增加借款金额
		userAccountMapper.updateAddLnAmount(userId,(amount).negate());
		//3.更新账单(wj_order)
		mBUserLoanOrderMapper.updateApplyLoanOrder(userId,orderNo,MBOrderInfoDTO.ORDER_STATUS_WAGE_ADVANCE_LOAN_CONFIRM,"工资先享借款",loanId);
	}




	/**
	 * 工资先享-提前还款  (保留)
	 * @param repayDTO 入参实体
	 * @return
	 */
	@Override
	public LoanResultCommonDTO wageAdvanceEarlyRepaymentConfirm(WageAdvanceEarlyRepayDTO repayDTO){

        //取参
		String userId = repayDTO.getUserId(); //用户编号
		String loanCodes = repayDTO.getLoanCodes();//借款编号
		String principal = repayDTO.getPrincipal();//还款本金
		String repaymentTotalMoney = repayDTO.getRepaymentTotalMoney();//还款总额

		//1.校验
		//1.1校验参数为空以及金额是否合法
		validatorRepayParams(userId, loanCodes,principal,repaymentTotalMoney, repayDTO.getRepaymentType(),repayDTO.getRepaymentWay());

		// 1.3 调用信贷系统，查询需还贷款的信息，用来校验应还与实还是否匹配（提前还款时，如果选择了多笔，则查询每个借款订单的还款总额）
		List<EarlyRepaymentInfoDTO> repaymentInfo = queryWageAdvanceLoanInfoByLoanIds(userId,loanCodes,principal,repaymentTotalMoney);

		//调用余额扣款
		return cutAmountFromAccountBalance(userId,loanCodes,principal,repaymentTotalMoney,repaymentInfo);
	}


	/**
	 * 工资先享 --  从余额扣款 (保留)
	 * @param userId
	 * @param loanCodes
	 * @param principal
	 * @param repaymentTotalMoney
	 * @param repaymentInfo
     * @return
     */
	private LoanResultCommonDTO cutAmountFromAccountBalance(String userId,String loanCodes,String principal,String repaymentTotalMoney,List<EarlyRepaymentInfoDTO> repaymentInfo){

		// 1. 判断是否已经上锁 并上锁
		int lockUpdateCount = userAccountMapper.updateUserWithdrawLock(userId);
		if (lockUpdateCount != 1) {
			throw new AppException("此账户其他交易正在处理中，请稍后再试");
		}

		try {
			//转换金额（还款总额）
			BigDecimal repayTotal = formatMoney(repaymentTotalMoney);

			// 2. 检测余额是否够还款
			if(!checkAccountIsEnoughToTransfer(userId, UserConstant.TRADETYPE_MA, repayTotal)){
				return new LoanResultCommonDTO("204002","账户余额不足|取消|换个方式");
			}

			// 2.1 扣款：先将还款金额从ma余额里扣除并放入冻结金额中，订单状态为还款处理中
			List<String> orderNos = userAccountTransactionService.processWageAdvanceEarlyRepayment(userId, repayTotal,repaymentInfo);
			try{
				//2.2 调用信贷系统，更新借款还款计划并在spv账户体系中记录回款信息，返回结果
				String code = updateWageAdvanceRepayInfo(userId,loanCodes,principal,repaymentTotalMoney,"",true);
				if(code.equals("200")){
					//2.3 根据信贷系统的返回结果，如果成功，则将钱从冻结金额里扣除，将订单状态改为确认；
					userAccountTransactionService.processWageAdvanceDeductionFrozenAmount(userId, repayTotal, orderNos,formatLoanCodes(loanCodes),repaymentInfo);
				}else{
					//2.4 失败的话，发送紧急邮件，运维处理
					BaseLogger.info("提前还款扣款成功，调用信贷系统更新记录时返回600");
					sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("wageAdvanceEarlyRepaymentConfirm 异常，提前还款扣款成功，调用信贷系统更新记录时异常，userId[" + userId + "]"));
					return new LoanResultCommonDTO("204003","还款处理中");
				}

			}catch(Exception e){
				BaseLogger.error("wageAdvanceEarlyRepaymentConfirm 异常，提前还款扣款成功，调用信贷系统更新记录时异常，userId:["+userId+"]",e);
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("wageAdvanceEarlyRepaymentConfirm 异常，提前还款扣款成功，调用信贷系统更新记录时异常，userId[" + userId + "]"));
				return new LoanResultCommonDTO("204003","还款处理中");
			}
		}catch(Exception e){
			BaseLogger.error("工资先享提前还款失败：",e);
			return new LoanResultCommonDTO("204001","还款失败");
		} finally {
			// 3. 解锁
			int unlockUpdateCount = userAccountMapper.updateUserWithdrawUnlock(userId);
			BaseLogger.audit("unlock wageAdvanceEarlyRepaymentConfirm userId[" + userId + "] unlockUpdateCount[" + unlockUpdateCount + "]" );
			if (unlockUpdateCount != 1) {
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("wageAdvanceEarlyRepaymentConfirm 异常，userId[" + userId + "]"));
			}

		}
		return new LoanResultCommonDTO("204000","提前还款成功");

	}


	/**
	 * 工资先享 -- 提前还款扣款前操作
	 * @param userId  用户号
	 * @param cardId 银行卡id
	 * @param loanCodes 借款编号
	 * @param principal 还款本金
	 * @param repaymentTotalMoney 还款总额
     * @return
     */
	@Override
	public String preOperateForWageAdvance(String userId, String cardId,String loanCodes,String principal,String repaymentTotalMoney){
		//1.校验参数
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(cardId)|| StringUtils.isBlank(loanCodes)|| StringUtils.isBlank(principal)||StringUtils.isBlank(repaymentTotalMoney)){
			throw new AppException("参数不完整");
		}

		// 2.调用信贷系统，查询需还贷款的信息，用来校验应还与实还是否匹配（提前还款时，如果选择了多笔，则查询每个借款订单的还款总额）
		List<EarlyRepaymentInfoDTO> repaymentInfo = queryWageAdvanceLoanInfoByLoanIds(userId,loanCodes,principal,repaymentTotalMoney);

		//转换金额（还款总额）
		BigDecimal repayTotal = formatMoney(repaymentTotalMoney);
		// 3 如果是多笔一次还，则循环生成还款中的订单orders，并生成一个扣款中的订单orderNo2，该订单的rel_order_no字段保存orders
		String orderNo = userAccountTransactionService.processWageAdvanceAddWithholdOrder(userId, cardId,loanCodes,new BigDecimal(principal), repayTotal, repaymentInfo);

		return orderNo;

	}


	/**
	 * 工资先享--扣款成功回调（提前还款）
	 * @param userId
	 * @param orderNo
	 * @param amount
	 * @param orderRepayStatus
     * @param title
     */
	@Override
	public void callbackForWithholdSuccess(String userId,String orderNo,String amount,String withholdStatus,String orderRepayStatus,String title){

		try{
			//1.更新金额和账单
			updateAmountAndBankCardWithholdOrder(userId,orderNo,amount,withholdStatus,orderRepayStatus,title,true);

			//3. 查询借款订单的相关信息
			OrderInfoQueryDTO orderInfoQueryDTO = mBUserLoanOrderMapper.queryOrderInfoByOrderNo(userId,orderNo);

			//调用信贷系统
			String code = updateWageAdvanceRepayInfo(userId,orderInfoQueryDTO.getChannelTradeId(),orderInfoQueryDTO.getPrice(),orderInfoQueryDTO.getTotalPrice(),orderInfoQueryDTO.getCardId(),false);

			if(code.equals("600")){
				BaseLogger.error("工资先享提前还款(银行卡) 扣款成功，但是调用信贷系统更新异常");
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("工资先享提前还款(银行卡) 扣款成功，回调时调用信贷系统异常 入参 "+JSONObject.toJSONString(orderInfoQueryDTO)));
			}

			BaseLogger.info("工资先享提前还款成功");

		}catch (Exception e){
			BaseLogger.error("工资先享提前还款成功回调更新数据库异常：",e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("工资先享提前还款(银行卡) 扣款成功，回调异常 userId[" + userId + "]"));
		}

	}


	/**
	 * 工资先享--扣款处理中回调
	 * @param userId
	 * @param orderNo
	 * @param orderRepayStatus
	 * @param title
	 */
	@Override
	public void callbackForWithholdProcess(String userId,String orderNo,String withholdStatus,String orderRepayStatus,String title){

		try{
			//1.更新账单
			updateBankCardWithholdOrder(userId,orderNo,withholdStatus,orderRepayStatus,title,false);

		}catch (Exception e){
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("callbackForWithholdProcess 工资先享提前还款(银行卡) 扣款前更新账单状态异常 userId[" + userId + "]"));
		}

	}


	/**
	 * 工资先享--扣款失败回调
	 * @param userId
	 * @param orderNo
	 * @param orderRepayStatus
	 * @param title
	 */
	@Override
	public void callbackForWithholdFail(String userId,String orderNo,String withholdStatus,String orderRepayStatus,String title){

		try{
			//1.更新账单
			updateBankCardWithholdOrder(userId,orderNo,withholdStatus,orderRepayStatus,title,false);

		}catch (Exception e){
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("callbackForWageAdvanceFail 工资先享提前还款(银行卡) 扣款失败后更新账单异常 userId[" + userId + "]"));
		}

	}


	/**
	 * 工资先享更新扣款订单和金额
	 * @param userId 用户编号
	 * @param orderNo 银行卡扣款订单编号
	 * @param amount 扣款金额
	 * @param orderRepayStatus   MBOrderInfoDTO.ORDER_STATUS_WAGE_REPAY_BANKCARD_CONFIRM
	 * @param title 还款订单title
	 * @param flag 是否更新还款历史记录表
     */
	@Transactional
	private void updateAmountAndBankCardWithholdOrder(String userId,String orderNo,String amount,String withholdStatus,String orderRepayStatus,String title,boolean flag){
		//1. 入ln账户增加还款金额
		userAccountMapper.updateAddLnAmount(userId,new BigDecimal(amount));

		//2. 更新账单状态
		updateBankCardWithholdOrder(userId,orderNo,withholdStatus,orderRepayStatus,title,flag);
	}



	/**
	 * 工资先享更新还款订单
	 * @param userId 用户编号
	 * @param orderNo 银行卡扣款订单编号
	 * @param orderRepayStatus   MBOrderInfoDTO.ORDER_STATUS_WAGE_REPAY_BANKCARD_CONFIRM
     * @param title 还款订单title
     */
	@Transactional
	private void updateBankCardWithholdOrder(String userId,String orderNo,String withholdStatus,String orderRepayStatus,String title,boolean flag){
		//查询扣款订单对应的还款订单
		String repayOrder = mBUserLoanOrderMapper.queryRepayOrderNos(userId,orderNo);

		mBUserLoanOrderMapper.updateApplyLoanOrder(userId,orderNo,withholdStatus,"银行卡扣款(汇总)",null);

		if(flag){
			//更新还款历史表对应记录为有效
			mBUserLoanOrderMapper.updateOrderRepayHistory(new OrderRepayHistory(orderNo));
		}


		String[] repayOrders = repayOrder.split(",");

		if(repayOrders.length == 0){
			throw new AppException("账单信息异常");
		}

		//更新还款订单为成功
		for(String repayOrderNo: repayOrders){
			mBUserLoanOrderMapper.updateApplyLoanOrder(userId,repayOrderNo,orderRepayStatus,title,null);
			if(flag){
				//更新还款历史表对应记录为有效
				mBUserLoanOrderMapper.updateOrderRepayHistory(new OrderRepayHistory(repayOrderNo));
		}
	}
	}



	//工资先享-提前还款--查询还款信息
	private List<EarlyRepaymentInfoDTO> queryWageAdvanceLoanInfoByLoanIds(String userId,String loanCodes,String repayPrincipal,String repayTotalMoney){
		List<EarlyRepaymentInfoDTO> resultList = new ArrayList<EarlyRepaymentInfoDTO>();
		BigDecimal totalMoney = new BigDecimal(0);
		String[] loanIds = formatLoanCodes(loanCodes);
		//调用信贷系统，获取借款记录列表
		BaseLogger.info(String.format("工资先享queryLoanInfoByLoanIds调用信贷系统checkRepayOrders查询入参userId:%s;loanCodes：%s;repayTotalMoney:%s",userId,loanCodes,repayTotalMoney));

		CheckRepayOrdersReqDTO reqDTO = new CheckRepayOrdersReqDTO();
		reqDTO.setUserId(userId);
		reqDTO.setLoanIds(loanIds);
		reqDTO.setRepayAmount(repayTotalMoney);
		reqDTO.setRepayPrincipal(repayPrincipal);

		CheckRepayOrdersResDTO resDTO =applyWageAdvanceDubboService.checkRepayOrders(reqDTO);

		BaseLogger.info("工资先享queryLoanInfoByLoanIds调用信贷系统checkRepayOrders查询结果："+JSONObject.toJSONString(resDTO));

		if(("200").equals(resDTO.getCode())){
			List<NoRepayOrderDetailDTO> list = resDTO.getRepayOrderDTOList();
			for(NoRepayOrderDetailDTO rod:list){
				EarlyRepaymentInfoDTO ld = new EarlyRepaymentInfoDTO();
				ld.setLoanId(rod.getLoanCode());
				ld.setInterest(rod.getOverplusInterest());
				ld.setPoundage(rod.getPoundage());
				ld.setPrincipal(rod.getOverplusPrincipal());
				ld.setTotalAmount(rod.getRepayAmountTotal());
				ld.setPenalty(rod.getOverplusPenalty());
				resultList.add(ld);
				totalMoney = totalMoney.add(new BigDecimal(ld.getTotalAmount()));
			}

		}else {
			throw new AppException("还款金额错误");
		}

		//校验是否是重复还款
		//1.2校验是否是重复还款（借款到期还款任务设定为每日循环跑）
		if(resultList.size()>0){
			for(EarlyRepaymentInfoDTO erd:resultList){
				if(mBUserLoanOrderMapper.checkWageAdvanceIsExistProcessOrder(userId,erd.getLoanId())){
					BaseLogger.info("存在处理中的订单，待处理完成后再做还款");
					throw new AppException("存在处理中的订单，待处理完成后再做还款");
				}
			}
		}

		// 校验传入金额，是否和从信贷系统中查到的需还金额相匹配
		validatorMoney(repayTotalMoney,totalMoney.toPlainString());

		return resultList;
	}


	//工资先享--提前还款--更新信贷信息
	/**
	 *
	 * @param userId
	 * @param loanCodes
	 * @param repayPrincipal
	 * @param repayTotal
	 * @param cardId
	 * @param typeFlag 是否是余额还款
     * @return
     */
	private String updateWageAdvanceRepayInfo(String userId, String loanCodes, String repayPrincipal, String repayTotal,String cardId,boolean typeFlag){
		Map<String,String> resultMap =  new HashMap<String,String>();
		String[] loanIds = formatLoanCodes(loanCodes);

		//一次还款多笔的情况
		BaseLogger.info(String.format("工资先享updateWageAdvanceRepayInfo调用信贷系统查询入参userId:%s;loanIds：%s;repayPrincipal:%s;repayTotal:%s",userId,loanCodes,repayPrincipal,repayTotal));
		SaveRepayOrderReqDTO reqDto = new SaveRepayOrderReqDTO();
		reqDto.setLoanId(loanIds);
		reqDto.setRepayPrincipal(repayPrincipal);
		reqDto.setRepayAmount(repayTotal);
		reqDto.setUserId(userId);
		reqDto.setTradeCardId(cardId);
		if(typeFlag){
			reqDto.setPayChannel(ExpireRepayReqDTO.PAY_CHANNEL_OO);
		}else{
			reqDto.setPayChannel(ExpireRepayReqDTO.PAY_CHANNEL_O1);
		}
		SaveRepayOrderResDTO resDTO = applyWageAdvanceDubboService.saveRepayOrder(reqDto);
		BaseLogger.info("工资先享updateWageAdvanceRepayInfo调用信贷系统查询结果："+JSONObject.toJSONString(resDTO));
		return  resDTO.getCode();
	}







	private void validatorWageAdvanceDueRepayParams(String userId, String loanCodes,String planId,
										 String principal,String interest, String repaymentMoney) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(loanCodes)|| StringUtils.isBlank(repaymentMoney)
				|| StringUtils.isBlank(planId)|| StringUtils.isBlank(interest)) {
			throw new AppException("参数不完整");
		}

		if (!NumberUtils.isNumber(principal) || new BigDecimal(principal).compareTo(new BigDecimal(0)) < 0) {
			throw new AppException("金额不合法");
		}
		if (!NumberUtils.isNumber(interest) || new BigDecimal(interest).compareTo(new BigDecimal(0)) < 0) {
			throw new AppException("金额不合法");
		}
		if (!NumberUtils.isNumber(repaymentMoney) || new BigDecimal(repaymentMoney).compareTo(new BigDecimal(0)) < 0) {
			throw new AppException("金额不合法");
		}

	}



	/**
	 * 工资计划 -- 扣款前操作
	 * @param userId  用户号
	 * @param cardId 银行卡id
	 * @param amount 扣款金额
	 * @return
	 */
	@Override
	public String preOperateForSalaryPlan(String userId, String cardId,String planCode,String amount){
		//1.校验参数
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(cardId)|| StringUtils.isBlank(planCode)|| StringUtils.isBlank(amount)){
			throw new AppException("参数不完整");
		}
		//转换金额（扣款金额）
		BigDecimal repayTotal = formatMoney(amount);
		// 3 生成一个扣款中的订单orderNo，该订单的rel_order_no字段保存orders
		return userAccountTransactionService.processSalaryPlanAddWithholdOrder(userId, cardId,planCode,repayTotal);
	}
	/**
	 * 工资计划--扣款处理中回调
	 * @param orderNo
	 */
	@Override
	public void callbackForSalaryPlanWithholdProcess(String userId,String orderNo){
		try{
			BaseLogger.audit("---->工资计划--扣款处理中回调-->callbackForSalaryPlanWithholdProcess-->in--");
			//1.更新账单状态
			updateSalaryPlanWithholdOrder(userId,orderNo,MBOrderInfoDTO.ORDER_STATUS_SALARY_PLAN_BANKCARD_WITHHOLD_PROCESS,
					MBOrderInfoDTO.ORDER_STATUS_SALARY_PLAN_BANKCARD_WITHHOLD_INIT,"工资计划(银行卡)扣款");
			BaseLogger.audit("---->工资计划--扣款处理中回调end-->callbackForSalaryPlanWithholdProcess-->end--");
		}catch (Exception e){
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("callbackForSalaryPlanWithholdProcess 工资计划-扣款处理中更新账单状态异常 orderNo[" + orderNo + "]"));
		}
	}
	/**
	 * 工资计划--扣款成功回调
	 * @param orderNo
	 * @param amount
	 */
	@Override
	public void callbackForSalaryPlanWithholdSuccess(String userId,String orderNo, BigDecimal amount) {
		// 1. 增加主账户余额
		userAccountMapper.updateAddMaAmount(userId, amount);
		//2.更新账单状态
		updateSalaryPlanWithholdOrder(userId,orderNo,MBOrderInfoDTO.ORDER_STATUS_SALARY_PLAN_BANKCARD_WITHHOLD_CONFIRM,
				MBOrderInfoDTO.ORDER_STATUS_SALARY_PLAN_BANKCARD_WITHHOLD_PROCESS,"工资计划(银行卡)扣款");
		//2.余额转入存钱罐
		BaseLogger.audit("---->工资计划-代扣转入余额再转入存钱罐-->applyWithholdToTa-->in--userId:"+userId+" amount:"+amount);
		mbUserAccountService.applyWithholdToTa(orderNo,userId,amount.toPlainString());
		BaseLogger.audit("---->工资计划-代扣转入余额再转入存钱罐end-->applyWithholdToTa-->in--userId:"+userId+" amount:"+amount);
	}
	/**
	 * 工资计划--扣款失败回调
	 * @param orderNo
	 * @param amount
	 */
	@Override
	public void callbackForSalaryPlanWithholdFail(String userId,String orderNo, BigDecimal amount) {
		updateSalaryPlanWithholdOrder(userId,orderNo,MBOrderInfoDTO.ORDER_STATUS_SALARY_PLAN_BANKCARD_WITHHOLD_FAIL,
				MBOrderInfoDTO.ORDER_STATUS_SALARY_PLAN_BANKCARD_WITHHOLD_PROCESS,"工资计划(银行卡)扣款");
	}

	/**
	 * 工资计划更新扣款订单
	 * @param orderNo 银行卡扣款订单编号
	 * @param orderStatus
	 * @param title 还款订单title
	 */
	@Transactional
	private void updateSalaryPlanWithholdOrder(String userId,String orderNo,String orderStatus,String oldOrderStatus,String title){
		//1.更新账单状态
		BaseLogger.audit("---->工资计划更新扣款订单-->updateSalaryPlanWithholdOrder-->in--orderNo:"+orderNo+" oldOrderStatus:"+oldOrderStatus+" title"+title);
		MBOrderInfoDTO mBOrderInfoDTO=new MBOrderInfoDTO();
		mBOrderInfoDTO.setOrderNo(orderNo);
		mBOrderInfoDTO=userAccountMapper.queryOrderInfoByOrderNo(mBOrderInfoDTO);
		userAccountMapper.updateSalaryPlanWithholdOrder(userId,orderNo,orderStatus,oldOrderStatus,title);
		BaseLogger.audit("---->工资计划更新计划状态-->updateSalaryPlanWithholdOrder-->in--orderStatus:"+orderStatus+" tradeId():"+mBOrderInfoDTO.getTradeId());
		//2.更新计划状态
		WjSalaryPlanRecordDTO wjSalaryPlanRecordDTO=new WjSalaryPlanRecordDTO();
		wjSalaryPlanRecordDTO.setId(mBOrderInfoDTO.getTradeId());
		if(MBOrderInfoDTO.ORDER_STATUS_SALARY_PLAN_BANKCARD_WITHHOLD_PROCESS.equals(orderStatus)){
			wjSalaryPlanRecordDTO.setRecordStatus(PlanConstant.PLAN_STATUS_EXECUTING.getCode());
		}else if(MBOrderInfoDTO.ORDER_STATUS_SALARY_PLAN_BANKCARD_WITHHOLD_FAIL.equals(orderStatus)){
			wjSalaryPlanRecordDTO.setRecordStatus(PlanConstant.PLAN_STATUS_EXECUTE_FAIL.getCode());
		}else if(MBOrderInfoDTO.ORDER_STATUS_SALARY_PLAN_BANKCARD_WITHHOLD_CONFIRM.equals(orderStatus)){
			wjSalaryPlanRecordDTO.setRecordStatus(PlanConstant.PLAN_STATUS_EXECUTE_COMPLETE.getCode());
		}else{
			throw new AppException("扣款订单状态无效");
		}
		wjSalaryPlanRecordMapper.updateSalaryPlanRecordStatus(wjSalaryPlanRecordDTO);
		BaseLogger.audit("---->工资计划更新扣款订单end-->updateSalaryPlanRecordStatus-->wjSalaryPlanRecordDTO:"+wjSalaryPlanRecordDTO.toString());
	}








	/**
	 * 工资先享 -- 到期还款
	 * @param reqDto
	 * @return
	 */

	public TradeSearchResultDTO wageAdvanceDueRepaymentForLoanTask(DueRepaymentCommonDTO reqDto) {
		reqDto.setOrderType(MBOrderInfoDTO.ORDER_STATUS_DUE_BANKCARD_WITHHOLD_INIT);
		return dueRepaymentService.doRepay(reqDto);
	}



	/**
	 * 工资先享 -- 到期还款扣款前操作
	 * @param userId  用户号
	 * @param cardId 银行卡id
	 * @param planId 还款计划编号
	 * @param amount 还款总额
	 * @return
	 */
	@Override
	public String preOperateForWageAdvanceDueRepayment(String userId, String cardId,String loanId,String planId,String amount){
		//1.校验参数
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(cardId)||StringUtils.isBlank(amount)){
			throw new AppException("参数不完整");
		}

		//转换金额（还款总额）
		BigDecimal repayTotal = formatMoney(amount);

		//生成针对单个的还款中订单
		String orderNo1 = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		String relLoanOrderNo = mBUserLoanOrderMapper.queryApplyLoanOrderId(userId,loanId,MBOrderInfoDTO.ORDER_TYPE_WAGE_ADVANCE_LOAN);
		mBUserLoanOrderMapper.addPledgeLoanOrder(MBOrderInfoDTO.getWageAdvanceDueRepaySingleWithhold(userId, orderNo1, repayTotal,cardId,planId,relLoanOrderNo));

		//生成银行卡扣款中的订单
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		mBUserLoanOrderMapper.addPledgeLoanOrder(MBOrderInfoDTO.getDueBankCardWithhold(userId, orderNo, repayTotal,repayTotal,cardId,loanId,orderNo1));

		return orderNo;

	}

	/**
	 * 工资先享-到期还款成功回调
	 * @param userId
	 * @param orderNo
	 * @param amount
	 * @param withholdStatus
	 * @param orderRepayStatus
     * @param title
     */
	@Override

	public void callbackForDueRepaymentWithholdSuccess(String userId, String orderNo, String amount, String withholdStatus, String orderRepayStatus, String title) {
		//查询扣款的信息
		DueRepaymentBankCardWithholdDTO orderInfoQueryDTO = mBUserLoanOrderMapper.queryWageAdvanceRepayInfo(userId,orderNo);

		try{
			//1.更新金额和账单
			updateupdateWageAdvanceLoanDueOrder(userId,orderNo,orderInfoQueryDTO.getRepayOrderNo(),amount,withholdStatus,orderRepayStatus,title);

			//调用信贷系统
			ExpireRepayResDTO result = updateWageAdvanceLoanDueRepayInfo(userId,orderInfoQueryDTO.getLoanCode(),orderInfoQueryDTO.getRepaymentTotalMoney(),orderInfoQueryDTO.getPlanId(),orderInfoQueryDTO.getCardId());

			if(result.getCode().equals("600")){
				BaseLogger.error("工资先享提前还款(银行卡) 扣款成功，但是调用信贷系统更新异常");
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("工资先享提前还款(银行卡) 扣款成功，回调时调用信贷系统异常 入参 "+JSONObject.toJSONString(orderInfoQueryDTO)));
			}

			//插入还款历史表
			//生成一个无效的还款历史记录，如果还款成功后，将更新该条记录
			mBUserLoanOrderMapper.addOrderRepayHistory(new OrderRepayHistory(orderNo,"0.0000","0.0000","0.0000","0.0000",OrderRepayHistory.IS_VALID_YES));
			mBUserLoanOrderMapper.addOrderRepayHistory(new OrderRepayHistory(orderInfoQueryDTO.getRepayOrderNo(),result.getPrincipal(),result.getInterest(),result.getPoundage(),result.getPenalty(),OrderRepayHistory.IS_VALID_YES));

		}catch (Exception e){
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("工资先享提前还款(银行卡) 扣款成功，回调异常 userId[" + userId + "]"));
		}

	}


	@Transactional
	private void updateupdateWageAdvanceLoanDueOrder(String userId, String orderNo,String repayOrder, String amount, String withholdStatus, String orderRepayStatus, String title){
		//1. 入ln账户增加还款金额
		userAccountMapper.updateAddLnAmount(userId,new BigDecimal(amount));

		//2. 更新账单状态
		mBUserLoanOrderMapper.updateApplyLoanOrder(userId,orderNo,withholdStatus,"银行卡扣款(汇总)",null);

		//更新还款订单为成功
		mBUserLoanOrderMapper.updateApplyLoanOrder(userId,repayOrder,orderRepayStatus,title,null);
	}


	//到期还款更新贷款信息
	private ExpireRepayResDTO updateWageAdvanceLoanDueRepayInfo(String userId,String loanCode,String repayAmount,String planId,String cardId){
		ExpireRepayReqDTO reqDTO = new ExpireRepayReqDTO(userId,loanCode,repayAmount,planId,ExpireRepayReqDTO.PAY_CHANNEL_O1,cardId);
		BaseLogger.info("工资先享到期还款调用信贷系统入参："+ JSONObject.toJSONString(reqDTO));
		ExpireRepayResDTO resDTO = applyWageAdvanceDubboService.expireUpdateRepay(reqDTO);
		BaseLogger.info("工资先享到期还款调用信贷系统查询结果："+ JSONObject.toJSONString(resDTO));
		return resDTO;
	}

	/**
	 * 工资先享-到期还款失败回调
	 * @param userId
	 * @param orderNo
	 * @param withholdStatus
	 * @param orderRepayStatus
     * @param title
     */
	@Override
	public void callbackForDueRepaymentWithholdFail(String userId, String orderNo, String withholdStatus, String orderRepayStatus, String title) {
		try{
			//1.查询账单信息
			DueRepaymentBankCardWithholdDTO repayOrderInfo = mBUserLoanOrderMapper.queryWageAdvanceRepayInfo(userId,orderNo);
			String loanCode = repayOrderInfo.getLoanCode();
			String planId = repayOrderInfo.getPlanId();
			String repayAmount = repayOrderInfo.getRepaymentTotalMoney();

			//2.更新账单
			updateBankCardWithholdOrder(userId,orderNo,repayOrderInfo.getRepayOrderNo(),withholdStatus,orderRepayStatus,title);

			//3.判断是否是超时回调，如果是超时回调，则继续往下走
			if(validatorIsCallbackOvertime(repayOrderInfo.getCreateDate())){
				TradeSearchResultDTO result = null;

				//3.1.如果还有没有循环到的银行卡，则继续扣款
				List<BindingCardDTO> cardList = withholdServiceFacade.queryWithholeCardList(userId,MBOrderInfoDTO.ORDER_STATUS_DUE_BANKCARD_WITHHOLD_INIT);
				//如果为空，则终止
				if (!cardList.isEmpty()) {
					//组装扣款实体
					DueRepaymentBankCardWithholdDTO paramDto = new DueRepaymentBankCardWithholdDTO(userId, "", loanCode, planId, repayAmount);
					//循环用户银行卡进行还款
					for (BindingCardDTO bcd : cardList) {
						//可扣款条件:1.已经绑定了第三方支付(不需要再发送验证码)；2.是渠道支持的银行卡；3.对应银行政策运营
						if (bcd.getIsBindingThird().equals("Y") && bcd.getIsSupportCard().equals("Y") && bcd.getStatus().equals("normal")) {
							//判断该张银行卡今天是否针对某笔还款计划扣过款
							Boolean flag = validatorBankCardIsWithhold(bcd.getCardId(),planId,MBOrderInfoDTO.ORDER_TYPE_WAGE_DUE_REPAY_BANKCARD);
							//如果还过，则不再扣款
							if(!flag){
								paramDto.setCardId(bcd.getCardId());
								BaseLogger.info("超时回调后继续执行的银行卡id:"+bcd.getCardId());
								//调用扣款
								result = dueRepaymentService.doCutBankcardAmount(paramDto);
								if (result.isFailed()) {
									continue;
								} else {
									break;
								}
							}
						}
					}
				}

				//2.如果都失败，则从存钱罐中扣款
				if(result == null || result.isFailed()) {
					BaseLogger.info("超时回调后继续执行从存钱罐扣钱");
					//做存钱罐赎回操作,并从余额扣款
					dueRepaymentService.cutAmountFromTa(userId,new BigDecimal(repayOrderInfo.getRepaymentTotalMoney()),loanCode,planId);
				}

			}
		}catch (Exception e){
			BaseLogger.error("银行卡扣款失败回调异常",e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("callbackForWageAdvanceFail 工资先享到期还款(银行卡) 失败回调异常 userId[" + userId + "]"));
		}
	}

	@Transactional
	private void updateBankCardWithholdOrder(String userId, String orderNo, String repayOrderNo,String withholdStatus, String orderRepayStatus, String title){
             //1.更新汇总账单
			mBUserLoanOrderMapper.updateApplyLoanOrder(userId,orderNo,withholdStatus,"银行卡扣款(汇总)",null);

			//2.更新还款账单
		mBUserLoanOrderMapper.updateApplyLoanOrder(userId,repayOrderNo,orderRepayStatus,title,null);
	}


	/**
	 * 校验回调是否超时(6秒即为超时)
	 * @param createDate 订单创建时间
	 * @return
	 * @desc 如果当前时间-订单创建时间>6秒，即视为超时
     */
	private boolean validatorIsCallbackOvertime(String createDate){

		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = mBUserLoanOrderMapper.queryCurrentTime();
		Date currentDate = null;//当前日期
		Date orderDate = null;//订单日期
		try {
			orderDate = format.parse(createDate);//转换日期
			currentDate = format.parse(currentTime);//转换日期
		} catch (ParseException e) {
			BaseLogger.error(e);
			return  false;
		}

		//如果当前时间-订单创建时间>6秒，即视为超时
		long overtime =currentDate.getTime()-orderDate.getTime();
		BaseLogger.info("判断是否超时回调时间overtime："+overtime);
		return overtime > 6000;
	}

	/**
	 * 判断该张银行卡今天是否针对某笔还款计划扣过款
	 * @param cardId
	 * @param planId
     * @return
     */
	private boolean validatorBankCardIsWithhold(String cardId,String planId,String orderType){
		return mBUserLoanOrderMapper.validatorBankCardIsWithhold(cardId,planId,orderType);
	}


	/*****************************************************************************************京东金融产品****************************************************************************************/


	/**
	 * 贷款申请
	 *
	 * @param userId
	 * @param amount
	 * @param loanId
	 */
	@Transactional
	public void applyVjSelfSupportLoan(String userId, String amount, String loanId) {

		//1.合账:检查用户账户以及相关订单是否匹配
		//checkUserAccountBalanceService.checkUserAccountBalance(userId);

		BigDecimal applyAmount = new BigDecimal(amount);

		//2.生成一个借款成功订单
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		mBUserLoanOrderMapper.addPledgeLoanOrder(MBOrderInfoDTO.getVjSelfSupportLoanApplyOrder(userId, orderNo, applyAmount, loanId, ""));

		//3. ln账户增加借款金额
		userAccountMapper.updateAddLnAmount(userId, (applyAmount).negate());
}


	/**
	 * 到期还款
	 *
	 * @param
	 * @return
	 */

	public TradeSearchResultDTO vjSelfLoanDueRepaymentForLoanTask(String userId, String loanCode, String planId, String repayAmount) {

		if(StringUtils.isBlank(userId) || StringUtils.isBlank(loanCode) || StringUtils.isBlank(planId) || StringUtils.isBlank(repayAmount)){
			return TradeSearchResultDTO.buildFailed("参数不合法");
		}

		if(mBUserLoanOrderMapper.checkVjSelfLoanIsDueRepeatRepay(userId,planId)){
			return TradeSearchResultDTO.buildProcess("重复的扣款申请，扣款已在处理中");
		}


		List<BindingCardDTO> cardList = withholdServiceFacade.querySecurityWithholdCard(userId,MBOrderInfoDTO.ORDER_TYPE_VJ_DUE_BANKCARD_WITHHOLD);
		if (cardList.isEmpty()) {
			BaseLogger.error("京东金融到期扣款卡信息异常："+JSONObject.toJSONString(cardList));
			return TradeSearchResultDTO.buildFailed("京东金融到期扣款卡信息异常:卡数量"+JSONObject.toJSONString(cardList));
		}

		BindingCardDTO bcd = null ;//
		for(BindingCardDTO cardDTO : cardList){
			if(cardDTO.getIsBindingThird().equals("Y") && cardDTO.getIsSupportCard().equals("Y") && cardDTO.getStatus().equals("normal")){
				String cardNo = cardDTO.getCardNo();
				boolean flag = mBUserLoanOrderMapper.checkIsSecurityCard(cardNo);
				if(flag){
					bcd = cardDTO;
					break;
				}
			}
		}

		if(bcd == null){
			BaseLogger.error("京东金融到期扣款卡信息异常："+JSONObject.toJSONString(cardList));
			return TradeSearchResultDTO.buildFailed("京东金融到期扣款卡信息异常:"+JSONObject.toJSONString(cardList));
		}

		//组装扣款实体
		DueRepaymentBankCardWithholdDTO paramDto = new DueRepaymentBankCardWithholdDTO(userId, "", loanCode, planId, repayAmount);

		//可扣款条件:1.已经绑定了第三方支付(不需要再发送验证码)；2.是渠道支持的银行卡；3.对应银行政策运营
		if (bcd.getIsBindingThird().equals("Y") && bcd.getIsSupportCard().equals("Y") && bcd.getStatus().equals("normal")) {
			paramDto.setCardId(bcd.getCardId());
			BaseLogger.info("开始扣款入参:"+JSONObject.toJSONString(bcd));
			//调用扣款
			TradeSearchResultDTO bankResult = vjDueRepaymentService.doCutBankcardAmount(paramDto);

			return bankResult;
		}

		return TradeSearchResultDTO.buildFailed("扣款失败，卡不符合扣款条件："+JSONObject.toJSONString(bcd));
	}


	/**
	 * 到期还款扣款前操作
	 *
	 * @param userId 用户号
	 * @param cardId 银行卡id
	 * @param planId 还款计划编号
	 * @param amount 还款总额
	 * @return
	 */
	public String preOperateForVjSelfLoanDueRepayment(String userId, String cardId, String loanId, String planId, String amount) {
		//1.校验参数
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(cardId) || StringUtils.isBlank(amount)) {
			throw new AppException("参数不完整");
		}

		//转换金额（还款总额）
		BigDecimal repayTotal = formatMoney(amount);

		//生成针对单个的还款中订单
		String orderNo1 = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		String relLoanOrderNo = mBUserLoanOrderMapper.queryApplyLoanOrderId(userId, loanId, MBOrderInfoDTO.ORDER_TYPE_VJ_LOAN);
		mBUserLoanOrderMapper.addPledgeLoanOrder(MBOrderInfoDTO.getVjSelfLoanDueRepaySingleWithhold(userId, orderNo1, repayTotal, cardId, planId, relLoanOrderNo));

		//生成银行卡扣款中的订单
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		mBUserLoanOrderMapper.addPledgeLoanOrder(MBOrderInfoDTO.getVjSelfLoanDueBankCardWithhold(userId, orderNo, repayTotal, repayTotal, cardId, loanId, orderNo1));

		return orderNo;

	}

	/**
	 * 到期还款成功回调
	 *
	 * @param userId
	 * @param orderNo
	 * @param amount
	 * @param withholdStatus
	 * @param orderRepayStatus
	 * @param title
	 */
	@Override
	public void callbackForVjSelfLoanDueRepaymentWithholdSuccess(String userId, String orderNo, String amount, String withholdStatus, String orderRepayStatus, String title) {
		//查询扣款的信息
		DueRepaymentBankCardWithholdDTO orderInfoQueryDTO = mBUserLoanOrderMapper.queryWageAdvanceRepayInfo(userId, orderNo);

		try {
			//1.更新金额和账单
			updateupdateWageAdvanceLoanDueOrder(userId, orderNo, orderInfoQueryDTO.getRepayOrderNo(), amount, withholdStatus, orderRepayStatus, title);

			//调用信贷系统
			com.zhuanyi.vjwealth.loan.jd.dto.ExpireRepayResDTO result = updateVjSelfLoanDueRepayInfo(userId,orderInfoQueryDTO.getLoanCode(),orderInfoQueryDTO.getRepaymentTotalMoney(),orderInfoQueryDTO.getPlanId(),orderInfoQueryDTO.getCardId());

			if(result.getCode().equals("600")){
				BaseLogger.error("callbackForVjSelfLoanDueRepaymentWithholdSuccess 扣款成功，但是调用信贷系统更新异常");
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("callbackForVjSelfLoanDueRepaymentWithholdSuccess 扣款成功，回调时调用信贷系统异常 入参 "+JSONObject.toJSONString(orderInfoQueryDTO)));
			}

			//插入还款历史表
			//生成一个无效的还款历史记录，如果还款成功后，将更新该条记录
			mBUserLoanOrderMapper.addOrderRepayHistory(new OrderRepayHistory(orderNo, "0.0000", "0.0000", "0.0000", "0.0000", OrderRepayHistory.IS_VALID_YES));
			mBUserLoanOrderMapper.addOrderRepayHistory(new OrderRepayHistory(orderInfoQueryDTO.getRepayOrderNo(), result.getPrincipal(), result.getInterest(), result.getPoundage(), result.getPenalty(), OrderRepayHistory.IS_VALID_YES));

		} catch (Exception e) {
			BaseLogger.error("vj自营信贷到期还款(银行卡) 扣款成功，回调异常："+e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("vj自营信贷到期还款(银行卡) 扣款成功，回调异常 userId[" + userId + "]"));
		}

	}


	//到期还款更新贷款信息
	private com.zhuanyi.vjwealth.loan.jd.dto.ExpireRepayResDTO updateVjSelfLoanDueRepayInfo(String userId, String loanCode, String repayAmount, String planId, String cardId) {

		com.zhuanyi.vjwealth.loan.jd.dto.ExpireRepayReqDTO reqDTO = new com.zhuanyi.vjwealth.loan.jd.dto.ExpireRepayReqDTO(userId, loanCode, repayAmount, planId, ExpireRepayReqDTO.PAY_CHANNEL_O1, cardId);
		BaseLogger.info("vj自营信贷到期还款调用信贷系统入参：" + JSONObject.toJSONString(reqDTO));
		com.zhuanyi.vjwealth.loan.jd.dto.ExpireRepayResDTO resDTO = jdLoanDubboService.expireUpdateRepay(reqDTO);
		BaseLogger.info("vj自营信贷到期还款调用信贷系统查询结果：" + JSONObject.toJSONString(resDTO));

		return resDTO;
	}

	/**
	 * 到期还款失败回调
	 *
	 * @param userId
	 * @param orderNo
	 * @param withholdStatus
	 * @param orderRepayStatus
	 * @param title
	 */
	@Override
	public void callbackForVjSelfLoanDueRepaymentWithholdFail(String userId, String orderNo, String withholdStatus, String orderRepayStatus, String title) {
		try {
			//1.查询账单信息
			DueRepaymentBankCardWithholdDTO repayOrderInfo = mBUserLoanOrderMapper.queryWageAdvanceRepayInfo(userId, orderNo);
			String loanCode = repayOrderInfo.getLoanCode();
			String planId = repayOrderInfo.getPlanId();
			//String repayAmount = repayOrderInfo.getRepaymentTotalMoney();

			//2.更新账单
			updateBankCardWithholdOrder(userId, orderNo, repayOrderInfo.getRepayOrderNo(), withholdStatus, orderRepayStatus, title);

			//3.调用信贷，置逾期
			DueRepayDTO dueRepayDTO = new DueRepayDTO();
			dueRepayDTO.setLoanId(loanCode);
			dueRepayDTO.setPlanId(planId);
			//dueRepayDTO.setUserId(userId);
			jdLoanDubboService.makePlanOverdue(dueRepayDTO);

		} catch (Exception e) {
			BaseLogger.error("银行卡扣款失败回调异常", e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("callbackForVjSelfLoanDueRepaymentWithholdFail vj自营信贷到期还款(银行卡) 失败回调异常 userId[" + userId + "]"));
		}
	}
	/************************************************************************************************我是分割线********************************************************************/
	
	
	/*****************************************************************************************PICC产品****************************************************************************************/


	/**
	 * 贷款申请
	 *	
	 * @param userId
	 * @param amount
	 * @param loanId
	 */
	@Transactional
	public void applyPiccSupportLoan(String userId, String amount, String loanId) {
		//1.合账:检查用户账户以及相关订单是否匹配
		//checkUserAccountBalanceService.checkUserAccountBalance(userId);

		BigDecimal applyAmount = new BigDecimal(amount);

		//2.生成一个借款成功订单
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		mBUserLoanOrderMapper.addPledgeLoanOrder(MBOrderInfoDTO.getPiccSupportLoanApplyOrder(userId, orderNo, applyAmount, loanId, ""));

		//3. ln账户增加借款金额
		userAccountMapper.updateAddLnAmount(userId, (applyAmount).negate());
}


	/**
	 * 到期还款
	 *
	 * @param
	 * @return
	 */
	public TradeSearchResultDTO piccLoanDueRepaymentForLoanTask(String userId, String loanCode, String planId, String repayAmount,String bankCardNo) {
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(loanCode) || StringUtils.isBlank(planId) || StringUtils.isBlank(repayAmount)){
			return TradeSearchResultDTO.buildFailed("参数不合法");
		}


		if(mBUserLoanOrderMapper.checkPiccLoanIsDueRepeatRepay(userId,planId)){
			return TradeSearchResultDTO.buildProcess("重复的扣款申请，扣款已在处理中");
		}
		
		List<BindingCardDTO> cardList =null; //withholdServiceFacade.queryWithholdCard(userId,MBOrderInfoDTO.ORDER_TYPE_PICC_DUE_BANKCARD_WITHHOLD,bankCardNo);
		if (cardList.isEmpty()||cardList.size()!=1) {
			BaseLogger.info("扣款银行卡信息:"+JSONObject.toJSONString(cardList));
			return TradeSearchResultDTO.buildFailed("扣款失败");
		}
		//组装扣款实体
		DueRepaymentBankCardWithholdDTO paramDto = new DueRepaymentBankCardWithholdDTO(userId, "", loanCode, planId, repayAmount);
		//用户银行卡进行还款
		BindingCardDTO bcd = cardList.get(0);
		//可扣款条件:1.已经绑定了第三方支付(不需要再发送验证码)；2.是渠道支持的银行卡；3.对应银行政策运营
		if (bcd.getIsBindingThird().equals("Y") && bcd.getIsSupportCard().equals("Y") && bcd.getStatus().equals("normal")) {
			paramDto.setCardId(bcd.getCardId());
			BaseLogger.info("开始扣款入参:"+JSONObject.toJSONString(bcd));
			//调用扣款
			TradeSearchResultDTO bankResult = piccDueRepaymentService.doCutBankcardAmount(paramDto);
			return bankResult;
		}

		return TradeSearchResultDTO.buildFailed("扣款失败");
	}


	/**
	 * 到期还款扣款前操作
	 *
	 * @param userId 用户号
	 * @param cardId 银行卡id
	 * @param planId 还款计划编号
	 * @param amount 还款总额
	 * @return
	 */
	public String preOperateForPiccLoanDueRepayment(String userId, String cardId, String loanId, String planId, String amount) {
		//1.校验参数
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(cardId) || StringUtils.isBlank(amount)) {
			throw new AppException("参数不完整");
		}

		//转换金额（还款总额）
		BigDecimal repayTotal = formatMoney(amount);

		//生成针对单个的还款中订单
		String orderNo1 = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		String relLoanOrderNo = mBUserLoanOrderMapper.queryApplyLoanOrderId(userId, loanId, MBOrderInfoDTO.ORDER_TYPE_PICC_LOAN);
		mBUserLoanOrderMapper.addPledgeLoanOrder(MBOrderInfoDTO.getPiccLoanDueRepaySingleWithhold(userId, orderNo1, repayTotal, cardId, planId, relLoanOrderNo));

		//生成银行卡扣款中的订单
		String orderNo = getId("OR", ISequenceService.SEQ_NAME_ORDER_SEQ);
		mBUserLoanOrderMapper.addPledgeLoanOrder(MBOrderInfoDTO.getPiccLoanDueBankCardWithhold(userId, orderNo, repayTotal, repayTotal, cardId, loanId, orderNo1));

		return orderNo;

	}

	/**
	 * 到期还款成功回调
	 *
	 * @param userId
	 * @param orderNo
	 * @param amount
	 * @param withholdStatus
	 * @param orderRepayStatus
	 * @param title
	 */
	public void callbackForPiccLoanDueRepaymentWithholdSuccess(String userId, String orderNo, String amount, String withholdStatus, String orderRepayStatus, String title) {
		//查询扣款的信息
		DueRepaymentBankCardWithholdDTO orderInfoQueryDTO = mBUserLoanOrderMapper.queryWageAdvanceRepayInfo(userId, orderNo);

		try {
			//1.更新金额和账单
			updateupdateWageAdvanceLoanDueOrder(userId, orderNo, orderInfoQueryDTO.getRepayOrderNo(), amount, withholdStatus, orderRepayStatus, title);

			//调用信贷系统
			com.zhuanyi.vjwealth.loan.jd.dto.ExpireRepayResDTO result = updatePiccLoanDueRepayInfo(userId,orderInfoQueryDTO.getLoanCode(),orderInfoQueryDTO.getRepaymentTotalMoney(),orderInfoQueryDTO.getPlanId(),orderInfoQueryDTO.getCardId());

			if(result.getCode().equals("600")){
				BaseLogger.error("callbackForPiccLoanDueRepaymentWithholdSuccess 扣款成功，但是调用信贷系统更新异常");
				sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("callbackForPiccLoanDueRepaymentWithholdSuccess 扣款成功，回调时调用信贷系统异常 入参 "+JSONObject.toJSONString(orderInfoQueryDTO)));
			}

			//插入还款历史表
			//生成一个无效的还款历史记录，如果还款成功后，将更新该条记录
			mBUserLoanOrderMapper.addOrderRepayHistory(new OrderRepayHistory(orderNo, "0.0000", "0.0000", "0.0000", "0.0000", OrderRepayHistory.IS_VALID_YES));
			mBUserLoanOrderMapper.addOrderRepayHistory(new OrderRepayHistory(orderInfoQueryDTO.getRepayOrderNo(), result.getPrincipal(), result.getInterest(), result.getPoundage(), result.getPenalty(), OrderRepayHistory.IS_VALID_YES));

		} catch (Exception e) {
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("PICC提前还款(银行卡) 扣款成功，回调异常 userId[" + userId + "]"));
		}

	}


	//到期还款更新贷款信息
	private com.zhuanyi.vjwealth.loan.jd.dto.ExpireRepayResDTO updatePiccLoanDueRepayInfo(String userId, String loanCode, String repayAmount, String planId, String cardId) throws Exception {

		com.zhuanyi.vjwealth.loan.jd.dto.ExpireRepayReqDTO reqDTO = new com.zhuanyi.vjwealth.loan.jd.dto.ExpireRepayReqDTO(userId, loanCode, repayAmount, planId, ExpireRepayReqDTO.PAY_CHANNEL_O1, cardId);
		BaseLogger.info("vj自营信贷到期还款调用信贷系统入参：" + JSONObject.toJSONString(reqDTO));
		com.zhuanyi.vjwealth.loan.jd.dto.ExpireRepayResDTO resDTO = null;//autoInsuranceLoanService.expireUpdateRepay(reqDTO);
		BaseLogger.info("vj自营信贷到期还款调用信贷系统查询结果：" + JSONObject.toJSONString(resDTO));

		return resDTO;
	}

	/**
	 * 到期还款失败回调
	 *
	 * @param userId
	 * @param orderNo
	 * @param withholdStatus
	 * @param orderRepayStatus
	 * @param title
	 */
	public void callbackForPiccLoanDueRepaymentWithholdFail(String userId, String orderNo, String withholdStatus, String orderRepayStatus, String title) {
		try {
			//1.查询账单信息
			DueRepaymentBankCardWithholdDTO repayOrderInfo = mBUserLoanOrderMapper.queryWageAdvanceRepayInfo(userId, orderNo);
			String loanCode = repayOrderInfo.getLoanCode();
			String planId = repayOrderInfo.getPlanId();
//			String repayAmount = repayOrderInfo.getRepaymentTotalMoney();

			//2.更新账单
			updateBankCardWithholdOrder(userId, orderNo, repayOrderInfo.getRepayOrderNo(), withholdStatus, orderRepayStatus, title);

			//3.扣款失败，置逾期
			DueRepayDTO req = new DueRepayDTO();
			req.setLoanId(loanCode);
			req.setPlanId(planId);
			req.setUserId(userId);
//			autoInsuranceLoanService.makePlanOverdue(req);
		} catch (Exception e) {
			BaseLogger.error("银行卡扣款失败回调异常", e);
			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_HIGH", pageEmailMap("callbackForPiccLoanDueRepaymentWithholdFail PICC自营信贷到期还款(银行卡) 失败回调异常 userId[" + userId + "]"));
		}
	}
	/************************************************************************************************我是分割线********************************************************************/

}




