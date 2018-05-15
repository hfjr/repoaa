package com.rqb.ips.depository.platform.mapper;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.rqb.ips.depository.platform.beans.IPSResponse;
/**
 * 
 * @author whb
 *  调用ips平台日志 
 *
 */
@Mapper
public interface ExcuteIpsPlatLogMapper {

	void insertIpsPlatformLogInfor(HashMap<String, String> map);
	//void insertIpsPlatformLog(@Param(value = "source") String source,@Param(value = "operationType") String operationType);

	//储存记录
	 void saveRechargeHistoryIPS(@Param("cardId") String cardId,@Param("cardNo") String cardNo,@Param("tradeNo") String tradeNo, @Param("amount") String amount, @Param("result")String result, @Param("message")String message,@Param("requestJson")String requestJson,@Param("responseJson") String responseJson, @Param("userId")String userId,@Param("status")String status,@Param("source")String source,@Param("operationType")String operationType);

}
