package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.entity.SaleChanceRecord;
import com.kaishengit.crm.service.AccountService;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.SaleChanceRecordService;
import com.kaishengit.crm.service.SaleChanceService;
import com.kaishengit.crm.service.impl.SaleChanceRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
@Controller
@RequestMapping("/chance")
public class ChanceController {
    @Autowired
    SaleChanceService saleChanceService;
    @Autowired
    AccountService accountService;
    @Autowired
    CustomerService customerService;
    @Autowired
    SaleChanceRecordService saleChanceRecordService;
    /**
     * 跳转到我的机会页面
     * @param accountId
     * @param pageNo
     * @return
     */
    @GetMapping("/{accountId}/my")
    public String myChance(@PathVariable Integer accountId,
                           @RequestParam(defaultValue="1",required=false) int pageNo,
                           Model model){
        //查找用户信息
        Account account = accountService.findByAccountId(accountId);
        //根据用户Id查找所对应的chance
        List<SaleChance> saleChanceList = saleChanceService.findByAccountId(accountId);

        model.addAttribute("saleChanceList",saleChanceList);
        model.addAttribute("account",account);
        return "/sale/chance";
    }

    /**
     * 新增机会
     * @param accountId
     * @return
     */
    @GetMapping("/{accountId}/my/add")
    public String addChance(@PathVariable Integer accountId,Model model){
        //获取当前用户的所有的客户信息
        List<Customer> customerList = customerService.findAllCustomerByAccountId(accountId);
        model.addAttribute("customerList",customerList);
        model.addAttribute("accountId",accountId);
        return "/sale/add";
    }
    @PostMapping("/{accountId}/my/add")
    public String addChance(@PathVariable Integer accountId,
                            SaleChance saleChance, RedirectAttributes redirectAttributes){
        saleChance.setAccountId(accountId);
        saleChance.setCreateTime(new Date());
        saleChance.setLastTime(new Date());
        saleChanceService.save(saleChance);
        redirectAttributes.addFlashAttribute("message","新增机会成功!!!");
        return "redirect:/chance/"+accountId+"/my";
    }

    /**
     * 向跟进信息的详情页跳转
     * @param saleChanceId
     * @param model
     * @return
     */
    @GetMapping("/{saleChanceId}/info")
    public String changeInfo(@PathVariable Integer saleChanceId,Model model){
        //通过销售机会的ID获得销售机会
        SaleChance saleChance = saleChanceService.findBySaleChanceServiceByKeyAndCustomer(saleChanceId);
        //查询所有的跟进记录
        List<SaleChanceRecord> saleChanceRecordList = saleChanceRecordService.findBySale_id(saleChanceId);
        //获取所有的跟进进度
        List<String> progressList = saleChanceService.findAllProgress();
        model.addAttribute("progressList",progressList);
        model.addAttribute("saleChance",saleChance);
        model.addAttribute("saleChanceRecordList",saleChanceRecordList);
        return "/sale/chanceInfo";
    }

    /**
     * 新增进度信息
     * @param saleChanceId
     * @param saleId
     * @param progress
     * @return
     */
    @PostMapping("/my/{saleChanceId}/progress/update")
    public String updateProgress(@PathVariable Integer saleChanceId,Integer saleId,String progress){
        //1.新增进度信息
        saleChanceRecordService.updateProgress(saleId,progress);
        return "redirect:/chance/"+saleChanceId+"/info";
    }
    @GetMapping("/my/{saleChanceId}/new/record/{context}")
    public String updateProgress(@PathVariable Integer saleChanceId,@PathVariable String context){
        //1.新增进度信息
        saleChanceRecordService.saveContext(context,saleChanceId);
        return "redirect:/chance/"+saleChanceId+"/info";
    }

    /**
     * 删除机会
     * TODO  删除机会的实现 客户详细页面的跟进记录
     * @param saleChanceId
     * @return
     */
    @GetMapping("/my/{saleChanceId}/delete/{accountId}")
    public String deleteChance(@PathVariable Integer saleChanceId,@PathVariable Integer accountId){
        //删除机会
        saleChanceService.deleteByKey(saleChanceId);
        return "redirect:/chance/"+accountId+"/my";
    }

}
