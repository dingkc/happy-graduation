package com.bttc.HappyGraduation.controller;

import com.bttc.HappyGraduation.business.note.pojo.vo.NotepadVO;
import com.bttc.HappyGraduation.business.note.service.interfaces.INotepadSV;
import com.bttc.HappyGraduation.common.ResultBean;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dk
 * @date 17:59 2019/3/27.
 */
@RestController
public class NotepadController {

    @Autowired
    private INotepadSV iNotepadSV;

    /**
     * <p>Title: addNotepad</p>
     * <p>Description: 新增记事本</p>
     * @Author: Dk
     * @param notepadVO : 记事本信息
     * @return: com.bttc.HappyGraduation.common.ResultBean
     * @Date: 2019/4/21 17:29
     **/
    @PostMapping(value = "${apiVersion1}/notepads")
    public ResultBean addNotepad(@RequestBody NotepadVO notepadVO) throws BusinessException {
        iNotepadSV.addNotepad(notepadVO);
        return ResultBean.ok(null);
    }

    /**
     * <p>Title: deleteNotepad</p>
     * <p>Description: 删除记事本</p>
     * @Author: Dk
     * @param notepadId : 记事本id
     * @return: com.bttc.HappyGraduation.common.ResultBean
     * @Date: 2019/4/21 17:29
     **/
    @DeleteMapping(value = "${apiVersion1}/notepads/{notepadId}")
    public ResultBean deleteNotepad(@PathVariable Integer notepadId) throws BusinessException {
        iNotepadSV.deleteNotepad(notepadId);
        return ResultBean.ok(null);
    }

    /**
     * <p>Title:updateNotepad </p>
     * <p>Description: 更新记事本</p>
     * @Author: Dk
     * @param notepadVO : 记事本信息
     * @return: com.bttc.HappyGraduation.common.ResultBean
     * @Date: 2019/4/21 17:29
     **/
    @PutMapping(value = "${apiVersion1}/notepads")
    public ResultBean updateNotepad(@RequestBody NotepadVO notepadVO) throws BusinessException {
        iNotepadSV.updateNotepad(notepadVO);
        return ResultBean.ok(null);
    }

    /**
     * <p>Title:queryNotepadById </p>
     * <p>Description: 查询记事本详情</p>
     * @Author: Dk
     * @param notepadId : 记事本id
     * @return: com.bttc.HappyGraduation.common.ResultBean
     * @Date: 2019/4/21 17:30
     **/
    @GetMapping(value = "${apiVersion1}/notepads/{notepadId}")
    public ResultBean queryNotepadById(@PathVariable Integer notepadId) {
        return ResultBean.ok(iNotepadSV.queryNotepadById(notepadId));
    }

    /**
     * <p>Title: queryNotepads</p>
     * <p>Description: 查询记事本列表</p>
     * @Author: Dk
     * @param notepadName :记事本名称
     * @param pageNumber :分页参数
     * @param pageSize :分页大小
     * @return: com.bttc.HappyGraduation.common.ResultBean
     * @Date: 2019/4/21 17:30
     **/
    @GetMapping(value = "${apiVersion1}/notepads")
    public ResultBean queryNotepads(@RequestParam String notepadName, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return ResultBean.ok(iNotepadSV.queryNotepadByCondition(notepadName, pageNumber, pageSize));
    }
}
