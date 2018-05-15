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
@RequestMapping("/api/v3.3")
public class MyBorrowController {
    @Autowired
    private IMyBorrowDubboService myBorrowDubboService;
    /**
     * 30.V信贷（右上角更多）我的借款明细-记录列表
     *
     * @param userId
     * @param borrowRecordStatus
     * @param page
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/myBorrow/borrowRecordList.security")
    @AppController
    public Object borrowRecordList(String userId, String borrowRecordStatus, String page) throws Exception {
        if(StringUtils.isEmpty(userId)){
            throw new AppException("用户编号不能为空");
        }
        if(StringUtils.isEmpty(borrowRecordStatus)){
            throw new AppException("借款状态不能为空");
        }
        return myBorrowDubboService.borrowRecordList(userId, borrowRecordStatus, page);
    }

    /**
     * 31.我的借款明细-记录列表-借款详情
     *
     * @param userId
     * @param borrowCode
     * @return
     * @since 3.3
     */
    @RequestMapping("/app/credit/myBorrow/borrowRecordDetail.security")
    @AppController
    public Object borrowRecordDetail(String userId, String borrowCode) {
        if(StringUtils.isEmpty(userId)){
            throw new AppException("用户编号不能为空");
        }
        if(StringUtils.isEmpty(borrowCode)){
            throw new AppException("借款编号不能为空");
        }
        return myBorrowDubboService.borrowRecordDetail(userId, borrowCode);
    }

}