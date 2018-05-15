package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.PreExpireRfPaymentResp;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RfRepayPlanDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RfResponseDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IProductQueryMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBProductProcess;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IUserAccountTransactionService;

@Service("MBWjProductProcess")
public class MBWjProductProcess implements IMBProductProcess {
	@Autowired
	private IProductQueryMapper productQueryMapper;
	@Autowired
	private IUserAccountTransactionService userAccountTransactionService;
	
	@Override
	public RfResponseDTO processOrder(BigDecimal amount, BigDecimal addProfit,MBUserInfoDTO userinfo, String orderNo, String productId,String tradeId) {

		BaseLogger.audit("MBWjProductProcess  processOrder in.");
      	String startTimeStr=DateUtils.formatDate(new Date(),"yyyy-MM-dd");
		//========================满标相关代码此版本暂时注释=======================
		//String startTimeStr=productQueryMapper.queryProductInterestDateStr(productId);
		List<RfRepayPlanDTO> repayplans = calcRepayPlanByMonthInteresting(amount, userinfo.getProductFirstMonthDay(), addProfit.add(userinfo.getProductReceiveRate()),startTimeStr,userinfo.getProductEndTime(), userinfo.getProductRepaymentType());
		//根据还款期数对还款计划排序
		Collections.sort(repayplans, new Comparator<RfRepayPlanDTO>() {
			@Override
			public int compare(RfRepayPlanDTO o1, RfRepayPlanDTO o2) {
				if (o1.getPeriod() > o2.getPeriod())
					return 1;
				else
					return 0;
			}
	    });
		
		//插入还款计划
		String repayPlanNo = userAccountTransactionService.getId("RP", ISequenceService.SEQ_NAME_REPAYPLAN_SEQ);

		for(RfRepayPlanDTO item:  repayplans){
			BaseLogger.audit("MBWjProductProcess  processOrder PlanRepayInterest:"+item.getPlanRepayInterest());
			item.setPlanRepayPrincipal(item.getPlanRepayPrincipal());
			item.setPlanRepayInterest(item.getPlanRepayInterest());
			item.setRepayPrincipal(item.getPlanRepayPrincipal());
			item.setRepayInterest(item.getPlanRepayInterest());
			item.setRepaymentPlanId(repayPlanNo);
			item.setOrderNo(orderNo);
			item.setUserId(userinfo.getUserId());
			item.setIsRepay("no");
			item.setRepaymentType(userinfo.getProductRepaymentType());
			item.setProductFlag(userinfo.getProductFlag());
			item.setOtherFee(BigDecimal.ZERO);
			
			if (userinfo.getProductFlag() != null && userinfo.getProductFlag().equals("greenhorn")){
				item.setGreenhornInterest(userinfo.getProductGreenhornReceiveRate().multiply(item.getPlanRepayInterest()).divide(userinfo.getProductReceiveRate(), 2, RoundingMode.DOWN));
			}else{
				item.setGreenhornInterest(BigDecimal.ZERO);
			}
		}
		
		RfResponseDTO dto = new RfResponseDTO();
		dto.setOrderNo(orderNo);
		dto.setRepayplans(repayplans);
		//保单凭据
		dto.setPolicyNo(userinfo.getProductPolicyNo());
		dto.setLoanId(userinfo.getProductLoanId());
		dto.setTradeId(tradeId);
		dto.setAmount(amount);
		dto.setName(userinfo.getName());
		dto.setIdentifyNo(userinfo.getIndentityNo());
		BaseLogger.audit("MBWjProductProcess  processOrder out.");
		return dto;
	
	}
	
