package com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.balance.common.dto.BankBalanceLimitDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface IUserQueryMapper {
	
	//查询用户的安全卡
	Map<String,String> queryUserSecurityCardInfo(@Param("userId")String userId);
	
	//查询用户的充值卡
	List<Map<String,String>> queryUserRechargeCardInfo(@Param("userId")String userId);
	
	//查询用户的充值卡,V3.2版本
	List<Map<String,String>> queryUserRechargeCardInfoV32(@Param("userId")String userId);

	//查询用户的充值卡,V3.6版本
	List<Map<String,String>> queryUserRechargeCardInfoV36(@Param("userId")String userId);
	
	//查询银行卡当天充值了多少
	String queryUserRechargeSuccessOneDay(@Param("userId")String userId);
	
	//查询用户的所有银行卡
	List<Map<String,String>> queryUserAllCardInfo(@Param("userId")String userId);
	
	//查询用户的所有银行卡数
	Map<String,String> queryUserAllCardNum(@Param("userId")String userId);
	
	//查询用户个人消息
	List<Map<String,String>> queryUserPersonalMessageList(Map<String,Object> paramMap);
	
	//查询用户系统消息
	List<Map<String,String>> queryUserSystemMessageList(Map<String,Object> paramMap);
	
	//根据消息主键查询
	Map<String,String> queryUserMessageById(@Param("id")String id);
	
	//查询用户消息数
	Map<String,String> queryUserMessageNum(@Param("userId")String userId);
	
	//更新用户阅读记录
	void updateUserMessageReadById(@Param("id")String id);
	
	//查询用户手机号和头像文件id
	Map<String,String> queryUserPhoneAndHeadPictureId(@Param("userId")String userId);

	//查询用户手机号和实名认证标示
	Map<String,String> queryUserPhoneAndIsShowCerty(@Param("userId")String userId);
	
	//查询用户手机号和实名认证标示
	Map<String,String> queryUserPhoneAndIsShowCertyAndIsInvestment(@Param("userId")String userId);
	

	//查询用户是否是工资单用户
	String getIsSign(@Param("userId")String userId);
	
	//查询银行卡支持的列表V3.2
	List<BankBalanceLimitDTO> queryAllSupportBankListV32();
	//查询银行卡支持的列表V3.6
	List<BankBalanceLimitDTO> queryAllSupportBankListV36();

	Map<String, String> queryMyInvitePageInfo(@Param("userChannelType") String userChannelType);

	Map<String,String> queryUserAllUsableCashCouponNum(@Param("userId") String userId);


    //根据传入的policyNo,去产品表查询相应大保单图片绝对路径
    String queryPolicyPicUrlByPolicyNo(@Param("policyNo")String policyNo);

	//查询用户手机号
	String queryUserPhoneNo(@Param("userId") String userId);

	boolean existUserByPhone(@Param("phone") String phone);

	String queryUserIdByPhone(@Param("phone") String phone);
	
	//Map<String, String> queryUserIsOpenAccount(String userId);*/
	boolean queryUserIsOpenAccount(String userId);

	Map<String, Object> queryUserInforByUserId(@Param("userId") String userId);

}
