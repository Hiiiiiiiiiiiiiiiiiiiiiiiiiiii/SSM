package com.kaishengit.service;


import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Book;


public interface BookService {
//    查询所有的book
    PageInfo<Book> findAll(int pageNo);
//    根据bookId查找
    Book findByBookId(int bookId);
//    根据文章的id删除文章
    void deleteByBookId(int bookId);
//    根据文章的id修改该文章
    void update(Book book);
}
