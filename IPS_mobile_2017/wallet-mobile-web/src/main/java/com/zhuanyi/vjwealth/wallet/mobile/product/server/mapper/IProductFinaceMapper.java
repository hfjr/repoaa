package com.zhuanyi.vjwealth.wallet.mobile.product.server.mapper;

import java.util.List;
import java.util.Map;

import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.*;
import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

@Mapper
public interface IProductFinaceMapper {
	
	/**
	 * 查询产品列表
	 * @param page 
	 * @return 产品列表
	 */
	List<ProductListQueryOldDTO> queryProductListForOld(@Param("page")Integer page);

	/**
	 * 查询产品列表
	 * @param page
	 * @return 产品列表
	 */
	List<ProductListQueryDTO> queryProductList(@Param("page")Integer page);

	/**
	 * 查询产品列表
	 * @param page
	 * @return 产品列表
	 */
	List<ProductListQueryV4DTO> queryProductListV4(@Param("page")Integer page);
	
	/**
	 * 查询产品列表
	 * @param userId
	 * @param page
	 * @param orderBy
	 * @return 产品列表
	 */
	List<ProductListQueryV6DTO> queryProductListV6(@Param("page")Integer page,@Param("productType")String productType,@Param("orderBy")String orderBy);

	/**
	 * 查询产品列表
	 * @param page
	 * @param orderBy
	 * @return 产品列表
	 */
	List<ProductListQueryV6DTO> queryHomeNewPersonProductList(@Param("orderBy")String orderBy);

	/**
	 * 查询产品列表
	 * @param page
	 * @param orderBy
	 * @return 产品列表
	 */
	List<ProductListQueryV6DTO> queryCommonProductList(@Param("orderBy")String orderBy);
	
	/**
	 * 查询用户投资记录数
	 * @param userId
	 * @return 投资记录数
	 */
	Integer countMyInvestment(@Param("userId")String userId);
	/**
	 * 查询产品列表
	 * @param page
	 * @return 产品列表
	 */
	List<ProductListQueryV2DTO> queryProductListV2(@Param("page")Integer page);

	/**
	 * 
	 * @param productId
	 * @return 产品详情
	 */
	ProductDetailQueryDTO queryProductDetail(@Param("userId")String userId,@Param("productId")String productId);

	/**
	 *
	 * @param productId
	 * @return 产品详情
	 */
	ProductDetailQueryDTO queryProductDetailV2(@Param("productId")String productId);
	
	/**
	 *
	 * @param productId
	 * @return 产品详情
	 */
	ProductDetailQueryV6DTO queryProductDetailV6(@Param("userId")String userId,@Param("productId")String productId);
	
	ProductDetailQueryV7DTO queryProductDetailV7(@Param("userId")String userId,@Param("productId")String productId);
	
	String queryProductBidAnnouncementDetail(@Param("productId")String productId);

	/**
	 * 
	 * @param productId
	 * @return 产品的投资记录
	 */
	List<ProductInvestmentListQueryDTO> queryProductInvestmentRecordList(@Param("productId")String productId);

	/**
	 *
	 * @param productId
	 * @return 产品的投资记录
	 */
	List<ProductInvestmentListQueryDTO> queryProductInvestmentRecordListV2(@Param("productId")String productId,@Param("page")Integer page);

	/**
	 * 查询产品详情（下订单初始化时调用）
	 * @param productId
	 * @return
	 */
	ProductOrderInitQueryDTO queryProductInfoForOrderInit(@Param("productId")String productId);

	/**
	 * 查询用户不可以购买VIP产品
	 * @param userId
	 * @param productId
     * @return true 没有权限 false 不处理
     */
	boolean queryUserBuyVIPProductPermission(@Param("userId") String userId, @Param("productId") String productId);

	//查询产品信息
	Map<String,String> queryProductInfoByOrderNo(@Param("orderNo")String orderNo);
	
}
