package com.bttc.HappyGraduation.business.friend.service.interfaces;

import com.bttc.HappyGraduation.business.friend.pojo.vo.FriendRecordListVO;
import com.bttc.HappyGraduation.business.friend.pojo.vo.FriendRecordVO;
import com.bttc.HappyGraduation.utils.exception.BusinessException;

/**
 * @author: Dk
 * @date: 2019/4/22 17:49
 **/
public interface IFriendRecordSV {

    /**
     * <p>Title: addFriendRecord</p>
     * <p>Description: 新增好友</p>
     * @Author: Dk
     * @param friendRecordVO : 好友记录信息
     * @return: void
     * @Date: 2019/4/22 20:10
     **/
    void addFriendRecord(FriendRecordVO friendRecordVO) throws BusinessException;

    /**
     * <p>Title: deleteFriendRecord</p>
     * <p>Description: 删除好友</p>
     * @Author: Dk
     * @param friendRecordId : 好友记录主键
     * @return: void
     * @Date: 2019/4/22 22:58
     **/
    void deleteFriendRecord(Integer friendRecordId);

    /**
     * <p>Title: updateFriendRecord</p>
     * <p>Description: 更新好友记录</p>
     * @Author: Dk
     * @param friendRecordId : 好友记录主键
     * @param friendRecordVO : 好友记录信息
     * @return: void
     * @Date: 2019/4/22 22:58
     **/
    void updateFriendRecord(Integer friendRecordId, FriendRecordVO friendRecordVO) throws BusinessException;

    /**
     * <p>Title: queryFriendRecord</p>
     * <p>Description: 查询好友记录信息详情</p>
     * @Author: Dk
     * @param friendRecordId : 好友记录主键
     * @return: com.bttc.HappyGraduation.business.friend.pojo.vo.FriendRecordVO
     * @Date: 2019/4/22 22:59
     **/
    FriendRecordVO queryFriendRecord(Integer friendRecordId);

    /**
     * <p>Title: queryFriendRecords</p>
     * <p>Description: 查询好友列表</p>
     * @Author: Dk
     * @param ownerId : 拥有者编号
     * @param pageNumber : 分页参数
     * @param pageSize : 分页大小
     * @return: com.bttc.HappyGraduation.business.friend.pojo.vo.FriendRecordListVO
     * @Date: 2019/4/22 23:00
     **/
    FriendRecordListVO queryFriendRecords(Integer ownerId, Integer pageNumber, Integer pageSize);
}
