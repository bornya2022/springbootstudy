package com.springboot.study.controller;

import com.springboot.study.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图控制器
 */
@Controller
public class BookController {
    @GetMapping("/books")
    public ModelAndView books(){
        List<Book> bookList=new ArrayList<>();
        Book book=new Book();
        book.setId(1);
        book.setName("三国演义");
        book.setAutor("罗贯中");
        Book book1=new Book();
        book1.setId(2);
        book1.setName("红楼梦");
        book1.setAutor("曹雪芹");
        bookList.add(book);
        bookList.add(book1);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("books",bookList);
        modelAndView.setViewName("books");
        return modelAndView;
    }

}
