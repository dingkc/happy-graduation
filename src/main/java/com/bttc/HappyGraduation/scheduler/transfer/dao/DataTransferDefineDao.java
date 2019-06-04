package com.bttc.HappyGraduation.scheduler.transfer.dao;

import com.bttc.HappyGraduation.scheduler.transfer.pojo.DataTransferDefinePO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author jiajt
 * @date 16:16 2019/3/19.
 */
public interface DataTransferDefineDao extends JpaRepository<DataTransferDefinePO,Long> {
    List<DataTransferDefinePO> findAllByStatus(Integer status);
}
