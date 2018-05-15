package com.zhuanyi.vjwealth.wallet.mobile.product.server.impl;

import com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ICommConfigsQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.BizCommonConstant;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IProductContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductContractService implements IProductContractService {
	@Autowired
	private ICommConfigsQueryService commConfigsQueryService;

	@Override
	public Map<String, Object> queryWarningAndProtectionContracts(String userId) {
		Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CONFIG_GROUP_WALLET_MOBILE);
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("title","风险提示和保障");
		List<KeyValueDTO> contracts=new ArrayList<KeyValueDTO>();
		contracts.add(new KeyValueDTO("资产收益权转让协议",commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+"/api/v3.6/app/mySalaryPlan/agreement-transfer"));
		contracts.add(new KeyValueDTO("收益权产品认购协议",commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+"/api/v3.6/app/mySalaryPlan/user-agreement"));
		contracts.add(new KeyValueDTO("融桥宝个人客户平台服务协议",commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+"/api/v3.6/app/mySalaryPlan/client-platform"));
		paramMap.put("records", contracts);
		return paramMap;
	}
}
