package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.service.AccountService;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.SaleChanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerController extends EnterPersonController{
    @Autowired
    CustomerService customerService;
    @Autowired
    AccountService accountService;
    @Autowired
    SaleChanceService saleChanceService;

    public Map getCustomerMessage(HttpSession session){
        //获取当前登录的用户信息
        Account account = (Account) session.getAttribute("account");

        //获取所有的行业
        List<String> tradeList = customerService.findAllTrade();
        //获取所有的用户来源
        List<String> sourceList = customerService.findAllSource();
        Map map = new HashMap();
        map.put("tradeList",tradeList);
        map.put("sourceList",sourceList);
        map.put("account",account);
        return map;
    }


    //    员工信息页面
    /**
     * 我的客户列表
     */
    @GetMapping("/my")
    public String customerPage(HttpSession httpSession, Model model){
//        查询当前登录用户的所有的客户信息

       List<Customer> customerList =  customerService.findAllCustomerByAccountId(getCustomerAccount(httpSession));

        model.addAttribute("customerList",customerList);

        return "customer/myCustomer";
    }

    /**
     * 向客户详细信息页面进行跳转
     * @param customerId
     * @param model
     * @return
     */
    @GetMapping("/{customerId}/customerMessage")
    public String customerMessage(@PathVariable int customerId, Model model){
        //查找所有用户
        List<Account> accountList = accountService.findAllAccount();
        //通过customerId来查找
        Customer customer = customerService.findByCustomerId(customerId);
        //获取所有机会
        List<SaleChance> saleChanceList = saleChanceService.findByCustId(customerId);
        model.addAttribute("customer",customer);
        model.addAttribute("accountList",accountList);
        model.addAttribute("saleChanceList",saleChanceList);
        return "customer/customerMessage";
    }

    /**
     * 向添加新客户页面跳转
     * @return
     */
    @GetMapping("/add")
    public String addNewCustomer(Model model,HttpSession session){

        Map map = getCustomerMessage(session);
        model.addAttribute("account",map.get("account"));
        model.addAttribute("tradeList",map.get("tradeList"));
        model.addAttribute("sourceList",map.get("sourceList"));

        return "customer/addNewCustomer";
    }

    /**
     * 新增客户
     * @param customer
     * @return
     */
    @PostMapping("/add")
    public String addNewCustomer(Customer customer,RedirectAttributes redirectAttributes){

        customerService.saveNewCustomer(customer);
        redirectAttributes.addFlashAttribute("message","新增客户成功!!!");
        return "redirect:/customer/my";
    }

    /**
     * 修改客户信息
     * @param model
     * @param session
     * @param customerId
     * @return
     */
    @GetMapping("/{customerId:\\d+}/change")
    public String changeCustomer(Model model,HttpSession session,@PathVariable int customerId){
        System.out.println(customerId);
        Map map = getCustomerMessage(session);
        Customer customer = customerService.findByCustomerId(customerId);
//        model.addAttribute("account",map.get("account"));
        model.addAttribute("tradeList",map.get("tradeList"));
        model.addAttribute("sourceList",map.get("sourceList"));
        model.addAttribute("customer",customer);
        return "customer/changeCustomer";
    }
    @PostMapping("/{customerId:\\d+}/change")
    public String changeCustomer(Customer customer,@PathVariable Integer customerId){
//        修改用户
        customer.setId(customerId);

        customerService.update(customer);

        return "redirect:/customer/my";
    }

    /**TODO
     * 删除客户
     * @param customerId
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/{customerId:\\d+}/delete")
    public String deleteCustomer(@PathVariable Integer customerId , RedirectAttributes redirectAttributes){
        customerService.deleteCustomerByCustomerId(customerId);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/customer/my";
    }
    @GetMapping("/{customerId:\\d+}/delete/public")
    public String deleteCustomerPublic(@PathVariable Integer customerId , RedirectAttributes redirectAttributes){
        customerService.deleteCustomerByCustomerId(customerId);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/customer/public";
    }

    /**
     * 转交客户
     * @param customerId
     * @param accountId
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/to/{customerId:\\d+}/other/{accountId:\\d+}")
    public String toOther(@PathVariable Integer customerId,
                          @PathVariable Integer accountId,
                          RedirectAttributes redirectAttributes){
//        更新转交信息
        Customer customer = customerService.findByCustomerId(customerId);
        customer.setAccountId(accountId);
        customerService.update(customer);
        redirectAttributes.addFlashAttribute("message","转交成功!!");
        return "redirect:/customer/my";
    }

    /**
     * 跳转到公海页面
     * @param model
     * @return
     */
    @GetMapping("/public")
    public String publicCustomer(Model model){
//        获取所有accountId为null的客户
        List<Customer> customerList = customerService.findAllPublicCustomer();
        model.addAttribute("customerList",customerList);
        return "/customer/publicCustomer";
    }

    /**
     * 把客户放入公海
     * @return
     */
    @GetMapping("/my/{customerId}/public")
    public String myToPublic(@PathVariable Integer customerId,RedirectAttributes redirectAttributes){
        Customer customer = customerService.findByCustomerId(customerId);
        customer.setAccountId(0);
        customerService.update(customer);
        redirectAttributes.addFlashAttribute("message","放入公海成功!!");
        return "redirect:/customer/my";
    }

    /**
     * 将客户信息导出为xls文件
     * @return
     */
    @GetMapping("/my/export.xls")
    public String exportXlsData(){



        return "redirect:/customer/public";
    }
    /**
     *
     */
    public String checkCustomerRole(){


        return null;
    }
}
