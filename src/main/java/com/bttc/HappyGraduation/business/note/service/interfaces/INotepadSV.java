package com.bttc.HappyGraduation.business.note.service.interfaces;

import com.bttc.HappyGraduation.business.note.pojo.vo.NotepadListVO;
import com.bttc.HappyGraduation.business.note.pojo.vo.NotepadVO;
import com.bttc.HappyGraduation.utils.exception.BusinessException;

/**
 * @Author: Dk
 * @Date: 2019/3/25 23:08
 **/
public interface INotepadSV {

    /**
     * <p>Title: addNotepad</p>
     * <p>Description: 新建记事本</p>
     * @Author: Dk
     * @param notepadVO : 记事本信息
     * @return: void
     * @Date: 2019/3/26 21:41
     **/
    void addNotepad(NotepadVO notepadVO) throws BusinessException;

    /**
     * <p>Title: deleteNotepad</p>
     * <p>Description: 删除记事本</p>
     * @Author: Dk
     * @param notepadId : 记事本编号
     * @return: void
     * @Date: 2019/3/26 21:43
     **/
    void deleteNotepad(Integer notepadId) throws BusinessException;

    /**
     * <p>Title: updateNotepad</p>
     * <p>Description: 更新记事本信息</p>
     * @Author: Dk
     * @param notepadVO : 记事本信息
     * @return: void
     * @Date: 2019/3/26 21:47
     **/
    void updateNotepad(NotepadVO notepadVO) throws BusinessException;

    /**
     * <p>Title: queryNotepadById</p>
     * <p>Description: 查询记事本详情</p>
     * @Author: Dk
     * @param notepadId : 记事本id
     * @return: com.bttc.HappyGraduation.business.note.pojo.vo.NotepadVO
     * @Date: 2019/3/26 22:18
     **/
    NotepadVO queryNotepadById(Integer notepadId);

    /**
     * <p>Title: queryNotepadByCondition</p>
     * <p>Description: 多条件查询记事本列表信息</p>
     * @Author: Dk
     * @param notepadName : 记事本名称
     * @param pageNumber : 分页页码
     * @param pageSize : 分页大小
     * @return: com.bttc.HappyGraduation.business.note.pojo.vo.NotepadListVO
     * @Date: 2019/3/26 22:19
     **/
    NotepadListVO queryNotepadByCondition(String notepadName, Integer pageNumber, Integer pageSize);
}
