package com.bttc.HappyGraduation.business.friend.pojo.vo;

import java.util.List;

/**
 * @author Dk
 * @date 21:07 2019/4/22.
 */
public class FriendRecordListVO {

    private List<FriendRecordVO> rows;
    private Integer total;

    public List<FriendRecordVO> getRows() {
        return rows;
    }

    public void setRows(List<FriendRecordVO> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
