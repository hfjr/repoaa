package com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper;

import com.fab.server.annotation.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
@Mapper
public interface IWeixinHomeMapper {
//        #1.bannerQueryList()
//        #2.productRecommendList()
//        #3.finacalRecommendList()
//        #4.platformDescribeList()
    public List<Map<String,String>> bannerQueryList();
    public List<Map<String,String>> productRecommendList();
    public List<Map<String,String>> finacalRecommendList();
    public List<Map<String,String>> platformDescribeList();
//    获取帮助中心一级标题
	public List<Map<String,String>> helpCenterTypeWX();
//    获取帮助中心二级标题
	public List<Map<String,String>> helpCenterTypeSubWX();
	
	//查询客服热线
	String queryServiceHotLine();
}
