package com.bttc.HappyGraduation.business.friend.dao;

import com.bttc.HappyGraduation.business.friend.pojo.po.FriendRecordPO;
import com.bttc.HappyGraduation.utils.base.BaseRepository;

import java.util.List;

/**
 * @Author: Dk
 * @Date: 2019/4/22 17:47
 **/
public interface FriendRecordDao extends BaseRepository<FriendRecordPO, Integer> {

    FriendRecordPO queryAllByFriendRecordIdAndState(Integer friendRecordId, Integer state);

    List<FriendRecordPO> queryAllByOwnerIdAndStateOrderByDoneDateDesc(Integer ownerId, Integer state);
}
