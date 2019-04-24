package com.bttc.HappyGraduation.business.ftp.service.impl;

import com.bttc.HappyGraduation.business.ftp.dao.RecycleBinDao;
import com.bttc.HappyGraduation.business.ftp.pojo.po.FtpFilePO;
import com.bttc.HappyGraduation.business.ftp.pojo.po.RecycleBinPO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.RecycleBinListVO;
import com.bttc.HappyGraduation.business.ftp.pojo.vo.RecycleBinVO;
import com.bttc.HappyGraduation.business.ftp.service.interfaces.IFtpFileSV;
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
import java.util.List;

/**
 * @author Dk
 * @date 23:11 2019/3/25.
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class RecycleBinSVImpl implements IRecycleBinSV {

    @Autowired
    private RecycleBinDao recycleBinDao;

    @Autowired
    private IFtpFileSV iFtpFileSV;

    @Override
    public void addRecord(RecycleBinVO recycleBinVO) throws BusinessException {
        RecycleBinPO recycleBinPO = BeanMapperUtil.map(recycleBinVO, RecycleBinPO.class);
        Date nowDate = DateUtil.getNowDate();
        Integer userId = SessionManager.getUserInfo().getUserId();
        recycleBinPO.setDoneDate(nowDate);
        recycleBinPO.setOperatorId(userId);
        recycleBinDao.save(recycleBinPO);
    }

    @Override
    public RecycleBinPO deleteRecord(Integer recycleBinId) throws BusinessException {
        RecycleBinPO recycleBinPO = BeanMapperUtil.map(queryById(recycleBinId), RecycleBinPO.class);
        recycleBinPO.setState(CommonConstant.CommonState.INVALID.getValue());
        recycleBinPO.setRecycleBinId(recycleBinId);
        recycleBinDao.updateBeans(recycleBinPO);
        return recycleBinPO;
    }

    @Override
    public void updateRecord(RecycleBinVO recycleBinVO) {
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
    public RecycleBinListVO queryByCondition(String recycleBinName, Integer pageNumber, Integer pageSize) {
        QueryParams<RecycleBinPO> queryParams = new QueryParams<>(RecycleBinPO.class);
        queryParams.and(Filter.like("fileName",recycleBinName)).and(Filter.eq("state", CommonConstant.CommonState.EFFECT.getValue()));
        Page<RecycleBinPO> beans = recycleBinDao.getBeans(queryParams, pageNumber - 1, pageSize);
        RecycleBinListVO recycleBinListVO = new RecycleBinListVO();
        recycleBinListVO.setRows(BeanMapperUtil.mapList(beans.getContent(), RecycleBinPO.class, RecycleBinVO.class));
        recycleBinListVO.setTotal((int)beans.getTotalElements());
        return recycleBinListVO;
    }

    @Override
    public void returnFile(Integer recycleBinId) throws BusinessException {
        RecycleBinPO recycleBinPO = deleteRecord(recycleBinId);
        FtpFilePO ftpFilePO = iFtpFileSV.queryFileByFileIdNoState(recycleBinPO.getFtpFileId());
        iFtpFileSV.updateFileToRecycleBin(ftpFilePO);
    }

    @Override
    public void emptinessRecycleBin() {
        RecycleBinPO recycleBinPO = new RecycleBinPO();
        recycleBinPO.setState(CommonConstant.CommonState.EFFECT.getValue());
        List<RecycleBinPO> beans = recycleBinDao.getBeans(recycleBinPO);
        beans.stream().forEach( r -> r.setState(CommonConstant.CommonState.INVALID.getValue()));
        recycleBinDao.batchUpdate(beans);
    }
}
