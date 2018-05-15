package com.zhuanyi.vjwealth.wallet.mobile.credit.server.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.loan.liutongbao.webservice.IApplyLoanLtbDubboService;
import com.zhuanyi.vjwealth.loan.product.webservice.IProductInfoDubboService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IPledgeInvestService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.FinancialLoanMapper;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.IPledgeInvestMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.ApplyLoanResultDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.dto.LoanResultCommonDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.order.server.service.IMBUserOrderService;
import com.zhuanyi.vjwealth.wallet.service.outer.message.dto.MessageDTO;
import com.zhuanyi.vjwealth.wallet.service.outer.message.server.service.IPhoneMessageService;

/**
 * Created by wangzhangfei on 16/7/12.
 */
@Service
public class PledgeInvestServiceImpl implements IPledgeInvestService {
	@Autowired
	private IPledgeInvestMapper pledgeInvestMapper;
	
	@Autowired
	private IProductInfoDubboService productInfoDubboService;
	
	@Remote
	private IPhoneMessageService phoneMessageService;
	
	@Remote
	private IMBUserOrderService mBUserOrderService;
	
	@Autowired
	private FinancialLoanMapper financialLoanMapper;
	
	@Autowired
	private IApplyLoanLtbDubboService applyLoanLtbDubboService;

	@Override
	public Object pledgeInvestIniti(String userId,String loanProductId, String page) {
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(page)){
			throw new AppException("参数不能为空");
		}

		//查询用户的可借金额和投资总额
		PledgeInvestInitiDTO pii = pledgeInvestMapper.queryPledgeInvestIniti(userId);
		pii.setTotalCanBorrowAmountLabel(pii.getTotalCanBorrowAmountLabel()+pii.getTotalCanBorrowAmount());
		if(pii == null){
			throw new AppException("数据异常");
		}
		pii.setIsShowRepaymentButton(pii.getIsShowRepaymentButton().equals("0")?"false":"true");
		
		//调用信贷系统：查询日利率
		String rate = getDayRate(loanProductId);
		
		BigDecimal rateB = new BigDecimal(rate);
		pii.setCanBorrowAmountInterest(rateB.multiply(new BigDecimal(100)).setScale(3,BigDecimal.ROUND_UNNECESSARY).toString()); //配合APP展示做处理
		String desc = "<font color=#5D7A8D>1千元用1天利息只需</font><font color=#FFBD30>"+rateB.multiply(new BigDecimal(1000)).setScale(2, BigDecimal.ROUND_HALF_UP)+"</font><font color=#5D7A8D>元</font>";
		pii.setCanBorrowAmountInterestDescription(desc);

		List<PledgeInvestInitiInnerOrderInfoDTO> list;
		if(pledgeInvestMapper.queryFinacialRewardSendInfo(userId)){
			//查询用户可借金额对应的投资记录信息
			list = pledgeInvestMapper.queryPledgeInvestInitiInnerOrderListLimit(userId,(Integer.parseInt(page)-1)*10);
		}else{
			list = pledgeInvestMapper.queryPledgeInvestInitiInnerOrderList(userId,(Integer.parseInt(page)-1)*10);
		}

		
		if(list == null){
			list = new ArrayList<PledgeInvestInitiInnerOrderInfoDTO>();
			
		}
		if(list==null || list.size() == 0){
			pii.setIsMore("false");
			pii.setNoBorrowInvestTip("<font color=#9B9B9B>抱歉，目前没有可匹配的理财订单|理财金额须大于</font><font color=#58CFF9>600</font><font color=#9B9B9B>元</font>");
		}
		if(list.size()>0){
			for(PledgeInvestInitiInnerOrderInfoDTO pd:list){
				pd.setAnnualYield(handleNumberStr(pd.getAnnualYield()));
			}
		}
		
		if(list.size()>=10){
			pii.setIsMore("true");
		}else{
			pii.setIsMore("false");
		}
		
		pii.setRecords(list);
		
