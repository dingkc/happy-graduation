package com.bttc.HappyGraduation.scheduler.service;


import com.bttc.HappyGraduation.scheduler.JobConstants;
import com.bttc.HappyGraduation.scheduler.dao.JobDefineRepository;
import com.bttc.HappyGraduation.scheduler.pojo.JobDefinePO;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiajt on 2018/11/1.
 */
@Service
@Transactional
public class CircleScanServiceImpl extends AbstractScanService {

    @Autowired
    private JobDefineRepository jobDefineRepository;


    @Override
    public List<JobDefinePO> getjobData() throws SchedulerException {
        List<String> jobGroup = super.getScanJobGroup();
        List<String> jobName = super.getScanJobName();
        Specification querySpecifi = (Specification<JobDefinePO>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("jobDefineType"), JobConstants.JOB_TYPE_CIRCLE));
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
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobDefinePO.getCronExpression());
        return TriggerBuilder.newTrigger() //定义name/group
                            .withIdentity(jobDefinePO.getJobDefineName() + jobDefinePO.getJobDefineId() + JobConstants.JOB_TRIGGER, jobDefinePO.getJobDefineGroup() + JobConstants.JOB_TRIGGER)
                            .withSchedule(scheduleBuilder)//使用SimpleTrigger
                            .build();
    }

}
