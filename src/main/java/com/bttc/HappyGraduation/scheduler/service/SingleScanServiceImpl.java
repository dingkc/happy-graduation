package com.bttc.HappyGraduation.scheduler.service;


import com.bttc.HappyGraduation.scheduler.JobConstants;
import com.bttc.HappyGraduation.scheduler.dao.JobDefineRepository;
import com.bttc.HappyGraduation.scheduler.dao.JobParameterRepository;
import com.bttc.HappyGraduation.scheduler.pojo.JobDefinePO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * Created by jiajt on 2018/11/1.
 */
@Service
@Transactional
public class SingleScanServiceImpl extends AbstractScanService {


    @Autowired
    private JobDefineRepository jobDefineRepository;

    @Autowired
    private JobParameterRepository jobParameterRepository;

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleScanServiceImpl.class);

    @Override
    public List<JobDefinePO> getjobData() throws SchedulerException {
        List<String> jobGroup = super.getScanJobGroup();
        List<String> jobName = super.getScanJobName();
        Specification querySpecifi = (Specification<JobDefinePO>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("jobDefineType"), JobConstants.JOB_TYPE_SINGLE));
            if(CollectionUtils.isNotEmpty(jobGroup)){
                CriteriaBuilder.In in = criteriaBuilder.in(root.get("jobDefineGroup"));
                for(String s : jobGroup){
                    in.value(s);
                }
                predicates.add(in);
            }
            if(CollectionUtils.isNotEmpty(jobName)){
                CriteriaBuilder.In in = criteriaBuilder.in(root.get("jobDefineName"));
                for(String s : jobName){
                    in.value(s);
                }
                predicates.add(in);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        List<JobDefinePO> jobDefinePOS = jobDefineRepository.findAll(querySpecifi);
        return super.compareJobs(jobDefinePOS);
    }

    @Override
    public Trigger formateTrigger(JobDefinePO jobDefinePO) {
        if(StringUtils.isNotBlank(jobDefinePO.getCronExpression())){
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobDefinePO.getCronExpression());
            return TriggerBuilder.newTrigger() //定义name/group
                    .withIdentity(jobDefinePO.getJobDefineName() + jobDefinePO.getJobDefineId() + JobConstants.JOB_TRIGGER, jobDefinePO.getJobDefineGroup() + JobConstants.JOB_TRIGGER)
                    .withSchedule(scheduleBuilder)//使用SimpleTrigger
                    .build();
        }
        return TriggerBuilder.newTrigger() //定义name/group
                .startNow()//一旦加入scheduler，立即生效
                .withSchedule(simpleSchedule().withRepeatCount(0))//使用SimpleTrigger
                .build();
    }

    @Override
    public void afterExcuted(JobExecutionContext context, String message, long constTimeMS, boolean isSucess) {
        LOGGER.info("单次进程执行完成，处理进程状态和日志记录");
        super.afterExcuted(context,message,constTimeMS,isSucess);
        //单次进程执行完成需要删除当前表
        String jobDefineId = (String) context.getJobDetail().getJobDataMap().get(JobConstants.COL_JOB_DEFINE_ID);
        jobDefineRepository.deleteById(Long.valueOf(jobDefineId));
        jobParameterRepository.deleteByJobDefineId(Long.valueOf(jobDefineId));
    }
}
