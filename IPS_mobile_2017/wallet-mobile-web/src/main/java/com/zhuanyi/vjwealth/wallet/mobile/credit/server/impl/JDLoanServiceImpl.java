package com.zhuanyi.vjwealth.wallet.mobile.credit.server.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.loan.constant.BizCodeType;
import com.zhuanyi.vjwealth.loan.jd.dto.ThirdPlatformMappingResDTO;
import com.zhuanyi.vjwealth.loan.jd.webservice.IJDLoanDubboService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IEasyLoanService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IJDLoanService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.ILoanProductService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants.LoanBizTypeConstant;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.CommonCommitResultDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.easyLoan.EasyLoanInitDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.easyLoan.EasyLoanSaveDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.jd.BankTypeInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.jd.BindCardInitDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.jd.BindedCardDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.jd.SupportBankDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct.CreditWayCodeEnum;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.FinancialLoanMapper;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.IEasyLoanMapper;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.ILoanCommonMapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.dto.UserInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IUserOperationService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.dto.BindingCardDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.payment.server.withhold.facade.IWithholdServiceFacade;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBRechargeDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.UserBankCardListInfoDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserAccountService;
import com.zhuanyi.vjwealth.wallet.service.outer.email.server.service.ISendEmailService;
import com.zhuanyi.vjwealth.wallet.service.outer.message.server.service.IPhoneMessageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 京东金融绑卡
 */
@Service
public class JDLoanServiceImpl implements IJDLoanService {

	@Autowired
	private IUserOperationService operationService;

	@Autowired
	private IJDLoanDubboService jdLoanDubboService;

	@Autowired
	private ILoanCommonMapper loanCommonMapper;

	@Autowired
	private FinancialLoanMapper financialLoanMapper;

	@Remote
	private IWithholdServiceFacade withholdServiceFacade;

	@Remote
	private IMBUserAccountService mBUserAccountService;

	@Override
	public Object querySupportBankList() {

		List<SupportBankDTO> bankList = new ArrayList<>();

		List<BindingCardDTO> cardList = withholdServiceFacade.queryWithholdBankList(LoanBizTypeConstant.JD_BIND_CARD);
		if(CollectionUtils.isNotEmpty(cardList)){
			for(BindingCardDTO bcd:cardList){
				if(bcd.getIsSupportCard().equals("Y")){
					bankList.add(new SupportBankDTO(bcd.getBankCode(),bcd.getBankName()));
				}
			}
		}

		Map<String,Object> result = new HashMap<>();
		result.put("bankList",bankList);
		return result;
	}

	@Override
	public Object queryBindedCardList(String ud) {
		BaseLogger.info("查询用户绑卡列表UserId:"+ud);

		String userId = getLocalUserId(ud);
		List<BindedCardDTO> bindedCardDTOList = new ArrayList<>();

		BindedCardDTO cardDTO = loanCommonMapper.queryUserSecurityCardList(userId);
		if(cardDTO !=null) {

			boolean isValid = validatorIsJdBinded(userId,cardDTO.getCardNoDesc());

			if(isValid){
			String phone = cardDTO.getAsideBankPhoneDesc();
			cardDTO.setAsideBankPhoneDesc("手机尾号"+(StringUtils.isBlank(phone)?"":phone.substring(phone.length()-4,phone.length())));
			String cardNo = cardDTO.getCardNoDesc();
			cardDTO.setCardNoDesc("**** **** **** "+(StringUtils.isBlank(cardNo)?"":cardNo.substring(cardNo.length()-4,cardNo.length())));
			Boolean flag = checkCardType(cardNo);
			if(flag != null){
				cardDTO.setCadTypeName(flag?"储蓄卡":"信用卡");
			}

				boolean status = loanCommonMapper.validatorBankIsNormal(cardDTO.getBankCode(),"paychannel005");
				cardDTO.setIsValid(status?"Y":"N");
			bindedCardDTOList.add(cardDTO);
		}
		}

		Map<String,Object> result = new HashMap<>();
		result.put("bankCardList",bindedCardDTOList);
		result.put("isCanBindCard",bindedCardDTOList.size()>0?"N":"Y");
		return result;
	}

