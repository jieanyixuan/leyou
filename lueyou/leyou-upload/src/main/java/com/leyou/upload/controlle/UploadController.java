package com.leyou.upload.controlle;

import com.leyou.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 王俊杰
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     *
     * @param file 自定义文件上传插件的参数,spring有MultipartFile封装
     * @return
     */
    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file) {
        String url = uploadService.uploadImage (file);
        if (StringUtils.isEmpty (url)) {
            return ResponseEntity.badRequest ().build ();
        }
        return ResponseEntity.ok (url);
    }

}
