package com.zhuanyi.vjwealth.wallet.mobile.common.server.service;

import com.zhuanyi.vjwealth.wallet.mobile.common.dto.CommonVariableDTO;

/**
 * 公共变量
 * 
 * @author SpeedJ
 *
 */
public interface ICommonVariableService {
	CommonVariableDTO queryCommonVariableMap(Long variableVersion);
}
