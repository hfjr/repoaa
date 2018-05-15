package com.zhuanyi.vjwealth.wallet.mobile.systemMessage.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.util.AccountMapUtils;
import com.zhuanyi.vjwealth.wallet.mobile.systemMessage.server.dto.WjSystemMessageDTO;
import com.zhuanyi.vjwealth.wallet.mobile.systemMessage.server.mapper.SystemMessageMapper;
import com.zhuanyi.vjwealth.wallet.mobile.systemMessage.server.service.ISystemMessageService;

/**
 * Created by ce
 */
@Service
public class SystemMessageServiceImpl implements ISystemMessageService {

    @Autowired
    private SystemMessageMapper systemMessageMapper;
    
    private static final int DISPLAY_TIME_01 = 999;//升级维护显示时间
    private static final int DISPLAY_TIME_02 = 10;//广告类显示时间
    private static final int DISPLAY_TIME_EACH = 5;//每条信息显示时间(秒)
    
    private static final String SYSTEM_MESSAGE_TYPE_ACTIVITY = "activity";
    private static final String SYSTEM_MESSAGE_TYPE_ADVERTISEMENT = "advertisement";
    private static final String SYSTEM_MESSAGE_TYPE_MAINTENANCE = "maintenance";
    private static final String SYSTEM_MESSAGE_TYPE = "serviceType";
    private static final String SYSTEM_MESSAGE_DISPLAY_TIME = "displayTime";
    private static final String SYSTEM_MESSAGE_DISPLAY_TIME_EACH = "displayTimeEach";
    private static final String SYSTEM_MESSAGE_LIST = "systemMessageList";

    public Map<String, Object> querySystemMessageList(String userId,String channelType)throws AppException {
    	if(StringUtils.isBlank(channelType)){
    		throw new AppException("渠道类型不能为空!");
    	}
    	int total = 0;
        //消息列表
        List<Map<String, String>> resultList = systemMessageMapper.querySystemMessageList(userId,channelType);
        //封装查询结果
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (resultList == null) {
            resultMap.put(SYSTEM_MESSAGE_LIST, resultList);
            return resultMap;
        }
        
        int count = (DISPLAY_TIME_02/DISPLAY_TIME_EACH-1)>1?(DISPLAY_TIME_02/DISPLAY_TIME_EACH-1):1;
        total += DISPLAY_TIME_EACH * resultList.size()* count;
        try {
			for(Map<String, String> map:resultList){
				
				if(SYSTEM_MESSAGE_TYPE_ADVERTISEMENT.equals(map.get(SYSTEM_MESSAGE_TYPE))
						||SYSTEM_MESSAGE_TYPE_ACTIVITY.equals(map.get(SYSTEM_MESSAGE_TYPE))){
					total += DISPLAY_TIME_EACH;
					map.put(SYSTEM_MESSAGE_DISPLAY_TIME, String.valueOf(total));
				}else if(SYSTEM_MESSAGE_TYPE_MAINTENANCE.equals(map.get(SYSTEM_MESSAGE_TYPE))){
					total += DISPLAY_TIME_01;
				}
			}
		} catch (Exception e) {
			BaseLogger.error("系统消息解析出错;",e);
		}
        
        resultMap.put(SYSTEM_MESSAGE_DISPLAY_TIME_EACH, String.valueOf(DISPLAY_TIME_EACH));
        resultMap.put(SYSTEM_MESSAGE_LIST, AccountMapUtils.changeListMapDataToLisMapString(resultList));
        return resultMap;
    }

    public WjSystemMessageDTO queryUserMessageDetailByMessageNo(String messageNo,String userId) {
    	WjSystemMessageDTO systemMessageDTO = systemMessageMapper.querySystemMessageDetailByMessageNo(messageNo);
    	if(systemMessageDTO==null){
			throw new AppException("系统消息不存在");
		}
    	
    	if(StringUtils.isNotBlank(userId)){
    		//新增消息阅读记录
    		systemMessageMapper.insertSystemMessageUserLog(systemMessageDTO.getMessageNo(),userId,systemMessageDTO.getServiceType());
    	}
    	
        return systemMessageDTO;
    }

}
