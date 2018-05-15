package com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.web.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.core.template.ITemplateService;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswrodAuthorizeDTO;
import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.service.IPaymentPasswordService;

/**
 * Created by yi on 16/4/5.
 */
@Controller
@RequestMapping("/api/v3.1")
public class PaymentPasswordController {


	    @Autowired
	    private ITemplateService template;
	    
	    @Autowired
	    private IPaymentPasswordService paymentPasswordService;

	    /**
	     * 1.设置支付密码
	     * @param userId 用户名
	     * @param password 密码(加密后)
	     * @param type 返回场景类型
	     * @return json串
	     */
	    @RequestMapping("/app/paymentPassword/setPaymentPassword.security")
	    @AppController
	    public Object setPaymentPassword(String userId,String paymentPassword) {

	    	return paymentPasswordService.setPaymentPassword(userId, paymentPassword);

	    }
	    
	    
	    
	    /**
	     * 2.关闭支付密码
	     * @param userId 用户名
	     * @param password 密码(加密后)
	     * @param type 返回场景类型
	     * @return json串
	     */
	    @RequestMapping("/app/paymentPassword/closePaymentPassword.security")
	    @AppController
	    public Object closePaymentPassword(String userId,String paymentPassword) {

	    	return paymentPasswordService.closePaymentPassword(userId, paymentPassword);
	    }
	    
	    
	    /**
	     * 3.验证支付密码
	     * @param userId 用户名
	     * @param password 密码(加密后)
	     * @param type 返回场景类型
	     * @return json串
	     */
	    @RequestMapping("/app/paymentPassword/checkPaymentPassword.security")
	    @AppController
	    public Object checkPaymentPassword(String userId,String paymentPassword) {

	    	return paymentPasswordService.checkPaymentPassword(userId, paymentPassword);
	    }
	    
	    
	    /**
	     * 4.重置支付密码(有原密码)
	     * @param userId 用户名
	     * @param password 新密码(加密后)
	     * @param type 返回场景类型
	     * @return json串
	     */
	    @RequestMapping("/app/paymentPassword/resetPaymentPasswordForRememberPassword.security")
	    @AppController
	    public Object resetPaymentPasswordForRememberPassword(String userId,String paymentPassword) {

	    	return paymentPasswordService.resetPaymentPasswordForRememberPassword(userId, paymentPassword);
	    	
	    }
	    
	    
	    /**
	     * 5.重置支付密码(忘记密码)
	     * @param paymentPasswrodAuthorizeDTO (安全卡卡号，身份证号，手机号，验证码，用户名)
	     * @param type 返回场景类型
	     * @return json串
	     */
	    @RequestMapping("/app/paymentPassword/resetPaymentPasswordForForgetPassword.security")
	    @AppController
	    public Object resetPaymentPasswordForForgetPassword(PaymentPasswrodAuthorizeDTO paymentPasswrodAuthorizeDTO) {
	    	
	    	return paymentPasswordService.resetPaymentPasswordForForgetPassword(paymentPasswrodAuthorizeDTO);

	    }
	    
	    
	    /**
	     * 6.重置支付密码-发送语音验证码(忘记密码)
	    * @param paymentPasswrodAuthorizeDTO (安全卡卡号，身份证号，手机号，用户名)
	     * @param type 返回场景类型
	     * @return json串
	     */
	    @RequestMapping("/app/paymentPassword/sendForgetToneNoticeForForgetPassword.security")
	    @AppController
	    public Object sendForgetToneNotice(PaymentPasswrodAuthorizeDTO paymentPasswrodAuthorizeDTO) {

           final String vaildeTime = paymentPasswordService.sendForgetToneNoticeForForgetPassword(paymentPasswrodAuthorizeDTO);
	    	return new HashMap<String,String>(){
				private static final long serialVersionUID = 1L;
			    {put("remainTime",vaildeTime);}
			};
	           
	    }
	    
	    
	    /**
	     * 7.重置支付密码-发送短信验证码(忘记密码)
	     * @param paymentPasswrodAuthorizeDTO (安全卡卡号，身份证号，手机号，用户名)
	     * @param type 返回场景类型
	     * @return json串
	     */
	    @RequestMapping("/app/paymentPassword/sendForgetSMSNoticeForForgetPassword.security")
	    @AppController
	    public Object sendForgetSMSNotice(PaymentPasswrodAuthorizeDTO paymentPasswrodAuthorizeDTO) {
	    	
	    	final String vaildeTime = paymentPasswordService.sendForgetSMSNoticeForForgetPassword(paymentPasswrodAuthorizeDTO);
	    	return new HashMap<String,String>(){
				private static final long serialVersionUID = 1L;
			    {put("remainTime",vaildeTime);}
			};
 
	    }
	    
	    
	    /**
	     * 8.解锁-忘记密码重置（自助）
	     * @param paymentPasswrodAuthorizeDTO (安全卡卡号，身份证号，手机号，用户名)
	     * @param type 返回场景类型
	     * @return json串
	     */
	    @RequestMapping("/app/paymentPassword/unlockPaymentPassword.security")
	    @AppController
	    public Object byselfForgetPassword(PaymentPasswrodAuthorizeDTO paymentPasswrodAuthorizeDTO) {

	           return paymentPasswordService.unlockPaymentPassword(paymentPasswrodAuthorizeDTO);
	    }
	    
	    
	    /**
	     * 9.解锁-忘记密码重置（自助 文字短信）
	     * @param paymentPasswrodAuthorizeDTO (安全卡卡号，身份证号，手机号，用户名)
	     * @param type 返回场景类型
	     * @return json串
	     */
	    @RequestMapping("/app/paymentPassword/sendForgetSMSNoticeForUnlockPaymentPassword.security")
	    @AppController
	    public Object bySelfSendForgetSMSNotice(PaymentPasswrodAuthorizeDTO paymentPasswrodAuthorizeDTO) {

	    	final String vaildeTime = paymentPasswordService.sendForgetSMSNoticeForUnlockPaymentPassword(paymentPasswrodAuthorizeDTO);
	    	return new HashMap<String,String>(){
				private static final long serialVersionUID = 1L;
			    {put("remainTime",vaildeTime);}
			};
	    }
	    
	    
	    /**
	     * 10.解锁-忘记密码重置（自助 语音短信）
	     * @param paymentPasswrodAuthorizeDTO (安全卡卡号，身份证号，手机号，用户名)
	     * @param type 返回场景类型
	     * @return json串
	     */
	    @RequestMapping("/app/paymentPassword/sendForgetToneNoticeForUnlockPaymentPassword.security")
	    @AppController
	    public Object bySelfSendForgetToneNotice(PaymentPasswrodAuthorizeDTO paymentPasswrodAuthorizeDTO) {

	    	final String vaildeTime = paymentPasswordService.sendForgetToneNoticeForUnlockPaymentPassword(paymentPasswrodAuthorizeDTO);
	    	return new HashMap<String,String>(){
				private static final long serialVersionUID = 1L;
			    {put("remainTime",vaildeTime);}
			};
	    	
	    }
	    
	    
	    
	   
	    
	    /**
	     * 12.支付密码-忘记密码初始化接口【无原密码】
	     * @param userId 用户名
	     * @param type 返回场景类型
	     * @return json串
	     */
	    @RequestMapping("/app/paymentPassword/resetPaymentPasswordForForgetPasswordIniti.security")
	    @AppController
	    public Object resetPaymentPasswordForForgetPasswordIniti(String userId) {

	    	final String phone = paymentPasswordService.resetPaymentPasswordForForgetPasswordIniti(userId);
	    	return new HashMap<String,String>(){
				private static final long serialVersionUID = 1L;
			    {put("phone",phone);}
			};
	    }
	    
	    
	    /**
	     * 13.支付密码主页初始化接口
	     * @param userId 用户名
	     * @param type 返回场景类型
	     * @return json串
	     */
	    @RequestMapping("/app/paymentPassword/paymentPasswordIniti.security")
	    @AppController
	    public Object paymentPasswordIniti(String userId) {

	    	final String IsSwitch = paymentPasswordService.paymentPasswordIniti(userId);
	    	return new HashMap<String,String>(){
				private static final long serialVersionUID = 1L;
			    {put("IsSwitch",IsSwitch);}
			};
	    }
	   

}
