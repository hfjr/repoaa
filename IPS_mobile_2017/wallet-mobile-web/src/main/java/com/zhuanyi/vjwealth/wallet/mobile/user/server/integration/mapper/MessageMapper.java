package com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.dto.MessageDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MessageMapper {

    //查询消息列表
    List<Map<String, String>> queryUserMessageList(Map<String, Object> paramMap);

    //查询消息
    MessageDTO queryUserMessageDetailByMessageId(@Param("userId") String userId,@Param("messageId") String messageId);

    //将消息更新为已读
//    void updateUserMessageReadTypeByMessageId(@Param("messageId") String messageId);

    //将消息更新为已读
    void updateUserMessageReadTypeByUserIdAndMessageId(@Param("userId") String userId,@Param("messageId") String messageId);

    //将消息更新为已读 批量
//    void updateUserMessageReadTypeByMessageIds(String[] messageIds);

    //删除消息
//    void deleteUserMessageByMessageId(@Param("messageId") String messageId);

    //删除消息
//    void deleteMessageByUserIdAndMessageId(@Param("userId") String userId,@Param("messageId") String messageId);

    //删除消息 批量
//    void deleteUserMessageByMessageIds(String[] messageIds);
    //删除消息 批量
    void deleteMessageByUserIdAndMessageIds(Map<String,Object> params);

    
  //----V32版本--start
  //查询消息列表
    List<Map<String, String>> queryUserMessageListV32(Map<String, Object> paramMap);
    
  //查询消息
    MessageDTO queryUserMessageDetailByMessageIdV32(@Param("userId") String userId,@Param("messageId") String messageId);
    
  //删除消息 批量
    void deleteMessageByUserIdAndMessageIdsV32(Map<String,Object> params);
    
  //将消息更新为已读
    void updateUserMessageReadTypeByUserIdAndMessageIdV32(@Param("userId") String userId,@Param("messageId") String messageId);

    boolean queryUserHasNewMsgFlag(@Param("userId") String userId);
  //----V32版本--end
}
