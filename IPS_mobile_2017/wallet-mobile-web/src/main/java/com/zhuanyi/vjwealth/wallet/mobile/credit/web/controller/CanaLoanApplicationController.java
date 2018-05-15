package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import com.fab.core.exception.service.AppException;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.loan.constant.BizCodeType;
import com.zhuanyi.vjwealth.loan.order.vo.RepaymentPlanQueryVo;
import com.zhuanyi.vjwealth.loan.order.webservice.ICanaLoanApplicationDubboService;
import com.zhuanyi.vjwealth.loan.util.PDFUtil;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.ICanaApplyLoanService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IPdfFileOperationService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.canaLoan.ApplyLoanQueryDTO;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:凯拿页面路由接口
 * @author: Tony Tang
 * @date: 2016-05-13 17:08
 */
@Controller
@RequestMapping("/api/v3.4")
public class CanaLoanApplicationController {
	@Autowired
	private ICanaApplyLoanService canaApplyLoanService;
	/**
	 * 12.借款申请 ［未借款，借款处理中，借款失败，借款成功（还款中）］ 初始化
	 *
	 * @param queryDto
	 * @return
	 * @since 3.4
	 */
	@RequestMapping("/app/credit/loanApplication/loanApplicationIniti.security")
	@AppController
	public Object loanApplicationIniti(ApplyLoanQueryDTO queryDto) {
		if (StringUtils.isEmpty(queryDto.getUserId())) {
			throw new AppException("用户编号不能为空");
		}
		if (StringUtils.isEmpty(queryDto.getProductTypeCode())) {
			throw new AppException("产品编号不能为空");
		}
		return canaApplyLoanService.loanApplicationInit(queryDto);
	}
	/**
	 * 13.借款申请［额度，还款期数填写］初始化
	 *
	 * @param userId
	 * @param productTypeCode
	 * @return
	 * @since 3.4
	 */
	@RequestMapping("/app/credit/loanApplication/amountAndRepaymentInstallmentFormIniti.security")
	@AppController
	public Object amountAndRepaymentInstallmentFormIniti(String userId, String productTypeCode) {
		if (StringUtils.isEmpty(userId)) {
			throw new AppException("用户编号不能为空");
		}
		if (StringUtils.isEmpty(productTypeCode)) {
			throw new AppException("产品编号不能为空");
		}
		return canaApplyLoanService.amountAndRepaymentInstallmentFormInit(userId,productTypeCode);
	}

	/**
	 * 14.借款申请［额度填写］保存接口
	 *
	 * @param param
	 * @return
	 * @since 3.4
	 */
	@RequestMapping("/app/credit/loanApplication/amountAndRepaymentInstallmentFormSave.security")
	@AppController
	public Object amountAndRepaymentInstallmentFormSave(RepaymentPlanQueryVo param) {
		if (param==null||StringUtils.isEmpty(param.getUserId())) {
			throw new AppException("用户编号不能为空");
		}
		if (StringUtils.isEmpty(param.getApplyAmount())) {
			throw new AppException("申请金额不能为空");
		}
		if (StringUtils.isEmpty(param.getBorrowCode())) {
			throw new AppException("借款编号不能为空");
		}
		return canaApplyLoanService.amountAndRepaymentInstallmentFormSave(param);
	}

