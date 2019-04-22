package com.bttc.HappyGraduation.business.friend.service.impl;

import com.bttc.HappyGraduation.business.friend.constant.FriendConstant;
import com.bttc.HappyGraduation.business.friend.dao.RequestApproveHistoryDao;
import com.bttc.HappyGraduation.business.friend.pojo.po.RequestApproveHistoryPO;
import com.bttc.HappyGraduation.business.friend.pojo.vo.RequestApproveHistoryListVO;
import com.bttc.HappyGraduation.business.friend.pojo.vo.RequestApproveHistoryVO;
import com.bttc.HappyGraduation.business.friend.service.interfaces.IRequestApproveHistorySV;
import com.bttc.HappyGraduation.common.BeanMapperUtil;
import com.bttc.HappyGraduation.common.DateUtil;
import com.bttc.HappyGraduation.session.web.SessionManager;
import com.bttc.HappyGraduation.utils.constant.CommonConstant;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author Dk
 * @date 17:54 2019/4/22.
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class RequestApproveHistorySVImpl implements IRequestApproveHistorySV {

    @Autowired
    private RequestApproveHistoryDao requestApproveHistoryDao;

    @Override
    public void addApproveHistory(RequestApproveHistoryVO requestApproveHistoryVO) throws BusinessException {
        Integer userId = SessionManager.getUserInfo().getUserId();
        Date nowDate = DateUtil.getNowDate();
        requestApproveHistoryVO.setCreatorId(userId);
        requestApproveHistoryVO.setCreateDate(nowDate);
        requestApproveHistoryVO.setOperatorId(userId);
        requestApproveHistoryVO.setDoneDate(nowDate);
        requestApproveHistoryVO.setStatus(FriendConstant.ApproveType.NO_PROCESS.getValue());
        requestApproveHistoryVO.setState(CommonConstant.CommonState.EFFECT.getValue());
        requestApproveHistoryDao.save(BeanMapperUtil.map(requestApproveHistoryVO, RequestApproveHistoryPO.class));
    }

    @Override
    public void deleteApproveHistory(Integer requestApproveHistoryId) {
        RequestApproveHistoryVO requestApproveHistoryVO = queryApproveHistory(requestApproveHistoryId);
        requestApproveHistoryVO.setState(CommonConstant.CommonState.INVALID.getValue());
        requestApproveHistoryDao.save(BeanMapperUtil.map(requestApproveHistoryVO, RequestApproveHistoryPO.class));
    }

    @Override
    public void updateApproveHistory(Integer requestApproveHistoryId, RequestApproveHistoryVO requestApproveHistoryVO) throws BusinessException {
        requestApproveHistoryVO.setDoneDate(DateUtil.getNowDate());
        requestApproveHistoryVO.setOperatorId(SessionManager.getUserInfo().getUserId());
        requestApproveHistoryDao.save(BeanMapperUtil.map(requestApproveHistoryVO, RequestApproveHistoryPO.class));
    }

    @Override
    public RequestApproveHistoryVO queryApproveHistory(Integer requestApproveHistoryId) {
        return BeanMapperUtil.map(requestApproveHistoryDao.queryAllByRequestApproveHistoryIdAndState(requestApproveHistoryId,
                CommonConstant.CommonState.EFFECT.getValue()), RequestApproveHistoryVO.class);
    }

    @Override
    public RequestApproveHistoryListVO queryApproveHistories(Integer targetId, Integer pageNumber, Integer pageSize) {
        List<RequestApproveHistoryPO> requestApproveHistoryPOS = requestApproveHistoryDao.queryAllByTargetIdAndStateOrderByCreateDateDesc(targetId, CommonConstant.CommonState.EFFECT.getValue());
        List<RequestApproveHistoryVO> requestApproveHistoryVOS = BeanMapperUtil.mapList(requestApproveHistoryPOS, RequestApproveHistoryPO.class, RequestApproveHistoryVO.class);
        RequestApproveHistoryListVO requestApproveHistoryListVO = new RequestApproveHistoryListVO();
        if (pageNumber != null && pageSize != null) {
            int size = requestApproveHistoryPOS.size();
            requestApproveHistoryListVO.setTotal(size);
            int end = pageNumber * pageSize;
            int start = end - pageSize;
            if (size <= start) {
                requestApproveHistoryListVO.setRows(null);
            } else {
                requestApproveHistoryListVO.setRows(requestApproveHistoryVOS.subList(start, Math.min(end, size)));
            }
        } else {
            requestApproveHistoryListVO.setRows(requestApproveHistoryVOS);
        }
        return requestApproveHistoryListVO;
    }
}
