package com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.service;

import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswordDTO;
import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswrodAuthorizeDTO;

/**
 * 支付密码模块接口
 * @author wangzhangfei 2016/04/18
 */
public interface IPaymentPasswordService {

    //  支付模块是否启用接口 【合并在账户中心逻辑中】

    // 初始化支付密码 【设置密码的时候调用】initiPaymentPassword
   // void initiPaymentPassword(String userId);
	
	
	
	

	/**
	 * 查询用户支付密码状态，用于支付拦截器
	 * @param userId 用户ID
	 * @return paymentPasswordDTO : [code: 200300(成功)
     *                                message: 成功或失败的提示信息 ]
	 * @see com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswordDTO
	 * @since 3.1.2
	 */
	PaymentPasswordDTO queryPaymentPasswordStatus(String userId);

    
    
    /**
     * 设置支付密码
     * @param userId  用户ID
     * @param password 密码(加密后)
     * @return paymentPasswordDTO : [code: 200500(成功),200501(失败)
     *                                message: 成功或失败的提示信息 ]
     * @see com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswordDTO
     * @since 3.1.2
     */
    PaymentPasswordDTO setPaymentPassword(String userId,String password);

    
    /**
     * 关闭支付密码
     * @param userId  用户ID
     * @param password 密码(加密后)
     * @return paymentPasswordDTO : [code: 200600(成功),200701(验证密码失败可以继续尝试),200702(验证密码失败账户被锁),200601(关闭支付密码失败(更新数据库失败))
     *                               message: 成功或失败的提示信息 ]
     * @see com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswordDTO
     * @since 3.1.2
     */
    PaymentPasswordDTO closePaymentPassword(String userId,String password);

    
    
    /**
     * 验证支付密码
     * @param userId  用户ID
     * @param password 密码(加密后)
     * @return paymentPasswordDTO : [code: 200700(成功),200701(验证密码失败可以继续尝试),200702(验证密码失败账户被锁)
     *                               message: 成功或失败的提示信息 ]
     * @see com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswordDTO
     * @since 3.1.2
     */
    PaymentPasswordDTO checkPaymentPassword(String userId,String password);

    
    
    /**
     * 重置支付密码（有原密码）
     * @param userId  用户ID
     * @param password 新密码(加密后)
     * @return paymentPasswordDTO : [code: 200800(成功),200801(失败)
     *                               message: 成功或失败的提示信息 ]
     * @see com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswordDTO
     * @since 3.1.2
     */
    PaymentPasswordDTO resetPaymentPasswordForRememberPassword(String userId,String password);

    
    
    /**
     * 重置支付密码（忘记密码）
     * @param paymentPasswrodAuthorizeDTO ：[ userId: 用户ID(必须)
     *                                        paymentCardNo ：安全卡卡号(必须)，
     *                                        identityNo ： 身份证号 (必须)
     *                                        code : 验证码]
     * @return paymentPasswordDTO : [code: 200900(成功),200901(失败)
     *                               message: 成功或失败的提示信息 ]
     * @see com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswrodAuthorizeDTO
     * @see com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswordDTO
     * @since 3.1.2
     */
    PaymentPasswordDTO resetPaymentPasswordForForgetPassword(PaymentPasswrodAuthorizeDTO paymentPasswrodAuthorizeDTO);
    

    /**
     * 重置支付密码-发送语音验证码（忘记密码）
     * @param paymentPasswrodAuthorizeDTO ：[ userId: 用户ID(必须)
     *                                        paymentCardNo ：安全卡卡号(必须)，
     *                                        identityNo ： 身份证号 (必须)]
     * @return vaildeTime ： 验证码有效时间
     * @see com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswrodAuthorizeDTO
     * @since 3.1.2
     */
    String sendForgetToneNoticeForForgetPassword(PaymentPasswrodAuthorizeDTO paymentPasswrodAuthorizeDTO);

    
    
    /**
     * 重置支付密码-发送短信验证码（忘记密码）
     * @param paymentPasswrodAuthorizeDTO ：[ userId: 用户ID(必须)
     *                                        paymentCardNo ：安全卡卡号(必须)，
     *                                        identityNo ： 身份证号 (必须)]
     * @return vaildeTime ： 验证码有效时间
     * @see com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswrodAuthorizeDTO
     * @since 3.1.2
     */
    String sendForgetSMSNoticeForForgetPassword(PaymentPasswrodAuthorizeDTO paymentPasswrodAuthorizeDTO);


    
    /**
     * 解锁-支付密码验证超过最大尝试次数
     * @param paymentPasswrodAuthorizeDTO ：[ userId: 用户ID(必须)
     *                                        paymentCardNo ：安全卡卡号(必须)，
     *                                        identityNo ： 身份证号 (必须)
     *                                        code : 验证码]
     * @return paymentPasswordDTO : [code: 201000(成功),201001(失败)
     *                               message: 成功或失败的提示信息 ]
     * @see com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswrodAuthorizeDTO
     * @see com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswordDTO
     * @since 3.1.2
     */
    PaymentPasswordDTO unlockPaymentPassword(PaymentPasswrodAuthorizeDTO paymentPasswrodAuthorizeDTO);
    
    
    
    /**
     * 解锁-支付密码验证超过最大尝试次数--发送短信验证码（自助）
     * @param paymentPasswrodAuthorizeDTO ：[ userId: 用户ID(必须)
     *                                        paymentCardNo ：安全卡卡号(必须)，
     *                                        identityNo ： 身份证号 (必须)]
     * @return vaildeTime ： 验证码有效时间
     * @see com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswrodAuthorizeDTO
     * @since 3.1.2
     */
    String sendForgetSMSNoticeForUnlockPaymentPassword(PaymentPasswrodAuthorizeDTO paymentPasswrodAuthorizeDTO);
    
    

    /**
     * 解锁-支付密码验证超过最大尝试次数-发送语音验证码（自助）
     * @param paymentPasswrodAuthorizeDTO ：[ userId: 用户ID(必须)
     *                                        paymentCardNo ：安全卡卡号(必须)，
     *                                        identityNo ： 身份证号 (必须)]
     * @return vaildeTime ： 验证码有效时间
     * @see com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswrodAuthorizeDTO
     * @since 3.1.2
     */
    String sendForgetToneNoticeForUnlockPaymentPassword(PaymentPasswrodAuthorizeDTO paymentPasswrodAuthorizeDTO);
    
    
    
    /**
     * 支付密码-忘记密码初始化接口【无原密码】
     * @param userId 用户ID
     * @return phone 手机号
     * @since 3.1.2
     */
    String resetPaymentPasswordForForgetPasswordIniti(String userId);
    
    
    /**
     * 支付密码主页初始化接口
     * @param userId 用户ID
     * @return IsSwitch:yes/no
     * @since 3.1.2
     */
    String paymentPasswordIniti(String userId);
    
    
    
    /**
     * 人工解锁初始化（客服）
     * @param userId 用户ID
     * @return paymentPasswordDTO : [code: 201100(成功),201101(失败)
     *                               message: 成功或失败的提示信息 ]
     * @see com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswordDTO
     * @since 3.1.2
     */
    PaymentPasswordDTO unlockInitializationForArtificial(String userId);
    
}
