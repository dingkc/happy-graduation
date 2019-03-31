package com.bttc.HappyGraduation.scheduler.transfer.service.interfaces;

import com.bttc.HappyGraduation.scheduler.transfer.pojo.DataTransferDefinePO;

public interface IDataTransferSV {
    void dataTransfer(DataTransferDefinePO dataTransferDefinePO) throws Exception;
}
