package com.rqb.ips.depository.platform.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
@Mapper
public interface IpsBidCardMapper {

	String queryIsBidCard(@Param(value = "userId") String userId);
 
}

