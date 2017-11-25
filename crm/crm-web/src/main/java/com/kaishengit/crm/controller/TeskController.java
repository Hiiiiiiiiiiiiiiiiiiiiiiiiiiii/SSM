package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/todo")
public class TeskController {
    @Autowired
    TaskService taskService;
    @GetMapping("/{accountId}")
    public String todoList(@PathVariable Integer accountId,Model model){
        //获得所有自己的待办事项
        List<Task> taskList = taskService.findByAccountId(accountId);
        model.addAttribute("taskList",taskList);
        model.addAttribute("accountId",accountId);
        return "/task/task";
    }
    @GetMapping("/add")
    public String addTodo(Model model, @RequestParam(required = false) Integer customerId,
                          @RequestParam(required = false) Integer saleChanceId,
                          HttpSession session){
        model.addAttribute("customerId",customerId);
        Account account = (Account) session.getAttribute("account");
        model.addAttribute("accountId",account.getUserId());
        model.addAttribute("saleChanceId",saleChanceId);
        return "/task/newTask";
    }
    @PostMapping("/add")
    public String addTodo(@RequestParam(required=false) Integer customerId,
                          @RequestParam(required=false) Integer accountId,
                          @RequestParam(required = false) Integer saleChanceId,
                          String finishTime, String remindTime, String title){
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date finishTimes = null;
        Date remindTimes = null;
        try {
            finishTimes = format1.parse(finishTime);
            remindTimes = format.parse(remindTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Task task = new Task();
        if(accountId!=null){
            task.setAccountId(accountId);
        }
        if(customerId!=null){
            task.setCustId(customerId);
        }
        if(saleChanceId!=null){
            task.setSaleId(saleChanceId);
        }
        task.setTitle(title);
        task.setFinishTime(finishTimes);
        task.setRemindTime(remindTimes);
        taskService.save(task);
        String url = "redirect:/todo/"+accountId;
        if(customerId!=null){
            url = "redirect:/customer/"+customerId+"/customerMessage";
        }
        if(saleChanceId!=null){
            url = "redirect:/chance/"+saleChanceId+"/info";
        }
        return url;
    }
    @GetMapping("{taskId}/change/{accountId}")
    public String changeStateOfDone(@PathVariable Integer taskId,@PathVariable Integer accountId){
        taskService.changeState(taskId);
        return "redirect:/todo/"+accountId;
    }
    @GetMapping("{taskId}/delete/{accountId}")
    public String deleteTask(@PathVariable Integer taskId,@PathVariable Integer accountId){
        taskService.deleteByKey(taskId);
        return "redirect:/todo/"+accountId;
    }
}
