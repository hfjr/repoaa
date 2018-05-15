package com.zhuanyi.vjwealth.wallet.mobile.credit.server.impl;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.loan.constant.BizCodeType;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IEasyLoanService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.ILoanProductService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.CommonCommitResultDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.easyLoan.EasyLoanInitDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.easyLoan.EasyLoanSaveDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.easyLoan.LoanPeriodDTO;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct.CreditWayCodeEnum;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.FinancialLoanMapper;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.mapper.IEasyLoanMapper;
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
 * 工资易贷
 */
@Service
public class EasyLoanServiceServiceImpl implements IEasyLoanService {

	@Autowired
	private IEasyLoanMapper easyLoanMapper;

	@Remote
	ISendEmailService sendEmailService;

	@Autowired
	private ILoanProductService loanProductService;

	@Remote
	private IPhoneMessageService phoneMessageService;

	@Autowired
	private FinancialLoanMapper financialLoanMapper;


	public Object EasyLoanApplySave(EasyLoanSaveDTO easyLoanSaveDTO) {

		String channelType = easyLoanSaveDTO.getUserChannelType();//渠道类型
		String userName = easyLoanSaveDTO.getUserName();//用户名
		String phone = easyLoanSaveDTO.getPhone();//手机号
		String loanProductId = easyLoanSaveDTO.getLoanProductId();

		//校验来源渠道
		if(StringUtils.isBlank(channelType)){
			throw new AppException("channelType不能为空");
		}

		if(StringUtils.isBlank(userName) || StringUtils.isBlank(phone)){
			throw new AppException("userName或phone不能为空");
		}

		if(StringUtils.isBlank(easyLoanSaveDTO.getHousePlaceDesc())){
			throw new AppException("housePlaceDesc不能为空");
		}

		//设置默认贷款期限单位
		easyLoanSaveDTO.setPeriodUnit("month");

		int count = easyLoanMapper.easyLoanApplySave(easyLoanSaveDTO);


		//如果是精英貸，則判斷公積金繳納是否夠期限，不夠的話，提示預約失敗
		if(BizCodeType.LOAN_JY_HOUSE_PRODUCT_ID.getCode().equals(loanProductId) && easyLoanSaveDTO.getFundPeriod().equals("1")){
			return new CommonCommitResultDTO("204001","预约失败","公积金缴纳期限不足|请选择其他产品","");
		}

		String productName = "";
		if(BizCodeType.LOAN_HOUSE_PRODUCT_ID.getCode().equals(loanProductId)){
			productName = CreditWayCodeEnum.CREDIT_HOUSE.getDesc();
		}
		else if(BizCodeType.LOAN_JY_HOUSE_PRODUCT_ID.getCode().equals(loanProductId)){
			productName = CreditWayCodeEnum.CREDIT_JY_HOUSE.getDesc();
		}
		else if(BizCodeType.LOAN_S_HOUSE_PRODUCT_ID.getCode().equals(loanProductId)){
			productName = CreditWayCodeEnum.CREDIT_S_HOUSE.getDesc();
		}

		if(count>0 ){
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("content", "["+userName+"]用户申请了"+productName+"，手机号["+phone+"]("+easyLoanSaveDTO.getHousePlaceDesc()+")");
			sendEmailService.sendAsyncEmail("EASY_LOAN_INFO", paramMap);
		}

		return new CommonCommitResultDTO("204000","预约成功","财富顾问稍后会与您联系|请保持通讯设备通畅","");
	}


	@Override
	public Object easyLoanInit(String userId,String loanProductId) {

		EasyLoanInitDTO init = easyLoanMapper.queryEasyLoanInitParam();

		if(StringUtils.isNotBlank(userId)){
			Map<String,String> userInfo = easyLoanMapper.queryUserInfoByUserId(userId);
			init.setUserName(userInfo.get("userName"));
			init.setPhone(userInfo.get("phone"));
		}

		List<Map<String,String>> fundPeriodList = new ArrayList<>();
		fundPeriodList.add(buildKeyAndValueMap("1","不足一年"));
		fundPeriodList.add(buildKeyAndValueMap("2","一年以上"));
		init.setFundPeriodSelection(fundPeriodList);
		init.setFundPeriodDefault(new HashMap<String, String>());




		//設置默認城市
		Map<String,String> cityMap = new HashMap<>();
		if(BizCodeType.LOAN_HOUSE_PRODUCT_ID.getCode().equals(loanProductId)){
			cityMap = buildKeyAndValueMap("310000","上海市");
		}
//		else if(BizCodeType.LOAN_JY_HOUSE_PRODUCT_ID.getCode().equals(loanProductId)){
//			cityMap = buildKeyAndValueMap("06002","上海市");
//		}
//		else if(BizCodeType.LOAN_S_HOUSE_PRODUCT_ID.getCode().equals(loanProductId)){
//			cityMap = buildKeyAndValueMap("01003","深圳市");
//			String picUrl =  financialLoanMapper.getParamsValueByKeyAndGroup("loan_common_pic_url","loan_comm")+"/4.0/gongziyidai_init_tip.png";
//			init.setTopIntroducePic(picUrl);
//		}
		init.setCityDefault(cityMap);


		//设置房产类型
		List<Map<String,String>> houseTypeList = new ArrayList<>();
		houseTypeList.add(buildKeyAndValueMap("1","住宅"));
		houseTypeList.add(buildKeyAndValueMap("2","商业"));
		houseTypeList.add(buildKeyAndValueMap("3","办公"));
		init.setHouseTypeSelection(houseTypeList);
		init.setHouseTypeDefault(houseTypeList.get(0));

		//设置借款期限
		LoanPeriodDTO loanPeriodDTO = queryPeriodByCity("310000");
		init.setLoanPeriodSelection(loanPeriodDTO.getLoanPeriodSelection());
		init.setLoanPeriodDefault(loanPeriodDTO.getLoanPeriodDefault());

		return init;
	}

	private Map<String,String> buildKeyAndValueMap(String key,String value){
		Map<String,String> map = new HashMap<>();
		map.put("key",key);
		map.put("value",value);
		return map;
	}


	@Override
	public Object queryCityList(String loanProductId) {
		return loanProductService.queryLoanProductCityList(loanProductId);
	}


	@Override
	public LoanPeriodDTO queryPeriodByCity(String cityCode) {

		List<Map<String,String>> shortLoanPeriodList = new ArrayList<>();
		shortLoanPeriodList.add(buildKeyAndValueMap("3","3个月"));
		shortLoanPeriodList.add(buildKeyAndValueMap("6","6个月"));
		shortLoanPeriodList.add(buildKeyAndValueMap("12","12个月"));

		List<Map<String,String>> longLoanPeriodList = new ArrayList<>();
		longLoanPeriodList.add(buildKeyAndValueMap("3","3个月"));
		longLoanPeriodList.add(buildKeyAndValueMap("6","6个月"));
		longLoanPeriodList.add(buildKeyAndValueMap("12","12个月"));
		longLoanPeriodList.add(buildKeyAndValueMap("24","24个月"));
		longLoanPeriodList.add(buildKeyAndValueMap("36","36个月"));

		List<Map<String,String>> resultLoanPeriodList = new ArrayList<>();

		if(cityCode.equals("320500")){
			resultLoanPeriodList = shortLoanPeriodList;
		}else {
			resultLoanPeriodList = longLoanPeriodList;
		}

		LoanPeriodDTO result = new LoanPeriodDTO();
		result.setLoanPeriodDefault(resultLoanPeriodList.get(0));
		result.setLoanPeriodSelection(resultLoanPeriodList);

		return result;
	}
}
