package com.zhuanyi.vjwealth.wallet.mobile.common.util;

/**
 * Created by wzf on 2016/8/15.
 */
public class NumberCommonUtils {

    /**
     * 处理利率显示问题
     * @param goal
     * @return
     */
    public static String handleNumberStr(String goal){
        String rex1 = ".00%";
        String rex2 = ".0%";
        String rex3 = "0%";
        String rex4 = "00%";
        if(goal.endsWith(rex1)){
            return goal.replace(rex1,"%");
        }if(goal.endsWith(rex2)){
            return goal.replace(rex2,"%");
        }if(goal.endsWith(rex3)){
            return goal.replace(rex3,"%");
        }if(goal.endsWith(rex3)){
            return goal.replace(rex4,"%");
        }
        return goal;
    }

}
