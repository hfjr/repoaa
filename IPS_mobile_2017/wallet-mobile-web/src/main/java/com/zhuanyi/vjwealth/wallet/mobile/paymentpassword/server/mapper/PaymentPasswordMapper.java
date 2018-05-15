package com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.CheckPaymentPasswordDTO;
import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.FilterValidatorPaymentPasswrodDTO;
import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.ValidatorPaymentPasswrodAuthorizeDTO;

/**
 * Created by yi on 16/4/13.
 */
@Mapper
public interface PaymentPasswordMapper {
	
	//查询支付密码状态
    FilterValidatorPaymentPasswrodDTO queryPaymentPasswordState(@Param("userId")String userId);
	
	//校验支付密码:result: true(成功),false(失败);  failures: 失败次数; lockFlag: 是否需要锁定账户(yes:需要,no:不需要)
	CheckPaymentPasswordDTO checkPaymentPassword(@Param("userId")String userId,@Param("password")String password);
	
	//校验用户，安全卡卡号，身份证号是否匹配
	ValidatorPaymentPasswrodAuthorizeDTO validatorPaymentPasswrodAuthorize(@Param("userId")String userId,@Param("identityNo")String identityNo,@Param("paymentCardNo")String paymentCardNo);
	
	//查询用户手机号
	String queryUserPhoneByUserId(@Param("userId")String userId);
	
	//查询是否启用支付密码
	String queryUserPaymentPasswordSwitch(@Param("userId")String userId);
	
	//查询用户支付密码信息
	Map<String,String> queryUserPaymentPasswordInfo(@Param("userId")String userId);
}
