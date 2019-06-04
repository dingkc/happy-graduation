package com.bttc.HappyGraduation.scheduler.service;


import com.alibaba.fastjson.JSON;
import com.bttc.HappyGraduation.scheduler.JobConstants;
import com.bttc.HappyGraduation.scheduler.dao.*;
import com.bttc.HappyGraduation.scheduler.pojo.*;
import com.bttc.HappyGraduation.scheduler.util.ExceptionUtil;
import com.bttc.HappyGraduation.scheduler.util.IDUtil;

import org.apache.commons.beanutils.BeanUtils;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by jiajt on 2018/11/1.
 */
@Component
@Transactional
public abstract class AbstractScanService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private JobDefineRepository jobDefineRepository;

    @Autowired
    private JobDefineHistoryRepository jobDefineHistoryRepository;

    @Autowired
    private JobLogRepository jobLogRepository;

    @Autowired
    private JobParameterRepository jobParameterRepository;

    @Autowired
    private JobParameterHistoryRepository jobParameterHistoryRepository;

    @Autowired
    private Environment env;


    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractScanService.class);

    public abstract List<JobDefinePO> getjobData() throws IOException, SQLException, SchedulerException;

    public abstract Trigger formateTrigger(JobDefinePO jobDefinePO);

    public void beforeExcuted(JobExecutionContext context) {
        LOGGER.info("进程业务逻辑处理开始，任务beforeExcuted，处理表数据");
        String jobDefineId = (String) context.getJobDetail().getJobDataMap().get(JobConstants.COL_JOB_DEFINE_ID);
        Optional<JobDefinePO> jobDefinePO = jobDefineRepository.findById(Long.valueOf(jobDefineId));
        if(!jobDefinePO.isPresent()){
            return;
        }
        List<JobParameterPO> jobParameterPOS = jobParameterRepository.findByJobDefineId(Long.valueOf(jobDefineId));
        Map<String,String> params = new HashMap<>();
        for(JobParameterPO jobParameterPO:jobParameterPOS){
            params.put(jobParameterPO.getParamKey(),jobParameterPO.getParamValue());
        }
        //记录日志
        JobLogPO jobLogPO = new JobLogPO();
        //注册事件转换类，防止date为空时转换报错
        ConvertUtils.register(new DateConverter(null), Date.class);
        try {
            BeanUtils.copyProperties(jobLogPO, jobDefinePO.get());
            jobLogPO.setJobLogId(IDUtil.createID());
            jobLogPO.setCreateDate(new Date(System.currentTimeMillis()));
            jobLogPO.setExcuteState(JobConstants.JOB_EXCUTE_STATE_RUNNING);
            jobLogPO.setExcuteMessage(JobConstants.JOB_START_MESSAGE+JSON.toJSONString(params));
            jobLogRepository.save(jobLogPO);
        } catch (Exception e) {
            LOGGER.error("保存进程开始日志报错！");
            LOGGER.error(ExceptionUtil.getStackTrace(e));
        }
    }

    public void afterExcuted(JobExecutionContext context, String message, long constTimems, boolean isSucess){
        LOGGER.info("进程业务逻辑处理完成，任务afterExcuted，处理表数据");
        if(message!=null && message.length()>2000){
            message = message.substring(0,2000);
        }
        String jobDefineId = (String) context.getJobDetail().getJobDataMap().get(JobConstants.COL_JOB_DEFINE_ID);
        try {
            Optional<JobDefinePO> jobDefinePO = jobDefineRepository.findById(Long.valueOf(jobDefineId));
            if(!jobDefinePO.isPresent()){
                return;
            }
            //记录日志
            JobLogPO jobLogPO = new JobLogPO();
            //注册事件转换类，防止date为空时转换报错
            ConvertUtils.register(new DateConverter(null), Date.class);
            BeanUtils.copyProperties(jobLogPO, jobDefinePO.get());
            jobLogPO.setJobLogId(IDUtil.createID());
            jobLogPO.setCostTime(constTimems);
            jobLogPO.setExcuteMessage(message);
            jobLogPO.setCreateDate(new Date(System.currentTimeMillis()));
            jobLogPO.setExcuteState(isSucess == true ? JobConstants.JOB_EXCUTE_STATE_SUCCESS : JobConstants.JOB_EXCUTE_STATE_FAILURE);
            jobLogRepository.save(jobLogPO);
            //插入历史表数据
            JobDefineHistoryPO jobDefineHistoryPO = new JobDefineHistoryPO();
            BeanUtils.copyProperties(jobDefineHistoryPO, jobDefinePO.get());
            Long jobDefineHistoryId = IDUtil.createID();
            jobDefineHistoryPO.setJobDefineHistoryId(jobDefineHistoryId);
            jobDefineHistoryPO.setCreateDate(new Date(System.currentTimeMillis()));
            jobDefineHistoryPO.setDoneDate(new Date(System.currentTimeMillis()));
            jobDefineHistoryPO.setExcuteState(isSucess == true ? JobConstants.JOB_EXCUTE_STATE_SUCCESS : JobConstants.JOB_EXCUTE_STATE_FAILURE);
            jobDefineHistoryRepository.save(jobDefineHistoryPO);
            //参数插入历史表
            List<JobParameterPO> jobParameterPOS = jobParameterRepository.findByJobDefineId(Long.valueOf(jobDefineId));
            List<JobParameterHistoryPO> jobParameterHistoryPOS = new ArrayList<>();
            for(JobParameterPO jobParameterPO:jobParameterPOS){
                JobParameterHistoryPO jobParameterHistoryPO = new JobParameterHistoryPO();
                BeanUtils.copyProperties(jobParameterHistoryPO, jobParameterPO);
                jobParameterHistoryPO.setJobParameterHistoryId(IDUtil.createID());
                jobParameterHistoryPO.setJobDefineHistoryId(jobDefineHistoryId);
                jobParameterHistoryPOS.add(jobParameterHistoryPO);
            }
            jobParameterHistoryRepository.saveAll(jobParameterHistoryPOS);
        } catch (Exception e1) {
            LOGGER.error("任务afterExcuted表数据处理出现异常，没有转移到历史表，单次进程可能又被拉起");
            LOGGER.error(ExceptionUtil.getStackTrace(e1));
        }
    }

    public JobDetail formateJob(JobDefinePO jobDefinePO) throws ClassNotFoundException, IOException, SQLException {
        String jobName = jobDefinePO.getJobDefineName()+ JobConstants.JOB_UNDERLINE+ jobDefinePO.getJobDefineId();
        String jobGroup = jobDefinePO.getJobDefineGroup();
        Class className = Class.forName(jobDefinePO.getClassName());
        //构建job信息,在用JobBuilder创建JobDetail的时候，有一个storeDurably()方法，可以在没有触发器指向任务的时候，将任务保存在队列中了。然后就能手动触发了
        JobDetail jobDetail = JobBuilder.newJob(className)
                .withDescription(jobDefinePO.getJobDefineType()).requestRecovery(false)
                .withIdentity(jobName, jobGroup)
                .storeDurably().build();
        //用JopDataMap来传递数据
        List<JobParameterPO> jobParameterPOS = jobParameterRepository.findByJobDefineIdAndState(jobDefinePO.getJobDefineId(), JobConstants.JOB_STATE_EFFECT);
        for (JobParameterPO jobParameterPO : jobParameterPOS) {
            jobDetail.getJobDataMap().put(jobParameterPO.getParamKey(), jobParameterPO.getParamValue());
        }
        jobDetail.getJobDataMap().put(JobConstants.COL_JOB_DEFINE_ID, jobDefinePO.getJobDefineId().toString());
        return jobDetail;
    }

    public List<JobDefinePO> compareJobs(List<JobDefinePO> jobDefinePOS) throws SchedulerException {
        List<JobDefinePO> addJobs = new ArrayList<>();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        for (JobDefinePO jobDefinePO : jobDefinePOS) {
            JobKey jobKey = JobKey.jobKey(jobDefinePO.getJobDefineName()+ JobConstants.JOB_UNDERLINE+ jobDefinePO.getJobDefineId(), jobDefinePO.getJobDefineGroup());
            //表里面为生效，实例上不存在，加入addlist
            if (JobConstants.JOB_STATE_EFFECT.equals(jobDefinePO.getState())){
                if(!scheduler.checkExists(jobKey)){
                    addJobs.add(jobDefinePO);
                    LOGGER.info("任务"+jobKey+"加入调度");
                }
            }else if (JobConstants.JOB_STATE_EXPIRE.equals(jobDefinePO.getState()) && scheduler.checkExists(jobKey)){//表里面为失效，实例上存在，直接删除
                scheduler.deleteJob(jobKey);
            }
        }
        return addJobs;
    }

    public List<String> getScanJobGroup(){
        String jobGroupProperty = env.getProperty(JobConstants.SCAN_JOB_GROUP_PROPERTY);
        return jobGroupProperty != null ? new ArrayList<>(Arrays.asList(jobGroupProperty.split(","))) : new ArrayList<>();
    }

    public List<String> getScanJobName(){
        String jobNameProperty = env.getProperty(JobConstants.SCAN_JOB_NAME_PROPERTY);
        return jobNameProperty != null ? new ArrayList<>(Arrays.asList(jobNameProperty.split(","))) : new ArrayList<>();
    }

}
