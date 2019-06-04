package com.bttc.HappyGraduation.scheduler.transfer.service.interfaces;

import com.bttc.HappyGraduation.scheduler.transfer.pojo.DataTransferDefinePO;

import java.util.List;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author jiajt
 * @date 16:21 2019/3/19.
 */
public interface IDataTransferDefineSV {
    public List<DataTransferDefinePO> queryAllbyStatusEffect();
}
