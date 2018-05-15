package com.zhuanyi.vjwealth.wallet.mobile.common.server.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public interface IWeixinHomeService {
//        #1.bannerQueryList()
//        #2.productRecommendList()
//        #3.finacalRecommendList()
//        #4.platformDescribeList()
    public List<Map<String, String>> bannerQueryList();

    public List<Map<String, String>> productRecommendList();

    public List<Map<String, String>> finacalRecommendList();

    public List<Map<String, String>> platformDescribeList();

    public Object weixinHomeInit();

//    微信帮助中心
	public Map<String,Object> helpCenterInit();

	public Object getServiceHotline();
}
