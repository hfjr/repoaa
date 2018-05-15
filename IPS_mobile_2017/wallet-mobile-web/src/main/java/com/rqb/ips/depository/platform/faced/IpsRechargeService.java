package com.rqb.ips.depository.platform.faced;

import com.rqb.ips.depository.platform.beans.IPSRechargeResponseBean;
import com.rqb.ips.depository.platform.beans.IPSRequierParamsBean;
import com.rqb.ips.depository.platform.beans.IpsRechargeBean;
import com.rqb.ips.depository.platform.beans.IPSResponse;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 
* <p>Title: IpsRechargeService.java</p>
* <p>Description: 充值</p>
* @author sunxiaolei
* @date 2017年12月5日
* @version 1.0
 */

public interface IpsRechargeService {
   
	/** 
	 * 充值申请
	 * */
	IPSRequierParamsBean recharge(IpsRechargeBean ipsRechargeBean);
	
	/** 
	 * 获取平台手续费
	 * */
	List<Map<String,String>> getMerFee(String paramGroup);
	
	/** 
	 * 记录日志
	 * */
	void saveHistory(String cardId,String cardNo,String tradeNo,String amount,String bankCode,String asidePhone,String result,String message,String requestJson,String responseJson,String userId,String status,String source,String operationType);
  
	/** 
	 * 记录订单
	 * */
    void saveRechargeOrder(String tradeNo,String userId,String amt,String orderNo,String bankCode,String ipsAcctNo);
    
    /** 
	 * 修改记录状态
	 * */
    void updateRechaStatus(IPSRechargeResponseBean ipsRechargeResponseBean);

}

