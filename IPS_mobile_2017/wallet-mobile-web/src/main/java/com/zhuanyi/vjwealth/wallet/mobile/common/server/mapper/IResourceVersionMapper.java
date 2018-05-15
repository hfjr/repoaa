package com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.dto.AppVersionDTO;

@Mapper
public interface IResourceVersionMapper {

    /**
     * 查询当前版本
     *
     * @param appVersion
     * @param type
     * @return isUpdate        --是否更新
     * isForceUpdate	--是否强制更新
     */
    AppVersionDTO queryCurrentAppVersion(@Param("appVersion") String appVersion, @Param("type") String type);


    /**
     * 查询最新的版本
     *
     * @param type
     * @return content --内容
     * url	--更新地址
     */
    AppVersionDTO queryNewestAppVersion(@Param("type") String type);

    /**
     * 查询当前版本
     *
     * @param appVersion
     * @param type
     * @return
     */
    AppVersionDTO queryCurrentAppVersionForSHQB(@Param("appVersion") String appVersion, @Param("type") String type, @Param("appType") String appType);


    /**
     * 查询最新的版本
     *
     * @param type
     */
    AppVersionDTO queryNewestAppVersionForSHQB(@Param("type") String type, @Param("appType") String appType);

}
