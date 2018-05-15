package com.rqb.ips.depository.platform.service;

import com.rqb.ips.depository.platform.beans.IPSResponse;
import com.rqb.ips.depository.platform.beans.IpsPlatformLogin;
import com.rqb.ips.depository.platform.faced.IpsPlatformLoginService;
import com.rqb.ips.depository.platform.mapper.ExcuteIpsPlatLogMapper;
import com.rqb.ips.depository.platform.menu.Define;

import java.util.Date;
import java.util.HashMap;

import org.bouncycastle.crypto.tls.HashAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hf.comm.utils.HttpClientUtils;
import com.hf.comm.utils.JSONUtils;

/**
 * @@author 朱涛 2017年12月1日下午5:01:28 TODO平台下登录
 */
@Service
public class ExcuteIpsPlatformLoginService implements IpsPlatformLoginService {

	
	@Autowired
	private ExcuteIpsPlatLogMapper excuteIpsPlatLogMapper;

	@Override
	public IPSResponse platformLogin(IpsPlatformLogin ipsPlatformLogin) {
		IPSResponse response = new IPSResponse();

		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("source", "APP");
		hashMap.put("operationType", "user.login");
		//hashMap.put("createDate", new Date());

		try {
			
			/*String sendJson = JSONUtils.obj2json(ipsPlatformLogin);
			hashMap.put("sendJson", sendJson);
			String ipsRes = HttpClientUtils.ipsPostMethods(sendJson);
			hashMap.put("returnJson", ipsRes);
			if (ipsRes != null) {
				response.setCode(IPSResponse.ErrCode.SUCCESS);
				response.setData(ipsRes);
				response.setMsg("成功");
				System.out.println(ipsRes);
				hashMap.put("status", "success");
			} else {
				response.setCode(IPSResponse.ErrCode.UNKNOW);
				response.setMsg("失败");
				hashMap.put("status", "error");
			}

			System.out.println(ipsRes);*/
			// TODO 转mainbiz时添加数据落地操作

		} catch (Exception e) {
			// 返回response的同时记录失败操作
		/*	// TODO 转mainbiz时添加数据落地操作
			response.setCode(IPSResponse.ErrCode.TIME_OUT);
			response.setMsg("连接超时");
			hashMap.put("status", "time_out");
*/
		}
		excuteIpsPlatLogMapper.insertIpsPlatformLogInfor(hashMap);

		//System.out.println(response);
		return response;
	}

	/**
	 * @author 朱涛 2017年12月1日下午5:01:59 TODO(这是绑定实现类)
	 * @param args
	 */
	public static void main(String[] args) {
		ExcuteIpsPlatformLoginService openAccount = new ExcuteIpsPlatformLoginService();
		IpsPlatformLogin ipsPlatformLogin = new IpsPlatformLogin();
		ipsPlatformLogin.setMerchantId("1184980025");
		ipsPlatformLogin.setUserName("13256859710");
		// ipsPlatformLogin.setWebUrl("http://ips.com/webtest.htm");
		// ipsPlatformLogin.setS2SUrl("http://ips.com/s2stest.htm");
		IPSResponse openAccount2 = openAccount.platformLogin(ipsPlatformLogin);
		/*
		 * System.out.println("提交信息:");
		 * System.out.println("代码:"+openAccount2.getCode());
		 * System.out.println("错误信息:"+openAccount2.getMsg());
		 * System.out.println("数据:"+openAccount2.getData());
		 */
	}
}
