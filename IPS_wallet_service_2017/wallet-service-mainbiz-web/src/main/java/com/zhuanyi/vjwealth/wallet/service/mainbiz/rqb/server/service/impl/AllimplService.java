package com.zhuanyi.vjwealth.wallet.service.mainbiz.rqb.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.zhuanyi.vjwealth.loan.cana.dto.ApplyCreditReq;
import com.zhuanyi.vjwealth.loan.cana.dto.ApplyCreditRsp;
import com.zhuanyi.vjwealth.loan.cana.dto.ApplyLoanReq;
import com.zhuanyi.vjwealth.loan.cana.dto.ApplyLoanRsp;
import com.zhuanyi.vjwealth.loan.cana.dto.ExecuteConfirmRepaymentReq;
import com.zhuanyi.vjwealth.loan.cana.dto.ExecuteConfirmRepaymentRsp;
import com.zhuanyi.vjwealth.loan.cana.dto.QueryCreditReq;
import com.zhuanyi.vjwealth.loan.cana.dto.QueryCreditRsp;
import com.zhuanyi.vjwealth.loan.cana.dto.QueryFundsReq;
import com.zhuanyi.vjwealth.loan.cana.dto.QueryFundsRsp;
import com.zhuanyi.vjwealth.loan.cana.dto.QueryInterestReq;
import com.zhuanyi.vjwealth.loan.cana.dto.QueryInterestRsp;
import com.zhuanyi.vjwealth.loan.cana.dto.QueryLoanListReq;
import com.zhuanyi.vjwealth.loan.cana.dto.QueryLoanListRsp;
import com.zhuanyi.vjwealth.loan.cana.dto.QueryLoanReq;
import com.zhuanyi.vjwealth.loan.cana.dto.QueryLoanRsp;
import com.zhuanyi.vjwealth.loan.cana.dto.QueryLoanStateReq;
import com.zhuanyi.vjwealth.loan.cana.dto.QueryLoanStateRsp;
import com.zhuanyi.vjwealth.loan.cana.dto.QueryPrepareRepaymentReq;
import com.zhuanyi.vjwealth.loan.cana.dto.QueryPrepareRepaymentRsp;
import com.zhuanyi.vjwealth.loan.cana.dto.cana.ContractDataRsp;
import com.zhuanyi.vjwealth.loan.cana.dto.cana.GenContractReq;
import com.zhuanyi.vjwealth.loan.cana.dto.cana.GenContractRsp;
import com.zhuanyi.vjwealth.loan.cana.dto.cana.SendVerificationReq;
import com.zhuanyi.vjwealth.loan.cana.webservice.ICanaLoanInterfaceService;
import com.zhuanyi.vjwealth.loan.client.dto.ClientCreditInfoDTO;
import com.zhuanyi.vjwealth.loan.client.webservice.IClientInfoDubboService;
import com.zhuanyi.vjwealth.loan.credit.webservice.ITaskDetailsInfoDubboService;
import com.zhuanyi.vjwealth.loan.functionInterface.Housingfund.vo.HousingfundResponseVO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.ComResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.FundLoanBankInfo;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.BasicInfoInitReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.BasicInfoSaveReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.BindCardInitReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.CheckApplyStatusReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.CommunicationFirstLoginReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.CommunicationInitReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.CommunicationSecondInitReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.CommunicationSecondLoginReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.ConfirmLoanApplyReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.ContactInfoInitReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.ContactInfoSaveReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.ContractReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.HousingInfoInitReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.HousingInfoSaveReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.ImproveApplyInfoReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.LoanApplyInitReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.LoanApplySaveReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.LoanProcessCountReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.LoanProcessDetailReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.LoanProcessListReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.PhoneCommunicationQueryReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.QueryOrderBasicInfoReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.QueryOrderStatusIsChangedReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.RepaymentTrialReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.UpdateChangedOrderStatusReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.YztBindCardReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.YztBindCardSendSmsReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.YztDynCodeReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.reqDTO.YztPicCodeReqDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.BasicInfoInitResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.BasicInfoSaveResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.BindCardInitResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.CheckApplyStatusResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.CommunicationFirstLoginResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.CommunicationInitResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.CommunicationSecondInitResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.CommunicationSecondLoginResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.ConfirmLoanApplyResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.ContactInfoInitResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.ContactInfoSaveResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.ContractResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.HousingInfoInitResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.HousingInfoSaveResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.ImproveApplyInfoResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.LoanApplyInitResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.LoanApplySaveResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.LoanProcessCountResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.LoanProcessDetailResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.LoanProcessListResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.PhoneCommunicationQueryResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.ProductCityInfoResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.QueryOrderBasicInfoResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.QueryOrderStatusIsChangedResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.RepaymentTrialResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.UpdateChangedOrderStatusResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.YztBindCardResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.YztBindCardSendSmsResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.YztDynCodeResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.dto.resDTO.YztPicCodeResDTO;
import com.zhuanyi.vjwealth.loan.housingFundLoan.webservice.IApplyHousingFundLoanDubboService;
import com.zhuanyi.vjwealth.loan.liutongbao.dto.AdvExpireReq;
import com.zhuanyi.vjwealth.loan.liutongbao.webservice.IApplyLoanLtbDubboService;
import com.zhuanyi.vjwealth.loan.order.cana.vo.BorrowRecordListVo;
import com.zhuanyi.vjwealth.loan.order.cana.vo.BorrowRecordQuery;
import com.zhuanyi.vjwealth.loan.order.cana.vo.PaymentDetailsVo;
import com.zhuanyi.vjwealth.loan.order.cana.vo.RecordInfoVo;
import com.zhuanyi.vjwealth.loan.order.vo.ClientUserInfoDTO;
import com.zhuanyi.vjwealth.loan.order.vo.ContractVo;
import com.zhuanyi.vjwealth.loan.order.vo.DirectContactInformationVo;
import com.zhuanyi.vjwealth.loan.order.vo.ImprovePersonalInformationVo;
import com.zhuanyi.vjwealth.loan.order.vo.IntentionPersonalInformationVo;
import com.zhuanyi.vjwealth.loan.order.vo.PersonalInformationVo;
import com.zhuanyi.vjwealth.loan.order.vo.RepaymentPlanQueryVo;
import com.zhuanyi.vjwealth.loan.order.webservice.IApplyFinancingLoanDubboService;
import com.zhuanyi.vjwealth.loan.order.webservice.IApplyForCreditDubboService;
import com.zhuanyi.vjwealth.loan.order.webservice.ICanaApplyForCreditDubboService;
import com.zhuanyi.vjwealth.loan.order.webservice.ICanaMyBorrowDubboService;
import com.zhuanyi.vjwealth.loan.order.webservice.ICreditRouteDubboService;
import com.zhuanyi.vjwealth.loan.order.webservice.ILoanApplicationDubboService;
import com.zhuanyi.vjwealth.loan.product.vo.ProductCityInfoVo;
import com.zhuanyi.vjwealth.loan.product.webservice.IProductCityInfoDubboService;
import com.zhuanyi.vjwealth.loan.product.webservice.IProductInfoDubboService;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.ProductCityDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.RepayOrderDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.AccumulationOrderDetailReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.AccumulationOrderRepayDetailReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.AllLoanOrderReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.CheckRepayOrdersReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.ExpireRepayReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.InitiReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.LoanOrderDetailReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.LoanOrderReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.NoRepayInfoReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.NoRepayOrderDetailReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.QueryLoanOrdersReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.QueryNoRepayLoanOrdersReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.QueryRepayAmountReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.QueryRepayOrdersReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.SaveRepayOrderReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.reqDTO.ShowRepayPlansReqDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.AccumulationOrderDetailResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.AccumulationOrderRepayDetailResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.AllLoanOrderInfoResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.AllLoanOrderResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.CheckRepayOrdersResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.CheckRepayPlanResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.CreditExistResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.ExpireRepayResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.InitiResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.LoanOrderDetailResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.LoanOrderResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.NoRepayInfoResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.NoRepayOrderDetailResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.OtherLoanOrderLeftResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.ProductsInfoResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.QueryLoanOrdersResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.QueryNoRepayLoanOrdersResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.QueryRepayAmountResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.QueryRepayOrdersResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.RouteCheckResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.SaveRepayOrderResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.dto.resDTO.ShowRepayPlansResDTO;
import com.zhuanyi.vjwealth.loan.wageAdvance.webservice.IApplyWageAdvanceDubboService;


