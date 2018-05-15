package com.zhuanyi.vjwealth.wallet.mobile.product.web.controller;

import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.IInvestmentOrderService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UploadFileInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UploadIdentityPhotosDTO;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IUserOperationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/api/v3.0")
public class InvestmentOrderController {
	
	@Autowired
	private IInvestmentOrderService investmentOrderService;
	
    @Autowired
    private IUserOperationService operationService;
	
	
	// 5.下订单初始化
    @RequestMapping("/app/order/queryProductCanBuy.security")
    @AppController
    public Object queryProductCanBuy(String productId,String userId) {

        return investmentOrderService.queryProductCanBuy(productId,userId);
    }


    //6.下订单[购买产品]
    @RequestMapping("/app/order/placeOrder.security")
    @AppController
    public Object placeOrder(String userId,String productId,String investmentAmount,String token) {

        return investmentOrderService.placeOrder(userId, productId, investmentAmount,token);
    }
    
    // 7.投资合同模板(空的模板)-H5页面
    @RequestMapping("/app/contract/investmentContractTemplate")
    public String investmentContractTemplate(String productId,Model model) {
    	Map<String,String> returnMap = investmentOrderService.investmentContractTemplate(productId);
    	if(returnMap.get("flag").equals("no")){
    		return "/app/contract/investmentContractError";
    	}else{
    		model.addAttribute("content", returnMap.get("content"));
            return "/app/contract/investmentContract";
    	}
    	
    }

    // 8.投资合同详情(具体订单)-H5页面
    @RequestMapping("/app/contract/investmentContractDetail.security")
    public String investmentContractDetail(String orderId,Model model) {
    	Map<String,String> returnMap = investmentOrderService.investmentContractDetail(orderId);
    	if(returnMap.get("flag").equals("no")){
    		return "/app/contract/investmentContractError";
    	}else{
    		model.addAttribute("content", returnMap.get("content"));
    		 return "/app/contract/investmentContractDetail";
    	}
       
    }
    
    
    
    /****************************************************************************************************************************************************/
    

    //我的投资校验 －我到投资
    //10.判断用户是否有投资
    @RequestMapping("/app/order/queryWhetherInvestmentRecord.security")
    @AppController
    public Object queryWhetherInvestmentRecord(String userId) {
    	return investmentOrderService.queryWhetherInvestmentRecord(userId);
    }

    //11 根据用户userId, 查询投资记录汇总［在投本金，待收利息，累积收益］
    @RequestMapping("/app/order/queryInvestmentRecordSummary.security")
    @AppController
    public Object queryUserInInvestment(String userId) {
    	return investmentOrderService.queryUserInInvestment(userId);
    }

    //12 根据用户userId, 查询投资记录列表
    @RequestMapping("/app/order/queryUserInvestmentRecordList.security")
    @AppController
    public Object queryUserInvestmentRecordList(String userId,String page,String investmentStatus) {

        return investmentOrderService.queryUserInvestmentRecordList(userId,investmentStatus,page);
    }

    //13 用户投资列表的，投资(动态)详情 InvestmentDetail
    @RequestMapping("/app/order/queryUserInvestmentDetail.security")
    @AppController
    public Object queryUserInvestmentDetail(String userId,String orderId) {
    	return investmentOrderService.queryUserInvestmentDetail(userId,orderId);

    }

    //15 投资动态-动态流程  InvestmentNewsFlow
    @RequestMapping("/app/order/queryUserInvestmentNewsFlow.security")
    @AppController
    public Object queryUserInvestmentNewsFlow(String userId,String orderId) {
    	return investmentOrderService.queryUserInvestmentNewsFlow(orderId);
    }

    //16 投资动态－回款计划  InvestmentNews
    @RequestMapping("/app/order/queryUserInvestmentNewsRepaymentPlan.security")
    @AppController
    public Object queryUserInvestmentNewsRepaymentPlan(String userId,String orderId) {
    	return investmentOrderService.queryUserInvestmentNewsRepaymentPlan(orderId);
    }

    //18,8 投资动态- 查询合同 Investment News
//    @RequestMapping("/app/order/queryUserInvestmentNewsQueryContract")
//    @AppController
//    public Object queryUserInvestmentNewsQueryContract(String userId) {
//        // TODO 参数验证
//        return JSONObject.parse(readJSONTemplate("/template/V3.0/product/queryUserInvestmentNewsQueryContract.ftl"));
//    }
    
