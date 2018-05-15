package com.rqb.ips.depository.platform.beans;

import java.io.Serializable;

/**
 * IPS接口返回对象
 * @version 1.0 ,2017/11/30
 * @auther fan
 * @since 1.0
 */
public class IPSResponse implements Serializable{
	private static final long serialVersionUID = -2443353344894594351L;
	
    /**
     * 错误代码
     */
    private String code;


    /**
     * 错误信息
     */
    private String msg;
    
    /**
     * 返回数据
     */
    private Object data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
	/**
     * 错误对象code
     *
     * @author fan
     * @version 0.1, 2017-11-30
     * @since 0.1
     */
    public static final class ErrCode {
    	/** 成功*/
        public static final String SUCCESS  = "000000";

        /** 连接超时 */
        public static final String TIME_OUT  = "0001";
        
        /** 重复提交 */
        public static final String REPEAT  = "0002";

        /**未知错误*/
        public static final String UNKNOW   = "999999";
    }
    
    /**
     * 判断返回结果是否成功
     *
     * @author fan
     * @version 0.1, 2017-11-30
     * @since 0.1
     */
    public Boolean isSuccess() {
        if(code == null || ErrCode.SUCCESS.equals(code)){
            return true;
        }
        return false;
    }
}
