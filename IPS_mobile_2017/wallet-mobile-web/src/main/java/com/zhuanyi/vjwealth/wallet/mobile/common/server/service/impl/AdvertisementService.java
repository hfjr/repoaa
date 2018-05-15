package com.zhuanyi.vjwealth.wallet.mobile.common.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhuanyi.vjwealth.wallet.mobile.common.dto.AdvertisementBannerDTO;
import com.zhuanyi.vjwealth.wallet.mobile.common.dto.AdvertisementDTO;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper.IAdvertisementMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IAdvertisementService;

@Service
public class AdvertisementService implements IAdvertisementService {
	@Autowired
	private IAdvertisementMapper advertisementMapper;

	@Override
	public List<AdvertisementDTO> queryAdvertisementInfo() {
		List<AdvertisementDTO> advertisementList = advertisementMapper.queryAdvertisementInfo();
		if(CollectionUtils.isNotEmpty(advertisementList)){
			for(AdvertisementDTO ad:advertisementList){
				String str = ad.getMarkStr();
				if(StringUtils.isNotBlank(str)){
					ad.setMark(JSONObject.parseObject(str,Map.class));
					ad.setMarkStr("");
				}else{
					ad.setMark(new HashMap<String, String>());
					ad.setMarkStr("");
				}
			}
		}
		return advertisementList;
	}

	@Override
	public List<AdvertisementBannerDTO> queryAdvertisementBannerInfo() {
		return advertisementMapper.queryAdvertisementBannerInfo();
	}

	@Override
	public List<AdvertisementBannerDTO> queryV2AdvertisementBannerInfo() {
		return advertisementMapper.queryV2AdvertisementBannerInfo();
	}

	@Override
	public List<AdvertisementBannerDTO> queryServiceAdvertisementBanner() {
		return advertisementMapper.queryServiceAdvertisementBanner();
	}

	@Override
    public List<AdvertisementBannerDTO> queryHomeAdvBannerInfoForSHQB(String appType) {
        //默认是小微版
        appType = StringUtils.isEmpty(appType) ? "MICRO" : appType;
        return advertisementMapper.queryHomeAdvBannerInfoForSHQB(appType);
	}
}
