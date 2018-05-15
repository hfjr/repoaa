package com.zhuanyi.vjwealth.wallet.mobile.common.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IBusessinessNoService;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ISequenceService;

@Service
public class BusessinessNoService implements IBusessinessNoService {

	@Autowired
	private ISequenceService sequenceService;

	// 订单序列
	private static String VJ_ORDER_SEQ = "vj_order";
	// 交易序列
	private static String VJ_TRADE_SEQ = "vj_trade";

	// 用户序列
	private static String VJ_USER_INFO_SEQ = "vj_user_info";

	// 账号序列
	private static String VJ_USER_ACCOUNT_SEQ = "vj_user_account";

	// 卡号序列
	private static String VJ_USER_CARD_SEQ = "vj_user_card";

	// 卡号序列
	private static String VJ_WALLET_BANK_CARD_SEQ = "vj_wallet_bank_card";
	
	//企业账户序列
	private static String VJ_ENTERPRISE_INFO_SEQ = "vj_enterprise_info";
	
	//企业管理员序列
	private static String VJ_ENTERPRISE_MANAGER_SEQ = "vj_enterprise_manager";
	
	//文件编号
	private static String VJ_FILE_ID = "vj_file_id";
	
	//文件批次号
	private static String VJ_FILE_BATCH = "vj_file_batch";
	

	private static String VJ_WALLET_BANK_CARD_NO_BEGIN = "6228";
	
	//购买协议编号
	private static String PURCHASE_V1_AGREEMENT="purchase_V1_agreement";

	// 最大长度
	private static int VJ_WALLET_BANK_CARD_NO_MAXLENGTH = 19;

	private String getSequenceNo(String prefix, String sequenceName) {
		StringBuffer sb = new StringBuffer();
		sb.append(prefix);
		sb.append(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		sb.append(sequenceService.getNextStringValue(sequenceName, 20 - sb.toString().length()));
		return sb.toString();
	}

	/**
	 * 获取订单号
	 * 
	 * @return
	 */
	public String getVjOrderNo() {
		return getSequenceNo("OR", VJ_ORDER_SEQ);
	}

	/**
	 * 获取交易单号
	 * 
	 * @return
	 */
	public String getVjTradeSeq() {
		return getSequenceNo("TR", VJ_TRADE_SEQ);
	}

	/**
	 * 获取用户序列
	 * 
	 * @return
	 */
	public String getVjUserInfoSeq() {
		return getSequenceNo("US", VJ_USER_INFO_SEQ);
	}

	/**
	 * 获取交易单号
	 * 
	 * @return
	 */
	public String getVjUserAccountSeq() {
		return getSequenceNo("AC", VJ_USER_ACCOUNT_SEQ);
	}

	/**
	 * 获取交易单号
	 * 
	 * @return
	 */
	public String getVjUserCardSeq() {
		return getSequenceNo("CA", VJ_USER_CARD_SEQ);
	}

	/**
	 * 
	 * @Title: getVjWalletBankCardNo
	 * @Description: 生成融桥宝银行银卡编号 6228开头 共19位 尾4位数字中不能包含4
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public String getVjWalletBankCardNo() {
		StringBuffer sb = new StringBuffer(VJ_WALLET_BANK_CARD_NO_BEGIN);
		// 中间11位数字，用seq
		sb.append(sequenceService.getNextStringValue(VJ_WALLET_BANK_CARD_SEQ, 11));
		// 末尾4位
		while (sb.length() < VJ_WALLET_BANK_CARD_NO_MAXLENGTH) {
			int tempNumber = new Random().nextInt(10);
			if (tempNumber != 4) {
				sb.append(tempNumber);
			}
		}
		return sb.toString();
	}

	public String getVjEnterpriseInfoNo(){
		return getSequenceNo("EN", VJ_ENTERPRISE_INFO_SEQ);
	}
	
	public String getVjEnterpriseManagerNo(){
		return getSequenceNo("MA", VJ_ENTERPRISE_MANAGER_SEQ);
	}
	
	/**
	 * 获取文件编号
	 * @return
	 */
	public String getVjFileId(){
		return getSequenceNo("FI", VJ_FILE_ID);
	}
	
	/**
	 * 获取文件批次编号
	 * @return
	 */
	public String getVjFileBatchNo(){
		return getSequenceNo("FN", VJ_FILE_BATCH);
	}
	
	
	public String getPurchaseV1AgreementNo(){
		return getSequenceNo("AG", PURCHASE_V1_AGREEMENT);
	}
	
}
