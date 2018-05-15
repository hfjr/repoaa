package com.zhuanyi.vjwealth.wallet.mobile.common.server.service;

import com.zhuanyi.vjwealth.wallet.mobile.common.dto.LogHttpRequestDTO;

public interface ILogHttpRequestService {
	
	/**
	 * 增加http请求日志
	 * @param logHttpRequestDTO
	 * @return
	 */
	int addLogHttpRequest(LogHttpRequestDTO logHttpRequestDTO);
	
	
}
