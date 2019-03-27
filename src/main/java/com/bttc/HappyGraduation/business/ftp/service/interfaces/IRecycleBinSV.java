package com.bttc.HappyGraduation.business.ftp.service.interfaces;

import com.bttc.HappyGraduation.business.ftp.pojo.vo.RecycleBinListVO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.RecycleBinVO;
import com.bttc.HappyGraduation.utils.exception.BusinessException;

/**
 * @Author: Dk
 * @Date: 2019/3/25 23:08
 **/
public interface IRecycleBinSV {

    void addRrecord(RecycleBinVO recycleBinVO) throws BusinessException;

    void deleteRrecord(Integer recycleBinId);

    void updateRrecord(RecycleBinVO recycleBinVO);

    RecycleBinVO queryById(Integer recycleBinId);

    RecycleBinListVO queryByCondition(RecycleBinVO recycleBinVO, Integer pageNumber, Integer pageSize);
}
