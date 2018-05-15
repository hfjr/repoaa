package com.zhuanyi.vjwealth.wallet.mobile.index.server.dto;


public enum HomeAPIVersionEnum {
    V37("v3.7"), V38("v3.8");

    private String version;

    private HomeAPIVersionEnum(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
