package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto;

import java.io.Serializable;

/**
 * Created by wzf on 2016/10/27.
 */
public class DeviceDetailInfo implements Serializable{

    private String equipmentNumber;//==设备号（IMEI）==
    private String equipmentIp  ;// ==访问IP（用户提交请求后得到的公网IP）==
    private String osType  ;//==操作系统类型（Android 、 IOS、或其他系统名称）==
    private String osVersion  ;// ==操作系统版本（对应的版本）==
    private String isAgent  ;// ==是否使用代理==
    private String  agentIp ;// ==代理IP（未使用代理时为空）==

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getEquipmentIp() {
        return equipmentIp;
    }

    public void setEquipmentIp(String equipmentIp) {
        this.equipmentIp = equipmentIp;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(String isAgent) {
        this.isAgent = isAgent;
    }

    public String getAgentIp() {
        return agentIp;
    }

    public void setAgentIp(String agentIp) {
        this.agentIp = agentIp;
    }
}
