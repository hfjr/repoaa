package com.zhuanyi.vjwealth.wallet.mobile.product.server.impl;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IProductQueryWXService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.ProductListQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.mapper.IProductFinaceWXMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductQueryWXService implements IProductQueryWXService {

	@Autowired
	private IProductFinaceWXMapper productFinaceMapper;

	@Override
	public Map<String,Object> queryProductList(String page) {
		if(StringUtils.isBlank(page)){
			throw new AppException("查询产品列表，页码参数不能为空");
		}
		if(!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1 || Integer.parseInt(page)%1>0){
			throw new AppException("查询产品列表，页码数值不合法，必须为大于0的整数");
		}
		Integer currentPage = (Integer.parseInt(page)-1)*10;
		
		List<ProductListQueryDTO> list = productFinaceMapper.queryProductList(currentPage);
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("records", list);
		returnMap.put("isMore", false);
		if(list!=null && list.size()>=10){
			returnMap.put("isMore", true);
		}
		
		return returnMap;
	}
}
