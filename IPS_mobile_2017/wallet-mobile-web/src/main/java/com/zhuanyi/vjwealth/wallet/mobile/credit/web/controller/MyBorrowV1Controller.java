package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import com.fab.core.exception.service.AppException;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.loan.order.webservice.IMyBorrowDubboService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hexy on 16/5/12.
 */
@Controller
@RequestMapping("/api/v3.3.1")
public class MyBorrowV1Controller {
    @Autowired
    private IMyBorrowDubboService myBorrowDubboService;
    /**
     * 30.V信贷（右上角更多）我的借款明细-记录列表
     *
     * @param userId
     * @param borrowRecordStatus
     * @param page
     * @param productTypeCode
     * @return
     * @since 3.3.1
     */
    @RequestMapping("/app/credit/myBorrow/borrowRecordList.security")
    @AppController
    public Object borrowRecordList(String userId, String borrowRecordStatus, String page,String productTypeCode) throws Exception {
        if(StringUtils.isEmpty(userId)){
            throw new AppException("用户编号不能为空");
        }
        if(StringUtils.isEmpty(borrowRecordStatus)){
            throw new AppException("借款状态不能为空");
        }
        return myBorrowDubboService.borrowRecordList(userId, borrowRecordStatus, page,productTypeCode);
    }

}