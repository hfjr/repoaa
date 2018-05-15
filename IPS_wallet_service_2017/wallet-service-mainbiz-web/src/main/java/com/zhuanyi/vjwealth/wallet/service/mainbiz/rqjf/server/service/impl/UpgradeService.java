package com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.mapper.IUpgradeMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.service.IUpgradeService;

@Service
public class UpgradeService implements IUpgradeService {

	@Autowired
	private IUpgradeMapper upgradeMapper;
	
	@Override
	public void upgradeService(String userId) {
		// 统计 佣金记录
		Integer countCommissionRecord=upgradeMapper.countCommissionRecord(userId);
		
		//最低是会员
		if(countCommissionRecord>0){
			// 更新等级id
			upgradeMapper.updateUserLevelId(userId, upgradeMapper.queryCKLevelId(userId));
			
		}
		
	}

}
