package com.bttc.HappyGraduation.scheduler.dao;

import com.bttc.HappyGraduation.scheduler.pojo.JobDefinePO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by jiajt on 2018/11/7.
 */
public interface JobDefineRepository extends CrudRepository<JobDefinePO, Long>, JpaSpecificationExecutor {
    List<JobDefinePO> findByJobDefineType(String defineType);
    JobDefinePO findByJobDefineIdAndState(Long jobDefineId, Integer state);
}
