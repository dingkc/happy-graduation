package com.bttc.HappyGraduation.business.note.service.impl;

import com.bttc.HappyGraduation.business.note.dao.NoteDao;
import com.bttc.HappyGraduation.business.note.pojo.po.NotePO;
import com.bttc.HappyGraduation.business.note.pojo.vo.NoteListVO;
import com.bttc.HappyGraduation.business.note.pojo.vo.NoteVO;
import com.bttc.HappyGraduation.business.note.service.interfaces.INoteSV;
import com.bttc.HappyGraduation.common.BeanMapperUtil;
import com.bttc.HappyGraduation.common.DateUtil;
import com.bttc.HappyGraduation.session.web.SessionManager;
import com.bttc.HappyGraduation.utils.base.Filter;
import com.bttc.HappyGraduation.utils.base.QueryParams;
import com.bttc.HappyGraduation.utils.constant.CommonConstant;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import com.bttc.HappyGraduation.utils.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.Optional;

/**
 * @author Dk
 * @date 23:10 2019/3/25.
 */
public class NoteSVImpl implements INoteSV {

    @Autowired
    private NoteDao noteDao;

    @Override
    public void addNote(NoteVO noteVO) throws BusinessException {
        String noteName = Optional.ofNullable(noteVO.getNoteName()).orElseThrow(() -> new BusinessException(ErrorCode.PARAMETER_NULL, "记事名称"));
        Integer notepadId = Optional.ofNullable(noteVO.getNotepadId()).orElseThrow(() -> new BusinessException(ErrorCode.NOTEPAD_ID_EMPTY));
        NotePO notePO = BeanMapperUtil.map(noteVO, NotePO.class);
        Date nowDate = DateUtil.getNowDate();
        Integer userId = SessionManager.getUserInfo().getUserId();
        notePO.setNotepadId(notepadId);
        notePO.setNoteName(noteName.trim());
        notePO.setCreateDate(nowDate);
        notePO.setCreatorId(userId);
        notePO.setDoneDate(nowDate);
        notePO.setOperatorId(userId);
        notePO.setState(CommonConstant.CommonState.EFFECT.getValue());
        noteDao.save(notePO);
    }

    @Override
    public void deleteNote(Integer noteId) throws BusinessException {
        NotePO notepadPO = new NotePO();
        notepadPO.setState(CommonConstant.CommonState.INVALID.getValue());
        notepadPO.setNotepadId(noteId);
        notepadPO.setDoneDate(DateUtil.getNowDate());
        notepadPO.setOperatorId(SessionManager.getUserInfo().getUserId());
        noteDao.save(notepadPO);
    }

    @Override
    public void updateNote(NoteVO noteVO) throws BusinessException {
        NotePO notePO = BeanMapperUtil.map(noteVO, NotePO.class);
        notePO.setDoneDate(DateUtil.getNowDate());
        notePO.setOperatorId(SessionManager.getUserInfo().getUserId());
        noteDao.updateBeans(notePO);
    }

    @Override
    public NoteVO queryNoteById(Integer noteId) {
        NotePO notePO = new NotePO();
        notePO.setState(CommonConstant.CommonState.EFFECT.getValue());
        notePO.setNotepadId(noteId);
        return BeanMapperUtil.map(noteDao.getBeans(notePO), NoteVO.class);
    }

    @Override
    public NoteListVO queryNoteByCondition(String noteName, Integer pageNumber, Integer pageSize) {
        QueryParams<NotePO> queryParams = new QueryParams<>(NotePO.class);
        queryParams.and(Filter.like("noteName", noteName)).and(Filter.eq("state", CommonConstant.CommonState.EFFECT.getValue()));
        Page<NotePO> beans = noteDao.getBeans(queryParams, pageSize, pageNumber - 1);
        NoteListVO notepadListVO = new NoteListVO();
        notepadListVO.setRows(BeanMapperUtil.mapList(beans.getContent(), NotePO.class, NoteVO.class));
        notepadListVO.setTotal((int)beans.getTotalElements());
        return notepadListVO;
    }
}
