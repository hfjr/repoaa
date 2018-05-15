package com.rqb.ips.depository.platform.service;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hf.comm.utils.HttpClientUtils;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.beans.AccountOpenBean;
import com.rqb.ips.depository.platform.beans.IPSResponse;
import com.rqb.ips.depository.platform.faced.IAccountService;
import com.rqb.ips.depository.platform.mapper.IpsOpenUserMapper;
import com.rqb.ips.depository.platform.menu.Define;

/**
 * 
 * <p>
 * Title: AccountOpenBean.java
 * </p>
 * <p>
 * Description：开户
 * </p>
 * 
 * @author sunxiaolei
 * @date 2017年12月5日
 * @version 1.0
 */
@Service
public class ExcuteAccountService implements IAccountService {

	@Autowired
	private IpsOpenUserMapper ipsOpen;

	@Override
	public IPSResponse openAccount(AccountOpenBean accountOpen) {
		IPSResponse response = new IPSResponse();
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("source", "APP");
		hashMap.put("operationType", "user.register");
		hashMap.put("createDate", new Date());

		try {
			String js = JSONUtils.obj2json(accountOpen);
			hashMap.put("sendJson", js);
			String ipsRes = HttpClientUtils.ipsPostMethod(Define.OperationType.REGISTER, js);

			hashMap.put("returnJson", ipsRes);
			//System.out.println(ipsRes);
			if (ipsRes != null) {
				response.setCode(IPSResponse.ErrCode.SUCCESS);
				response.setData(ipsRes);
				response.setMsg("成功");
				hashMap.put("status", "success");
			} else {
				response.setCode(IPSResponse.ErrCode.UNKNOW);
				response.setMsg("连接未知错误");
				hashMap.put("status", "error");
			}
			//System.out.println(ipsRes);
		} catch (Exception e) {
			// 返回response的同时记录失败操作
			// TODO 转mainbiz时添加数据落地操作
			response.setCode(IPSResponse.ErrCode.TIME_OUT);
			response.setMsg("连接超时");
			hashMap.put("status", "time_out");
		}
		
		ipsOpen.insertJsons(hashMap);
		//System.out.println(response);
		return response;
	}

	public static void main(String[] args) {
		/*
		 * AccountOpenBean a = new AccountOpenBean();
		 * a.setMerBillNo("105d6123456"); a.setMerDate("2017-12-01");
		 * a.setUserType("1"); a.setUserName("sunxiaole");
		 * a.setMobileNo("18017639091"); a.setIdentNo("342422199812252133");
		 * a.setBizType("1"); a.setRealname("孙晓磊"); a.setEnterName("");
		 * a.setOrgCode(""); a.setIsAssureCom("");
		 * a.setWebUrl("http://ips.com/webtest.htm");
		 * a.setS2SUrl("http://ips.com/s2stest.htm");
		 */
		// ExcuteAccountService ex=new ExcuteAccountService();
		// ex.openAccount(a);
	}
}
