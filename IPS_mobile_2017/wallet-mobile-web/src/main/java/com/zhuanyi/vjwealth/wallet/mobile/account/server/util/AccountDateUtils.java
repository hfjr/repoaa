package com.zhuanyi.vjwealth.wallet.mobile.account.server.util;

import java.util.Calendar;
import java.util.Date;

import com.fab.server.util.Format;

public class AccountDateUtils {
	
	
	/**
	 * 获取昨日日期
	 * 格式默认为"yyyy-MM-dd"
	 * @return
	 */
	public static String getYestodayString(){
		Calendar cal=getCurrentCalendar();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return Format.dateToString(cal.getTime(), "yyyy-MM-dd");
	}
	
	public static String getYestodayString(String urlPattern){
		Calendar cal=getCurrentCalendar();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return Format.dateToString(cal.getTime(), urlPattern);
	}
	
	
	
	//获取当前Calendar日期
	public static Calendar getCurrentCalendar(){
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		return cal;
	}
	
	
	public static void main(String[] args) {
		System.out.println(getYestodayString());
	}

	
	
	

}
