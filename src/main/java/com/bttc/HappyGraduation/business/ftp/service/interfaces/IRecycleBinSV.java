package com.bttc.HappyGraduation.business.ftp.service.interfaces;

import com.bttc.HappyGraduation.business.ftp.pojo.po.RecycleBinPO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.RecycleBinListVO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.RecycleBinVO;
import com.bttc.HappyGraduation.utils.exception.BusinessException;

import java.util.List;

/**
 * @Author: Dk
 * @Date: 2019/3/25 23:08
 **/
public interface IRecycleBinSV {

    void addRecord(RecycleBinVO recycleBinVO) throws BusinessException;

    RecycleBinPO deleteRecord(Integer recycleBinId) throws BusinessException;

    void updateRecord(RecycleBinVO recycleBinVO);

    RecycleBinVO queryById(Integer recycleBinId);

    RecycleBinListVO queryByCondition(String recycleBinName, Integer pageNumber, Integer pageSize);

    void returnFile(Integer recycleBinId) throws BusinessException;

    void emptinessRecycleBin();

    void dealState4Task();
}
