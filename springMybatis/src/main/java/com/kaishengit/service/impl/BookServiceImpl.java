package com.kaishengit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Book;
import com.kaishengit.entity.BookExample;
import com.kaishengit.mapper.BookMapper;
import com.kaishengit.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookMapper bookMapper;
    @Override
    public PageInfo<Book> findAll(int pageNo) {
        PageHelper.startPage(pageNo,5);
        List<Book> list = bookMapper.selectByExample(new BookExample());
        return new PageInfo<Book>(list);
    }

    @Override
    public void deleteByBookId(int bookId) {
        bookMapper.deleteByPrimaryKey(bookId);
    }

    @Override
    public void update(Book book) {
        bookMapper.updateByPrimaryKey(book);
        System.out.println(book.getBookName());
//      修改失败


    }

    @Override
    public Book findByBookId(int bookId) {
        return bookMapper.selectByPrimaryKey(bookId);
    }

}
