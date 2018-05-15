package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface IRqbCommonSequenceMapper {

	//获取下一个sequence
	public String getNextSequence(@Param("pre")String pre,@Param("sequenceName")String sequenceName);	
}
