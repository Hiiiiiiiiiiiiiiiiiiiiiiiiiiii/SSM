package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @GetMapping("/{accountId}/add")
    public String addTodo(@PathVariable Integer accountId,Model model){

        model.addAttribute("accountId",accountId);
        return "/task/newTask";
    }
    @PostMapping("/{accountId}/add")
    public String addTodo(@PathVariable Integer accountId,String finishTime,String remindTime,String title){
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
        task.setTitle(title);
        task.setFinishTime(finishTimes);
        task.setRemindTime(remindTimes);
        task.setAccountId(accountId);
        taskService.save(task);
        return "redirect:/todo/"+accountId;
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
