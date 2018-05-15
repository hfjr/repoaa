package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import com.zhuanyi.vjwealth.loan.order.vo.UploadIdentitysVO;
import com.zhuanyi.vjwealth.loan.util.ValidateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fab.core.exception.service.AppException;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.loan.constant.BizCodeType;
import com.zhuanyi.vjwealth.loan.order.vo.DirectContactInformationVo;
import com.zhuanyi.vjwealth.loan.order.vo.RepaymentPlanQueryVo;
import com.zhuanyi.vjwealth.loan.order.webservice.ILoanApplicationDubboService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IFileOperationService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UploadIdentityPhotosDTO;

import java.util.Map;

/**
 * Created by hexy on 16/5/12.
 */
@Controller
@RequestMapping("/api/v3.3")
public class LoanApplicationController {
	@Autowired
	private ILoanApplicationDubboService loanApplicationDubboService;
	@Autowired
	private IFileOperationService fileOperationService;

	/**
	 * 12.借款申请 ［未借款，借款处理中，借款失败，借款成功（还款中）］ 初始化
	 *
	 * @param userId
	 * @return
	 * @since 3.3
	 */
	@RequestMapping("/app/credit/loanApplication/loanApplicationIniti.security")
	@AppController
	public Object loanApplicationIniti(String userId) {
		if (StringUtils.isEmpty(userId)) {
			throw new AppException("用户编号不能为空");
		}
		return loanApplicationDubboService.loanApplicationInit(userId);
	}

	/**
	 * 13.借款申请［额度，还款期数填写］初始化
	 *
	 * @param userId
	 * @param borrowCode
	 * @return
	 * @since 3.3
	 */
	@RequestMapping("/app/credit/loanApplication/amountAndRepaymentInstallmentFormIniti.security")
	@AppController
	public Object amountAndRepaymentInstallmentFormIniti(String userId, String borrowCode) {
		if (StringUtils.isEmpty(userId)) {
			throw new AppException("用户编号不能为空");
		}
		if (StringUtils.isEmpty(borrowCode)) {
			throw new AppException("借款编号不能为空");
		}
		return loanApplicationDubboService.amountAndRepaymentInstallmentFormInit(userId, borrowCode);
	}

	/**
	 * 14.借款申请［额度，还款期数填写］ 动态生成还款详情
	 *
	 * @param param
	 * @return
	 * @since 3.3
	 */
	@RequestMapping("/app/credit/loanApplication/dynamicallyGeneratedRepaymentDetail.security")
	@AppController
	public Object dynamicallyGeneratedRepaymentDetail(RepaymentPlanQueryVo param) {
		if (param == null || com.alibaba.dubbo.common.utils.StringUtils.isEmpty(param.getUserId())) {
			throw new AppException("用户编号不能为空");
		}
		return loanApplicationDubboService.dynamicallyGeneratedRepaymentDetail(param);
	}

	/**
	 * 15.借款申请［额度，还款期数填写］确认（保存）
	 *
	 * @param param
	 * @return
	 * @since 3.3
	 */
	@RequestMapping("/app/credit/loanApplication/amountAndRepaymentInstallmentFormSave.security")
	@AppController
	public Object amountAndRepaymentInstallmentFormSave(RepaymentPlanQueryVo param) {
		if (param == null || StringUtils.isEmpty(param.getUserId())) {
			throw new AppException("用户编号不能为空");
		}
		if (StringUtils.isEmpty(param.getRepaymentPeriod())) {
			throw new AppException("还款期数不能为空");
		}
		if (StringUtils.isEmpty(param.getRepaymentMethod())) {
			throw new AppException("还款方式不能为空");
		}
		if (StringUtils.isEmpty(param.getApplyAmount())) {
			throw new AppException("申请金额不能为空");
		}
		if (StringUtils.isEmpty(param.getBorrowCode())) {
			throw new AppException("借款编号不能为空");
		}
		param.setProductId(BizCodeType.LOAN_PRODUCT_ID.getCode());
		return loanApplicationDubboService.amountAndRepaymentInstallmentFormSave(param);
	}

