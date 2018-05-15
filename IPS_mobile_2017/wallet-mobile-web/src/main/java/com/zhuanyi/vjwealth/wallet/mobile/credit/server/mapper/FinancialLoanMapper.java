package com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.financialLoan.*;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.pledgeInvest.ProductDetailDTO;
import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

/**
 * 理财贷
 * Created by wangzf on 16/5/20.
 * 
 */
@Mapper
public interface FinancialLoanMapper {
	
	

	/**
	 * 查询投资列表记录
	 * @param userId
	 * @return
	 */
	List<FinancialLoanInvestmentDTO> queryFinancialLoanInvestmentList(@Param("userId")String userId, @Param("page")Integer page);

	/**
	 * 投资记录汇总
	 * @param userId
	 * @return
	 */
	FinancialLoanInvestmentSummaryDTO queryFinancialLoanInvestmentSummary(@Param("userId")String userId);

	/**
	 * 查询投资记录详情
	 * @param orderId
	 */
	FinancialLoanInvestmentDetailDTO queryInvestmentDetailByOrderId(@Param("orderId")String orderId, @Param("userId")String userId);

	/**
	 * 查询投资动态
	 * @param orderId
	 * @return
	 */
	FinancialLoanInvestmentNewFlows queryInvestmentNewsFlow(@Param("orderId")String orderId);

	/**
	 * 查询投资还款计划
	 * @param orderId
	 * @return
	 */
	FinancialLoanRepayPlanDTO queryInvestmentRepayPlan(@Param("orderId")String orderId);
	
	/**
	 * 查询定向理财产品列表
	 */
	List<FinancialLoanInitiInnerProductDTO> queryFinancialLoanProductList(@Param("page")Integer page);

	/**
	 * 查询定向理财产品列表(新)
	 */
	List<FinancialLoanInitiInnerProductDTO> queryNewFinancialLoanProductList(@Param("page")Integer page);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	Map<String, String> queryUserNameAndIdentyNoByUserId(@Param("userId")String userId);
	
	/**
	 * 下单初始化
	 * @param productId
	 * @return
	 */
	InvestInitiDTO queryProductIsCanBuy(@Param("productId")String productId);

	/**
	 * 
	 * @param key 
	 * @param group
	 * @return
	 */
	String getParamsValueByKeyAndGroup(@Param("key")String key, @Param("group")String group);

	/**
	 * 获取用户投资产品对应的贷款产品信息
	 * @param userId
	 * @return
	 */
	List<FinancialLoanCreditProductInfoDTO> queryUserLoanProductInfo(@Param("userId")String userId,@Param("page")Integer page);

	/**
	 * 根据投资产品编号，获取信贷产品编号
	 * @param productId
	 * @return
	 */
	String queryLoanProductIdById(@Param("productId")String productId);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	UserInfoParamDTO queryUserBaseInfoByUserId(@Param("userId")String userId);

	/**
	 * 
	 * @return
	 */
	List<Map<String, String>> queryLoanCommonIcon();

	/**
	 * 查询用户是否有投资记录
	 * @param userId
	 * @return
	 */
	String queryIfInvested(@Param("userId")String userId);

	/**
	 * 查询产品名称及到期时间
	 * @param productId
	 * @return
	 */
	Map<String, String> queryProductNameAndPeriod(@Param("productId")String productId);

	/**
	 * 查询公积金信息的对应展示信息
	 * @param cityCode
	 * @return  map (key值：labelName,labelValue)
	 *         
	 */
	List<Map<String, String>> queryCityLabelPkByCityCode(@Param("cityCode")String cityCode);

	/**
	 * 查询项目期限
	 * @param productId
	 * @return
	 */
	String queryProductPeriodByProductId(@Param("productId")String productId);
	
	
	/**
	 * 查询投资期限
	 * @param borrowCode
	 * @return
	 */
	String queryLoanPeriodByBorrowCode(@Param("borrowCode")String borrowCode);

	/**
	 * 需要实名认证
	 * @param userId
	 * @return
     */
	String getIsShowInvestmentIip(@Param("userId")String userId);

	/**
	 * 查询用户是否有安全卡
	 * @param userId
	 * @return
     */
	boolean checkIsHaveSecurityCard(@Param("userId")String userId);

	/**
	 * 查询邀请好友注册送小金鱼每日剩余名额
	 * @return
     */
	int querySurplusSpaceForInviteRegisterUserNum();

	/**
	 * @author zhangyingxuan
	 * @date 20160831
	 * 一个手机号同时拥有的小金鱼数目限制
	 * @return
	 */
	BigDecimal queryGoldFishUpperLimitAmount();

	/**
	 * @author zhangyingxuan
	 * @date 20160901
	 * 查询用户在投的小金鱼金额
	 * @param userId
	 * @return
	 */
	BigDecimal queryInvestGoingAmount(@Param("userId") String userId);

	ProductDetailDTO queryProductDetailByLoanCode(@Param("userId")String userId, @Param("loanCode") String loanCode);
}