	@Override
	public RfResponseDTO processOrder(BigDecimal amount,
			MBUserInfoDTO userinfo, String orderNo, String productId,
			String tradeId) {
		BaseLogger.audit("MBWjProductProcess  processOrder in.");
		String startTimeStr=DateUtils.formatDate(new Date(),"yyyy-MM-dd");
		//========================满标相关代码此版本暂时注释=======================
		//String startTimeStr=productQueryMapper.queryProductInterestDateStr(productId);
		List<RfRepayPlanDTO> repayplans = calcRepayPlanByMonthInteresting(amount, userinfo.getProductFirstMonthDay(), userinfo.getProductReceiveRate(),startTimeStr,userinfo.getProductEndTime(), userinfo.getProductRepaymentType());
		//根据还款期数对还款计划排序
		Collections.sort(repayplans, new Comparator<RfRepayPlanDTO>() {
			@Override
			public int compare(RfRepayPlanDTO o1, RfRepayPlanDTO o2) {
				if (o1.getPeriod() > o2.getPeriod())
					return 1;
				else
					return 0;
			}
	    });
		
		//插入还款计划
		String repayPlanNo = userAccountTransactionService.getId("RP", ISequenceService.SEQ_NAME_REPAYPLAN_SEQ);

		for(RfRepayPlanDTO item:  repayplans){
			BaseLogger.audit("MBWjProductProcess  processOrder PlanRepayInterest:"+item.getPlanRepayInterest());
			item.setPlanRepayPrincipal(item.getPlanRepayPrincipal());
			item.setPlanRepayInterest(item.getPlanRepayInterest());
			item.setRepayPrincipal(item.getPlanRepayPrincipal());
			item.setRepayInterest(item.getPlanRepayInterest());
			item.setRepaymentPlanId(repayPlanNo);
			item.setOrderNo(orderNo);
			item.setUserId(userinfo.getUserId());
			item.setIsRepay("no");
			item.setRepaymentType(userinfo.getProductRepaymentType());
			item.setProductFlag(userinfo.getProductFlag());
			item.setOtherFee(BigDecimal.ZERO);
			
			if (userinfo.getProductFlag() != null && userinfo.getProductFlag().equals("greenhorn")){
				item.setGreenhornInterest(userinfo.getProductGreenhornReceiveRate().multiply(item.getPlanRepayInterest()).divide(userinfo.getProductReceiveRate(), 2, RoundingMode.DOWN));
			}else{
				item.setGreenhornInterest(BigDecimal.ZERO);
			}
		}
		
		RfResponseDTO dto = new RfResponseDTO();
		dto.setOrderNo(orderNo);
		dto.setRepayplans(repayplans);
		//保单凭据
		dto.setPolicyNo(userinfo.getProductPolicyNo());
		dto.setLoanId(userinfo.getProductLoanId());
		dto.setTradeId(tradeId);
		dto.setAmount(amount);
		dto.setName(userinfo.getName());
		dto.setIdentifyNo(userinfo.getIndentityNo());
		BaseLogger.audit("MBWjProductProcess  processOrder out.");
		return dto;
	}

