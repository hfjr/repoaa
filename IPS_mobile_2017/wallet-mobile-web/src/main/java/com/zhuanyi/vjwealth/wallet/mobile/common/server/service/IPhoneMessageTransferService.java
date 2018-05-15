package com.zhuanyi.vjwealth.wallet.mobile.common.server.service;

import java.util.Map;


/**
 * 手机短信接口
 * Created by ce 
 */
public interface IPhoneMessageTransferService {

    /**
     * 发送手机短信
     * @param jsonData
     */
	Map<String, Object> sendPhoneMessage(String jsonData);

    

}