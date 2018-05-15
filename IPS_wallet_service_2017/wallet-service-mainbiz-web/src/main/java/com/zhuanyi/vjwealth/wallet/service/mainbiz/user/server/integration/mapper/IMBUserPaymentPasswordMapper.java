package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;

/**
 * Created by yi on 16/4/13.
 */
@Mapper
public interface IMBUserPaymentPasswordMapper {
	
	
	//更新支付密码----将移到mainbiz中
	int updateUserPaymentPassword(@Param("userId")String userId,@Param("password")String password);
	
	//更新验证支付密码失败次数
	int updateUserPaymentPasswordValidationFailures(@Param("userId")String userId,@Param("failures")String failures,@Param("reason")String reason);
	
	//更新支付密码的状态
	int updateUserPaymentPasswordState(@Param("userId")String userId,@Param("state")String state);
	
	//由于输错支付密码锁定账户
	int lockUserPaymentPasswordForCheck(@Param("userId")String userId);
	
	//解锁：更新支付密码状态为normal，failures次数为0
	int unlockUserPaymentPassword(@Param("userId")String userId);
	
	//新增用户支付密码数据----将移到mainbiz中
	void saveUserPaymentPassword(@Param("userId")String userId,@Param("password")String password);
	
	//删除用户支付密码
	int deleteUserPaymentPassword(@Param("userId")String userId,@Param("password")String password);
	
	//删除用户支付密码
	int deleteUserPaymentPasswordByUserId(@Param("userId")String userId);
	
	
	//更新支付密码鉴权校验的失败次数
	int updateUserPaymentPasswordAuthorizationFailures(@Param("userId")String userId,@Param("failures")String failures,@Param("reason")String reason);
	
	//由于支付密码鉴权失败锁定账户
    int lockUserPaymentPasswordForAuthorization(@Param("userId")String userId);
	
	
}
