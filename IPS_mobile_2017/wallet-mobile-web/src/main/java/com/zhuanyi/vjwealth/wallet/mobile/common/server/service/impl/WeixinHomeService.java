package com.zhuanyi.vjwealth.wallet.mobile.common.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper.IWeixinHomeMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.IWeixinHomeService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
@Service
public class WeixinHomeService implements IWeixinHomeService {
    @Autowired
    private IWeixinHomeMapper weixinHomeMapper;
    @Override
    public List<Map<String,String>> bannerQueryList() {
       return weixinHomeMapper.bannerQueryList();
    }

    @Override
    public List<Map<String, String>> productRecommendList() {

        return weixinHomeMapper.productRecommendList();
    }

    @Override
    public List<Map<String, String>> finacalRecommendList() {

        return weixinHomeMapper.finacalRecommendList();
    }

    @Override
    public List<Map<String, String>> platformDescribeList() {

        return weixinHomeMapper.platformDescribeList();
    }

    public Object weixinHomeInit() {
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> body = new HashMap<>();
        Map<String,Object> footer = new HashMap<>();
        try {
        	body.put("bannerQueryList",weixinHomeMapper.bannerQueryList());
            body.put("productRecommendList",weixinHomeMapper.productRecommendList());
            body.put("finacalRecommendList",weixinHomeMapper.finacalRecommendList());
            body.put("platformDescribeList",weixinHomeMapper.platformDescribeList());
            footer.put("status","200");
		} catch (Exception e) {
			footer.put("status","600");
		}
        map.put("body",body);
        map.put("footer",footer);
        return map;
    }

    
//    帮助中心初始化
	@Override
	public Map<String, Object> helpCenterInit() {
		Map<String,Object> map = new HashMap<>();
		Map<String,Object> body = new HashMap<>();
        Map<String,Object> footer = new HashMap<>();
        try {
        	body.put("helpCenterTypeWX", weixinHomeMapper.helpCenterTypeWX());
            body.put("helpCenterTypeSubWX", weixinHomeMapper.helpCenterTypeSubWX());
            footer.put("status","200");
		} catch (Exception e) {
			footer.put("status","600");
		}
        map.put("body",body);
        map.put("footer",footer);
		return map;
		
	}

    @Override
    public Object getServiceHotline() {
        String hotline = weixinHomeMapper.queryServiceHotLine();
        if (StringUtils.isBlank(hotline)) {
            BaseLogger.audit("服务热线为空,请在wj_mobile_variable_info添加变量");
            return new HashMap<String, String>();
        }
        return JSONObject.parseObject(hotline);
    }
	
	
}
