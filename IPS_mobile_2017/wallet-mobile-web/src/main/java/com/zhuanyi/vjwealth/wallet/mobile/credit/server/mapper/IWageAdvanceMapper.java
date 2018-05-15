package com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.wageAdvance.CreditInitiDTO;
import org.apache.ibatis.annotations.Param;

/**
 * Created by wangzhangfei on 16/7/12.
 */
@Mapper
public interface IWageAdvanceMapper {

	/**
	 * 查询用户安全卡(招商银行储蓄卡（5668)
	 * @param userId
	 * @return
	 */
	String queryUserSecurityCardDesc(@Param("userId") String userId);

	/**
	 * 查询借款初始化信息
	 * @return
	 */
	CreditInitiDTO queryUserCreditIniti();

}
