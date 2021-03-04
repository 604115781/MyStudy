package com.zjk.item.Controller;

import com.zjk.item.Service.QuartzService;
import javax.annotation.PostConstruct;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : zjk
 * @description:TODO
 * @date :2020/11/25 18:27
 */
@RequestMapping("/Quartz")
@Controller
public class QuartzController {

    /*
    * 任务名称
    * */
    private static String jobName = "JOB_NAME";
    /*
    * 任务组
    * */
    private static String jobGroupName = "JOB_GROUP_NAME";

    /*
    * 触发器名称
    * */
    private static String triggerName = "TRIGGER_NAME";

    /*
    * 触发器组
    * */
    private static String triggerGroupName = "TRIGGER_GROUP_NAME";

    @RequestMapping("/build")
    //@PostConstruct
    public void buildQuartz(){

        JobBuilder jobBuilder = JobBuilder.newJob(QuartzService.class).withIdentity(jobName,jobGroupName);
        jobBuilder.usingJobData("jobDetail参数","jobDetail值");

        JobDetail jobDetail = jobBuilder.build();

        /*
        * 按照指定间隔时间执行
        * startNow()马上执行
        * withIntervalInSeconds(1) 按秒循环，1s循环一次
        * repeatForever() 永远循环
        * */
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName,triggerGroupName)
            .startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
            .build();

        //表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/3 * * * * ? *")
            .withMisfireHandlingInstructionDoNothing();

        /*
        *  按照cron格式 定时执行
        * */
        Trigger triggerCron = TriggerBuilder.newTrigger().withIdentity(triggerName,triggerGroupName)
            .startNow().withSchedule(scheduleBuilder).usingJobData("trigger参数","trigger值")
            .build();

        SchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = null;
        try {
            scheduler = stdSchedulerFactory.getScheduler();
            scheduler.scheduleJob(jobDetail,triggerCron);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


}