		return pii;
	}

	@Override
	public Object pledgeInvestBorrowIniti(String userId, String loanProductId, String orderId) {
		//参数验证
		validateUserId(userId);
		validateOrderId(orderId);

		//查询卡号,可借最大额度,可借最大天数
		PledgeInvestBorrowInitiDTO pledgeInvestBorrowInitiDTO = getPledgeInvestBorrowInitiDTO(userId,loanProductId,orderId);

		//查询V理财订单信息
		PledgeInvestInitiInnerOrderInfoDTO  borrowOrder  = 	pledgeInvestMapper.queryPledgeInvestBorrowOrderInfo(userId,orderId);
		if(null == borrowOrder){
			borrowOrder =  new PledgeInvestInitiInnerOrderInfoDTO();
			BaseLogger.error("borrowOrder 为空");
			throw new AppException("该用户不符合借款条件");
		}
		borrowOrder.setAnnualYield(handleNumberStr(borrowOrder.getAnnualYield()));
		pledgeInvestBorrowInitiDTO.setBorrowOrder(borrowOrder);


		return pledgeInvestBorrowInitiDTO;
	}

	@Override
	public Object pledgeInvestBorrowInitiV2(String userId, String loanProductId, String orderId) {
		//参数验证
		validateUserId(userId);
		validateOrderId(orderId);

		//查询卡号,可借最大额度,可借最大天数
		PledgeInvestBorrowInitiDTO pledgeInvestBorrowInitiDTO = getPledgeInvestBorrowInitiDTO(userId,loanProductId,orderId);

		PledgeInvestBorrowInitiV2DTO initiV2DTO = new PledgeInvestBorrowInitiV2DTO();
		BeanUtils.copyProperties(pledgeInvestBorrowInitiDTO,initiV2DTO);//拷贝属性

		//查询V理财订单信息
		PledgeInvestInitiInnerOrderInfoDTO  borrowOrder  = 	pledgeInvestMapper.queryPledgeInvestBorrowOrderInfo(userId,orderId);
		if(null == borrowOrder){
			borrowOrder =  new PledgeInvestInitiInnerOrderInfoDTO();
			BaseLogger.error("borrowOrder 为空");
			throw new AppException("该用户不符合借款条件");
		}

		initiV2DTO.setGuaranteeProjectLabel("担保项目");
		initiV2DTO.setGuaranteeProject(borrowOrder.getProductCode());
		initiV2DTO.setGuaranteeAmountLabel("担保金额");
		initiV2DTO.setGuaranteeAmount(borrowOrder.getInvestmentAmount()+"元");
		String desc = "<font color=#B8CDD6>最少借</font><font color=#FFBD30>"+initiV2DTO.getCanBorrowMinAmount()+"</font><font color=#B8CDD6>元, 最多可借</font><font color=#FFBD30>"+initiV2DTO.getCanBorrowMaxAmount()+"</font><font color=#B8CDD6>元</font>";
		initiV2DTO.setLoanAmountTip(desc);

		return initiV2DTO;
	}

	/**
	 * 验证理财订单ID
	 * @param orderId
     */
	private void validateOrderId(String orderId) {
		if (StringUtils.isEmpty(orderId)) {
			throw new AppException("理财订单编号不能为空");
		}
	}

	/**
	 * 验证用户ID
	 * @param userId
     */
	private void validateUserId(String userId) {
		if (StringUtils.isEmpty(userId)) {
			throw new AppException("用户编号不能为空");
		}
	}

	private  PledgeInvestBorrowInitiDTO getPledgeInvestBorrowInitiDTO(String userId, String loanProductId, String orderId){
		BaseLogger.audit(String.format("流通宝-初始化[getPledgeInvestBorrowInitiDTO] 输入 userId : [%s],loanProductId : [%s],orderId : [%s] ",userId, loanProductId,orderId));
        //校验
        if(StringUtils.isBlank(loanProductId) || StringUtils.isBlank(orderId) ){
            throw new AppException("参数不能为空");
        }

		//查询卡号,可借最大额度,可借最大天数
		PledgeInvestBorrowInitiDTO pledgeInvestBorrowInitiDTO = pledgeInvestMapper.queryPledgeInvestBorrowIniti(userId,orderId);

		if (null == pledgeInvestBorrowInitiDTO) {
			BaseLogger.error("pledgeInvestBorrowInitiDTO 为空");
			throw new AppException("该用户不符合借款条件");
		}
		//调用信贷系统：查询日利率
		String rate = getDayRate(loanProductId);

		//可借最大额度（最大可借额度） X <=理财本金 (最小可借金额 500 元 年)
		String canBorrowMaxAmount = pledgeInvestBorrowInitiDTO.getCanBorrowMaxAmount();
		//收益总和
		String  incomeSum = pledgeInvestMapper.queryIncomeSumByUserIdAndOrderId(userId,orderId);
		BaseLogger.audit(String.format("流通宝- orderId : [%s], incomeSum : [%s]",orderId,incomeSum));
		//可借最大天数
		Integer canBorrowMaxDay = calcCanBorrowMaxDay(incomeSum,rate,canBorrowMaxAmount,pledgeInvestBorrowInitiDTO.getCanBorrowMaxDay());
		//设置
		pledgeInvestBorrowInitiDTO.setCanBorrowMaxDay(String.valueOf(canBorrowMaxDay));
		pledgeInvestBorrowInitiDTO.setCanBorrowMaxDayTip("最多可借"+String.valueOf(canBorrowMaxDay));

		return pledgeInvestBorrowInitiDTO;
	}


	/**
	 * 可借最大天数
	 * (1. 到期还本付息: 所有的收益总和/(0.035%*最多可借金额) 2. 按月付息: 剩余的收益总和/(0.035%*最多可借金额) 以上计算出的结果和理财剩余到期天数比。取最小值为准。)
	 * @param incomeSum 收益总和
	 * @param rate 日利率
	 * @param canBorrowMaxAmount 最多可借金额
	 * @param expirationDays 理财剩余到期天数
	 * @return
	 */
	private Integer calcCanBorrowMaxDay(String incomeSum,String rate,String canBorrowMaxAmount,String expirationDays){

		BaseLogger.audit(String.format("流通宝-可借最大天数 输入参数incomeSum : [%s], rate : [%s], canBorrowMaxAmount : [%s], expirationDays : [%s] ",incomeSum,rate,canBorrowMaxAmount,expirationDays));
		Integer	canBorrowMaxDay = 0;
		Integer  days =  Integer.parseInt(expirationDays) ; // 理财剩余到期天数
		BigDecimal borrowMaxAmount = new BigDecimal(canBorrowMaxAmount); //最多可借金额
		BigDecimal rateb = new BigDecimal(rate);//日利率
		BigDecimal incomeSumAmount = new BigDecimal(incomeSum);//收益总和

		//计算: 1. 到期还本付息: 所有的收益总和/(0.035%*最多可借金额) 2. 按月付息: 剩余的收益总和/(0.035%*最多可借金额) 以上计算出的结果和理财剩余到期天数比。取最小值为准。
		canBorrowMaxDay = (incomeSumAmount.divide(borrowMaxAmount.multiply(rateb),2)).setScale(0,BigDecimal.ROUND_DOWN).intValue();

		Integer calcDay = days.compareTo(canBorrowMaxDay) > 0 ? canBorrowMaxDay : days;
		BaseLogger.audit(String.format("流通宝-可借最大天数 输出 calcDay : [%s] ", calcDay));
		return calcDay;
	}

	@Override
	public Object dynamicallyGeneratedInterest(String loanProductId,
			String borrowAmount, String borrowDay) {
		//校验
		if(StringUtils.isBlank(loanProductId) || StringUtils.isBlank(borrowAmount) || StringUtils.isBlank(borrowDay)){
			throw new AppException("参数不能为空");
		}
		if(!StringUtils.isNumeric(borrowAmount)){
			throw new AppException("借款金额不合法");
		}
		if(!StringUtils.isNumeric(borrowDay)){
			throw new AppException("借款天数不合法");
		}
		
		//调用信贷系统：查询日利率
		String rate = getDayRate(loanProductId);
		
		PledgeLoanTrialInterestDTO pti = new PledgeLoanTrialInterestDTO();
		try{
			
			BigDecimal rateb = new BigDecimal(rate);//日利率
			BigDecimal amountb = new BigDecimal(borrowAmount);//借款金额
			BigDecimal days = new BigDecimal(borrowDay);//借款天数
//			BigDecimal interest = amountb.multiply(days).multiply(rateb).setScale(2,BigDecimal.ROUND_HALF_UP);//计算利息=金额*天数*日利率
			BigDecimal interest = amountb.multiply(days).multiply(rateb).setScale(2,BigDecimal.ROUND_UP);//计算利息=金额*天数*日利率
			
			//计算还款日期
			Calendar cl = Calendar.getInstance();
			cl.setTime(new Date());
			cl.add(Calendar.DAY_OF_MONTH, Integer.parseInt(borrowDay));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String repayDate = format.format(cl.getTime());
			
			//设置返回结果
			pti.setRepaymentDate(repayDate);
			pti.setTotalInterest(interest.toPlainString());
			pti.setRepaymentPrincipalAndInterest(amountb.add(interest).toPlainString());
			
		}catch (Exception e){
			BaseLogger.error(String.format("试计算利息失败borrowAmount:%d;borrowDay:%d", borrowAmount,borrowDay));
			throw new AppException("试计算利息失败，请检查借款金额和天数是否合法");
		}
		
		return pti;
	}

	@Override
	public Object dynamicallyGeneratedBorrowDay(String userId,
			String orderId,String loanProductId,
			String borrowAmount) {

		//校验
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(orderId) || StringUtils.isBlank(loanProductId) || StringUtils.isBlank(borrowAmount) ){
			throw new AppException("参数不能为空");
		}
		if(!StringUtils.isNumeric(borrowAmount)){
			throw new AppException("借款金额不合法");
		}
		Integer canBorrowMaxDay = getCanBorrowMaxDay(userId, orderId, loanProductId, borrowAmount);

		//设置
		PledegInvestCalcBorrowMaxDayDTO pledegInvestCalcBorrowMaxDayDTO = new PledegInvestCalcBorrowMaxDayDTO();
		pledegInvestCalcBorrowMaxDayDTO.setCanBorrowMaxDay(String.valueOf(canBorrowMaxDay));
		pledegInvestCalcBorrowMaxDayDTO.setCanBorrowMaxDayTip("最多可借"+String.valueOf(canBorrowMaxDay));

		return pledegInvestCalcBorrowMaxDayDTO;

	}

	private Integer getCanBorrowMaxDay(String userId, String orderId, String loanProductId, String borrowAmount) {
		//查询卡号,可借最大额度,可借最大天数
		PledgeInvestBorrowInitiDTO pledgeInvestBorrowInitiDTO = pledgeInvestMapper.queryPledgeInvestBorrowIniti(userId,orderId);
		if (null == pledgeInvestBorrowInitiDTO) {
			BaseLogger.error("getCanBorrowMaxDay pledgeInvestBorrowInitiDTO 为空");
			throw new AppException("该用户不符合借款条件");
        }
		//下单的时候做金额及天数验证,计算的时候不做验证
		//调用信贷系统：查询日利率
		String rate = getDayRate(loanProductId);
		//收益总和
		String  incomeSum = pledgeInvestMapper.queryIncomeSumByUserIdAndOrderId(userId,orderId);

		//可借最大天数
		return calcCanBorrowMaxDay(incomeSum,rate,borrowAmount,pledgeInvestBorrowInitiDTO.getCanBorrowMaxDay());
	}

	@Override
	public String informationConfirmationSendSMSNotice(String userId) {
		//根据userId 查询用户手机号
		String phone = pledgeInvestMapper.queryUserPhoneByUserId(userId);
		if(StringUtils.isBlank(phone)){
			throw new AppException("数据异常：用户手机号不存在");
		}
		//获取验证码
		return sendSMSNotice(phone);
	}

	@Override
	public String informationConfirmationSendToneNotice(String userId) {
		//根据userId 查询用户手机号
		String phone = pledgeInvestMapper.queryUserPhoneByUserId(userId);
		if(StringUtils.isBlank(phone)){
			throw new AppException("数据异常：用户手机号不存在");
		}
		//获取验证码
		return sendToneNotice(phone);
	}
	
	//获取短信验证码
    private String sendSMSNotice(String phone){
    	MessageDTO md = phoneMessageService.sendTextMessage(phone, "settlement_platform_default");
    	if (!md.getSendFlag()) {
			throw new AppException(md.getSendFlagMessage());
		}
    	return md.getVaildeTime();
    }
    
    //获取语音验证码
    private String sendToneNotice(String phone){
    	MessageDTO md = phoneMessageService.sendToneMessage(phone, "settlement_platform_default");
    	if (!md.getSendFlag()) {
			throw new AppException(md.getSendFlagMessage());
		}
    	return md.getVaildeTime();
    }
    
    
    //校验验证码
    private void validatorCode(String phone,String code){
    	MessageDTO message = phoneMessageService.checkValidationCode(phone, code, "settlement_platform_default");
		if (!message.getSendFlag()) {
			throw new AppException(message.getSendFlagMessage());
			//return Boolean.FALSE;
		}
		//return Boolean.TRUE;
    }
	
	

	@Override
	public Object applySMSVerificationConfirm(String userId, String code,
			String orderId,String loanProductId, String borrowAmount, String borrowDay) {

        //校验
        if(StringUtils.isBlank(code) || StringUtils.isBlank(orderId) || StringUtils.isBlank(loanProductId) || StringUtils.isBlank(borrowAmount) || StringUtils.isBlank(borrowDay)){
            throw new AppException("参数不能为空");
        }
        if(!StringUtils.isNumeric(borrowAmount)){
            throw new AppException("借款金额不合法");
        }
        if(!StringUtils.isNumeric(borrowDay)){
            throw new AppException("借款天数不合法");
        }


		String phone = pledgeInvestMapper.queryUserPhoneByUserId(userId);
		if(StringUtils.isBlank(phone)){
			throw new AppException("数据异常：用户手机号不存在");
		}
		//校验验证码
		validatorCode(phone,code);
		
		// 1,校验金额，期限是否符合条件
		validatorAmount(userId,orderId,loanProductId,borrowAmount);
		// 2.验证天数合法
		validatorBorrowDay(userId,orderId,loanProductId,borrowAmount,borrowDay);


		//借款下单
		ApplyLoanResultDTO ad = mBUserOrderService.addPledgeLoanOrder(userId, orderId, borrowAmount, borrowDay);
		
		PledgeApplyDTO pa = new PledgeApplyDTO();
		pa.setCode(ad.getCode());
		pa.setMessage(ad.getMessage());
		if(ad.getCode().equals("203100")){
			pa.setMessage("成功放款："+borrowAmount+"元");
			pa.setReceivableBankCard(ad.getFeedbackInformation());
			//银行卡信息
			pa.setReceivableBankCardLabel("收款银行卡");

			pa.setFeedbackInformation("预计3分钟内（借款金额>5万，T+1日到账）");

			pa.setIconURL(financialLoanMapper.getParamsValueByKeyAndGroup("pledge_loan_app_success","pledge_loan"));
		}
		
		
		return pa;
	}

	/**
	 * 获取贷款产品日利率
	 * @param loanProductId
	 * @return
     */
	private String getDayRate(String loanProductId) {
		//调用信贷系统：查询日利率
		BaseLogger.audit("流通宝调用信贷系统查询贷款产品利率入参loanProductId："+loanProductId);
		if(StringUtils.isBlank(loanProductId)){
			BaseLogger.error("loanProductId 为空");
			throw new AppException("查询贷款利率失败");
		}
		String rateJson = productInfoDubboService.selectProductRepayTypeById(loanProductId);
		BaseLogger.audit(String.format("流通宝调用信贷系统查询贷款产品利率loanProductId : [%s],rateJson : [%s] ",loanProductId, rateJson));
		if(StringUtils.isBlank(rateJson)){
			BaseLogger.error("查询贷款利率为空");
			throw new AppException("查询贷款利率失败");
		}
		JSONObject jso = JSONObject.parseObject(rateJson);
		return jso.getString("dayRate");
	}


	/**
	 *  BigDecimal比较
	 * @param bigDecimal1
	 * @param bigDecimal2
     * @return
     */
	private boolean compareBigDecimal(String bigDecimal1, String bigDecimal2) {
		if(StringUtils.isBlank(bigDecimal1) || StringUtils.isBlank(bigDecimal2)){
			BaseLogger.error("比较参数不能为空");
			throw new AppException("参数为空");
		}
		BigDecimal t1 = new BigDecimal(bigDecimal1);
		BigDecimal t2 = new BigDecimal(bigDecimal2);
		return t1.compareTo(t2) <= 0 ? true: false;
	}


	/**
	 * 验证借款金额
	 * @param userId
	 * @param orderId
	 * @param loanProductId
	 * @param borrowAmount
     */
	private void validatorAmount(String userId, String orderId,String loanProductId,
			String borrowAmount) {
		//查询卡号,可借最大额度,可借最大天数
		PledgeInvestBorrowInitiDTO pledgeInvestBorrowInitiDTO = getPledgeInvestBorrowInitiDTO(userId,loanProductId,orderId);

		//验证可借金额是否合法
		//比较最小值
		String  pledgeInvestMinAmount =  financialLoanMapper.getParamsValueByKeyAndGroup("pledge_invest_min_amount","pledge_invest");

		if(!compareBigDecimal(borrowAmount, pledgeInvestBorrowInitiDTO.getCanBorrowMaxAmount()) || !compareBigDecimal(pledgeInvestMinAmount, borrowAmount)){
			throw new AppException("借款金额不合法");
		};

	}

	/**
	 * 验证借款天数
	 * @param userId
	 * @param orderId
	 * @param loanProductId
	 * @param borrowAmount
	 * @param borrowDay
	 */
	private void validatorBorrowDay(String userId, String orderId,String loanProductId,
								 String borrowAmount, String borrowDay) {

		//查询卡号,可借最大额度,可借最大天数
		PledgeInvestBorrowInitiDTO pledgeInvestBorrowInitiDTO = getPledgeInvestBorrowInitiDTO(userId,loanProductId,orderId);

		//重新计算和输入的天数比较验证
		Integer canBorrowMaxDay = getCanBorrowMaxDay(userId, orderId, loanProductId, borrowAmount);
		//验证可借天数是否合法(<=canBorrowMaxDay)
		if(!compareBigDecimal(borrowDay,String.valueOf(canBorrowMaxDay))){
			throw new AppException("借款天数不合法");
		};

	}

