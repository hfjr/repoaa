package com.zhuanyi.vjwealth.wallet.mobile.common.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.mobile.common.dto.LogHttpRequestDTO;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper.ILogHttpRequestMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ILogHttpRequestService;

@Service
public class LogHttpRequestService implements ILogHttpRequestService {
	@Autowired
	private ILogHttpRequestMapper ILogHttpRequestMapper;

	@Override
	public int addLogHttpRequest(LogHttpRequestDTO logHttpRequestDTO) {
		return ILogHttpRequestMapper.addLogHttpRequest(logHttpRequestDTO);
	}


}
