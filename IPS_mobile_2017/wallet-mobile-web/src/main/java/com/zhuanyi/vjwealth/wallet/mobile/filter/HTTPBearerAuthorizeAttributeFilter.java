package com.zhuanyi.vjwealth.wallet.mobile.filter;

/**
 * Created by hexy on 2016/12/14.
 */

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fab.core.util.ControllerUtils;

import com.zhuanyi.vjwealth.loan.oauth.jwt.ITokenGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 使用jwt认证的filter
 */
public class HTTPBearerAuthorizeAttributeFilter implements Filter {

    @Autowired
    private ITokenGenerate tokenGenerate;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String auth = httpRequest.getHeader("Authorization");

        // 验证Service调用
        if (tokenGenerate.validToken(auth)) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.setStatus(HttpServletResponse.SC_OK);

        ObjectMapper mapper = new ObjectMapper();

        httpResponse.getWriter().write(mapper.writeValueAsString(ControllerUtils.getErrorResponseWithKeyCodeAndMessageBody("700",  "token验证失败", null)));
        return;
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }
}