//	{"code":200,"data":{"noRepaymentCapital":"1,166.00","noSettleLoan":2},"message":"success"}
	
	@Override
	public Object repaymentIniti(String userId) {
		if(StringUtils.isBlank(userId)){
			throw new AppException("userId不能为空");
		}
		
		//调用信贷系统，查询未还本金和借款笔数
		BaseLogger.info("流通宝调用信贷系统查询贷款还款初始化入参userId："+userId);
		String jsonStr = applyLoanLtbDubboService.queryNoRepayInfo(userId);
		BaseLogger.info("流通宝调用信贷系统查询贷款还款初始化结果："+jsonStr);
		
		JSONObject obj = JSONObject.parseObject(jsonStr);
		if(obj.getString("code").equals("200")){
			RepaymentInitiDTO rid = new RepaymentInitiDTO();
			
			JSONObject data = obj.getJSONObject("data");
			String count = data.getString("noSettleLoan");
			rid.setNoRepaymentCapitalTitle("未还本金");
			
			rid.setNoRepaymentCapital("¥"+data.getString("noRepaymentCapital"));
			rid.setNoSettleLoanDescription("共"+count+"笔借款未结清");
			rid.setDueRepaymentLabel("到期还款");
			rid.setDueRepayment("请查看");
			rid.setDueRepaymentType("到期将从余额自动还款");
			rid.setEarlyRepaymentLabel("提前还款");
			rid.setEarlyRepayment("共"+count+"笔");
			rid.setEarlyRepaymentType("可提前结清所有的借款");
			return rid;
		}else{
			throw new AppException("系统异常");
		}
		
	}

