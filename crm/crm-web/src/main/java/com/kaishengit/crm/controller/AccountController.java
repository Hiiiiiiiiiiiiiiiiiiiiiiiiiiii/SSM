package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.entity.SaleChanceRecord;
import com.kaishengit.crm.exception.AuthenticationException;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.service.AccountService;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.DeptService;
import com.kaishengit.crm.service.SaleChanceRecordService;
import com.kaishengit.web.result.AjaxResult;
import com.kaishengit.web.result.DataTablesResult;
import com.kaishengit.weixin.WeixinUtil;
import com.kaishengit.weixin.exception.WeiXinException;
import com.mysql.fabric.xmlrpc.base.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    DeptService deptService;
    @Autowired
    SaleChanceRecordService saleChanceRecordService;
    @Autowired
    CustomerService customerService;
    @GetMapping("/login")
    public String UserLogin() {
        return "login";
    }

    /**
     * 用户登录验证
     *
     * @param mobile   用户传入的账号（即mobile）
     * @param password 用户待验证的密码
     * @return 跳转页面
     */
    @PostMapping("/login")
    public String UserLogin(String mobile, String password, HttpSession httpSession, RedirectAttributes redirectAttributes) {
//        校验账户以及密码
        Account account = null;
        try {
            account = accountService.login(mobile, password);
        } catch (AuthenticationException message) {
            redirectAttributes.addFlashAttribute("message", "账号或者密码错误请重新登录");
            return "redirect:/login";
        }
        httpSession.setAttribute("account", account);
        return "redirect:/home";
    }

    /**
     * 跳转到home页面
     *
     * @return
     */
    @GetMapping("/home")
    public String home(Model model) {
        Date date = null;

        //查询各个进程的数量并封装到一个List中
        //梯形表(业务进度)todo 可根据时间再做拓展
        List<Map<String,Object>> chanceList  = saleChanceRecordService.findAllChanceAddToList(date);
        //折线图(每月新增客户次数)
        List<Map<String,Object>> customerList  = customerService.findCustomerCountByTime();
        model.addAttribute("customerList",customerList);
        model.addAttribute("chanceList",chanceList);
        return "home";
    }

    /**
     * 退出登录
     *
     * @param httpSession
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession httpSession, RedirectAttributes redirectAttributes) {
        httpSession.invalidate();
        redirectAttributes.addFlashAttribute("message", "已成功退出!!");
        return "redirect:/login";
    }

    /**
     * 跳转到员工列表
     *
     * @return
     */
    @GetMapping("/employee")
    public String employeePage() {
        return "employee/list";
    }

    /**
     * 接收异步请求
     */
    @GetMapping("/employee/dept.json")
    @ResponseBody
    public List<Dept> employeeZTree() {
//        查询所有的部门信息
        return deptService.findAllDept();
    }

    @GetMapping("/employee/load.json")
    @ResponseBody
    public DataTablesResult<Account> loadEmployeeList(
            Integer draw, Integer start, Integer length,
            Integer deptId,
            HttpServletRequest request) {
        String keyword = request.getParameter("search[value]");
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("length", length);
        map.put("accountName", keyword);
        map.put("deptId", deptId);


        List<Account> accountList = accountService.pageForAccount(map);
        Long total = accountService.accountCountByDeptId(deptId);
        return new DataTablesResult<>(draw, total.intValue(), accountList);
    }

    /**
     * 添加新的员工
     * 以及添加关联关系
     * @return
     */
    @GetMapping("/employee/new")
    @ResponseBody
    public AjaxResult addNewEmployee(String userName, String mobile, String password, Integer[] deptId){
        try {
            accountService.saveNewEmployee(userName, mobile, password, deptId);
            return AjaxResult.success();
        } catch (ServiceException ex) {
            ex.printStackTrace();
            return AjaxResult.error(ex.getMessage());
        }
    }

    /**
     * 添加新部门
     * @return
     */
    @PostMapping("/employee/dept/new/{pid}")
    @ResponseBody
    public AjaxResult saveNewDept(String deptName,@PathVariable Integer pid){
        try{
//      存入数据库中
            Integer deptId = deptService.saveNewDept(deptName,pid);

            //      同步到企业微信中
            WeixinUtil weixinUtil = new WeixinUtil();

            weixinUtil.createDept(deptName,pid,deptId);

        }catch (IOException e){

            return AjaxResult.error(e.getMessage());

        }

        return AjaxResult.success();
    }
    @PostMapping("/employee/{accountId:\\d+}/delete")
    @ResponseBody
    public AjaxResult deleteEmployee(@PathVariable int accountId){
        //删除该员工所有信息
        accountService.deleteAccount(accountId);
        return AjaxResult.success();
    }
}
