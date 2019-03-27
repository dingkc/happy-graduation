package com.bttc.HappyGraduation.business.ftp.service.interfaces;

import com.bttc.HappyGraduation.business.ftp.pojo.vo.RecycleBinListVO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.RecycleBinVO;
import com.bttc.HappyGraduation.utils.exception.BusinessException;

/**
 * @Author: Dk
 * @Date: 2019/3/25 23:08
 **/
public interface IRecycleBinSV {

    void addRecord(RecycleBinVO recycleBinVO) throws BusinessException;

    void deleteRecord(Integer recycleBinId);

    void updateRecord(RecycleBinVO recycleBinVO);

    RecycleBinVO queryById(Integer recycleBinId);

    RecycleBinListVO queryByCondition(String recycleBinName, Integer pageNumber, Integer pageSize);
}
