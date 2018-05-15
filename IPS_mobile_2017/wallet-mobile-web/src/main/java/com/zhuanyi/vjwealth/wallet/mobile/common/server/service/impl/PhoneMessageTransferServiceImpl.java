package com.zhuanyi.vjwealth.wallet.mobile.common.server.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import vj.EncrypterTool;

import com.alibaba.fastjson.JSONObject;
import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IPhoneMessageTransferService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.phoneMessage.server.service.IMBUserPhoneMessageService;

/**
 * Created by ce
 */
@Service
public class PhoneMessageTransferServiceImpl implements IPhoneMessageTransferService {

	@Remote
	private IMBUserPhoneMessageService userPhoneMessageService;
	
	private static final String SEND_MESSAGE_TYPE = "bizType";
	private static final String SEND_MESSAGE_SYSTEM = "systemName";
	private static final String SEND_MESSAGE_DATA = "defineMessageData";
	private static final String SEND_MESSAGE_PHONE = "phone";
	private static final String DATA = "data";
	private static final String SIGN = "sign";
	private static final String RESULT = "result";
	private static final String SUCCESS = "success";
    
	@Override
	public Map<String, Object> sendPhoneMessage(String jsonStr) {
		JSONObject json = JSONObject.parseObject(jsonStr);
		String data = json.getString(DATA);
		String sign = json.getString(SIGN);
		
		String expectedSign = EncrypterTool.string2MD5(data + "abc");
		if(!sign.equals(expectedSign)){
			throw new AppException("sign is error!");
		}
		
		if(StringUtils.isBlank(data)){
			BaseLogger.error("sendPhoneMessage-->data is null!");
			throw new AppException("data is null");
		}
		if(StringUtils.isBlank(sign)){
			BaseLogger.error("sendPhoneMessage-->sign is null!");
			throw new AppException("sign is null");
		}
		JSONObject jsonData = JSONObject.parseObject(data);
		String defineMessageData = jsonData.getString(SEND_MESSAGE_DATA);
		//校验参数
		verificaData(jsonData);
		
		JSONObject jsonDefineMessageData = JSONObject.parseObject(defineMessageData);
		Set<String> key = jsonDefineMessageData.keySet();
		Iterator<String> iter = key.iterator();
		Map<String,Object> map = new HashMap<String,Object>();
	    while (iter.hasNext()) {
	        String field = iter.next();
	        map.put(field, jsonDefineMessageData.get(field));
	    }
	    userPhoneMessageService.sendTextMessage(jsonData.getString(SEND_MESSAGE_SYSTEM),jsonData.getString(SEND_MESSAGE_PHONE), 
	    		jsonData.getString(SEND_MESSAGE_TYPE), map);
	    
	    Map<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put(RESULT, SUCCESS);
	    return resultMap;
	}

    private void verificaData(JSONObject jsonData){
    	if(StringUtils.isBlank(jsonData.getString(SEND_MESSAGE_TYPE))){
			BaseLogger.error("sendPhoneMessage-->bizType is null!");
			throw new AppException("bizType is null");
		}
    	if(StringUtils.isBlank(jsonData.getString(SEND_MESSAGE_DATA))){
			BaseLogger.error("sendPhoneMessage-->defineMessageData is null!");
			throw new AppException("defineMessageData is null");
		}
    	if(StringUtils.isBlank(jsonData.getString(SEND_MESSAGE_SYSTEM))){
			BaseLogger.error("sendPhoneMessage-->systemName is null!");
			throw new AppException("systemName is null");
		}
    	if(StringUtils.isBlank(jsonData.getString(SEND_MESSAGE_PHONE))){
			BaseLogger.error("sendPhoneMessage-->phone is null!");
			throw new AppException("phone is null");
		}
    }
    
}
