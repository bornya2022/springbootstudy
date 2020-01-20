package com.springboot.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 全局数据配置实例请求器
 */

@Controller
public class GlobalController{
    @GetMapping("/globalhello")
    @ResponseBody
    public void GlobalHello(Model model){
        Map<String,Object> map=model.asMap();
        Set<String> set=map.keySet();
        //迭代器
        Iterator<String> iterator=set.iterator();
        while (iterator.hasNext()){
            String key= iterator.next();
            Object value=map.get(key);
            System.out.println(key+">>>>>"+value);
        }

    }
}
