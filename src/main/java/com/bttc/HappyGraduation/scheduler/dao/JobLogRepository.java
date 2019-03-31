package com.bttc.HappyGraduation.scheduler.dao;

import com.bttc.HappyGraduation.scheduler.pojo.JobLogPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jiajt on 2018/11/7.
 */
public interface JobLogRepository  extends JpaRepository<JobLogPO,Long> {
}
