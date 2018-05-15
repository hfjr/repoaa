package com.rqb.ips.depository.platform.controller;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.crypto.tls.HashAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.web.controller.annotation.AppController;
import com.hf.comm.utils.GenerateMerBillNoUtil;
import com.rqb.ips.depository.platform.beans.IpsUserInfoDTO;
import com.rqb.ips.depository.platform.faced.IPSQueryUserInfoService;
import com.rqb.ips.depository.platform.faced.IpsBidCardService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserAccountService;

@Controller
public class IpsBidCardController {

	@Autowired
	private IPSQueryUserInfoService iPSQueryUserInfoService;

	@Autowired
	private IpsBidCardService ipsBidCardService;


	
	@RequestMapping("ips/isBIdCard")
	@AppController
	public Object ipsBidCard(String userId) {

		HashMap<String, String> returnMap = new HashMap<String, String>();

		IpsUserInfoDTO ipsUserInfoDTO = new IpsUserInfoDTO();

		// 根据用户查询存管号码
		String ipsAcctNo = ipsBidCardService.queryIsBidCard(userId);
		if(ipsAcctNo==null){
			throw new AppException("用户未开户");
		}

		if(ipsAcctNo==null){
			throw new AppException("用户未开户");
		}
		
		// 用户存管账号
		ipsUserInfoDTO.setUseripsid(ipsAcctNo);
		ipsUserInfoDTO.setQuerytype("01");
		ipsUserInfoDTO.setMerBillNo(GenerateMerBillNoUtil.getBillNoGenerate());

		IpsUserInfoDTO ipsUserInfo = iPSQueryUserInfoService.IpsQueryUserInfo(ipsUserInfoDTO);

		if (ipsUserInfo == null) {
			throw new AppException("用户不存在");
		}

		String bankName = ipsUserInfo.getBankName();

		String bankCard = ipsUserInfo.getBankCard();

		if (StringUtils.isBlank(bankName) || StringUtils.isBlank(bankCard)) {
			returnMap.put("date", "10");
			returnMap.put("msg", "该用户未绑卡");
		} else {
			returnMap.put("date", "00");
			returnMap.put("msg", "该用户已绑卡");
		}

		return returnMap;
	}

}
