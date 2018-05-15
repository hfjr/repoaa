package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by yi on 16/3/1.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class UploadIdentityPhotosDTO {
    String code;
    String message;
    List<UploadFileInfoDTO> fileList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UploadFileInfoDTO> getFileList() {
        return fileList;
    }

    public void setFileList(List<UploadFileInfoDTO> fileList) {
        this.fileList = fileList;
    }
}
