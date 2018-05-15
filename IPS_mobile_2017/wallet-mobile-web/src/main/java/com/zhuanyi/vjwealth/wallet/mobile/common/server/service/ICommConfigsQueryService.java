package com.zhuanyi.vjwealth.wallet.mobile.common.server.service;

import java.util.Map;

/**
 * Created by tony tang on 2016/4/21 0021.
 */
public interface ICommConfigsQueryService {
   Map<String,String> queryConfigKeyByGroupAndKey(String paramGroup, String paramKey);
   Map<String,String> queryConfigKeyByGroup(String paramGroup);
}
