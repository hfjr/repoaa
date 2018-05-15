package com.rqb.ips.depository.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.rqb.ips.depository.platform.faced.IpsBidCardService;
import com.rqb.ips.depository.platform.mapper.IpsBidCardMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserAccountService;

@Service
public class IpsBidCardServiceImpl implements IpsBidCardService {

	
	@Autowired
	 private  IpsBidCardMapper  ipsBidCardMapper;

	@Override
	public String queryIsBidCard(String userId) {
		// TODO Auto-generated method stub
		return ipsBidCardMapper.queryIsBidCard(userId);
	}
	
	
	
}
