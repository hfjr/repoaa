package com.zhuanyi.vjwealth.wallet.mobile.product.server;

import java.util.Map;

import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.ProductDetailQueryDTO;

public interface IProductQueryService {

	/**
	 * @title 产品列表
	 * @param page
	 * @return map
	 *         key      |    value
	 *         records  |   产品列表
	 * @since 3.0
	 */
	Map<String,Object>  queryProductListForOld(String page);

	/**
	 * @title 产品列表
	 * @param page
	 * @return map
	 *         key      |    value
	 *         records  |   产品列表
	 * @since 3.0
	 */
	Map<String,Object>  queryProductList(String page);



	/**
	 * @title 产品列表
	 * @param page
	 * @return map
	 *         key      |    value
	 *         records  |   产品列表
	 * @since 3.4
	 */
	Map<String,Object>  queryProductListV4(String page);
	
	/**
	 * @title 产品列表
	 * @param userId
	 * @param uuid
	 * @param page
	 * @param orderBy
	 * @return map
	 *         key      |    value
	 *         records  |   产品列表
	 * @since 3.6
	 */
	Map<String,Object>  queryProductListV6(String userId,String uuid,String productType, String page,String orderBy);



	/**
	 * @title 首页新手产品列表
	 * @param userId
	 * @param uuid
	 * @param page
	 * @param orderBy
	 * @return map
	 *         key      |    value
	 *         records  |   产品列表
	 * @since 3.6
	 */
	Map<String,Object>  queryHomeNewPersonProductList(String userId,String uuid,String orderBy);
	/**
	 * @title 首页普通产品列表
	 * @param userId
	 * @param uuid
	 * @param page
	 * @param orderBy
	 * @return map
	 *         key      |    value
	 *         records  |   产品列表
	 * @since 3.6
	 */
	Map<String,Object>  queryCommonProductList(String userId,String uuid,String orderBy);

	/**
	 * @title 产品列表
	 * @param page
	 * @return map
	 *         key      |    value
	 *         records  |   产品列表
	 * @since 3.3
	 */
	Map<String,Object>  queryProductListV2(String page);

	/**
	 * @title 产品详情
	 * @param productId
	 * @return
	 * @since 3.0
	 */
	ProductDetailQueryDTO queryProductDetail(String userId,String productId);


	/**
	 * @title 产品详情
	 * @param productId
	 * @return
	 * @since 3.1
	 */
	ProductDetailQueryDTO queryProductDetailV2(String productId);
	
	/**
	 * @title 产品详情
	 * @param userId
	 * @param uuid
	 * @param productId
	 * @return
	 * @since 3.6
	 */
	Map<String, Object> queryProductDetailV6(String userId,String uuid,String productId);
	
	/**
	 * @title 产品详情
	 * @param userId
	 * @param uuid
	 * @param productId
	 * @return
	 * @since 3.7
	 */
	Map<String, Object> queryProductDetailV7(String userId,String uuid,String productId);
	
	/**
	 * @title 产品详情介绍
	 * @param productId
	 * @return
	 * @since 3.7
	 */
	String queryProductBidAnnouncementDetail(String productId);

	/**
	 * @title 根据产品编号查询对该产品的所有投资人信息记录
	 * @return map
	 *         key      |    value <br/>
	 *         records  |   投资记录
	 * @since 3.0
	 */
	Map<String,Object>  queryProductInvestmentRecordList(String productId);


	/**
	 * @title 根据产品编号查询对该产品的所有投资人信息记录
	 * @param page
	 * @return map
	 *         key      |    value <br/>
	 *         records  |   投资记录
	 * @since 3.3
	 */
	Map<String,Object>  queryProductInvestmentRecordListV2(String productId, String page);

}