	@Override
	public RfRepayPlanDTO generateAdvExpireOrder(BigDecimal amount, MBUserInfoDTO userinfo, String orderNo, String startTimeStr, String endTimeStr) {
		String startTime=startTimeStr;
		String endTime=endTimeStr;
		if (startTime.compareTo(endTime)>0) {
			startTime=endTimeStr;
			endTime=startTimeStr;
		}
		BaseLogger.audit("generateAdvExpireOrder in orderNo:"+orderNo);
		List<RfRepayPlanDTO> repayplans = calcRepayPlanByMonthInteresting(amount, userinfo.getProductFirstMonthDay(), userinfo.getProductReceiveRate(),startTime,endTime, userinfo.getProductRepaymentType());
		//根据还款期数对还款计划排序
		Collections.sort(repayplans, new Comparator<RfRepayPlanDTO>() {
			@Override
			public int compare(RfRepayPlanDTO o1, RfRepayPlanDTO o2) {
				if (o1.getPeriod() > o2.getPeriod())
					return 1;
				else
					return 0;
			}
		});
		//插入还款计划
		//String repayPlanNo =userAccountTransactionService.getId("RP", ISequenceService.SEQ_NAME_REPAYPLAN_SEQ);
		//汇总 计算总利息生成一条还款计划 (提前到期一次性结清)
		BigDecimal planRepayPrincipal=BigDecimal.ZERO;
		BigDecimal planRepayInterest=BigDecimal.ZERO;
		BigDecimal repayPrincipal=BigDecimal.ZERO;
		BigDecimal repayInterest=BigDecimal.ZERO;
		BigDecimal greenhornInterest=BigDecimal.ZERO;
		for(RfRepayPlanDTO item: repayplans){
			BaseLogger.audit("generateAdvExpireOrder in item:"+item.toString());
			planRepayPrincipal=planRepayPrincipal.add(item.getPlanRepayPrincipal());
			planRepayInterest=planRepayInterest.add(item.getPlanRepayInterest());
			repayPrincipal=repayPrincipal.add(item.getPlanRepayPrincipal());
			repayInterest=repayInterest.add(item.getPlanRepayInterest());

			if (userinfo.getProductFlag() != null && userinfo.getProductFlag().equals("greenhorn")){
				greenhornInterest=greenhornInterest.add(userinfo.getProductGreenhornReceiveRate().multiply(item.getPlanRepayInterest()).divide(userinfo.getProductReceiveRate(), 2, RoundingMode.DOWN));
			}
		}
		RfRepayPlanDTO rfRepayPlanDTO=new RfRepayPlanDTO();
		rfRepayPlanDTO.setIsPreExpire("yes");
		rfRepayPlanDTO.setPreExpireStatus(PreExpireRfPaymentResp.EXPIRE_STATUS_SUCCESS);
		rfRepayPlanDTO.setPlanRepayDateTime(new Date());
		rfRepayPlanDTO.setPlanRepayPrincipal(planRepayPrincipal);
		rfRepayPlanDTO.setPlanRepayInterest(planRepayInterest);
		rfRepayPlanDTO.setRepayPrincipal(repayPrincipal);
		rfRepayPlanDTO.setRepayInterest(repayInterest);
		//rfRepayPlanDTO.setRepaymentPlanId(repayPlanNo);
		rfRepayPlanDTO.setOrderNo(orderNo);
		rfRepayPlanDTO.setUserId(userinfo.getUserId());
		rfRepayPlanDTO.setIsRepay("no");
		rfRepayPlanDTO.setRepaymentType(userinfo.getProductRepaymentType());
		rfRepayPlanDTO.setProductFlag(userinfo.getProductFlag());
		rfRepayPlanDTO.setOtherFee(BigDecimal.ZERO);
		rfRepayPlanDTO.setGreenhornInterest(greenhornInterest);
		BaseLogger.audit("generateAdvExpireOrder out rfRepayPlanDTO:"+rfRepayPlanDTO.toString());
		return rfRepayPlanDTO;
	}

	@Override
	public boolean queryOrder(String orderNo, String productId, String tradeId) {
		// TODO Auto-generated method stub
		return false;
	}

