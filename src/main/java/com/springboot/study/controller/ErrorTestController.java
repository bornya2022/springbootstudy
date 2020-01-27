package com.springboot.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 错误测试控制器
 */
@RestController
public class ErrorTestController {
    @GetMapping("errortest")
    public String errortest(){
        int i=1/0;
        return "error test!";
    }
}
