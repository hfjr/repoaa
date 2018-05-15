package com.zhuanyi.vjwealth.wallet.mobile.rqjf.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.fab.web.controller.annotation.AppController;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.QRCodeUtil;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.ValidatorUtils;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.dto.CkbApplyDTO;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.ICKBService;

@Controller
public class CKBController {

	@Autowired
	private ICKBService ckService;
    @Value("project_url")
    private String projectName;

	@RequestMapping("/api/user/ckb/queryCKBStatus/v1.0.security")
	@AppController
	public Object queryCKBStatus(String userId) {

		return ckService.queryCKBStatus(userId);
	}

	@RequestMapping("/api/user/ckb/doApply/v1.0.security")
	@AppController
	public Object doApply(CkbApplyDTO ckbApplyDTO) {
		validateDoApply(ckbApplyDTO);
		return ckService.doApply(ckbApplyDTO);
	}

	@RequestMapping("/api/user/ckb/queryCKDetail/v1.0.security")
	@AppController
	public Object queryCKDetail(String userId) {

		return ckService.queryCKDetail(userId);
	}

	@RequestMapping("/api/user/ckb/queryIntroduceCareerList/v1.0.security")
	@AppController
	public Object queryIntroduceCareerList(String userId) {

		return ckService.queryIntroduceCareerList(userId);
	}

	@RequestMapping("/api/user/center/queryUserCenter/v2.0.security")
	@AppController
	public Object queryUserCenter(String userId) {

		return ckService.queryUserCenter(userId);
	}

	@RequestMapping("/api/user/ckb/queryUserCommission/v1.0.security")
	@AppController
	public Object queryUserCommission(String page, String userId) {
		ValidatorUtils.validatePage(page);
		return ckService.queryUserCommission(page, userId);
	}

	@RequestMapping("/api/user/ckb/queryAchievementCommission/v1.0.security")
	@AppController
	public Object queryAchievementCommission(String userId) {

		return ckService.queryAchievementCommission(userId);
	}

	@RequestMapping("/api/user/ckb/queryUserCKScore/v1.0.security")
	@AppController
	public Object queryUserCKScore(String userId) {

		return ckService.queryUserCKScore(userId);
	}
	
	//我的邀请
	@RequestMapping("/api/user/center/myinvite/v1.0.security")
	@AppController
	public Object myinvite(String userId) {
		
		return ckService.myinvite(userId);
	}
	//上传头像
	@RequestMapping("/api/user/center/uploadHeadCulpture/v1.0.security")
	@AppController
	public Object uploadHeadCulpture(String userId,MultipartFile file) {
		
		Map<String,String> returnMap=new HashMap<String,String>();
		returnMap.put("headCulptureUrl", ckService.uploadHeadCulpture(userId, file));
		return returnMap;
		
		
	}
	
	
	//生成邀请二维码
	@RequestMapping("/api/user/center/inviteqrcode.security")
	public void inviteqrcode(String userId,String phone,HttpServletResponse response) throws IOException {
		//		/app/user/register
		QRCodeUtil.zxingCodeCreateByIO(projectName+"/api/user/regist/page/v1.0?phone="+phone, 100, 100, response.getOutputStream(), "jpg");
	}
	
	//注册界面
	@RequestMapping("/api/user/regist/page/v1.0")
	public String registPage(String phone,Model model) {
		//		/app/user/register
		model.addAttribute("phone", phone);
		return "/rqjf/regist";
	}
	
	
	
	
	//查询地址
	@RequestMapping("/api/user/address/queryAddressList/v1.0.security")
	@AppController
	public Object queryAddressList(String page,String userId) {
		ValidatorUtils.validatePage(page);
		return ckService.queryAddressList(page, userId);
	}
	
	@RequestMapping("/api/user/address/addAddress/v1.0.security")
	@AppController
	public Object addAddress(String userId,String receiveName,String phone,String province,String city,String area,String detailAddress,String firstChoice) {
		
		return ckService.addAddress(userId,receiveName, phone, province, city,area, detailAddress, firstChoice);
	}
	
	@RequestMapping("/api/user/address/updateAddress/v1.0.security")
	@AppController
	public Object updateAddress(String receiveName,String addressId,String userId,String phone,String province,String city,String area,String detailAddress,String firstChoice) {
		ValidatorUtils.validateNull(addressId, "addressId不能为空");
		return ckService.updateAddress(receiveName,addressId, userId, phone, province, city, area,detailAddress, firstChoice);
	}
	@RequestMapping("/api/user/address/firstChoice/v1.0.security")
	@AppController
	public Object addressFirstChoice(String addressId,String userId) {
		
		ValidatorUtils.validateNull(addressId, "addressId不能为空");
		return ckService.addressFirstChoice(addressId, userId);
	}
	
	@RequestMapping("/api/user/address/deleteAddress/v1.0.security")
	@AppController
	public Object deleteFirstChoice(String addressId,String userId) {
		
		ValidatorUtils.validateNull(addressId, "addressId不能为空");
		return ckService.deleteFirstChoice(addressId, userId);
	}
	
	@RequestMapping("/api/user/recommend/queryAllInviteList/v1.0.security")
	@AppController
	public Object queryAllInviteList(String userId) {
		
		return ckService.queryAllInviteList( userId);
	}
	
	
	private void validateDoApply(CkbApplyDTO ckbApplyDTO){
		String realName=ckbApplyDTO.getRealName();
		String phone=ckbApplyDTO.getPhone();
		String idNo=ckbApplyDTO.getIdNo();
		ValidatorUtils.validateNull(realName, "realName");
		ValidatorUtils.validateNull(phone, "phone");
		ValidatorUtils.validateIDCard(idNo);
	}
}
