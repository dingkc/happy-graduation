package com.bttc.HappyGraduation.scheduler.dao;

import com.bttc.HappyGraduation.scheduler.pojo.JobParameterPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by jiajt on 2018/11/7.
 */
public interface JobParameterRepository extends JpaRepository<JobParameterPO, Long> {
    List<JobParameterPO> findByJobDefineIdAndState(Long jobDefineId, Integer state);
    List<JobParameterPO> findByJobDefineId(Long jobDefineId);
    void deleteByJobDefineId(Long jobDefineId);
}
