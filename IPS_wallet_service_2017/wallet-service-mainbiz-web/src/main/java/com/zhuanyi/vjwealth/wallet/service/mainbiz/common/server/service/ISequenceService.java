package com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service;

/**
 * 公共序列 service <br>
 * 使用前置：{@link file comm_sequence.sql} 查看文件最下文件说明
 * 
 * 初始化使用：{@link file carloan_sequence_01.sql}
 * 
 * @author Speed J
 *
 */
public interface ISequenceService {
	public static final String SEQ_COMMON = "common_seq"; // 公共sequence 序列
	public static final String SEQ_NAME_USER_ID_SEQ = "vj_user_info"; // 用户ID
	public static final String SEQ_NAME_USER_ACCOUNT_SEQ = "vj_user_account"; // 用户账户
	public static final String SEQ_NAME_ORDER_SEQ = "vj_order"; // 订单seq
	public static final String SEQ_NAME_CHECK_ACCOUNT_SEQ = "wj_check_account"; // 对账单主表seq
	public static final String SEQ_NAME_CHECK_ACCOUNT_DETAIL_SEQ = "wj_check_account_detail"; // 对账单明细seq
	
	public static final String SEQ_NAME_CARD_SEQ = "vj_user_card"; //card

	public static final String SEQ_NAME_REPAYPLAN_SEQ = "vj_rf_replayplan"; // 还款计划seq
	public static final String SEQ_NAME_RFACOUNT_SEQ = "vj_rf_acount"; // wj_user_trade_account_rfseq
	public static final String SEQ_NAME_ORDER_PRODUCT_SEQ = "vj_rf_order_product"; // 产品订单表seq

	public static final String SEQ_NAME_TRADE_SEQ = "vj_rf_trade"; // 交易流水号seq

	//public static final String SEQ_NAME_TRADE_SEQ_TA = "vj_ta_trade"; //TA交易流水号  add by zhangyingxuan on 20160712

	public static final String SEQ_NAME_CONTRACT_SEQ = "wj_contract_info"; // 合同流水号seq

	public static final int DEFAULT_ADDED_TO_LENGTH = 0; // 默认补齐查询位数
	public static final char DEFAULT_ADDED_CHARACTER = '0'; // 默认补齐填充字符

	/**
	 * 获取当前序列 默认查询 "common_seq" 公共seq
	 * 
	 * @return
	 */
	Long getCurrentLongValue();

	/**
	 * 获取当前序列
	 * 
	 * @param sequenceName
	 *            序列号名称 {@link file comm_sequence.sql}
	 * @return
	 */
	Long getCurrentLongValue(String sequenceName);

	/**
	 * 获取当前序列 默认查询 "common_seq" 公共seq
	 * 
	 * @return
	 */
	String getCurrentStringValue();

	String getCurrentStringValue(String sequenceName);

	/**
	 * 获取当前序列
	 * 
	 * @param sequenceName
	 *            序列号名称 {@link file comm_sequence.sql}
	 * @param addedToLength
	 *            补齐位数 若返回值为 23， addedToLength = 5 则 return 00023 以'0补充' 默认为 0
	 *            不补齐
	 * @return
	 */
	String getCurrentStringValue(String sequenceName, int addedToLength);
	

	/**
	 * 获取当前序列
	 * 
	 * @param sequenceName
	 *            序列号名称 {@link file comm_sequence.sql}
	 * @param 	bussiessType	业务类型
	 * @param 	allLength		总长度
	 * 					
	 *            补齐位数 若返回值为 23， addedToLength = 5 则 return 00023 以'0补充' 默认为 0
	 *            不补齐
	 * @return
	 */
	String getBussinessCurrentStringValue(String bussiessType,String sequenceName, int allLength);
	
	/**
	 * 获取当前序列
	 * 
	 * @param sequenceName
	 *            序列号名称 {@link file comm_sequence.sql}
	 * @param addedToLength
	 *            补齐位数 若返回值为 23， addedToLength = 5 则 return 00023 以'0补充' 默认为 0
	 *            不补齐
	 * @param addedCharacter
	 *            当 addedToLength 大于0时 则 补齐的用 addedCharacter 去补齐
	 * @return
	 */
	String getCurrentStringValue(String sequenceName, int addedToLength, char addedCharacter);

	/**
	 * 获取下一个序列 默认查询 "common_seq" 公共seq
	 * 
	 * @return
	 */
	Long getNextLongValue();

	/**
	 * 获取下一个序列值
	 * 
	 * @param sequenceName
	 * @return
	 */
	Long getNextLongValue(String sequenceName);

	/**
	 * 获取下一个序列 默认查询 "common_seq" 公共seq
	 * 
	 * @return
	 */
	String getNextStringValue();

	String getNextStringValue(String sequenceName);

	/**
	 * 获取下一个序列
	 * 
	 * @param sequenceName
	 *            序列号名称 {@link file comm_sequence.sql}
	 * @param addedToLength
	 *            补齐位数 若返回值为 23， addedToLength = 5 则 return 00023 以'0补充' 默认为 0
	 *            不补齐
	 * @return
	 */
	String getNextStringValue(String sequenceName, int addedToLength);

	/**
	 * 获取下一个序列
	 * 
	 * @param sequenceName
	 *            序列号名称 {@link file comm_sequence.sql}
	 * @param addedToLength
	 *            补齐位数 若返回值为 23， addedToLength = 5 则 return 00023 以'0补充' 默认为 0
	 *            不补齐
	 * @param addedCharacter
	 *            当 addedToLength 大于0时 则 补齐的用 addedCharacter 去补齐
	 * @return
	 */
	String getNextStringValue(String sequenceName, int addedToLength, char addedCharacter);

}
