package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.ConfirmShareModelDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBOrderDetailDTO;

@Mapper
public interface IMBQueryOrderDetailMapper {
	
	ConfirmShareModelDTO queryConfirmShareModel(@Param("paramKey")String paramKey, @Param("ymdInt")Integer ymdInt);

	List<String> queryNextTwoWorkdaysByBuyday(@Param("ymdInt")Integer ymdInt);

	int countOrderAmount(@Param("orderNo")String orderNo);
	
	MBOrderDetailDTO queryBillDetailByOrderNo(@Param("orderNo")String orderNo);
}