  //18.查看保单凭证
    @RequestMapping(value = "app/policy/queryPolicyDetail/{policyNo}.security", method = RequestMethod.POST)
    public String queryPolicyDetail(@PathVariable String policyNo,Model model) {
        //根据传入的policyNo,去产品表查询相应大保单图片绝对路径
        //大保单图片暂时维护在阿里云上,必须保证 每个大保单号 有对应的 大保单图片
        //维护参考:
        // 1.大保单图片上传至阿里云服务器,获得该图片的绝对url
        //2.将该url更新至产品表wallet.wj_product_finance 的相应字段policy_pic_url(位于policy_no字段后)
        //3.在wallet-manage进行大保单补单,成功后即可正常访问到大保单图片
        model.addAttribute("policyPicUrl", operationService.queryPolicyPicUrlByPolicyNo(policyNo));
        return "/app/policy/policyDetail";
    }


    //19实名认证初始化  RealNameAuthenticate initialization
    @RequestMapping("/app/user/authenticate/realNameAuthenticateInit.security")
    @AppController
    public Object realNameAuthenticateInit(String userId) {
    	return investmentOrderService.realNameAuthenticateInit(userId);
    }

    //20上传[证件照片]身份证 [APP控件不支持文件对象数组模式]
    @RequestMapping(value = {"app/user/authenticate/uploadIdentityPhotos.security"}, headers=("content-type=multipart/*"),method = RequestMethod.POST)
    @AppController
    public Object uploadIdentityPhotos(@RequestParam(value = "userId") String userId
            ,@RequestParam(value = "uuid") String uuid
            ,@RequestParam(value = "rightSideFile", required = false) MultipartFile rightSideFile
            ,@RequestParam(value = "reverseSideFile", required = false) MultipartFile reverseSideFile
            ,@RequestParam(value = "uploadSuccessFileId", required = false) String uploadSuccessFileId) throws Exception {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(uuid) || !operationService.validatorUserIdAndUuid(userId, uuid)) {
            throw new AppException(610, "请重新登录") ;
        }else{
            return  investmentOrderService.uploadIdentityPhotos(userId,rightSideFile,reverseSideFile,uploadSuccessFileId);
        }

    }


    @RequestMapping(value = {"app/user/authenticate/saveUploadIdentityPhotos.security"},method = RequestMethod.POST)
    @AppController
    public Object saveUploadIdentityPhotos(@RequestParam(value = "userId") String userId
            ,@RequestParam(value = "rightSideFileId") String rightSideFileId
            ,@RequestParam(value = "reverseSideFileId") String reverseSideFileId
            ,@RequestParam(value = "userChannelType") String userChannelType){

          return  investmentOrderService.saveUploadIdentityPhotos(userId,rightSideFileId,reverseSideFileId,userChannelType);
    }

    //21.下载证件照片
    @RequestMapping(value = "app/user/authenticate/loadIdentityPhoto/{fileId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> loadIdentityPhoto(@PathVariable String fileId) {
        byte[] data = operationService.loadFile(fileId);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.add("Access-Control-Allow-Origin","*");
        headers.setContentDispositionFormData("attachment", fileId + ".png");
        return new ResponseEntity<byte[]>(data, headers, HttpStatus.CREATED);
    }
    
    
    //25.工资单列表接口
    @RequestMapping("/app/user/queryPayRollList.security")
    @AppController
    public Object queryPayRollList(String userId,String page) {
        return investmentOrderService.queryPayRollList(userId,page);
    }

    //26.工资单详情接口
    @RequestMapping("/app/user/queryPayRollDetail.security")
    @AppController
    public Object queryPayRollDetail(String payRollId) {
        return investmentOrderService.queryPayRollDetail(payRollId);
    }

    
    
    
    /**
     * 用户登录
     *
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping("/app/user/login")
    @AppController
    public Object userLogin(String phone, String password) {
        return investmentOrderService.userLogin(phone, password);
    }
    
    
    /**
	 * 财富首页初始化
	 * @param userId
	 * @return
	 */
	@RequestMapping("/app/account/queryMAccountInfo.security")
	@AppController
	public Object queryMAccountInfo(String userId) {
		
		return investmentOrderService.queryMAccountInfo(userId);
	}
	
	
	/**
	 * e账户页面初始化
	 * @param userId
	 * @return
	 */
	@RequestMapping("/app/account/queryEAccountInfo.security")
	@AppController
	public Object queryEAccountInfo(String userId) {
		//return investmentOrderService.queryEAccountInfo(userId);
        return investmentOrderService.queryTAccountInfo(userId);
	}
    
}
