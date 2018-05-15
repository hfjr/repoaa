package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service;

import java.math.BigDecimal;
import java.util.List;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RfRepayPlanDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.RfResponseDTO;
@Mapper
public interface IMBProductProcess {
	
	RfResponseDTO processOrder(BigDecimal amount, MBUserInfoDTO userinfo, String orderNo, String productId, String tradeId );
	
	RfResponseDTO processOrder(BigDecimal amount,BigDecimal addProfit, MBUserInfoDTO userinfo, String orderNo, String productId, String tradeId );

	RfRepayPlanDTO generateAdvExpireOrder(BigDecimal amount, MBUserInfoDTO userinfo, String orderNo, String startTime, String endTime);

	boolean queryOrder(String orderNo, String productId, String tradeId);
	
	List<RfRepayPlanDTO> calcRepayPlanByMonthInteresting(BigDecimal amount, int firstmonthday, BigDecimal annualRating,String interestStartTime, String productEndTime, String productRepaymentType);
}
