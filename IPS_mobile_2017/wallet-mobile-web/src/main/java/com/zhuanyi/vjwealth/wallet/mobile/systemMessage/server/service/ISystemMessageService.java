package com.zhuanyi.vjwealth.wallet.mobile.systemMessage.server.service;

import java.util.Map;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.systemMessage.server.dto.WjSystemMessageDTO;

/**
 * 系统消息接口
 * Created by ce 
 */
public interface ISystemMessageService {

    /**
     * 消息列表[]
     * @param userId
     * @param messageType
     * @param page
     * @return
     */
    Map<String, Object>  querySystemMessageList(String userId,String channelType)throws AppException;

    /**
     * 查询消息详情
     * @param userId
     * @param messageId
     * @return
     */
    WjSystemMessageDTO queryUserMessageDetailByMessageNo(String messageNo,String userId);

    

}