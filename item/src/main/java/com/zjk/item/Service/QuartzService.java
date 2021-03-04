package com.zjk.item.Service;

import java.util.Date;
import java.util.Map.Entry;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author : zjk
 * @description:定时任务
 * @date :2020/11/25 18:28
 */
@EnableScheduling
@Component
public class QuartzService implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        //获取JobDetail中的JobDataMap
        JobDataMap jobDetailMap = jobExecutionContext.getJobDetail().getJobDataMap();
        for(Entry<String, Object> objectEntry: jobDetailMap.entrySet()){
            System.out.println("jobDetailMap---"+objectEntry.getKey()+" "+objectEntry.getValue());
        }
        //获取trigger中的JobDataMap
        JobDataMap triggerMap = jobExecutionContext.getTrigger().getJobDataMap();
        for(Entry<String, Object> objectEntry: triggerMap.entrySet()){
            System.out.println("triggerMap---"+objectEntry.getKey()+" "+objectEntry.getValue());
        }
        //获取trigger和JobDetail中的JobDataMap总和，如果有同一个key，则后者会将前者覆盖
        JobDataMap allMap = jobExecutionContext.getMergedJobDataMap();
        for(Entry<String, Object> objectEntry: allMap.entrySet()){
            System.out.println("allMap"+objectEntry.getKey()+" "+objectEntry.getValue());
        }
    }
    @Scheduled(cron = "0/5 * *  * * ? ")
    public void test(){
        System.out.println(new Date().getSeconds());
    }
}
