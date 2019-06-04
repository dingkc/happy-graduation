package com.bttc.HappyGraduation.business.ftp.pojo.vo;

import java.util.List;

/**
 * @author Dk
 * @date 21:43 2019/3/28.
 */
public class FtpFileListVO {

    private List<FtpFileVO> rows;
    private Integer total;

    public List<FtpFileVO> getRows() {
        return rows;
    }

    public void setRows(List<FtpFileVO> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
