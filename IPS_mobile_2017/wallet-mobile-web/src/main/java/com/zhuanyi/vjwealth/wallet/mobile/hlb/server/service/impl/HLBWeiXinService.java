package com.zhuanyi.vjwealth.wallet.mobile.hlb.server.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.IBillTemplateService;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.dto.BillListQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.enums.BillListQueryTypeEnum;
import com.zhuanyi.vjwealth.wallet.mobile.bill.server.impl.support.BillTemplateQueryFactory;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ICommConfigsQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.BizCommonConstant;
import com.zhuanyi.vjwealth.wallet.mobile.hlb.server.mapper.IHLBWeiXinMapper;
import com.zhuanyi.vjwealth.wallet.mobile.hlb.server.service.IHLBWeiXinService;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.MyAssetsDTO;
import com.zhuanyi.vjwealth.wallet.mobile.index.server.dto.WjActivityInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IProductQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.mapper.IUserAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.IUserQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IUserOperationService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserShareInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserInviteService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HLBWeiXinService implements IHLBWeiXinService {

    @Autowired
    private IHLBWeiXinMapper hlbWeiXinMapper;

    @Autowired
    private ICommConfigsQueryService commConfigsQueryService;

    @Autowired
    private IUserAccountQueryMapper userAccountQueryMapper;

    @Autowired
    private BillTemplateQueryFactory billQueryFactory;

    @Autowired
    private IProductQueryService productQueryService;

    @Autowired
    private IUserOperationService userOperationService;

    @Autowired
    private IUserQueryMapper userQueryMapper;

    @Remote
    private IMBUserInviteService mbUserInviteService;

    //    帮助中心初始化
    @Override
    public Map<String, Object> helpCenterInit() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> body = new HashMap<>();
        Map<String, Object> footer = new HashMap<>();
        try {
            body.put("helpCenterTypeWX", hlbWeiXinMapper.helpCenterTypeWX());
            body.put("helpCenterTypeSubWX", hlbWeiXinMapper.helpCenterTypeSubWX());
            footer.put("status", "200");
        } catch (Exception e) {
            footer.put("status", "600");
        }
        map.put("body", body);
        map.put("footer", footer);
        return map;

    }

    @Override
    public Object getServiceHotline() {
        String hotline = hlbWeiXinMapper.queryServiceHotLine();
        if (StringUtils.isBlank(hotline)) {
            BaseLogger.audit("服务热线为空,请在wj_mobile_variable_info添加变量");
            return new HashMap<String, String>();
        }
        return JSONObject.parseObject(hotline);
    }

    @Override
    public Map queryMyAssets(String userId) {
        Map paramMap = commConfigsQueryService.queryConfigKeyByGroup("wallet_assets");
        Map returnMap = new HashMap();
        List<MyAssetsDTO> myAssetsDTOList = hlbWeiXinMapper.queryMyAssets(userId);
        DecimalFormat df = new DecimalFormat("#######0.00");
        Double totalAmount = 0d;
        for (MyAssetsDTO dto : myAssetsDTOList) {
            totalAmount += Double.parseDouble(dto.getAmount());
            dto.setIcon(paramMap.get(dto.getIcon()) + "");
            dto.setColor(paramMap.get(dto.getColor()) + "");
        }
        returnMap.put("totalAmount", df.format(totalAmount));
        returnMap.put("items", myAssetsDTOList);
        return returnMap;
    }

    @Override
    public Map<String, Object> getBillListByUserIdAndType(String userId, String type, String page) {
        if (BillListQueryTypeEnum.EA.getValue().equals(type) || BillListQueryTypeEnum.TA.getValue().equals(type) || BillListQueryTypeEnum.BANKCARDWITHHOLD.getValue().equals(type)) {
            throw new AppException("查询账单列表，账单类型不正确");
        }
        //1.校验参数
        validatorParams(userId, type, page);

        List<BillListQueryDTO> billList = null;

        //以下订单类型重写（去除存钱罐相关订单）
        if (BillListQueryTypeEnum.ALL.getValue().equals(type)) {
            billList = hlbWeiXinMapper.getAllBillListByUserIdAndType(userId, (Integer.parseInt(page) - 1) * 10);
        } else if (BillListQueryTypeEnum.INCOME.getValue().equals(type)) {
            billList = hlbWeiXinMapper.getIncomeBillListByUserIdAndType(userId, (Integer.parseInt(page) - 1) * 10);
        } else if (BillListQueryTypeEnum.WITHDRAW.getValue().equals(type)) {
            billList = hlbWeiXinMapper.getWithdrawBillListByUserIdAndType(userId, (Integer.parseInt(page) - 1) * 10);
        } else if (BillListQueryTypeEnum.FROZENALL.getValue().equals(type)) {
            billList = hlbWeiXinMapper.getFrozenAllBillListByUserIdAndType(userId, (Integer.parseInt(page) - 1) * 10);
        }
        if (billList != null) {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("records", billList);
            returnMap.put("isMore", false);
            if (billList != null && billList.size() >= 10) {
                returnMap.put("isMore", true);
            }
            return returnMap;
        }

        IBillTemplateService instance = billQueryFactory.getBillListServiceInstance(type);
        return instance.getBillList(userId, page);
    }

    private void validatorParams(String userId, String type, String page) {
        if (StringUtils.isBlank(userId)) {
            throw new AppException("查询账单列表，用户ID不能为空");
        }
        if (StringUtils.isBlank(type)) {
            throw new AppException("查询账单列表，账单类型不能为空");
        }
        if (StringUtils.isBlank(page)) {
            throw new AppException("查询账单列表，页码不能为空");
        }
        if (!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1 || Integer.parseInt(page) % 1 > 0) {
            throw new AppException("查询账单列表，页码数值不合法，必须为大于0的整数");
        }
    }

    @Override
    public Map queryProductListV37(String userId, String uuid, String page, String userChannelType) {
        if (!StringUtils.isBlank(userId) && !StringUtils.isBlank(uuid)) {
            if (!userOperationService.validatorUserIdAndUuid(userId, uuid)) {
                throw new AppException(610, "请重新登录");
            }
        }
        Map returnMap =null;// productQueryService.queryProductListV6(userId, uuid, page, null);

        Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CONFIG_GROUP_WALLET_MOBILE);
        Map<String, String> currentMap = new HashMap<>();
        currentMap.put("currentIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/icon/current_financing.png");
        currentMap.put("productName", "存钱罐");
        currentMap.put("investmentType", "活期");
        currentMap.put("startInvestmentDesc", "起投金额");
        currentMap.put("startInvestmentMoney", "100");
        currentMap.put("yearRateDesc", "年化收益");
        currentMap.put("yearRate", userAccountQueryMapper.queryTaReceiveRate() + "%");
        currentMap.put("code", "204907");

        return returnMap;
    }

    @Override
    public Map<String, Object> queryWeiXinHome() {
        Map<String, Object> returnMap = new HashMap<String, Object>();

//        returnMap.put("bannerList", weixinHomeMapper.bannerQueryList());

        Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CONFIG_GROUP_WALLET_MOBILE);

        Map loanConfigMap = commConfigsQueryService.queryConfigKeyByGroupAndKey("house_fund_loan", null);

        Map loanMap = new HashMap();
        loanMap.put("loanProductName", "公积金贷");
        //微信端要求文字
        loanMap.put("maxLoanAmountIcon", "最高50000元");
        String newIconUrl = "";
        if ("yes".equals(loanConfigMap.get("house_fund_loan_is_new"))) {
            newIconUrl = commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + loanConfigMap.get("house_fund_loan_new_icon");
        }
        loanMap.put("newIcon", newIconUrl);
        loanMap.put("loanMinRate", loanConfigMap.get("house_fund_loan_day_rate"));
        loanMap.put("loanPeriod", "借款周期" + loanConfigMap.get("house_fund_loan_period") + "期");
        loanMap.put("buttonText", "去贷款");
        loanMap.put("loanActivityIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/loan_activity.png");

        //设置贷款活动区信息
        returnMap.put("loanActivity", loanMap);


        returnMap.put("latestActivityLabel", "最新活动");
        returnMap.put("latestActivityIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/latest_activity.png");
        returnMap.put("latestActivityList", queryWeiXinHomeActivityList(commConfigMap));