	/**
	 * 16.借款申请［身份证信息上传］初始化
	 *
	 * @param userId
	 * @return
	 * @since 3.3
	 */
	@RequestMapping("/app/credit/loanApplication/uploadIdentityFormIniti.security")
	@AppController
	public Object uploadIdentityFormIniti(String userId, String borrowCode) {
		if (StringUtils.isEmpty(userId)) {
			throw new AppException("用户编号不能为空");
		}
		if (StringUtils.isEmpty(borrowCode)) {
			throw new AppException("借款编号不能为空");
		}
		return loanApplicationDubboService.uploadIdentityFormInit(userId, borrowCode);
	}

	/**
	 * 17.借款申请［身份证信息上传］保存
	 * 
	 * @param userId
	 * @param rightSideFile
	 * @param reverseSideFile
	 * @param handheldIdentity
	 * @param borrowCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {
			"app/credit/loanApplication/uploadIdentityFormSave.security" }, headers = ("content-type=multipart/*"), method = RequestMethod.POST)
	@AppController
	public Object uploadIdentityPhotos(@RequestParam(value = "userId") String userId,
			@RequestParam(value = "rightSideFile", required = false) MultipartFile rightSideFile,
			@RequestParam(value = "reverseSideFile", required = false) MultipartFile reverseSideFile,
			@RequestParam(value = "handheldIdentity", required = false) MultipartFile handheldIdentity,
			@RequestParam(value = "uploadSuccessFileIds", required = false) String uploadSuccessFileIds,
			@RequestParam(value = "borrowCode", required = false) String borrowCode) throws Exception {
		UploadIdentityPhotosDTO uploadIdentityPhotosDTO = fileOperationService.uploadIdentityPhotos(userId,
				rightSideFile, reverseSideFile, handheldIdentity, uploadSuccessFileIds, borrowCode);
		// 保存上传图片信息至Loan系统
		// loanApplicationDubboService.uploadIdentityFormSave(uploadIdentityPhotosDTO);
		return uploadIdentityPhotosDTO;
	}

	/**
	 * 17.1 借款申请［身份证信息上传］关系保存接口
	 * @param uploadIdentitysVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"app/credit/loanApplication/saveUploadIdentitys.security"},method = RequestMethod.POST)
	@AppController
	public Object saveUploadIdentitys(UploadIdentitysVO uploadIdentitysVO){

		return  fileOperationService.saveUploadIdentitys(uploadIdentitysVO);
	}


	// 加载图片
	@RequestMapping(value = "app/credit/loanApplication/loadIdentityPhoto/{fileId}.png")
	public ResponseEntity<byte[]> loadIdentityPhoto(@PathVariable String fileId) {
		byte[] data = fileOperationService.loadFile(fileId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", fileId + ".png");
		return new ResponseEntity<byte[]>(data, headers, HttpStatus.CREATED);
	}

	/**
	 * 18.借款申请［直接联系人信息填写］初始化
	 *
	 * @param userId
	 * @param borrowCode
	 * @return
	 * @since 3.3
	 */
	@RequestMapping("/app/credit/loanApplication/directContactInformationFormIniti.security")
	@AppController
	public Object directContactInformationFormIniti(String userId, String borrowCode) {
		if (StringUtils.isEmpty(userId)) {
			throw new AppException("用户编号不能为空");
		}
		if (StringUtils.isEmpty(borrowCode)) {
			throw new AppException("借款编号不能为空");
		}
		return loanApplicationDubboService.directContactInformationFormIniti(userId, borrowCode);
	}

