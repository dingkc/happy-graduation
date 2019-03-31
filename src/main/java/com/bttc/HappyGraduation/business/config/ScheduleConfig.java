package com.bttc.HappyGraduation.business.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

/**
 * Created by jiajt on 2018/7/6.
 */
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer,AsyncConfigurer {

    /** 异步处理 */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler taskScheduler = taskScheduler();
        taskRegistrar.setTaskScheduler(taskScheduler);
    }

    /** 异步处理 */
    @Override
    public Executor getAsyncExecutor() {
        return taskScheduler();
    }

    /** 异步处理 异常 */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }

    /** 定时任务多线程处理 */
    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(20);
        scheduler.setThreadNamePrefix("osrdcTask-");
        //处理web app关闭时，不强制中断task的执行，有60秒的缓冲期，完成当前需要完成的任务。缺省为false，强制中断
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }

}
