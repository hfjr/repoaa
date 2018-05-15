package com.zhuanyi.vjwealth.wallet.mobile.credit.server;


import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.easyLoan.EasyLoanSaveDTO;

/**
 * 工资易贷接口
 */
public interface IEasyLoanService {

	/**
	 * 工资易贷   意向申请
	 * @param easyLoanSaveDTO
	 */
	Object EasyLoanApplySave(EasyLoanSaveDTO easyLoanSaveDTO);



	/**
	 * 产品初始化
	 * @param userId
	 * @return
	 * @since 4.0
	 */
	Object easyLoanInit(String userId,String productId);

	/**
	 * 获取支持城市列表接口
	 * @param loanProductId
	 * @return
     */
	Object queryCityList(String loanProductId);

	/**
	 * 查询借款期限
	 * @param cityCode
	 * @return
     */
	Object queryPeriodByCity(String cityCode);
}
