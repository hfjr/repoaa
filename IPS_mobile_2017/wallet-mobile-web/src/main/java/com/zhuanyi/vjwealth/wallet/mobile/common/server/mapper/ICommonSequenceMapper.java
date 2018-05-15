package com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper;

import com.fab.server.annotation.Mapper;

@Mapper
public interface ICommonSequenceMapper {
	public static final String NAME_SPACE = ICommonSequenceMapper.class.getName();

	Long getCurrentValue(String sequenceName);

	Long getNextValue(String sequenceName);
}
