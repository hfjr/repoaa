package com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.loan.common.dto.CommConfigsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ICommConfigsMapper {
    List<CommConfigsDTO> queryCommConfigsByGroupAndKey(@Param("query") CommConfigsDTO commParamDTO);
}
