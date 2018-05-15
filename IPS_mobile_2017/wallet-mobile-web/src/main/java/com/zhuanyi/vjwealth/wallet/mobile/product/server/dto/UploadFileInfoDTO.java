package com.zhuanyi.vjwealth.wallet.mobile.product.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by yi on 16/3/2.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
public class UploadFileInfoDTO {

   private String  fileId;
    private String  fileNameCode;

    public UploadFileInfoDTO() {
    }

    public UploadFileInfoDTO(String fileId, String fileNameCode) {
        this.fileId = fileId;
        this.fileNameCode = fileNameCode;
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
}
