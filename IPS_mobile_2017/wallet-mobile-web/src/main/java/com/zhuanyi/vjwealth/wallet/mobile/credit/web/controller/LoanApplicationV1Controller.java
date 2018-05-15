package com.zhuanyi.vjwealth.wallet.mobile.credit.web.controller;

import com.fab.core.exception.service.AppException;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.loan.constant.BizCodeType;
import com.zhuanyi.vjwealth.loan.order.vo.DirectContactInformationVo;
import com.zhuanyi.vjwealth.loan.order.vo.RepaymentPlanQueryVo;
import com.zhuanyi.vjwealth.loan.order.webservice.ILoanApplicationDubboService;
import com.zhuanyi.vjwealth.loan.util.ValidateUtil;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IFileOperationService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UploadIdentityPhotosDTO;
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

import java.util.Map;

/**
 * Created by hexy on 16/5/12.
 */
@Controller
@RequestMapping("/api/v3.3.1")
public class LoanApplicationV1Controller {
	@Autowired
	private ILoanApplicationDubboService loanApplicationDubboService;

	/**
	 * 12.借款申请 ［未借款，借款处理中，借款失败，借款成功（还款中）］ 初始化
	 *
	 * @param userId
     * @param productTypeCode
	 * @return
	 * @since 3.3.1
	 */
	@RequestMapping("/app/credit/loanApplication/loanApplicationIniti.security")
	@AppController
	public Object loanApplicationIniti(String userId,String productTypeCode) {
		if (StringUtils.isEmpty(userId)) {
			throw new AppException("用户编号不能为空");
		}
		return loanApplicationDubboService.loanApplicationInit(userId,productTypeCode);
	}

}