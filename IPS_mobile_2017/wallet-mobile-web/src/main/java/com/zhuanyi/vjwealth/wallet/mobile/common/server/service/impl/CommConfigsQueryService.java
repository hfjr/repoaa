package com.zhuanyi.vjwealth.wallet.mobile.common.server.service.impl;

import com.zhuanyi.vjwealth.loan.common.dto.CommConfigsDTO;

import com.zhuanyi.vjwealth.loan.constant.BizConstant;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.mapper.ICommConfigsMapper;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ICommConfigsQueryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommConfigsQueryService implements ICommConfigsQueryService {
    @Autowired
    private ICommConfigsMapper commParamMapper;

    /**
     * 查询字段表
     * @param paramGroup
     * @param paramKeys 多个参数用"|"号分割
     * @return
     */
    @Override
    public Map<String, String> queryConfigKeyByGroupAndKey(String paramGroup,String paramKeys) {
        List<String> paramKeyList=null;
        if(StringUtils.isNotEmpty(paramKeys)){
            paramKeyList=new ArrayList<>();
            for(String str:paramKeys.split(BizConstant.SPLIT_CHAR)){
                paramKeyList.add(str);
            }
        }
        CommConfigsDTO commParamDTO=new CommConfigsDTO(paramGroup,paramKeyList);
        List<CommConfigsDTO> list=commParamMapper.queryCommConfigsByGroupAndKey(commParamDTO);
        Map<String,String> dataMap=new HashMap<>();
        for(CommConfigsDTO dto:list){
            dataMap.put(dto.getParamKey(),dto.getParamValue());
        }
        return dataMap;
    }

    @Override
    public Map<String, String> queryConfigKeyByGroup(String paramGroup) {
        return queryConfigKeyByGroupAndKey(paramGroup,null);
    }
}
