package com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.constants;

public class PaymentPasswordResultCode {
	
	//1.设置支付密码
	public static final String SET_PASSWORD_SUCCESS="200500";
	public static final String SET_PASSWORD_FAIL="200501";
	
	
	
	//2.关闭支付密码
	public static final String CLOSE_PASSWORD_SUCCESS="200600";//成功
	public static final String CLOSE_PASSWORD_FAIL="200601";//关闭支付密码失败
	
	
	
	
	//3.验证支付密码
	public static final String CHECK_PASSWORD_SUCCESS="200700";//成功
	public static final String CHECK_PASSWORD_FAIL_WRONG="200701";//密码错误，还剩几次机会
	public static final String CHECK_PASSWORD_FAIL_USE_UP="200702";//密码错误，账户被锁
	public static final String CHECK_PASSWORD_FAIL="200703";//其他异常
	
	
	
	//4.重置支付密码
	public static final String RESET_PASSWORD_SUCCESS="200800";//成功
	public static final String RESET_PASSWORD_FAIL="200801";//重置支付密码失败
	
	

	//5.忘记支付密码
	public static final String FORGET_PASSWORD_SUCCESS="200900";//成功
	public static final String FORGET_PASSWORD_FAIL_WRONG="200901";//你还有2次机会
	public static final String FORGET_PASSWORD_FAIL_LOCK="200902";//账户锁定
	public static final String FORGET_PASSWORD_FAIL_INDENTITY_NO_WRONG="200903";//身份证号格式不正确
	public static final String FORGET_PASSWORD_FAIL_CARD_NO_WRONG="200904";//安全卡卡号格式不正确
	public static final String FORGET_PASSWORD_FAIL_CODE_WRONG="200905";//验证码错误
	public static final String FORGET_PASSWORD_FAIL_OTHER="200906";//其他错误
	
	
	
	//5.解锁
	public static final String UNLOCK_PASSWORD_SUCCESS="201000";//成功
	public static final String UNLOCK_PASSWORD_FAIL_WRONG="201001";//你还有2次机会
	public static final String UNLOCK_PASSWORD_FAIL_LOCK="201002";//账户锁定
	public static final String UNLOCK_PASSWORD_FAIL_INDENTITY_NO_WRONG="201003";//身份证号格式不正确
	public static final String UNLOCK_PASSWORD_FAIL_CARD_NO_WRONG="201004";//安全卡卡号格式不正确
	public static final String UNLOCK_PASSWORD_FAIL_CODE_WRONG="201005";//验证码错误
	public static final String UNLOCK_PASSWORD_FAIL_OTHER="201006";//其他错误
	
	
	
	//6.客服解锁
	public static final String CS_UNLOCK_PASSWORD_SUCCESS="201100";//成功
	public static final String CS_UNLOCK_PASSWORD_FAIL="201101";//关闭支付密码失败
		
		
	
	//7.拦截器支付密码校验
	public static final String FILTER_PASSWORD_SUCCESS="201300";//请输入支付密码，你还有3次机会
	public static final String FILTER_PASSWORD_FAIL_CLOSE="201301";//支付密码未启用
	public static final String FILTER_PASSWORD_FAIL_LOCK_BY_CHECK="201302";//账户锁定--提示去解锁(验证支付密码导致锁定)
	public static final String FILTER_PASSWORD_FAIL_LOCKBy_AUTHORIZATION="201303";//账户锁定--提示联系客服(支付密码鉴权失败导致锁定)
	public static final String FILTER_PASSWORD_FAIL_OTHER="201304";//其他错误
	

}
