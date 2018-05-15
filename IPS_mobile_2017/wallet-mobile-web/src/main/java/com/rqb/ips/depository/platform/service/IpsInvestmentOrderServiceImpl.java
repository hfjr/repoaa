package com.rqb.ips.depository.platform.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.fab.core.template.ITemplateService;
import com.rqb.ips.depository.platform.faced.IpsInvestmentOrderService;
import com.vj.vbus.event.dto.EventParam;
import com.vj.vbus.event.dto.MessageParam;
import com.vj.vbus.service.IEventInstanceService;
import com.vjwealth.event.api.dto.ExcuteServiceRequestDTO;
import com.vjwealth.event.api.service.IExcuteEventService;
import com.zhuanyi.vjwealth.wallet.mobile.common.server.service.ICommConfigsQueryService;
import com.zhuanyi.vjwealth.wallet.mobile.common.util.BizCommonConstant;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IInvestmentOrderService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.ContractDeatilQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.InvestmentDetailQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.InvestmentRecordSummaryQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.KeyValueDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.NewestActivityListDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.OrderInitQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.PayRollDetailDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.PlaceOrderReturnDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.RepaymentPlanListDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.SalaryBillListQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UploadFileInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UploadIdentityPhotosDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UserCertificationDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UserInvestmentListQueryDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.account.UserEAccountHomeDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.account.UserMAccountHomeDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.account.UserTAccountHomeDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.mapper.IInvestmentOrderMapper;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.mapper.IProductFinaceMapper;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.mapper.IUserAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.constant.UserValidationMethodConstant;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.IUserInviteInfoMapper;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.integration.mapper.IUserQueryMapper;
import com.zhuanyi.vjwealth.wallet.service.file.upload.server.service.ICommonAttachmentOperate;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBLoginUserDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBRfReturnDTO;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserAccountService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.server.service.IMBUserService;

@Service
public class IpsInvestmentOrderServiceImpl implements IpsInvestmentOrderService {

	@Remote
	private ICommonAttachmentOperate commonAttachmentOperate;

	@Autowired
	private IInvestmentOrderMapper investmentOrderMapper;
	
	@Autowired
	private ITemplateService templadteService;
	
	@Autowired
	private IUserQueryMapper userQueryMapper;
	
	@Autowired
	private IUserAccountQueryMapper userAccountQueryMapper;
	
	@Remote
    private IMBUserService mbUserService;

	@Remote
    private IMBUserAccountService mbUserAccountService;

	@Autowired
	private IEventInstanceService eventInstanceService;

	@Autowired
	private IUserInviteInfoMapper userInviteInfoMapper;

    @Value("${file.types}")
    private String fileTypes;

	@Autowired
	private ICommConfigsQueryService commConfigsQueryService;

	@Autowired
	private IProductFinaceMapper productFinaceMapper;
	
	@Autowired
	private IExcuteEventService excuteEventService;

	@Override
	public OrderInitQueryDTO queryProductCanBuy(String productId,String userId) {


//		判断产品是否在维护模式
		if(this.isMaintenanceMode()){
			throw new AppException("功能正在升级中,请稍后再试");
		}

		//	1. 校验
		validatorUserId(userId);
		validatorProductId(productId);
		//	2. 订单初始化查询产品信息
		OrderInitQueryDTO oqd = investmentOrderMapper.queryProductCanBuy(productId,userId);
		if(oqd == null){
			throw new AppException("产品信息初始化失败");
		}

		//TODO 调用创建token的方法，并且返回给客户端保存 createToken()



		return oqd;
	}

	public OrderInitQueryDTO queryProductCanBuyV2(String productId, String userId) {

//		判断产品是否在维护模式
		if(this.isMaintenanceMode()){
			throw new AppException("功能正在升级中,请稍后再试");
		}

		//	1. 校验
		validatorUserId(userId);
		validatorProductId(productId);
		//	2. 订单初始化查询产品信息
		OrderInitQueryDTO oqd = investmentOrderMapper.queryProductCanBuyV2(productId,userId);
		if(oqd == null){
			throw new AppException("产品信息初始化失败");
		}

		return oqd;
	}

	private void validatorUserId(String userId){
		if(StringUtils.isBlank(userId)){
			throw new AppException("用户ID不能为空");
		}
	}
	
	private void validatorOrderId(String orderId){
		if(StringUtils.isBlank(orderId)){
			throw new AppException("订单编号不能为空");
		}
	}

	private void validatorLoanCode(String loanCode){
		if(StringUtils.isBlank(loanCode)){
			throw new AppException("贷款订单编号不能为空");
		}
	}
	
	private void validatorProductId(String productId){
		if(StringUtils.isBlank(productId)){
			throw new AppException("产品编号不能为空");
		}
	}
	
