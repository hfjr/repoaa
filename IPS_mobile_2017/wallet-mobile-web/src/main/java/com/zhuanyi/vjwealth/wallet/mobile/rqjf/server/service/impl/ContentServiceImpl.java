package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.impl;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.MapperUtils;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper.IContentMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IContentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hexy on 2017/9/6.
 */
@Service
public class ContentServiceImpl implements IContentService {


    @Autowired
    private IContentMapper contentMapper;

    @Override
    public List<Map<String, Object>> queryList(String page, String channelIds, String contentSequence) {

        if (StringUtils.isBlank(page)) {
            throw new AppException("查询图片列表，页码参数不能为空");
        }
        if (!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1 || Integer.parseInt(page) % 1 > 0) {
            throw new AppException("查询图片列表，页码数值不合法，必须为大于0的整数");
        }
        Integer currentPage = (Integer.parseInt(page) - 1) * 10;

        List<Map<String, Object>> list = contentMapper.queryList(currentPage, MapperUtils.splitQueryCondition(channelIds), contentSequence);

        return list;
//
//        Map<String,Object> returnMap = new HashMap<String,Object>();
//
//        returnMap.put("records", list);
//        returnMap.put("isMore", false);
//        if(list!=null && list.size()>=10){
//            returnMap.put("isMore", true);
//        }
//
//        return returnMap;

    }
}
