package com.zhuanyi.vjwealth.wallet.mobile.product.server.mapper;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IProductFinaceWXMapper {
	

	/**
	 * 查询产品列表
	 * @param page
	 * @return 产品列表
	 */
	List<ProductListQueryDTO> queryProductList(@Param("page") Integer page);


}
