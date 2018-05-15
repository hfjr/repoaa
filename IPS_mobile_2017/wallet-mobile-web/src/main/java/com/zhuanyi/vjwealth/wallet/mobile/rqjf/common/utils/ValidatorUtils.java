package com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.fab.core.exception.service.AppException;

public class ValidatorUtils {

	//校验手机号
	public static void validatePhone(String phone){
		
		if(StringUtils.isBlank(phone)){
			throw new AppException("手机号不能为空");
		}
		
		if(!Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$").matcher(phone).matches()){
			// 验证手机号  
			throw new AppException("请输入正确手机号");
		}
	}
	
	//校验身份证
	public static void validateIDCard(String idNo){
		
		if(StringUtils.isBlank(idNo)){
			throw new AppException("身份证不能为空");
		}
		
		if(!IdcardValidator.isValidatedAllIdcard(idNo)){
			throw new AppException("身份证号不正确");
		}
	}
	
	//校验金额
	public static void validateAmt(String amt){
		
		if(StringUtils.isBlank(amt)){
			throw new AppException("金额不能为空");
		}
			
		if(!NumberUtils.isNumber(amt)){
			throw new AppException("请输入正确金额");	
		}
		
		if(Double.valueOf(amt)<=0){
			throw new AppException("金额必须大于0");	
		}
	}
	
	//校验page
	public static void validatePage(String page){

		if(StringUtils.isBlank(page)){
			throw new AppException("page不能为空");
		}
			
		if(!NumberUtils.isNumber(page)){
			throw new AppException("page必须为数字");	
		}
		
		if(Integer.parseInt(page)<=0){
			throw new AppException("页码必须>0");	
		}
		
	}
	public static void validateNumber(String number,String descript){
		
		if(!NumberUtils.isNumber(number)){
			throw new AppException(descript+"必须为数字");	
		}
		
		
	}
	
	public static void validateNull(String param,String paraDescription){
		if(StringUtils.isBlank(param))
			throw new AppException("["+paraDescription+"]参数不可为空");
		
	}
	public static void validateNull2(String param,String paraDescription){
		if(StringUtils.isBlank(param))
			throw new AppException(paraDescription);
		
	}
	
}
