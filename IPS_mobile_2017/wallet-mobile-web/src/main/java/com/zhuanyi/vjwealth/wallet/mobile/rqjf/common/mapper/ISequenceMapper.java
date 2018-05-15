package com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface ISequenceMapper {

	
	public String getFullSequence(@Param("fixName")String fixName,@Param("squenceName")String squenceName);
	
	

}
