package com.zhuanyi.vjwealth.wallet.mobile.user.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.fab.web.controller.annotation.AppController;
import com.hf.comm.utils.JSONUtils;
import com.rqb.ips.depository.platform.beans.IpsUserInfoDTO;
import com.rqb.ips.depository.platform.faced.IPSQueryUserInfoService;
import com.zhuanyi.vjwealth.wallet.mobile.user.server.service.IUserOperationService;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.user.dto.MBRechargeDTO;

/**
 * @author Administrator
 */
@Controller
public class UserOperationController {

    @Autowired
    private IUserOperationService operationService;
    
    @Autowired
    private IPSQueryUserInfoService iPSQueryUserInfoService;
    
    @Value("${file.headpictur.path}")
    private String fileBathPath;

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
        return operationService.userLogin(phone, password);
    }

    /**
     * 发送注册文字短信
     *
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping("/app/user/sendRegisterSMSNotice")
    @AppController
    public Object sendRegisterSMSNotice(String phone, String password) {
        return operationService.sendRegisterSMSNotice(phone, password);
    }

    /**
     * 查询
     *
     
     */
    @RequestMapping("/app/user/ips_query")
    @ResponseBody
    public Object ips_query(String ipsno,String type,String billno)  {
    	
		IpsUserInfoDTO ipsUserInfoDTO = new IpsUserInfoDTO();
    	ipsUserInfoDTO.setQuerytype(type);
    	ipsUserInfoDTO.setMerBillNo(billno);
    	ipsUserInfoDTO.setUseripsid(ipsno);
    	String ipsQueryUserInfojson="";
    	IpsUserInfoDTO ipsQueryUserInfo = iPSQueryUserInfoService.IpsQueryUserInfo(ipsUserInfoDTO);
    	System.out.println(ipsQueryUserInfo);
    	
    	try {
			ipsQueryUserInfojson = JSONUtils.obj2json(ipsQueryUserInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(ipsQueryUserInfojson);
    	System.out.println("");
    	

        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("ipsQueryUserInfojson", ipsQueryUserInfojson);
        return returnMap;
    }
    
    /**
     * 发送注册语音
     *
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping("/app/user/sendRegisterToneNotice")
    @AppController
    public Object sendRegisterToneNotice(String phone, String password) {
        return operationService.sendRegisterToneNotice(phone, password);
    }

    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param code
     * @return
     */
    @RequestMapping("/app/user/register")
    @AppController
    public Object register(String phone, String password, String code) {
        return operationService.register(phone, password, code);
    }

    /**
     * 忘记密码修改
     *
     * @param phone
     * @param password
     * @param code
     * @return
     */
    @RequestMapping("/app/user/updateForgetPassword")
    @AppController
    public Object updateForgetPassword(String phone, String password, String code) {
        return operationService.updateForgetPassword(phone, password, code);
    }

    /**
     * 发送忘记密码语音短信
     *
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping("/app/user/sendForgetToneNotice")
    @AppController
    public Object sendForgetToneNotice(String phone, String password) {
        return operationService.sendForgetToneNotice(phone, password);
    }

    /**
     * 发送忘记密码文字短信
     *
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping("/app/user/sendForgetSMSNotice")
    @AppController
    public Object sendForgetSMSNotice(String phone, String password) {
        return operationService.sendForgetSMSNotice(phone, password);
    }


    /**
     * 激活发送文字短信
     *
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping("/app/user/sendActivateSMSNotice")
    @AppController
    public Object sendActivateSMSNotice(String phone, String password) {
        return operationService.sendActivateSMSNotice(phone, password);
    }

    /**
     * 激活发送语音短信
     *
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping("/app/user/sendActivateToneNotice")
    @AppController
    public Object sendActivateToneNotice(String phone, String password) {
        return operationService.sendActivateToneNotice(phone, password);
    }

    /**
     * 激活后更新用户
     *
     * @param phone
     * @param password
     * @param code
     * @return
     */
    @RequestMapping("/app/user/updateActivateUserInfo")
    @AppController
    public Object updateActivateUserInfo(String phone, String password, String code) {
        return operationService.updateActivateUserInfo(phone, password, code);
    }

    /**
     * 修改密码
     */
    @RequestMapping("/app/user/updatePassword.security")
    @AppController
    public Object updatePassword(String userId, String oldPassword, String newPassword) {
        return operationService.updateUserPassword(userId, oldPassword, newPassword);
    }

    /**
     * 帮助中心
     *
     * @return
     */
    @RequestMapping("/app/user/goHelp")
    public Object goHelp(Model model) {
        model.addAttribute("serviceHotline", operationService.getServiceHotline());
        return "/app/help/helpCenter";
    }

    /**
     * 帮助中心锚点
     *
     * @return
     */
    @RequestMapping("/app/user/goHelpbyhref")
    public String goHelpbyhref(Model model, String href) {
        model.addAttribute("serviceHotline", operationService.getServiceHotline());
        model.addAttribute("href", href);
        return "/app/help/helpCenter";
    }


    /**
     * 帮助中心锚点
     *
     * @return
     */
    @RequestMapping("/app/user/goVCredit")
    public String goVCredit() {
        return "/app/credit/v-xindai";
    }

    /**
     * 总资产饼图
     *
     * @param userId
     * @param uuid
     * @return
     * @deprecated 3.0上线后删除此方法
     */
    @RequestMapping("/app/user/totalAccountReport.security")
    public Object totalAccountReport(String userId, Model model) {
        model.addAttribute("data", operationService.queryUserTotolAccountAmountReport(userId));
        return "/report/highcharts/totalAccount";
    }


    /**
     * 总资产饼图 3.0
     * 定期理财 模块启用.
     *
     * @param userId
     * @param model
     * @return
     * @since v3.0
     */
    @RequestMapping("/api/v3.0/user/totalAccountReport.security")
    public Object totalAccountReportV30(String userId, Model model) {
        model.addAttribute("data", operationService.queryUserTotolAccountAmountReportForV30(userId));
        return "/report/highcharts/totalAccount";
    }


    /**
     * 意见反馈
     */
    @RequestMapping("/app/user/userFeedback")
    @AppController
    public Object userFeedback(String userId, String advice) {

        return operationService.saveUserFeedback(userId, advice);
    }

    /**
     * 查询用户是否购买过理财
     */
    @RequestMapping("/app/user/queryUserIsPurchaseV.security")
    @AppController
    public Object queryUserIsPurchaseV(String userId, String uuid) {

        return operationService.queryUserIsPurchaseV(userId);
    }

    /**
     * 用户绑卡时,查询客户默认信息(姓名,身份证号)
     */
    @RequestMapping("/app/user/queryUserDefaultInfo.security")
    @AppController
    public Object queryUserDefaultInfo(String userId, String uuid) {
        return operationService.queryUserDefaultInfo(userId);
    }


    /**
     * 总账户-->e账户
     *
     * @param userId
     * @param uuid
     * @param money
     * @return
     */
    @RequestMapping("/app/order/applyMaToEa.security")
    @AppController
    public Object applyMaToEa(String userId, String money) {
        //return operationService.applyMaToEa(userId, money);

        throw new AppException("存钱罐暂时下线，敬请期待");
        //return operationService.applyMaToTa(userId, money);
    }


    /**
     * 总账户-->e账户
     *
     * @param userId
     * @param uuid
     * @param money
     * @param paymentPassword
     * @since 3.1.2
     */
    @RequestMapping("/api/v3.1/app/order/applyMaToEa.payment")
    @AppController
    public Object applyMaToEaForPaymentPassword(String userId, String money) {
        //return operationService.applyMaToEa(userId, money);
        //return operationService.applyMaToTa(userId, money);


        throw new AppException("存钱罐暂时下线，敬请期待");
    }

    /**
     * @param userId
     * @param money
     * @return
     * @author zhangyingxuan
     * @date 20160708
     * 总账户-->T金所账户
     */
    @RequestMapping("/api/v1.0/app/order/applyMaToTa.payment")
    @AppController
    public Object applyMaToTa(String userId, String money) {
        throw new AppException("存钱罐暂时下线，敬请期待");
//        if (StringUtil.isNullString(money)) {
//            throw new AppException("金额不合法");
//        }
//
//        if (new BigDecimal(money).compareTo(new BigDecimal("100")) < 0) {
//            throw new AppException("金额不合法");
//        }
//
//        if (money.lastIndexOf(".") != -1 && (StringUtil.isNullString(money.substring(money.lastIndexOf(".") + 1)) || Integer.valueOf(money.substring(money.lastIndexOf(".") + 1)) > 0)) {
//            throw new AppException("金额不合法");
//        }
//     return operationService.applyMaToTa(userId, money);
    }


    /**
     * e-->总账户
     *
     * @param userId
     * @param uuid
     * @param money
     * @return
     */
    @RequestMapping("/app/order/transferEaToMa.security")
    @AppController
    public Object transferEaToMa(String userId, String money) {
        //return operationService.transferEaToMa(userId, money);
        return operationService.transferTaToMa(userId, money);
    }

    /**
     * @param userId
     * @param money
     * @return
     * @author zhangyingxuan
     * @date 20160708
     * ta --》 总账户  TA账户转MA账户, 提现到余额
     */
    @RequestMapping("/api/v1.0/app/order/transferTaToMa.security")
    @AppController
    public Object transferTaToMa(String userId, String money) {
        return operationService.transferTaToMa(userId, money);
    }

    /**
     * e-->总账户
     *
     * @param userId
     * @param uuid
     * @param money
     * @param paymentPassword
     * @since 3.1.2
     */
    @RequestMapping("/api/v3.1/app/order/transferEaToMa.payment")
    @AppController
    public Object transferEaToMaForPaymentPassword(String userId, String money) {
        //return operationService.transferEaToMa(userId, money);
        return operationService.transferTaToMa(userId, money);
    }


    /**
     * v+-->ma
     *
     * @param userId
     * @param uuid
     * @param money
     * @return
     */
    @RequestMapping("/app/order/transferV1ToMa.security")
    @AppController
    public Object transferV1ToMa(String userId, String money) {
        return operationService.transferV1ToMa(userId, money);
    }

    /**
     * ma-->v+
     *
     * @param userId
     * @param uuid
     * @param money
     * @return
     */
    @RequestMapping("/app/order/applyMaToV1.security")
    @AppController
    public Object applyMaToV1(String userId, String money) {
        return operationService.applyMaToV1(userId, money);
    }

    /**
     * ma-->提现
     *
     * @param userId
     * @param uuid
     * @param money
     * @return
     */
    @RequestMapping("/app/order/withdrawMa.security")
    @AppController
    public Object withdrawMa(String userId, String money) {
        return operationService.withdrawMa(userId, null,money);
    }

    /**
     * ma-->提现
     *
     * @param userId
     * @param uuid
     * @param money
     * @param paymentPassword
     * @since 3.1.2
     */
    @RequestMapping("/api/v3.1/app/order/withdrawMa.payment")
    @AppController
    public Object withdrawMaForPaymentPassword(String userId, String money) {
        return operationService.withdrawMa(userId,null, money);
    }

    /**
     * e-->提现
     *
     * @param userId
     * @param uuid
     * @param money
     * @return
     */
    @RequestMapping("/app/order/withdrawEa.security")
    @AppController
    public Object withdrawEa(String userId, String money) {
        //return operationService.withdrawEa(userId, money);
        return operationService.withdrawTa(userId, money);
    }

    /**
     * e-->提现
     *
     * @param userId
     * @param uuid
     * @param money
     * @param paymentPassword
     * @return
     * @since 3.1.2
     */
    @RequestMapping("/api/v3.1/app/order/withdrawEa.payment")
    @AppController
    public Object withdrawEaForPaymentPassword(String userId, String money) {
        BaseLogger.audit("withdrawEaForPaymentPassword-->start");
        //return operationService.withdrawEa(userId, money);
        return operationService.withdrawTa(userId, money);
    }

    /**
     * e-->T金所下架 提现至银行卡
     *
     * @return
     * @since 3.1.2
     */
    @RequestMapping("/api/v3.1/app/order/withdrawTaForTjsOffTheShelf.mock")
    @AppController
    public Object withdrawTaForTjsOffTheShelf(String sign) {
        BaseLogger.audit("withdrawTaForTjsOffTheShelf-->start");
        if(!"20170120".equals(sign)){
            return "该功能暂不开放!";
        }
        return operationService.withdrawTaForTjsOffTheShelf();
    }
    /**
     * @param userId
     * @param money
     * @return
     * @author zhangyingxuan
     * @date 20160712
     * Ta账户提现到银行卡
     */
    @RequestMapping("/api/v1.0/app/order/withdrawTa.payment")
    @AppController
    public Object withdrawTaForPaymentPassword(String userId, String money) {
        BaseLogger.audit("withdrawTaForPaymentPassword-->start");
        return operationService.withdrawTa(userId, money);
    }


    /**
     * 未绑卡,发送验证码
     */
    @RequestMapping("/app/user/rechargeToMaSendCode.security")
    @AppController
    public Object rechargeToMaSendCode(MBRechargeDTO dto) {
        return operationService.rechargeToMaSendCode(dto);
    }

    /**
     * 未绑卡,发送验证码V3.2
     */
    @RequestMapping("/api/v3.2/app/user/rechargeToMaSendCode.security")
    @AppController
    public Object rechargeToMaSendCodeV32(MBRechargeDTO dto) {
        return operationService.rechargeToMaSendCodeV32(dto);
    }

    /**
     * 未绑卡,发送验证码V3.6
     */
    @RequestMapping("/api/v3.6/app/user/bindCardSendCode.security")
    @AppController
    public Object bindCardSendCode(MBRechargeDTO dto) {
        dto.setOrderType("salary_plan_bankcard_withhold");
        return operationService.bindCardSendCode(dto);
    }

    /**
     * 未绑卡,充值
     */
    @RequestMapping("/app/user/doRechargeToMa.security")
    @AppController
    public Object doRechargeToMa(MBRechargeDTO dto) {
        return operationService.doRechargeToMa(dto);
    }

    /**
     * 绑卡验证码确认V3.6
     */
    @RequestMapping("/api/v3.6/app/user/confirmBindCardSendCode.security")
    @AppController
    public Object confirmBindCardSendCode(MBRechargeDTO dto) {
        dto.setOrderType("salary_plan_bankcard_withhold");
        return operationService.confirmBindCardSendCode(dto);
    }

    /**
     * 已经绑过卡,发送验证码
     */
    @RequestMapping("/app/user/rechargeToMaAlreadyBindCardSendCode.security")
    @AppController
    public Object rechargeToMaAlreadyBindCardSendCode(String userId, String cardId, String amount) {
        return operationService.rechargeToMaAlreadyBindCardSendCode(userId, cardId, amount);
    }

    /**
     * 已经绑过卡,发送验证码V3.2
     */
    @RequestMapping("/api/v3.2/app/user/rechargeToMaAlreadyBindCardSendCode.security")
    @AppController
    public Object rechargeToMaAlreadyBindCardSendCodeV32(String userId, String cardId, String amount) {
        return operationService.rechargeToMaAlreadyBindCardSendCodeV32(userId, cardId, amount);
    }

    /**
     * 已经绑过卡,充值
     */
    @RequestMapping("/app/user/doRechargeToMaAlreadyBind.security")
    @AppController
    public Object doRechargeToMaAlreadyBind(String userId, String cardId, String orderNo, String amount, String dynamicCode) {
        return operationService.doRechargeToMaAlreadyBind(userId, cardId, orderNo, amount, dynamicCode);
    }

    /**
     * 查询是否绑过卡,如果没有绑过卡,则提示该次绑定的卡将成为安全卡
     */
    @RequestMapping("/app/user/queryIsFirstBindCard.security")
    @AppController
    public Object queryIsFirstBindCard(String userId, String uuid) {
        return operationService.queryIsFirstBindCard(userId);
    }


    //v1理财购买协议
    @RequestMapping("/app/user/purchaseV1Agreement.security")
    public String purchaseV1Agreement(String userId, Model model) {
        //查询用户信息,完善购买细节
        model.addAttribute("agreeInfo", operationService.queryV1AgreementUserInfo(userId));
        return "/app/agreement/V1Agreement";
    }

    // 身份证正面文件上传
    @RequestMapping(value = {"app/user/authenticate/uploadIdentityPhoto"}, method = RequestMethod.POST)
    @AppController
    public Object uploadPicFile(@RequestParam(value = "picFile", required = false) MultipartFile file) throws Exception {
        return operationService.uploadPicFile(file);

    }


    @RequestMapping(value = {"app/user/authenticate/uploadIdentityPhotos"}, method = RequestMethod.POST)
    @AppController
    public Object uploadIdentityPhotos(@RequestParam(value = "userId") String userId
            , @RequestParam(value = "rightSideFile", required = false) MultipartFile rightSideFile
            , @RequestParam(value = "reverseSideFile", required = false) MultipartFile reverseSideFile
            , @RequestParam(value = "uploadSuccessFileId", required = false) String uploadSuccessFileId) throws Exception {

        return operationService.uploadIdentityPhotos(userId, rightSideFile, reverseSideFile, uploadSuccessFileId);
    }

    // 加载图片
    @RequestMapping(value = "app/user/authenticate/loadIdentityPhoto/{fileId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> loadIdentityPhoto(@PathVariable String fileId) {
        byte[] data = operationService.loadFile(fileId);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(data, headers, HttpStatus.CREATED);
    }

//	
//	/**
//	 * 头像上传
//	 * 
//	 * @return
//	 * @throws UnsupportedEncodingException 
//	 * @throws IOException
//	 * @throws IllegalStateException
//	 */
//	@RequestMapping(value = "/app/user/uploadHeadPictur")
//	@ResponseBody
//	public String headPicturUpload(String userId,HttpServletRequest request) {
//		String returnStr="success";
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//		
//		// 创建文件夹
//		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
//			returnStr=operationService.headPicturUpload(userId, entity.getValue());
//		}
//		return returnStr;
//	}
//	
//	/**
//	 * 头像下载
//	 * 
//	 * @return
//	 * @throws UnsupportedEncodingException 
//	 * @throws IOException
//	 * @throws IllegalStateException
//	 */
//	@RequestMapping(value = "/app/user/downloadHeadPictur")
//	public void downloadHeadPictur(String userId,HttpServletRequest request,HttpServletResponse response) {
//		try {
//	 		Map<String,String> map=	operationService.queryHeadPicturById(userId);
//			FileOperateUtil.download(request, response,fileBathPath+ map.get("relativeFilePath")+  map.get("fileRename"), map.get("filleOriginalName"));
//		} catch (Exception e) {
//			BaseLogger.error(e);
//		}
//	}


    /**
     * 安卓 手机日志文件上传
     *
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     * @throws IllegalStateException
     */
    @RequestMapping(value = "/app/user/uploadDeviceLog")
    @AppController
    public Object uploadDeviceLog(String userId, MultipartFile file) {

        return operationService.uploadDeviceLog(userId, file);
    }

    //未读消息变为已阅消息
    @RequestMapping("/app/user/haveReadMessage.security")
    @AppController
    public Object haveReadMessage(String userId, String messageId) {
        operationService.userHaveReadMessage(userId, messageId);
        return new HashMap<String, String>();
    }

    /**
     * 删除消息
     *
     * @return
     */
    @RequestMapping(value = "/app/user/deleteUserMessage.security")
    @AppController
    public Object deleteUserMessage(String userId, String messageIds) {
        Map<String, String> result = new HashMap<String, String>();
        try {
            if (StringUtils.isBlank(messageIds))
                throw new AppException("请选择消息");
            operationService.deleteUserMessage(messageIds.split(","));
            result.put("result", "success");
            return result;
        } catch (Exception e) {
            BaseLogger.error("删除消息失败", e);
            result.put("result", "fail");
            return result;
        }
    }

    /**
     * 联动付款确认后,异步接受通知回调
     */
    @RequestMapping("/unionMobile/notice")
    @ResponseBody
    public void unionMobilePayNotice(HttpServletRequest request, HttpServletResponse response) {
        BaseLogger.audit("联动通知回调开始：");
        //获取联动平台支付结果通知数据
        Map<String, Object> ht = new HashMap<String, Object>();
        String name = "", values = "";
        for (Enumeration names = request.getParameterNames(); names.hasMoreElements(); ht.put(name, values)) {
            name = (String) names.nextElement();
            values = request.getParameter(name);
        }

        BaseLogger.audit("联动请求参数:" + ht);
        PrintWriter out = null;
        try {
            //验签,业务处理,返回数据加签
            String data = operationService.payNotice(ht);
            data = "<html><head><META NAME=\"MobilePayPlatform\" CONTENT=\"" + data + "\" /></head></html>";
            response.setHeader("Content-Type", "text/html;charset=UTF-8");
            out = response.getWriter();
            out.print(data);
        } catch (IOException e) {
            BaseLogger.error("联动通知回调异常：" + e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
            BaseLogger.audit("联动通知回调结束：");
        }
    }

    /**
     * 易宝动付款确认后,异步接受通知回调
     */
    @RequestMapping("/yeePay/notice")
    @ResponseBody
    public void yeePayNotice(HttpServletRequest request, HttpServletResponse response) {
        BaseLogger.audit("易宝通知回调开始：");
        //获取联动平台支付结果通知数据
        String data = request.getParameter("data");
        String encryptkey = request.getParameter("encryptkey");

        PrintWriter out = null;
        try {
            //验签,业务处理,返回数据加签
            String result = operationService.yeePayNotice(data, encryptkey);
//            result = "<html><head><META CONTENT=\"" + result + "\" /></head></html>";
            response.setHeader("Content-Type", "text/html;charset=UTF-8");
            out = response.getWriter();
            out.print(result);
        } catch (IOException e) {
            BaseLogger.error("易宝通知回调异常：" + e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
            BaseLogger.audit("易宝通知回调结束：");
        }
    }


    //	跳转定期理财介绍页面
    @RequestMapping("/app/user/appointedFinancialPage.security")
    public String appointedFinancialPage(String userId, String uuid, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("uuid", uuid);
        model.addAttribute("hasAppointmentFinancial", operationService.hasAppointedFinancial(userId));
        return "/app/appointFinancial/appoint_financial_detial";
    }

    //	预约定期理财
    @RequestMapping("/app/user/appointedFinancial.security")
    @AppController
    public Object appointedFinancial(String userId) {
        operationService.appointmentFinancial(userId);
        return null;
    }


    /**
     * 充值确认后,查询充值订单是否回调成功
     * 如果还未回调成功,主动查询第三方接口做处理
     */
    @RequestMapping("/app/user/queryRechargeNotice.security")
    @AppController
    public Object queryRechargeNotice(String userId, String uuid, String orderNo) {
        BaseLogger.audit("充值回调轮询-->queryRechargeNotice-->in--");
        return operationService.queryRechargeNotice(userId, orderNo);
    }


    /**
     * 帮助中心
     *
     * @return
     * @since 3.3
     */
    @RequestMapping("/api/v3.3/app/user/centerHelp")
    public Object helpCenter(Model model) {
        model.addAttribute("typeList", operationService.getHelpCenterType());
        return "/app/help/v3.3/helpCenter";
    }

    /**
     * 帮助中心锚点
     *
     * @return
     * @since 3.3
     */
    @RequestMapping("/api/v3.3/app/user/helpCenterDetail")
    public String helpCenterDetail(Model model, String detailId) {
        model.addAttribute("detail", operationService.getHelpCenterTypeSubDetail(detailId));
        return "/app/help/v3.3/helpCenterDetail";
    }

    @RequestMapping("/app/user/LoginForSHQB")
    @ResponseBody
    public String SHQBLogin(@RequestBody String paramStr)  {
        return operationService.userLoginForSHQB(paramStr);
    }

    /**
     * 保存用户设备Id信息
     *
     * @return
     */
    @RequestMapping(value = "/app/user/saveUserAppDeviceId.security")
    @AppController
    public Object saveUserAppDeviceId(String userId,String deviceId,String deviceType,String otherData) {
        Map<String, String> result = new HashMap<String, String>();
        try {
            if (StringUtils.isBlank(userId)){
                throw new AppException("请登录");
            }
            if (StringUtils.isBlank(deviceId)){
                throw new AppException("请传入设备Id");
            }
            if (StringUtils.isBlank(deviceType)){
                throw new AppException("请传入设备类型");
            }
            operationService.saveUserAppDevice(userId, deviceId, deviceType,otherData);
            result.put("result", "success");
            return result;
        } catch (Exception e) {
            BaseLogger.error("保存用户设备Id信息失败", e);
            result.put("result", "fail");
            return result;
        }
    }

    @RequestMapping("/app/user/sendHouseFundLoanIntentionSMSNotice")
    @AppController
    public Object sendHouseFundLoanIntentionSMSNotice(String phone) {
        return operationService.sendHouseFundLoanIntentionSMSNotice(phone);
    }

    @RequestMapping("/app/user/sendHouseFundLoanIntentionSMSNoticeV2")
    @AppController
    public Object sendHouseFundLoanIntentionSMSNoticeV2(String phone) {
        return operationService.sendHouseFundLoanIntentionSMSNoticeV2(phone);
    }


    /**
     * 京东快捷支付，付款确认后,异步接受通知回调
     */
    @RequestMapping("/jdPay/notice")
    @ResponseBody
    public void jingdongPayNotice(HttpServletRequest request, HttpServletResponse response) {
        BaseLogger.audit("京东快捷支付通知回调开始：");

        ServletOutputStream stream = null;
        try {
            Map<String, Object> ht = new HashMap<String, Object>();
            String name = "", values = "";
            for (Enumeration names = request.getParameterNames(); names.hasMoreElements(); ht.put(name, values)) {
                name = (String) names.nextElement();
                values = request.getParameter(name);
            }

            String resp = request.getParameter("resp");
            //验签,业务处理,返回数据加签
            //1.处理字符串
            //2.调用mainbiz
            String result = operationService.jingdongNotice(resp);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            stream = response.getOutputStream();
            stream.write(result.getBytes());
        } catch (IOException e) {
            BaseLogger.error("京东快捷支付通知回调异常：" + e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    BaseLogger.error("outPutDataAsBytes中关闭流失败,原因:" + e);
                }
            }
            BaseLogger.audit("京东快捷支付通知回调结束：");
        }
    }

    /**
     * 京东支付代扣回调地址
     *
     * @param request
     * @param response
     */
    @RequestMapping("/jdPay/withhold/notice")
    @ResponseStatus(HttpStatus.OK)
    public void jdPayWithholdNotice(HttpServletRequest request, HttpServletResponse response) {
        BaseLogger.audit("【京东支付】代扣通知回调开始>>>>>");

        ServletOutputStream stream = null;
        try {
            Map<String, String[]> params = request.getParameterMap();
            String respString = "";
            for (String key : params.keySet()) {
                String[] values = params.get(key);
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    respString += key + "=" + value + "&";
                }
            }
            // 去掉最后一个空格
            respString = respString.substring(0, respString.length() - 1);
            BaseLogger.info(String.format("【京东支付】代扣回调请求参数：%s", respString));
            String result = operationService.jdPayWithholdNotice(respString);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            stream = response.getOutputStream();
            stream.write(result.getBytes());
        } catch (Exception e) {
            BaseLogger.error("京东支付代扣通知回调异常：" + e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    BaseLogger.error("outPutDataAsBytes中关闭流失败,原因:" + e);
                }
            }
            BaseLogger.audit("【京东支付】代扣通知回调结束>>>>>");
        }
    }
}
