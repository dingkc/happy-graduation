package com.bttc.HappyGraduation.business.friend.service.interfaces;

import com.bttc.HappyGraduation.business.friend.pojo.vo.RequestApproveHistoryListVO;
import com.bttc.HappyGraduation.business.friend.pojo.vo.RequestApproveHistoryVO;
import com.bttc.HappyGraduation.utils.exception.BusinessException;

/**
 * @Author: Dk
 * @Date: 2019/4/22 17:50
 **/
public interface IRequestApproveHistorySV {

    /**
     * <p>Title: addApproveHistory</p>
     * <p>Description: 新增好友申请记录</p>
     * @Author: Dk
     * @param requestApproveHistoryVO : 好友申请记录信息
     * @return: void
     * @Date: 2019/4/22 21:16
     **/
    void addApproveHistory(RequestApproveHistoryVO requestApproveHistoryVO) throws BusinessException;

    /**
     * <p>Title: deleteApproveHistory</p>
     * <p>Description: 删除好友申请记录</p>
     * @Author: Dk
     * @param requestApproveHistoryId : 好友申请记录主键
     * @return: void
     * @Date: 2019/4/22 21:16
     **/
    void deleteApproveHistory(Integer requestApproveHistoryId);

    /**
     * <p>Title: updateApproveHistory</p>
     * <p>Description: 更新好友申请记录状态</p>
     * @Author: Dk
     * @param requestApproveHistoryId : 好友申请记录主键
     * @param requestApproveHistoryVO : 好友申请记录信息
     * @return: void
     * @Date: 2019/4/22 21:16
     **/
    void updateApproveHistory(Integer requestApproveHistoryId, RequestApproveHistoryVO requestApproveHistoryVO) throws BusinessException;

    /**
     * <p>Title: queryApproveHistory</p>
     * <p>Description: 查询好友申请记录信息详情</p>
     * @Author: Dk
     * @param requestApproveHistoryId : 好友申请记录主键
     * @return: com.bttc.HappyGraduation.business.friend.pojo.vo.RequestApproveHistoryVO
     * @Date: 2019/4/22 21:17
     **/
    RequestApproveHistoryVO queryApproveHistory(Integer requestApproveHistoryId);

    /**
     * <p>Title: queryApproveHistories</p>
     * <p>Description: 查询好友申请记录</p>
     * @Author: Dk
     * @param targetId : 被添加人编号
     * @param pageNumber : 分页页码
     * @param pageSize : 分页大小
     * @return: com.bttc.HappyGraduation.business.friend.pojo.vo.RequestApproveHistoryListVO
     * @Date: 2019/4/22 21:34
     **/
    RequestApproveHistoryListVO queryApproveHistories(Integer targetId, Integer pageNumber, Integer pageSize);
}