//	
//	{
//	    "code": 200,
//	    "message": "success",
//	    "records": [
//	        {
//	            "loanCode":"ddddd",
//	            "endTime": 1468669974000,
//	            "loanAmountTotal": "583.00",
//	            "overplusInterest": "0.61",
//	            "overplusPrincipal": "583.00",
//	            "startTime": 1468410597000
//	        },
//	        {
//	            "loanCode":"ddddd",
//	            "endTime": 1468669990000,
//	            "loanAmountTotal": "583.00",
//	            "overplusInterest": "0.61",
//	            "overplusPrincipal": "583.00",
//	            "startTime": 1468410613000
//	        }
//	    ]
//	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public Object dueRepaymentIniti(String userId, String page) {
		
		if(StringUtils.isBlank(userId)|| StringUtils.isBlank(page)){
			throw new AppException("参数不能为空");
		}
		
		DueRepaymentInitiDTO did = new DueRepaymentInitiDTO();
		String amount = pledgeInvestMapper.queryUserMaAmountByUserId(userId);
		did.setAccountBalance(amount);
		did.setRepaymentTip("请到余额账户里充值后进行还款操作");
		did.setAccountBalanceLabel("账户余额:");
		
		//调用信贷系统，查询用户借款记录
		BaseLogger.info("流通宝调用信贷系统查询到期还款初始化入参userId："+userId);
		String jsonStr = applyLoanLtbDubboService.queryNoRepayLoanOrders(userId,Integer.parseInt(page));
		BaseLogger.info("流通宝调用信贷系统查询到期还款初始化结果："+jsonStr);
		JSONObject obj = JSONObject.parseObject(jsonStr);
		
		if(obj.getString("code").equals("200")){
			List<Map<String,String>> list =JSONArray.parseObject(obj.getString("records"),List.class);
			List<DueRepaymentInitiInnerRecordDTO> resultList = new ArrayList<DueRepaymentInitiInnerRecordDTO>();
			for(Map<String,String> map:list){
				DueRepaymentInitiInnerRecordDTO di = new DueRepaymentInitiInnerRecordDTO();
				di.setLoanCode(map.get("loanCode"));
				di.setLoanDateAndMoney(handlerDateCommon(map.get("startTime"),"yyyy年MM月dd日")+" 借款"+map.get("loanAmountTotal")+"元");
				di.setToRepaymentLabel("到期付款");
				String principal = map.get("overplusPrincipal");
				String interest = map.get("overplusInterest");
				di.setSurplusCapital("<font color=#4A4A4A>剩余本金:</font><font color=#4AC0F0>"+principal+"</font><font color=#4A4A4A>元</font>");
				di.setSurplusInterest("<font color=#4A4A4A>剩余利息:</font><font color=#4AC0F0>"+interest+"</font><font color=#4A4A4A>元</font>");
				di.setToRepaymentMoneyDate(handlerDateCommon(map.get("endTime"),"yyyy年MM月dd日"));
				di.setPrincipal(principal);
				di.setInterest(interest);
				resultList.add(di);
			}
			did.setRecords(resultList);
			if(list.size()<10){
				did.setIsMore("false");
			}else{
				did.setIsMore("true");
			}
			return did;
		}else{
			throw new AppException("系统异常");
		}
		
	}

	
	
