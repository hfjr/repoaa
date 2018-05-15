package com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.fab.server.util.Format;

public class MySalaryPlanDateUtils {
	
	//获取当前Calendar日期
	public static Calendar getCurrentCalendar(){
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		return cal;
	}
	
	//获取当前Calendar的日
	public static Integer getCurrentDay(){
		return getCurrentCalendar().get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取定存日期
	 * 格式默认为"yyyy年MM月dd日"
	 * @return
	 */
	public static String getDepositDateString(String day){
		Calendar cal=getCurrentCalendar();
		if(StringUtils.isNotEmpty(day)) {
			cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day));
			if(Integer.valueOf(day) < getCurrentDay()) {
				cal.add(Calendar.MONTH, 1);
			}
		}
		return Format.dateToString(cal.getTime(), "yyyy年MM月dd日");
	}

//	public static void main(String[] args) {
//		System.out.println(getCurrentDay());
//		System.out.println(getDepositDateString("05"));
//	}

}
