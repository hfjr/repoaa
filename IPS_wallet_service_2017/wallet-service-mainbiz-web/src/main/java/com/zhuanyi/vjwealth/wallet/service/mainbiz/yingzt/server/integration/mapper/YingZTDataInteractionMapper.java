package com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.integration.mapper;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.dto.YingZTDataInteractionDTO;

@Mapper
public interface YingZTDataInteractionMapper {

    /**
     * @param yingZTDataInteractionDTO
     * @return void
     * @Description: 新增小赢交互接口日志记录
     */
    void insertYingZTDataInteraction(YingZTDataInteractionDTO yingZTDataInteractionDTO);

}
