package com.kaishengit.service;

import com.kaishengit.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BookService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
//   Transactional的作用是开启事务
//    事务的作用就是保证数据的一致性
//    要成功都成功 要失败都失败
//    其底层在做的时候是

    /**
     *      {
     *
     *          Object result = method.invoke(target,args);//目标方法
     *      }
     *      当程序成功运行的时候在进行提交
     *
     *
     */


    @Transactional
    public void save(Book book){
        String sql="insert into t_book(book_name,book_author,book_type)values(?,?,?)";
        jdbcTemplate.update(sql,book.getBookName(),book.getBookAuthor(),book.getBookType());
//        if(1==1){
//            throw new RuntimeException();
//        }
//        jdbcTemplate.update(sql,book.getBookName(),book.getBookAuthor(),book.getBookType());
    }
    public Book findById(int id){
        String sql = "select * from t_book where book_id = ?";
//        BeanPropertyRowMapper 类似与在刚接触java对接数据库的时候 自己建的rowMapper
//        其实现的功能就是从数据库中获取数据并封装成一个对象
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Book.class),id);
    }

    public List<Book> findAll(){
        String sql = "select * from t_book";
        //    获取的是一个集合的时候使用BeanPropertyRowMapper
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Book.class));
    }
    public int count(){
        String sql = "select count(*) from t_book";
//        当需要返回count数据的时候 传入 参数类型 . class 属性
        return jdbcTemplate.queryForObject(sql,int.class);
    }


}
