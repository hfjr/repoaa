package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fab.core.exception.service.AppException;
import com.fab.server.util.Format;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.mapper.IUserAccountQueryMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper.IGreenMapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.service.IGreenService;
@Service
public class GreenService implements IGreenService {

	@Autowired
	private IGreenMapper homeMapper;
	@Value("${yuming}")
	private String yuming;
	
	@Value("${project_url}")
	private String project_url;
	@Autowired
	private IUserAccountQueryMapper userAccountQueryMapper;
	
	@Override
	public Map<String, Object> redPackageInit(String userId) {
		
		Map<String,Object> returnMap=new HashMap<String,Object>();
		if(StringUtils.isNotBlank(userId)){
			int packageRecord=homeMapper.countPackageRecord(userId);
			int couponsRecord=homeMapper.countCouponsRecord(userId);
			returnMap.put("redPackage", packageRecord>0?"N":"Y");
			returnMap.put("packageTitle", packageRecord>0?"已领取":"点击领取");
			
			returnMap.put("coupon", couponsRecord>0?"N":"Y");
			returnMap.put("couponTitle", couponsRecord>0?"已领取":"点击领取");
		}else{
			returnMap.put("redPackage", "Y");
			returnMap.put("packageTitle", "点击领取");
			returnMap.put("coupon", "Y");
			returnMap.put("couponTitle", "点击领取");
		}
		//http://app.rongqiaobao.com/rqb-weixin-client/index.html#/index/lottery?fromApp=andirod&userid=US201707030000002651&uuid=UU20170908170100687052BCEFACD107
		
		Map productMap = userAccountQueryMapper.queryNewHandFinancialReceiveRate();
		
		returnMap.put("packageLabel", "1000元红包");
		returnMap.put("packageDescription", "成功注册,立即获得1000元投资抵扣红包");
		
		returnMap.put("couponLabel", "12%超高收益");
		returnMap.put("couponDescription", "100元起投,新人专享12%超高收益新手标");
		
		returnMap.put("lotteryLabel", "iphon7免费抽");
		returnMap.put("lotteryDescription", "成功注册,立即获得免费抽Iphone7机会");
		
		returnMap.put("grennProductId", (String)productMap.get("productId"));
		
		returnMap.put("lotteryUrl", yuming+"/rqb-weixin-client/index.html#/index/lottery?fromApp=andirod");
		
		returnMap.put("ruleUrl", "http://rongqiaobao.oss-cn-shanghai.aliyuncs.com/activity/touzijiaxi.html");
		return returnMap;
	}

	@Override
	public Map<String, Object> redPackageDoApply(String userId) {
		//校验 是否已经领取
		if(homeMapper.countPackageRecord(userId)>0){
			throw new AppException("您已领取,每人只能领取一次");
		}
		//红包领取 30日内有效
		Calendar date=Calendar.getInstance();
		date.add(Calendar.DATE, 30);
		//10*5   100元起投
		for(int i=0;i<5;i++){
			homeMapper.insertRqbPackageInfo(userId, "10","100", Format.dateToString(new Date()), Format.dateToString(date.getTime(),"yyyy-MM-dd"),"green");
		}
		//50*1   1000元起投
		homeMapper.insertRqbPackageInfo(userId, "50","1000", Format.dateToString(new Date()), Format.dateToString(date.getTime(),"yyyy-MM-dd"),"green");
		//100*1  5000元起投
		homeMapper.insertRqbPackageInfo(userId, "100","5000", Format.dateToString(new Date()), Format.dateToString(date.getTime(),"yyyy-MM-dd"),"green");
		//200*4  10000元起投
		for(int i=0;i<4;i++){
			homeMapper.insertRqbPackageInfo(userId, "200","10000", Format.dateToString(new Date()), Format.dateToString(date.getTime(),"yyyy-MM-dd"),"green");
		}
		return new HashMap<String,Object>();
	}
	
	@Override
	public Map<String, Object> couponDoApply(String userId) {
		//校验是否已经领取
		if(homeMapper.countCouponsRecord(userId)>0){
			throw new AppException("您已领取,每人只能领取一次");
		}
		//卡券领取
		// 1月标-3个月标的加息0.5个点，6个月标的加息1个点，12个月标的加息1.5个点 10天内有效
		Calendar date=Calendar.getInstance();
		date.add(Calendar.DATE, 10);
		// 1-3
		homeMapper.insertCouponsInfo(userId, "0.005", "1",  Format.dateToString(new Date()), Format.dateToString(date.getTime(),"yyyy-MM-dd"),"green");
		// 6-12
		homeMapper.insertCouponsInfo(userId, "0.01", "6", Format.dateToString(new Date()), Format.dateToString(date.getTime(),"yyyy-MM-dd"),"green");
		// 12
		homeMapper.insertCouponsInfo(userId, "0.015", "12",Format.dateToString(new Date()), Format.dateToString(date.getTime(),"yyyy-MM-dd"),"green");
		
		Map<String,Object> returnMap=new HashMap<String,Object>();
		return returnMap;
	}
	
	
	@Override
	public Map<String, Object> lotteryInit(String userId) {
		
		Map<String,Object> returnMap=new HashMap<String,Object>();
		returnMap.put("lotteryTimes", homeMapper.countLuckTimesRecord(userId));
		
		return returnMap;
	}
	
