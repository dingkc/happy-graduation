package com.bttc.HappyGraduation.controller;

import com.bttc.HappyGraduation.business.note.pojo.vo.NoteVO;
import com.bttc.HappyGraduation.business.note.service.interfaces.INoteSV;
import com.bttc.HappyGraduation.common.ResultBean;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dk
 * @date 17:59 2019/3/27.
 */
@RestController
public class NoteController {
    
    @Autowired
    private INoteSV iNoteSV;

    /**
     * <p>Title: addNote</p>
     * <p>Description: 新增记事</p>
     * @Author: Dk
     * @param noteVO : 记事信息
     * @return: com.bttc.HappyGraduation.common.ResultBean
     * @Date: 2019/4/21 17:32
     **/
    @PostMapping(value = "${apiVersion1}/notes")
    public ResultBean addNote(@RequestBody NoteVO noteVO) throws BusinessException {
        iNoteSV.addNote(noteVO);
        return ResultBean.ok(null);
    }

    /**
     * <p>Title: deleteNote</p>
     * <p>Description: 删除记事</p>
     * @Author: Dk
     * @param noteId : 记事id
     * @return: com.bttc.HappyGraduation.common.ResultBean
     * @Date: 2019/4/21 17:33
     **/
    @DeleteMapping(value = "${apiVersion1}/notes/{noteId}")
    public ResultBean deleteNote(@PathVariable Integer noteId) throws BusinessException {
        iNoteSV.deleteNote(noteId);
        return ResultBean.ok(null);
    }

    /**
     * <p>Title: updateNote</p>
     * <p>Description: 更新记事信息</p>
     * @Author: Dk
     * @param noteVO : 记事信息
     * @return: com.bttc.HappyGraduation.common.ResultBean
     * @Date: 2019/4/21 17:33
     **/
    @PutMapping(value = "${apiVersion1}/notes")
    public ResultBean updateNote(@RequestBody NoteVO noteVO) throws BusinessException {
        iNoteSV.updateNote(noteVO);
        return ResultBean.ok(null);
    }

    /**
     * <p>Title: queryNoteById</p>
     * <p>Description: 查询记事详情</p>
     * @Author: Dk
     * @param noteId : 记事id
     * @return: com.bttc.HappyGraduation.common.ResultBean
     * @Date: 2019/4/21 17:34
     **/
    @GetMapping(value = "${apiVersion1}/notes/{noteId}")
    public ResultBean queryNoteById(@PathVariable Integer noteId) {
        return ResultBean.ok(iNoteSV.queryNoteById(noteId));
    }

    /**
     * <p>Title: queryNotes</p>
     * <p>Description: 查询记事列表</p>
     * @Author: Dk
     * @param noteName :
     * @param pageNumber :
     * @param pageSize :
     * @return: com.bttc.HappyGraduation.common.ResultBean
     * @Date: 2019/4/21 17:34
     **/
    @GetMapping(value = "${apiVersion1}/notes")
    public ResultBean queryNotes(@RequestParam Integer notepadId, @RequestParam(required = false) String noteName,
                                 @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return ResultBean.ok(iNoteSV.queryNoteByCondition(notepadId, noteName, pageNumber, pageSize));
    }
}
