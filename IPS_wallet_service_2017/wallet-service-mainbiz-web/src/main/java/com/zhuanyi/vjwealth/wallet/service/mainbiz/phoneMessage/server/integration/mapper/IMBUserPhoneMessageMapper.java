package com.zhuanyi.vjwealth.wallet.service.mainbiz.phoneMessage.server.integration.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.phoneMessage.dto.MessageServerLogDTO;

/**
 * @author ce
 *
 */
@Mapper
public interface IMBUserPhoneMessageMapper {
	
	//查询短信业务对应模板信息
	String queryMessageServiceByServiceType(@Param("serviceType") String serviceType );
	
	//新增短信接口调用日志
	int insertMessageInterfaceLog(MessageServerLogDTO messageServerLogDTO);
	
}
