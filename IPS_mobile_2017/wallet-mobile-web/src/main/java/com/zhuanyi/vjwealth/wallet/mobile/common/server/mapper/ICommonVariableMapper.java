package com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.dto.VariableModel;

@Mapper
public interface ICommonVariableMapper {
	List<VariableModel> queryCommonVariableMap(@Param("variableVersion") Long variableVersion);

	Long queryCommonNewVariableVersion(@Param("variableVersion") Long variableVersion);
}
