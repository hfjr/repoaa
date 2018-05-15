package com.zhuanyi.vjwealth.wallet.mobile.credit.server.utils;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.fab.core.exception.service.AppException;

public class MoneyUtil {
	
	private static final BigDecimal RATE = new BigDecimal(100);
	
	/**
	 * 转换成“元”为单位
	 * @param money
	 * @return
	 */
	public static String toYuan(String money){
		if(StringUtils.isBlank(money)){
			throw new AppException("金额转换异常");
		}
		try{
			BigDecimal tempMoney = new BigDecimal(money);
			tempMoney = tempMoney.divide(RATE);
			return tempMoney.toPlainString();
		}catch(Exception e){
			throw new AppException("金额转换异常");
		}
	}
	
	/**
	 * 转换成 “分” 为单位
	 * @param money
	 * @return
	 */
	public static String toFen(String money){
		if(StringUtils.isBlank(money)){
			throw new AppException("金额转换异常");
		}
		try{
			BigDecimal tempMoney = new BigDecimal(money);
			tempMoney = tempMoney.multiply(RATE);
			return tempMoney.toPlainString();
		}catch(Exception e){
			throw new AppException("金额转换异常");
		}
		
	}
	
}