	private void validatorPage(String page){
		if(StringUtils.isBlank(page)){
			throw new AppException("页码不能为空");
		}
		
		if(!StringUtils.isNumeric(page) || Integer.parseInt(page) < 1 || Integer.parseInt(page)%1>0){
			throw new AppException("页码数值不合法，必须为大于0的整数");
		}
	}

	
	@Override
	public Map<String,Object> queryWhetherInvestmentRecord(String userId) {
		validatorUserId(userId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("isHaveInvertmentRecord", investmentOrderMapper.queryWhetherInvestmentRecord(userId));
		return map;
	}


	@Override
	public InvestmentRecordSummaryQueryDTO queryUserInInvestment(String userId) {
		validatorUserId(userId);
		return investmentOrderMapper.queryUserInInvestment(userId);
	}


	@Override
	public Map<String, Object> queryUserInvestmentRecordList(String userId, String investmentStatus, String page) {
		//1.校验参数
		validatorUserInvestmentRecordListParams(userId,investmentStatus,page);
		Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CONFIG_GROUP_WALLET_MOBILE);
		//2.根据类型,返回不同的投资列表
		List<UserInvestmentListQueryDTO> resultList = doQueryUserInvestmentRecord(userId, investmentStatus, page);
		if(resultList!=null){
			for(UserInvestmentListQueryDTO dto:resultList){
				dto.setInvestmentStatusIconURL(MessageFormat.format("{0}/static/pic/investment/{1}",commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL),dto.getInvestmentStatusIconURL()));
			}
		}
		//3.包装返回结果
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("records", resultList);
		returnMap.put("isMore", false);
		if(resultList!=null && resultList.size()>=10){
			returnMap.put("isMore", true);
		}
		return returnMap;
	}


	private List<UserInvestmentListQueryDTO> doQueryUserInvestmentRecord(String userId, String investmentStatus, String page) {
		 Integer currentPage = (Integer.parseInt(page) - 1) * 10;
		 //1.查询投资中的
		 if("investment".equals(investmentStatus)){
			 return investmentOrderMapper.queryUserInvestment(userId,currentPage);
	     }
		 //2.查询投资结束
	     if("investmentEnd".equals(investmentStatus)){
	    	 return investmentOrderMapper.queryUserInvestmentEnd(userId,currentPage);
	     }
	     //3.查询待赔付
	     if("pendingPayment".equals(investmentStatus)){
	    	 return investmentOrderMapper.queryUserPendingPayment(userId,currentPage);
	     }

		 return null;
	}


	private void validatorUserInvestmentRecordListParams(String userId, String investmentStatus, String page) {
		
		validatorUserId(userId);
		validatorPage(page);
		if(StringUtils.isBlank(investmentStatus)){
			throw new AppException("投资类型不能为空");
		}
		
	}


	@Override
	public InvestmentDetailQueryDTO queryUserInvestmentDetail(String userId, String orderId) {
		//1.校验
		validatorUserId(userId);
		validatorOrderId(orderId);
		//2.执行查询
		InvestmentDetailQueryDTO idd = investmentOrderMapper.queryUserInvestmentDetail(userId,orderId);
		if(idd == null){
			throw new AppException("投资信息查询失败");
		}
		return idd; 
	}


	
	@Override
	public NewestActivityListDTO queryUserInvestmentNewsFlow(String orderId) {
		//1.校验订单号
		validatorOrderId(orderId);
		//2.返回投资动态列表
		NewestActivityListDTO nld = investmentOrderMapper.queryUserInvestmentNewsFlow(orderId);
		if(nld == null){
			throw new AppException("查询投资动态信息失败");
		}
		return nld;
	}


	@Override
	public RepaymentPlanListDTO queryUserInvestmentNewsRepaymentPlan(String orderId) {
		//1.校验订单号
		validatorOrderId(orderId);
		//2.返回还款计划列表
		RepaymentPlanListDTO rpd = investmentOrderMapper.queryUserInvestmentNewsRepaymentPlan(orderId);
		if(rpd == null){
			throw new AppException("查询还款计划信息失败");
		}
		return rpd;
	}


	@Override
	public UserCertificationDTO realNameAuthenticateInit(String userId) {
		//1.校验
	    validatorUserId(userId);
		
		//2.如果是可以实名认证，则查询认证表，返回当前的认证状态
	    UserCertificationDTO ucd = investmentOrderMapper.realNameAuthenticateInit(userId);
	    if(ucd==null){
	    	ucd = new UserCertificationDTO();
	    }
	    ucd.setRealName(handlerUserName(ucd.getRealName()));
		return ucd;
	}

	private String handlerUserName(String userName){
		if(!StringUtils.isBlank(userName)){
			String returnName = userName.substring(0, 1);
			String secondName = userName.substring(1,userName.length());
			if(secondName.length()>0){
				for(int i=0;i<secondName.length();i++){
					returnName = returnName+"*";
				}
			}
			return returnName;
		}
		return "";
	}

	@Transactional
    public UploadIdentityPhotosDTO uploadIdentityPhotos(String userId, MultipartFile rightSideFile, MultipartFile reverseSideFile, String uploadSuccessFileId) throws IOException {

		BaseLogger.audit("上传身份证 Start");
        UploadIdentityPhotosDTO uploadIdentityPhotosDTO = new UploadIdentityPhotosDTO();
        List<UploadFileInfoDTO> fileList = new ArrayList<UploadFileInfoDTO>();
        String rightSideFileId = null, reverseSideFileId = null;

        //参数验证
        if (StringUtils.isBlank(userId)) {
            BaseLogger.audit("上传身份证，用户ID不能为空");
            throw new AppException("上传身份证，用户信息不存在");
        }

        //判断文件类型 ,过滤文件
        String[] types = StringUtils.split(fileTypes,",");
        for (MultipartFile file : new MultipartFile[]{rightSideFile, reverseSideFile}) {
            if (null != file && !file.isEmpty()) {
				//验证文件格式
				if (FilenameUtils.isExtension(file.getOriginalFilename(), types)) {
					UploadFileInfoDTO uploadFileInfoDTO = new UploadFileInfoDTO();
					byte[] bytes = file.getBytes();
                    String fileId = commonAttachmentOperate.saveAttachementAndReturnFileId(file.getOriginalFilename(), bytes, "identity-pic");
                    String fileNameCode = FilenameUtils.getBaseName(file.getOriginalFilename());
                    uploadFileInfoDTO.setFileId(fileId);
                    uploadFileInfoDTO.setFileNameCode(fileNameCode);
                    fileList.add(uploadFileInfoDTO);
                } else {
                    BaseLogger.audit("上传文件格式不对");
                }
            }
        }
        uploadIdentityPhotosDTO.setFileList(fileList);

		int fileSize = fileList.size();
		BaseLogger.audit(String.format("上传文件数 fileSize : %s ",fileSize));
        if (Integer.compare(0, fileSize) == 0) {
            uploadIdentityPhotosDTO.setCode("200401");
            uploadIdentityPhotosDTO.setMessage("上传文件失败");
            BaseLogger.audit(String.format("fileSize : %s  上传文件失败",fileSize));
        } else {
            uploadIdentityPhotosDTO.setCode("200400");
            uploadIdentityPhotosDTO.setMessage("上传成功");
            BaseLogger.audit("上传文件成功");
            if (Integer.compare(2, fileSize) == 0) {
                for (UploadFileInfoDTO uploadFileInfoDTO : fileList) {
                    if ("200410".equals(uploadFileInfoDTO.getFileNameCode())) {
                        rightSideFileId = uploadFileInfoDTO.getFileId();
                    } else {
                        reverseSideFileId = uploadFileInfoDTO.getFileId();
                    }
                }

            } else if (Integer.compare(1, fileSize) == 0) {

				// 回传时必须有一个文件为空，一个文件对象不为空；且回传成功文件ID不能为空
				if((null == rightSideFile && null != reverseSideFile) || (null == reverseSideFile && null != rightSideFile)){

					if (StringUtils.isBlank(uploadSuccessFileId) ) {
						BaseLogger.audit("上传身份证，回传成功文件ID不能为空");
						throw new AppException("上传身份证，回传成功文件ID不能为空");
					} else {
						if ("200410".equals(fileList.get(0).getFileNameCode())) {
							rightSideFileId = fileList.get(0).getFileId();
						} else {
							reverseSideFileId = fileList.get(0).getFileId();
						}

						if(StringUtils.isBlank(rightSideFileId)){
							rightSideFileId = uploadSuccessFileId;
						}else{
							reverseSideFileId = uploadSuccessFileId;
						}
					}
				} else {
					// 两个文件不为空，只有一个保存成功 【一个文件格式不对，一个文件格式对】
					if ("200410".equals(fileList.get(0).getFileNameCode())) {
						rightSideFileId = fileList.get(0).getFileId();
					} else {
						reverseSideFileId = fileList.get(0).getFileId();
					}

					if(StringUtils.isBlank(rightSideFileId)){
						rightSideFileId = uploadSuccessFileId;
					}else{
						reverseSideFileId = uploadSuccessFileId;
					}
				}


            }

            //更新用户身份证认证标识 认证表
            //清除认证记录，再保存记录
            investmentOrderMapper.deleteUserCertification(userId);
            investmentOrderMapper.saveUserRealNameCertification(userId, rightSideFileId, reverseSideFileId);

        }
		BaseLogger.audit("上传身份证 End");
        return uploadIdentityPhotosDTO;
    }

	@Override
	@Transactional
	public UploadIdentityPhotosDTO saveUploadIdentityPhotos(String userId, String rightSideFileId, String reverseSideFileId, String userChannelType) {
		BaseLogger.audit(String.format("saveUploadIdentityPhotos 入参数 userId ：%s, reverseSideFileId ：%s , reverseSideFileId ：%s , channelType ：%s ",userId,reverseSideFileId,reverseSideFileId,userChannelType));
		UploadIdentityPhotosDTO uploadIdentityPhotosDTO = new UploadIdentityPhotosDTO();
		List<UploadFileInfoDTO> fileList = new ArrayList<UploadFileInfoDTO>();

		// 参数验证
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(rightSideFileId) || StringUtils.isBlank(reverseSideFileId) || StringUtils.isBlank(userChannelType) ) {
			BaseLogger.audit(String.format("参数验证异常 reverseSideFileId ：%s , reverseSideFileId ：%s , channelType ：%s ",reverseSideFileId,reverseSideFileId,userChannelType));
			throw new AppException("参数不合法,请稍后再试") ;
		}else{
			// 查询 rightSideFileId,reverseSideFileId是否存在验证
			List<String> fileIds = new ArrayList<>();
			fileIds.add(rightSideFileId);
			fileIds.add(reverseSideFileId);
			Boolean isExit = commonAttachmentOperate.isExitAttachementByFileIds(fileIds);
			if (!isExit) {
				BaseLogger.audit("调用文件服务系统［commonAttachmentOperate.isExitAttachementByFileIds］－图片信息不存在");
				throw new AppException("图片信息不存在");
			}
			//更新用户身份证认证标识 认证表
			//清除认证记录，再保存记录
			investmentOrderMapper.deleteUserCertification(userId);
			//TODO 是否加入渠道保存
			investmentOrderMapper.saveUserRealNameCertification(userId, rightSideFileId, reverseSideFileId);
			BaseLogger.audit(String.format("saveUploadIdentityPhotos  userId ：%s channelType ：%s  保存用户认证图片关系成功 ",userId,userChannelType));
			uploadIdentityPhotosDTO.setCode("200400");
			uploadIdentityPhotosDTO.setMessage("保存成功");
			fileList.add(new UploadFileInfoDTO(rightSideFileId,"rightSideFile"));
			fileList.add(new UploadFileInfoDTO(reverseSideFileId,"reverseSideFile"));
		}
		uploadIdentityPhotosDTO.setFileList(fileList);
        return 	uploadIdentityPhotosDTO;
	}

	@Override
	public void deleteUserCertification(String userId) {
		validatorUserId(userId);
		investmentOrderMapper.deleteUserCertification(userId);
	}

	@Override
	public Object queryPayRollList(String userId, String page) {
		//1.校验参数
		validatorUserId(userId);
		validatorPage(page);
		
		//2.查询工资单列表
		Integer currentPage = (Integer.parseInt(page) - 1) * 10;
		List<SalaryBillListQueryDTO> resultList = investmentOrderMapper.queryPayRollList(userId,currentPage);
		
		//3.包装返回结果
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("records", resultList);
		returnMap.put("isMore", false);
		if(resultList!=null && resultList.size()>=10){
			returnMap.put("isMore", true);
		}
		return returnMap;
	}

	@Override
	public Object queryPayRollDetail(String payRollId) {
		if(StringUtils.isBlank(payRollId)){
			throw new AppException("工资单ID不能为空");
		}
		PayRollDetailDTO payRollDetailDTO = new PayRollDetailDTO();
		try{
			Map<String,String> detail = investmentOrderMapper.queryPayRollDetail(payRollId);
			String jsonDetailString = detail.get("payRollDetail");
			List<KeyValueDTO> keyValueDTOList =	JSON.parseArray(jsonDetailString, KeyValueDTO.class);
			payRollDetailDTO.setPayRollDetail(keyValueDTOList);
		}catch(Exception e){
			BaseLogger.error("获取用户工资单详情信息异常"+e);
			throw new AppException("获取用户工资单详情信息异常");
		}
		return payRollDetailDTO;
	}

	
	
	
	@Override
	public Object placeOrder(String userId, String productId, String investmentAmount,String token) {

		//TODO 验证Token validateToken

		validatorPlaceOrderParams(userId,productId,investmentAmount,token);
        BaseLogger.audit(String.format("下单开始  userId :%s ，productId :%s  金额  : %s  ",userId,productId,investmentAmount));
		try{

			MBRfReturnDTO mbRfReturnDTO = mbUserAccountService.applyMaToRf(userId, investmentAmount, productId,token);
			if (null != mbRfReturnDTO) {
				BaseLogger.audit(String.format("下单结束 返回信息为 code : %s  ,message :%s ,remainingInvestment :%s ,paymentInformation :%s ",
						mbRfReturnDTO.getCode(), mbRfReturnDTO.getMessage(), mbRfReturnDTO.getRemainingInvestment(), mbRfReturnDTO.getPaymentInformation()));
			} else {
				BaseLogger.audit("下单结束 返回信息为  MBRfReturnDTO: 为空" );
			}
			//投资增加营销事件 add by tony tang 20160815
//			addEventInstance(userId,investmentAmount,productId);
			
			
			return mbRfReturnDTO;
		}catch (Exception e){
			if( e instanceof  AppException){
				Integer code = ((AppException) e).getKey();
				String message = e.getMessage();
				if(null !=  code){
					BaseLogger.audit(code + " : " +e.getMessage());
				   return new PlaceOrderReturnDTO(code.toString() , message,"","");
				}
				
				
				BaseLogger.error(e.getMessage());
				throw new AppException(message);
			}
			BaseLogger.error(e.getMessage());
			throw new AppException("系统繁忙，请稍后再试");
		}
	}
	
	
	public Object placeOrderByRecommender(String userId, String productId, String investmentAmount,String token,String recommendPhone) {

		//TODO 验证Token validateToken

		validatorPlaceOrderParams(userId,productId,investmentAmount,token);
        BaseLogger.audit(String.format("下单开始  userId :%s ，productId :%s  金额  : %s  ",userId,productId,investmentAmount));
		try{

			MBRfReturnDTO mbRfReturnDTO = mbUserAccountService.applyMaToRf(userId, investmentAmount, productId,token);
			 
			if (null != mbRfReturnDTO) {
				BaseLogger.audit(String.format("下单结束 返回信息为 code : %s  ,message :%s ,remainingInvestment :%s ,paymentInformation :%s ",
						mbRfReturnDTO.getCode(), mbRfReturnDTO.getMessage(), mbRfReturnDTO.getRemainingInvestment(), mbRfReturnDTO.getPaymentInformation()));
			} else {
				BaseLogger.audit("下单结束 返回信息为  MBRfReturnDTO: 为空" );
			}
			//投资增加营销事件 add by tony tang 20160815
//			addEventInstance(userId,investmentAmount,productId);
			
			//增加推荐返佣事件
			this.placeFinanceOrderEvent(userId, mbRfReturnDTO.getOrderNo(), investmentAmount, productId,recommendPhone);
			
			return mbRfReturnDTO;
		}catch (Exception e){
			if( e instanceof  AppException){
				Integer code = ((AppException) e).getKey();
				String message = e.getMessage();
				if(null !=  code){
					BaseLogger.audit(code + " : " +e.getMessage());
				   return new PlaceOrderReturnDTO(code.toString() , message,"","");
				}
				BaseLogger.error(e.getMessage());
				throw new AppException(message);
			}
			BaseLogger.error(e.getMessage());
			throw new AppException("系统繁忙，请稍后再试");
		}
	}
	
	
	
	/**
	 * ips无红包---IPS冻结接口
	 * @param userId
	 * @param productId
	 * @param investmentAmount
	 * @param token
	 * @param recommendPhone
	 * @param couponId 加息券id
	 * @return
	 */
	public Object placeOrderByRecommenderIps(String userId, String productId, String investmentAmount,String token,String recommendPhone, String couponId,String source) {
		
		//TODO 验证Token validateToken
		
		validatorPlaceOrderParams(userId,productId,investmentAmount,token);
		BaseLogger.audit(String.format("下单开始  userId :%s ，productId :%s  金额  : %s  ",userId,productId,investmentAmount));
		try{
			
			Object mbRfReturnDTO = mbUserAccountService.ipsApplyMaToRf(userId, investmentAmount, productId,token,couponId,source);
			
			/*if (null != mbRfReturnDTO) {
				BaseLogger.audit(String.format("下单结束 返回信息为 code : %s  ,message :%s ,remainingInvestment :%s ,paymentInformation :%s ",
						mbRfReturnDTO.getCode(), mbRfReturnDTO.getMessage(), mbRfReturnDTO.getRemainingInvestment(), mbRfReturnDTO.getPaymentInformation()));
			} else {
				BaseLogger.audit("下单结束 返回信息为  MBRfReturnDTO: 为空" );
			}*/
			//投资增加营销事件 add by tony tang 20160815
//			addEventInstance(userId,investmentAmount,productId);
			
			//增加推荐返佣事件   ips 此处事件暂定
			//this.placeFinanceOrderEvent(userId, mbRfReturnDTO.getOrderNo(), investmentAmount, productId,recommendPhone);
			
			return mbRfReturnDTO;
		}catch (Exception e){
			if( e instanceof  AppException){
				Integer code = ((AppException) e).getKey();
				String message = e.getMessage();
				if(null !=  code){
					BaseLogger.audit(code + " : " +e.getMessage());
					return new PlaceOrderReturnDTO(code.toString() , message,"","");
				}
				BaseLogger.error(e.getMessage());
				throw new AppException(message);
			}
			BaseLogger.error(e.getMessage());
			throw new AppException("系统繁忙，请稍后再试");
		}
	}

	@Override
	public Object placeOrder(String userId, String productId, String investmentAmount,String rpId, String clientType, String token) {
		boolean noVipPermission = productFinaceMapper.queryUserBuyVIPProductPermission(userId, productId);
		if(noVipPermission) {//VIP产品且用户没有购买权限
			throw new AppException("该理财产品为特定用户定制产品，如有需要请联系客服，谢谢！");
		}
        if(StringUtils.isBlank(rpId)){
            return placeOrder(userId, productId, investmentAmount,token);
        }

        validatorPlaceOrderParams(userId,productId,investmentAmount,token);

		BaseLogger.audit(String.format("下单开始  userId :%s ，productId :%s  金额  : %s  ",userId,productId,investmentAmount));
        BaseLogger.audit(String.format("下单开始  userId :%s ，productId :%s  红包  : %s  ",userId,productId,rpId));
		try{
			MBRfReturnDTO mbRfReturnDTO = mbUserAccountService.applyMaToRf(userId, investmentAmount, productId,rpId, clientType, token);
			if (null != mbRfReturnDTO) {
				BaseLogger.audit(String.format("下单结束 返回信息为 code : %s  ,message :%s ,remainingInvestment :%s ,paymentInformation :%s ",
						mbRfReturnDTO.getCode(), mbRfReturnDTO.getMessage(), mbRfReturnDTO.getRemainingInvestment(), mbRfReturnDTO.getPaymentInformation()));
			} else {
				BaseLogger.audit("下单结束 返回信息为  MBRfReturnDTO: 为空" );
			}
			
			
			
			//投资增加营销事件 add by tony tang 20160815
//			addEventInstance(userId,investmentAmount,productId);
			return mbRfReturnDTO;
		}catch (Exception e){
			if( e instanceof  AppException){
				Integer code = ((AppException) e).getKey();
				String message = e.getMessage();
				if(null !=  code){
					BaseLogger.audit(code + " : " +e.getMessage());
					return new PlaceOrderReturnDTO(code.toString() , message,"","");
				}
				BaseLogger.error(e.getMessage());
				throw new AppException(message);
			}
			BaseLogger.error(e.getMessage());
			throw new AppException("系统繁忙，请稍后再试");
		}
	}
	
	
	@Override
	public Object placeOrder(String userId, String productId, String investmentAmount, String rpId, String couponId,String clientType, String token, String recommendPhone) {
		boolean noVipPermission = productFinaceMapper.queryUserBuyVIPProductPermission(userId, productId);
		if(noVipPermission) {//VIP产品且用户没有购买权限
			throw new AppException("该理财产品为特定用户定制产品，如有需要请联系客服，谢谢！");
		}
		//推荐人校验
//		if(StringUtils.isNotBlank(recommendPhone)){
//			if(userInviteInfoMapper.queryRecommenderExitByPhone(recommendPhone)<1){
//				throw new AppException("推荐人不存在,请确认填写是否正确");
//			}
//			
//			if(userInviteInfoMapper.querytRecommenderIsSelfByPhone(recommendPhone, userId)>0){
//				throw new AppException("推荐人不能是自己");
//			}
//		}
		
        if(StringUtils.isBlank(rpId)&&StringUtils.isBlank(couponId)){
            return placeOrderByRecommender(userId, productId, investmentAmount, token, recommendPhone);
        }

        validatorPlaceOrderParams(userId,productId,investmentAmount,token);

		BaseLogger.audit(String.format("下单开始  userId :%s ，productId :%s  金额  : %s  ",userId,productId,investmentAmount));
        BaseLogger.audit(String.format("下单开始  userId :%s ，productId :%s  红包  : %s  ",userId,productId,rpId));
		try{
			MBRfReturnDTO mbRfReturnDTO = mbUserAccountService.applyMaToRf(userId, investmentAmount, productId,rpId,couponId, clientType, token);
			if (null != mbRfReturnDTO) {
				BaseLogger.audit(String.format("下单结束 返回信息为 code : %s  ,message :%s ,remainingInvestment :%s ,paymentInformation :%s ",
						mbRfReturnDTO.getCode(), mbRfReturnDTO.getMessage(), mbRfReturnDTO.getRemainingInvestment(), mbRfReturnDTO.getPaymentInformation()));
			} else {
				BaseLogger.audit("下单结束 返回信息为  MBRfReturnDTO: 为空" );
			}
			
			//增加推荐返佣事件
			this.placeFinanceOrderEvent(userId, mbRfReturnDTO.getOrderNo(), investmentAmount, productId,recommendPhone);
			
			
			return mbRfReturnDTO;
		}catch (Exception e){
			if( e instanceof  AppException){
				Integer code = ((AppException) e).getKey();
				String message = e.getMessage();
				if(null !=  code){
					BaseLogger.audit(code + " : " +e.getMessage());
					return new PlaceOrderReturnDTO(code.toString() , message,"","");
				}
				BaseLogger.error(e.getMessage());
				throw new AppException(message);
			}
			BaseLogger.error(e.getMessage());
			throw new AppException("系统繁忙，请稍后再试");
		}
	}
	
	
	private void validatorPlaceOrderParams(String userId, String productId, String investmentAmount,String token) {
		validatorUserId(userId);
		validatorProductId(productId);
		if(!StringUtils.isNumeric(investmentAmount)){
			throw new AppException("投资金额不合法");
		}
		if(Integer.parseInt(investmentAmount)%100>0){
			throw new AppException("投资金额必须为100的整数倍");
		}
		if(StringUtils.isBlank(token)){
			throw new AppException("token失效，订单已经过时.");
		}
		
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,String> investmentContractDetail(String orderId) {
		Map<String,String> returnMap = new HashMap<String,String>();
		if(StringUtils.isBlank(orderId)){
			returnMap.put("flag", "no");
			return returnMap;
		}
		ContractDeatilQueryDTO cdd = investmentOrderMapper.investmentContractDetail(orderId);
		if(cdd == null || StringUtils.isBlank(cdd.getContractFixJson())){
			returnMap.put("flag", "no");
			return returnMap;
		}
		
		try{
			//获取合同固定信息
			Map<String,Object> fixMap = JSONObject.parseObject(cdd.getContractFixJson(),Map.class);
			cdd.setContractFixJson(null);
			//获取合同的用户投资信息
			Map<String,Object> paramMap = JSONObject.parseObject(JSONObject.toJSONString(cdd),Map.class);
			paramMap.putAll(fixMap);
			//获取合同模板
			String content = investmentOrderMapper.investmentContractTemplate(cdd.getProductId());
			
			returnMap.put("flag", "yes");
			returnMap.put("content", generateTemplateContent(content,paramMap));
			return returnMap;
		}catch(Exception e){
			returnMap.put("flag", "no");
			return returnMap;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,String> investmentContractTemplate(String productId) {
		Map<String,String> returnMap = new HashMap<String,String>();
		//1.校验
		if(StringUtils.isBlank(productId)){
			returnMap.put("flag", "no");
			return returnMap;
		}
		try{
			ContractDeatilQueryDTO cdd = new ContractDeatilQueryDTO("","","","","","","","","","","","","","","","","","","","");
			String content = investmentOrderMapper.investmentContractTemplate(productId);
			returnMap.put("flag", "yes");
			returnMap.put("content", generateTemplateContent(content, JSONObject.parseObject(JSONObject.toJSONString(cdd),Map.class)));
			return returnMap;
		}catch(Exception e){
			returnMap.put("flag", "no");
			BaseLogger.error("合同模板异常:"+e);
			return returnMap;
		}
		
	}
	
	// 模板生成
	private String generateTemplateContent(String templateContent,Map<String, Object> templateParam){
		// 1.查询模板
		String content="";
		try{
			// 2.填充模板
			content=templadteService.processByContent(templateContent, templateParam);
		}catch(Exception e){
			throw new AppException("加载模板异常,请检查模板与内容是否匹配:"+e);
		}
		return content; 
	}

	//用户登录
    public Map<String, String> userLogin(String phone, String password) {
        UserValidationMethodConstant.validateLoginInfo(phone, password);
        Map<String, String> returnMap = new HashMap<String, String>();
        MBLoginUserDTO user = mbUserService.loginForApp(phone, password);
        returnMap.put("userId", user.getUserId());
        returnMap.put("uuid", user.getUuid());
        returnMap.put("isSign", user.getSign().equals("1")?"yes":"no");
        return returnMap;
    }

	@Override
	public Boolean isMaintenanceMode() {
		return !investmentOrderMapper.isMaintenanceMode();
	}

	
	
	@Override
	public UserMAccountHomeDTO queryMAccountInfo(String userId) {
		validatorUserId(userId);
		return userAccountQueryMapper.queryMAccountInfo(userId);
	}

	@Override
	public UserEAccountHomeDTO queryEAccountInfo(String userId) {
		validatorUserId(userId);
		return userAccountQueryMapper.queryEAccountInfo(userId);
	}

	@Override
	public UserTAccountHomeDTO queryTAccountInfo(String userId) {
		validatorUserId(userId);
        return userAccountQueryMapper.queryTAccountInfo(userId);
	}

	@Override
	public String queryInvestmentContractNoByUserIdAndLoanCode(@Param("userId") String userId, @Param("loanCode") String loanCode) {
		validatorUserId(userId);
		validatorLoanCode(loanCode);
		return investmentOrderMapper.queryInvestmentContractNoByUserIdAndLoanCode(userId,loanCode);
	}

	private void placeFinanceOrderEvent(String userId,String orderNo,String amount,String productId,String recommendPhone){
		try{
			ExcuteServiceRequestDTO excuteServiceRequestDTO = new ExcuteServiceRequestDTO();
			Map<String, String>	paramMap=new HashMap<String,String>();
			paramMap.put("userId", userId);
			paramMap.put("tradeNo",orderNo);
			paramMap.put("tradePrice", amount);
			paramMap.put("tradeType", "finances");
			paramMap.putAll(productFinaceMapper.queryProductInfoByOrderNo(orderNo));
			paramMap.putAll(userAccountQueryMapper.queryEventInfo(orderNo));
			paramMap.put("recommendUserId",userInviteInfoMapper.queryRecommendUserIdByPhone(recommendPhone));
			excuteServiceRequestDTO.setParamJsonObject(excuteServiceRequestDTO.parseObject(paramMap));
			excuteEventService.excuteAsyncEvent("EV_0004", excuteServiceRequestDTO);
		}catch (Exception ex){
			BaseLogger.error("事情平台EV_0004失败",ex);
		}
	}
	/**
	 * 投资增加营销事件
	 */
	private void addEventInstance(String userId,String amount,String productId){
		//add market event by tony tang 20160815
		MessageParam messageParam=new MessageParam();
		EventParam eventParam=new EventParam();
		eventParam.setRecommendInvestAmount(amount);
		eventParam.setProductId(productId);
		try{
			//查询用户推荐人
			eventParam.setRecommendUserId(userInviteInfoMapper.queryRecommendUserIdByUserId(userId));//推荐人Id
			messageParam.setEventParam(eventParam);
			eventInstanceService.addEventInstance("user_invest",userId,messageParam);
		}catch (Exception ex){
			BaseLogger.error("投资增加营销事件信息异常!",ex);
		}
	}
	public Map<String,Object> queryMAccountInfo(String userId,String userChannelType) {
		validatorUserId(userId);
		UserTAccountHomeDTO userTAccountHomeDTO = userAccountQueryMapper.queryTAccountInfo(userId);
		Map<String, String> commConfigMap = commConfigsQueryService.queryConfigKeyByGroup(BizCommonConstant.CONFIG_GROUP_WALLET_MOBILE);
		Map<String,Object> paramMap=new HashMap<String,Object>();

		paramMap.put("title","存钱罐");
		paramMap.put("investmentAmountLabel", "持有金额(元)");
		paramMap.put("investmentAmount", userTAccountHomeDTO.getTaAvailableAmount());
		paramMap.put("yesterdayReceiveLabel", "昨日收益(元)");
		paramMap.put("yesterdayReceive",userTAccountHomeDTO.getYesterdayReceive());
		paramMap.put("allAreadyReceiveLabel", "累计收益(元)");
		paramMap.put("allAreadyReceive", userTAccountHomeDTO.getAllAreadyReceive());
		paramMap.put("remainingInvestmentLabel", "剩余可投(元)");
		paramMap.put("remainingInvestment", userTAccountHomeDTO.getMaAvailableAmount());
		paramMap.put("annualYieldLabel", "预计年化收益");
		paramMap.put("annualYield",userTAccountHomeDTO.getWeekReceiveRate());
		paramMap.put("startInvestmentAmountLabel", "起投金额(元)");
		paramMap.put("startInvestmentAmount", "100.00");
		paramMap.put("millionCopiesIncomeLabel", "万份收益(元)");
		paramMap.put("frozenAmountLabel", "申请冻结金额(元)");
		paramMap.put("frozenAmountDescLabel", "冻结金额说明");
		paramMap.put("millionCopiesIncome",userTAccountHomeDTO.getEveryReceiveRate());
		;Map<String,String> taConfirmAmountDate=getTAConfirmAmountDate();
		if(new BigDecimal(userTAccountHomeDTO.getFrozenAmount()).compareTo(BigDecimal.ZERO)>0){
			paramMap.put("isShowFrozenAmount", "true");
			paramMap.put("frozenAmount", userTAccountHomeDTO.getFrozenAmount());
			paramMap.put("frozenAmountDesc", MessageFormat.format("{0}确认份额|{1}发放收益",
					taConfirmAmountDate.get("confirmAmountDate"),taConfirmAmountDate.get("profitDate")));
		}else{
			paramMap.put("isShowFrozenAmount", "false");
			paramMap.put("frozenAmount", "");
			paramMap.put("frozenAmountDesc","");
		}
		paramMap.put("productIntroductionLabel", "产品介绍");

		paramMap.put("productIntroductionIconUrl", commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+"/static/icon/product_introduction.png");
		paramMap.put("productIntroductionTips", "T金所由TCL集团联合上海银行成立");
		paramMap.put("productIntroduction", "存钱罐是融桥宝为用户打造的全新活期理财计划,由T金所严格风控,并承诺回购。");

		List<com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO> productDetail=new ArrayList<com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO>();
		productDetail.add(new com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO("百元起投","100元即可投资"));
		productDetail.add(new com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO("超高收益", MessageFormat.format("预计年化收益{0}%",userTAccountHomeDTO.getWeekReceiveRate())));
		productDetail.add(new com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO("安全至上","项目由T金所提供全额担保"));
		productDetail.add(new com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO("实时提现","随存随取，申请提现瞬间到账"));
		paramMap.put("productDetail",productDetail);

		List<com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO> productFeatures=new ArrayList<com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO>();
		productFeatures.add(new com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO("百元起投",commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+"/static/icon/tfax_100.png"));
		productFeatures.add(new com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO("随存随取",commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+"/static/icon/tfax_free.png"));
		productFeatures.add(new com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO("本息担保",commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+"/static/icon/principal_guarantee.png"));
		productFeatures.add(new com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO("高收益率",commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+"/static/icon/tfax_highprofit.png"));
		paramMap.put("productFeatures",productFeatures);

		paramMap.put("productTradingRulesLabel","交易规则");
		paramMap.put("productTradingRulesIconUrl",commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+"/static/icon/trading_rules.png");
		List<com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO> productTradingRules=new ArrayList<com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO>();
		productTradingRules.add(new com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO("转入",MessageFormat.format("现在买入,{0}产生收益，{1}首笔收益到账",
				taConfirmAmountDate.get("confirmAmountDate"),taConfirmAmountDate.get("profitDate"))));
		productTradingRules.add(new com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO("转出","随时可卖，当日到账"));
		paramMap.put("productTradingRules",productTradingRules);

		paramMap.put("warningAndProtectionLabel","风险提示和保障");
		paramMap.put("warningAndProtectionIconUrl",commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+"/static/icon/warning_protection.png");
		paramMap.put("warningAndProtection","风险提示和保障文案");

		paramMap.put("FAQLabel","常见问题");
		paramMap.put("FAQIconUrl",commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)+"/static/icon/product_faq.png");
		paramMap.put("FAQ","常见问题文案");
		paramMap.put("FAQUrl",MessageFormat.format(commConfigMap.get(BizCommonConstant.CONFIG_ITEM_CENTER_HELP),commConfigMap.get(BizCommonConstant.CONFIG_ITEM_HOST_URL)));

		List<com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO> buttonGroup=new ArrayList<com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO>();
		buttonGroup.add(new com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO("转入","turnedInto"));
		if(new BigDecimal(userTAccountHomeDTO.getTaAvailableAmount()).compareTo(BigDecimal.ZERO)>0){
			paramMap.put("code","204700");
			paramMap.put("message", "已投资存钱罐");
			buttonGroup.add(new com.zhuanyi.vjwealth.wallet.mobile.account.server.dto.KeyValueDTO("转出","turnedOut"));
		}else{
			paramMap.put("code","204701");
			paramMap.put("message", "未投资存钱罐");
		}
		paramMap.put("buttonGroup",buttonGroup);
		return paramMap;
	}
	public String queryTaReceiveRate(){
		return userAccountQueryMapper.queryTaReceiveRate();
	}

	/**
	 * 获取T金所确认份额的时间， 下一个工作日
     * @return
     */
	private Map<String,String> getTAConfirmAmountDate() {
		Map<String,String> taConfirmAmountMap=new HashMap<String,String>();
		Date confirmAmountDate=mbUserAccountService.queryTAConfirmAmountDate(new Date());
		taConfirmAmountMap.put("confirmAmountDate",com.zhuanyi.vjwealth.loan.util.DateUtil.getMonthAndDay(confirmAmountDate,0));
		taConfirmAmountMap.put("profitDate",com.zhuanyi.vjwealth.loan.util.DateUtil.getMonthAndDay(confirmAmountDate,1));
		return taConfirmAmountMap;
	}

	
	/**
	 * ips下单即冻结
	 */
	@Override
	public Object placeOrderIps(String userId, String productId,
			String investmentAmount, String rpId, String couponId,
			String clientType, String token, String recommendPhone,String source) {
		// TODO Auto-generated method stub
		try{
			
			BaseLogger.audit(String.format("下单开始  userId :%s ，productId :%s  金额  : %s  ",userId,productId,investmentAmount));
		    BaseLogger.audit(String.format("下单开始  userId :%s ，productId :%s  红包  : %s  ",userId,productId,rpId));
			
	      validatorPlaceOrderParams(userId,productId,investmentAmount,token);
			 
		  boolean noVipPermission = productFinaceMapper.queryUserBuyVIPProductPermission(userId, productId);
		
		if(noVipPermission) {//VIP产品且用户没有购买权限
			throw new AppException("该理财产品为特定用户定制产品，如有需要请联系客服，谢谢！");
		}

		//=============没有红包 此处调用 ips 冻结接口=================
        if(StringUtils.isBlank(rpId)/*&&StringUtils.isBlank(couponId)*/){
        	//ips无红包
        	Object mbRfReturnDTO = placeOrderByRecommenderIps(userId, productId, investmentAmount, token, recommendPhone, couponId,source);
        	BaseLogger.audit("============== 此处调用ips冻结接口======================");
        	return mbRfReturnDTO;
        }else{
        	//=============没有红包 此处调用 ips红包冻结组合接口=================
        	
        	Object mbRfReturnDTO = mbUserAccountService.applyMaToRfIps(userId, investmentAmount, productId,rpId,couponId, clientType, token,source);
        	BaseLogger.audit("============== 此处调用ips红包组合冻结接口======================");
        	return mbRfReturnDTO;
        }

      //  validatorPlaceOrderParams(userId,productId,investmentAmount,token);

		//BaseLogger.audit(String.format("下单开始  userId :%s ，productId :%s  金额  : %s  ",userId,productId,investmentAmount));
       // BaseLogger.audit(String.format("下单开始  userId :%s ，productId :%s  红包  : %s  ",userId,productId,rpId));
			//MBRfReturnDTO mbRfReturnDTO = mbUserAccountService.applyMaToRf(userId, investmentAmount, productId,rpId,couponId, clientType, token);
			/*if (null != mbRfReturnDTO) {
				BaseLogger.audit(String.format("下单结束 返回信息为 code : %s  ,message :%s ,remainingInvestment :%s ,paymentInformation :%s ",
						mbRfReturnDTO.getCode(), mbRfReturnDTO.getMessage(), mbRfReturnDTO.getRemainingInvestment(), mbRfReturnDTO.getPaymentInformation()));
			} else {
				BaseLogger.audit("下单结束 返回信息为  MBRfReturnDTO: 为空" );
			}*/
			
			//增加推荐返佣事件
			//this.placeFinanceOrderEvent(userId, mbRfReturnDTO.getOrderNo(), investmentAmount, productId,recommendPhone);
			
			
			//return mbRfReturnDTO;
		}catch (Exception e){
			if( e instanceof  AppException){
				Integer code = ((AppException) e).getKey();
				String message = e.getMessage();
				if(null !=  code){
					BaseLogger.audit(code + " : " +e.getMessage());
					return new PlaceOrderReturnDTO(code.toString() , message,"","");
				}
				BaseLogger.error(e.getMessage());
				throw new AppException(message);
			}
			BaseLogger.error(e.getMessage());
			throw new AppException("系统繁忙，请稍后再试");
		}
	}
	
	
	
	
}
