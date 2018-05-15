package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import com.fab.core.exception.service.AppException;
import com.fab.core.template.ITemplateService;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.ConfirmLoanApplyReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.HousingInfoSaveReqDTO;
import com.zhuanyi.vjwealth.loan.order.vo.PersonalInformationVo;
import com.zhuanyi.vjwealth.loan.order.vo.UploadIdentitysVO;
import com.zhuanyi.vjwealth.loan.order.webservice.ILoanApplicationDubboService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IFundLoanService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by hexy on 16/5/12.
 */
@Controller
@RequestMapping("/api/v4.0")
public class FundLoanController {
	@Autowired
	private IFundLoanService fundLoanService;


//	/**
//	 * 1.白领专享，申请额度个人信息初始化
//	 *
//	 * @param userId
//	 * @return
//	 * @since 4.0
//	 */
//	@RequestMapping("/app/credit/fund/applyCreditPersonalInfoInit.security")
//	@AppController
//	public Object applyCreditPersonalInfoInit(String userId) {
//		return fundLoanService.applyCreditPersonalInfoInit(userId);
//	}


//	/**
//	 * 2.白领专享，申请额度  公积金信息初始化
//	 * @param
//	 * @return
//	 * @since 4.0
//	 */
//	@RequestMapping("/app/credit/fund/fundAccountIniti.security")
//	@AppController
//	public Object fundAccountIniti(String userId,String cityCode,String borrowCode) {
//		return fundLoanService.fundAccountIniti(userId,cityCode,borrowCode);
//	}


//	/**
//	 * 3.白领专享，申请额度个人信息保存
//	 * @return
//	 * @since 4.0
//	 */
//	@RequestMapping("/app/credit/fund/queryCityList")
//	@AppController
//	public Object queryCityList() {
//		return fundLoanService.queryCityList();
//	}


//	/**
//	 * 4.白领专享，申请额度个人信息保存
//	 * @param query
//	 * @return
//	 * @since 4.0
//	 */
//	@RequestMapping("/app/credit/fund/applyCreditPersonalInfoCommit.security")
//	@AppController
//	public Object applyCreditPersonalInfoCommit(PersonalInformationVo query,HttpServletRequest request) {
//		return fundLoanService.applyCreditPersonalInfoCommit(query,request);
//	}



	/**
	 * 5.借款申请-任务表单初始化
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/grantLoanFormIniti.security")
	@AppController
	public Object grantLoanFormIniti(String userId,String borrowCode) {
		return fundLoanService.grantLoanFormIniti(userId,borrowCode);
	}




	/***************************************************************************分割线******************************************************/


