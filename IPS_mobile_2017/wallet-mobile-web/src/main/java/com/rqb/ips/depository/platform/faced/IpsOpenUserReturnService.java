package com.rqb.ips.depository.platform.faced;

import java.util.Map;
/**
 * 
 * title:开户返回回掉
 * @author sunxiaolei
 *
 */
public interface IpsOpenUserReturnService {
	
	/**
	 * 开户返回
	 * @param respResult
	 * @return 
	 * @throws Exception 
	 */


	void queryOpenStatus(Map<String, Object> respResult);


	void openUser(Map<String, Object> respResult);



}
