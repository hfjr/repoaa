package com.rqb.ips.depository.platform.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface CheckCommParamMapper {

	Map<String, String> queryWithDrawParam();
   
	String queryIpsAccountByUserId(@Param(value = "userId") String userId);

}
