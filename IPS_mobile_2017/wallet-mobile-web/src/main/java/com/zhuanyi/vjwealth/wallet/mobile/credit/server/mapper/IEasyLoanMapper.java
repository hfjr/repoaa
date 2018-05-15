package com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper;

import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.easyLoan.EasyLoanInitDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.easyLoan.EasyLoanSaveDTO;
import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

import java.util.Map;

/**
 * Created by yi on 16/5/20.
 */
@Mapper
public interface IEasyLoanMapper {
	
	/**
	 * 意向申请保存
	 * @param
	 * @return
	 */
	int easyLoanApplySave(EasyLoanSaveDTO easyLoanSaveDTO);


	/**
	 * 查询用户基本信息
	 * @param userId
	 * @return
     */
	Map<String,String> queryUserInfoByUserId(@Param("userId")String userId);

	/**
	 * 查询初始化图片，电话等
	 * @return
     */
	EasyLoanInitDTO queryEasyLoanInitParam();
}
