package com.zhuanyi.vjwealth.wallet.mobile.common.server.service;

public interface IBusessinessNoService {

	/**
	 * 
	* @Title: getVjOrderNo 
	* @Description:  生成订单号
	* @param @return    
	* @return String    返回类型 
	* @throws
	 */
	String getVjOrderNo();
	
	/**
	 * 
	* @Title: getVjTradeSeq 
	* @Description:  生成交易号
	* @param @return    
	* @return String    返回类型 
	* @throws
	 */
	String getVjTradeSeq();
	
	/**
	 * 
	* @Title: getVjUserInfoSeq 
	* @Description:  生成用户信息ID
	* @param @return    
	* @return String    返回类型 
	* @throws
	 */
	String getVjUserInfoSeq();
	
	/**
	 * 
	* @Title: getVjUserAccountSeq 
	* @Description:   生成用户账户ID
	* @param @return    
	* @return String    返回类型 
	* @throws
	 */
	String getVjUserAccountSeq();
	
	/**
	 * 
	* @Title: getVjUserCardSeq 
	* @Description:  生成用户银行卡ID
	* @param @return    
	* @return String    返回类型 
	* @throws
	 */
	String getVjUserCardSeq();
	
	/**
	 * 
	* @Title: getVjWalletBankCardNo 
	* @Description:  生成融桥宝银行银卡编号
	* 	6228开头	共18位 尾号没有四	
	* @param @return    
	* @return String    返回类型 
	* @throws
	 */
	String getVjWalletBankCardNo();
	
	/**
	 * 
	* @Title: getVjEnterpriseInfoNo 
	* @Description:  企业账户信息ID 
	* @param @return    
	* @return String    返回类型 
	* @throws
	 */
	String getVjEnterpriseInfoNo();
	
	/**
	 * 
	* @Title: getVjEnterpriseManagerNo 
	* @Description:  企业管理人员（如HR）ID
	* @param @return    
	* @return String    返回类型 
	* @throws
	 */
	String getVjEnterpriseManagerNo();
	
	/**
	 * 
	* @Title: getVjFileId 
	* @Description:  文件主键的编号
	* @param @return    
	* @return String    返回类型 
	* @throws
	 */
	String getVjFileId();
	
	/**
	 * 
	* @Title: getVjFileId 
	* @Description:  文件批次的编号
	* @param @return    
	* @return String    返回类型 
	* @throws
	 */
	String getVjFileBatchNo();
	
	
	/**
	* @Title: getPurchaseV1AgreementNo 
	* @Description:  会员购买v1理财协议
	* @param @return    
	* @return String    返回类型 
	* @throws
	 */
	String getPurchaseV1AgreementNo();
}
