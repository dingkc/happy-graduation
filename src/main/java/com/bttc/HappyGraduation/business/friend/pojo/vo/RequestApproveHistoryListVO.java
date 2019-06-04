package com.bttc.HappyGraduation.business.friend.pojo.vo;

import java.util.List;

/**
 * @author Dk
 * @date 21:06 2019/4/22.
 */
public class RequestApproveHistoryListVO {

    private List<RequestApproveHistoryVO> rows;
    private Integer total;

    public List<RequestApproveHistoryVO> getRows() {
        return rows;
    }

    public void setRows(List<RequestApproveHistoryVO> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
