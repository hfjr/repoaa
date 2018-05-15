package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.fab.server.util.Format;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.ConfirmShareModelDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderDetailDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBQueryOrderDetailMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBQueryOrderDetialService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserOperationService;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;

@Service
public class MBQueryOrderDetialService implements IMBQueryOrderDetialService {
	@Autowired
	private IMBQueryOrderDetailMapper shareModelMapper;
	
	@Autowired
	IMBUserOperationService mbUserOperationService;

	@Remote
	ISendEmailService sendEmailService;

		
	private static final String TRADETYPE_V1 = "v1";
	private static final String TRADETYPE_EA = "ea";
	private static final String TRADETYPE_TA = "ta";

	// 提现,充值时的title
	private static final Map<String, String> bankTitleMap = new HashMap<String, String>();

	// ea账户提现,ea ->ma,充值时,实时到账
	private static final Map<String, String> actualDateMap = new HashMap<String, String>();

	// 余额提现,ma-> ea ,ma -> v+,v+ -> ma ,代发ea时,显示进度
	private static final Map<String, String> showProcessMap = new HashMap<String, String>();

	// 根据交易类型,获取交易跑批时间
	private static final Map<String, String> batchTypeMap = new HashMap<String, String>();

	// ma->ea,ma->v+未成功时,提示确认与到账日期
	private static final Map<String, String> footTipMap = new HashMap<String, String>();

	// 交易完成,高亮显示
	private static final Map<String, String> activeMap = new HashMap<String, String>();

	// 交易完成日期的计算方式
	private static final Map<String, String> compeleteDateMap = new HashMap<String, String>();

	static {
		bankTitleMap.put("withdraw_ea", "提现到");
		bankTitleMap.put("withdraw_ta", "提现到");
		bankTitleMap.put("withdraw_ma", "提现到");
		bankTitleMap.put("recharge_ma", "充值卡");
		bankTitleMap.put("apply_ma_to_rf", "申购V理财");
		//update by eric $.2015-12-29 10:38:57 提现新增unknown类型
//		actualDateMap.put("withdraw_ea", "提现时间");
		actualDateMap.put("transfer_ea_to_ma", "到账时间");
		actualDateMap.put("transfer_ta_to_ma", "到账时间");
		actualDateMap.put("recharge_ma", "充值时间");
		actualDateMap.put("apply_ma_to_rf", "购买时间");
		
		// update by xuewentao 修改提现到ea 为显示进度条
		showProcessMap.put("withdraw_ea", "yes");
		showProcessMap.put("withdraw_ta", "yes");
		showProcessMap.put("transfer_ea_to_ma", "no");
		showProcessMap.put("transfer_ta_to_ma", "no");
		showProcessMap.put("recharge_ma", "no");
		showProcessMap.put("apply_ma_to_rf", "no");
		showProcessMap.put("batch_apply", "yes");
		showProcessMap.put("withdraw_ma", "yes");
		showProcessMap.put("apply_ma_to_ea", "yes");
		showProcessMap.put("apply_ma_to_v1", "yes");
		showProcessMap.put("transfer_v1_to_ma", "yes");
		showProcessMap.put("apply_ma_to_ta", "yes");

		batchTypeMap.put("batch_apply", TRADETYPE_EA);
		batchTypeMap.put("batch_apply", TRADETYPE_TA);
		batchTypeMap.put("apply_ma_to_ea", TRADETYPE_EA);
		batchTypeMap.put("apply_ma_to_ta", TRADETYPE_TA);
		batchTypeMap.put("apply_ma_to_v1", TRADETYPE_V1);

		footTipMap.put("batch_apply_noconfirm", "yes");
		footTipMap.put("apply_ma_to_ea_noconfirm", "yes");
		footTipMap.put("apply_ma_to_ta_noconfirm", "yes");
		footTipMap.put("apply_ma_to_v1_noconfirm", "yes");
		
		
		//add by xuewentao 新增提现成功显示类型 $.2015-12-28 14:40:42
		activeMap.put("withdraw_ea_success", "yes");
		activeMap.put("withdraw_ta_success", "yes");
		activeMap.put("batch_apply_confirm", "yes");
		activeMap.put("withdraw_ma_success", "yes");
		activeMap.put("apply_ma_to_v1_confirm", "yes");
		activeMap.put("apply_ma_to_ea_confirm", "yes");
		activeMap.put("apply_ma_to_ta_confirm", "yes");
		activeMap.put("transfer_v1_to_ma_confirm", "yes");

		compeleteDateMap.put("withdraw_ma", "next");
		compeleteDateMap.put("withdraw_ea", "current");
		compeleteDateMap.put("withdraw_ta", "current");
		compeleteDateMap.put("transfer_v1_to_ma", "next");
		compeleteDateMap.put("apply_ma_to_ea", "confirm");
		compeleteDateMap.put("apply_ma_to_ta", "confirm");
		compeleteDateMap.put("apply_ma_to_v1", "confirm");
		compeleteDateMap.put("batch_apply", "confirm");

	}

