package com.kaishengit.crm.controller;

import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.SaleChanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class StatisticsController {
    @Autowired
    SaleChanceRecordService saleChanceRecordService;
    @Autowired
    CustomerService customerService;
    @GetMapping("/statistics/table")
    public String mian(Model model){
        Date date = null;

        //查询各个进程的数量并封装到一个List中
        //梯形表(业务进度)todo 可根据时间再做拓展
        List<Map<String,Object>> chanceList  = saleChanceRecordService.findAllChanceAddToList(date);
        //折线图(每月新增客户次数)
        List<Map<String,Object>> customerList  = customerService.findCustomerCountByTime();
        model.addAttribute("customerList",customerList);
        model.addAttribute("chanceList",chanceList);
        return "/statistics/main";
    }

}
