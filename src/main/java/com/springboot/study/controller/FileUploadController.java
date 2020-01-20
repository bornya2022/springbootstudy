package com.springboot.study.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传处理控制器
 */
@RestController
public class FileUploadController {
    //设置日期格式
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");
    @PostMapping("/upload")
    public String upload(MultipartFile multipartFile, HttpServletRequest request){
        //设置上传文件保存路径
        String realPath=request.getSession().getServletContext().getRealPath("/uploadfile");
        //获取当前系统时间
        String format=simpleDateFormat.format(new Date());
        File file=new File(realPath+format);
        //通过上传日期对上传文件进行分类保存
        if(file.isDirectory()){
            file.mkdirs();
        }
        String oldName=multipartFile.getOriginalFilename();
        //采用随机数方式给上传文件命名，防止文件重名
        String newName= UUID.randomUUID().toString()+
                oldName.substring(oldName.lastIndexOf("."),oldName.length());
        try {
            //保存文件
            multipartFile.transferTo(new File(file,newName));
            //设置上传文件的访问路径
            String filepath=request.getScheme()+"://"+request.getServerName()+":"+
                    request.getServerPort()+"/uploadfile/"+format+newName;
            return  filepath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";


    }
}