//	{
//	    "code": 200,
//	    "data": {
//	        "endTime": 1468669990000,
//	        "loanAmountTotal": "583.00",
//	        "overplusInterest": "0.61",
//	        "overplusPrincipal": "583.00",
//	        "poundage": "0.00",
//	        "repayAmountTotal": "583.61",
//	        "repayTypeName": "到期还本付息",
//	        "startTime": 1468410613000
//	    },
//	    "message": "success"
//	}

	
	@Override
	public Object dueRepaymentDetail(String userId, String loanCode) {
		
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(loanCode)){
			throw new AppException("参数不能为空");
		}
		DueRepaymentDetailDTO dd =  new DueRepaymentDetailDTO();
		//调用信贷系统，查询借款详情
		String jsonStr = applyLoanLtbDubboService.queryNoRepayLoanDetail(userId, loanCode);
		JSONObject obj = JSONObject.parseObject(jsonStr);
		if(obj.getString("code").equals("200")){
			JSONObject data = obj.getJSONObject("data");
			
			String loanDate = handlerDateCommon(data.getString("startTime"),"yyyy年MM月dd日");
			String loanMoney = data.getString("loanAmountTotal");
			String repayDate = handlerDateCommon(data.getString("endTime"),"yyyy年MM月dd日");
			String principal = data.getString("overplusPrincipal");
			
			dd.setLoanDateDesc(loanDate+"借");
			dd.setLoanMoney("¥"+loanMoney);
			dd.setNoRepaymentCapitalTitle(repayDate+"应还款");
			dd.setNoRepaymentCapital("¥"+principal);
//			dd.setNoSettleLoanDescription("我们会在还款日当天中午12:00开始自动扣款|请确保余额账户资金充足");
			dd.setNoSettleLoanDescription("我们会在还款日当天自动扣款|请确保余额账户资金充足");
			
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			list.add(buildLabelAndValueMap("本金",principal+"元"));
			list.add(buildLabelAndValueMap("利息",data.getString("overplusInterest")+"元"));
			list.add(buildLabelAndValueMap("手续费",data.getString("poundage")+"元"));
			list.add(buildLabelAndValueMap("还款方式",data.getString("repayTypeName")));
			
			dd.setLoanInfo(list);
			
			return dd;
		}else{
			throw new AppException("系统异常");
		}
		
	}
	
	private Map<String, String> buildLabelAndValueMap(final String label, final String value) {
		return new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
				put("label", label);
				put("value", value);
			}
		};
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object earlyRepaymentIniti(String userId,String page) {
		
		if(StringUtils.isBlank(userId)|| StringUtils.isBlank(page)){
			throw new AppException("参数不能为空");
		}
		
		EarlyRepaymentInitiDTO did = new EarlyRepaymentInitiDTO();
		
		
		//调用信贷系统，查询用户借款记录
		BaseLogger.info("流通宝调用信贷系统查询提前还款初始化入参userId："+userId);
		String jsonStr = applyLoanLtbDubboService.queryNoRepayLoanOrders(userId,Integer.parseInt(page));
		BaseLogger.info("流通宝调用信贷系统查询提前还款初始化结果："+jsonStr);
		JSONObject obj = JSONObject.parseObject(jsonStr);
		
		if(obj.getString("code").equals("200")){
			List<Map<String,String>> list =JSONArray.parseObject(obj.getString("records"),List.class);
			List<DueRepaymentInitiInnerRecordDTO> resultList = new ArrayList<DueRepaymentInitiInnerRecordDTO>();
			String count = obj.getString("totalCount");
			for(Map<String,String> map:list){
				DueRepaymentInitiInnerRecordDTO di = new DueRepaymentInitiInnerRecordDTO();
				di.setLoanCode(map.get("loanCode"));
				di.setLoanDateAndMoney(handlerDateCommon(map.get("startTime"),"yyyy年MM月dd日")+" 借款"+map.get("loanAmountTotal")+"元");
				di.setToRepaymentLabel("toRepayment");
				String principal = map.get("overplusPrincipal");
				String interest = map.get("overplusInterest");
				di.setSurplusCapital("<font color=#4A4A4A>未还本金:</font><font color=#4AC0F0>"+principal+"</font><font color=#4A4A4A>元</font>");
				di.setSurplusInterest("<font color=#4A4A4A>未还利息:</font><font color=#4AC0F0>"+interest+"</font><font color=#4A4A4A>元</font>");
				di.setToRepaymentMoneyDate(handlerDateCommon(map.get("endTime"),"yyyy年MM月dd日"));
				di.setPrincipal(handlerMoney(principal));
				di.setInterest(handlerMoney(interest));
				resultList.add(di);
			}
			did.setRecords(resultList);
			did.setRepaymentTip("共"+count+"笔借款未还，提前还款无需手续费");
			if(list.size()<10){
				did.setIsMore("false");
			}else{
				did.setIsMore("true");
			}
			return did;
		}else{
			throw new AppException("系统异常");
		}
	}

	
