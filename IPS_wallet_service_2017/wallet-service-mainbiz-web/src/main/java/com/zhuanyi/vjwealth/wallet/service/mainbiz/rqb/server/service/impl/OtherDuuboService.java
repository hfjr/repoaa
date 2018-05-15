package com.zhuanyi.vjwealth.wallet.service.mainbiz.rqb.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.loan.cana.dto.ApplyLoanReq;
import com.zhuanyi.vjwealth.loan.jd.dto.ExpireRepayReqDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.ExpireRepayResDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.ThirdPlatformMappingResDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.checkApply.CheckApplyReqDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.checkApply.CheckApplyResDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.checkApplyResult.CheckApplyResultReqDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.checkApplyResult.CheckApplyResultResDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.getCardInfo.GetCardInfoReqDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.getCardInfo.GetCardInfoResDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.getToken.GetTokenReqDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.getToken.GetTokenResDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.payment.PaymentConfirmDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.payment.PaymentConfirmResultDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.payment.PaymentReqDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.payment.PaymentResDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.paymentResult.PaymentResultReqDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.paymentResult.PaymentResultResDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.preRepaymentPlan.PreRepaymentPlanReqDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.preRepaymentPlan.PreRepaymentPlanResDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.queryRepaymentPlan.QueryRepaymentPlanReqDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.queryRepaymentPlan.QueryRepaymentPlanResDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.repaymentResult.RepaymentResultReqDTO;
import com.zhuanyi.vjwealth.loan.jd.dto.repaymentResult.RepaymentResultResDTO;
import com.zhuanyi.vjwealth.loan.jd.webservice.IJDLoanDubboService;
import com.zhuanyi.vjwealth.loan.oauth.jwt.ITokenGenerate;
import com.zhuanyi.vjwealth.loan.order.cana.vo.BorrowApplyIntiVo;
import com.zhuanyi.vjwealth.loan.order.cana.vo.InformationConfirmationSMSVo;
import com.zhuanyi.vjwealth.loan.order.cana.vo.InformationConfirmationVo;
import com.zhuanyi.vjwealth.loan.order.cana.vo.LoanApplicationInitVo;
import com.zhuanyi.vjwealth.loan.order.vo.RepaymentPlanQueryVo;
import com.zhuanyi.vjwealth.loan.order.webservice.ICanaLoanApplicationDubboService;
import com.zhuanyi.vjwealth.loan.sd.dto.SDUserInfoPreReqDTO;
import com.zhuanyi.vjwealth.loan.sd.webservice.ISDDubboService;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.DueRepayDTO;
import com.zhuanyi.vjwealth.wallet.service.coupon.dto.CouponForInvest;
import com.zhuanyi.vjwealth.wallet.service.coupon.dto.UserCouponDTO;
import com.zhuanyi.vjwealth.wallet.service.coupon.server.service.IUserCouponService;


@Service
public class OtherDuuboService implements IUserCouponService,ICanaLoanApplicationDubboService,ISDDubboService,IJDLoanDubboService,ITokenGenerate {

	@Override
	public BorrowApplyIntiVo amountAndRepaymentInstallmentFormInit(String arg0,
			String arg1) throws AppException {
		// TODO Auto-generated method stub
		return new BorrowApplyIntiVo();
	}

	@Override
	public Map<String, Object> amountAndRepaymentInstallmentFormSave(
			RepaymentPlanQueryVo arg0) throws AppException {
		// TODO Auto-generated method stub
		return new HashMap<String, Object>();
	}

	@Override
	public InformationConfirmationVo informationConfirmationIniti(String arg0,
			String arg1) throws AppException {
		// TODO Auto-generated method stub
		return new InformationConfirmationVo();
	}

	@Override
	public InformationConfirmationSMSVo informationConfirmationSMSVerificationIniti(
			String arg0, String arg1) throws AppException {
		// TODO Auto-generated method stub
		return new InformationConfirmationSMSVo();
	}

	@Override
	public Map<String, Object> informationConfirmationSMSVerificationSave(
			String arg0, ApplyLoanReq arg1) throws AppException {
		// TODO Auto-generated method stub
		return new HashMap<String, Object>();
	}

	@Override
	public Map<String, String> investmentContractTemplate(String arg0,
			String arg1, String arg2) {
		// TODO Auto-generated method stub
		return new HashMap<String, String>();
	}

	@Override
	public LoanApplicationInitVo loanApplicationInit(String arg0, String arg1)
			throws AppException {
		// TODO Auto-generated method stub
		return new LoanApplicationInitVo();
	}

	@Override
	public void saveContractFileId(String arg0, String arg1)
			throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public Object bindingSDOrderInfo(String arg0, String arg1)
			throws AppException {
		// TODO Auto-generated method stub
		return new Object();
	}

	@Override
	public boolean existSdOrderId(String arg0) throws AppException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String queryOrderIdBySdOrderId(String arg0) throws AppException {
		// TODO Auto-generated method stub
		return new String();
	}