	/**
	 * 1.借款-公积金贷-初始化路由接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/checkApplyStatus.security")
	@AppController
	public Object checkApplyStatus(String userId) {

		return fundLoanService.checkApplyStatus(userId,true);
	}


	/**
	 * 2.1 注册及征信授权协议 H5页面
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/xiyi1.security")
	@AppController
	public Object xiyi1() {
		return null;
	}

	/**
	 * 2.2 借款引导介绍页-注册及征信授权协议接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/xiyi2.security")
	@AppController
	public Object xiyi2() {
		return null;
	}


	/**
	 * 3.借款引导介绍页（可见：用户第一次访问可见）
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/creditIntroduce")
	@AppController
	public Object creditIntroduce() {
		return fundLoanService.creditIntroduce();
	}


	/**
	 * 4.信贷产品匹配接口（条件：金额）
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/queryMatchedProductList.security")
	@AppController
	public Object queryMatchedProductList(String userId,String loanMinAmount,String loanMaxAmount) {
		return fundLoanService.queryMatchedProductList(userId,loanMinAmount, loanMaxAmount);
	}


	/**
	 * 5.借款进度-记录数接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/queryLoanProcessCount.security")
	@AppController
	public Object queryLoanProcessCount(String userId) {
		return fundLoanService.queryLoanProcessCount(userId);
	}

	/**
	 * 6. 借款进度-产品列表接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/queryLoanRecordList.security")
	@AppController
	public Object queryLoanRecordList(String userId) {
		return fundLoanService.queryLoanRecordList(userId);
	}

	/**
	 * 7. 借款进度-产品列表详情接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/queryLoanRecordDetail.security")
	@AppController
	public Object queryLoanRecordDetail(String userId,String borrowCode,String isChanged) {
		return fundLoanService.queryLoanRecordDetail(userId,borrowCode,isChanged);
	}

	/**
	 * 8. 申请额度-公积金信息初始化接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/fundAccountIniti.security")
	@AppController
	public Object fundAccountIniti(String userId,String borrowCode,String cityCode,String isInit) {
		return fundLoanService.fundAccountIniti(userId,borrowCode,cityCode,isInit);
	}


	/**
	 * 9. 申请额度-公积金信息保存接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/applyCreditPersonalInfoCommit.security")
	@AppController
	public Object applyCreditPersonalInfoCommit(FundInfoSaveDTO query, HttpServletRequest request) {
		return fundLoanService.applyCreditPersonalInfoCommit(query,request);
	}

	/**
	 * 9.1 申请额度-公积金授信结果查询
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/queryCreditResult.security")
	@AppController
	public Object queryCreditResult(String userId,String borrowCode) {
		return fundLoanService.queryCreditResult(userId,borrowCode);
	}

	/**
	 * 10.申请额度-公积金支持城市列表接口
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/queryCityList")
	@AppController
	public Object queryCityList() {
		return fundLoanService.queryCityList();
	}

	/**
	 * 11.申请借款-借款金额信息初始化接口
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/loanApplyInit.security")
	@AppController
	public Object loanApplyInit(String userId,String borrowCode) {
		return fundLoanService.loanApplyInit(userId,borrowCode);
	}

	/**
	 * 11.1 申请借款-试算接口
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/repaymentTrial.security")
	@AppController
	public Object repaymentTrial(String userId,String borrowCode,String loanAmount,String loanPeriod) {
		return fundLoanService.repaymentTrial(userId,borrowCode,loanAmount,loanPeriod);
	}

	/**
	 * 12 申请借款-借款金额信息保存接口
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/loanApplySave.security")
	@AppController
	public Object loanApplySave(FundLoanApplySaveDTO applySaveDTO) {
		return fundLoanService.loanApplySave(applySaveDTO);
	}

	/**
	 * 13. 完善资料-初始化接口
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/improveApplyInfo.security")
	@AppController
	public Object improveApplyInfo(String userId,String borrowCode) {
		return fundLoanService.improveApplyInfo(userId,borrowCode);
	}

	/**
	 * 14. 完善资料-放款银行卡-初始化接口
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/bingdingBankCardInit.security")
	@AppController
	public Object bingdingBankCardInit(String userId,String borrowCode) {
		return fundLoanService.bingdingBankCardInit(userId,borrowCode);
	}

	/**
	 * 15. 完善资料-放款银行卡-发送绑卡短信验证码接口
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/bingdingBankcardSendSMS.security")
	@AppController
	public Object bingdingBankcardSendSMS(CheckBingdingBankcardDTO reqDto) {
		return fundLoanService.bingdingBankcardSendSMS(reqDto);
	}

	/**
	 * 15.1 完善资料-放款银行卡-发送绑卡短信验证码接口(重发)
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/bingdingBankcardSendSMSAgain.security")
	@AppController
	public Object bingdingBankcardSendSMSAgain(CheckBingdingBankcardDTO reqDto) {
		return fundLoanService.bingdingBankcardSendSMSAgain(reqDto);
	}

	/**
	 * 16. 完善资料-放款银行卡-绑卡验证接口
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/checkBingdingBankcard.security")
	@AppController
	public Object checkBingdingBankcard(CheckBingdingBankcardDTO reqDto) {
		return fundLoanService.checkBingdingBankcard(reqDto);
	}

	/**
	 * 17. 完善资料-实名认证-初始化接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/certificationInit.security")
	@AppController
	public Object certificationInit(String userId,String borrowCode) {
		return fundLoanService.certificationInit(userId,borrowCode);
	}

	/**
	 * 18. 完善资料-实名认证-图片上传接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/certificationUploadPic.security")
	@AppController
	public Object certificationUploadPic(String userId,String picFile) {
		return fundLoanService.certificationUploadPic(userId,picFile);
	}


	/**
	 * 19.完善资料-实名认证-保存接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/certificationSave.security")
	@AppController
	public Object certificationSave(Object obj) {
		return fundLoanService.certificationSave(obj);
	}


	/**
	 * 19.1 完善资料-实名认证-更新接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/certificationUpdate.security")
	@AppController
	public Object certificationUpdate(UploadIdentitysVO uploadIdentitysVO) {
		return fundLoanService.certificationUpdate(uploadIdentitysVO);
	}


	/**
	 * 20.完善资料-基本资料-初始化接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/basicInfoInit.security")
	@AppController
	public Object basicInfoInit(String userId,String borrowCode) {
		return fundLoanService.basicInfoInit(userId,borrowCode);
	}

	/**
	 * 21.完善资料-基本资料-保存接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/basicInfoSave.security")
	@AppController
	public Object basicInfoSave(BasicInfoSaveDTO query) {
		return fundLoanService.basicInfoSave(query);
	}


	/**
	 * 22.完善资料-联系人-初始化接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/contactInfoInit.security")
	@AppController
	public Object contactInfoInit(String userId,String borrowCode) {
		return fundLoanService.contactInfoInit(userId,borrowCode);
	}

	/**
	 * 23.完善资料-联系人-保存接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/contactInfoSave.security")
	@AppController
	public Object contactInfoSave(ContactInfoSaveDTO obj) {
		return fundLoanService.contactInfoSave(obj);
	}

	/**
	 * 24.完善资料-运营商-初始化登录【step1】接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/communicationLoginInfoInit.security")
	@AppController
	public Object communicationLoginInfoInit(String userId,String borrowCode) {
		return fundLoanService.communicationLoginInfoInit(userId,borrowCode);
	}

	/**
	 * 24.1 完善资料-运营商-初始化登录【step1】接口-发送验证码
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/communicationLoginInfoInitSendSMS.security")
	@AppController
	public Object communicationLoginInfoInitSendSMS(String userId,String borrowCode) {
		final String times = fundLoanService.communicationLoginInfoInitSendSMS(userId,borrowCode);
		return new HashMap<String,String>(){{
			put("remainTime",times);
		}};
	}

	/**
	 * 25.完善资料-运营商-运营商授权协议接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/communicationProtocol.security")
	@AppController
	public Object communicationProtocol(String userId,String borrowCode,String contractCode) {
		return fundLoanService.communicationProtocol(userId,borrowCode,contractCode);
	}

	/**
	 * 26.完善资料-运营商-登录保存【step1】接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/communicationLoginInfoSave.security")
	@AppController
	public Object communicationLoginInfoSave(CommunicationLoginInfoSaveDTO obj) {
		return fundLoanService.communicationLoginInfoSave(obj);
	}

	/**
	 * 27. 完善资料-运营商-初始化详单获取【step2】接口（刷新时使用）
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/communicationDetailInfoInit.security")
	@AppController
	public Object communicationDetailInfoInit(String userId,String borrowCode) {
		return fundLoanService.communicationDetailInfoInit(userId,borrowCode);
	}

	/**
	 * 27.1  完善资料-运营商-初始化详单获取【step2】接口--发送验证码
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/communicationDetailInfoInitSendSMS.security")
	@AppController
	public Object communicationDetailInfoInitSendSMS(String userId,String borrowCode) {
		final String times = fundLoanService.communicationDetailInfoInitSendSMS(userId,borrowCode);
		return new HashMap<String,String>(){{
			put("remainTime",times);
		}};
	}

	/**
	 * 28. 完善资料-运营商-初始化详单保存【step2】接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/communicationDetailInfoSave.security")
	@AppController
	public Object communicationDetailInfoSave(CommunicationDetailInfoSaveDTO obj) {
		return fundLoanService.communicationDetailInfoSave(obj);
	}


	/**
	 * 29. 完善资料-【借款合同协议】接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/loanApplyContract.security")
	@AppController
	public Object loanApplyContract(String userId) {
		return fundLoanService.loanApplyContract(userId);
	}


	/**
	 * 30. 完善资料-【确认借款】接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/loanApplyInfoShow.security")
	@AppController
	public Object loanApplyInfoShow(String userId,String borrowCode) {
		return fundLoanService.loanApplyInfoShow(userId,borrowCode);
	}


	/**
	 * 31. 银行卡所属地区及联系人地址地区列表
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/queryDistrictList")
	@AppController
	public Object queryDistrictList() {
		return fundLoanService.queryDistrictList();
	}

	/**
	 * 32 借款-产品筛选初始化页面
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/loanProductInit.security")
	@AppController
	public Object loanProductInit(String userId) {
		return fundLoanService.loanProductInit(userId);
	}

	/**
	 * 33 有贷款状态更新的提示接口
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/queryOrderStatusIsChanged.security")
	@AppController
	public Object orderStatusChanged(String userId) {
		return fundLoanService.orderStatusChanged(userId);
	}

	/**
	 * 34 点击贷款更新状态后更新
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/changeOrderStatus.security")
	@AppController
	public Object changeOrderStatus(String userId,String borrowCode) {
		return fundLoanService.changeOrderStatus(userId,borrowCode);
	}

	/**
	 * 35. 完善资料-全部资料提交确认接口
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/improveApplyInfoCommit.security")
	@AppController
	public Object improveApplyInfoCommit(ConfirmLoanApplyReqDTO reqDTO,HttpServletRequest request) {
		return fundLoanService.improveApplyInfoCommit(reqDTO,request);
	}


	/**
	 * 36. 完善资料-全部资料提交确认接口
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/getPicUrl.security")
	@AppController
	public Object getPicUrl(String userId,String borrowCode,String type) {
		final String picUrl = fundLoanService.getPicUrl(userId,borrowCode,type);
		return new HashMap<String,String>(){{
			put("picUrl",picUrl);
		}};
	}


	/**
	 * 40.贷款产品介绍图片
	 * @param
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/productIntroduce")
	@AppController
	public Object productIntroduce(String productId) {
		return fundLoanService.productIntroduce(productId);
	}


	/**
	 * 37.公积金贷借款合同
	 *
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/contract")
	public String contract(String borrowCode,Model model) {

		if (StringUtils.isEmpty(borrowCode)) {
			return "app/credit/loanApplication/404Error";
		}

		try{
			String content = fundLoanService.getFundLoanContract(borrowCode);
			model.addAttribute("content",content);
		}catch (AppException e){
			return "app/credit/loanApplication/404Error";
		}

		return "/app/protocol/4.0/yzt-contract";
	}


	/**
	 * 38.咨询协议
	 *
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/consult")
	public String consult(String borrowCode,Model model) {

		if (StringUtils.isEmpty(borrowCode)) {
			return "app/credit/loanApplication/404Error";
		}

		try{
			String content = fundLoanService.getFundLoanConsult(borrowCode);
			model.addAttribute("content",content);
		}catch (AppException e){
			return "app/credit/loanApplication/404Error";
		}

		return "/app/protocol/4.0/yzt-consult";
	}


	/**
	 * 39.个人征信授权协议
	 *
	 * @return
	 * @since 4.0
	 */
	@RequestMapping("/app/credit/fund/reference")
	public String reference(String borrowCode,Model model) {

		if (StringUtils.isEmpty(borrowCode)) {
			return "app/credit/loanApplication/404Error";
		}

		try{
			String content = fundLoanService.getFundLoanReference(borrowCode);
			model.addAttribute("content",content);
		}catch (AppException e){
			return "app/credit/loanApplication/404Error";
		}

		return "/app/protocol/4.0/yzt-reference";
	}



}