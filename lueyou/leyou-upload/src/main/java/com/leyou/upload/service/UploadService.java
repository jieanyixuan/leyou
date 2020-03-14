package com.leyou.upload.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 王俊杰
 * 文件上传业务接口
 */
public interface UploadService {
    /**
     * 图片上传
     * @param file
     * @return
     */
    public String uploadImage(MultipartFile file);
}
