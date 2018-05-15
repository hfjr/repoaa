package com.zhuanyi.vjwealth.wallet.service.mainbiz.phoneMessage.server.service.impl;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.phoneMessage.dto.MessageServerLogDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.phoneMessage.server.integration.mapper.IMBUserPhoneMessageMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.phoneMessage.server.service.IMBUserPhoneMessageService;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;
import com.zhuanyi.vjwealth.wallet.service.outer.message.server.service.IPhoneMessageService;

@Service
public class MBUserPhoneMessageService implements IMBUserPhoneMessageService {

	@Autowired
	private IMBUserPhoneMessageMapper userPhoneMessageMapper;

	@Autowired
	private ISequenceService sequenceService;

	@Remote   
	IPhoneMessageService phoneMessageService;

	@Remote
	ISendEmailService sendEmailService;
	
	private static final String SEND_TEXT_MESSAGE_LOG_IN = "-->MBUserPhoneMessageService-->sendTextMessage-->in--";
	private static final String SEND_TEXT_MESSAGE_LOG_INFO = "-->MBUserPhoneMessageService-->sendTextMessage-->plateformId IS NULL--";
	private static final String SEND_TEXT_MESSAGE_LOG_END = "-->MBUserPhoneMessageService-->sendTextMessage-->end--";
	private static final String ERROR_MESSAGE_001 = "业务类型为空;";
	private static final String ERROR_MESSAGE_002 = "手机号错误;";
	
	@Override
	@Async
	public void sendTextMessage(String systemName,String phone, String serviceType,
			Map<String, Object> defineMessageData) {
		BaseLogger.audit(SEND_TEXT_MESSAGE_LOG_IN);
		boolean flag = true;
		String plateformId = "";
		StringBuffer errorMessage = new StringBuffer();
		
		try {
			//检验参数
			if(StringUtils.isBlank(serviceType)){
				errorMessage.append(ERROR_MESSAGE_001);
				flag = false;
			}else{
				plateformId = userPhoneMessageMapper.queryMessageServiceByServiceType(serviceType);
				if(StringUtils.isBlank(plateformId)){
					BaseLogger.audit(SEND_TEXT_MESSAGE_LOG_INFO);
					errorMessage.append(SEND_TEXT_MESSAGE_LOG_INFO);
					flag = false;
				}
			}
			if (StringUtils.isBlank(phone)
					||!Pattern.compile("^[1][3,4,5,6,7,8][0-9]{9}$").matcher(phone).matches()) {
				errorMessage.append(ERROR_MESSAGE_002);
				flag = false;
			}
			
			MessageServerLogDTO messageServerLogDTO = new MessageServerLogDTO();
			messageServerLogDTO.setMessagePlateformId(plateformId);
			messageServerLogDTO.setPhone(phone);
			messageServerLogDTO.setRequestSystem(systemName);
			messageServerLogDTO.setSendData(defineMessageData!=null?defineMessageData.toString():"");
			messageServerLogDTO.setType(serviceType);
			messageServerLogDTO.setErrorMessage(errorMessage.toString());
			//调用记录
			userPhoneMessageMapper.insertMessageInterfaceLog(messageServerLogDTO);
		} catch (Exception e) {
			BaseLogger.error("MBUserPhoneMessageService-->sendTextMessage-->save log error--",e);
		}
		
		if(flag){
			try {
				phoneMessageService.sendTextMessageByBizType(phone, serviceType, defineMessageData);
				
				
//				phoneMessageService.sendTextMessageByBizType("13917988436", serviceType, defineMessageData);
			} catch (Exception e) {
				BaseLogger.error("to-->IPhoneMessageService-->sendTextMessage-->error--",e);
			}
		}
		
		BaseLogger.audit(SEND_TEXT_MESSAGE_LOG_END);
	}


	

}
