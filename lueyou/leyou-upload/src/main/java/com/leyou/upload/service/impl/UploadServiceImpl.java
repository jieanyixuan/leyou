package com.leyou.upload.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.upload.service.UploadService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author 王俊杰
 * 文件上传业务层
 */
@Service
@Transactional
public class UploadServiceImpl implements UploadService {

    //定义可以上传的文件类型
    private static final List<String> CONTENT_TYPES = Arrays.asList("image/jpeg", "image/gif");
   //定义日志
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);

    //fastFDS文件系统java客户端
    @Autowired
    private FastFileStorageClient fastFileStorageClient;
    /**
     * 图片上传
     *
     * @param file
     * @return
     */
    @Override
    public String uploadImage(MultipartFile file) {
        //得到文件的全名称
        String filename = file.getOriginalFilename ();

        //得到文件传输类型
        String contentType = file.getContentType ();
        //检查文件类型
        if (!CONTENT_TYPES.contains (contentType)) {
            LOGGER.info ("文件类型不合法:{}",filename);
            return null;
        }
        //检查文件内容
        try {
            BufferedImage bufferedImage = ImageIO.read (file.getInputStream ());
            if (bufferedImage == null) {
                LOGGER.info ("文件内容不合法:{}",filename);
                return null;
            }

            //文件后缀
            String suffix = StringUtils.unqualify (filename); //该方法得到最后一个点后面的内容

            //文件上传到文件服务器
            //file.transferTo (new File ("E:\\imageservice\\logo\\" + filename));
            StorePath storePath = fastFileStorageClient.uploadFile (file.getInputStream (), file.getSize (), suffix, null);

            //生成url地址,返回
            //return "http://image.leyou.com/logo/" + filename;
            return "http://image.leyou.com/" + storePath.getFullPath ();
        } catch (IOException e) {
            LOGGER.info ("服务器内部错误:{}",filename);
            e.printStackTrace ();
        }

        return null;
    }
}
