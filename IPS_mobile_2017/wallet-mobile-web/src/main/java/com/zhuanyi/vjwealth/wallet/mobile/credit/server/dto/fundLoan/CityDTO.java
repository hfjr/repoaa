package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

import java.util.List;

/**
 * Created by wzf on 2016/10/16.
 */
public class CityDTO {

    private String code;//城市首字母

    private List<CityBaseDTO> citys;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<CityBaseDTO> getCitys() {
        return citys;
    }

    public void setCitys(List<CityBaseDTO> citys) {
        this.citys = citys;
    }
}
