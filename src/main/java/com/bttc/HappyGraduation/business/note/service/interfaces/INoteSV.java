package com.bttc.HappyGraduation.business.note.service.interfaces;

import com.bttc.HappyGraduation.business.note.pojo.vo.NoteListVO;
import com.bttc.HappyGraduation.business.note.pojo.vo.NoteVO;
import com.bttc.HappyGraduation.utils.exception.BusinessException;

/**
 * @Author: Dk
 * @Date: 2019/3/25 23:08
 **/
public interface INoteSV {

    void addNote(NoteVO noteVO) throws BusinessException;

    void deleteNote(Integer noteId) throws BusinessException;

    void updateNote(NoteVO noteVO) throws BusinessException;

    NoteVO queryNoteById(Integer noteId);

    NoteListVO queryNoteByCondition(String noteName, Integer pageNumber, Integer pageSize);
}
