package com.bttc.HappyGraduation.business.note.pojo.vo;

import java.util.List;

/**
 * @author Dk
 * @date 21:58 2019/3/26.
 */
public class NotepadListVO {

    private List<NotepadVO> rows;
    private Integer total;

    public List<NotepadVO> getRows() {
        return rows;
    }

    public void setRows(List<NotepadVO> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
