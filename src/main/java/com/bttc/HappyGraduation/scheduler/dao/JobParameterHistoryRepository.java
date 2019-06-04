package com.bttc.HappyGraduation.scheduler.dao;

import com.bttc.HappyGraduation.scheduler.pojo.JobParameterHistoryPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jiajt on 2018/11/7.
 */
public interface JobParameterHistoryRepository extends JpaRepository<JobParameterHistoryPO,Long> {
}
