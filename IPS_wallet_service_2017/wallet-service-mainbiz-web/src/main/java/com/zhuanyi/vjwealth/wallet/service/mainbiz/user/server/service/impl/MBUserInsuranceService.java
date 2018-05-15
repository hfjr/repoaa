package com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.impl;

import com.fab.core.exception.service.AppException;
import com.fab.server.util.Format;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBUserInsuranceDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.integration.mapper.IMBUserInsuranceMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserInsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Service
public class MBUserInsuranceService implements IMBUserInsuranceService {

    @Autowired
    private IMBUserInsuranceMapper mbuseImbUserInsuranceMapper;

    public boolean hasPICCInsurance(String userId) {
        return mbuseImbUserInsuranceMapper.countBuyPICCInsurance(userId) > 0 ? true : false;
    }


    public MBUserInsuranceDTO queryPICCInsuranceInfo(String userId) {
        if (!hasPICCInsurance(userId))
            throw new AppException(620, "您还未领取保险,赶快免费领取吧");

        return mbuseImbUserInsuranceMapper.queryPICCInsuranceInfo(userId);
    }

    @Transactional
    public void buyPICCInsurance(String userId) {
        //	1. 校验是否购买过保险
        if (hasPICCInsurance(userId))
            throw new AppException(620, "您已领取保险,无需再次领取");

        //	2. 购买保险
        Calendar time = Calendar.getInstance();
        String startTime = Format.dateToString(time.getTime(), "yyyy-MM-dd");
        time.add(Calendar.YEAR, 1);
        time.add(Calendar.DATE, -1);
        String endTime = Format.dateToString(time.getTime(), "yyyy-MM-dd");
        mbuseImbUserInsuranceMapper.buyPICCInsurance(userId, startTime, endTime);

        //	3. 如果线程并发,回滚
        if (mbuseImbUserInsuranceMapper.countBuyPICCInsurance(userId) > 1)
            throw new AppException(620, "客观莫急,保险已成功领取");
    }
}
