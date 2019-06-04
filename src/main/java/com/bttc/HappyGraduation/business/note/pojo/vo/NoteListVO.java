package com.bttc.HappyGraduation.business.note.pojo.vo;

import java.util.List;

/**
 * @author Dk
 * @date 22:38 2019/3/26.
 */
public class NoteListVO {

    private List<NoteVO> rows;
    private Integer total;

    public List<NoteVO> getRows() {
        return rows;
    }

    public void setRows(List<NoteVO> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
