package com.bttc.HappyGraduation.business.ftp.service.impl;

import com.bttc.HappyGraduation.business.ftp.dao.RecycleBinDao;
import com.bttc.HappyGraduation.business.ftp.pojo.po.RecycleBinPO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.RecycleBinListVO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.RecycleBinVO;
import com.bttc.HappyGraduation.business.ftp.service.interfaces.IRecycleBinSV;
import com.bttc.HappyGraduation.common.BeanMapperUtil;
import com.bttc.HappyGraduation.common.DateUtil;
import com.bttc.HappyGraduation.session.web.SessionManager;
import com.bttc.HappyGraduation.utils.base.Filter;
import com.bttc.HappyGraduation.utils.base.QueryParams;
import com.bttc.HappyGraduation.utils.constant.CommonConstant;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author Dk
 * @date 23:11 2019/3/25.
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class RecycleBinSVImpl implements IRecycleBinSV {

    @Autowired
    private RecycleBinDao recycleBinDao;

    @Override
    public void addRrecord(RecycleBinVO recycleBinVO) throws BusinessException {
        RecycleBinPO recycleBinPO = BeanMapperUtil.map(recycleBinVO, RecycleBinPO.class);
        Date nowDate = DateUtil.getNowDate();
        Integer userId = SessionManager.getUserInfo().getUserId();
        recycleBinPO.setDoneDate(nowDate);
        recycleBinPO.setOperatorId(userId);
        recycleBinDao.save(recycleBinPO);
    }

    @Override
    public void deleteRrecord(Integer recycleBinId) {
        RecycleBinPO recycleBinPO = new RecycleBinPO();
        recycleBinPO.setState(CommonConstant.CommonState.INVALID.getValue());
        recycleBinPO.setRecycleBinId(recycleBinId);
        recycleBinDao.save(recycleBinPO);
    }

    @Override
    public void updateRrecord(RecycleBinVO recycleBinVO) {
        RecycleBinPO recycleBinPO = BeanMapperUtil.map(recycleBinVO, RecycleBinPO.class);
        recycleBinPO.setState(CommonConstant.CommonState.INVALID.getValue());
        recycleBinDao.save(recycleBinPO);
    }

    @Override
    public RecycleBinVO queryById(Integer recycleBinId) {
        RecycleBinPO recycleBinPO = recycleBinDao.queryAllByRecycleBinIdAndState(recycleBinId, CommonConstant.CommonState.EFFECT.getValue());
        return BeanMapperUtil.map(recycleBinPO, RecycleBinVO.class);
    }

    @Override
    public RecycleBinListVO queryByCondition(RecycleBinVO recycleBinVO, Integer pageNumber, Integer pageSize) {
        QueryParams<RecycleBinPO> queryParams = new QueryParams<>(RecycleBinPO.class);
        RecycleBinPO recycleBinPO = BeanMapperUtil.map(recycleBinVO, RecycleBinPO.class);
        recycleBinPO.setState(CommonConstant.CommonState.EFFECT.getValue());
        queryParams.and(Filter.like("fileName",recycleBinVO.getFileName()));
        Page<RecycleBinPO> beans = recycleBinDao.getBeans(recycleBinPO, queryParams, pageNumber - 1, pageSize);
        RecycleBinListVO recycleBinListVO = new RecycleBinListVO();
        recycleBinListVO.setRows(BeanMapperUtil.mapList(beans.getContent(), RecycleBinPO.class, RecycleBinVO.class));
        recycleBinListVO.setTotal((int)beans.getTotalElements());
        return recycleBinListVO;
    }
}
