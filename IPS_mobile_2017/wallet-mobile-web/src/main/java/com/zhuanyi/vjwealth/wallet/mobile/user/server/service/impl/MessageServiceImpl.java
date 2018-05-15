package com.zhuanyi.vjwealth.wallet.mobile.user.server.service.impl;

import com.zhuanyi.vjwealth.wallet.mobile.account.server.util.AccountMapUtils;
import com.zhuanyi.vjwealth.wallet.mobile.user.dto.MessageDTO;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.MessageMapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IMessageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yi on 16/1/29.
 */
@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    public Map<String, Object> queryMessageListByUserIdAndMessageType(String userId, String messageType, String page) {
        Integer currentPage = (Integer.parseInt(page) - 1) * 10;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("messageType", messageType);
        paramMap.put("page", currentPage);

        //消息列表
        List<Map<String, String>> resultList = messageMapper.queryUserMessageList(paramMap);

        //封装查询结果
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("isMore", "true");
        if (resultList == null) {
            resultMap.put("isMore", "false");
            resultMap.put("records", resultList);
            return resultMap;
        }
        if (resultList.size() < 10) {
            resultMap.put("isMore", "false");
        }
        resultMap.put("records", AccountMapUtils.changeListMapDataToLisMapString(resultList));
        return resultMap;
    }

    public MessageDTO queryUserMessageDetailByMessageId(String userId, String messageId) {
        return messageMapper.queryUserMessageDetailByMessageId(userId, messageId);
    }

    //    public void updateUserMessageReadTypeByUserIdAndMessageIds(String userId, String messageIds) {
//        String[] ids = StringUtils.split(messageIds, ",");
//        messageMapper.updateUserMessageReadTypeByMessageIds(ids);
//    }
//
    public void updateUserMessageReadTypeByUserIdAndMessageId(String userId, String messageId) {
        messageMapper.updateUserMessageReadTypeByUserIdAndMessageId(userId, messageId);
    }
//
//    public void updateUserMessageReadTypeByMessageId(String messageId) {
//        messageMapper.updateUserMessageReadTypeByMessageId(messageId);
//    }

    public void deleteMessageByUserIdAndMessageIds(String userId, String messageIds) {
        // 传入多个参数
        Map<String, Object> params = new HashMap<String, Object>();
        String[] messageIdsArray = StringUtils.split(messageIds, ",");
        params.put("userId", userId);
        params.put("messageIdsArray", messageIdsArray);
        messageMapper.deleteMessageByUserIdAndMessageIds(params);
    }

//    public void deleteMessageByMessageIds(String messageIds) {
//        String[] ids = StringUtils.split(messageIds, ",");
//        messageMapper.deleteUserMessageByMessageIds(ids);
//    }
//
//    public void deleteMessageByUserIdAndMessageId(String userId, String messageId) {
//        messageMapper.deleteMessageByUserIdAndMessageId(userId, messageId);
//    }
//
//    public void deleteMessageByMessageId(String messageId) {
//        messageMapper.deleteUserMessageByMessageId(messageId);
//    }

    //-------V32版本--start
    public Map<String, Object> queryMessageListByUserIdAndMessageTypeV32(String userId, String messageType, String page) {
        Integer currentPage = (Integer.parseInt(page) - 1) * 10;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("messageType", messageType);
        paramMap.put("page", currentPage);

        //消息列表
        List<Map<String, String>> resultList = messageMapper.queryUserMessageListV32(paramMap);

        //封装查询结果
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("isMore", "true");
        if (resultList == null) {
            resultMap.put("isMore", "false");
            resultMap.put("records", resultList);
            return resultMap;
        }
        if (resultList.size() < 10) {
            resultMap.put("isMore", "false");
        }
        resultMap.put("records", AccountMapUtils.changeListMapDataToLisMapString(resultList));
        return resultMap;
    }

    public MessageDTO queryUserMessageDetailByMessageIdV32(String userId, String messageId) {
        return messageMapper.queryUserMessageDetailByMessageIdV32(userId, messageId);
    }

    public void deleteMessageByUserIdAndMessageIdsV32(String userId, String messageIds) {
        // 传入多个参数
        Map<String, Object> params = new HashMap<String, Object>();
        String[] messageIdsArray = StringUtils.split(messageIds, ",");
        params.put("userId", userId);
        params.put("messageIdsArray", messageIdsArray);
        messageMapper.deleteMessageByUserIdAndMessageIdsV32(params);
    }

    public void updateUserMessageReadTypeByUserIdAndMessageIdV32(String userId, String messageId) {
        messageMapper.updateUserMessageReadTypeByUserIdAndMessageIdV32(userId, messageId);
    }

    @Override
    public Object queryUserHasNewMsg(String userId) {
        Map returnMap = new HashMap();
        returnMap.put("hasNewMsg", messageMapper.queryUserHasNewMsgFlag(userId));
        return returnMap;
    }

    //-------V32版本--end
}