	public Map<String, String> getBankTitleMap() {
		return bankTitleMap;
	}

	public Map<String, String> getActualDateMap() {
		return actualDateMap;
	}

	public Map<String, String> getshowProcessMap() {
		return showProcessMap;
	}

	public Map<String, String> getBatchTypeMap() {
		return batchTypeMap;
	}

	public Map<String, String> getFootTipMap() {
		return footTipMap;
	}

	public Map<String, String> getActiveMap() {
		return activeMap;
	}

	public Map<String, String> getCompeleteDateMap() {
		return compeleteDateMap;
	}

	public MBOrderDetailDTO getProcess(String orderNo) {
		// 1. 校验订单号是否存在
		this.checkOrderExits(orderNo);
		
		MBOrderDetailDTO bill = shareModelMapper.queryBillDetailByOrderNo(orderNo);
		
		String orderType = bill.getOrderType();
		
		//	是否需要显示 进度明细
		String isShowProcess=showProcessMap.get(orderType);
		
		if(StringUtils.isBlank(isShowProcess)){
			//	交易类型不存在
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("content", this.getClass()+"[getProcess] 交易类型不存在,传入类型["+orderType+"]");
//			sendEmailService.sendAsyncEmail("SYSTEM_ERROR_WARN", paramMap);
			throw new AppException("交易类型不存在");
		}
		
		
		//	2. 包装填充信息
		//	2.1 不显示时间进度条
		if (isShowProcess.equals("no")) {
			return bill.getDefaultProcess
					(getBankTitleMap().get(orderType),
					getActualDateMap().get(orderType),
					getshowProcessMap().get(orderType));
		}

		//	2.2 显示时间进度条
		return bill.getProcessByOrderTypeAndStatus(
				getBankTitleMap().get(orderType),
				getActualDateMap().get(orderType),
				getshowProcessMap().get(orderType), 
				bill.getDate(),
				getCompeleteDate(orderType, bill.getDate()),
				getActiveMap().get(bill.getOrderStatus()),
				getFootTip(orderType, bill.getOrderStatus(), bill.getDate()));
	}
	
	
	
	public void checkOrderExits(String orderNo){
		if(StringUtils.isBlank(orderNo))
			throw new AppException("订单号不能为空");
		
		if(shareModelMapper.countOrderAmount(orderNo)<1)
			throw new AppException("订单不存在");
		
	}

	/**
	 * ma->ea,ma->v+未成功时,提示确认与到账日期
	 * 
	 * @param orderStatus
	 * @return
	 */
	private String getFootTip(String orderType, String orderStatus,String orderDate) {
		if (footTipMap.get(orderStatus) != null) {
			
			String comfirmDay = getConfirmAmountDate(batchTypeMap.get(orderType), orderDate);
			
			return "预计" + comfirmDay + "确认份额," + getNextDay(comfirmDay)	+ "收益到账";
		}
		return "";
	}

