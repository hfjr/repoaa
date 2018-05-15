package com.zhuanyi.vjwealth.wallet.mobile.product.server;

import java.io.IOException;
import java.util.Map;

import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.account.UserTAccountHomeDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.InvestmentDetailQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.InvestmentRecordSummaryQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.NewestActivityListDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.OrderInitQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.RepaymentPlanListDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UploadIdentityPhotosDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UserCertificationDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.account.UserEAccountHomeDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.account.UserMAccountHomeDTO;


public interface IInvestmentOrderService {

	/**
	 * 下订单的初始化页面
	 * @param productId
	 * @param userId
	 * @return 示例
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
	  @since 3.0
	 */
	OrderInitQueryDTO queryProductCanBuy(String productId,String userId);

	/**
	 * 下订单的初始化页面
	 * @param productId
	 * @param userId
	 * @return 示例
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
	 @since 3.1
	 */
	OrderInitQueryDTO queryProductCanBuyV2(String productId,String userId);

	/**
	 * @title 判断用户是否进行过投资
	 * @param userId 用户ID
	 * @return true or false
	 * @since v3.0
	 */
	Map<String,Object> queryWhetherInvestmentRecord(String userId);

	/**
	 * @title 查询投资记录汇总［在投本金，待收利息，累积收益］
	 * @param userId
	 * @return
	 * @since 3.0
	 */
	InvestmentRecordSummaryQueryDTO queryUserInInvestment(String userId);

	/**
	 * 查询用户投资记录列表
	 * @param userId 用户ID
	 * @param investmentStatus （investment：投资中；investmentEnd：已结束；pendingPayment：待赔付）
	 * @param page 页码
	 * @return
	 * @since 3.0
	 */
	Map<String,Object> queryUserInvestmentRecordList(String userId, String investmentStatus, String page);

	/**
	 * @title 用户投资列表的，投资(动态)详情
	 * @param userId 用户ID
	 * @param orderId 账单编号
	 * @return
	 * @since 3.0
	 */
	InvestmentDetailQueryDTO queryUserInvestmentDetail(String userId, String orderId);

	/**
	 * @title 投资动态-动态流程
	 * @param orderId 订单编号
	 * @return
	 * @since 3.0
	 */
	NewestActivityListDTO queryUserInvestmentNewsFlow(String orderId);

	/**
	 * @title 投资动态－回款计划
	 * @param orderId 订单编号
	 * @return
	 * @since 3.0
	 */
	RepaymentPlanListDTO queryUserInvestmentNewsRepaymentPlan(String orderId);

	/**
	 * @title 实名认证
	 * @param userId 用户ID
	 * @return
	 * @since 3.0
	 */
	UserCertificationDTO realNameAuthenticateInit(String userId);


	/**
	 * 身份证正面文件上传
	 * @param userId
	 * @param rightSideFile
	 * @param reverseSideFile
	 * @param uploadSuccessFileId [只有一个文件上传成功，第二次上传使用]
	 * @return
	 * @since 3.0
	 */
	UploadIdentityPhotosDTO uploadIdentityPhotos(String userId,MultipartFile rightSideFile
			, MultipartFile reverseSideFile,String uploadSuccessFileId) throws IOException;


	/**
	 * 保存用户实名身份证文件上传关系
	 * @param userId
	 * @param rightSideFileId
	 * @param reverseSideFileId
	 * @param userChannelType [ios,android,wechat]
	 * @return
	 * @since 3.0
	 */
	UploadIdentityPhotosDTO saveUploadIdentityPhotos(String userId,String rightSideFileId
			, String reverseSideFileId,String userChannelType)  ;

	/**
	 * 删除用户授权信息
	 * @param userId 用户ID
	 * @return
	 * @since 3.0
	 */
	void deleteUserCertification(String userId);

	/**
	 * 查询用户工资单列表
	 * @param userId 用户ID
	 * @param page 页码
	 * @return
	 * @since 3.0
	 */
	Object queryPayRollList(String userId, String page);

	/**
	 * 查询用户工资单列表
	 * @param payRollId 工资单ID
	 * @return
	 * @since 3.0
	 */
	Object queryPayRollDetail(String payRollId);

	/**
	 * 下单
	 * @param userId 用户ID
	 * @param productId 产品ID
	 * @param investmentAmount 投资金额
	 * @return
	 * @since 3.0
	 */
	Object placeOrder(String userId, String productId, String investmentAmount,String token);
	
	
	/**
	 * 下单包含推荐人
	 * @param userId
	 * @param productId
	 * @param investmentAmount
	 * @param token
	 * @return
	 */
	Object placeOrder(String userId, String productId, String investmentAmount, String rpId, String couponId,String clientType, String token,String recommendPhone);
	/**
	 * 红包冻结
	 * @param userId
	 * @param productId
	 * @param investmentAmount
	 * @param packageId
	 * @param couponId
	 * @param clientType
	 * @param token
	 * @param recommendPhone
	 * @return
	 */
/*	Object packetByForzen(String userId, String productId, String investmentAmount, String packageId, String couponId,
			String clientType, String token, String recommendPhone);*/
	/**
	 * @author zhangyingxuan
	 * @date 20160721
	 * 购买理财产品增加红包购买
	 * @param userId
	 * @param productId
	 * @param investmentAmount
	 * @param rpId
	 * @param token
     * @return
     */
	Object placeOrder(String userId, String productId, String investmentAmount, String rpId, String clientType, String token);

	
	/**
	 * 查询合同详情
	 * @param orderId 订单号
	 * @return flag:是否成功
	 *         content:合同模板的页面片段
	 * @since 3.0
	 */
	Map<String,String> investmentContractDetail(String orderId);

	/**
	 * 查询产品的合同模板
	 * @param productId 产品号
	 * @return flag:是否成功
	 *         content:合同模板的页面片段
	 * @since 3.0
	 */
	Map<String,String> investmentContractTemplate(String productId);

	/**
	 * 用户登录
	 * @param phone
	 * @param password
	 * @return
	 */
	Object userLogin(String phone, String password);

	/**
	 * 判断产品是否在维护模式
	 * @return
	 */
	Boolean isMaintenanceMode();

	/**
	 * 财富首页初始化
	 * @param userId
	 * @return
	 */
	UserMAccountHomeDTO queryMAccountInfo(String userId);

	/**
	 * e账户首页的初始化
	 * @param userId
	 * @return
	 */
	UserEAccountHomeDTO queryEAccountInfo(String userId);

	/**
	 * T金所首页初始化
	 * @param userId
	 * @return
     */
	UserTAccountHomeDTO queryTAccountInfo(String userId);

	/**
	 * 查询理财合同编号根据贷款订单编号和用户编号
	 * @param userId 用户编号
	 * @param loanCode 借款编号
	 * @return 合同编号
	 */
	String queryInvestmentContractNoByUserIdAndLoanCode(@Param("userId")String userId, @Param("loanCode")String loanCode);
	/**
	 * 存钱罐页面初始化
	 * @param userId
	 * @param userChannelType 渠道类型(微信:weixin;安卓:Android;苹果:IOS)
	 * @since 3.6
	 * @return
	 */
	Map<String,Object> queryMAccountInfo(String userId, String userChannelType);
	/**
	 * 存钱罐产品年化利率
	 * @since 3.6
	 * @return
	 */
	String queryTaReceiveRate();
}
