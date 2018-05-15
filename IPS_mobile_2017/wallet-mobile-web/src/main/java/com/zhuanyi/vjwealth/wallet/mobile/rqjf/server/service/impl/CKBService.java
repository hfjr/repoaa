package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.account.PersonalCenterDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.mapper.IUserAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.MapperUtils;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.common.utils.OSSClientUtil;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.dto.CkbApplyDTO;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper.ICKBMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.ICKBService;
@Service
public class CKBService implements ICKBService {

	@Autowired
	private ICKBMapper ckIckbMapper;
	
	@Autowired
    private IUserAccountQueryMapper userAccountQueryMapper;
	
	@Value("${yuming}")
	private String yuming;

	
	@Override
	public Map<String, Object> queryCKBStatus(String userId) {
		return ckIckbMapper.queryCKBStatus(userId);
	}

	@Override
	public Map<String, Object> doApply(CkbApplyDTO ckbApplyDTO) {

		ckIckbMapper.doApply(ckbApplyDTO);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("message", "尊敬的用户|您的申请已提交成功");
		return returnMap;
	}

	@Override
	public Map<String, Object> queryCKDetail(String userId) {
		Map<String,Object> returnMap=ckIckbMapper.queryCKDetail(userId);
		returnMap.put("treatmentList", MapperUtils.parseJSONArrayByStr(returnMap.get("treatmentList")));
		return returnMap;
	}

	@Override
	public Map<String, Object> queryIntroduceCareerList(String userId) {
		Map<String,Object> returnMap=new HashMap<String, Object>();
		List<Map<String, Object>> careerList= ckIckbMapper.queryIntroduceCareerList();
		for(Map<String, Object> map :careerList){
			map.put("standardList", MapperUtils.parseJSONArrayByStr(map.get("standardList")));
		}
		returnMap.put("careerList", careerList);
		return returnMap;
	}

	@Override
	public Map<String, Object> queryUserCommission(String page,String userId) {
		Map<String,Object> returnMap=new HashMap<String, Object>();
		//1.	返回列表
		List<Map<String,Object>> auctionList =ckIckbMapper.queryUserCommission((Integer.parseInt(page) - 1) * 10,userId);
		//	getListRows(userId, (Integer.parseInt(page) - 1) * 10);
		//	2. 包装分页信息
		returnMap.put("commissionList", auctionList);
		returnMap.put("isMore", false);
		if (auctionList != null && auctionList.size() >= 10) {
			returnMap.put("isMore", true);
		}
		return returnMap;	
	}

	@Override
	public Map<String, Object> queryAchievementCommission(String userId) {
		Map<String,Object> returnMap=new HashMap<String, Object>();
		returnMap.put("commissionList", ckIckbMapper.queryAchievementCommission(userId));
		return returnMap;
	}

	@Override
	public Map<String, Object> queryUserCKScore(String userId) {
		Map<String,Object> returnMap=new HashMap<String, Object>();
		returnMap.putAll(ckIckbMapper.queryUserCKScore(userId));
		//point
		returnMap.put("point", Integer.parseInt((String)returnMap.get("point")));
		returnMap.put("time", "");
		returnMap.put("credits",new String[]{"一般","中等","良好","优秀","极好"});
		returnMap.put("grades",new String[]{"350","500","600","650","700","1000"});
		returnMap.put("time", "");
		return returnMap;
	}

