package com.zhuanyi.vjwealth.wallet.mobile.account.server.service;

import java.util.Map;


/**
 * 三级账单明细
 * @author Eric
 */
public interface IOrderHelperService {
	
	Map<String,String> getProcess(String orderNo);
	
}
