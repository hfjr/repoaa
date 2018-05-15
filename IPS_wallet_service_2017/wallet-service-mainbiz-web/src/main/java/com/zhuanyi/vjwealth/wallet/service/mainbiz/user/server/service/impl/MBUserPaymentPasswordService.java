package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserPaymentPasswordMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserPaymentPasswordService;

@Service
public class MBUserPaymentPasswordService implements IMBUserPaymentPasswordService {
	
	@Autowired
	private IMBUserPaymentPasswordMapper mBUserPaymentPasswordMapper;

	@Override
	public int updateUserPaymentPasswordState(String userId, String state) {
		return mBUserPaymentPasswordMapper.updateUserPaymentPasswordState(userId, state);
	}

	@Override
	public int lockUserPaymentPasswordForCheck(String userId) {
		return mBUserPaymentPasswordMapper.lockUserPaymentPasswordForCheck(userId);
	}

	@Override
	public int unlockUserPaymentPassword(String userId) {
		return mBUserPaymentPasswordMapper.unlockUserPaymentPassword(userId);
	}

	@Override
	public void saveUserPaymentPassword(String userId, String password) {
		mBUserPaymentPasswordMapper.saveUserPaymentPassword(userId,password);
	}

	@Override
	public int deleteUserPaymentPassword(String userId, String password) {
		return mBUserPaymentPasswordMapper.deleteUserPaymentPassword(userId, password);
	}

	@Override
	public int updateUserPaymentPasswordAuthorizationFailures(String userId,
			String failures, String reason) {
		return mBUserPaymentPasswordMapper.updateUserPaymentPasswordAuthorizationFailures(userId,failures,reason);
	}

	@Override
	public int lockUserPaymentPasswordForAuthorization(String userId) {
		return mBUserPaymentPasswordMapper.lockUserPaymentPasswordForAuthorization(userId);
	}

	@Override
	public int updateUserPaymentPassword(String userId, String password) {
		return mBUserPaymentPasswordMapper.updateUserPaymentPassword(userId,password);
	}

	@Override
	public int updateUserPaymentPasswordValidationFailures(String userId,
			String failures, String reason) {
		return mBUserPaymentPasswordMapper.updateUserPaymentPasswordValidationFailures(userId,failures,reason);
	}
	
	
	@Override
	public int unlockInitializationForArtificial(String userId){
		return mBUserPaymentPasswordMapper.deleteUserPaymentPasswordByUserId(userId);
	}
	

}
