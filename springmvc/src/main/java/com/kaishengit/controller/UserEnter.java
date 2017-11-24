package com.kaishengit.controller;

import com.kaishengit.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserEnter {
    @GetMapping("/user")
    public ModelAndView enter(Model model){
//        可以使用model的addAttribute方法来直接给页面传值
//        model.addAttribute("hello","hello world");
//      参数列表填入要跳转的页面 然后使用modelAndView的addObject以键值对的形式给页面传值
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("hello","Hello");
        return modelAndView;
    }

    @PostMapping("/user")
    public String enter(User user){
//      若返回值是以redirect开头的话 是重定向跳转
        return "redirect:/user";
    }

}
