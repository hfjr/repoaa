package com.zhuanyi.vjwealth.wallet.mobile.user.server.service;

import java.util.List;
import java.util.Map;

import com.rqb.ips.depository.platform.beans.AccountOpenBean;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.BankBalanceLimitDTO;

public interface IUserQueryService {
	
	/**
	 * @title 查询用户的提现卡(在用户提现的时候调用次接口,只会返回一张卡)
	 * @param 用户ID
	 * @return "cardId":  卡id
               "sufCardNo": 尾号
               "bankName": 银行名称
               "cardType": 卡类型
               "bankCode": 银行编码
	 */
	Map<String,Object> queryWithdrawCard(String userId);
	
	/**
	 * @title 查询用户充值卡(在充值的时候调用此接口,可能返回多张卡)
	 * @param 用户ID
	 * @return "cardId":  卡id
               "sufCardNo": 尾号
               "bankName": 银行名称
               "cardType": 卡类型
               "bankCode": 银行编码
	 */
	Map<String,Object> queryRechargeCard(String userId);
	
	/**
	 * @title 查询用户充值卡(在充值的时候调用此接口,可能返回多张卡)
	 * @param 用户ID
	 * @return "cardId":  卡id
               "sufCardNo": 尾号
               "bankName": 银行名称
               "cardType": 卡类型
               "bankCode": 银行编码
	 */
	Map<String,Object> queryRechargeCardV32(String userId);
	
	/**
	 * @title ips开户
	 * @param 用户ID
	 * @return 
	 */
	//Map<Object,Object> queryUserIsOpen(String userId, String userName, String identNo,String realName);*/
	/**
	 * @title 查询用户充值卡(在充值的时候调用此接口,可能返回多张卡)
	 * @return "cardId":  卡id
	"sufCardNo": 尾号
	"bankName": 银行名称
	"cardType": 卡类型
	"bankCode": 银行编码
	 */
	Map<String,Object> queryRechargeCardV36(String userId);
	
	/**
	 * @title 查询我的银行卡
	 * @param 用户ID
	 * @return "cardId":  卡id
               "sufCardNo": 尾号
               "bankName": 银行名称
               "cardType": 卡类型
               "bankCode": 银行编码
	 */
	Map<String,Object> queryMyBankCards(String userId);
	
	
	
	/**
	 * @title 查询用户的消息列表
	 * @param userId 用户ID
	 * @param type 消息类型(personal:个人;system:系统)
	 * @param page 显示页
	 * @return "id" 主键id
	 *         "title" 消息标题
	 *         "readType" 是否阅读
	 *         "sub_title"  消息简介
	 *         "createDate" 消息时间
	 */
	Map<String,Object> queryUserMessageList(String userId,String type,String page);
	
	/**
	 * 
	 * @param 消息主键ID
	 * @return "content" 内容
	 *        
	 */
	Map<String,String> queryUserMessageById(String id);
	
	/**
	 * @title 账户中心显示接口
	 * @return  "cardNum": "3张",                 //我的银行卡数
                "headSculptureId": "US_12123123", //头像文件ID(用于下载头像)
                "phone": "132****8965"            //手机号
	 */
	Map<String,String> accountCenterInfo(String userId);


	/**
	 * @title 账户中心显示接口v3
	 * @return  "cardNum": "3张",                 //我的银行卡数
	"isShowCerty": "yes or no", // 是否显示实名认证
	"phone": "132****8965"            //手机号
	 */
	Map<String,String> accountCenterInfoV3(String userId);
	
	/**
	 * @title 账户中心显示接口v3.1
	 * @return  "cardNum": "3张",                 //我的银行卡数
	 * "isShowCerty": "yes or no", // 是否显示实名认证
	 * "phone": "132****8965"            //手机号
	 * "isSign" "yes"/"no"             是否是工资单用户
	 * @since 3.1
	 */
	Map<String,String> accountCenterInfoV31(String userId);
	
	
	/**
	 * @title 账户中心显示接口v3.2
	 * @return 
	 *	 "cardNum" : "2",
	 *   "isShowCerty" : "no",
	 *   "isShowInvertmentRecord" : "no",
	 *   "isShowpaymentPassword " : "yes",
	 *   "phone" : "185****6331",
	 *   "paymentPasswordStatusCode" : "201200",
	 *   "paymentPasswordStatusDescription" : "已设置",
	 *   "paymentPasswordDialog" : "支付密码已经锁定 |  关闭  | 解锁支付密码"
	 * @since 3.2
	 */
	Map<String,String> accountCenterInfoV32(String userId);

	/**
	 * 添加返回参数 cashCouponNum 我的可用红包数
	 * @param userId
	 * @return
     */
	Map<String,String> accountCenterInfoV33(String userId);

	
	/**
	 * @title 用户消息数
	 * @return  "userMessageNum":                //用户消息数
	 */
	Map<String,String> queryUserMessageNum(String userId);
	
	
	
	/**
	 * @title 查询支持的银行卡列表
	 * @return
	 *  bankCode
		bankName
		balancePerLimit
		balanceDayLimit
		status	  		--状态
		status_desc		--状态描述
		limitTip		--限额描述

	 */
	public List<BankBalanceLimitDTO> queryAllSupportBankList();
	/**
	 * @title 查询支持的银行卡列表
	 * @return
	 *  bankCode
	bankName
	balancePerLimit
	balanceDayLimit
	status	  		--状态
	status_desc		--状态描述
	limitTip		--限额描述

	 */
	public List<BankBalanceLimitDTO> queryAllSupportBankListV36();
	
	/**
	 * @title 查询支持的银行卡列表V3.2
	 * @return
	 *  bankCode
		bankName
		balancePerLimit
		balanceDayLimit
		status	  		--状态
		status_desc		--状态描述
		limitTip		--限额描述

	 */
	public List<BankBalanceLimitDTO> queryAllSupportBankListV32();
}
