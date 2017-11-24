package com.kaishengit.test;

import com.kaishengit.entity.Book;
import com.kaishengit.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class JdbcTest {
    @Autowired
    BookService bookService;
    @Test
    public void saveTest(){
        Book book = new Book();
        book.setBookName("龙族");
        book.setBookAuthor("江南");
        book.setBookType("连载小说");
        bookService.save(book);
    }
    @Test
    public void findByIdTest(){
        Book book = bookService.findById(8);
        System.out.println(book);
    }
    @Test
    public void findAllTest(){
        List bookList = bookService.findAll();
        for(int i=0;i<bookList.size();i++){
            System.out.println(bookList.get(i));
        }
    }
    @Test
    public void countTest(){
        int i = bookService.count();
        System.out.println(i);
    }
}
