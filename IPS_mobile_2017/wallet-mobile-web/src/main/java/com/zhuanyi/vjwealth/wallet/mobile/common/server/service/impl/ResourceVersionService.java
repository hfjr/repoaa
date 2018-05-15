package com.zhuanyi.vjwealth.wallet.mobile.common.server.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.zhuanyi.vjwealth.wallet.mobile.user.util.ParamValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.mobile.common.dto.AppVersionDTO;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper.IResourceVersionMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IResourceVersionService;

@Service
public class ResourceVersionService implements IResourceVersionService {
    @Autowired
    private IResourceVersionMapper resourceVersionMapper;

    @Override
    public Map<String, Object> checkAppVersion(String type, String appVersion) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        //当前版本
        AppVersionDTO currentAppVersionDTO = resourceVersionMapper.queryCurrentAppVersion(appVersion, type);
        //最新版本
        AppVersionDTO newestAppVersionDTO = resourceVersionMapper.queryNewestAppVersion(type);
        //当前版本不存在,强制更新
        if (currentAppVersionDTO == null) {
            returnMap.put("actionType", AppVersionDTO.ACTION_CHECKRESOURCEVERSION_02);
            returnMap.put("updateContent", newestAppVersionDTO.getUpdateContent());
            returnMap.put("version", newestAppVersionDTO.getVersion());
            return returnMap;
        }
        returnMap.put("actionType", currentAppVersionDTO.getActionType(newestAppVersionDTO.getUrl(), newestAppVersionDTO.getContent()));
        returnMap.put("updateContent", currentAppVersionDTO.getUpdateContent());
        returnMap.put("version", newestAppVersionDTO.getVersion());
        return returnMap;
    }

    @Override
    public Map<String, Object> checkAppVersionForSHQB(String deviceType, String appVersion, String appType) {
        ParamValidUtil.validatorDeviceType(deviceType);
        ParamValidUtil.validatorAppType(appType);
        ParamValidUtil.validatorAppVersion(appVersion);

        Map<String, Object> returnMap = new HashMap<String, Object>();
        //当前版本
        AppVersionDTO currentAppVersionDTO = resourceVersionMapper.queryCurrentAppVersionForSHQB(appVersion, deviceType, appType);
        //最新版本
        AppVersionDTO newestAppVersionDTO = resourceVersionMapper.queryNewestAppVersionForSHQB(deviceType, appType);
        //当前版本不存在,强制更新
        if (currentAppVersionDTO == null) {
            returnMap.put("actionType", AppVersionDTO.ACTION_CHECKRESOURCEVERSION_02);
            returnMap.put("updateContent", newestAppVersionDTO.getUpdateContent());
            return returnMap;
        }
        returnMap.put("actionType", currentAppVersionDTO.getActionType(newestAppVersionDTO.getUrl(), newestAppVersionDTO.getContent()));
        returnMap.put("updateContent", currentAppVersionDTO.getUpdateContent());
        return returnMap;
    }
}
