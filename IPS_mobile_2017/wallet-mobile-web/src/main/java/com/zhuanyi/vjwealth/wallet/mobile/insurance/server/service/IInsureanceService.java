package com.zhuanyi.vjwealth.wallet.mobile.insurance.server.service;

import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserInsuranceDTO;

import java.util.Map;

/**
 * 保险类接口
 *
 * @author Eric
 */
public interface IInsureanceService {


    /**
     * 是否购买过人保
     *
     * @param userId
     * @return
     */
    public boolean hasPICCInsurance(String userId);

    /**
     * 查询购保单详情
     *
     * @param userId
     * @return 开始时间 startTime
     * 结束时间endTime
     * 保障期限 safeTerm
     * 保障金 safeMoney
     * 可赔次数 compensateTimes
     * 购买金额 payMoneyq
     * 热线	hotline
     */
    public MBUserInsuranceDTO queryPICCInsuranceInfo(String userId);

    /**
     * 领取人保
     * 用户只可以领取一次保险
     * 任何一起异常将直接抛出
     *
     * @param userId
     */
    public void buyPICCInsurance(String userId);


    public Map queryV2PICCInsuranceInfo(String userId, String userChannelType);
}
