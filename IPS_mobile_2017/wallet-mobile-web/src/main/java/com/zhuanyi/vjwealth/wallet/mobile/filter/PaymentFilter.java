package com.zhuanyi.vjwealth.wallet.mobile.filter;

import com.fab.core.logger.BaseLogger;
import com.fab.core.util.ControllerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.constants.PaymentPasswordResultCode;
import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.dto.PaymentPasswordDTO;
import com.zhuanyi.vjwealth.wallet.mobile.paymentpassword.server.service.IPaymentPasswordService;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IUserOperationService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;

/**
 *  支付密码拦截器
 * Created by yi on 16/4/15.
 */
public class PaymentFilter implements Filter {

    @Autowired
    private IUserOperationService userOperationService;

    @Autowired
    private IPaymentPasswordService paymentPasswordService;

    public void init(FilterConfig filterConfig) throws ServletException {

        //初始化数据
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        userOperationService = (IUserOperationService) ctx.getBean("userOperationService");

        paymentPasswordService = (IPaymentPasswordService) ctx.getBean("paymentPasswordService");


        BaseLogger.info("wallet-app PaymentFilter init...ok");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    	BaseLogger.audit("PaymentFilter-->start");
        String userId = request.getParameter("userId");
        String loginUuid = request.getParameter("uuid");

        //1.授权信息判断
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(loginUuid) || !userOperationService.validatorUserIdAndUuid(userId, loginUuid)) {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.print(new ObjectMapper().writeValueAsString(ControllerUtils.getErrorResponseWithKeyCodeAndMessageBody("610", "请重新登录", null)));
            pw.flush();
            return;
        }

        String paymentPassword = request.getParameter("paymentPassword");
        String FirstResponseCode = request.getParameter("FirstResponseCode");//第二次请求中，获取第一次请求的相应码
        PaymentPasswordDTO  paymentPasswordStatusDTO=  paymentPasswordService.queryPaymentPasswordStatus(userId);
        //2.支付密码启用判断【 是否启用】

          
         if(StringUtils.isBlank(paymentPassword)){
                 //2.1如果是未启用支付密码，且有第一次请求的相应码，则可以通过
	        	if(StringUtils.equals(PaymentPasswordResultCode.FILTER_PASSWORD_FAIL_CLOSE, paymentPasswordStatusDTO.getCode()) &&
	        			StringUtils.equals(PaymentPasswordResultCode.FILTER_PASSWORD_FAIL_CLOSE, FirstResponseCode)){
	        		    chain.doFilter(request, response);
	        		    return;
	        	}else{
	        		//2.2支付密码未启用
	                response.setContentType("application/json;charset=UTF-8");
	                PrintWriter pw = response.getWriter();
	                pw.print(new ObjectMapper().writeValueAsString(ControllerUtils.getSuccessfulResponse( paymentPasswordStatusDTO)));
	                pw.flush();
	                return;
	        	}

          } else {

                //3.1支付密码锁定判断- 支付密码验证锁定场景
	            //3.2支付密码锁定判断 - 解锁,表单验证锁定场景
	             if (StringUtils.equals(PaymentPasswordResultCode.FILTER_PASSWORD_FAIL_LOCK_BY_CHECK, paymentPasswordStatusDTO.getCode()) 
	            		 || StringUtils.equals(PaymentPasswordResultCode.FILTER_PASSWORD_FAIL_LOCKBy_AUTHORIZATION, paymentPasswordStatusDTO.getCode())) {
	
	                    response.setContentType("application/json;charset=UTF-8");
	                    PrintWriter pw = response.getWriter();
	                    pw.print(new ObjectMapper().writeValueAsString(ControllerUtils.getSuccessfulResponse(paymentPasswordStatusDTO)));
	                    pw.flush();
	                    return;
	                }
	
	                PaymentPasswordDTO paymentPasswordDTO = paymentPasswordService.checkPaymentPassword(userId, paymentPassword);
	                // 4.1支付密码信息判断【 锁定】 //200702 支付密码模块，验证支付密码失败,账户已经锁定
	                // 4.2支付密码信息判断【 正常 - 错误】 //200701 支付密码模块，验证支付密码失败，你还有几次机会
	                if (!StringUtils.equals(PaymentPasswordResultCode.CHECK_PASSWORD_SUCCESS, paymentPasswordDTO.getCode())) {
	                    response.setContentType("application/json;charset=UTF-8");
	                    PrintWriter pw = response.getWriter();
	                    pw.print(new ObjectMapper().writeValueAsString(ControllerUtils.getSuccessfulResponse(paymentPasswordDTO)));
	                    pw.flush();
	                    return;
	                }
          }


        // 5.支付密码信息判断【 正常- 正确】 or 支付密码密码不启用
        chain.doFilter(request, response);
    }


    public void destroy() {

    }
}
