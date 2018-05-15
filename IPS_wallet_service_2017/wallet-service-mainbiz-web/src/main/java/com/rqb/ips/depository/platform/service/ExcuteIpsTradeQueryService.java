package com.rqb.ips.depository.platform.service;


import java.util.Map;

import com.hf.comm.utils.DESUtil;
import com.hf.comm.utils.Define;
import com.hf.comm.utils.HttpClientUtils;
import com.hf.comm.utils.IpsVerifySign;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.beans.IPSResponse;
import com.rqb.ips.depository.platform.beans.IpsTradeQuery;
import com.rqb.ips.depository.platform.faced.IpsTradeQueryService;

/**
 * 
 *  TODO(这是交易查询实现类)
 */
public class ExcuteIpsTradeQueryService implements IpsTradeQueryService{


	@Override
	public IPSResponse tradeQuery(IpsTradeQuery ipsTradeQuery) {
		IPSResponse response=new IPSResponse();
			try {
				String ipsRes=HttpClientUtils.ipsPostMethod(Define.OperationType.COMMONQUERY,JSONUtils.obj2json(ipsTradeQuery));
				System.out.println("接口输出 "+ipsRes);	
				Map<String, Object> json2map = JSONUtils.json2map(ipsRes);
					
					response.setCode(json2map.get("resultCode").toString());
					response.setMsg(json2map.get("resultMsg").toString());
					response.setData(ipsRes);
			} catch (Exception e) {
				response.setCode(IPSResponse.ErrCode.TIME_OUT);
				response.setMsg("连接超时");
			} 
		
		return response;
	}
	
	
	
	
	/**
	 *  TODO(这是交易查询测试)
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args)   {
		ExcuteIpsTradeQueryService openAccount=new ExcuteIpsTradeQueryService();
		IpsTradeQuery    ipsTradeQuery=new IpsTradeQuery();

		ipsTradeQuery.setIpsAcctNo("100000184497");
//		ipsTradeQuery.setIpsAcctNo("1234512451");
		ipsTradeQuery.setQueryType("01");//查询类型01:账户查询、02：交易查询、03:余额查询
//		ipsTradeQuery.setMerBillNo("118498100000142522680220171211031234");//商户订单
		
		IPSResponse ipsResponse = new IPSResponse();
		
		 ipsResponse = openAccount.tradeQuery(ipsTradeQuery);
		 System.out.println("代码:"+ipsResponse.getCode());
		 System.out.println("错误信息:"+ipsResponse.getMsg());
		 System.out.println("Data:"+ipsResponse.getData());
		 
		 
		

		 
		 
	}
}
