package com.zhuanyi.vjwealth.wallet.mobile.product.server;

import java.util.Map;

public interface IProductQueryWXService {


	/**
	 * @title 产品列表
	 * @param page
	 * @return map
	 *         key      |    value
	 *         records  |   产品列表
	 * @since 3.0
	 */
	Map<String,Object>  queryProductList(String page);

}
