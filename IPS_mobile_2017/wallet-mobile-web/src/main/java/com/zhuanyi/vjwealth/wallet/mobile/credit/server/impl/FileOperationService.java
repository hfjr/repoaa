package com.zhuanyi.vjwealth.wallet.mobile.credit.server.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhuanyi.vjwealth.loan.order.vo.UploadIdentitysVO;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fab.core.annotation.Remote;
import com.fab.core.exception.service.AppException;
import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.loan.order.webservice.ILoanApplicationDubboService;
import com.zhuanyi.vjwealth.wallet.mobile.credit.server.IFileOperationService;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UploadFileInfoDTO;
import com.zhuanyi.vjwealth.wallet.mobile.product.server.dto.UploadIdentityPhotosDTO;
import com.zhuanyi.vjwealth.wallet.service.file.upload.server.service.ICommonAttachmentOperate;

@Service
public class FileOperationService implements IFileOperationService {

    @Remote
    private ICommonAttachmentOperate commonAttachmentOperate;

    @Value("${file.types}")
    private String fileTypes;

    @Autowired
    private ILoanApplicationDubboService loanApplicationDubboService;

    @Transactional
    public UploadIdentityPhotosDTO uploadIdentityPhotos(String userId, MultipartFile rightSideFile,
                                                        MultipartFile reverseSideFile, MultipartFile handheldIdentity, String uploadSuccessFileIds,
                                                        String borrowCode) throws AppException {
        if (StringUtils.isBlank(userId)) {
            BaseLogger.audit("上传身份证，用户ID不能为空");
            throw new AppException("上传身份证，用户ID不能为空");
        }
        if (StringUtils.isBlank(borrowCode)) {
            BaseLogger.audit("上传身份证，订单ID不能为空");
            throw new AppException("上传身份证，订单ID不能为空");
        }
        JSONArray jsonFileIds = null;
        try {
            jsonFileIds = (JSONArray) JSONArray.parse(uploadSuccessFileIds);
        } catch (Exception e) {
            throw new AppException("上传成功ID参数格式不正确！");
        }
        List<UploadFileInfoDTO> fileList = new ArrayList<UploadFileInfoDTO>();
        // 判断文件类型 ,过滤文件
        String[] types = StringUtils.split(fileTypes, ",");
        BaseLogger.info(uploadSuccessFileIds);
        if (null != jsonFileIds) {
            for (int i = 0; i < jsonFileIds.size(); i++) {
                JSONObject jo = jsonFileIds.getJSONObject(i);
                UploadFileInfoDTO uploadFileInfoDTO = new UploadFileInfoDTO();
                uploadFileInfoDTO.setFileId(jo.getString("fileId"));
                uploadFileInfoDTO.setFileNameCode(jo.getString("fileNameCode"));
                fileList.add(uploadFileInfoDTO);
            }
        }
        try {
            for (MultipartFile file : new MultipartFile[]{rightSideFile, reverseSideFile, handheldIdentity}) {
                UploadFileInfoDTO uploadFileInfoDTO = new UploadFileInfoDTO();
                if (null != file && !file.isEmpty()) {
                    // 验证文件格式
                    if (FilenameUtils.isExtension(file.getOriginalFilename(), types)) {
                        byte[] bytes = file.getBytes();
                        if (bytes == null || bytes.length == 0) {
                            throw new AppException("文件内容不能为空！");
                        }
                        String fileId = this.uploadPic(file.getOriginalFilename(), bytes);
                        String fileNameCode = FilenameUtils.getBaseName(file.getOriginalFilename());
                        uploadFileInfoDTO.setFileId(fileId);
                        uploadFileInfoDTO.setFileNameCode(fileNameCode);
                        fileList.add(uploadFileInfoDTO);
                    } else {
                        throw new AppException("上传文件格式不对！");
                    }
                }
            }
        } catch (AppException e) {
            throw new AppException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("图片上传失败");
        }
        if (3 != fileList.size()) {
            throw new AppException("请上传身份证正面、反面、手持照片!");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("borrowCode", borrowCode);
        map.put("fileList", fileList);
        BaseLogger.info("______证件照上传信息______:" + JSONObject.toJSONString(map));
        Map<String, Object> saveMap = loanApplicationDubboService.uploadIdentityFormSave(JSONObject.toJSONString(map));
        if (saveMap == null || "0".equals(saveMap.get("isUploadIdentityFormSave"))) {
            throw new AppException("服务器保存文件失败!");
        }
        UploadIdentityPhotosDTO uploadIdentityPhotosDTO = new UploadIdentityPhotosDTO();
        uploadIdentityPhotosDTO.setFileList(fileList);
        uploadIdentityPhotosDTO.setCode("202200");
        uploadIdentityPhotosDTO.setMessage("上传成功");
        BaseLogger.audit("上传成功");
        return uploadIdentityPhotosDTO;
    }

    @Override
    public UploadIdentityPhotosDTO saveUploadIdentitys(UploadIdentitysVO uploadIdentitysVO) throws AppException {
        BaseLogger.audit("白领专享保存上传图片信息开始: " + uploadIdentitysVO);
        // 验证参数
        uploadIdentitysVO.validate();
        //验证文件ID是否存在
        List<String> fileIds = new ArrayList<>();
        fileIds.add(uploadIdentitysVO.getRightSideFileId());
        fileIds.add(uploadIdentitysVO.getReverseSideFileId());
        fileIds.add(uploadIdentitysVO.getHandheldIdentityFileId());
        Boolean isExit = commonAttachmentOperate.isExitAttachementByFileIds(fileIds);
        if (!isExit) {
            BaseLogger.audit("白领专享保存－调用文件系统－图片信息不存在");
            throw new AppException("图片信息不存在");
        }
        List<UploadFileInfoDTO> fileList = new ArrayList<UploadFileInfoDTO>();
        fileList.add(new UploadFileInfoDTO(uploadIdentitysVO.getRightSideFileId(), "200410"));
        fileList.add(new UploadFileInfoDTO(uploadIdentitysVO.getReverseSideFileId(), "200411"));
        fileList.add(new UploadFileInfoDTO(uploadIdentitysVO.getHandheldIdentityFileId(), "200412"));
        UploadIdentityPhotosDTO uploadIdentityPhotosDTO = new UploadIdentityPhotosDTO();
        uploadIdentityPhotosDTO.setFileList(fileList);
        Map<String, Object> saveMap;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("borrowCode", uploadIdentitysVO.getBorrowCode());
            map.put("fileList", fileList);
            BaseLogger.audit("______证件照上传信息______:" + JSONObject.toJSONString(map));
            saveMap = loanApplicationDubboService.uploadIdentityFormSave(JSONObject.toJSONString(map));
        } catch (Exception e) {
            BaseLogger.error("调用信贷系统－保存上传身份证信息异常", e);
            throw new AppException("图片上传失败");
        }
        if (null == saveMap || "0".equals(saveMap.get("isUploadIdentityFormSave"))) {
            uploadIdentityPhotosDTO.setCode("202201");
            uploadIdentityPhotosDTO.setMessage("上传失败");
            BaseLogger.audit("调用信贷系统－服务器保存文件失败!");
        } else {
            uploadIdentityPhotosDTO.setCode("202200");
            uploadIdentityPhotosDTO.setMessage("上传成功");
            BaseLogger.audit("调用信贷系统－保存文件成功!");
        }
        return uploadIdentityPhotosDTO;
    }

    // 上传图片
    public String uploadPic(String fileName, byte[] fileBytes) throws IOException {
        return commonAttachmentOperate.saveAttachementAndReturnFileId(fileName, fileBytes, "identity-pic");
    }

    // 加载图片
    public byte[] loadFile(String fileNo) {
        return commonAttachmentOperate.downloadFile(fileNo);
    }
}
