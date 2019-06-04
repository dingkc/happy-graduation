package com.bttc.HappyGraduation.business.note.service.impl;

import com.bttc.HappyGraduation.business.note.dao.NotepadDao;
import com.bttc.HappyGraduation.business.note.pojo.po.NotepadPO;
import com.bttc.HappyGraduation.business.note.pojo.vo.NotepadListVO;
import com.bttc.HappyGraduation.business.note.pojo.vo.NotepadVO;
import com.bttc.HappyGraduation.business.note.service.interfaces.INotepadSV;
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
 * @date 23:09 2019/3/25.
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class NotepadSVImpl implements INotepadSV {

    @Autowired
    private NotepadDao notepadDao;

    @Override
    public void addNotepad(NotepadVO notepadVO) throws BusinessException {
        NotepadPO notepadPO = BeanMapperUtil.map(notepadVO, NotepadPO.class);
        Date nowDate = DateUtil.getNowDate();
        Integer userId = SessionManager.getUserInfo().getUserId();
        notepadPO.setCreateDate(nowDate);
        notepadPO.setCreatorId(userId);
        notepadPO.setDoneDate(nowDate);
        notepadPO.setOperatorId(userId);
        notepadPO.setState(CommonConstant.CommonState.EFFECT.getValue());
        notepadDao.save(notepadPO);
    }

    @Override
    public void deleteNotepad(Integer notepadId) throws BusinessException {
        NotepadPO notepadPO = new NotepadPO();
        notepadPO.setState(CommonConstant.CommonState.INVALID.getValue());
        notepadPO.setNotepadId(notepadId);
        notepadPO.setDoneDate(DateUtil.getNowDate());
        notepadPO.setOperatorId(SessionManager.getUserInfo().getUserId());
        notepadDao.save(notepadPO);
    }

    @Override
    public void updateNotepad(NotepadVO notepadVO) throws BusinessException {
        NotepadPO notepadPO = BeanMapperUtil.map(notepadVO, NotepadPO.class);
        notepadPO.setDoneDate(DateUtil.getNowDate());
        notepadPO.setOperatorId(SessionManager.getUserInfo().getUserId());
        notepadDao.updateBeans(notepadPO);
    }

    @Override
    public NotepadVO queryNotepadById(Integer notepadId) {
        NotepadPO notepadPO = new NotepadPO();
        notepadPO.setState(CommonConstant.CommonState.EFFECT.getValue());
        notepadPO.setNotepadId(notepadId);
        return BeanMapperUtil.map(notepadDao.getBeans(notepadPO), NotepadVO.class);
    }

    @Override
    public NotepadListVO queryNotepadByCondition(String notepadName, Integer pageNumber, Integer pageSize) {
        QueryParams<NotepadPO> queryParams = new QueryParams<>(NotepadPO.class);
        queryParams.and(Filter.like("notepadName", notepadName)).and(Filter.eq("state", CommonConstant.CommonState.EFFECT.getValue()));
        Page<NotepadPO> beans = notepadDao.getBeans(queryParams, pageSize, pageNumber - 1);
        NotepadListVO notepadListVO = new NotepadListVO();
        notepadListVO.setRows(BeanMapperUtil.mapList(beans.getContent(), NotepadPO.class, NotepadVO.class));
        notepadListVO.setTotal((int)beans.getTotalElements());
        return notepadListVO;
    }
}
