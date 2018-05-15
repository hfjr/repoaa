package com.zhuanyi.vjwealth.wallet.service.mainbiz.account.server.service;

public interface ICheckBillImplService {
	
	/**
	 * 下载对账文件
	 * @param date
	 */
	public void downloadBill(String date) throws Exception; 
}
