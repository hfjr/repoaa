package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto.fundLoan;

import java.util.List;

/**
 * Created by wzf on 2016/10/16.
 */
public class FundLoanCityListDTO {
    private String selectDesc;//请选择城市
    private List<CityBaseDTO> hotCitys;//热门城市
    private List<CityDTO> allCitys;//所有城市

    public String getSelectDesc() {
        return selectDesc;
    }

    public void setSelectDesc(String selectDesc) {
        this.selectDesc = selectDesc;
    }

    public List<CityBaseDTO> getHotCitys() {
        return hotCitys;
    }

    public void setHotCitys(List<CityBaseDTO> hotCitys) {
        this.hotCitys = hotCitys;
    }

    public List<CityDTO> getAllCitys() {
        return allCitys;
    }

    public void setAllCitys(List<CityDTO> allCitys) {
        this.allCitys = allCitys;
    }
}
