package com.zhuanyi.vjwealth.wallet.mobile.insurance.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ICommConfigsQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.core.annotation.Remote;
import com.zhuanyi.vjwealth.wallet.mobile.insurance.server.service.IInsureanceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserInsuranceDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserInsuranceService;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class InsureanceService implements IInsureanceService {

    @Remote
    private IMBUserInsuranceService mbImbUserInsuranceService;

    @Autowired
    private ICommConfigsQueryService commConfigsQueryService;

    public boolean hasPICCInsurance(String userId) {
        return mbImbUserInsuranceService.hasPICCInsurance(userId);
    }

    public MBUserInsuranceDTO queryPICCInsuranceInfo(String userId) {
        return mbImbUserInsuranceService.queryPICCInsuranceInfo(userId);
    }

    public void buyPICCInsurance(String userId) {
        mbImbUserInsuranceService.buyPICCInsurance(userId);
    }

    @Override
    public Map queryV2PICCInsuranceInfo(String userId, String userChannelType) {
        userChannelType = userChannelType != null ? userChannelType : "weixin";
        Map paramMap = commConfigsQueryService.queryConfigKeyByGroup("picc_insurance");
        MBUserInsuranceDTO dto = mbImbUserInsuranceService.queryPICCInsuranceInfo(userId);
        Map returnMap = JSON.parseObject(JSON.toJSONString(dto), Map.class);
        returnMap.put("topBanner", userChannelType.equals("weixin") ? paramMap.get("insurance_top_banner_weixin") : paramMap.get("insurance_top_banner_app"));
        returnMap.put("phoneIcon", paramMap.get("insurance_help_phone_icon"));
        returnMap.put("helpDesc", MessageFormat.format(paramMap.get("insurance_help_desc") + "", dto.getHotline()));
        returnMap.put("insuranceScopeIcon", paramMap.get("insurance_scope_icon"));
        returnMap.put("insuranceScopeDesc", paramMap.get("insurance_scope_desc"));
        returnMap.put("insuranceMaskingPic", paramMap.get("insurance_masking_pic"));
        return returnMap;
    }
}
