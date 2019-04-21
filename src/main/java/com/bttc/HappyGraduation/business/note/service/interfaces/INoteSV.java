package com.bttc.HappyGraduation.business.note.service.interfaces;

import com.bttc.HappyGraduation.business.note.pojo.vo.NoteListVO;
import com.bttc.HappyGraduation.business.note.pojo.vo.NoteVO;
import com.bttc.HappyGraduation.utils.exception.BusinessException;

/**
 * @Author: Dk
 * @Date: 2019/3/25 23:08
 **/
public interface INoteSV {

    /**
     * <p>Title: addNote</p>
     * <p>Description: 新增记事</p>
     * @Author: Dk
     * @param noteVO : 记事信息
     * @return: void
     * @Date: 2019/3/26 22:46
     **/
    void addNote(NoteVO noteVO) throws BusinessException;

    /**
     * <p>Title: deleteNote</p>
     * <p>Description: 删除记事</p>
     * @Author: Dk
     * @param noteId : 记事编号
     * @return: void
     * @Date: 2019/3/26 22:46
     **/
    void deleteNote(Integer noteId) throws BusinessException;

    /**
     * <p>Title: updateNote</p>
     * <p>Description: 更新记事</p>
     * @Author: Dk
     * @param noteVO : 记事信息
     * @return: void
     * @Date: 2019/3/26 22:47
     **/
    void updateNote(NoteVO noteVO) throws BusinessException;

    /**
     * <p>Title: queryNoteById</p>
     * <p>Description: 查询记事详情</p>
     * @Author: Dk
     * @param noteId : 记事编号
     * @return: com.bttc.HappyGraduation.business.note.pojo.vo.NoteVO
     * @Date: 2019/3/26 22:47
     **/
    NoteVO queryNoteById(Integer noteId);

    /**
     * <p>Title: queryNoteByCondition</p>
     * <p>Description: 多条件查询记事</p>
     * @Author: Dk
     * @param noteName : 记事名称
     * @param pageNumber : 分页页码
     * @param pageSize : 分页大小
     * @return: com.bttc.HappyGraduation.business.note.pojo.vo.NoteListVO
     * @Date: 2019/3/26 22:47
     **/
    NoteListVO queryNoteByCondition(Integer notepadId, String noteName, Integer pageNumber, Integer pageSize);
}
