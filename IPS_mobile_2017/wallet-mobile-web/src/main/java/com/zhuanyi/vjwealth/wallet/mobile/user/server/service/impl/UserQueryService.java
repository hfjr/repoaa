package com.zhuanyi.vjwealth.wallet.mobile.user.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.util.AccountMapUtils;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ISendExceptionEmailService;
import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.mapper.PaymentPasswordMapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.IUserQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IUserQueryService;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.BankBalanceLimitDTO;
import com.zhuanyi.vjwealth.wallet.service.balance.pay.server.service.IUserBankService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserService;

@Service
public class UserQueryService implements IUserQueryService {

	@Autowired
	private ISendExceptionEmailService sendExceptionEmailService;
	@Autowired
	private IUserQueryMapper userQueryMapper;
	@Remote
	private IUserBankService userBankService;
	@Autowired
	private PaymentPasswordMapper paymentPasswordMapper;
	@Value("${local_server_ip}")
	private String local_server_ip;
	@Remote
	IMBUserService mbUserService;

	public Map<String, Object> queryWithdrawCard(String userId) {
		Map<String, String> withdrawCard = userQueryMapper.queryUserSecurityCardInfo(userId);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (withdrawCard == null) {
			throw new AppException("没有可用的安全卡,请先去充值并绑卡");
		}
		returnMap.put("isBandCard", "yes");
		returnMap.put("card", AccountMapUtils.changeMapDataToMapString(withdrawCard));
		return returnMap;
	}

	public Map<String, Object> queryRechargeCard(String userId) {
		List<Map<String, String>> listMap = userQueryMapper.queryUserRechargeCardInfo(userId);
		// 查询用户卡片当天消费额度
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (listMap == null || listMap.size() < 1) {
			returnMap.put("isBandCard", "no");
			returnMap.put("cards", new ArrayList<Map<String, String>>());
			return returnMap;
		}
		returnMap.put("isBandCard", "yes");
		returnMap.put("cards", AccountMapUtils.changeListMapDataToLisMapString(listMap));

		return returnMap;
	}

	/*@Override
	public Map<Object, Object> queryUserIsOpen(String userId, String userName, String identNo, String realName) {

		// 判断用户是否开过户
		Map<String, Object> user = ipsUserMapper.queryUserType();
		HashMap<Object, Object> ip = ipsUserMapper.queryUserIsOpen(userId);
		if ("ips".equals(ip.get("tradeType")) && "1".equals(ip.get("tradeAccountStatus"))) {
			throw new AppException("你已开户!");// 继续
		}
		if (ip != null && ip.size() > 1) {
			throw new AppException("您已经开过户了");
		}else
		{
			BaseLogger.audit("没开过户");
		}
		//查用户手机号
		HashMap<Object, Object> phone = ipsUserMapper.queryUserPhone(userId);
		AccountOpenBean accountOpen = new AccountOpenBean();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		String date = sim.format(new Date());
		String merBillNo = GenerateMerBillNoUtil.getBillNoGenerate();
		accountOpen.setMerBillNo(merBillNo);
		accountOpen.setMerDate(date);
		
		accountOpen.setMobileNo(phone.get("phone").toString());
		accountOpen.setUserName(userName);
		accountOpen.setIdentNo(identNo);
		// 重数据库获取值 业务类型
		accountOpen.setBizType(user.get("bizType").toString());
		// 重数据库获取用户类型
		accountOpen.setUserType(user.get("userType").toString());
		accountOpen.setRealname(realName);
		// 自己的接口 先写死了

		// accountOpen.setWebUrl(local_server_ip+"/api/ips/openUser/webCallBack");
		// accountOpen.setS2SUrl(local_server_ip+"/api/ips/openUser/s2sCallBack");
		accountOpen.setWebUrl("http://ips.com/webtest.htm");
		accountOpen.setS2SUrl("http://ips.com/s2stest.htm");

		// 调用ips
		IPSResponse response = accountService.openAccount(accountOpen);

		// 取出返回数据
		String code = response.getCode();
		String msg = response.getMsg();
		String da = (String) response.getData();
		// System.out.println(da);
		HashMap<Object, Object> hashMap = new HashMap<>();
		hashMap.put("data", da);
		hashMap.put("code", code);
		hashMap.put("msg", msg);

		// ips数据落地
		// 用户信息
		ipsUserMapper.insertUserInfo(userId, userName, identNo, realName);
		// 存用户订单号 类型开户 ，状态为不确定(0)
		ipsUserMapper.insertUserBillNo(userId, merBillNo);
		return hashMap;

	}*/

