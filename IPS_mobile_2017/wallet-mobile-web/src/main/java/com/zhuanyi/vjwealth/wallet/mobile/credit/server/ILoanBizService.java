package com.zhuanyi.vjwealth.wallet.mobile.credit.server;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.loan.order.vo.PersonalInformationVo;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.FundLoanShareStatisticsDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:信贷业务层
 * @author: Tony Tang
 * @date: 2016-05-13 17:08
 */
public interface ILoanBizService {
    /**
     * 页面路由
     *
     * @param userId
     * @param productTypeCode
     * @return
     */
    Object loanApplicationInit(String userId, String productTypeCode);

    /**
     * 申请额度初始化
     *
     * @param userId
     * @param productTypeCode
     * @return
     */
    Object creditApplicationInit(String userId, String productTypeCode);

    /**
     * 10.申请额度-完善个人信息初始化
     *
     * @param userId
     * @param productTypeCode
     * @return
     */
    Object improvePersonalInformationIniti(String userId, String productTypeCode);

    /**
     * 10.1 申请额度-完善个人信息初始化-公积金初始化
     *
     * @param userId
     * @param cityCode
     * @param borrowCode
     * @return
     */
    Object fundAccountIniti(String userId, String cityCode, String borrowCode);

    /**
     * 10.2 申请额度-完善个人信息初始化-社保初始化
     *
     * @param userId
     * @param cityCode
     * @param borrowCode
     * @return
     */
    Object socialSecurityAccountIniti(String userId, String cityCode, String borrowCode);

    /**
     * 申请额度-完善个人信息确认（保存）
     *
     * @param request
     * @param query
     * @return
     */
    Object improvePersonalInformationSave(HttpServletRequest request, PersonalInformationVo query);

    /**
     * 参数效验
     *
     * @throws AppException
     */
    void checkPersonalInformation(HttpServletRequest request, PersonalInformationVo query) throws AppException;

    Object cleanData(String phone);

    /**
     * 公积金贷款页面推广统计
     * @param source
     * @param code
     * @param memo
     * @return
     * @throws AppException
     */
    Object fundLoanShareStatistics(FundLoanShareStatisticsDTO dto) throws AppException;
}
