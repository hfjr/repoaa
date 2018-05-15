package com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.service.impl;

import org.springframework.stereotype.Service;

import com.fab.core.util.SpringContextUtils;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.service.ICheckBillImplService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.service.ICheckBillService;

@Service("checkBillService")
public class CheckBillServiceImpl implements ICheckBillService {

	
	public void downloadBillByType(String beanName,String date) throws Exception{
		ICheckBillImplService payChannel =
				(ICheckBillImplService)SpringContextUtils.getContext().getBean(beanName);
		payChannel.downloadBill(date);
	}

	
}