	// 原来的接口
	@Override
	public Map<String, Object> queryRechargeCardV32(String userId) {
		List<Map<String, String>> listMap = userQueryMapper.queryUserRechargeCardInfoV32(userId);
		// 查询用户卡片当天消费额度
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (listMap == null || listMap.size() < 1) {
			returnMap.put("isBandCard", "no");
			returnMap.put("cards", new ArrayList<Map<String, String>>());
			return returnMap;
		}
		returnMap.put("isBandCard", "yes");
		returnMap.put("cards", AccountMapUtils.changeListMapDataToLisMapString(listMap));

		return returnMap;
	}

	@Override
	public Map<String, Object> queryRechargeCardV36(String userId) {
		List<Map<String, String>> listMap = userQueryMapper.queryUserRechargeCardInfoV36(userId);
		// 查询用户卡片当天消费额度
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (listMap == null || listMap.size() < 1) {
			returnMap.put("isBandCard", "no");
			returnMap.put("cards", new ArrayList<Map<String, String>>());
			return returnMap;
		}
		returnMap.put("isBandCard", "yes");
		returnMap.put("cards", AccountMapUtils.changeListMapDataToLisMapString(listMap));
		return returnMap;
	}

	public Map<String, Object> queryMyBankCards(String userId) {
		List<Map<String, String>> listMap = userQueryMapper.queryUserAllCardInfo(userId);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (listMap == null) {
			returnMap.put("cards", new ArrayList<Map<String, String>>());
			returnMap.put("isBandCard", "no");
			return returnMap;
		}
		returnMap.put("cards", AccountMapUtils.changeListMapDataToLisMapString(listMap));
		returnMap.put("isBandCard", "yes");
		return returnMap;
	}

	@Override
	public Map<String, Object> queryUserMessageList(String userId, String type, String page) {
		Integer currentPage = (Integer.parseInt(page) - 1) * 10;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("page", currentPage);

		List<Map<String, String>> resultList = null;
		// 获取个人消息列表
		if (type.equals("personal")) {
			resultList = userQueryMapper.queryUserPersonalMessageList(paramMap);
		}
		// 获取系统消息列表
		else if (type.equals("system")) {
			resultList = userQueryMapper.queryUserSystemMessageList(paramMap);
		}

		// 封装查询结果
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("isMore", "true");
		if (resultList == null) {
			resultMap.put("isMore", "false");
			resultMap.put("records", resultList);
			return resultMap;
		}
		if (resultList.size() < 10) {
			resultMap.put("isMore", "false");
		}
		resultMap.put("records", AccountMapUtils.changeListMapDataToLisMapString(resultList));
		return resultMap;

	}

	@Override
	public Map<String, String> queryUserMessageById(String id) {

		// 返回消息详情
		Map<String, String> map = userQueryMapper.queryUserMessageById(id);
		if (map == null) {
			throw new AppException("消息不存在");
		}
		// 将消息置为已读
		userQueryMapper.updateUserMessageReadById(id);

		return AccountMapUtils.changeMapDataToMapString(map);
	}

