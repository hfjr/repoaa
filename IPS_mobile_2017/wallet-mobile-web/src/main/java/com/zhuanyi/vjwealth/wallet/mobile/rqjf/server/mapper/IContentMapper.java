package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface IContentMapper {

    /**
     * 查询内容列表
     *
     * @param channelIds
     * @param page
     * @param contentSequence
     * @return 内容列表
     */
    List<Map<String,Object>> queryList(@Param("page") Integer page, @Param("channelIds") String channelIds, @Param("contentSequence") String contentSequence);

}
