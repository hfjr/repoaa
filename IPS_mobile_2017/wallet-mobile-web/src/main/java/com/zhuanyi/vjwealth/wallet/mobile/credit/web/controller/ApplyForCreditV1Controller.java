package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.ILoanBizService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.FundLoanShareStatisticsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hexy on 16/5/12.
 */
@Controller
@RequestMapping("/api/v3.3.1")
public class ApplyForCreditV1Controller {
    @Autowired
    private ILoanBizService loanBizService;
    /**
     * 1.申请额度初始化
     *
     * @param userId
     * @param productTypeCode
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/applyForCredit/creditApplicationIniti.security")
    @AppController
    public Object creditApplicationInit(String userId,String productTypeCode) {
        if (StringUtils.isEmpty(userId)) {
            throw new AppException("用户编号不能为空");
        }
        return loanBizService.creditApplicationInit(userId,productTypeCode);
    }
    /**
     * 10.申请额度-完善个人信息初始化
     *
     * @param userId
     * @return
     * @since 3.3.1
     */
    @RequestMapping("/app/credit/applyForCredit/improvePersonalInformationIniti.security")
    @AppController
    public Object improvePersonalInformationIniti(String userId,String productTypeCode) {
        if (StringUtils.isEmpty(userId)) {
            throw new AppException("用户编号不能为空");
        }
        if (StringUtils.isEmpty(productTypeCode)) {
            throw new AppException("产品编号为空");
        }
        return loanBizService.improvePersonalInformationIniti(userId,productTypeCode);
    }

    @RequestMapping("/app/credit/applyForCredit/shareStatistics")
    @AppController
    public Object fundLoanShareStatistics(FundLoanShareStatisticsDTO dto, HttpServletRequest request) {
        dto.setIp(getIpAddr(request));
        return loanBizService.fundLoanShareStatistics(dto);
    }

    public String getIpAddr(HttpServletRequest request) {
        try {
            String ip = request.getHeader("X-Real-IP");
            if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
            ip = request.getHeader("X-Forwarded-For");
            if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                // 多次反向代理后会有多个IP值，第一个为真实IP。
                int index = ip.indexOf(',');
                if (index != -1) {
                    return ip.substring(0, index);
                } else {
                    return ip;
                }
            } else {
                return request.getRemoteAddr();
            }
        } catch (Exception e) {
            BaseLogger.error(e.getMessage());
        }
        return "";
    }
}
