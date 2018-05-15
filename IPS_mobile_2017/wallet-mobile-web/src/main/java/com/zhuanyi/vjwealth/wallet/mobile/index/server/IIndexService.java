package com.zhuanyi.vjwealth.wallet.mobile.index.server;

import java.util.List;
import java.util.Map;

import com.zhuanyi.vjwealth.wallet.mobile.common.dto.AdvertisementBannerDTO;

public interface IIndexService {
    /**
     * 1.首页初始化界面
     *
     * @param userId
     * @return
     * @since 3.6
     */
    Map<String, Object> queryIndex(String userId, String uuid);

    /**
     * 4.1最新活动
     *
     * @param userId
     * @return
     * @since 3.6
     */
    Object queryActivityList(String userId, String uuid, String page);

    /**
     * 4.2动态列表
     *
     * @param userId
     * @return
     * @since 3.6
     */
    Map<String,Object> queryDynamicList(String userId, String uuid, String page);

    /**
     * APP圣诞首页初始化接口
     *
     * @return
     */
    Map<String, Object> querySdIndex(String channelType);

    Map<String, Object> queryHumanService(String userId);

    /*
    个人中心-我的资产
     */
    Map queryMyAssets(String userId);

    Map queryProductListV37(String userId, String uuid, String page, String userChannelType);

    /**
     * 微信首页新接口
     *
     * @return
     */
    Map<String, Object> queryWeiXinHome();

    List<AdvertisementBannerDTO> queryV2AdvertisementBannerInfo();

    Map<String, Object> queryV38Index(String channelType);
}