	/**
	 * 获取交易完成日期 ma提现,v1->ma交易日是订单日期 getNextDay()
	 * 
	 * ma->v1,ma->ea,批量申购ea,getConfirmAmountDate();
	 */
	/**
	 * 获取交易完成日期 ma提现,v1->ma交易日是订单日期 getNextDay()
	 * 
	 * ma->v1,ma->ea,批量申购ea,getConfirmAmountDate();
	 */
	private String getCompeleteDate(String orderType, String orderDate) {
		// 1. 订单类型
		String compeleteDateMapType = compeleteDateMap.get(orderType);
		if (StringUtils.isBlank(compeleteDateMapType))
			throw new AppException("订单类型不存在" + orderType);

		// 2. 返回对应日期
		//		(下一个自然日)
		if(compeleteDateMapType.equals("next"))
			return getNextDay(orderDate);
		
		
		if(compeleteDateMapType.equals("current")){
			try {
				return Format.dateToString(Format.stringToDate(orderDate,"yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd");
			} catch (ParseException e) {
				BaseLogger.error("转换当前日期异常：" + orderDate);
			}
			return "";
		}
		
		if(compeleteDateMapType.equals("next_noon"))
			return getNextNoonDay(orderDate);
		
		if(compeleteDateMapType.equals("confirm"))
			return getConfirmAmountDate(batchTypeMap.get(orderType), orderDate);
		
		return "";
	}

	/**
	 * 获取确认份额时间
	 * 
	 * 
	 * @return 2015-09-26
	 */
	private String getConfirmAmountDate(String transType, String buyDay) {

		try {
			String confirmDay = getConfirmAmountDate2(transType,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(buyDay));
			return confirmDay;
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
		if (TRADETYPE_TA.equals(transType)) {
			return getTAConfirmAmountDate(buyDay);
		}
		String paramKey = TRADETYPE_V1.equals(transType) ? "v1_confirm_share_detail_time"
				: "confirm_share_detail_time";

		Integer hmsInt = new Integer(new SimpleDateFormat("HHmmss").format(buyDay));
		Integer ymdInt = new Integer(new SimpleDateFormat("yyyyMMdd").format(buyDay));
		// 1. 查询公共日期 以及 今天是否是休假日
		ConfirmShareModelDTO confirmShareModelDTO = shareModelMapper.queryConfirmShareModel(paramKey, ymdInt);

		// 查询buyday 能确认份额的两个工作日
		List<String> workdays = shareModelMapper.queryNextTwoWorkdaysByBuyday(ymdInt);

		// 不是工作日 或者 大于需要确认份额的时间
		if ("N".equals(confirmShareModelDTO.getWorkingDayFlag())|| hmsInt >= new Integer(confirmShareModelDTO.getConfirmShareDetailTime())) {
			return workdays.get(1); // 取下一天
		}
		return workdays.get(0);
	}

	// 获取T金所确认份额的时间， 下一个工作日
	private String getTAConfirmAmountDate(Date buyDay) {
		Integer ymdInt = new Integer(new SimpleDateFormat("yyyyMMdd").format(buyDay));
		// 查询buyday 能确认份额的两个工作日
		List<String> workdays = shareModelMapper.queryNextTwoWorkdaysByBuyday(ymdInt);
		String confirmDay = workdays.get(0);
		try {
			return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(confirmDay));
		} catch (ParseException e) {
			BaseLogger.error("getTAConfirmAmountDate 获取下一确认份额天异常：" + buyDay);
		}
		return "";
	}


	/**
	 * 下一个中午
	 * @param day
	 * @return 2015-12-05
	 */
	private  String getNextNoonDay(String day) {
		try {
			//1. 传入时间当天12点
			Calendar calNoonTime = Calendar.getInstance();
			Date dateMoonTime = new SimpleDateFormat("yyyy-MM-dd").parse(day);
			calNoonTime.setTime(dateMoonTime);
			calNoonTime.add(Calendar.HOUR, 12);
			
			//2.原始时间
			Calendar cal = Calendar.getInstance();
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(day);
			cal.setTime(date);
			
			//3. 比较
			if(cal.after(calNoonTime)){
				//3.1	大于当天12点的D+2
				cal.add(Calendar.DATE, 2);
				return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			}
			
			//	3.2	小于当天12点的,D+1
			cal.add(Calendar.DATE, 1);
			return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		} catch (ParseException e) {
			BaseLogger.error("getNextMoonDay 获取下一天异常：" + day);
		}
		
		return "";
	}
	
	
	
	/**
	 * 下一个自然日
	 * @param day
	 * @return 2015-09-27
	 */
	private String getNextDay(String day) {
		try {
			Calendar cal = Calendar.getInstance();
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
			cal.setTime(date);
			cal.add(Calendar.DATE, 1);
			return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		} catch (ParseException e) {
			BaseLogger.error("getNextDay 获取下一天异常：" + day);
		}
		return "";
	}

	//下一个工作日
//	private String getNextWorkDay(String targetDateStr) {
//		Date date = null;
//		try {
//			date = Format.stringToDate(targetDateStr, "yyyy-MM-dd");
//		} catch (ParseException e) {
//			BaseLogger.error("getNextDay 获取下一个工作日异常：" + targetDateStr);
//			throw new AppException("系统繁忙,请稍后再试");
//		}
//		return mbUserOperationService.getNextWorkDay(date);
//	}

}