package com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.integration.mapper;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.dto.YingZTContractInfoDTO;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface YingZTContractInfoMapper {

    /**
     * @param yingZTContractInfoDTO
     * @return void
     * @Description: 新增合同信息
     */
    void saveContractInfo(YingZTContractInfoDTO yingZTContractInfoDTO);


    /**
     * 查询产品类型编号
     * @param productId
     * @return
     */
    String queryProductTypeByProductId(@Param("productId")String productId);

}
