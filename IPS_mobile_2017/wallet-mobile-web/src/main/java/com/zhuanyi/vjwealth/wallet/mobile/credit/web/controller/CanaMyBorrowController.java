package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import com.fab.core.exception.service.AppException;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.ICanaMyBorrowService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hexy on 16/5/12.
 */
@Controller
@RequestMapping("/api/v3.4")
public class CanaMyBorrowController {
    @Autowired
    private ICanaMyBorrowService canaMyBorrowService;

    /**
     * 20.工资随享（右上角更多）我的借款明细-记录列表接口
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
        if(StringUtils.isEmpty(productTypeCode)){
            throw new AppException("产品编号不能为空");
        }
        if(StringUtils.isEmpty(page)){
            throw new AppException("页码不能为空");
        }
        return canaMyBorrowService.borrowRecordList(userId, borrowRecordStatus, page,productTypeCode);
    }

    /**
     * 21.我的借款明细-记录列表-借款详情接口
     *
     * @param userId
     * @param borrowCode
     * @param productTypeCode
     * @return
     * @since 3.4
     */
    @RequestMapping("/app/credit/myBorrow/borrowRecordDetail.security")
    @AppController
    public Object borrowRecordDetail(String userId, String borrowCode,String productTypeCode) {
        if(StringUtils.isEmpty(userId)){
            throw new AppException("用户编号不能为空");
        }
        if(StringUtils.isEmpty(borrowCode)){
            throw new AppException("借款编号不能为空");
        }
        return canaMyBorrowService.borrowRecordDetail(userId,borrowCode,productTypeCode);
    }

    /**
     *  22.全部借款列表接口
     * @param userId
     * @param recordType
     * @param page
     * @param productTypeCode
     * @since 3.4
     * @return
     */
    @RequestMapping("/app/credit/myBorrow/allBorrowRecord.security")
    @AppController
    public Object allBorrowRecord(String userId, String recordType, String page,String productTypeCode) {
        if(StringUtils.isEmpty(userId)){
            throw new AppException("用户编号不能为空");
        }
        if(StringUtils.isEmpty(productTypeCode)){
            throw new AppException("产品编号不能为空");
        }
        if(StringUtils.isEmpty(recordType)){
            throw new AppException("记录类型不能为空");
        }
        return canaMyBorrowService.allBorrowRecord(userId,recordType,page,productTypeCode);
    }
    /**
     *  22.1.全部借款列表接口
     * @param userId
     * @param productTypeCode
     * @since 3.4
     * @return
     */
    @RequestMapping("/app/credit/myBorrow/allRepaymentRecord.security")
    @AppController
    public Object allRepaymentRecord(String userId,String productTypeCode) {
        if(StringUtils.isEmpty(userId)){
            throw new AppException("用户编号不能为空");
        }
        if(StringUtils.isEmpty(productTypeCode)){
            throw new AppException("产品编号不能为空");
        }
        return canaMyBorrowService.allRepaymentRecord(userId,productTypeCode);
    }

    /**
     *  23.全部借款列表-还款详情接口
     * @param userId
     * @param borrowCode
     * @param repaymentPeriodCode
     * @since 3.4
     * @return
     */
    @RequestMapping("/app/credit/myBorrow/allBorrowRecordDetail.security")
    @AppController
    public Object allBorrowRecordDetail(String userId,String borrowCode,String repaymentPeriodCode) {
        if(StringUtils.isEmpty(userId)){
            throw new AppException("用户编号不能为空");
        }
        if(StringUtils.isEmpty(repaymentPeriodCode)){
            throw new AppException("还款期数编号不能为空");
        }
        if(StringUtils.isEmpty(borrowCode)){
            throw new AppException("借款编号不能为空");
        }
        return canaMyBorrowService.allBorrowRecordDetail(userId,borrowCode,repaymentPeriodCode);
    }
    /**
     *  24.立即还款保存接口
     * @param userId
     * @param borrowCode
     * @param repaymentPeriodCode
     * @since 3.4
     * @return
     */
    @RequestMapping("/app/credit/myBorrow/immediateRepaymentSave.security")
    @AppController
    public Object immediateRepaymentSave(String userId,String borrowCode,String repaymentPeriodCode) {
        if(StringUtils.isEmpty(userId)){
            throw new AppException("用户编号不能为空");
        }
        if(StringUtils.isEmpty(repaymentPeriodCode)){
            throw new AppException("还款期数编号不能为空");
        }
        if(StringUtils.isEmpty(borrowCode)){
            throw new AppException("借款编号不能为空");
        }
        return canaMyBorrowService.immediateRepaymentSave(userId,borrowCode,repaymentPeriodCode);
    }

}