package com.zhuanyi.vjwealth.wallet.mobile.common.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.fab.server.util.Format;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.ConfirmShareModelDTO;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper.IShareModelMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IOrderDateHelperService;

/**
 * 账单的确认完成日期的计算
 * @author wangzf
 */
@Service
public class OrderDateHelperService implements IOrderDateHelperService{
	
	@Autowired
	private IShareModelMapper shareModelMapper;
	
	
	/**
	 * @title 获取工资，余额申购e账户的确认日期
	 * @param orderDate 账单创建日期
	 * @return 确认日期
	 */
	@Override
	public String getEaConfirmDate(String orderDate){
		String confirmDateStr;
		try {
			confirmDateStr = getConfirmDate("confirm_share_detail_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orderDate));
			return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(confirmDateStr));
		} catch (ParseException e) {
			BaseLogger.error("查询订单详情，日期格式转换异常",e);
			throw new AppException("查询订单详情，日期格式转换异常");
		}
		
	}

	/**
	 * @title 获取工资，余额申购e账户的确认日期
	 * @param orderDate 账单创建日期
	 * @return 确认日期
	 */
	@Override
	public String getTaConfirmDate(String orderDate){
//		String confirmDateStr;
//		try {
//			confirmDateStr = getConfirmDate("ta_confirm_share_detail_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orderDate));
//			return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(confirmDateStr));
//		} catch (ParseException e) {
//			BaseLogger.error("查询订单详情，日期格式转换异常",e);
//			throw new AppException("查询订单详情，日期格式转换异常");
//		}

		try {
			Integer ymdInt = new Integer(new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orderDate)));
			// orderDate 下一个工作日
			List<String> workdays = shareModelMapper.queryNextTwoWorkdaysByBuyday(ymdInt);
			String confirmDay = workdays.get(0);
			return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(confirmDay));
		} catch (ParseException e) {
			BaseLogger.error("getTAConfirmAmountDate 获取下一确认份额天异常：" + orderDate);
		}
		return "";
	}
	
	/**
	 * @title 获取余额申购v+的确认日期
	 * @param orderDate 账单创建日期
	 * @return 确认日期
	 */
	@Override
	public String getV1ConfirmDate(String orderDate){
		String confirmDateStr;
		try {
			confirmDateStr = getConfirmDate("v1_confirm_share_detail_time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orderDate));
			return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(confirmDateStr));
		} catch (ParseException e) {
			BaseLogger.error("查询订单详情，日期格式转换异常",e);
			throw new AppException("查询订单详情，日期格式转换异常");
		}
	}
	
	
	private String getConfirmDate(String paramKey,Date orderDate){
		Integer hmsInt = new Integer(new SimpleDateFormat("HHmmss").format(orderDate));
		Integer ymdInt = new Integer(new SimpleDateFormat("yyyyMMdd").format(orderDate));
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
	
	
	/**
	 * 下一个中午
	 * @param day
	 * @return 2015-12-05
	 */
	@Override
	public  String getNextNoonDay(String day){
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
	 * @title 根据账单时间，获取当天的日期（账单时间去掉时分秒,e账户提现）
	 * @param orderDate
	 * @return
	 */
	@Override
	public String getCurrentDay(String orderDate){
		try {
			return Format.dateToString(Format.stringToDate(orderDate,"yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd");
		} catch (ParseException e) {
			BaseLogger.error("查询订单详情，日期格式转换异常",e);
			throw new AppException("查询订单详情，日期格式转换异常");
		}
	}
	
	
	/**
	 * @title 下一个自然日( ma提现,v1->ma)
	 * @param orderDate 订单日期
	 * @return 2015-09-27
	 */
	@Override
	public String getNextDay(String orderDate) {
		try {
			Calendar cal = Calendar.getInstance();
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(orderDate);
			cal.setTime(date);
			cal.add(Calendar.DATE, 1);
			return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		} catch (ParseException e) {
			BaseLogger.error("getNextDay 获取下一天异常：" + orderDate);
			throw new AppException("查询订单详情，日期格式转换异常");
		}
	}

}
