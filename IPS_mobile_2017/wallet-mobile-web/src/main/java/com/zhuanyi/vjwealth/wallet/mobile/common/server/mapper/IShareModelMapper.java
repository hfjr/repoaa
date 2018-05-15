package com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.ConfirmShareModelDTO;

@Mapper
public interface IShareModelMapper {
	
	ConfirmShareModelDTO queryConfirmShareModel(@Param("paramKey")String paramKey, @Param("ymdInt")Integer ymdInt);

	List<String> queryNextTwoWorkdaysByBuyday(@Param("ymdInt")Integer ymdInt);

}
