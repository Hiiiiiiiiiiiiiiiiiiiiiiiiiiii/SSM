package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.service.DiskService;
import com.kaishengit.web.result.AjaxResult;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/disk")
public class DiskController {
    @Autowired
    DiskService diskService;

    String accessKey ="AopW6jDWoGJM_HjV3zaA1Rr77Tl_pgkK2KnNy5ZK";

    String secreKey = "o1zzCmG_JalqW2QcGuXI8oec1HSq0KJfflJOZp0D";

    String bucket = "qiniu";
    /**
     * 跳转到公司网盘页面
     * @param model
     * @param pid
     * @return
     */
    @GetMapping("/company/{pid:\\d+}")
    public String toMyDisk(Model model, @PathVariable Integer pid){
        //获取凭证
        Auth auth = Auth.create(accessKey,secreKey);
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"name\":\"$(fname)\",\"filesize\":$(fsize)}");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        model.addAttribute("upToken",upToken);
        //获取数据库中的文件
        List<Disk> diskList = diskService.findFileByPid(pid);
        model.addAttribute("diskList",diskList);
        model.addAttribute("pid",pid);
        return "disk/disk";
    }
    @GetMapping("/company/create/{pid:\\d+}/dir")
    @ResponseBody
    public AjaxResult createDir(@PathVariable Integer pid, @RequestParam String folderName, HttpSession httpSession){
        Account account = (Account) httpSession.getAttribute("account");
        //创建该文件夹
        diskService.createDir(pid,folderName,account.getUserId());
        //根据pid获取所有文件夹信息
        List diskList = diskService.findFileByPid(pid);
        return AjaxResult.successWithData(diskList);
    }

}
