package com.kaishengit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.multipart.MultipartFile;



@Controller
public class Enter {
    @GetMapping("/enter/{pageNo:\\d+}")
    public String enter(@PathVariable int pageNo){

        return "user";
    }
    @PostMapping("/enter")
    public String enter(String userName,MultipartFile files){
        System.out.println(files.getOriginalFilename());
        return "redirect:/user";
    }
}
