package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserDetailDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserTradeAccountCardDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserAccountMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserInfoService;

@Service("mBUserInfoService")
public class MBUserInfoService implements IMBUserInfoService {

	@Autowired
    private IMBUserMapper userMapper;
	
	@Autowired
	private IMBUserAccountMapper userAccountMapper;
	
	@Override
	public MBUserDetailDTO queryUserByPhone(String phone) {
		return userMapper.queryUserDetailByPhone(phone);
	}

	@Override
	public UserTradeAccountCardDTO querySecurityCardByUserId(String userId) {
		return userAccountMapper.querySecurityCardByUserId(userId);
	}
}
