package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

import java.io.Serializable;

/**
 * 微信图片上传DTO
 * Created by hexy on 16/8/29.
 */
public class UploadPicFileDTO implements Serializable {
    private String code;
    private String message;
    private String  fileId;
    private String  fileNameCode;

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

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileNameCode() {
        return fileNameCode;
    }

    public void setFileNameCode(String fileNameCode) {
        this.fileNameCode = fileNameCode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UploadPicFileDTO{");
        sb.append("code='").append(code).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", fileId='").append(fileId).append('\'');
        sb.append(", fileNameCode='").append(fileNameCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
