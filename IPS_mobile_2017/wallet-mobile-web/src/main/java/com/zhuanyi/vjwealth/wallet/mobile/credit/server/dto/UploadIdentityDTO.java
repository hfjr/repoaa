package com.zhuanyi.vjwealth.wallet.mobile.credit.server.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Created by hexy on 16/5/12.
 */
public class UploadIdentityDTO implements Serializable {

    private String userId;
    private MultipartFile rightSideFile;
    private MultipartFile reverseSideFile;
    private MultipartFile handheldIdentity;
    private String uploadSuccessFileIds;
    private String borrowCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public MultipartFile getRightSideFile() {
        return rightSideFile;
    }

    public void setRightSideFile(MultipartFile rightSideFile) {
        this.rightSideFile = rightSideFile;
    }

    public MultipartFile getReverseSideFile() {
        return reverseSideFile;
    }

    public void setReverseSideFile(MultipartFile reverseSideFile) {
        this.reverseSideFile = reverseSideFile;
    }

    public String getUploadSuccessFileIds() {
        return uploadSuccessFileIds;
    }

    public void setUploadSuccessFileIds(String uploadSuccessFileIds) {
        this.uploadSuccessFileIds = uploadSuccessFileIds;
    }

    public MultipartFile getHandheldIdentity() {
        return handheldIdentity;
    }

    public void setHandheldIdentity(MultipartFile handheldIdentity) {
        this.handheldIdentity = handheldIdentity;
    }

    public String getBorrowCode() {
        return borrowCode;
    }

    public void setBorrowCode(String borrowCode) {
        this.borrowCode = borrowCode;
    }
}
