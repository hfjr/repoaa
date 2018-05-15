package com.zhuanyi.vjwealth.wallet.mobile.hlb.server.service;

import java.util.Map;

public interface IHLBWeiXinService {

    //    微信帮助中心
    public Map<String, Object> helpCenterInit();

    //帮助中心服务热线
    public Object getServiceHotline();

    Map queryMyAssets(String userId);

    Map<String,Object> getBillListByUserIdAndType(String userId,String type,String page);

    Map queryProductListV37(String userId, String uuid, String page, String userChannelType);

    Map<String, Object> queryWeiXinHome();

    public Object queryShareQRCodePic(String userId);

    public Object queryWeiXinShareInfo(String userId);
}
