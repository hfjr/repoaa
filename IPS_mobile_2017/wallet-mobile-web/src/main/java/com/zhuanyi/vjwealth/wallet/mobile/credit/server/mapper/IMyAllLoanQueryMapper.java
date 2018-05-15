package com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper;

import com.fab.server.annotation.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by wangzhangfei on 16/7/12.
 */
@Mapper
public interface IMyAllLoanQueryMapper {

	/**
	 * 查询产品名称
	 * @param orderNo
	 * @return
	 */
	String queryProductNameByOrderNo(@Param("orderNo") String orderNo);




}
