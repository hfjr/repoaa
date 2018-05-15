package com.zhuanyi.vjwealth.wallet.mobile.mySalaryPlan.server;

import com.alibaba.dubbo.common.utils.StringUtils;

/**
 * @Copyright (C), 2015-2016,蔚捷互联网金融服务有限公司
 * @desc:字符串截取工具类
 * @author: Tony Tang
 * @date: 2016-08-31 17:08
 */
public class SplitUtil {
    public static String splitLastStr(String str,int digit){
        if(StringUtils.isEmpty(str)||str.length()<=digit){
            return str;
        }
        return str.substring(str.length()-digit,str.length());
    }

    public static void main(String[] args) {
        System.out.println(splitLastStr("12323",4));
    }
}
