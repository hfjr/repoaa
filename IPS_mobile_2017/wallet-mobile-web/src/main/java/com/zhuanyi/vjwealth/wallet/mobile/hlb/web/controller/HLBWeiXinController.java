package com.zhuanyi.vjwealth.wallet.mobile.hlb.web.controller;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.hlb.server.service.IHLBWeiXinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HLBWeiXinController {
    @Autowired
    private IHLBWeiXinService hlbWeiXinService;

    @RequestMapping("/api/weixin/helpCenterInit")
    public String helpCenterInit(Model model) {
        model.addAttribute("helpCenter", hlbWeiXinService.helpCenterInit());
        model.addAttribute("serviceHotline", hlbWeiXinService.getServiceHotline());
        return "app/help/helpCenterWithDb";
    }

    @RequestMapping("/api/personalCenter/queryMyAssets.security")
    @AppController
    public Object queryMyAssets(String userId) {
        return hlbWeiXinService.queryMyAssets(userId);
    }

    // 9.2根据账单类型获取账单列表
    @RequestMapping("/api/bill/queryBillList.security")
    @AppController
    public Object queryBillList(String userId, String billType, String page) {
        return hlbWeiXinService.getBillListByUserIdAndType(userId, billType, page);
    }

    @RequestMapping("/api/product/queryV2ProductList")
    @AppController
    public Object queryProductList(String userId, String uuid, String page, String userChannelType) {
        return hlbWeiXinService.queryProductListV37(userId, uuid, page, userChannelType);
    }

    @RequestMapping("/api/weixin/weixinHomeInit")
    @AppController
    public Object weixinHomeInit() {
        return hlbWeiXinService.queryWeiXinHome();
    }

    @RequestMapping("/api/user/queryHYBInviteQRCodePic.security")
    @AppController
    public Object queryHYBInviteQRCodePic(String userId) {
        return hlbWeiXinService.queryShareQRCodePic(userId);
    }

    @RequestMapping("/api/user/queryHYBWeiXinShareInfo.security")
    @AppController
    public Object queryHYBWeiXinShareInfo(String userId) {
        return hlbWeiXinService.queryWeiXinShareInfo(userId);
    }
}
