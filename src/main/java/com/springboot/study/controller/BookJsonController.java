package com.springboot.study.controller;

import com.springboot.study.model.BookJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 控制器
 * RestController组合注解可以代替Controller和ResponseBody
 */
@RestController
public class BookJsonController {
    @GetMapping("/bookjson")
    public BookJson bookJson(){
        BookJson bookJson=new BookJson();
        bookJson.setName("三国演义");
        bookJson.setAutor("罗贯中");
        bookJson.setPrice(30f);
        bookJson.setPublicationDate(new Date());
        return bookJson;

    }

}
