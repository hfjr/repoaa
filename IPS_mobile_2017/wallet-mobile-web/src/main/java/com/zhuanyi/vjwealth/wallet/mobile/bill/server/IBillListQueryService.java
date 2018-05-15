package com.zhuanyi.vjwealth.wallet.mobile.bill.server;

import java.util.Map;

public interface IBillListQueryService {
	
	/**
	 * @title 根据条件查询账单列表
	 * @param userId 用户ID
	 * @param type 类型()
	 * @param page 页码
	 * @return
	 * @since 3.0
	 */
	Map<String,Object> getBillListByUserIdAndType(String userId,String type,String page);

}
