package com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants;

public enum CityLabelPkConstant {
	
	FUND_ACCOUNT_LABEL("fundAccountLabel", "公积金label值"), 
	FUND_ACCOUNT_PASSWORD_LABEL("fundAccountPasswordLabel", "公积金密码label值"),
	FUND_ACCOUNT_INPUT_TIP("fundAccountInputTip", "公积金input值"),
	FUND_ACCOUNT_PASSWORD_INPUT_TIP("fundAccountPasswordInputTip", "公积金密码input值"),
	
	SOCIAL_SECURITY_ACCOUNT_LABEL("socialSecurityAccountLabel", "社保label值"),
	SOCIAL_SECURITY_ACCOUNT_PASSWORD_LABEL("socialSecurityAccountPasswordLabel", "社保密码label值"),
	SOCIAL_SECURITY_ACCOUNT_INPUT_TIP("socialSecurityAccountInputTip", "社保input值"),
	SOCIAL_SECURITY_ACCOUNT_PASSWORD_INPUT_TIP("socialSecurityAccountPasswordInputTip", "社保密码input值"),
	
	FUND_ACCOUNT_HELP_CONTENT("fundAccountHelpContent", "公积金帮助信息"),
	FUND_ACCOUNT_TIP_CONTENT("fundAccountTipContent", "公积金提示信息"),
	SOCIAL_SECURITY_ACCOUNT_HELP_CONTENT("socialSecurityAccountHelpContent", "社保帮助信息"),
	SOCIAL_SECURITY_ACCOUNT_TIP_CONTENT("socialSecurityAccountTipContent", "社保提示信息")
	;
	
	private String labelName;

	private String desc;
	
	private CityLabelPkConstant(String labelName, String desc) {
		this.labelName = labelName;
		this.desc = desc;
	}

	public String getLabelName() {
		return labelName;
	}

	public String getDesc() {
		return desc;
	}


}
