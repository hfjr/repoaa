package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.loanProduct;

import java.io.Serializable;

/**
 *
 * Created by hexy on 16/8/26.
 */
public class HouseCity implements Serializable {

    private String cityCode;
    private String cityName;
    private String canChoose;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCanChoose() {
        return canChoose;
    }

    public void setCanChoose(String canChoose) {
        this.canChoose = canChoose;
    }

    @Override
    public String toString() {
        return "HouseCity{" +
                "cityCode='" + cityCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", canChoose='" + canChoose + '\'' +
                '}';
    }
}
