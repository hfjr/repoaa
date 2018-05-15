package com.zhuanyi.vjwealth.wallet.mobile.common.server.service;

import java.util.List;

import com.zhuanyi.vjwealth.wallet.mobile.common.dto.AdvertisementBannerDTO;
import com.zhuanyi.vjwealth.wallet.mobile.common.dto.AdvertisementDTO;

public interface IAdvertisementService {
	
	/**
	 * @title 查询所有的广告板子信息
	 * @return app端,将获得的url与本地缓存中的url进行比较,如果url有变化,则通过url从后台取新的 图片;如果没有变化,则从缓存中获取
	 * 
	 */
	List<AdvertisementDTO> queryAdvertisementInfo();
	
	/**
	 * @title 查询小赢理财广告板子
	 * @return 广告板子列表
	 */
	List<AdvertisementBannerDTO> queryAdvertisementBannerInfo();

	/**
	 * @title 查询小赢理财广告板子
	 * @return 广告板子列表
	 */
	List<AdvertisementBannerDTO> queryV2AdvertisementBannerInfo();

	/**
	 * 服务模块的广告板子
	 * @return 广告板子列表
	 */
	List<AdvertisementBannerDTO> queryServiceAdvertisementBanner();

	/**
	 * 查询商户钱包首页广告位
	 * @return
     */
	List<AdvertisementBannerDTO> queryHomeAdvBannerInfoForSHQB(String appType);


}
