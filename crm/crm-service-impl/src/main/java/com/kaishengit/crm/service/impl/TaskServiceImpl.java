package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.example.TaskExample;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.jobs.SendMessageJob;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.mapper.SaleChanceMapper;
import com.kaishengit.crm.mapper.TaskMapper;
import com.kaishengit.crm.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private SaleChanceMapper saleChanceMapper;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Override
    public List<Task> findByAccountId(Integer accountId) {
        TaskExample taskExample = new TaskExample();
        taskExample.createCriteria().andAccountIdEqualTo(accountId);
        List<Task> taskList = taskMapper.selectByExample(taskExample);
        for(Task task : taskList){
            if(task.getCustId()!=null){
                //通过客户id查询该客户对象 并封装到Tesk实体类中
                Customer customer = customerMapper.selectByPrimaryKey(task.getCustId());
                task.setCustomer(customer);
            }else if(task.getSaleId()!=null){
                SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(task.getSaleId());
                task.setSaleChance(saleChance);
            }
        }
        return taskList;
    }

    /**
     * 添加新的Task
     * @param task
     */
    @Override
    @Transactional
    public void save(Task task) {
        //添加新的调度任务
        task.setCreateTime(new Date());
        taskMapper.insertSelective(task);

        //判断 提醒是否是空值
        if(task.getRemindTime()!=null) {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.putAsString("accountId", task.getAccountId());
            jobDataMap.put("message", task.getTitle());
            JobDetail jobDetail = JobBuilder
                    .newJob(SendMessageJob.class)
                    .setJobData(jobDataMap)
                    .withIdentity(new JobKey("taskID:" + task.getId(), "sendMessageGroup"))
                    .build();

            //2017-09-08 12:35 -> cron
            //String -> DateTime
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String newFinshTime = simpleDateFormat.format(task.getRemindTime());
            DateTime dateTime = formatter.parseDateTime(newFinshTime);

            StringBuilder cron = new StringBuilder("0")
                    .append(" ")
                    .append(dateTime.getMinuteOfHour())
                    .append(" ")
                    .append(dateTime.getHourOfDay())
                    .append(" ")
                    .append(dateTime.getDayOfMonth())
                    .append(" ")
                    .append(dateTime.getMonthOfYear())
                    .append(" ? ")
                    .append(dateTime.getYear());

//            logger.info("CRON EX: {}" ,cron.toString());

            ScheduleBuilder scheduleBuilder =
                    CronScheduleBuilder.cronSchedule(cron.toString()); //!!!! Cron表达式
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withSchedule(scheduleBuilder)
                    .build();

            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                scheduler.scheduleJob(jobDetail, trigger);
                scheduler.start();
            } catch (Exception ex) {
                throw new ServiceException(ex, "添加定时任务异常");
            }


        }


    }

    @Override
    public void changeState(Integer taskId) {
        //根据taskId查询对象
        Task task = taskMapper.selectByPrimaryKey(taskId);
        System.out.println(task.getDone()+"***************************************");
        if(task.getDone()==0){
            task.setDone((byte)1);
            System.out.println("********************************");
        }else{
            task.setDone((byte)0);
        }
        //更新该对象
        taskMapper.updateByPrimaryKey(task);
    }

    @Override
    public void deleteByKey(Integer taskId) {
        Task task = taskMapper.selectByPrimaryKey(taskId);
        taskMapper.deleteByPrimaryKey(taskId);
        if(task.getRemindTime()!=null){
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                scheduler.deleteJob(new JobKey("taskID:" + taskId, "sendMessageGroup"));
            } catch (SchedulerException e) {
                throw new ServiceException(e,"删除定时任务异常");
            }
        }

    }
}
