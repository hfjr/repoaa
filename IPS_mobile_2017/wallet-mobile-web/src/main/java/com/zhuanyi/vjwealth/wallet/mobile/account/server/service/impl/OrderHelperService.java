package com.zhuanyi.vjwealth.wallet.mobile.account.server.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.service.IOrderHelperService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBQueryOrderDetialService;

@Service
public class OrderHelperService implements IOrderHelperService{
	@Remote
	IMBQueryOrderDetialService mbImbQueryOrderDetialService;
	
	
	public Map<String, String> getProcess(String orderNo) {

		return mbImbQueryOrderDetialService.getProcess(orderNo).getMBOrderDetailMap();
	}
	
}
