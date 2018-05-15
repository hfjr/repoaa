package com.zhuanyi.vjwealth.wallet.mobile.index.server.mapper;


import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.WjActivityInfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IWjActivityInfoMapper {

    List<WjActivityInfoDTO> queryActivityList(@Param("page") Integer page);

    String queryNewestProductFinace();

    List<WjActivityInfoDTO> queryAppHomeActivityList(@Param("version") String version);

}
