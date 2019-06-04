package com.bttc.HappyGraduation.business.friend.dao;

import com.bttc.HappyGraduation.business.friend.pojo.po.RequestApproveHistoryPO;
import com.bttc.HappyGraduation.utils.base.BaseRepository;

import java.util.List;

/**
 * @Author: Dk
 * @Date: 2019/4/22 17:48
 **/
public interface RequestApproveHistoryDao extends BaseRepository<RequestApproveHistoryPO, Integer>{

    RequestApproveHistoryPO queryAllByRequestApproveHistoryIdAndState(Integer requestApproveHistoryId, Integer state);

    List<RequestApproveHistoryPO> queryAllByTargetIdAndStateOrderByCreateDateDesc(Integer targetId, Integer state);
}
