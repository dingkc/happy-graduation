package com.bttc.HappyGraduation.business.ftp.pojo.vo;

import java.util.List;

/**
 * @author Dk
 * @date 12:13 2019/3/27.
 */
public class RecycleBinListVO {

    private List<RecycleBinVO> rows;
    private Integer total;

    public List<RecycleBinVO> getRows() {
        return rows;
    }

    public void setRows(List<RecycleBinVO> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
