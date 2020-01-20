package com.springboot.study.controller;

import com.springboot.study.model.Author;
import com.springboot.study.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 请求参数预处理
 * 2个实体类的name属性名相同时，参数传递会混淆，采用@ControllerAdvice结合@InitBinder解决该问题。
 */
public class CanshuBookController {
    @GetMapping("/canshubook")
    @ResponseBody
    public String book(@ModelAttribute("b") Book book, @ModelAttribute("a") Author author){
        return book.toString()+">>>>"+author.toString();
    }
}
