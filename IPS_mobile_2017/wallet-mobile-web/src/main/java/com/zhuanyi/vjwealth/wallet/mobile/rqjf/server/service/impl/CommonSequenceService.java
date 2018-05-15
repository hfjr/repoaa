package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper.IRqbCommonSequenceMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.ICommonSequenceService;

@Service
public class CommonSequenceService implements ICommonSequenceService {
	@Autowired
	private IRqbCommonSequenceMapper rqbCommonSequenceMapper;

	@Override
	public String getNextSequence(String preName, String sequenceName) {
	
		return rqbCommonSequenceMapper.getNextSequence(preName, sequenceName);
	}
}