@Service
public class AllimplService implements ILoanApplicationDubboService,
		IApplyForCreditDubboService, ICanaApplyForCreditDubboService,
		IProductCityInfoDubboService, ICreditRouteDubboService,
		ITaskDetailsInfoDubboService, IProductInfoDubboService,
		IClientInfoDubboService, IApplyFinancingLoanDubboService,
		IApplyLoanLtbDubboService, 
		ICanaMyBorrowDubboService, ICanaLoanInterfaceService,
		IApplyWageAdvanceDubboService, IApplyHousingFundLoanDubboService {

	@Override
	public Map<String, Object> applyHouseFundLoanIntention(
			IntentionPersonalInformationVo arg0) throws AppException {
		// TODO Auto-generated method stub
		return  new HashMap<String, Object>();
	}

	@Override
	public void authenticationCallBack(String arg0) throws AppException {
		// TODO Auto-generated method stub

	}

	@Override
	public BasicInfoInitResDTO basicInfoInit(BasicInfoInitReqDTO arg0) {
		// TODO Auto-generated method stub
		return new BasicInfoInitResDTO();
	}

	@Override
	public BasicInfoSaveResDTO basicInfoSave(BasicInfoSaveReqDTO arg0) {
		// TODO Auto-generated method stub
		return new BasicInfoSaveResDTO();
	}

	@Override
	public YztBindCardResDTO bindCard(YztBindCardReqDTO arg0) {
		// TODO Auto-generated method stub
		return new YztBindCardResDTO();
	}

	@Override
	public BindCardInitResDTO bindCardInit(BindCardInitReqDTO arg0) {
		// TODO Auto-generated method stub
		return new BindCardInitResDTO();
	}

	@Override
	public YztBindCardSendSmsResDTO bindCardSendSms(
			YztBindCardSendSmsReqDTO arg0) {
		// TODO Auto-generated method stub
		return new YztBindCardSendSmsResDTO("200", "ok");
	}

	@Override
	public CheckApplyStatusResDTO checkApplyStatus(CheckApplyStatusReqDTO arg0) {
		// TODO Auto-generated method stub
		return new CheckApplyStatusResDTO();
	}

	@Override
	public CommunicationFirstLoginResDTO communicationFirstLogin(
			CommunicationFirstLoginReqDTO arg0) {
		// TODO Auto-generated method stub
		return new CommunicationFirstLoginResDTO();
	}

	@Override
	public CommunicationInitResDTO communicationLoginInit(
			CommunicationInitReqDTO arg0) {
		// TODO Auto-generated method stub
		return new CommunicationInitResDTO();
	}

	@Override
	public CommunicationSecondLoginResDTO communicationSecondLogin(
			CommunicationSecondLoginReqDTO arg0) {
		// TODO Auto-generated method stub
		return new CommunicationSecondLoginResDTO();
	}

	@Override
	public CommunicationSecondInitResDTO communicationSecondLoginInit(
			CommunicationSecondInitReqDTO arg0) {
		// TODO Auto-generated method stub
		return new CommunicationSecondInitResDTO();
	}

	@Override
	public ConfirmLoanApplyResDTO confirmLoanApply(ConfirmLoanApplyReqDTO arg0) {
		// TODO Auto-generated method stub
		return new ConfirmLoanApplyResDTO();
	}

	@Override
	public ContactInfoInitResDTO contactInfoInit(ContactInfoInitReqDTO arg0) {
		// TODO Auto-generated method stub
		return new ContactInfoInitResDTO();
	}

	@Override
	public ContactInfoSaveResDTO contactInfoSave(ContactInfoSaveReqDTO arg0) {
		// TODO Auto-generated method stub
		return new ContactInfoSaveResDTO();
	}

	@Override
	public LoanProcessCountResDTO countLoanProcess(LoanProcessCountReqDTO arg0) {
		// TODO Auto-generated method stub
		return new LoanProcessCountResDTO();
	}

	@Override
	public boolean existUserHouseFundIntentionInfo(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean existUserHouseFundIntentionInfoByIdCard(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public HousingInfoInitResDTO getHouseFundCityLoginInfo(String arg0)
			throws AppException {
		// TODO Auto-generated method stub
		return new HousingInfoInitResDTO();
	}

	@Override
	public Double getHouseFundLoanReturnAmountForHYB(List<String> arg0)
			throws AppException {
		// TODO Auto-generated method stub
		return 0.00;
	}

	@Override
	public ProductCityInfoResDTO getProductCityInfo(String arg0) {
		// TODO Auto-generated method stub
		return new ProductCityInfoResDTO();
	}

	@Override
	public HousingInfoInitResDTO housingFundInfoInit(HousingInfoInitReqDTO arg0) {
		// TODO Auto-generated method stub
		return new HousingInfoInitResDTO();
	}

	@Override
	public HousingInfoSaveResDTO housingInfoSave(HousingInfoSaveReqDTO arg0) {
		// TODO Auto-generated method stub
		return new HousingInfoSaveResDTO();
	}

	@Override
	public HousingfundResponseVO housingInfoSaveV2(HousingInfoSaveReqDTO arg0)
			throws AppException {
		// TODO Auto-generated method stub
		return new HousingfundResponseVO();
	}

	@Override
	public ImproveApplyInfoResDTO improveApplyInfo(ImproveApplyInfoReqDTO arg0) {
		// TODO Auto-generated method stub
		return new ImproveApplyInfoResDTO();
	}

	@Override
	public LoanApplyInitResDTO loanApplyInit(LoanApplyInitReqDTO arg0) {
		// TODO Auto-generated method stub
		return new LoanApplyInitResDTO();
	}

	@Override
	public LoanApplySaveResDTO loanApplySave(LoanApplySaveReqDTO arg0) {
		// TODO Auto-generated method stub
		return new LoanApplySaveResDTO();
	}

	@Override
	public LoanProcessDetailResDTO loanProcessDetail(
			LoanProcessDetailReqDTO arg0) {
		// TODO Auto-generated method stub
		return new LoanProcessDetailResDTO();
	}

	@Override
	public LoanProcessListResDTO loanProcessList(LoanProcessListReqDTO arg0) {
		// TODO Auto-generated method stub
		return new LoanProcessListResDTO();
	}

	@Override
	public FundLoanBankInfo queryFundLoanBankInfo() {
		// TODO Auto-generated method stub
		return new FundLoanBankInfo();
	}

	@Override
	public QueryOrderBasicInfoResDTO queryHouseFundOrderInfoByUserId(String arg0)
			throws AppException {
		// TODO Auto-generated method stub
		return new QueryOrderBasicInfoResDTO();
	}

	@Override
	public QueryOrderBasicInfoResDTO queryOrderBasicInfo(
			QueryOrderBasicInfoReqDTO arg0) {
		// TODO Auto-generated method stub
		return new QueryOrderBasicInfoResDTO();
	}

	@Override
	public QueryOrderStatusIsChangedResDTO queryOrderStatusIsChanged(
			QueryOrderStatusIsChangedReqDTO arg0) {
		// TODO Auto-generated method stub
		return new QueryOrderStatusIsChangedResDTO();
	}

	@Override
	public ContractResDTO queryYztContract(ContractReqDTO arg0) {
		// TODO Auto-generated method stub
		return new ContractResDTO();
	}

	@Override
	public HousingfundResponseVO refreshValidateCode(HousingInfoSaveReqDTO arg0)
			throws AppException {
		// TODO Auto-generated method stub
		return new HousingfundResponseVO();
	}

	@Override
	public RepaymentTrialResDTO repaymentTrial(RepaymentTrialReqDTO arg0) {
		// TODO Auto-generated method stub
		return new RepaymentTrialResDTO();
	}

	@Override
	public PhoneCommunicationQueryResDTO sendPhoneCommunicationQuery(
			PhoneCommunicationQueryReqDTO arg0) {
		// TODO Auto-generated method stub
		return new PhoneCommunicationQueryResDTO();
	}

	@Override
	public YztDynCodeResDTO sendYztDynCode(YztDynCodeReqDTO arg0) {
		// TODO Auto-generated method stub
		return new YztDynCodeResDTO("200", "ok");
	}

	@Override
	public YztPicCodeResDTO sendYztPicCode(YztPicCodeReqDTO arg0) {
		// TODO Auto-generated method stub
		return new YztPicCodeResDTO("200", "ok");
	}

	@Override
	public UpdateChangedOrderStatusResDTO updateChangedOrderStatus(
			UpdateChangedOrderStatusReqDTO arg0) {
		// TODO Auto-generated method stub
		return new UpdateChangedOrderStatusResDTO();
	}

	@Override
	public Map<String, Object> updateHouseFundLoanIntentionInfo(
			IntentionPersonalInformationVo arg0) throws AppException {
		// TODO Auto-generated method stub
		return new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> updateHouseFundLoanIntentionInfoByPhone(
			IntentionPersonalInformationVo arg0) throws AppException {
		// TODO Auto-generated method stub
		return new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> updateHouseFundLoanIntentionInfoForSSD(
			IntentionPersonalInformationVo arg0) throws AppException {
		// TODO Auto-generated method stub
		return new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> uploadIdentityFormUpdate(String arg0)
			throws AppException {
		// TODO Auto-generated method stub
		return new HashMap<String, Object>();
	}

	@Override
	public ComResDTO yztLoanCreditAudit(String arg0) {
		// TODO Auto-generated method stub
		return new ComResDTO();
	}

	@Override
	public CheckRepayOrdersResDTO checkRepayOrders(CheckRepayOrdersReqDTO arg0) {
		// TODO Auto-generated method stub
		return new CheckRepayOrdersResDTO();
	}

	@Override
	public CheckRepayPlanResDTO checkRepayPlanIsRepay(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return new CheckRepayPlanResDTO();
	}

	@Override
	public com.zhuanyi.vjwealth.loan.wageAdvance.dto.ComResDTO examineLoanOrder(
			String arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return new com.zhuanyi.vjwealth.loan.wageAdvance.dto.ComResDTO();
	}

	@Override
	public ExpireRepayResDTO expireUpdateRepay(ExpireRepayReqDTO arg0) {
		// TODO Auto-generated method stub
		return new ExpireRepayResDTO();
	}

	@Override
	public AccumulationOrderDetailResDTO getAccumulationOrderDetail(
			AccumulationOrderDetailReqDTO arg0) {
		// TODO Auto-generated method stub
		return new AccumulationOrderDetailResDTO();
	}

	@Override
	public AccumulationOrderRepayDetailResDTO getAccumulationOrderRepayDetail(
			AccumulationOrderRepayDetailReqDTO arg0) {
		// TODO Auto-generated method stub
		return new AccumulationOrderRepayDetailResDTO();
	}

	@Override
	public AllLoanOrderInfoResDTO getAllLoanOrderInfo(String arg0) {
		// TODO Auto-generated method stub
		return new AllLoanOrderInfoResDTO();
	}

	@Override
	public AllLoanOrderResDTO getAllLoanOrderList(AllLoanOrderReqDTO arg0) {
		// TODO Auto-generated method stub
		return new AllLoanOrderResDTO();
	}

	@Override
	public InitiResDTO getInitiData(InitiReqDTO arg0) {
		// TODO Auto-generated method stub
		return new InitiResDTO();
	}

	@Override
	public String getLatestNeedRepayTime(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public LoanOrderDetailResDTO getLoanOrderDetail(LoanOrderDetailReqDTO arg0) {
		// TODO Auto-generated method stub
		return new LoanOrderDetailResDTO();
	}

	@Override
	public NoRepayInfoResDTO getNoRepayInfo(NoRepayInfoReqDTO arg0) {
		// TODO Auto-generated method stub
		return new NoRepayInfoResDTO();
	}

	@Override
	public NoRepayOrderDetailResDTO getNoRepayLoanOrderDetail(
			NoRepayOrderDetailReqDTO arg0) {
		// TODO Auto-generated method stub
		return new NoRepayOrderDetailResDTO();
	}

	@Override
	public QueryLoanOrdersResDTO getQueryLoanOrders(QueryLoanOrdersReqDTO arg0) {
		// TODO Auto-generated method stub
		return new QueryLoanOrdersResDTO();
	}

	@Override
	public QueryNoRepayLoanOrdersResDTO getQueryNoRepayLoanOrders(
			QueryNoRepayLoanOrdersReqDTO arg0) {
		// TODO Auto-generated method stub
		return new QueryNoRepayLoanOrdersResDTO();
	}

	@Override
	public QueryRepayAmountResDTO getQueryRepayAmountByIds(
			QueryRepayAmountReqDTO arg0) {
		// TODO Auto-generated method stub
		return new QueryRepayAmountResDTO();
	}

	@Override
	public QueryRepayOrdersResDTO getQueryRepayOrders(
			QueryRepayOrdersReqDTO arg0) {
		// TODO Auto-generated method stub
		return new QueryRepayOrdersResDTO();
	}

	@Override
	public RouteCheckResDTO getRouteCheck(String arg0) {
		// TODO Auto-generated method stub
		return new RouteCheckResDTO();
	}

	@Override
	public ShowRepayPlansResDTO getShowRepayPlans(ShowRepayPlansReqDTO arg0) {
		// TODO Auto-generated method stub
		return new ShowRepayPlansResDTO();
	}

	@Override
	public int insertClientCreditInfo(ClientCreditInfoDTO arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CreditExistResDTO isClientCreditExist(String arg0) {
		// TODO Auto-generated method stub
		return new CreditExistResDTO();
	}

	@Override
	public OtherLoanOrderLeftResDTO isOtherLoanOrderLeft(String arg0) {
		// TODO Auto-generated method stub
		return new OtherLoanOrderLeftResDTO();
	}

	@Override
	public ProductsInfoResDTO mineCreditInvestigationWay() {
		// TODO Auto-generated method stub
		return new ProductsInfoResDTO();
	}

	@Override
	public AllLoanOrderInfoResDTO queryBorrowInfo(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return new AllLoanOrderInfoResDTO();
	}

	@Override
	public RepayOrderDTO repayOrderDetailDue(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return new RepayOrderDTO();
	}

	@Override
	public RepayOrderDTO repayOrderDetailEarly(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return new RepayOrderDTO();
	}

	@Override
	public LoanOrderResDTO saveLoanOrder(LoanOrderReqDTO arg0) {
		// TODO Auto-generated method stub
		return new LoanOrderResDTO();
	}

	@Override
	public SaveRepayOrderResDTO saveRepayOrder(SaveRepayOrderReqDTO arg0) {
		// TODO Auto-generated method stub
		return new SaveRepayOrderResDTO();
	}

	@Override
	public Map<String, String> wageAdvanceContract(String arg0, String arg1,
			String arg2) {
		// TODO Auto-generated method stub
		return new HashMap<String, String>();
	}

	@Override
	public ApplyCreditRsp applyCredit(ApplyCreditReq arg0) {
		// TODO Auto-generated method stub
		return new ApplyCreditRsp();
	}

	@Override
	public ApplyLoanRsp applyLoan(ApplyLoanReq arg0) {
		// TODO Auto-generated method stub
		return new ApplyLoanRsp();
	}

	@Override
	public ExecuteConfirmRepaymentRsp executeConfirmRepayment(
			ExecuteConfirmRepaymentReq arg0) {
		// TODO Auto-generated method stub
		return new ExecuteConfirmRepaymentRsp();
	}

	@Override
	public GenContractRsp genContract(GenContractReq arg0) {
		// TODO Auto-generated method stub
		return new GenContractRsp();
	}

	@Override
	public ContractDataRsp getContractData(String arg0) {
		// TODO Auto-generated method stub
		return new ContractDataRsp();
	}

	@Override
	public String getPdfContract(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public QueryCreditRsp queryCredit(QueryCreditReq arg0) {
		// TODO Auto-generated method stub
		return new QueryCreditRsp();
	}

	@Override
	public List<QueryInterestRsp> queryInterest(QueryInterestReq arg0) {
		// TODO Auto-generated method stub
		return new ArrayList<QueryInterestRsp>();
	}

	@Override
	public QueryLoanRsp queryLoan(QueryLoanReq arg0) {
		// TODO Auto-generated method stub
		return new QueryLoanRsp();
	}

	@Override
	public QueryLoanListRsp queryLoanList(QueryLoanListReq arg0) {
		// TODO Auto-generated method stub
		return new QueryLoanListRsp();
	}

	@Override
	public QueryLoanStateRsp queryLoanState(QueryLoanStateReq arg0) {
		// TODO Auto-generated method stub
		return new QueryLoanStateRsp();
	}

	@Override
	public QueryFundsRsp queryMatchingFunds(QueryFundsReq arg0) {
		// TODO Auto-generated method stub
		return new QueryFundsRsp();
	}

	@Override
	public QueryPrepareRepaymentRsp queryPrepareRepayment(
			QueryPrepareRepaymentReq arg0) {
		// TODO Auto-generated method stub
		return new QueryPrepareRepaymentRsp();
	}

	@Override
	public void sendVerification(SendVerificationReq arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<RecordInfoVo> allBorrowRecord(BorrowRecordQuery arg0) {
		// TODO Auto-generated method stub
		return new ArrayList<RecordInfoVo>();
	}

	@Override
	public List<RecordInfoVo> allRepaymentRecord(BorrowRecordQuery arg0) {
		// TODO Auto-generated method stub
		return new ArrayList<RecordInfoVo>();
	}

	@Override
	public PaymentDetailsVo borrowRecordDetail(String arg0, String arg1)
			throws AppException {
		// TODO Auto-generated method stub
		return new PaymentDetailsVo();
	}

	@Override
	public BorrowRecordListVo borrowRecordList(BorrowRecordQuery arg0)
			throws AppException {
		// TODO Auto-generated method stub
		return new BorrowRecordListVo();
	}

//	@Override
//	public Map<String, Object> informationConfirmationSMSVerificationSave(
//			String arg0, ApplyLoanReq arg1) throws AppException {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public void saveContractFileId(String arg0, String arg1)
//			throws AppException {
//		// TODO Auto-generated method stub
//
//	}

	@Override
	public String expireRepay(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String queryLoanOrderDetail(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String queryLoanOrders(String arg0, Integer arg1) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String queryNoRepayInfo(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String queryNoRepayLoanDetail(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String queryNoRepayLoanOrders(String arg0, Integer arg1) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String queryRepayAmountById(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String queryRepayAmountByIds(String arg0, String[] arg1) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String queryRepayOrderTotal(String arg0, String arg1, String arg2,
			String arg3) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String queryRepayOrders(String arg0, String arg1, Integer arg2) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String queryRepayOrdersByOrderIds(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String queryRepayOrdersTotal(String arg0, String[] arg1, String arg2) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String queryRepayType() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String saveLoanOrder(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String saveRepayOrder(String arg0, String arg1, String arg2,
			String arg3) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String saveRepayOrders(String arg0, String[] arg1, String arg2) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void updateBatchOrderLoanRepayAdvExpire(List<AdvExpireReq> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public String applyLoan(String arg0, String arg1, Long arg2) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String applyLoan(String arg0, String arg1, Long arg2, String arg3) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String investmentTargetValue() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String queryLoanDetailed(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String queryLoanList(String[] arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String queryLoanList(String arg0, String[] arg1, Integer arg2) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String rePayNotice(String arg0, Integer arg1) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void rebuildRepayPlan(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public String totalInterest(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String trialRepayPlan(String arg0, Long arg1) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String queryClientCreditAccountInfo(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public int queryClientCreditQuota(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, String> userLogin(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return new HashMap<String, String> ();
	}

	@Override
	public Boolean validatorClientIdAndUuid(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ProductsInfoResDTO creditInvestigationWay() {
		// TODO Auto-generated method stub
		return new ProductsInfoResDTO();
	}

	@Override
	public String selectProductCityInfo(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String selectProductInfoByPage(Integer arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String selectProductRateAndPeriodById(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String selectProductRateById(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String selectProductRepayTypeById(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String bindCardTaskFinish(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String clientTaskInfoAudit(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String financialQuotaTaskFinish(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String housingfundTaskFinish(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String inviteRegisterTaskFinish(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String selectTaskListDetailed(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String selectTaskListSimple(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String selectValidTaskListDetailed(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String selectValidTaskListSimple(String arg0) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Map<String, Object> cleanData(String arg0) throws AppException {
		// TODO Auto-generated method stub
		return new HashMap<String, Object>();
	}

	@Override
	public List<ProductCityDTO> selectProductCitiesByProductId(String arg0) {
		// TODO Auto-generated method stub
		return new ArrayList<ProductCityDTO> ();
	}

	@Override
	public ProductCityInfoVo selectProductCityByCityCode(String arg0,
			String arg1) {
		// TODO Auto-generated method stub
		return new ProductCityInfoVo();
	}

	@Override
	public String selectProductCityCodeByCityName(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Map<String, Object> creditApplicationInit(String arg0)
			throws AppException {
		// TODO Auto-generated method stub
		return new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> creditApplicationInit(String arg0, String arg1)
			throws AppException {
		// TODO Auto-generated method stub
		return new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> fundAccountInit(String arg0, String arg1,
			String arg2) throws AppException {
		// TODO Auto-generated method stub
		return new HashMap<String, Object>();		
	}

	@Override
	public ImprovePersonalInformationVo improvePersonalInformationInit(
			String arg0, String arg1) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> improvePersonalInformationSave(
			PersonalInformationVo arg0) throws AppException {
		// TODO Auto-generated method stub
		return new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> socialSecurityAccountInit(String arg0,
			String arg1, String arg2) throws AppException {
		// TODO Auto-generated method stub
		return new HashMap<String, Object>();
	}

	@Override
	public void synOrderUserInfo(ClientUserInfoDTO arg0) throws AppException {
		// TODO Auto-generated method stub

	}


	@Override
	public Map<String, Object> amountAndRepaymentInstallmentFormSave(
			RepaymentPlanQueryVo arg0) throws AppException {
		// TODO Auto-generated method stub
		return new HashMap<String, Object>();
	}


	@Override
	public Map<String, Object> directContactInformationFormSave(
			DirectContactInformationVo arg0) throws AppException {
		// TODO Auto-generated method stub
		return  new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> dynamicallyGeneratedRepaymentDetail(
			RepaymentPlanQueryVo arg0) throws AppException {
		// TODO Auto-generated method stub
		return  new HashMap<String, Object>();
	}


	@Override
	public Map<String, Object> informationConfirmationSMSVerificationSave(
			String arg0, String arg1, String arg2) throws AppException {
		// TODO Auto-generated method stub
		return  new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> informationConfirmationSMSVerificationSendSMSNotice(
			String arg0, String arg1, String arg2, String arg3)
			throws AppException {
		// TODO Auto-generated method stub
		return  new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> informationConfirmationSave(String arg0,
			String arg1) throws AppException {
		// TODO Auto-generated method stub
		return  new HashMap<String, Object>();
	}

	@Override
	public Map<String, String> investmentContractTemplate(String arg0,
			String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> investmentContractTemplateForPledgeInvest(
			String arg0, String arg1, String arg2, String arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> loanApplicationInit(String arg0)
			throws AppException {
		// TODO Auto-generated method stub
		return  new HashMap<String, Object>();
	}


	@Override
	public Map<String, Object> loanContractList(String arg0, String arg1)
			throws AppException {
		// TODO Auto-generated method stub
		return  new HashMap<String, Object>();
	}

	@Override
	public List<ContractVo> loanProductContractList(String arg0, String arg1)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> repaymentPeriodDetail(String arg0, String arg1)
			throws AppException {
		// TODO Auto-generated method stub
		return  new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> uploadIdentityFormInit(String arg0, String arg1)
			throws AppException {
		// TODO Auto-generated method stub
		return  new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> uploadIdentityFormSave(String arg0)
			throws AppException {
		// TODO Auto-generated method stub
		return  new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> amountAndRepaymentInstallmentFormInit(
			String arg0, String arg1) throws AppException {
		// TODO Auto-generated method stub
		return  new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> directContactInformationFormIniti(String arg0,
			String arg1) throws AppException {
		// TODO Auto-generated method stub
		return  new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> informationConfirmationIniti(String arg0,
			String arg1) throws AppException {
		// TODO Auto-generated method stub
		return  new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> informationConfirmationSMSVerificationIniti(
			String arg0, String arg1) throws AppException {
		// TODO Auto-generated method stub
		return  new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> loanApplicationInit(String arg0, String arg1)
			throws AppException {
		// TODO Auto-generated method stub
		return  new HashMap<String, Object>();
	}

}
