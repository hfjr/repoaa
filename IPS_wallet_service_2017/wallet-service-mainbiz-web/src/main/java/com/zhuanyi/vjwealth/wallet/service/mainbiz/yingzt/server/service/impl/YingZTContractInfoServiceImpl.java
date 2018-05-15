package com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.service.impl;

import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.dto.YingZTContractInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.integration.mapper.YingZTContractInfoMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.service.IYingZTContractInfoService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yi on 16/3/16.
 */
@Service
public class YingZTContractInfoServiceImpl implements IYingZTContractInfoService {

    @Autowired
    private YingZTContractInfoMapper yingZTContractInfoMapper;

    @Autowired
    private ISequenceService sequenceService;

    public void saveContractInfo(String userId, String orderNo, String productId) {
        BaseLogger.audit(String.format("保存合同开始 - userId:[%s],orderNo:[%s],productId:[%s],",userId,  orderNo, productId));
        //验证参数
        if (StringUtils.isBlank(userId) ||  StringUtils.isBlank(orderNo)  || StringUtils.isBlank(productId) ){
            throw new AppException("保存合同请求参数不合法");
        }

        //产品类型
        String productType = yingZTContractInfoMapper.queryProductTypeByProductId(productId);
        BaseLogger.audit(String.format("产品类型 - productType :[%s]",productType));

        //生成合同编号
        String contractNo = getContractNo(StringUtils.stripToEmpty(productType) , ISequenceService.SEQ_NAME_CONTRACT_SEQ) ;

        YingZTContractInfoDTO yingZTContractInfoDTO = new YingZTContractInfoDTO(userId,orderNo,productId,contractNo,userId,null);

        yingZTContractInfoDTO.setContractNo(contractNo);
        yingZTContractInfoMapper.saveContractInfo(yingZTContractInfoDTO);

        BaseLogger.audit(String.format("保存合同结束 - contractNo :[%s]",contractNo));

    }

    private String getContractNo(String prefix, String sequenceName) {
        return prefix + new SimpleDateFormat("yyyyMMdd").format(new Date()) + sequenceService.getNextStringValue(sequenceName, 10);
    }
}
