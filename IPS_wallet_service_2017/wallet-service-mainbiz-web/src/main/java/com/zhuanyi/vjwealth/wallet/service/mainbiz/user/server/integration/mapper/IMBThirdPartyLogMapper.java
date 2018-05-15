package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.ThirdPartyLogDTO;

/**
 * Created by zyx on 2016/7/12.
 */
@Mapper
public interface IMBThirdPartyLogMapper {
    void insert(ThirdPartyLogDTO thirdPartyLogDTO);
    void update(ThirdPartyLogDTO thirdPartyLogDTO);
}
