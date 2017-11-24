package com.kaishengit.controller;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Value;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {
    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("qiniu.secreKey")
    private String secreKey;
    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${crop}")
    private String crop;

    @GetMapping("/index")
    public String index (Model model){
        Auth auth = Auth.create(accessKey,secreKey);
        StringMap putPolicy = new StringMap();
//        魔法变量 七牛给出有解释
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        model.addAttribute("upToken",upToken);
        return "index";
    }
}
