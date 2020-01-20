package com.springboot.study.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 全局异常捕获机制的实现
 * 文件上传大小限制异常
 */
@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public void uploadException(MaxUploadSizeExceededException e, HttpServletResponse resp)
        throws IOException{
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out=resp.getWriter();
        ///日志输出
        out.write("上传文件大小超出限制");
        out.flush();
        out.close();

    }
}
