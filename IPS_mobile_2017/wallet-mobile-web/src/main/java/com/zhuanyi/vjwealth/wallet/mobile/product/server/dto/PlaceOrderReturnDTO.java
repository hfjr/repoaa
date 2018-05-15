package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class PlaceOrderReturnDTO {
	
    	private String code;  //结果code
        private String message;//结果信息
        private String remainingInvestment;//剩余可投资
        private String paymentInformation;//回款信息
        
		public PlaceOrderReturnDTO(String code, String message, String remainingInvestment, String paymentInformation) {
			super();
			this.code = code;
			this.message = message;
			this.remainingInvestment = remainingInvestment;
			this.paymentInformation = paymentInformation;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getRemainingInvestment() {
			return remainingInvestment;
		}

		public void setRemainingInvestment(String remainingInvestment) {
			this.remainingInvestment = remainingInvestment;
		}

		public String getPaymentInformation() {
			return paymentInformation;
		}

		public void setPaymentInformation(String paymentInformation) {
			this.paymentInformation = paymentInformation;
		}
        
}
