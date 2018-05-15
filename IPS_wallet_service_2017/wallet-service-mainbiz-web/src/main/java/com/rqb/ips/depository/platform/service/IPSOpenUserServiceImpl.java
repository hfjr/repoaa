package com.rqb.ips.depository.platform.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.hf.comm.utils.GenerateMerBillNoUtil;
import com.hf.comm.utils.IpsRequestUtils;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.beans.AccountOpenBean;
import com.rqb.ips.depository.platform.faced.IPSOpenUserService;
import com.rqb.ips.depository.platform.mapper.IpsOpenUserMapper;
import com.rqb.ips.depository.platform.menu.Define;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderInfoDTO;

@Service

public class IPSOpenUserServiceImpl implements IPSOpenUserService {
	@Autowired
	private IpsOpenUserMapper ipsUserMapper;
	//回调地址
	@Value(value = "${local_server_ip}")
	private String localServerIp;
	
	@Override
	@Transactional
	public Map<Object, Object> queryUserIsOpen(String userId, String identNo, String realName,String source) {

		// 查询用户类型
		Map<String, Object> user = ipsUserMapper.queryUserType();
		// 判断用户是否开过户
		HashMap<Object, Object> ip = ipsUserMapper.queryUserIsOpen(userId);
		/*
		 * if ("ips".equals(ip.get("tradeType")) &&
		 * "1".equals(ip.get("tradeAccountStatus"))) { throw new
		 * AppException("你已开户!");// 继续 }
		 */
		//判断不为空 并且大于等于1
		if (ip != null && ip.size() >=1) {
			throw new AppException("您已经开过户了");
		} else {
			BaseLogger.audit("没开过户");
		}

		// 查用户手机号
		HashMap<Object, Object> phones = ipsUserMapper.queryUserPhone(userId);
		AccountOpenBean accountOpen = new AccountOpenBean();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		String date = sim.format(new Date());
		String merBillNo = GenerateMerBillNoUtil.getBillNoGenerate();
		accountOpen.setMerBillNo(merBillNo);
		accountOpen.setMerDate(date);
		String phone = phones.get("phone").toString();
		accountOpen.setMobileNo(phone);
		// 用户名是手机号+0 一直网上加
		// String userName=phone+数据库的历史记录;
		int shuZi = 1;
		int freQuency = ipsUserMapper.queryUserFreQuency(userId);
		int userQueryShu = freQuency + shuZi;
		String userNames = String.valueOf(userQueryShu);
		//调用补零
		userNames=autoGenericCode(userNames,4);
		StringBuffer buff = new StringBuffer(phone);
		String userName = buff.append(userNames).toString();
		accountOpen.setUserName(userName);
		accountOpen.setIdentNo(identNo);
		// 重数据库获取值 业务类型
		accountOpen.setBizType(user.get("bizType").toString());
		// 重数据库获取用户类型
		accountOpen.setUserType(user.get("userType").toString());
		accountOpen.setRealname(realName);
		// 自己的接口 先写死了

		accountOpen.setWebUrl(localServerIp+"/api/ips/openUser/webCallBack?source="+source);
		accountOpen.setS2SUrl(localServerIp+"/api/ips/openUser/s2sCallBack");
		// 调用ips
		Map openAccountsss = openAccount(accountOpen);

		// ips数据落地
		// 用户信息
		ipsUserMapper.insertUserInfo(userId, userName, identNo, realName);
		// 存用户订单号 类型开户 ，状态为不确定
		String openAccountConfirm = MBOrderInfoDTO.ORDER_STATUS_OPEN_ACCOUNT_NOCONFIRM;
		ipsUserMapper.insertUserBillNo(userId, merBillNo,openAccountConfirm);
		return openAccountsss;

	}

	public Map openAccount(AccountOpenBean accountOpen) {
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("source", "APP");
		hashMap.put("operationType", "user.register");
		hashMap.put("createDate", new Date());
		Map<String, String> ipsRess = new HashMap<String, String>();
		try {
			String js = JSONUtils.obj2json(accountOpen);
			hashMap.put("sendJson", js);
			ipsRess = IpsRequestUtils.getRequestMap(Define.OperationType.REGISTER, js);
			if (ipsRess != null) {
				hashMap.put("status", "success");
			} else {
				hashMap.put("status", "error");
				throw new AppException("参数异常");
			}
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}

		ipsUserMapper.insertJsons(hashMap);
		return ipsRess;
	}
	
	
	
	/**
     * 不够位数的在前面补0，保留num的长度位数字
     * @param code
     * @return
     */
    private static String autoGenericCode(String code, int num) {
        String result = "";
        result = String.format("%0" + num + "d", Integer.parseInt(code) );

        return result;
    }
    
    public static void main(String[] arg){
    	System.out.println(autoGenericCode("2122",4));
    }


}
