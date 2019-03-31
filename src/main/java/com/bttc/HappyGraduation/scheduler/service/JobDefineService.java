package com.bttc.HappyGraduation.scheduler.service;


import com.bttc.HappyGraduation.scheduler.JobConstants;
import com.bttc.HappyGraduation.scheduler.dao.JobDefineRepository;
import com.bttc.HappyGraduation.scheduler.dao.JobParameterRepository;
import com.bttc.HappyGraduation.scheduler.pojo.JobDefinePO;
import com.bttc.HappyGraduation.scheduler.pojo.JobParameterPO;
import com.bttc.HappyGraduation.scheduler.util.IDUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by jiajt on 2018/11/7.
 */
@Component
@Transactional
public class JobDefineService {

    @Autowired
    private JobDefineRepository jobDefineRepository;

    @Autowired
    private JobParameterRepository jobParameterRepository;

    public void addSingleJob(JobDefinePO jobDefinePO, List<JobParameterPO> jobParameterPOS){
        Long jobDefineId = IDUtil.createID();
        jobDefinePO.setJobDefineId(jobDefineId);
        jobDefinePO.setCreateDate(new Date());
        jobDefinePO.setExcuteState(JobConstants.JOB_EXCUTE_STATE_UNSTART);
        jobDefinePO.setJobDefineType(JobConstants.JOB_TYPE_SINGLE);
        jobDefinePO.setDoneDate(new Date());
        jobDefinePO.setState(JobConstants.JOB_STATE_EFFECT);
        jobDefineRepository.save(jobDefinePO);
        for(JobParameterPO jobParameterPO:jobParameterPOS){
            jobParameterPO.setJobParameterId(IDUtil.createID());
            jobParameterPO.setJobDefineId(jobDefineId);
            jobParameterPO.setState(JobConstants.JOB_STATE_EFFECT);
        }
        jobParameterRepository.saveAll(jobParameterPOS);
    }

    public Long addCircleJob(JobDefinePO jobDefinePO,List<JobParameterPO> jobParameterPOS){
        Long jobDefineId = IDUtil.createID();
        jobDefinePO.setJobDefineId(jobDefineId);
        jobDefinePO.setCreateDate(new Date());
        jobDefinePO.setExcuteState(JobConstants.JOB_EXCUTE_STATE_UNSTART);
        jobDefinePO.setDoneDate(new Date());
        jobDefinePO.setJobDefineType(JobConstants.JOB_TYPE_CIRCLE);
        jobDefinePO.setState(JobConstants.JOB_STATE_EFFECT);
        JobDefinePO saveJobDefine = jobDefineRepository.save(jobDefinePO);
        for(JobParameterPO jobParameterPO:jobParameterPOS){
            jobParameterPO.setJobParameterId(IDUtil.createID());
            jobParameterPO.setJobDefineId(jobDefineId);
            jobParameterPO.setState(JobConstants.JOB_STATE_EFFECT);
        }
        jobParameterRepository.saveAll(jobParameterPOS);
        return saveJobDefine.getJobDefineId();
    }

    public void deleteJob(JobDefinePO jobDefinePO){
        JobDefinePO jobDefine = jobDefineRepository.findByJobDefineIdAndState(jobDefinePO.getJobDefineId(),JobConstants.JOB_STATE_EFFECT);
        if (null != jobDefinePO.getOperatorId()){
            jobDefine.setOperatorId(jobDefinePO.getOperatorId());
        }
        if (StringUtils.isNotBlank(jobDefinePO.getOperatorName())){
            jobDefine.setOperatorName(jobDefinePO.getOperatorName());
        }
        jobDefine.setDoneDate(jobDefinePO.getDoneDate());
        jobDefine.setState(JobConstants.JOB_STATE_EXPIRE);
        jobDefineRepository.save(jobDefine);
        List<JobParameterPO> jobParameterPOS = jobParameterRepository.findByJobDefineIdAndState(jobDefinePO.getJobDefineId(),JobConstants.JOB_STATE_EFFECT);
        if (CollectionUtils.isNotEmpty(jobParameterPOS)){
            for (JobParameterPO jobParameterPO : jobParameterPOS){
                jobParameterPO.setState(JobConstants.JOB_STATE_EXPIRE);
            }
            jobParameterRepository.saveAll(jobParameterPOS);
        }
    }

}
