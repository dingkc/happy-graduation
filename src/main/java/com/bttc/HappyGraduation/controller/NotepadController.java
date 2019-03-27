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

    @PostMapping(value = "${apiVersion1}/notepads")
    public ResultBean addNotepad(@RequestBody NotepadVO notepadVO) throws BusinessException {
        iNotepadSV.addNotepad(notepadVO);
        return ResultBean.ok(null);
    }

    @DeleteMapping(value = "${apiVersion1}/notepads/{notepadId}")
    public ResultBean deleteNotepad(@PathVariable Integer notepadId) throws BusinessException {
        iNotepadSV.deleteNotepad(notepadId);
        return ResultBean.ok(null);
    }

    @PutMapping(value = "${apiVersion1}/notepads")
    public ResultBean updateNotepad(@RequestBody NotepadVO notepadVO) throws BusinessException {
        iNotepadSV.updateNotepad(notepadVO);
        return ResultBean.ok(null);
    }

    @GetMapping(value = "${apiVersion1}/notepads/{notepadId}")
    public ResultBean queryRecycleBin(@PathVariable Integer notepadId) {
        return ResultBean.ok(iNotepadSV.queryNotepadById(notepadId));
    }

    @GetMapping(value = "${apiVersion1}/notepads")
    public ResultBean queryRecycleBin(@RequestParam String notepadName, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return ResultBean.ok(iNotepadSV.queryNotepadByCondition(notepadName, pageNumber, pageSize));
    }
}