//	{
//	    "code": 200,
//	    "data": {
//	        "interest": "1.22",
//	        "principal": "1,166.00",
//	        "poundage": "0.00",
//	        "repayAmountTotal": "1,167.22",
//	        "repayPrincipal": "1,166.00"
//	    },
//	    "message": "success"
//	}

	
	@Override
	public Object earlyRepaymentDetail(String userId, String loanCodes,
			String repaymentMoney) {
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(loanCodes)|| StringUtils.isBlank(repaymentMoney)){
			throw new AppException("参数不能为空");
		}
		String[] loanIds = loanCodes.split(",");
		
		EarlyRepaymentDetailDTO detailDto = new EarlyRepaymentDetailDTO();  
		detailDto.setRepaymentMoneyLabel("还款本金");
		detailDto.setNoRepaymentCapitalLabel("未还本金");
		detailDto.setCapitalLabel("本金");
		detailDto.setCounterFeeLabel("手续费");
		detailDto.setInterestLabel("利息");
		detailDto.setRepaymentTotalMoneyLabel("还款总额");
		detailDto.setAvailableBalanceMoneyLabel("账户余额");
		
		String amount = pledgeInvestMapper.queryUserMaAmountByUserId(userId);
		detailDto.setAvailableBalanceMoney(amount);
		detailDto.setAvailableBalanceMoneyDesc(amount);
		
		String resultJson = "";
		BaseLogger.info(String.format("流通宝调用信贷系统查询借款记录详情入参userId：%s;loanCode:%s;repaymentMoney:%s",userId,loanCodes,repaymentMoney));
		if(loanIds.length == 1){
			resultJson = applyLoanLtbDubboService.queryRepayAmountById(userId, loanIds[0],repaymentMoney);
		}
		if(loanIds.length > 1){
			resultJson = applyLoanLtbDubboService.queryRepayAmountByIds(userId, loanIds);
		}
		BaseLogger.info("流通宝调用信贷系统查询借款记录详情结果："+resultJson);
		
		JSONObject obj = JSONObject.parseObject(resultJson);
		if(obj.getString("code").equals("200")){
			JSONObject data = obj.getJSONObject("data");
			detailDto.setRepaymentMoney(handlerMoney(data.getString("principal")));
			detailDto.setRepaymentMoneyDesc(data.getString("principal"));
			detailDto.setNoRepaymentCapital(data.getString("noRepayPrincipal"));
			detailDto.setCapital(data.getString("principal"));
			detailDto.setInterest(data.getString("interest"));
			detailDto.setCounterFee(data.getString("poundage"));
			detailDto.setRepaymentTotalMoney(handlerMoney(data.getString("repayAmountTotal")));
			detailDto.setRepaymentTotalMoneyDesc(data.getString("repayAmountTotal"));
			return detailDto;
			
		}else{
			throw new AppException("订单还款异常");
		}

	}


	private boolean validatorIfShowButton(){
		Date da= new Date();
		String time = financialLoanMapper.getParamsValueByKeyAndGroup("pledge_invest_loan_task_time","pledge_invest");
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		String tis = format.format(da);
		return (time.compareTo(tis)>=0);
	}
	
	