	@Override
	public Object saveSDOrderInfo(SDUserInfoPreReqDTO arg0) throws AppException {
		// TODO Auto-generated method stub
		return new String();
	}


	@Override
	public CheckApplyResDTO checkApply(CheckApplyReqDTO arg0)
			throws AppException {
		// TODO Auto-generated method stub
		return new CheckApplyResDTO();
	}

	@Override
	public CheckApplyResultResDTO checkApplyResult(CheckApplyResultReqDTO arg0) {
		// TODO Auto-generated method stub
		return new CheckApplyResultResDTO();
	}

	@Override
	public ExpireRepayResDTO expireUpdateRepay(ExpireRepayReqDTO arg0) {
		// TODO Auto-generated method stub
		return new ExpireRepayResDTO();
	}

	@Override
	public GetCardInfoResDTO getCardInfo(GetCardInfoReqDTO arg0)
			throws AppException {
		// TODO Auto-generated method stub
		return new GetCardInfoResDTO();
	}

	@Override
	public boolean getCrplCert() throws AppException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public GetTokenResDTO getToken(GetTokenReqDTO arg0) throws AppException {
		// TODO Auto-generated method stub
		return new GetTokenResDTO();
	}

	@Override
	public void makeCities() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makePlanOverdue(DueRepayDTO arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String parseTxt(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public PaymentResDTO payment(PaymentReqDTO arg0) throws AppException {
		// TODO Auto-generated method stub
		return new PaymentResDTO();
	}

	@Override
	public void paymentDeal(PaymentResultReqDTO arg0) throws AppException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PaymentResultResDTO paymentResult(PaymentResultReqDTO arg0) {
		// TODO Auto-generated method stub
		return new PaymentResultResDTO();
	}

	@Override
	public PreRepaymentPlanResDTO preRepaymentPlan(PreRepaymentPlanReqDTO arg0)
			throws AppException {
		// TODO Auto-generated method stub
		return new PreRepaymentPlanResDTO();
	}

	@Override
	public QueryRepaymentPlanResDTO queryRepaymentPlan(
			QueryRepaymentPlanReqDTO arg0) throws AppException {
		// TODO Auto-generated method stub
		return new QueryRepaymentPlanResDTO();
	}

	@Override
	public ThirdPlatformMappingResDTO queryVjNoByThirdPlatformNo(String arg0) {
		// TODO Auto-generated method stub
		return new ThirdPlatformMappingResDTO();
	}

	@Override
	public RepaymentResultResDTO repaymentResult(RepaymentResultReqDTO arg0)
			throws AppException {
		// TODO Auto-generated method stub
		return new RepaymentResultResDTO();
	}

	@Override
	public void sendEmail(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean whiteListCities(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String createToken(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Boolean validToken(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean cashCouponRedeem(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean distributeRegisterCashCoupon(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<CouponForInvest> queryCanInvestCashCouponList(String arg0,
			String arg1, String arg2, String arg3) {
		// TODO Auto-generated method stub
		return new ArrayList<CouponForInvest>();
	}

	@Override
	public Map<String, Object> queryCashCouponCanUse(String arg0, String arg1,
			String arg2, String arg3, String arg4) {
		// TODO Auto-generated method stub
		return new HashMap<String, Object>();
	}

	@Override
	public List<UserCouponDTO> queryUserCashCouponHistoryList(String arg0,
			Integer arg1) {
		// TODO Auto-generated method stub
		return new ArrayList<UserCouponDTO>();
	}

	@Override
	public List<UserCouponDTO> queryUserCashCouponUnUsedList(String arg0,
			Integer arg1, String arg2) {
		// TODO Auto-generated method stub
		return new ArrayList<UserCouponDTO>();
	}

	@Override
	public List<UserCouponDTO> queryUserInterestCouponHistoryList(String arg0,
			Integer arg1) {
		// TODO Auto-generated method stub
		return new ArrayList<UserCouponDTO>();
	}

	@Override
	public List<UserCouponDTO> queryUserInterestCouponUnUsedList(String arg0,
			Integer arg1, String arg2) {
		// TODO Auto-generated method stub
		return new ArrayList<UserCouponDTO>();
	}

	@Override
	public boolean rateCouponRedeem(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateCashCouponUnUsed(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean updateCashCouponUsed(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public PaymentConfirmResultDTO doSpvLendAmount(PaymentConfirmDTO arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void endOrder(String arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInvCert() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentConfirmResultDTO paymentConfirm(PaymentConfirmDTO arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CheckApplyResDTO repeatCheckApply(String arg0, String arg1) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentResDTO repeatPayment(PaymentReqDTO arg0, String arg1) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateOrderConfirmInfo(PaymentConfirmDTO arg0) {
		// TODO Auto-generated method stub
		return 0;
	}




}