	/**
	 * 19.借款申请［直接联系人信息填写］保存
	 *
	 * @param query
	 * @return
	 * @since 3.3
	 */
	@RequestMapping("/app/credit/loanApplication/directContactInformationFormSave.security")
	@AppController
	public Object directContactInformationFormSave(DirectContactInformationVo query) {
        if (StringUtils.isEmpty(query.getUserId())) {
            throw new AppException("用户编号不能为空");
        }
        if (StringUtils.isEmpty(query.getDirectContactName())) {
            throw new AppException("联系人姓名不能为空");
        }
        if (!ValidateUtil.isUsername(query.getDirectContactName())) {
            throw new AppException("联系人姓名格式不合法:请输入2到8汉字,或者2到16个英文字母");
        }
        if (StringUtils.isEmpty(query.getDirectContactPhone())) {
            throw new AppException("联系人手机号码不能为空");
        }
        if (!ValidateUtil.isMobile(query.getDirectContactPhone())) {
            throw new AppException("请输入有效的联系人手机号码");
        }
		return loanApplicationDubboService.directContactInformationFormSave(query);
	}

	/**
	 * 20.借款申请［信息确认］初始化
	 *
	 * @param userId
	 * @return
	 * @since 3.3
	 */
	@RequestMapping("/app/credit/loanApplication/informationConfirmationIniti.security")
	@AppController
	public Object informationConfirmationIniti(String userId, String borrowCode) {
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(borrowCode)) {
			throw new AppException("用户编号或接口编码不能为空");
		}
		return loanApplicationDubboService.informationConfirmationIniti(userId, borrowCode);
	}

	/**
	 * 21.借款申请［信息确认］《合同及相关协议》借款合同列表
	 *
	 * @param userId
	 * @return
	 * @since 3.3
	 */
	@RequestMapping("/app/credit/loanApplication/contracts/loanContractList.security")
	@AppController
	public Object loanContractList(String userId, String borrowCode) {
		if (StringUtils.isEmpty(userId)) {
			throw new AppException("用户编号不能为空");
		}
        if (StringUtils.isEmpty(borrowCode)) {
            throw new AppException("借款编号不能为空");
        }
		return loanApplicationDubboService.loanContractList(userId, borrowCode);
	}

	/**
	 * 22.借款申请［信息确认］《合同及相关协议》-借款合同 H5
	 * @param contractCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/app/credit/loanApplication/contracts/loanContract.security")
	public String loanContract(String userId,String borrowCode,String contractCode, Model model) {
		if (StringUtils.isEmpty(userId)  || StringUtils.isEmpty(borrowCode)) {
			return "app/credit/loanApplication/404Error";
		}
        Map<String,String> returnMap = loanApplicationDubboService.investmentContractTemplate(userId,borrowCode,contractCode);
        model.addAttribute("content", returnMap.get("content"));
		return "app/credit/loanApplication/loanContract";
	}

	/**
	 * 23.借款申请［信息确认］《合同及相关协议》-个人借贷合同履约保证保险投保协议 H5
	 * @param contractCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/app/credit/loanApplication/contracts/personalLoanContractAgreement.security")
	public String personalLoanContractAgreement(String userId,String borrowCode,String contractCode, Model model) {
		if (StringUtils.isEmpty(userId)  || StringUtils.isEmpty(borrowCode)) {
			return "app/credit/loanApplication/404Error";
		}
        Map<String,String> returnMap = loanApplicationDubboService.investmentContractTemplate(userId,borrowCode,contractCode);
        model.addAttribute("content", returnMap.get("content"));
		return "app/credit/loanApplication/personalLoanContractAgreement";
	}

	/**
	 * 24.借款申请［信息确认］《合同及相关协议》-个人征信查询及信息使用授权书 H5
	 * @param contractCode
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/app/credit/loanApplication/contracts/personalInformationInquiryAndInformationUseAuthorization.security")
	public String personalInformationInquiryAndInformationUseAuthorization(String userId,String borrowCode,String contractCode, Model model) {
		if (StringUtils.isEmpty(userId)  || StringUtils.isEmpty(borrowCode)) {
			return "app/credit/loanApplication/404Error";
		}
        Map<String,String> returnMap = loanApplicationDubboService.investmentContractTemplate(userId,borrowCode,contractCode);
        model.addAttribute("content", returnMap.get("content"));
		return "app/credit/loanApplication/personalInformationInquiryAndInformationUseAuthorization";
	}

	/**
	 * 25.借款申请［信息确认］保存
	 *
	 * @param userId
	 * @return
	 * @since 3.3
	 */
	@RequestMapping("/app/credit/loanApplication/informationConfirmationSave.security")
	@AppController
	public Object informationConfirmationSave(String userId, String borrowCode) {
		if (StringUtils.isEmpty(userId)) {
			throw new AppException("用户编号不能为空");
		}
        if (StringUtils.isEmpty(borrowCode)) {
            throw new AppException("借款编号编号不能为空");
        }
		return loanApplicationDubboService.informationConfirmationSave(userId, borrowCode);
	}

	/**
	 * 26.借款申请［信息确认-SMS］初始化
	 *
	 * @param userId
	 * @return
	 * @since 3.3
	 */
	@RequestMapping("/app/credit/loanApplication/informationConfirmationSMSVerificationIniti.security")
	@AppController
	public Object informationConfirmationSMSVerificationIniti(String userId, String borrowCode) {
		if (com.alibaba.dubbo.common.utils.StringUtils.isEmpty(userId)) {
			throw new AppException("用户编号不能为空");
		}
		if (com.alibaba.dubbo.common.utils.StringUtils.isEmpty(borrowCode)) {
			throw new AppException("借款编号不能为空");
		}
		return loanApplicationDubboService.informationConfirmationSMSVerificationIniti(userId, borrowCode);
	}

	/**
	 * 27 借款申请［信息确认-SMS］ (27.1 获取文字验证码)
	 *
	 * @param userId
	 * @return
	 * @since 3.3
	 */
	@RequestMapping("/app/credit/loanApplication/informationConfirmationSMSVerificationSendSMSNotice.security")
	@AppController
	public Object informationConfirmationSMSVerificationSendSMSNotice(String userId, String borrowCode, String phone,
			String isSendSMS) {
        if (StringUtils.isEmpty(userId)) {
            throw new AppException("用户编号不能为空");
        }
        if (StringUtils.isEmpty(borrowCode)) {
            throw new AppException("借款编号不能为空");
        }
        if (StringUtils.isEmpty(phone)) {
            throw new AppException("手机号码不能为空");
        }
		return loanApplicationDubboService.informationConfirmationSMSVerificationSendSMSNotice(userId, borrowCode,
				phone, isSendSMS);
	}

	/**
	 * 28.借款申请［信息确认-SMS］保存
	 *
	 * @param userId
	 * @return
	 * @since 3.3
	 */
	@RequestMapping("/app/credit/loanApplication/informationConfirmationSMSVerificationSave.security")
	@AppController
	public Object informationConfirmationSMSVerificationSave(String userId, String borrowCode, String code) {
		if (StringUtils.isEmpty(userId)) {
			throw new AppException("用户编号不能为空");
		}
        if (StringUtils.isEmpty(borrowCode)) {
            throw new AppException("借款编号不能为空");
        }
        if (StringUtils.isEmpty(code)) {
            throw new AppException("短信验证码不能为空");
        }
		return loanApplicationDubboService.informationConfirmationSMSVerificationSave(userId, borrowCode,code);
	}

	/**
	 * 29.还款［借款成功（还款中），初始化页面］ 本期还款详情
	 *
	 * @param userId
	 * @return
	 * @since 3.3
	 */
	@RequestMapping("/app/credit/loanApplication/repaymentPeriodDetail.security")
	@AppController
	public Object repaymentPeriodDetail(String userId, String repaymentPeriodCode) {
		if (StringUtils.isEmpty(userId)) {
			throw new AppException("用户编号不能为空");
		}
		if (StringUtils.isEmpty(repaymentPeriodCode)) {
			throw new AppException("本期还款期数编号不能为空");
		}
		return loanApplicationDubboService.repaymentPeriodDetail(userId, repaymentPeriodCode);
	}

}