package com.zhuanyi.vjwealth.wallet.mobile.product.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.fab.web.controller.annotation.AppController;

import com.rqb.ips.depository.platform.beans.IPSResponse;
import com.rqb.ips.depository.platform.beans.IpsUnfreeze;

import com.rqb.ips.depository.platform.beans.wj_orderIPS;
import com.rqb.ips.depository.platform.faced.UnfreezeServices;

/**
 * @刘发涛 回调解冻
 */
@RequestMapping("/unfreezeController")
@Controller
public class unfreezeController {

	/*
	 * @Remote
	 */
	@Autowired
	private UnfreezeServices ipsUnfreezeServices;

	@RequestMapping("/test")
	@AppController
	public Object ceshi(HttpServletRequest req, HttpServletResponse rep) throws Exception {
		String freezeId = req.getParameter("freezeId");
		String ipsAcctNo = req.getParameter("ipsAcctNo");
		String trdAmt = req.getParameter("trdAmt");
		IpsUnfreeze ipsUnfreeze = new IpsUnfreeze();
		ipsUnfreeze.setMerDate("2015-01-02");
		ipsUnfreeze.setProjectNo("201712032");
		ipsUnfreeze.setBizType("4");
		
		ipsUnfreeze.setFreezeId(freezeId);
	
		ipsUnfreeze.setIpsAcctNo(ipsAcctNo);
		ipsUnfreeze.setMerFee("2");
		
		ipsUnfreeze.setTrdAmt(trdAmt);
		IPSResponse unfreeze = ipsUnfreezeServices.Unfreeze(ipsUnfreeze);
		
		String data =(String) unfreeze.getData();
		String msg = unfreeze.getMsg();
		String code = unfreeze.getCode();
		
		
		
		
		HashMap<String,String> hashMap = new HashMap<>();
		hashMap.put("data",data);
		hashMap.put("msg",msg);
		hashMap.put("code",code);
		
		return hashMap;

	}

	@RequestMapping("/unfreeze")
	@AppController
	public IPSResponse unfreeze(HttpServletRequest req, HttpServletResponse rep) {
		String resultCode = req.getParameter("resultCode");
		String resultMsg = req.getParameter("resultMsg");
		String merchantID = req.getParameter("merchantID");
		String sign = req.getParameter("sign");
		String response = req.getParameter("response");

		String port = "trade.unFreeze";
		wj_orderIPS wj_orderIPS = new wj_orderIPS();

		wj_orderIPS.setData(response);
		wj_orderIPS.setCode(resultCode);
		wj_orderIPS.setReturnmsg(resultMsg);
		wj_orderIPS.setTrade_account_id(merchantID);
		wj_orderIPS.setSign(sign);

		IPSResponse success = ipsUnfreezeServices.success(port, wj_orderIPS);

		/**
		 * 
		 * 
		 * */

		return success;
	}

}
