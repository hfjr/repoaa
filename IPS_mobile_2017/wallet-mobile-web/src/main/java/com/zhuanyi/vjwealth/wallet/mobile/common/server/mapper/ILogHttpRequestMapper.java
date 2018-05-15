package com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.dto.LogHttpRequestDTO;

@Mapper
public interface ILogHttpRequestMapper {
	
	/**
	 * 查询所有的广告板子信息
	 * @return
	 */
	int addLogHttpRequest(LogHttpRequestDTO logHttpRequestDTO);

	
}
