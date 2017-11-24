package com.kaishengit.crm.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendMessageJob implements Job {

    private Logger logger = LoggerFactory.getLogger(SendMessageJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String message = (String) dataMap.get("message");
        Integer accountId = dataMap.getInt("accountId");

        logger.info("To:{} Message:{}",accountId,message);
    }
}