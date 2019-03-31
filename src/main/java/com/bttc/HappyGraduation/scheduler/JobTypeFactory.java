package com.bttc.HappyGraduation.scheduler;

import com.bttc.HappyGraduation.scheduler.service.AbstractScanService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by jiajt on 2018/11/7.
 */
@Component
public class JobTypeFactory {
    @Resource
    AbstractScanService circleScanServiceImpl;

    @Resource
    AbstractScanService singleScanServiceImpl;

    public AbstractScanService getBaseJobInstance(String schedulerType){
        switch (schedulerType){
            case "I":
                return singleScanServiceImpl;
            case "C":
                return circleScanServiceImpl;
            default:
                return null;
        }

    }
}
