package com.zhuanyi.vjwealth.wallet.mobile.common.server.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ISendExceptionEmailService;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;

@Service
public class SendExceptionEmailService implements ISendExceptionEmailService{
	@Remote
	private ISendEmailService emailService;
	
	
	@Override
	public void sendAccountExceptionEmail(String userId, String errorMessage,Class<?> cls, String method) {
		sendException(userId, errorMessage, cls.toString()+"."+method);
	}
	@Override
	public void sendUserExceptionEmail(String userId, String errorMessage,Class<?> cls, String method) {
		sendException(userId, errorMessage, cls.toString()+"."+method);
	}
	
	public void sendException(String userId,String errorMessage,String location){
		BaseLogger.error("用户["+userId+"]错误信息:"+errorMessage+"坐标:["+location+"]");
		Map<String,Object> errorMap=new HashMap<String,Object>();
		errorMap.put("userId", userId);
		errorMap.put("errorMessage", errorMessage);
		errorMap.put("location", location);
		emailService.sendNormalEmail("SYSTEM_ERROR_0006", errorMap);
	}
}
