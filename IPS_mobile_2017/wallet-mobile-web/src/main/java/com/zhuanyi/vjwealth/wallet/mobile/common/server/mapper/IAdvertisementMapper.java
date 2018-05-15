package com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper;

import java.util.List;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.dto.AdvertisementBannerDTO;
import com.zhuanyi.vjwealth.wallet.mobile.common.dto.AdvertisementDTO;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IAdvertisementMapper {
	
	/**
	 * 查询所有的广告板子信息
	 * @return
	 */
	List<AdvertisementDTO> queryAdvertisementInfo();

	/**
	 * @title 小赢理财广告板子
	 * @return 广告板子列表
	 */
	List<AdvertisementBannerDTO> queryAdvertisementBannerInfo();
	/**
	 * @title 小赢理财广告板子
	 * @return 广告板子列表
	 */
	List<AdvertisementBannerDTO> queryV2AdvertisementBannerInfo();

	/**
	 * 服务模块广告板子
	 * @return
	 */
	List<AdvertisementBannerDTO> queryServiceAdvertisementBanner();

	List<AdvertisementBannerDTO> queryHomeAdvBannerInfoForSHQB(@Param("appType") String appType);

	List<AdvertisementBannerDTO> queryV3AdvertisementBannerInfo();
}
