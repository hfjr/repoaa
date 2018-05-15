package com.zhuanyi.vjwealth.wallet.mobile.common.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zhuanyi.vjwealth.wallet.mobile.common.dto.CommonVariableDTO;
import com.zhuanyi.vjwealth.wallet.mobile.common.dto.VariableModel;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper.ICommonVariableMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ICommonVariableService;

@Service
public class CommonVariableService implements ICommonVariableService {

	@Autowired
	private ICommonVariableMapper commonVariableMapper;

	public CommonVariableDTO queryCommonVariableMap(Long variableVersion) {
		CommonVariableDTO commonVariableDTO = new CommonVariableDTO();
		commonVariableDTO.setOldvariableVersion(variableVersion);
		// 查询版本是否需要更新

		Long newVariableVersion = commonVariableMapper.queryCommonNewVariableVersion(variableVersion);
		if (newVariableVersion > variableVersion) {
			// 2. 查询需要更新的版本字段
			List<VariableModel> variableList = commonVariableMapper.queryCommonVariableMap(variableVersion);
			Map<String, Object> returnMap = new HashMap<String, Object>();
			for (VariableModel variableModel : variableList) {
				returnMap.put(variableModel.getVariableKey(), JSON.parse(variableModel.getVariableValue()));
			}
			commonVariableDTO.setVariableMap(returnMap);
			commonVariableDTO.setNeedSync(Boolean.TRUE);
		}

		if (newVariableVersion <= variableVersion) {
			commonVariableDTO.setVariableMap(new HashMap<String, Object>());
			commonVariableDTO.setNeedSync(Boolean.FALSE);
		}
		commonVariableDTO.setOldvariableVersion(variableVersion);
		commonVariableDTO.setNewVariableVersion(newVariableVersion);

		return commonVariableDTO;
	}
}
