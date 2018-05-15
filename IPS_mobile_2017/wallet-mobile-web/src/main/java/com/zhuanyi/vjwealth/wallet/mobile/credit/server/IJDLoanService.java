package com.zhuanyi.vjwealth.wallet.mobile.credit.server;


import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.easyLoan.EasyLoanSaveDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBRechargeDTO;

/**
 * 京东金融绑卡接口
 */
public interface IJDLoanService {


	/**
	 * 查询支持的银行卡列表
	 * @return
     */
	Object querySupportBankList();

	/**
	 * 查询用户绑定过的银行卡
	 * @param userId
	 * @return
     */
	Object queryBindedCardList(String userId);

	/**
	 * 绑定银行卡初始化
	 * @param userId
	 * @return
     */
	Object bindCardInit(String userId);


	/**
	 * 查询银行卡类型及名称
	 * @param cardNo
	 * @return
     */
	Object queryBankNameByCardNo(String cardNo);

	/**
	 * 发送验证码
	 * @param dto
	 * @return
     */
	Object bindCardSendSms(MBRechargeDTO dto);

	/**
	 * 发送验证码
	 * @param dto
	 * @return
     */
	Object bindCardConfirm(MBRechargeDTO dto);

}