//        returnMap.put("latestDynamicLabel", "最新动态");
        //最新动态（获取最新3条）
//        returnMap.put("latestDynamicIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/latest_dynamic.png");
//        JSONArray jsonArray = com.alibaba.fastjson.JSON.parseArray(com.alibaba.fastjson.JSON.toJSONString(((Map) queryDynamicList(null, null, "0")).get("records")));
//        JSONArray top3JsonArray = new JSONArray();
//        for (int i = 0; i < 3; i++) {
//            Object object = jsonArray.get(i);
//            if (object != null) {
//                top3JsonArray.add(object);
//            }
//        }
//        returnMap.put("latestDynamicList", top3JsonArray);

        Map financialConfigMap = commConfigsQueryService.queryConfigKeyByGroupAndKey("financial", "financial_new_hand_icon");

        Map financialMap = new HashMap();
        financialMap.put("financialLabel", "定期理财");
        financialMap.put("financialTipIcon", "百元起投");
        financialMap.put("financialIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/financial.png");
        Map productMap = userAccountQueryMapper.queryNewHandFinancialReceiveRate();
        financialMap.put("rate", formatRate(productMap.get("rate") + ""));
        financialMap.put("desc", "年化收益");
        financialMap.put("newHandIcon", productMap.get("productFlag").equals("greenhorn") ? commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + financialConfigMap.get("financial_new_hand_icon") : null);
        financialMap.put("buttonText", "去理财");
        financialMap.put("code", "204904");
        financialMap.put("productId", productMap.get("productId"));
        financialMap.put("productName", productMap.get("productName"));

        returnMap.put("financialArea", financialMap);

