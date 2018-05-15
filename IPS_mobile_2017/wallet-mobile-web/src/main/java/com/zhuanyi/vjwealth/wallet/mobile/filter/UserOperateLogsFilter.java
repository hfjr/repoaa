package com.zhuanyi.vjwealth.wallet.mobile.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.mobile.common.dto.UserLogsDTO;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.UserVistorLogServer;

public class UserOperateLogsFilter implements Filter {

    
    private UserVistorLogServer userVistorLogServer;
    
  
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (!isMultipart((HttpServletRequest) request)) {
        	//用户访问日志
    		UserLogsDTO userLogsDTO=new UserLogsDTO();
    		//请求参数,消息头,开始时间
    		userLogsDTO.parseRequestParam((HttpServletRequest)request);
    		try{
    			chain.doFilter(request, response);
    		}catch(Exception ex){
    			BaseLogger.error("【UserOperateLogsFilter】用户调用接口异常："+ex);
    			userLogsDTO.setExceptionMeesage(JSON.toJSONString(ex));
    		}
    		//计算接口耗时时间
    	    userLogsDTO.setConsumingTime(String.valueOf(System.currentTimeMillis()-userLogsDTO.getStartTime()));
    		//2. 保存数据库中
    		userVistorLogServer.userRequestLog(userLogsDTO);
    	    return;
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        userVistorLogServer = (UserVistorLogServer) ctx.getBean("userVistorLogServer");
        BaseLogger.info("wallet-app UserOperateLogsFilter init...ok");
    }

    public void destroy() {

    }

    public boolean isMultipart(HttpServletRequest request) {
        return (request != null && ServletFileUpload.isMultipartContent(request));
    }

}