	/**
	 * 15.借款申请［信息确认］初始化
	 *
	 * @param userId
	 * @param borrowCode
	 * @return
	 * @since 3.4
	 */
	@RequestMapping("/app/credit/loanApplication/informationConfirmationIniti.security")
	@AppController
	public Object informationConfirmationIniti(String userId, String borrowCode) {
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(borrowCode)) {
			throw new AppException("用户编号或接口编码不能为空");
		}
		return canaApplyLoanService.informationConfirmationIniti(userId,borrowCode);
	}
	/**
	 * 16.借款申请［信息确认］《合同及相关协议》-借款合同 H5接口
	 *
	 * @param userId
	 * @return
	 * @since 3.4
	 */
	@RequestMapping(value ="/app/credit/loanApplication/contracts/loanContract.security")
	public Object loanContract(String userId,String borrowCode,String productTypeCode, Model model) {
		if (StringUtils.isEmpty(userId)  || StringUtils.isEmpty(borrowCode)) {
			return "app/credit/canaLoanApplication/404Error";
		}
		Map<String,String> returnMap = canaApplyLoanService.investmentContractTemplate(userId,borrowCode,productTypeCode);
		if( BizCodeType.IS_NO.getCode().equals(returnMap.get("flag"))){
			return "app/credit/canaLoanApplication/404Error";
		}
		if(BizCodeType.CONTRACT_FREVIEW.getCode().equals(returnMap.get("type"))){
			model.addAttribute("content",returnMap.get("content"));
			return "app/credit/canaLoanApplication/loanContract";
		}else{
			if(returnMap.get("fileId")==null){
				return "app/credit/canaLoanApplication/404Error";
			}
			model.addAttribute("pdfUrl",returnMap.get("fileId")+".pdf");
			return "app/credit/canaLoanApplication/loanPdfContract";
		}
	}
	/**
	 * 16.1.查看PDF文件
	 * @param fileId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/app/credit/loanApplication/contracts/{fileId}.pdf", method = RequestMethod.GET)
	public ResponseEntity<byte[]> loadContract(@PathVariable String fileId) throws IOException {
		byte[] bytes=canaApplyLoanService.downContract(fileId);
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/pdf"));
		headers.setContentLength(bytes.length);
		headers.add(HttpHeaders.ACCEPT_RANGES,"bytes");
		//headers.setLastModified(System.currentTimeMillis()-100000);
		//headers.setETag("W/\""+fileId+"-1469700527000\"");
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}
	/**
	 * 16.2.下载PDF文件
	 * @param fileId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/app/credit/loanApplication/downContract/{fileId}.pdf")
	public ResponseEntity<byte[]> downContract(@PathVariable String fileId) throws IOException {
		byte[] bytes=canaApplyLoanService.downContract(fileId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", fileId + ".pdf");
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
	}
	/**
	 * 17.借款申请［信息确认-SMS］初始化
	 *
	 * @param userId
	 * @return
	 * @since 3.4
	 */
	@RequestMapping("/app/credit/loanApplication/informationConfirmationSMSVerificationIniti.security")
	@AppController
	public Object informationConfirmationSMSVerificationIniti(String userId, String borrowCode) {
		if (StringUtils.isEmpty(userId)) {
			throw new AppException("用户编号不能为空");
		}
		if (StringUtils.isEmpty(borrowCode)) {
			throw new AppException("借款编号不能为空");
		}
		return canaApplyLoanService.informationConfirmationSMSVerificationIniti(userId,borrowCode);
	}

	/**
	 * 18 借款申请［信息确认-SMS］ (18 获取文字验证码)
	 *
	 * @param userId
	 *  @param borrowCode
	 * @return
	 * @since 3.4
	 */
	@RequestMapping("/app/credit/loanApplication/informationConfirmationSMSVerificationSendSMSNotice.security")
	@AppController
	public Object informationConfirmationSMSVerificationSendSMSNotice(String userId, String borrowCode) {
        if (StringUtils.isEmpty(userId)) {
            throw new AppException("用户编号不能为空");
        }
        if (StringUtils.isEmpty(borrowCode)) {
            throw new AppException("借款编号不能为空");
        }
		return canaApplyLoanService.informationConfirmationSMSVerificationSendSMSNotice(userId,borrowCode);
	}

	/**
	 * 19.借款申请［信息确认-SMS］保存
	 *
	 * @param userId
	 * @return
	 * @since 3.4
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
		return canaApplyLoanService.informationConfirmationSMSVerificationSave(userId,borrowCode,code);
	}

}