package com.zhuanyi.vjwealth.wallet.mobile.filter;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.fab.core.logger.BaseLogger;
import com.fab.core.util.ControllerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IUserOperationService;

public class UserLoginFilter implements Filter {

    @Autowired
    private IUserOperationService userOperationService;
  
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (isMultipart((HttpServletRequest) request)) {
        	
            // process the uploaded file
        } else {
        	JSON.toJSONString(request.getParameterMap());
            String userId = request.getParameter("userId");
            String loginUuid = request.getParameter("uuid");
            BaseLogger.audit("userId");
            BaseLogger.audit("loginUuid");
            if (StringUtils.isBlank(userId) || StringUtils.isBlank(loginUuid) || !userOperationService.validatorUserIdAndUuid(userId, loginUuid)) {
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter pw = response.getWriter();
                pw.print(new ObjectMapper().writeValueAsString(ControllerUtils.getErrorResponseWithKeyCodeAndMessageBody("610", "请重新登录", null)));
                pw.flush();
                return;
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {

        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        userOperationService = (IUserOperationService) ctx.getBean("userOperationService");
        BaseLogger.info("wallet-app UserLoginFilter init...ok");
    }

    public void destroy() {

    }

    public boolean isMultipart(HttpServletRequest request) {
        return (request != null && ServletFileUpload.isMultipartContent(request));
    }

}
