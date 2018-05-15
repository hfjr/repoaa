package com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.service.impl;

import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.dto.YingZTDataInteractionDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.integration.mapper.YingZTDataInteractionMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.service.IYingZTDataInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Created by yi on 16/2/29.
 */
@Service
public class YingZTDataInteractionServiceImpl implements IYingZTDataInteractionService {

    @Autowired
    private YingZTDataInteractionMapper yingZTDataInteractionMapper;

    public void insertYingZTDataInteraction(String methodName, String sendJsonStr, String returnJsonStr) {

        try {
            YingZTDataInteractionDTO yingZTDataInteractionDTO = new YingZTDataInteractionDTO("yingzt", "VJWEALTH", methodName, sendJsonStr, returnJsonStr);
            yingZTDataInteractionMapper.insertYingZTDataInteraction(yingZTDataInteractionDTO);
            BaseLogger.audit("YingZTDataInteractionServiceImpl 接口交互日志记录成功, yingZTDataInteractionDTO : "+ yingZTDataInteractionDTO);
        } catch (Exception e) {
            BaseLogger.error("YingZTDataInteractionServiceImpl 接口交互日志记录失败", e);
        }
    }
}
