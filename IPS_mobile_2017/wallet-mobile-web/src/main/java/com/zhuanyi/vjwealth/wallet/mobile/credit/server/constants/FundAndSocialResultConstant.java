package com.zhuanyi.vjwealth.wallet.mobile.credit.server.constants;

public enum FundAndSocialResultConstant {
	
    SAVED_SUCCESS("202900", "保存成功","资料成功提交|将于一个工作日内完成审核"), 
    SAVED_FAIL("202901", "保存失败","");
	
	private String code;

	private String name;
	
	private String message;

	private FundAndSocialResultConstant(String code, String name, String message) {
		this.code = code;
		this.name = name;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getMessage() {
		return message;
	}

}
