package com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.dto.UserLogsDTO;

@Mapper
public interface IUserLogsMapper {

	public void saveUserOperateLogs(UserLogsDTO userLogsDTO);
	
	public String queryLogsSwitch();
}
