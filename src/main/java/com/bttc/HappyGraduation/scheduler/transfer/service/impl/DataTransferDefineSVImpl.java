package com.bttc.HappyGraduation.scheduler.transfer.service.impl;

import com.bttc.HappyGraduation.scheduler.transfer.dao.DataTransferDefineDao;
import com.bttc.HappyGraduation.scheduler.transfer.pojo.DataTransferDefinePO;
import com.bttc.HappyGraduation.scheduler.transfer.service.interfaces.IDataTransferDefineSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author jiajt
 * @date 16:21 2019/3/19.
 */
@Service
public class DataTransferDefineSVImpl implements IDataTransferDefineSV {

    @Autowired
    private DataTransferDefineDao dataTransferDefineDao;

    public List<DataTransferDefinePO> queryAllbyStatusEffect(){
        return dataTransferDefineDao.findAllByStatus(1);
    }
}
