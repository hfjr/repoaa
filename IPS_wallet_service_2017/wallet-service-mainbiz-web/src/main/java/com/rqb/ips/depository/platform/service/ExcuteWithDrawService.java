package com.rqb.ips.depository.platform.service;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hf.comm.utils.HttpClientUtils;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.beans.IPSResponse;
import com.rqb.ips.depository.platform.beans.WithDraw;
import com.rqb.ips.depository.platform.faced.IWithDrawService;
import com.rqb.ips.depository.platform.mapper.ExcuteIpsPlatLogMapper;
import com.rqb.ips.depository.platform.menu.Define;

/**
 * 
 * <p>
 * Title: ExcuteWithDrawService.java
 * </p>
 * <p>
 * Description: 提现
 * </p>
 * 
 * @author sunxiaolei
 * @date 2017年12月5日
 * @version 1.0
 */


@Service
public class ExcuteWithDrawService implements IWithDrawService {

	@Autowired
	private ExcuteIpsPlatLogMapper excuteIpsPlatLogMapper;
	
	
	@Override
	public IPSResponse withDraw(String orderId,WithDraw withDraw,String source) {
		// TODO Auto-generated method stub
		IPSResponse response = new IPSResponse();
		HashMap<String, String> hashMap = new HashMap<String, String>();
		//hashMap.put("source", "APP");
		hashMap.put("operationType", "trade.withdraw");
		hashMap.put("orderId", orderId);

		try {

			/*String sendJson = JSONUtils.obj2json(withDraw);
			hashMap.put("sendJson", sendJson);
			String ipsRes = HttpClientUtils.ipsPostMethod(Define.OperationType.WITHDRAW, sendJson);
			hashMap.put("returnJson", ipsRes);
			if (ipsRes != null) {
				response.setCode(IPSResponse.ErrCode.SUCCESS);
				response.setData(ipsRes);
				response.setMsg("成功");
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
			// TODO 转mainbiz时添加数据落地操作
			/*response.setCode(IPSResponse.ErrCode.TIME_OUT);
			response.setMsg("连接超时");
			hashMap.put("status", "time_out");*/

		}

		excuteIpsPlatLogMapper.insertIpsPlatformLogInfor(hashMap);

		//System.out.println(response);
		return response;
	}

	public static void main(String[] args) {
		WithDraw withDraw = new WithDraw();
		withDraw.setMerBillNo("2254020052");
		withDraw.setMerDate("2015-01-02");
		withDraw.setUserType("1");
		withDraw.setMerFee("12");
		withDraw.setTrdAmt("18");
		withDraw.setIpsFeeType("1");
		withDraw.setMerFeeType("1");
		withDraw.setIpsAcctNo("100000184497");
		withDraw.setWebUrl("http://180.168.26.114:20010/p2p-deposit/test/p2pweb.html");
		withDraw.setS2SUrl("http://180.168.26.114:20010/p2p-deposit/test/send.do");
		ExcuteWithDrawService a = new ExcuteWithDrawService();
		//a.withDraw(withDraw);
	}
}
