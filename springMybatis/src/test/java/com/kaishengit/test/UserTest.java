package com.kaishengit.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserTest {
    @Autowired
    private BookMapper bookMapper;

    @Test
    public void findById(){
        Book book = bookMapper.selectByPrimaryKey(1);

        System.out.println(book.getBookName());
    }
}
