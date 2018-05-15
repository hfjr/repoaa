package com.zhuanyi.vjwealth.wallet.mobile.index.server.dto;

import java.io.Serializable;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc: label  value实体
 * @author: cuidez
 * @date: 2016-09-11 10:08
 */
public class LableValueDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String label;
    private String value;


    public LableValueDTO(String label, String value) {
        this.label = label;
        this.value = value;
    }


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}
    
}
