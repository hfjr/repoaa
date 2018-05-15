package com.rqb.ips.depository.platform.faced;

import java.util.Map;

/**
 * 
 * @author whb
 * 处理提现
 */
public interface IpsWithDrawService {

	void updateWithDraw(Map<String, Object> json2map);

	Map<String,Object> userWithDraw(String userId);

	boolean queryWithDrawStatus(String merBillNo);

}