	@Override
	public Map<String, Object> queryUserCenter(String userId) {
		validatorUserId(userId);
        PersonalCenterDTO personalCenterDTO=userAccountQueryMapper.queryPersonalCenterInfo(userId);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("totalAssets",personalCenterDTO.getTotalAssets());
        returnMap.put("totalRevenue", personalCenterDTO.getTotalRevenue());
        returnMap.put("availableBalance",personalCenterDTO.getAvailableBalance());

        returnMap.put("isShowFreezingAmount", "false");
        returnMap.put("freezingAmount","");
        if(StringUtils.isNotBlank(personalCenterDTO.getFreezingAmount())&&
                new BigDecimal(personalCenterDTO.getFreezingAmount()).compareTo(BigDecimal.ZERO)>0){
            returnMap.put("isShowFreezingAmount", "true");
            returnMap.put("freezingAmount", MessageFormat.format("{0}元",personalCenterDTO.getFreezingAmount()));
        }
        returnMap.put("regularAmount","");
        if(StringUtils.isNotBlank(personalCenterDTO.getRegularAmount())&&
                new BigDecimal(personalCenterDTO.getRegularAmount()).compareTo(BigDecimal.ZERO)>0){
            returnMap.put("regularAmount", MessageFormat.format("待回款{0}元", personalCenterDTO.getRegularAmount()));
        }
        returnMap.put("currentAmount", "");
        if(StringUtils.isNotBlank(personalCenterDTO.getCurrentAmount())&&
                new BigDecimal(personalCenterDTO.getCurrentAmount()).compareTo(BigDecimal.ZERO)>0){
            returnMap.put("currentAmount", MessageFormat.format("{0}元", personalCenterDTO.getCurrentAmount()));
        }
//        returnMap.put("salaryPlan", "");
//        returnMap.put("loanInfo", "");
//        returnMap.put("isCKVip", ckIckbMapper.queryUserCKVIP(userId)>0?"yes":"no");
        returnMap.putAll(ckIckbMapper.queryUserExtraInfo(userId));
    	returnMap.put("lotteryUrl", yuming+"/rqb-weixin-client/index.html#/index/lottery?fromApp=andirod");
        return returnMap;
	}
	
	
	
	@Override
    public Map<String, Object> myinvite(String userId) {
		
    	return ckIckbMapper.queryMyinvite(userId);
    }    

    
    @Override
    public String uploadHeadCulpture(String userId, MultipartFile file) {
    	OSSClientUtil ossClient=new OSSClientUtil();
    	try {
			 ossClient.uploadFile2OSS(file.getInputStream(),userId+".png");
			 String headCulptureurl= "http://rongqiaobao.oss-cn-shanghai.aliyuncs.com/HeadCulpture/"+userId+".png";
			 ckIckbMapper.updateHeadCulptureUrl(userId, headCulptureurl);
			 return headCulptureurl;
		} catch (IOException e) {
			BaseLogger.error("上传头像异常:"+e);
			throw new AppException("网络异常,请稍后再试");
		}
    }

    
    @Override
    public Map<String, Object> queryAddressList(String page,String userId) {
    	
    	Map<String,Object> returnMap=new HashMap<String, Object>();
		//1.	返回列表
		List<Map<String,Object>> auctionList =ckIckbMapper.queryAddressList((Integer.parseInt(page) - 1) * 10,userId);
		//	2. 包装分页信息
		returnMap.put("addressList", auctionList);
		returnMap.put("isMore", false);
		if (auctionList != null && auctionList.size() >= 10) {
			returnMap.put("isMore", true);
		}
		return returnMap;	
    }
    @Override
    public  Map<String,String> updateAddress(String receiveName,String addressId,String userId,String phone,String province,String city,String area,String detailAddress,String firstChoice) {
    	ckIckbMapper.updateRqbAddressInfo(receiveName,addressId, userId, phone, province, city,area, detailAddress, firstChoice);
    	return new HashMap<String,String>();
    }
    
    @Override
    public  Map<String,String> addAddress(String userId,String receiveName, String phone, String province,String city,String area ,String detailAddress, String firstChoice) {
    	ckIckbMapper.insertRqbAddressInfo(userId,receiveName, phone, province, city,area, detailAddress, firstChoice);
    	return new HashMap<String,String>();
    }
    
    @Override
    public  Map<String,String> addressFirstChoice(String addressId, String userId) {
    	ckIckbMapper.updateUnFirstChioce(addressId, userId);
    	ckIckbMapper.updateFirstChioce(addressId, userId);
    	return new HashMap<String,String>();
    }
    
    @Override
    public Map<String, String> deleteFirstChoice(String addressId, String userId) {
    	ckIckbMapper.deleteAddress(addressId, userId);
    	return new HashMap<String,String>();
    }
    
    @Override
    public Map<String, Object> queryAllInviteList(String userId) {
    	Map<String,Object> returnMap=new HashMap<String, Object>();
		//1.	返回列表
		List<Map<String,Object>> inviteList =ckIckbMapper.queryAllInviteList(userId);
		//	2. 包装分页信息
		returnMap.put("inviteList", inviteList);
    	return returnMap;
    }
    private void validatorUserId(String userId){
    	if(StringUtils.isBlank(userId)){
    		throw new AppException("用户ID不能为空");
    	}
    }

}