	//跑还款计划，按月计息
	public List<RfRepayPlanDTO> calcRepayPlanByMonthInteresting(BigDecimal amount, int firstmonthday, BigDecimal annualRating,String interestStartTime, String productEndTime, String productRepaymentType){
		BaseLogger.audit("calcRepayPlanByMonthInteresting in interestStartTime:"+interestStartTime+" productEndTime:"
				+productEndTime+" amount:"+amount+" annualRating:"+annualRating+" firstmonthday:"+firstmonthday+" productRepaymentType:"+productRepaymentType);
		Date interestStartDate=null;
		try {
			interestStartDate= DateUtils.parseDate(interestStartTime, new String[]{"yyyy-MM-dd"});
		} catch (DateParseException e) {
			throw new AppException("日期报错");
		}
		//小数位数
		int decimallength = 2;
		
		//计算首月日利率的天数
		if (firstmonthday < 28 || firstmonthday > 31){
			firstmonthday = 30;
		}
		
		//计算还款月利率，年利率÷12=月利率
		//计算日利率，月利率÷firstmonthday=日利率
		BigDecimal monthRating = annualRating.divide(new BigDecimal(12), 10, RoundingMode.DOWN);
		BigDecimal dayRating = annualRating.divide(new BigDecimal(12*firstmonthday), 10, RoundingMode.DOWN);
		
		BigDecimal monthInteresting = amount.multiply(monthRating);
		BigDecimal dayInteresting = amount.multiply(dayRating);
		BaseLogger.audit("calcRepayPlanByMonthInteresting in interestStartTime:"+interestStartTime+" productEndTime:"
				+productEndTime+" amount:"+amount+" annualRating:"+annualRating+" firstmonthday:"+firstmonthday
				+" productRepaymentType:"+productRepaymentType+" dayInteresting:"+dayInteresting);
		//计算贷款的天数应采用“算头不算尾”的方法，即天数有贷款发放的当日算至贷款归还的前一日为止，贷款归还的当日不计利息
		//确定结息日，对于等额本金、等额本息、等本等息和累进还款方式的个人贷款，委托扣款日为期末月末日
		Date endDate;
		try {
			endDate = DateUtils.parseDate(productEndTime, new String[]{"yyyy-MM-dd"});
			
			Calendar ca = Calendar.getInstance();
			ca.setTime(endDate);
			ca.add(Calendar.DAY_OF_YEAR, 1);
			
			endDate=ca.getTime();
		} catch (DateParseException e) {
			throw new AppException("日期报错");
		}
		
		List<RfRepayPlanDTO> temprepayplans = new ArrayList<RfRepayPlanDTO>();
		Calendar ca = Calendar.getInstance();
		ca.setTime(endDate);
		
		//插入末期还款计划
		RfRepayPlanDTO tempenddto = new RfRepayPlanDTO();
		tempenddto.setPlanRepayDate(ca.getTimeInMillis());
		tempenddto.setPlanRepayDateTime(ca.getTime());
		temprepayplans.add(tempenddto);
		ca.add(Calendar.MONTH, -1);
		ca.getTime();
		
		//插入还款计划
		Calendar caStart = Calendar.getInstance();
		caStart.setTime(interestStartDate);
		while(ca.after(caStart)){
			RfRepayPlanDTO dto = new RfRepayPlanDTO();
			dto.setPlanRepayDate(ca.getTimeInMillis());
			dto.setPlanRepayDateTime(ca.getTime());
			temprepayplans.add(dto);
			ca.add(Calendar.MONTH, -1);
			ca.getTime();
		}
		
		//计算头一个月的还款天数，                零头天数，只算利息，不包含本金
		int term0daycount = daysBetween(interestStartDate, temprepayplans.get(temprepayplans.size()-1).getPlanRepayDateTime());
		//计算还款期数，注意的是，还款中，头一个月零头天数，不算如还款期数
		int termcount = temprepayplans.size()-1;

		Calendar todayCal = Calendar.getInstance();
		todayCal.setTime(interestStartDate);

		if (ca.compareTo(todayCal) == 0){
			term0daycount = 0;
			termcount = temprepayplans.size();
		}
		
		System.out.println(termcount+"个月零"+term0daycount+"天");
		
//		贷款天数=月*30天+零头天数（如1月1日至5月24日即为4*30+24=144天） 　　日利率=年利率/360 　　月利率=年利率/12 
//		在放款当期及贷款结清当期采用按日计息
		
		List<RfRepayPlanDTO> repayplans = new ArrayList<RfRepayPlanDTO>();
		if (productRepaymentType.equals("monthly_interest")){//按月付息
			if (temprepayplans.size() == 1){
				//只有一个月
				RfRepayPlanDTO firstdto = temprepayplans.get(temprepayplans.size()-1);
				repayplans.add(firstdto);
				firstdto.setPlanRepayPrincipal(amount);
				firstdto.setPeriod(1);
				firstdto.setTotalperiod(1);
				
				if (term0daycount > 0)
					firstdto.setPlanRepayInterest(dayInteresting.multiply(new BigDecimal(term0daycount)));
				else
					firstdto.setPlanRepayInterest(monthInteresting);
			}else{
				int index = 0;

				//第一个月
				RfRepayPlanDTO firstdto = temprepayplans.get(temprepayplans.size()-1);
				repayplans.add(firstdto);
				firstdto.setPlanRepayPrincipal(BigDecimal.ZERO);
				firstdto.setPeriod(++index);
				firstdto.setTotalperiod(temprepayplans.size());
				
				if (term0daycount > 0)
					firstdto.setPlanRepayInterest(dayInteresting.multiply(new BigDecimal(term0daycount)));
				else
					firstdto.setPlanRepayInterest(monthInteresting);
				
				//中间月份
				for(int i = temprepayplans.size()-2; i>0; i--){
					RfRepayPlanDTO dto = temprepayplans.get(i);
					repayplans.add(dto);
					dto.setPlanRepayPrincipal(BigDecimal.ZERO);
					dto.setPlanRepayInterest(monthInteresting);		
					dto.setPeriod(++index);
					dto.setTotalperiod(temprepayplans.size());
				}

				//最后一个月
				RfRepayPlanDTO enddto = temprepayplans.get(0);
				repayplans.add(enddto);
				enddto.setPlanRepayPrincipal(amount);
				enddto.setPlanRepayInterest(monthInteresting);
				enddto.setPeriod(++index);
				enddto.setTotalperiod(temprepayplans.size());

			}

		}else if (productRepaymentType.equals("repay_maturity")){//到期还本付息
			if (temprepayplans.size() == 1){
				//只有一个月
				RfRepayPlanDTO firstdto = temprepayplans.get(temprepayplans.size()-1);
				repayplans.add(firstdto);
				firstdto.setPlanRepayPrincipal(amount);
				firstdto.setPeriod(1);
				firstdto.setTotalperiod(1);
				
				if (term0daycount > 0)
					firstdto.setPlanRepayInterest(dayInteresting.multiply(new BigDecimal(term0daycount)));
				else
					firstdto.setPlanRepayInterest(monthInteresting);				
			}else{
				BigDecimal bd = new BigDecimal(0);
				//第一个月
				
				if (term0daycount > 0)
					bd = bd.add(dayInteresting.multiply(new BigDecimal(term0daycount)));
				else
					bd = bd.add(monthInteresting);			
				
				//中间月份
				for(int i = temprepayplans.size()-2; i>0; i--){
					bd = bd.add(monthInteresting);
				}

				//最后一个月
				RfRepayPlanDTO enddto = temprepayplans.get(0);
				repayplans.add(enddto);
				enddto.setPlanRepayPrincipal(amount);
				enddto.setPlanRepayInterest(bd.add(monthInteresting));
				enddto.setPeriod(1);
				enddto.setTotalperiod(1);
			}
		}else if (productRepaymentType.equals("principal_and_interest_equal")){//等额本息
			if (temprepayplans.size() == 1){
				//只有一个月
				RfRepayPlanDTO firstdto = temprepayplans.get(temprepayplans.size()-1);
				repayplans.add(firstdto);
				firstdto.setPlanRepayPrincipal(amount);
				firstdto.setPeriod(1);
				firstdto.setTotalperiod(1);
				
				if (term0daycount > 0)
					firstdto.setPlanRepayInterest(dayInteresting.multiply(new BigDecimal(term0daycount)));
				else
					firstdto.setPlanRepayInterest(monthInteresting);
			}else{
				//第一个月
				RfRepayPlanDTO firstdto = temprepayplans.get(temprepayplans.size()-1);
				repayplans.add(firstdto);
				firstdto.setPlanRepayPrincipal(BigDecimal.ZERO);
				firstdto.setPeriod(1);
				firstdto.setTotalperiod(temprepayplans.size());
				
				if (term0daycount > 0)
					firstdto.setPlanRepayInterest(dayInteresting.multiply(new BigDecimal(term0daycount)));
				else
					firstdto.setPlanRepayInterest(monthInteresting);
				
//				每月月供额=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕 
				BigDecimal monthPay = amount.multiply(monthRating).multiply(BigDecimal.ONE.add(monthRating).pow(termcount)).divide(BigDecimal.ONE.add(monthRating).pow(termcount).subtract(BigDecimal.ONE), 10, RoundingMode.DOWN);
				
				//中间月份
				int index = 0;
				for(int i = temprepayplans.size()-2; i>=0; i--){
					RfRepayPlanDTO dto = temprepayplans.get(i);
					repayplans.add(dto);
					
					index++;
//					每月应还利息=贷款本金×月利率×〔(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)〕÷〔(1+月利率)^还款月数-1〕 
//					每月应还本金=贷款本金×月利率×(1+月利率)^(还款月序号-1)÷〔(1+月利率)^还款月数-1〕 
					BigDecimal monthInterestingPay = amount.multiply(monthRating).multiply(BigDecimal.ONE.add(monthRating).subtract(BigDecimal.ONE.add(monthRating).pow(index-1))).divide(BigDecimal.ONE.add(monthRating).pow(termcount).subtract(BigDecimal.ONE), 10, RoundingMode.DOWN);
					BigDecimal monthPrincalPay = monthPay.subtract(monthInterestingPay);
					
					dto.setPlanRepayPrincipal(monthPrincalPay);
					dto.setPlanRepayInterest(monthInterestingPay);		
					dto.setPeriod(index+1);
					dto.setTotalperiod(temprepayplans.size());
				}
			}			
			
		}else if (productRepaymentType.equals("principal_equal")){//等额本金
			if (temprepayplans.size() == 1){
				//只有一个月
				RfRepayPlanDTO firstdto = temprepayplans.get(temprepayplans.size()-1);
				repayplans.add(firstdto);
				firstdto.setPlanRepayPrincipal(amount);
				firstdto.setPeriod(1);
				firstdto.setTotalperiod(1);
				
				if (term0daycount > 0)
					firstdto.setPlanRepayInterest(dayInteresting.multiply(new BigDecimal(term0daycount)));
				else
					firstdto.setPlanRepayInterest(monthInteresting);
			}else{
				//第一个月
				RfRepayPlanDTO firstdto = temprepayplans.get(temprepayplans.size()-1);
				repayplans.add(firstdto);
				firstdto.setPlanRepayPrincipal(BigDecimal.ZERO);
				firstdto.setPeriod(1);
				firstdto.setTotalperiod(temprepayplans.size());
				
				if (term0daycount > 0)
					firstdto.setPlanRepayInterest(dayInteresting.multiply(new BigDecimal(term0daycount)));
				else
					firstdto.setPlanRepayInterest(monthInteresting);
				
				//每月所还本金=贷款总金额/贷款期次
				BigDecimal monthPrincalPay = amount.divide(new BigDecimal(termcount), 10, RoundingMode.DOWN);

				int index = 0;
				//中间月份
				for(int i = temprepayplans.size()-2; i>=0; i--){
					RfRepayPlanDTO dto = temprepayplans.get(i);
					repayplans.add(dto);
					
					index++;
					
					//每月应还利息=剩余本金×月利率=(贷款本金-已归还本金累计额)×月利率
					BigDecimal monthInterestingPay = amount.subtract(monthPrincalPay.multiply(new BigDecimal(index-1))).multiply(monthRating);
					
					dto.setPlanRepayPrincipal(monthPrincalPay);
					dto.setPlanRepayInterest(monthInterestingPay);	
					dto.setPeriod(index+1);
					dto.setTotalperiod(temprepayplans.size());
				}

			}				
			
		}else if (productRepaymentType.equals("principal_equal_and_interest_equal")){//等本等息
			
		}
		
		for(int i=0; i<repayplans.size(); i++){
			System.out.println("第"+repayplans.get(i).getPeriod()+"期："+DateFormatUtils.format(repayplans.get(i).getPlanRepayDateTime(),"yyyy-MM-dd"));
		}
		return repayplans;
	}

