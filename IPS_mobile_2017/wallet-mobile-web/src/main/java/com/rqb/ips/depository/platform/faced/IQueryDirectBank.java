package com.rqb.ips.depository.platform.faced;

import com.rqb.ips.depository.platform.beans.IPSResponse;
import com.rqb.ips.depository.platform.beans.QueryDirectBank;

import java.util.Map;

/**
 * 
* <p>Title: IQueryDirectBank.java</p>
* <p>Description: </p>
* 查询银行
* @author sunxiaolei
* @date 2017年12月1日
* @version 1.0
 */
public interface IQueryDirectBank {

	/**
	 * @Title:查询银行
	 * @param queryDirectBank
	 * @return
	 */
	IPSResponse queryBankList();
	
	/**
	 * @Title:查询开户信息
	 * @param queryDirectBank
	 * @return
	 */
	Map<String,String> queryIPSAccount(String userId,String tradeType,String tradeAccountStatus);
	
	Boolean isIPS(String userId);

}
