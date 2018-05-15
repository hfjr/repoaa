package com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.server.util.Format;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.mapper.IRegistSendRecommenderMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.rqjf.server.service.IRegisteredService;

@Service
public class RegisteredService implements IRegisteredService {
	
	@Autowired
	private IRegistSendRecommenderMapper registSendRecommenderMapper;

	@Override
	public void regristSendRecommender(String userId, String recommendUserId) {
		if(StringUtils.isBlank(recommendUserId)){
			return ;
		}
		Calendar date=Calendar.getInstance();
		date.add(Calendar.DATE, 30);
		registSendRecommenderMapper.insertRqbPackageInfo(recommendUserId, "10","10", Format.dateToString(new Date()), Format.dateToString(date.getTime(),"yyyy-MM-dd"));
	}
	

}
