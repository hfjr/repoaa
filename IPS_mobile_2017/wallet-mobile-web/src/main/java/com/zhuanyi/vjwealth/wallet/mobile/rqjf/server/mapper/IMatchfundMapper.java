package com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fab.server.annotation.Mapper;
import com.zhuanyi.vjwealth.wallet.mobile.rqjf.server.dto.MatchfundApplyDTO;

@Mapper
public interface IMatchfundMapper {


	/**
	 * 
	 * 查询产品列表
	 * 
	 * {
	"body": {
		"isMore":"true",
		"productList":
		[
			{
				"productId":"PZB2017061000001",
				"title": "宁德市xxx屋子",
				"matchfundPrice": "70.16万",
				"soldOut":"no",
				"endDate":"2017-06-02",
			   "productPicUrl":"http://www.yesee.org/upfile/image/20150430/20150430115148284828.jpg"
			},
			{	
				"productId":"PZB2017061000001",
				"title": "长宁区xxx屋子",
				"matchfundPrice": "700.16万",
				"soldOut":"no",
				"endDate":"2017-06-02",
			   "productPicUrl":"http://pic.58pic.com/58pic/17/16/35/72658PIC2KM_1024.jpg"
			},
			{
				"productId":"PZB2017061000001",
				"title": "浦东区xxx屋子",
				"matchfundPrice": "700.16万",
				"soldOut":"no",
				"endDate":"2017-06-02",
			   "productPicUrl":"http://img5.imgtn.bdimg.com/it/u=2312458885,3391264598&fm=23&gp=0.jpg"
			},
			{
				"productId":"PZB2017061000001",
				"title": "陆家嘴xxx屋子",
				"matchfundPrice": "170.16万",
				"soldOut":"yes",
				"endDate":"2017-06-02",
			   "productPicUrl":"http://www.qqleju.com/uploads/allimg/131207/07-084826_423.jpg"
			}
		]
	},
    "footer": {
        "status": "200"
    }

}
	 * @return
	 */
	public List<Map<String,Object>> queryProductList(@Param("page")int page,@Param("auction")String auction,@Param("location")String location,@Param("buyType")String buyType,@Param("auctionStatus")String auctionStatus,@Param("priceArea")String priceArea);
	
	/**
	 * 
	 * 查询产品详情
	 * 
	 * {
    "body": {
        "productPicList": [
            {
                "picUrl": "http://pic27.nipic.com/20130122/10558908_131118160000_2.jpg"
            },
            {
                "picUrl": "http://pic27.nipic.com/20130122/10558908_131118160000_2.jpg"
            },
            {
                "picUrl": "http://pic27.nipic.com/20130122/10558908_131118160000_2.jpg"
            },
            {
                "picUrl": "http://pic27.nipic.com/20130122/10558908_131118160000_2.jpg"
            },
            {
                "picUrl": "http://pic27.nipic.com/20130122/10558908_131118160000_2.jpg"
            }
        ],
        "title": "[第一次拍卖]奔驰跑车",
        "matchfundPrice": "199,9999",
        "raisePrices": "6,0000",
        "evaluatePrices": "188,0000",
        "startPrice": "1971600",
        "term": "2天",
        "delayTerm": "5分钟一次",
        "auctionType": "拍卖",
        "applyStatus": "init", // 可预约:init/已预约:process/产品已经结束:over
		"location":"上海浦东",
		"endDate":"2017-06-02", //结束时间
		"preferredPurchaser":"否", //优先购买权人
		"disposalUnit":"上海浦东法院", //处置单位
		"consultationMethod":"021-0588", //咨询方式
		"bidRules":"只要有一个人不低于起拍价",
		"reservationRecord":"30", //数量
		"bidAnnouncement":"http://1092.001.222:99/wallet-mobile/h5", // 竞拍公告地址
		"introductionSubjectMatter":"http://1092.001.222:99/wallet-mobile/h5" //标的物介绍

    },
    "footer": {
        "status": "200"
    }
}
	 * @return
	 */
	public Map<String,Object> queryProductDetail(@Param("userId")String userId,@Param("productId")String productId);

	
	//检查是否可预约
	public Integer checkCanApply(@Param("userId")String userId,@Param("productId")String productId);
	/**
	 * 
	 * {
	"body": {
		"phone":"18821880888",
		"idNo":"110106198802056353",
		"realName":"真实姓名"

	},
    "footer": {
        "status": "200"

    }

}
	 * @return
	 */
	public Map<String,Object> applyInit(@Param("userId")String userId);
	
	
	
	/**
	 * 
	 * {
	"body": {

	},
    "footer": {
        "status": "200",
		"message":"尊敬的用户|您的申请已提交成功"
    }

}
	 * @return
	 */
	public void doApply(MatchfundApplyDTO matchfundApplyDTO);
	
	
	
	public List<Map<String,Object>> queryBannerInfo();
	
	public String queryBidAnnouncement(String productId);
	
	public String queryIntroductionSubjectMatter(String productId);

	
}