//        Map salaryPlanMap = new HashMap();
//
//        salaryPlanMap.put("salaryPlanLabel", "工资计划");
//        salaryPlanMap.put("salaryPlanTipCon", "定存工资");
//        salaryPlanMap.put("salaryPlanIcon", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL) + "/static/index/salary_plan_sd.png");
//        salaryPlanMap.put("rate", userAccountQueryMapper.queryTaReceiveRate());
//        salaryPlanMap.put("desc", "近7日年化收益");
//        salaryPlanMap.put("buttonText", "去定制");
//
//        returnMap.put("salaryPlanArea", salaryPlanMap);
        return returnMap;
    }

    private String formatRate(String rate) {
        if (rate.indexOf(".") > 0) {
            //正则表达
            rate = rate.replaceAll("0+?$", "");//去掉后面无用的零
            rate = rate.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return rate;
    }

    private List<WjActivityInfoDTO> queryWeiXinHomeActivityList(Map<String, String> commConfigMap) {
        List<WjActivityInfoDTO> records = hlbWeiXinMapper.queryAppHomeActivityList();
        List<WjActivityInfoDTO> recordsList = new ArrayList<WjActivityInfoDTO>();
        for (WjActivityInfoDTO dto : records) {
            if (!StringUtils.isBlank(dto.getWeixinPicUrl())) {
                dto.setSmallPic(MessageFormat.format(dto.getWeixinPicUrl(), commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)));
            }
            if (!StringUtils.isBlank(dto.getWeixinLinkUrl())) {
                dto.setDetailUrl(dto.getWeixinLinkUrl());
            }
            dto.setSmallPic(MessageFormat.format(dto.getSmallPic(), commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)));
            dto.setDetailUrl(MessageFormat.format(dto.getDetailUrl(), commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)));
            recordsList.add(dto);
        }
        return recordsList;
    }

    @Override
    public Object queryShareQRCodePic(String userId) {
        return mbUserInviteService.queryHLBInviteQRCodePic(userId);
    }

    @Override
    public Object queryWeiXinShareInfo(String userId) {
        return mbUserInviteService.queryHLBWeiXinShareInfo(userId);
    }
}