	public Map<String, Object> lotteryDoApply(String userId) {
		//校验是否已经领取
		if(homeMapper.countLuckTimesRecord(userId)<=0){
			throw new AppException("您已没有抽奖次数");
		}
		//获取中奖号码
		Integer lotteryNumber=getLotteryNumber();
		
		//  TODO ..
		
		//	减少一次中奖次数
		homeMapper.reduceLuckTimesByUserId(userId);
		//  插入一条中奖记录
		homeMapper.insertLotteryRecord(userId, lotteryNumber.toString());
		//  根据中奖号码 做对应的奖品赠送
		sendGiftByLuckNumber(userId, lotteryNumber);
		Map<String,Object> returnMap=new HashMap<String,Object>();
		returnMap.put("lotteryNumber", lotteryNumber);
		return returnMap;
	}
	
	// 根据中奖号码送礼品
	public void sendGiftByLuckNumber(String userId,Integer luckNumber){
		Calendar packagedate=Calendar.getInstance();
		packagedate.add(Calendar.DATE, 30);
		
		Calendar date=Calendar.getInstance();
		switch (luckNumber) {
//		1	100元红包
		case 1:
			homeMapper.insertRqbPackageInfo(userId, "100","5000", Format.dateToString(new Date()), Format.dateToString(packagedate.getTime(),"yyyy-MM-dd"),"lottery");
			break;
//		2	200元红包
		case 2:
			homeMapper.insertRqbPackageInfo(userId, "200","10000", Format.dateToString(new Date()), Format.dateToString(packagedate.getTime(),"yyyy-MM-dd"),"lottery");
			break;
//		3	1.0加息券
		case 3:
			date.add(Calendar.DATE, 10);
			homeMapper.insertCouponsInfo(userId, "0.01", "6",Format.dateToString(new Date()), Format.dateToString(date.getTime(),"yyyy-MM-dd"),"lottery");
			break;
//		4	IPADMINI
		case 4:
			//TODO ..
			break;
//		5	10元红包
		case 5:
			homeMapper.insertRqbPackageInfo(userId, "10","100", Format.dateToString(new Date()), Format.dateToString(packagedate.getTime(),"yyyy-MM-dd"),"lottery");
			break;
//		6	苹果表
		case 6:
			//homeMapper.insertRqbPackageInfo(userId, "20","500", Format.dateToString(new Date()), Format.dateToString(packagedate.getTime(),"yyyy-MM-dd"),"lottery");
			break;
//		7	1.5加息券
		case 7:
			date.add(Calendar.DATE, 10);
			homeMapper.insertCouponsInfo(userId, "0.015", "12",Format.dateToString(new Date()), Format.dateToString(date.getTime(),"yyyy-MM-dd"),"lottery");
			break;
//		8	iphone7
		case 8:
			//TODO ..
			break;
		default:
			break;
		}
		
		
	}
	
	
	
	// 获取中奖号码
	private int getLotteryNumber(){
		//奖池数组
		List<Map<String,Integer>> lotteryList=homeMapper.queryLotteryList();
		
		//新的中奖数列(离散)
		Map<Integer,Integer> lotteryMap=new HashMap<Integer,Integer>();
		Integer countLotteryMath=0;
		// 通过离散数组进行获取抽奖号码  例如 : [0,10,20,30,40,50,70,90,100] ,数值区间表示中奖概率,在哪段数值区间内,就算哪个数值中奖
		Integer lotteryMathArray[]=new Integer[lotteryList.size()];
		int i=0;
		for(Map<String,Integer> temp:lotteryList){
			Integer lotteryNumber=temp.get("lottery_number");
			countLotteryMath+=temp.get("lottery_probability");
			lotteryMap.put(countLotteryMath, lotteryNumber);
			lotteryMathArray[i]=countLotteryMath;
			i++;
		}
		//产生幸运数字
		int targetLotteryNumber=new Random().nextInt(countLotteryMath);
		
		//查找幸运号码(就是lotteryMap中的key,来查找对应的原始数字)
		int luckNumer=searchArry(targetLotteryNumber, lotteryMathArray);
		
		//原始号码
		return lotteryMap.get(luckNumer);
	}
	
	//  查找最"近"的数字
	private Integer searchArry(int target,Integer [] arry){		
		if(arry.length==1){
			if(arry[0]<target){
				return -1;
			}
			return arry[0];
		}else{
			Integer[] newArry=Arrays.copyOf(arry, arry.length/2);
			Integer b=newArry[newArry.length-1];
			//左边数组
			if(b>=target){
				return searchArry(target, newArry);
			}else{
			//右边数组
				return searchArry(target, Arrays.copyOfRange(arry, arry.length/2, arry.length));
			}
		}
	}

}
