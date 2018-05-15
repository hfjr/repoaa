package com.zhuanyi.vjwealth.wallet.mobile.product.server.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.ContractDeatilQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.InvestmentDetailQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.InvestmentRecordSummaryQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.NewestActivityListDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.OrderInitQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.RepaymentPlanListDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.SalaryBillListQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UserCertificationDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UserInvestmentListQueryDTO;

@Mapper
public interface IInvestmentOrderMapper {
	

	/**
	 * 查询 用户是否可购买
	 * @param productId 产品编号
	 * @param userId 用户编号
	 * @return
	 *  {
	    "isCanBuy": "true",
	    "information": "",
	    "product": {
	      "productId": "20160116880001",
	      "productCode": "loan_20160116880001",
	      "productName": "随时贷",
	      "remainingInvestment": "180000",
	      "AnnualYield": "0.1",
	      "projectTerm": "30",
	      "paymentWay": "到期还本付息"
	    },
	    "availableBalance": "10000",
	    "availableBalancePlaceholderTip": "可用余额10,000元",
	    "inputBottomTipContents": "必须为100的整数倍|",
	    "fromInvestmentAmount": "100",
	    "incrementAmount": "100"
	  }
	 */
	OrderInitQueryDTO queryProductCanBuy(@Param("productId")String productId,@Param("userId")String userId);

	/**
	 * 查询 用户是否可购买
	 * @param productId 产品编号
	 * @param userId 用户编号
	 * @since 3.1
	 * @return
	 *  {
	"isCanBuy": "true",
	"information": "",
	"product": {
	"productId": "20160116880001",
	"productCode": "loan_20160116880001",
	"productName": "随时贷",
	"remainingInvestment": "180000",
	"AnnualYield": "0.1",
	"projectTerm": "30",
	"paymentWay": "到期还本付息"
	},
	"availableBalance": "10000",
	"availableBalancePlaceholderTip": "可用余额10,000元",
	"inputBottomTipContents": "必须为100的整数倍|",
	"fromInvestmentAmount": "100",
	"incrementAmount": "100"
	}
	 */
	OrderInitQueryDTO queryProductCanBuyV2(@Param("productId")String productId,@Param("userId")String userId);

	/**
	 * @title 查询用户是否进行过投资
	 * @param userId 用户ID
	 * @return
	 */
	boolean queryWhetherInvestmentRecord(@Param("userId")String userId);

	/**
	 * @title 查询投资记录汇总［在投本金，待收利息，累积收益］
	 * @param userId
	 * @return
	 */
	InvestmentRecordSummaryQueryDTO queryUserInInvestment(@Param("userId")String userId);

	/**
	 * @title 查询投资中的投资记录
	 * @param userId 用户编号
	 * @param page 页码
	 * @return
	 */
	List<UserInvestmentListQueryDTO> queryUserInvestment(@Param("userId")String userId,@Param("page")Integer page);

	/**
	 * @title 查询投资结束的投资记录
	 * @param userId 用户编号
	 * @param page 页码
	 * @return
	 */
	List<UserInvestmentListQueryDTO> queryUserInvestmentEnd(@Param("userId")String userId,@Param("page")Integer page);

	/**
	 * @title 查询待赔付的投资记录
	 * @param userId 用户编号
	 * @param page 页码 
	 * @return
	 */
	List<UserInvestmentListQueryDTO> queryUserPendingPayment(@Param("userId")String userId,@Param("page")Integer page);

	/**
	 * 单条投资记录的详情
	 * @param userId 用户ID
	 * @param orderId 订单编号
	 * @return
	 */
	InvestmentDetailQueryDTO queryUserInvestmentDetail(@Param("userId")String userId,@Param("orderId")String orderId);

	/**
	 * @title 查询投资的动态流程列表
	 * @param orderId 订单编号
	 * @return
	 */
	NewestActivityListDTO queryUserInvestmentNewsFlow(@Param("orderId")String orderId);

	/**
	 * @title 还款计划
	 * @param orderId 订单编号
	 * @return
	 */
	RepaymentPlanListDTO queryUserInvestmentNewsRepaymentPlan(@Param("orderId")String orderId);

	
	
	/**
	 * @title 判断用户是否可以实名认证
	 * @param userId
	 * @return
	 */
	boolean queryUserIsCanAuthenticate(@Param("userId")String userId);

	/**
	 * @title 查询用户认证信息
	 * @param userId 用户ID
	 * @return
	 */
	UserCertificationDTO realNameAuthenticateInit(@Param("userId")String userId);

	/**
	 * 保存实名认证信息
	 * @param userId
	 * @param rightSideFileId
	 * @param reverseSideFileId
	 */
	void saveUserRealNameCertification(@Param("userId")String userId,@Param("rightSideFileId")String rightSideFileId,@Param("reverseSideFileId")String reverseSideFileId);

	/**
	 * 删除用户授权信息
	 * @param userId
	 */
	void deleteUserCertification(@Param("userId")String userId);

	/**
	 * 查询用户工资单列表
	 * @param userId 用户ID
	 * @param page 页码
	 * @return
	 */
	List<SalaryBillListQueryDTO> queryPayRollList(@Param("userId")String userId,@Param("page")Integer page);
	
	
	/**
	 * 查询用户工资单详情
	 * @param payRollId 工资单ID
	 * @return
	 */
	Map<String,String> queryPayRollDetail(@Param("payRollId")String payRollId);

	/**
	 * 查询产品的合同模板
	 * @param productId 产品编号
	 * @return
	 */
	String investmentContractTemplate(@Param("productId")String productId);

	/**
	 * 查询理财合同编号根据贷款订单编号和用户编号
	 * @param userId 用户编号
	 * @param loanCode 借款编号
	 * @return 合同编号
	 */
	String queryInvestmentContractNoByUserIdAndLoanCode(@Param("userId")String userId,@Param("loanCode")String loanCode);

	/**
	 * 查询合同的详细信息
	 * @param orderId 订单编号
	 * @return
	 */
	ContractDeatilQueryDTO investmentContractDetail(@Param("orderId")String orderId);

	/**
	 * 判断产品是否在维护模式
	 * @return
	 */
	Boolean isMaintenanceMode();

	

}
