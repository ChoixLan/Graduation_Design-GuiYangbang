package com.gyu.controller;

import com.gyu.utils.TokenUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Log4j2
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TokenUtils tokenUtils;

    // 上传文件会自动绑定到MultipartFile
    @PostMapping("/upload")
    public String upload(HttpServletRequest request,
                         @RequestParam("description") String  description,
                         @RequestParam("file") MultipartFile file) throws Exception{
        //接收参数description
        System.out.println("description: " + description);
        //如果文件不为空，写入上传路径
        if (!file.isEmpty()){
            //上传文件路径
            String path = "D:\\development\\毕业设计\\GYUbang - java\\src\\main\\resources\\static\\lostArticle";
            log.info("path = " + path);
            //上传文件名
            String filename = file.getOriginalFilename();
            log.info("filename =",filename);
            File filePath = new File(path, filename);
            //判断路径是否存在，如果不存在就创建一个
            if (!filePath.getParentFile().exists()){
                filePath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            file.transferTo(new File(path,filename));
            System.out.println(filename);
            String Path = request.getScheme() + "://" +
                    request.getServerName() + ":"
                    + request.getServerPort()
                    + "/lostArticle/" + filename;
            return Path;
        }else {
            return "error";
        }
    }

    @RequestMapping("/hello")
    @PreAuthorize("hasAnyAuthority('admin')")//test 权限
    public String hello() {
        return "hello";
    }
}
