package com.zhuanyi.vjwealth.wallet.mobile.common.server.service;

import java.util.Map;

public interface IResourceVersionService {

    /**
     * 检查当前app的版本,是否需要(强制)更新
     *
     * @param type       -- IOS / ANDROID  //注意大写
     * @param appVersion -- 版本号
     * @return 返回更新类型, 以及更新内容
     * actionType:Action_CheckResourceVersion_(00:不更新,01:更新,02:强制更新 )
     * updateContent:{
     * "content": "1.修复了若干bug;2.做了若干优化;",
     * "url": "http://120.55.75.223:7020/wallet-mobile/resources/android/wallet_app_v1.0.apk"
     * }
     */
    Map<String, Object> checkAppVersion(String type, String appVersion);

    /**
     * 商户钱包版本更新
     * @param deviceType 设备类型(S^IOS:IOS,ANDROID:ANDROID$)
     * @param appVersion 当前版本号
     * @param appType APP类型 小微版 MICRO  云支付版 CLOUD
     * @return
     */
    Map<String, Object> checkAppVersionForSHQB(String deviceType, String appVersion, String appType);

}