	@Override
	public Object bindCardInit(String ud) {
		BaseLogger.info("绑卡初始化UserId:"+ud);

		String userId = getLocalUserId(ud);

		BindCardInitDTO initDTO = new BindCardInitDTO();

		UserInfoDTO userInfoDTO = loanCommonMapper.queryUserInfoById(userId);

		if(userInfoDTO!=null){
			String realName = userInfoDTO.getRealName();
			initDTO.setUserName(realName);
			String indentityNo = userInfoDTO.getIndentityNo();
			String indentityNoShow = indentityNo;
			if(indentityNo.length() == 18){
				indentityNoShow = indentityNo.substring(0,6)+"********"+indentityNo.substring(14,18);
			}
			initDTO.setIndentityNo(userInfoDTO.getIndentityNo());
			initDTO.setIndentityNoShow(indentityNoShow);//展示时，隐藏部分信息

			//
			String tempName = realName.substring(0,1)+realName.substring(1,realName.length());
			initDTO.setHeadTip("银行卡将同时作为提现卡和还款卡，设置完成后不可更改。为了保障账户安全，请添加"+tempName+"的储蓄卡");
		}


		BindedCardDTO cardDTO = loanCommonMapper.queryUserSecurityCardList(userId);
		if(cardDTO !=null) {
			boolean isValid = validatorIsJdBinded(userId,cardDTO.getCardNoDesc());

			if(!isValid){
				String cardNo = cardDTO.getCardNoDesc();
				BankTypeInfoDTO typeInfoDTO = queryBankNameByCardNo(cardNo);
				initDTO.setCardTypeName(typeInfoDTO.getCardTypeName());
				initDTO.setBankName(typeInfoDTO.getBankName());
				initDTO.setBankCode(typeInfoDTO.getBankCode());
				initDTO.setCardNo(cardNo);
				initDTO.setPhone(cardDTO.getAsideBankPhoneDesc());
			}
		}


		List<Map<String,String>> mapList = new ArrayList<>();
		mapList.add(buildKeyAndValueMap("01","身份证"));

		initDTO.setIndentityTypeSelection(mapList);

		String contractUrl = financialLoanMapper.getParamsValueByKeyAndGroup("rechargeBindBankCardIniti_protocolDetailURL","wallet_vcredit_yingzt");

		initDTO.setProtocolDesc("《融桥宝移动支付协议》");
		initDTO.setProtocolUrl(contractUrl);
		initDTO.setProtocolTitle("融桥宝移动支付协议");

		return initDTO;
	}


	private boolean validatorIsJdBinded(String userId,String cardNo){
		return mBUserAccountService.isBindCardForWithHoldInLocal(userId,cardNo,"jingdong");
	}

	@Override
	public BankTypeInfoDTO queryBankNameByCardNo(String cardNo) {
		BaseLogger.info("查询银行信息cardNo:"+cardNo);
		BankTypeInfoDTO bankTypeInfoDTO = getBankTypeInfo(cardNo);
		if(bankTypeInfoDTO==null){
			return new BankTypeInfoDTO();
		}

		String cardType = bankTypeInfoDTO.getCardTypeName();
		bankTypeInfoDTO.setCardTypeName(cardType.equals("Y")?"信用卡":"储蓄卡");
		return bankTypeInfoDTO;

	}

	private BankTypeInfoDTO getBankTypeInfo(String cardNo){
		if(!StringUtils.isBlank(cardNo) && cardNo.length()>8) {
			//先根据前6位查询。
			BankTypeInfoDTO bankTypeInfoDTO = loanCommonMapper.queryBankinfoByCardBin(cardNo.substring(0, 6));
			if (bankTypeInfoDTO == null) {
				bankTypeInfoDTO = loanCommonMapper.queryBankinfoByCardBin(cardNo.substring(0, 8));
			}
			return bankTypeInfoDTO;
		}
		return null;
	}

	/**
	 * 判断是否是储蓄卡
	 * @param cardNo
	 * @return
	 */
	private Boolean checkCardType(String cardNo){
		BankTypeInfoDTO bankTypeInfoDTO = getBankTypeInfo(cardNo);
		if(bankTypeInfoDTO == null){
			return null;
		}
		if(bankTypeInfoDTO.getCardTypeName().equals("Y")){
			return false;
		}
		return true;
	}

	@Override
	public Object bindCardSendSms(MBRechargeDTO dto) {
		BaseLogger.info("绑卡发送验证码:"+ JSONObject.toJSONString(dto));

		String userId = getLocalUserId(dto.getUserId());
		dto.setUserId(userId);
		dto.setOrderType(LoanBizTypeConstant.JD_BIND_CARD);

		MBRechargeDTO resultDto = operationService.bindCardSendCode(dto);

		//如果已经存在同开号的充值卡，则返回已存在的cardId
		BindedCardDTO cardDTO = loanCommonMapper.queryUserRechargeCardByCardNo(userId,dto.getCardNo());
		if(cardDTO != null && StringUtils.isNotBlank(cardDTO.getCardId())){
			resultDto.setCardId(cardDTO.getCardId());
		}

		return resultDto;
	}

	@Override
	public Object bindCardConfirm(MBRechargeDTO dto) {
		BaseLogger.info("绑卡确认:"+ JSONObject.toJSONString(dto));

		String userId = getLocalUserId(dto.getUserId());
		dto.setUserId(userId);
		dto.setOrderType(LoanBizTypeConstant.JD_BIND_CARD);

		return operationService.confirmBindCardSendCode(dto);
	}




	private Map<String,String> buildKeyAndValueMap(String key, String value){
		Map<String,String> map = new HashMap<>();
		map.put("key",key);
		map.put("value",value);
		return map;
	}


	private String getLocalUserId(String userId){

		if(StringUtils.isBlank(userId)){
			throw new AppException("userId 为空");
		}

		ThirdPlatformMappingResDTO resDTO = jdLoanDubboService.queryVjNoByThirdPlatformNo(userId);
		if(!resDTO.isSuccess()){
			throw new AppException("数据异常");
		}
		return resDTO.getVjNo();
	}


	public static void main(String[] args) {
		String indentityNo = "341622198902262314";
		System.out.println(indentityNo.substring(0,6)+"********"+indentityNo.substring(14,18)) ;
	}
}
