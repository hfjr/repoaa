package com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants;

public enum TaskImgKeyConstant {
	
    CARD_YES("loan_task_ground_img_card_yes", "绑卡任务未完成"), 
    CARD_NO("loan_task_ground_img_card_no", "绑卡任务完成"), 
    CARD_COMPELETE_BIG("loan_task_ground_img_card_compelete_big", "绑卡任务达成图标（大）"),
    CARD_COMPELETE_SMALL("loan_task_ground_img_card_compelete_small", "绑卡任务达成图标（小）"),
    INVEST_YES("loan_task_ground_img_invest_yes", "投资任务未完成"),
    INVEST_NO("loan_task_ground_img_invest_no", "投资任务完成"),
    SOCIAL_YES("loan_task_ground_img_social_yes", "公积金社保任务未完成"),
    SOCIAL_NO("loan_task_ground_img_social_no", "公积金社保任务达成"),
	INVITE_REGISTER_YES("loan_task_ground_img_invite_register_yes","邀请好友注册任务未完成"),
	INVITE_REGISTER_NO("loan_task_ground_img_invite_register_no","邀请好友注册任务达成");
	
	private String key;
	
	private String value;

	private TaskImgKeyConstant(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
