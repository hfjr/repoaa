package com.zhuanyi.vjwealth.wallet.mobile.user.server.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.loan.client.dto.ClientBasicInfoDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.HousingFundLoginModeDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.CheckApplyStatusReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.HouseFundLoanApplyInfoDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.HousingInfoSaveReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.CheckApplyStatusResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.HousingInfoInitResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.HousingInfoSaveResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.webservice.IApplyHousingFundLoanDubboService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.FundAccountInitiTypeSelectDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.FundAccountInitiTypeSelectDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.IUserQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IFriendHelpService;
import com.zhuanyi.vjwealth.wallet.mobile.user.util.ParamValidUtil;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserInviteService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by csy on 2016/11/2.
 */
@Service
public class FriendHelpServiceImpl implements IFriendHelpService {
    @Remote
    private IMBUserInviteService mbUserInviteService;

    @Autowired
    private IApplyHousingFundLoanDubboService applyHousingFundLoanDubboService;

    @Remote
    private IMBUserService mbUserService;

    @Autowired
    private IUserQueryMapper userQueryMapper;

    @Override
    public Object queryShareQRCodePic(String userId, String type) {
        ParamValidUtil.validatorUserId(userId);
        return mbUserInviteService.queryInviteQRCodePic(userId, type);
    }

    @Override
    public Object queryWeiXinShareInfo(String userId, String type) {
        ParamValidUtil.validatorUserId(userId);
        return mbUserInviteService.queryWeiXinShareInfo(userId, type);
    }

    @Override
    public Object queryHouseFundCityLoginInfo(String cityCode) {
        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isBlank(cityCode)) {
            cityCode = "06002";//默认城市：上海
        }

        HousingInfoInitResDTO dto = applyHousingFundLoanDubboService.getHouseFundCityLoginInfo(cityCode);


        //获取公积金账号类型列表
        List<HousingFundLoginModeDTO> list = dto.getHousingFundLoginModeList();

        //公积金账号类型详细列表
        List<FundAccountInitiTypeSelectDTO> selectDTOs = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(list)) {
            for (HousingFundLoginModeDTO modelDto : list) {
                //设置公积金类型的详细信息
                FundAccountInitiTypeSelectDTO typeSelectDTO = new FundAccountInitiTypeSelectDTO();
                FundAccountInitiTypeSelectDetailDTO detailDTO = new FundAccountInitiTypeSelectDetailDTO();
                try {
                    BeanUtils.copyProperties(detailDTO, modelDto);
                } catch (Exception e) {
                    BaseLogger.error(e);
                    throw new AppException("数据异常，请稍后再试");
                }

                typeSelectDTO.setKey(modelDto.getLoginType());
                typeSelectDTO.setValue(modelDto.getLoginTypeLabel());
                typeSelectDTO.setUiDesc(detailDTO);

                selectDTOs.add(typeSelectDTO);
            }
        }


        //设置默认logintype
        FundAccountInitiTypeSelectDTO defaultTypeSelectDTO = new FundAccountInitiTypeSelectDTO();
        FundAccountInitiTypeSelectDetailDTO defaultDetailDTO = new FundAccountInitiTypeSelectDetailDTO();

        HousingFundLoginModeDTO defaultModel = dto.getDefaultHousingFundLoginMode();
        try {
            BeanUtils.copyProperties(defaultDetailDTO, defaultModel);
        } catch (Exception e) {
            BaseLogger.error(e);
            throw new AppException("数据异常，请稍后再试");
        }

        defaultTypeSelectDTO.setKey(defaultModel.getLoginType());
        defaultTypeSelectDTO.setValue(defaultModel.getLoginTypeLabel());
        defaultTypeSelectDTO.setUiDesc(defaultDetailDTO);

        result.put("defaultLoginType", defaultTypeSelectDTO);
        result.put("loginTypeSelections", selectDTOs);
        return result;
    }

    @Override
    public Object applyHouseFundLoanCreditLimit(HouseFundLoanApplyInfoDTO dto) {
        ParamValidUtil.validatorUserId(dto.getUserId());
        ParamValidUtil.validatorNotEmpty(dto.getLoginType());
        ParamValidUtil.validatorNotEmpty(dto.getCityCode());
        ParamValidUtil.validatorNotEmpty(dto.getHouseFundAccount());

        ClientBasicInfoDTO clientBasicInfoDTO = new ClientBasicInfoDTO();
        clientBasicInfoDTO.setChannelId("wj_hyb");
        clientBasicInfoDTO.setId(dto.getUserId());
        clientBasicInfoDTO.setPhone(userQueryMapper.queryUserPhoneNo(dto.getUserId()));

        CheckApplyStatusReqDTO checkApplyStatusReqDTO = new CheckApplyStatusReqDTO(clientBasicInfoDTO);

        BaseLogger.info("调用信贷系统：创建预授信订单 开始reqDTO = " + JSON.toJSONString(checkApplyStatusReqDTO));
        CheckApplyStatusResDTO resDTO = applyHousingFundLoanDubboService.checkApplyStatus(checkApplyStatusReqDTO);
        BaseLogger.info("调用信贷系统：创建预授信订单 结束reqDTO = " + JSON.toJSONString(resDTO));
        if (!resDTO.getCode().equals("200")) {
            throw new AppException(resDTO.getMsg());
        }

        HousingInfoSaveReqDTO housingInfoSaveReqDTO = new HousingInfoSaveReqDTO();
        housingInfoSaveReqDTO.setUserId(dto.getUserId());
        housingInfoSaveReqDTO.setBorrowCode(resDTO.getBorrowCode());
        housingInfoSaveReqDTO.setCityCode(dto.getCityCode());
        housingInfoSaveReqDTO.setFundAccount(dto.getHouseFundAccount());
        housingInfoSaveReqDTO.setFundAccountPassword(dto.getHouseFundPassword());
        housingInfoSaveReqDTO.setLoginType(dto.getLoginType());
        housingInfoSaveReqDTO.setIdCard(dto.getIdCard());
        housingInfoSaveReqDTO.setOtherParam(dto.getOtherParam());
        housingInfoSaveReqDTO.setEquipmentIp("0");
        housingInfoSaveReqDTO.setEquipmentNumber("0");
        housingInfoSaveReqDTO.setOsType("0");
        housingInfoSaveReqDTO.setOsVersion("0");
        housingInfoSaveReqDTO.setRealName(dto.getRealName());

        BaseLogger.info("调用信贷系统：公积金信息保存及爬取任务创建 开始reqDTO = " + JSON.toJSONString(housingInfoSaveReqDTO));
        HousingInfoSaveResDTO housingInfoSaveResDTO = applyHousingFundLoanDubboService.housingInfoSave(housingInfoSaveReqDTO);
        BaseLogger.info("调用信贷系统：公积金信息保存及爬取任务创建 结束resDTO = " + JSON.toJSONString(housingInfoSaveResDTO));
        if (!housingInfoSaveResDTO.getCode().equals("200")) {
            throw new AppException(housingInfoSaveResDTO.getMsg());
        }
        return housingInfoSaveResDTO;
    }

    @Override
    public Object queryMyCommission(String userId) {
        Map resultMap = new HashMap();
        List<String> recommendUserIdList = mbUserInviteService.queryRecommendUserListByActivityCode(userId, "gjjd_hyb");
        resultMap.put("inviteUserNum", recommendUserIdList == null ? 0 : recommendUserIdList.size());
        resultMap.put("returnAmount", applyHousingFundLoanDubboService.getHouseFundLoanReturnAmountForHYB(recommendUserIdList));
        return resultMap;
    }
}
