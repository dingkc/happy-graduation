package com.bttc.HappyGraduation.controller;

import com.bttc.HappyGraduation.business.friend.pojo.vo.FriendRecordVO;
import com.bttc.HappyGraduation.business.friend.pojo.vo.RequestApproveHistoryVO;
import com.bttc.HappyGraduation.business.friend.service.interfaces.IFriendRecordSV;
import com.bttc.HappyGraduation.business.friend.service.interfaces.IRequestApproveHistorySV;
import com.bttc.HappyGraduation.common.ResultBean;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dk
 * @date 19:59 2019/4/22.
 */
@RestController
public class FriendController {

    @Autowired
    private IFriendRecordSV iFriendRecordSV;

    @Autowired
    private IRequestApproveHistorySV iRequestApproveHistorySV;

    @PostMapping(value = "${apiVersion1}/friends-approves")
    public ResultBean addApproveHistory(@RequestBody RequestApproveHistoryVO requestApproveHistoryVO) throws BusinessException {
        iRequestApproveHistorySV.addApproveHistory(requestApproveHistoryVO);
        return ResultBean.ok(null);
    }

    @DeleteMapping(value = "${apiVersion1}/friends-approves/{requestApproveHistoryId}")
    public ResultBean deleteApproveHistory(@PathVariable Integer requestApproveHistoryId){
        iRequestApproveHistorySV.deleteApproveHistory(requestApproveHistoryId);
        return ResultBean.ok(null);
    }

    @PutMapping(value = "${apiVersion1}/friends-approves/{requestApproveHistoryId}")
    public ResultBean updateApproveHistory(@PathVariable Integer requestApproveHistoryId, @RequestBody RequestApproveHistoryVO requestApproveHistoryVO) throws BusinessException {
        iRequestApproveHistorySV.updateApproveHistory(requestApproveHistoryId, requestApproveHistoryVO);
        return ResultBean.ok(null);
    }

    @GetMapping(value = "${apiVersion1}/friends-approves/{requestApproveHistoryId}")
    public ResultBean queryApproveHistory(@PathVariable Integer requestApproveHistoryId) {
        return ResultBean.ok(iRequestApproveHistorySV.queryApproveHistory(requestApproveHistoryId));
    }

    @GetMapping(value = "${apiVersion1}/friends-approves")
    public ResultBean queryApproveHistories(@RequestParam Integer targetId, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return ResultBean.ok(iRequestApproveHistorySV.queryApproveHistories(targetId, pageNumber, pageSize));
    }

    @PostMapping(value = "${apiVersion1}/friends")
    public ResultBean addFriendRecord(@RequestBody FriendRecordVO friendRecordVO) throws BusinessException {
        iFriendRecordSV.addFriendRecord(friendRecordVO);
        return ResultBean.ok(null);
    }

    @DeleteMapping(value = "${apiVersion1}/friends/{friendRecordId}")
    public ResultBean deleteFriendRecord(@PathVariable Integer friendRecordId) {
        iFriendRecordSV.deleteFriendRecord(friendRecordId);
        return ResultBean.ok(null);
    }

    @PutMapping(value = "${apiVersion1}/friends/{friendRecordId}")
    public ResultBean updateFriendRecord(@PathVariable Integer friendRecordId, @RequestBody FriendRecordVO friendRecordVO) throws BusinessException {
        iFriendRecordSV.updateFriendRecord(friendRecordId, friendRecordVO);
        return ResultBean.ok(null);
    }

    @GetMapping(value = "${apiVersion1}/friends/{friendRecordId}")
    public ResultBean queryFriendRecord(@PathVariable Integer friendRecordId) {
        return ResultBean.ok(iFriendRecordSV.queryFriendRecord(friendRecordId));
    }

    @GetMapping(value = "${apiVersion1}/friends")
    public ResultBean queryFriendRecords(@RequestParam Integer ownerId, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return ResultBean.ok(iFriendRecordSV.queryFriendRecords(ownerId, pageNumber, pageSize));
    }
}
