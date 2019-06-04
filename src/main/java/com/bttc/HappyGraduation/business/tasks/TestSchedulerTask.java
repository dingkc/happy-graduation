package com.bttc.HappyGraduation.business.tasks;

import com.bttc.HappyGraduation.scheduler.job.AbstractJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author kaiz
 * @date 19:37 2019/3/28.
 */
@Component
public class TestSchedulerTask extends AbstractJob {
    private final Logger logger = LoggerFactory.getLogger(TestSchedulerTask.class);

    @Override
    public String execute(Map map) {
        logger.info("TestSchedulerTask()开始执行");

        System.out.println("这是测试进程框架");

        String message = "TestSchedulerTask()执行完成";
        logger.info(message);
        return message;
    }
}
