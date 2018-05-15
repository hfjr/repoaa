package com.zhuanyi.vjwealth.wallet.mobile.user.server.service;

import com.zhuanyi.vjwealth.wallet.mobile.user.dto.MessageDTO;

import java.util.Map;

/**
 * 用户消息接口
 * Created by yi on 16/1/29.
 */
public interface IMessageService {

    /**
     * 消息列表[]
     * @param userId
     * @param messageType
     * @param page
     * @return
     */
    Map<String, Object>  queryMessageListByUserIdAndMessageType(String userId,String messageType,String page);

    /**
     * 查询消息详情
     * @param userId
     * @param messageId
     * @return
     */
    MessageDTO queryUserMessageDetailByMessageId(String userId,String messageId);
//
//    /**
//     * 消息阅读接口［批量］
//     * @param userId
//     * @param messageIds
//     */
//   void updateUserMessageReadTypeByUserIdAndMessageIds(String userId,String messageIds);
//
    /**
     * 单个消息阅读接口
     * @param userId
     * @param messageId
     */
    void updateUserMessageReadTypeByUserIdAndMessageId(String userId,String messageId);
//
//    /**
//     * 单个消息阅读接口
//     * @param messageId
//     */
//    void updateUserMessageReadTypeByMessageId(String messageId);
//
    /**
     * 消息删除［批量］
     * @param userId
     * @param messageIds
     */
    void deleteMessageByUserIdAndMessageIds(String userId,String messageIds);
//
//    /**
//     * 消息删除［批量］
//     * @param messageIds
//     */
//    void deleteMessageByMessageIds(String messageIds);
//
//    /**
//     * 单个消息删除
//     * @param userId
//     * @param messageId
//     */
//    void deleteMessageByUserIdAndMessageId(String userId,String messageId);
//
//    /**
//     * 单个消息删除
//     * @param messageId
//     */
//    void deleteMessageByMessageId(String messageId);

    //----V32版本--start
    /**
     * 消息列表[]
     * @param userId
     * @param messageType
     * @param page
     * @return
     */
    Map<String, Object>  queryMessageListByUserIdAndMessageTypeV32(String userId,String messageType,String page);
    
    /**
     * 查询消息详情
     * @param userId
     * @param messageId
     * @return
     */
    MessageDTO queryUserMessageDetailByMessageIdV32(String userId,String messageId);
    
    /**
     * 消息删除［批量］
     * @param userId
     * @param messageIds
     */
    void deleteMessageByUserIdAndMessageIdsV32(String userId,String messageIds);
    
    /**
     * 单个消息阅读接口
     * @param userId
     * @param messageId
     */
    void updateUserMessageReadTypeByUserIdAndMessageIdV32(String userId,String messageId);

    Object queryUserHasNewMsg(String userId);
    //----V32版本--end
}