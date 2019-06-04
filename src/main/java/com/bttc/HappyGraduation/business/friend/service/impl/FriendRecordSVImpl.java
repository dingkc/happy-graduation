package com.bttc.HappyGraduation.business.friend.service.impl;

import com.bttc.HappyGraduation.business.friend.dao.FriendRecordDao;
import com.bttc.HappyGraduation.business.friend.pojo.po.FriendRecordPO;
import com.bttc.HappyGraduation.business.friend.pojo.vo.FriendRecordListVO;
import com.bttc.HappyGraduation.business.friend.pojo.vo.FriendRecordVO;
import com.bttc.HappyGraduation.business.friend.service.interfaces.IFriendRecordSV;
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
 * @date 17:51 2019/4/22.
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class FriendRecordSVImpl implements IFriendRecordSV {

    @Autowired
    private FriendRecordDao friendRecordDao;

    @Override
    public void addFriendRecord(FriendRecordVO friendRecordVO) throws BusinessException {
        Integer userId = SessionManager.getUserInfo().getUserId();
        Date nowDate = DateUtil.getNowDate();
        friendRecordVO.setCreatorId(userId);
        friendRecordVO.setCreateDate(nowDate);
        friendRecordVO.setOpteratorId(userId);
        friendRecordVO.setDoneDate(nowDate);
        friendRecordVO.setState(CommonConstant.CommonState.EFFECT.getValue());
        friendRecordDao.save(BeanMapperUtil.map(friendRecordVO, FriendRecordPO.class));
    }

    @Override
    public void deleteFriendRecord(Integer friendRecordId) {
        FriendRecordVO friendRecordVO = queryFriendRecord(friendRecordId);
        friendRecordVO.setState(CommonConstant.CommonState.INVALID.getValue());
        friendRecordDao.save(BeanMapperUtil.map(friendRecordVO, FriendRecordPO.class));
    }

    @Override
    public void updateFriendRecord(Integer friendRecordId, FriendRecordVO friendRecordVO) throws BusinessException {
        friendRecordVO.setOpteratorId(SessionManager.getUserInfo().getUserId());
        friendRecordVO.setDoneDate(DateUtil.getNowDate());
        friendRecordDao.save(BeanMapperUtil.map(friendRecordVO, FriendRecordPO.class));
    }

    @Override
    public FriendRecordVO queryFriendRecord(Integer friendRecordId) {
        return BeanMapperUtil.map(friendRecordDao.queryAllByFriendRecordIdAndState(friendRecordId,
                CommonConstant.CommonState.EFFECT.getValue()), FriendRecordVO.class);
    }

    @Override
    public FriendRecordListVO queryFriendRecords(Integer ownerId, Integer pageNumber, Integer pageSize) {
        FriendRecordListVO recordListVO = new FriendRecordListVO();
        List<FriendRecordPO> friendRecordPOS = friendRecordDao.queryAllByOwnerIdAndStateOrderByDoneDateDesc(ownerId, CommonConstant.CommonState.EFFECT.getValue());
        List<FriendRecordVO> friendRecordVOS = BeanMapperUtil.mapList(friendRecordPOS, FriendRecordPO.class, FriendRecordVO.class);
        if (pageNumber != null && pageSize != null) {
            int size = friendRecordPOS.size();
            recordListVO.setTotal(size);
            int end = pageNumber * pageSize;
            int start = end - pageSize;
            if (size <= start) {
                recordListVO.setRows(null);
            } else {
                recordListVO.setRows(friendRecordVOS.subList(start, Math.min(end, size)));
            }
        } else {
            recordListVO.setRows(friendRecordVOS);
        }
        return recordListVO;
    }
}
