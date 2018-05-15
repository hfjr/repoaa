package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserInsuranceDTO;

/**
 * @author xuewentao
 *
 */
@Mapper
public interface IMBUserInsuranceMapper {

	//查询购买人保险信息
	MBUserInsuranceDTO queryPICCInsuranceInfo(@Param("userId")String userId);
	
	//是否已购买过人保
	int countBuyPICCInsurance(@Param("userId")String userId);
	
	//购买中国人保
	void buyPICCInsurance(@Param("userId")String userId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
}
