package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.FescoUserInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserFescoMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserFescoService;

@Service
public class MBUserFescoService implements IMBUserFescoService {

    @Autowired
    private IMBUserFescoMapper userFescoMapper;

    @Autowired
    private IMBUserMapper userMapper;
    
	@Override
	public int queryFescoUserCountByphone(String phone) {
		return userFescoMapper.queryFescoUserCountByphone(phone);
	}

	@Override
	public int queryFescoUserCountByOpenId(String openId) {
		return userFescoMapper.queryFescoUserCountByOpenId(openId);
	}

	@Override
	public void insertFescoUserInfo(FescoUserInfoDTO userInfo) {
		userFescoMapper.insertFescoUserInfo(userInfo);
	}

	@Override
	public void updateFescoUserInfo(FescoUserInfoDTO userInfo) {
		userFescoMapper.updateFescoUserInfo(userInfo);
	}

	@Override
	public List<FescoUserInfoDTO> queryFescoUsers() {
		return userFescoMapper.queryFescoUsers();
	}
	
	@Override
	public String queryFescoUrl(String key) {
		return userFescoMapper.getParamsValueByKeyAndGroup(key, "wage_advance");
	}

	@Override
	public void updateFescoPhoneByOpenId(FescoUserInfoDTO userInfo) {
		userFescoMapper.updateFescoPhoneByOpenId(userInfo);
	}

	@Override
	public void updateUserChannel(String phone, String encodePassword, String openId, String channel) {
		userMapper.updateUserChannel(phone, encodePassword, openId, channel);
	}
}
