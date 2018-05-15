package com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjfmsg.server.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjfmsg.server.dto.MessageDTO;
@Mapper
public interface IMessageMapper {
	
	 public Map<String,String> getMessageTemplateInfo(@Param("templateNo")String templateNo);
	 
	 public Integer countTemplate(@Param("templateNo")String templateNo);
	
	 int insertSelective(MessageDTO messageDTO);
	 
	 
	 public void insertWjuserMessageRef(@Param("messageId")String messageId,@Param("userId")String userId);
}
