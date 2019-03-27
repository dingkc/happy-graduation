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

    @PostMapping(value = "${apiVersion1}/notes")
    public ResultBean addNote(@RequestBody NoteVO noteVO) throws BusinessException {
        iNoteSV.addNote(noteVO);
        return ResultBean.ok(null);
    }

    @DeleteMapping(value = "${apiVersion1}/notes/{noteId}")
    public ResultBean deleteNote(@PathVariable Integer noteId) throws BusinessException {
        iNoteSV.deleteNote(noteId);
        return ResultBean.ok(null);
    }

    @PutMapping(value = "${apiVersion1}/notes")
    public ResultBean updateNote(NoteVO noteVO) throws BusinessException {
        iNoteSV.updateNote(noteVO);
        return ResultBean.ok(null);
    }

    @GetMapping(value = "${apiVersion1}/notes/{noteId}")
    public ResultBean queryRecycleBin(@PathVariable Integer noteId) {
        return ResultBean.ok(iNoteSV.queryNoteById(noteId));
    }

    @GetMapping(value = "${apiVersion1}/notes")
    public ResultBean queryRecycleBin(String noteName, Integer pageNumber, Integer pageSize) {
        return ResultBean.ok(iNoteSV.queryNoteByCondition(noteName, pageNumber, pageSize));
    }
}
