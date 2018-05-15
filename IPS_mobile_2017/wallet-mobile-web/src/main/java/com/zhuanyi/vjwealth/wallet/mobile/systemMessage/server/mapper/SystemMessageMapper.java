package com.zhuanyi.vjwealth.wallet.mobile.systemMessage.server.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.systemMessage.server.dto.WjSystemMessageDTO;

@Mapper
public interface SystemMessageMapper {

    //查询系统消息列表
    List<Map<String, String>> querySystemMessageList(@Param("userId") String userId,@Param("channelType") String channelType);

    //查询消息
    WjSystemMessageDTO querySystemMessageDetailByMessageNo(@Param("messageNo") String messageNo);
    
    //新增用户系统消息查看记录
    int insertSystemMessageUserLog(@Param("messageNo")String messageNo,@Param("userId") String userId,@Param("serviceType") String serviceType);

}
