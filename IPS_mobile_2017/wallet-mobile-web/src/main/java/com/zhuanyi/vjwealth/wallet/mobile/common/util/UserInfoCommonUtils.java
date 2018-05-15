package com.zhuanyi.vjwealth.wallet.mobile.common.util;

import com.alibaba.dubbo.common.utils.StringUtils;

/**
 * Created by wzf on 2016/11/10.
 */
public class UserInfoCommonUtils {

    //处理姓名
    public static String operateName(String name){
        //姓名为空
        if(StringUtils.isBlank(name)){
            return "";
        }
        //姓名为1位的
        if(name.length() == 1){
            return name;
        }

        //姓名大于一位
        if(name.length() > 1){
            String tempName = name.substring(0,1);
            for(int i=0;i<name.length()-1;i++){
                tempName = tempName + "*";
            }
            return tempName;
        }

        return name;
    }


    //处理手机号
    public static String operatePhone(String phone){

        if(StringUtils.isBlank(phone)){
            return "";
        }

        if(phone.length() == 11){
            return phone.substring(0,3)+"****"+phone.substring(7,11);
        }

        return phone;
    }


    //处理身份证号
    public static String operateIdNo(String idNo){
        if(StringUtils.isBlank(idNo)){
            return "";
        }

        if(idNo.length() == 15){
            return idNo.substring(0,3)+"*********"+idNo.substring(11,15);
        }

        if(idNo.length() == 18){
            return idNo.substring(0,3)+"************"+idNo.substring(14,18);
        }

        return idNo;
    }

    //处理银行卡号
    public static String operateBankcardNo(String bankcardNo){
        if(StringUtils.isBlank(bankcardNo)){
            return "";
        }

        if(bankcardNo.length() >= 8){
            int loopLength = bankcardNo.length() - 8;
            String tempStr = "";
            for(int i=0;i<loopLength;i++){
                tempStr = tempStr+"*";
            }
            return bankcardNo.substring(0,4)+tempStr+bankcardNo.substring(bankcardNo.length()-4,bankcardNo.length());
        }

        return bankcardNo;
    }


    public static void main(String[] args){
        String name = "王张飞";
        System.out.println(operateName(name));

        String phone = "13120784975";
        System.out.println(operatePhone(phone));

        String operateIdNo = "341622198902262314";
        System.out.println(operateIdNo(operateIdNo));

        String bankcardNo = "341622198902262314";
        System.out.println(operateBankcardNo(bankcardNo));
    }

}
