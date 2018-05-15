package com.zhuanyi.vjwealth.wallet.mobile.product.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fab.core.exception.service.AppException;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IInvestmentOrderService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.account.UserTAccountHomeDTO;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IUserOperationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@Controller
@RequestMapping("/api/v3.1")
public class InvestmentOrderV2Controller {
	
	@Autowired
	private IInvestmentOrderService investmentOrderService;

    /**
     * @author zhangyingxuan
     * @date 20160708
     * T金所页面初始化
     * @param userId
     * @return
     */
    @RequestMapping("/app/account/queryMAccountInfo.security")
    @AppController
    public Object queryTAccountInfo(String userId) {
        UserTAccountHomeDTO userTAccountHomeDTO = investmentOrderService.queryTAccountInfo(userId);
        String productInfo = userTAccountHomeDTO.getProductInfo();
        productInfo = productInfo.replace("$investmentAmount$", userTAccountHomeDTO.getTaAvailableAmount());
        productInfo = productInfo.replace("$allAreadyReceive$", userTAccountHomeDTO.getAllAreadyReceive());
        productInfo = productInfo.replace("$yesterdayReceive$", userTAccountHomeDTO.getYesterdayReceive());
        productInfo = productInfo.replace("$remainingInvestment$", userTAccountHomeDTO.getMaAvailableAmount());
        productInfo = productInfo.replace("$annualYield$", userTAccountHomeDTO.getWeekReceiveRate());
        productInfo = productInfo.replace("$frozenAmount$", userTAccountHomeDTO.getFrozenAmount());
        return JSON.parseObject(productInfo);
    }
    
}
