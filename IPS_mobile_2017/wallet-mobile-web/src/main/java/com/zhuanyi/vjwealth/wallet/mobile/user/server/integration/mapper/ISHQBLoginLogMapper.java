package com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper;


import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.dto.SHQBLoginLogDTO;

@Mapper
public interface ISHQBLoginLogMapper {
    int insertLoginLog(SHQBLoginLogDTO dto);
}
