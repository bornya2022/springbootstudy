package com.springboot.study.config;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局数据配置实例
 * 任意Controller请求userInfo方法都可以获得info中的数据
 */
@ControllerAdvice
public class GlobalConfig {
    @ModelAttribute(value = "info")
    public Map<String,String> userInfo(){
        HashMap<String,String> map=new HashMap<>();
        map.put("username","罗贯中");
        map.put("gender","男");
        return map;
    }
    // @InitBinder("b")处理Controller中@ModelAttribute("b")对应的参数
    //在WebDataBinder中还可以设置允许的字段，禁止的字段，必填字段和验证器等。
    @InitBinder("b")
    public void init(WebDataBinder binder){
        binder.setFieldDefaultPrefix("b.");
    }
    @InitBinder("a")
    public void init1(WebDataBinder binder){
        binder.setFieldDefaultPrefix("a.");
    }

}