//	{"code":200,"data":{"repayTypeName":"流通宝还款","repayTypeValue":"LTB_REPAY",
//		"repayWayName":"融桥宝余额","repayWayValue":"WJ_BALANCE"},"message":"success"}

	@Override
	public Object earlyRepaymentConfirmIniti(String userId, String loanCodes,
			String repaymentMoney) {
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(loanCodes) ||StringUtils.isBlank(repaymentMoney)){
			throw new AppException("参数不能为空");
		}
		EarlyRepaymentConfirmInitiDTO ed = new EarlyRepaymentConfirmInitiDTO();
		
		BaseLogger.info(String.format("流通宝earlyRepaymentConfirmIniti调用信贷系统查询入参userId：%s;loanCodes:%s;repaymentMoney:%s",userId,loanCodes.toString(),repaymentMoney));
		String resultJson = applyLoanLtbDubboService.queryRepayType();
		BaseLogger.info("流通宝earlyRepaymentConfirmIniti调用信贷系统查询结果："+resultJson);
		
		JSONObject obj = JSONObject.parseObject(resultJson);
		if(obj.getString("code").equals("200")){
			
			JSONObject data = obj.getJSONObject("data");
			
			ed.setRepaymentTitle("付款详情");
			ed.setRepaymentTypeLabel("付款类型");
			ed.setRepaymentTypeDesc(data.getString("repayTypeName"));
			ed.setRepaymentType(data.getString("repayTypeValue"));
			ed.setRepaymentWayLabel("付款方式");
			ed.setRepaymentWayDesc(data.getString("repayWayName"));
			ed.setRepaymentWay(data.getString("repayWayValue"));
			ed.setNeedRepaymentMoneyLabel("需付款");
			ed.setNeedRepaymentMoney(repaymentMoney);
			
			return ed;
		}else{
			throw new AppException("系统异常");
		}
		
	}

	@Override
	public Object earlyRepaymentConfirm(String userId, String loanCodes,String principal,
			String repaymentMoney,String repaymentType,String repaymentWay) {

		if(validatorIfShowButton()){
			throw new AppException("系统维护中，请选择其他时间节点还款");
		}
		
		LoanResultCommonDTO result = mBUserOrderService.earlyRepaymentConfirm(userId, loanCodes,principal, repaymentMoney, repaymentType, repaymentWay);
		
		EarlyRepayResultDTO erd = new EarlyRepayResultDTO();
		erd.setCode(result.getCode());
		erd.setMessage(result.getMessage());
		
        if(result.getCode().equals("203200")){
        	erd.setFeedbackInformation("还款成功,"+repaymentMoney+"元");
			erd.setIconURL(financialLoanMapper.getParamsValueByKeyAndGroup("early_repay_app_success","pledge_loan"));
		}
		if(result.getCode().equals("203202")){
			erd.setFeedbackInformation(erd.getMessage());
		}
        if(result.getCode().equals("203203")){
        	erd.setFeedbackInformation("还款处理中,"+repaymentMoney+"元,稍后可通过账单查询处理结果");
			erd.setIconURL(financialLoanMapper.getParamsValueByKeyAndGroup("early_repay_app_success","pledge_loan"));
		}
		
		return erd;
	}

	


	/**
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public Object queryLoanRecordList(String userId, String page) {
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(page)){
			throw new AppException("参数不能为空");
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<LoanRecordListDTO> resultList = new ArrayList<LoanRecordListDTO>();
		//调用信贷系统，获取借款记录列表
		BaseLogger.info(String.format("流通宝调用信贷系统查询借款记录入参userId：%s;page:%s",userId,page));
		String jsonStr = applyLoanLtbDubboService.queryLoanOrders(userId,Integer.parseInt(page));
		BaseLogger.info("流通宝调用信贷系统查询借款记录结果："+jsonStr);
		
		JSONObject obj = JSONObject.parseObject(jsonStr);
		
		if(obj.getString("code").equals("200")){
			List<Map<String,String>> list =JSONArray.parseObject(obj.getString("records"),List.class);
			for(Map<String,String> map:list){
				LoanRecordListDTO ld = new LoanRecordListDTO();
				ld.setLoanCode(map.get("loanCode"));
				ld.setLoanAmount("¥"+map.get("loanAmount"));
				Object date = map.get("loanTimeStr");
				ld.setLoanTimeStr(handlerDateCommon(date,"yyyy年MM月dd日"));
				ld.setLoanStatus(map.get("loanStatus"));
				ld.setLoanStatusStr(map.get("loanStatusStr"));
				resultList.add(ld);
			}
			if(resultList.size()<10){
				resultMap.put("isMore", "false");
			}else{
				resultMap.put("isMore", "true");
			}
			resultMap.put("records", resultList);
			
			return resultMap;
		}else{
			throw new AppException("系统异常");
		}
	}

	
	
	@Override
	public Object queryLoanRecordDetail(String userId, String loanCode) {
		if(StringUtils.isBlank(userId)|| StringUtils.isBlank(loanCode)){
			throw new AppException("参数不能为空");
		}
		
		BaseLogger.info(String.format("流通宝调用信贷系统查询借款记录详情入参userId：%s;loanCode:%s",userId,loanCode));
		String jsonStr = applyLoanLtbDubboService.queryLoanOrderDetail(userId, loanCode);
		BaseLogger.info("流通宝调用信贷系统查询借款记录详情结果："+jsonStr);
		
		JSONObject obj = JSONObject.parseObject(jsonStr);
		if(obj.getString("code").equals("200")){
			JSONObject data = obj.getJSONObject("data");
			LoanRecordDetailDTO lrd = new LoanRecordDetailDTO();
			lrd.setLoanCode(data.getString("loanCode"));
			String loanStatus = data.getString("loanStatus");//账单状态（R：使用中，F：提前或到期结清，ST：强制结清）
			lrd.setLoanStatus(loanStatus);
			lrd.setLoanStatusTitle(loanStatus.equals("R")?"使用中金额":"已结清本金");
			lrd.setTopLoanAmountTitle("¥ "+(loanStatus.equals("R")?data.getString("noRepayPricipal"):data.getString("loanAmount")));
			lrd.setIsShowAdvancePaymentDesc("false");
			ProductDetailDTO productDetailDTO=financialLoanMapper.queryProductDetailByLoanCode(userId,loanCode);
			if(productDetailDTO!=null&&"yes".equals(productDetailDTO.getIsPreExpire())){
				lrd.setIsShowAdvancePaymentDesc("true");
			}
			lrd.setAdvancePaymentDesc("抵押定期理财产品提前到期");

			lrd.setAdvanceRepayTitle("提前还款");
			lrd.setRepayFinishTitle("好借好还，再借不难！");
			lrd.setNoRepayPricipal(handlerMoney(data.getString("noRepayPricipal")));
			
			//借款信息
			LoanRecordDetailInnerloanInfoDTO lii = new LoanRecordDetailInnerloanInfoDTO();
			lii.setContract("点击查看");
			lii.setContractLabel("借款合同");
			lii.setContractTitle("借款合同");
			lii.setContractURL(financialLoanMapper.getParamsValueByKeyAndGroup("pledge_invest_contract","pledge_invest"));
			lii.setLoanTitle("借款明细");
			List<Map<String,String>> loanList = new ArrayList<Map<String,String>>();
			loanList.add(buildLabelAndValueMap("借款金额", data.getString("loanAmount")+"元"));
			loanList.add(buildLabelAndValueMap("合同期限", handlerDateCommon(data.getString("startDate"),"yyyy/MM/dd")+"-"+handlerDateCommon(data.getString("endDate"),"yyyy/MM/dd")));
			loanList.add(buildLabelAndValueMap("还款方式", data.getString("repayTypeName")));
			lii.setLoanDetail(loanList);
			lrd.setLoanInfos(lii);
			

            //还款总额
			String repayPrincipal = data.getString("sumRepayPrincipal");
			BigDecimal repayPrincipalB;
			if(StringUtils.isBlank(repayPrincipal)){
				repayPrincipalB = new BigDecimal("0");
			}else{
				repayPrincipalB = new BigDecimal(handlerMoney(repayPrincipal));
			}

			//如果有过还款记录，就显示还款明细
			if(repayPrincipalB.compareTo(new BigDecimal(0)) > 0){
				//已结清的订单，才有还款信息
				LoanRecordDetailInnerrepaymentInfoDTO lid = new LoanRecordDetailInnerrepaymentInfoDTO();
				lid.setRepayRecordLabel("还款记录");
				lid.setRepayTitle("还款明细");
				lid.setRepayRecord("点击查看");
				List<Map<String,String>> repayList = new ArrayList<Map<String,String>>();
				repayList.add(buildLabelAndValueMap("已还本金", repayPrincipal+"元"));
				repayList.add(buildLabelAndValueMap("已还利息", data.getString("sumRepayInterest")+"元"));
				repayList.add(buildLabelAndValueMap("手续费", data.getString("poundage")+"元"));
				lid.setRepaymentDetail(repayList);
				lrd.setRepaymentInfos(lid);
			}else{
				lrd.setRepaymentInfos(new LoanRecordDetailInnerrepaymentInfoDTO());
			}
			
			return lrd;
			
		}else{
			throw new AppException("系统异常");
		}
	}

	
	
//	{
//	    "code": 200,
//	    
//	        "records": [
//	            {
//	                "counterFee": "0.00",
//	                "repayInterest": "5.24",
//	                "repayPrincipal": "500.00",
//	                "repayTime": "2016-07-14 21:05",
//	                "repayTotal": "505.24"
//	            }
//	        ],
//	   
//	    "message": "success"
//	}

	
	@SuppressWarnings("unchecked")
	@Override
	public Object queryrepayRecordList(String userId, String loanCode,
			String page) {
		if(StringUtils.isBlank(userId)|| StringUtils.isBlank(loanCode) || StringUtils.isBlank(page)){
			throw new AppException("参数不能为空");
		}
		
		BaseLogger.info(String.format("流通宝调用信贷系统查询借款的还款记录入参userId：%s;loanCode:%s;page:%s",userId,loanCode,page));
		String jsonStr = applyLoanLtbDubboService.queryRepayOrders(userId, loanCode,Integer.parseInt(page));
		BaseLogger.info("流通宝调用信贷系统查询借款的还款记录结果："+jsonStr);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		JSONObject obj = JSONObject.parseObject(jsonStr);
		
		if(obj.getString("code").equals("200")){
			List<Map<String,String>> list =JSONArray.parseObject(obj.getString("records"),List.class);
			List<LoanRecordDetailReapyListDTO> resultList = new ArrayList<LoanRecordDetailReapyListDTO>();
			for(Map<String,String> map:list){
				LoanRecordDetailReapyListDTO ld = new LoanRecordDetailReapyListDTO();
				ld.setCounterFeeTitle("手续费");
				ld.setRepayInterestTitle("利息");
				ld.setRepayPrincipalTitle("本金");
				ld.setCounterFee(map.get("counterFee")+"元");
				ld.setRepayInterest(map.get("repayInterest")+"元");
				ld.setRepayPrincipal(map.get("repayPrincipal")+"元");
				ld.setRepayDate(handlerDateCommon(map.get("repayTime"), "yyyy年MM月dd日"));
				ld.setRepayTotal("¥"+map.get("repayTotal"));
				ld.setRepayTime(handlerDateToTimeCommon(map.get("repayTime")));
				resultList.add(ld);
			}
			if(resultList.size()<10){
				resultMap.put("isMore", "false");
			}else{
				resultMap.put("isMore", "true");
			}
			resultMap.put("records", resultList);
			return resultMap;
		}else{
			throw new AppException("系统异常");
		}
	}

	
	/**
	 * @param dateStr 时间戳
	 * @param style yyyy-MM-dd，yyyy年MM月dd日
	 * @return
	 */
	private String handlerDateCommon(Object dateStr,String style){
		String str = String.valueOf(dateStr);
		if(StringUtils.isBlank(str)){
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(style);
		Long datess = Long.parseLong(str);
		Date date = new Date(datess);
		return format.format(date);
	}
	
	/**
	 * @param dateStr 时间戳
	 * @return
	 */
	private String handlerDateToTimeCommon(Object dateStr){
		String str = String.valueOf(dateStr);
		if(StringUtils.isBlank(str)){
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Long datess = Long.parseLong(str);
		Date date = new Date(datess);
		return format.format(date);
	}
	
	private String handlerMoney(String money){
		return money.replace(",", "");
	}


	/**
	 * 处理利率显示问题
	 * @param goal
	 * @return
     */
	private String handleNumberStr(String goal){
		String rex1 = ".00%";
		String rex2 = ".0%";
		String rex3 = "0%";
		if(goal.endsWith(rex1)){
			return goal.replace(rex1,"%");
		}if(goal.endsWith(rex2)){
			return goal.replace(rex2,"%");
		}if(goal.endsWith(rex3)){
			return goal.replace(rex3,"%");
		}
        return goal;
	}
	
	
}
