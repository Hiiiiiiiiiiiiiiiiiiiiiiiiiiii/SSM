package com.kaishengit.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Book;
import com.kaishengit.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/book/list")
public class BookController {
    @Autowired
    BookService bookService;
//    详细页面
    @GetMapping("/{bookId}/detail")
    public String detail(@PathVariable int bookId, Model model) {
        Book book = bookService.findByBookId(bookId);
        model.addAttribute("book",book);
        return "detail";
    }
//    查询所有的书籍
    @GetMapping()
    public String list(@RequestParam(name="p",required = false,defaultValue = "1") int pageNo , Model model){
        PageInfo<Book> pageInfo = bookService.findAll(pageNo);
        model.addAttribute("pageInfo",pageInfo);
        return "web";
    }
//   根据书籍ID删除对应的书籍
    @GetMapping("/{bookId}/delete")
    public String delete(@PathVariable int bookId, RedirectAttributes redirectAttributes){
        bookService.deleteByBookId(bookId);
        redirectAttributes.addFlashAttribute("message", "以成功删除该书籍信息!");
        return "redirect:/book/list";
    }
//    根据书籍ID修改对应的书籍
    @GetMapping("/{bookId}/change")
    public String change(@PathVariable int bookId,Model model){
        Book book = bookService.findByBookId(bookId);
        model.addAttribute("book",book);
        return "change";
    }
    @GetMapping("/update")
    public String update(Book book ,RedirectAttributes redirectAttributes){
        bookService.update(book);
        redirectAttributes.addFlashAttribute("message","修改成功!!!!");
        return "redirect:/book/list";
    }
}
