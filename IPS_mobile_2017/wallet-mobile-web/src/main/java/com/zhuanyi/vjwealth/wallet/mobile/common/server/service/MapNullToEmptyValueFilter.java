package com.zhuanyi.vjwealth.wallet.mobile.common.server.service;

import com.alibaba.fastjson.serializer.ValueFilter;

import java.util.Map;

/**
 * Created by hexy on 16/6/24.
 */
public class MapNullToEmptyValueFilter implements ValueFilter {
    @Override
    public Object process(Object object, String name, Object value) {

        if (null == value && object instanceof Map)
            return ""; // 如果值为null，返回空字符串
        else
            return value;
    }
}
