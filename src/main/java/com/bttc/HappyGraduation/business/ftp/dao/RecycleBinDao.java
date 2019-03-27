package com.bttc.HappyGraduation.business.ftp.dao;

import com.bttc.HappyGraduation.business.ftp.pojo.po.RecycleBinPO;
import com.bttc.HappyGraduation.utils.base.BaseRepository;

/**
 * @Author: Dk
 * @Date: 2019/3/25 23:03
 **/
public interface RecycleBinDao extends BaseRepository<RecycleBinPO, Integer> {

    /**
     * <p>Title: queryAllByRecycleBinIdAndState</p>
     * <p>Description: 根据回收站记录id查询回收站数据</p>
     * @Author: Dk
     * @param recycleBinId : 记录id
     * @param state : 数据状态
     * @return: com.bttc.HappyGraduation.business.ftp.pojo.po.RecycleBinPO
     * @Date: 2019/3/27 17:04
     **/
    RecycleBinPO queryAllByRecycleBinIdAndState(Integer recycleBinId, Integer state);
}