	// 账户中心显示接口
	@Override
	public Map<String, String> accountCenterInfo(String userId) {
		// 用户手机号与头像ID
		Map<String, String> userInfo = userQueryMapper.queryUserPhoneAndHeadPictureId(userId);
		if (userInfo == null) {
			sendExceptionEmailService.sendUserExceptionEmail(userId, "该userId对应的客户不存在", this.getClass(),
					"accountCenterInfo");
			throw new AppException("系统繁忙,请稍后再试");
		}
		// 我的银行卡数
		Map<String, String> bankNum = userQueryMapper.queryUserAllCardNum(userId);

		userInfo.putAll(bankNum);
		return AccountMapUtils.changeMapDataToMapString(userInfo);
	}

	// 账户中心显示接口
	@Override
	public Map<String, String> accountCenterInfoV3(String userId) {
		// 用户手机号与头像ID
		Map<String, String> userInfo = userQueryMapper.queryUserPhoneAndIsShowCerty(userId);
		if (userInfo == null) {
			sendExceptionEmailService.sendUserExceptionEmail(userId, "该userId对应的客户不存在", this.getClass(),
					"accountCenterInfo");
			throw new AppException("系统繁忙,请稍后再试");
		}
		// 我的银行卡数
		Map<String, String> bankNum = userQueryMapper.queryUserAllCardNum(userId);

		userInfo.putAll(bankNum);
		return AccountMapUtils.changeMapDataToMapString(userInfo);
	}

	// 账户中心显示接口
	@Override
	public Map<String, String> accountCenterInfoV31(String userId) {
		// 用户手机号与头像ID
		Map<String, String> userInfo = userQueryMapper.queryUserPhoneAndIsShowCertyAndIsInvestment(userId);
		if (userInfo == null) {
			sendExceptionEmailService.sendUserExceptionEmail(userId, "该userId对应的客户不存在", this.getClass(),
					"accountCenterInfo");
			throw new AppException("系统繁忙,请稍后再试");
		}
		// 我的银行卡数
		Map<String, String> bankNum = userQueryMapper.queryUserAllCardNum(userId);

		userInfo.putAll(bankNum);
		return AccountMapUtils.changeMapDataToMapString(userInfo);
	}

	// 账户中心显示接口
	@Override
	public Map<String, String> accountCenterInfoV32(String userId) {
		// 用户手机号与头像ID
		Map<String, String> userInfo = userQueryMapper.queryUserPhoneAndIsShowCertyAndIsInvestment(userId);
		if (userInfo == null) {
			sendExceptionEmailService.sendUserExceptionEmail(userId, "该userId对应的客户不存在", this.getClass(),
					"accountCenterInfo");
			throw new AppException("系统繁忙,请稍后再试");
		}
		// 我的银行卡数
		Map<String, String> bankNum = userQueryMapper.queryUserAllCardNum(userId);
		userInfo.putAll(bankNum);

		// 查询支付密码状态
		Map<String, String> passwordMap = paymentPasswordMapper.queryUserPaymentPasswordInfo(userId);
		userInfo.putAll(passwordMap);

		return AccountMapUtils.changeMapDataToMapString(userInfo);
	}

	@Override
	public Map<String, String> accountCenterInfoV33(String userId) {
		// 用户手机号与头像ID
		Map<String, String> userInfo = this.accountCenterInfoV32(userId);

		// 我的红包数（可用红包）
		Map<String, String> cashCouponNum = userQueryMapper.queryUserAllUsableCashCouponNum(userId);
		userInfo.putAll(cashCouponNum);

		return AccountMapUtils.changeMapDataToMapString(userInfo);
	}

	// 用户消息数
	@Override
	public Map<String, String> queryUserMessageNum(String userId) {

		return AccountMapUtils.changeMapDataToMapString(userQueryMapper.queryUserMessageNum(userId));
	}

	@Override
	public List<BankBalanceLimitDTO> queryAllSupportBankList() {

		return userBankService.queryAllSupportBankList();
	}

	@Override
	public List<BankBalanceLimitDTO> queryAllSupportBankListV36() {
		return userQueryMapper.queryAllSupportBankListV36();
	}

	@Override
	public List<BankBalanceLimitDTO> queryAllSupportBankListV32() {

		return userQueryMapper.queryAllSupportBankListV32();
	}
}
