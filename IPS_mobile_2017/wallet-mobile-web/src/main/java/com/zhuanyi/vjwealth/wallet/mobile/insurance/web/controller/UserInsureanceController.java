package com.zhuanyi.vjwealth.wallet.mobile.insurance.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.insurance.server.service.IInsureanceService;


@Controller
public class UserInsureanceController {

    @Autowired
    private IInsureanceService insureanceService;

    //保险首页
    @RequestMapping("/app/insurance/insuranceIndex.security")
    public String insuranceindex(String userId, String uuid, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("uuid", uuid);
        return insureanceService.hasPICCInsurance(userId) ? "/app/insurance/insurance-lq" : "/app/insurance/insurance-ad";
    }

    //保险详情界面
    @RequestMapping("/app/insurance/insuranceDetail.security")
    public String insuranceDetail(String userId, String uuid, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("uuid", uuid);
        return insureanceService.hasPICCInsurance(userId) ? "/app/insurance/insurance-lq" : "/app/insurance/insurance-ad";
    }


    //查询是否购买过保险
    @RequestMapping("/app/insurance/hasPICCInsurance.security")
    @AppController
    public Object hasPICCInsurance(String userId) {
        Map<String, String> res = new HashMap<String, String>();
        res.put("hasPICCInsurance", insureanceService.hasPICCInsurance(userId) ? "yes" : "no.security");
        return res;
    }


    //查询保险信息
    @RequestMapping("/app/insurance/queryPICCInsuranceInfo.security")
    @AppController
    public Object queryPICCInsuranceInfo(String userId) {

        return insureanceService.queryPICCInsuranceInfo(userId);
    }

    //购买保险
    @RequestMapping("/app/insurance/buyPICCInsurance.security")
    @AppController
    public Object buyPICCInsurance(String userId) {
        insureanceService.buyPICCInsurance(userId);
        return null;
    }

    //查询保险信息
    @RequestMapping("/app/insurance/queryV2PICCInsuranceInfo.security")
    @AppController
    public Object queryV2PICCInsuranceInfo(String userId, String userChannelType) {
        return insureanceService.queryV2PICCInsuranceInfo(userId, userChannelType);
    }

}