    /**
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    private int daysBetween(Date smdate,Date bdate)    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        try {
			smdate=sdf.parse(sdf.format(smdate));
	        bdate=sdf.parse(sdf.format(bdate));  
		} catch (ParseException e) {
			throw new AppException("日期报错");
		}  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    } 
    
	public static void main(String [] agrs) {
		try {
			MBUserInfoDTO userinfo = new MBUserInfoDTO();
			userinfo.setProductReceiveRate(new BigDecimal(0.08));
			userinfo.setProductEndTime("2017-01-31");
			userinfo.setProductFirstMonthDay(31);
			userinfo.setProductRepaymentType("repay_maturity");
//			userinfo.setProductRepaymentType("repay_maturity");
			MBWjProductProcess dd = new MBWjProductProcess();
			List<RfRepayPlanDTO> list=dd.calcRepayPlanByMonthInteresting(new BigDecimal(600), userinfo.getProductFirstMonthDay(), userinfo.getProductReceiveRate(),"2016-11-11",userinfo.getProductEndTime(), userinfo.getProductRepaymentType());
			for(RfRepayPlanDTO d:list){
				System.out.println(d.toString());
			}
			/*RfRepayPlanDTO rfRepayPlanDTO=dd.generateAdvExpireOrder(new BigDecimal(300),userinfo,"OR201603260000010580","2016-11-10","2016-12-11");
			System.out.println(rfRepayPlanDTO);

			System.out.println(rfRepayPlanDTO.getPlanRepayInterest().subtract(new BigDecimal("1.35")));*/

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
