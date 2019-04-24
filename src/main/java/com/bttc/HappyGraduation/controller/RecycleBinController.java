package com.bttc.HappyGraduation.controller;

import com.bttc.HappyGraduation.business.ftp.pojo.vo.RecycleBinVO;
import com.bttc.HappyGraduation.business.ftp.service.interfaces.IRecycleBinSV;
import com.bttc.HappyGraduation.common.ResultBean;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dk
 * @date 18:47 2019/3/27.
 */
@RestController
public class RecycleBinController {

    @Autowired
    private IRecycleBinSV iRecycleBinSV;

    @PostMapping(value = "${apiVersion1}/recycle-bins")
    public ResultBean addRecord(@RequestBody RecycleBinVO recycleBinVO) throws BusinessException {
        iRecycleBinSV.addRecord(recycleBinVO);
        return ResultBean.ok(null);
    }

    @DeleteMapping(value = "${apiVersion1}/recycle-bins/{recycleBinId}")
    public ResultBean deleteRecord(@PathVariable Integer recycleBinId) throws BusinessException {
        iRecycleBinSV.deleteRecord(recycleBinId);
        return ResultBean.ok(null);
    }

    @PutMapping(value = "${apiVersion1}/recycle-bins")
    public ResultBean updateRecord(@RequestBody RecycleBinVO recycleBinVO) {
        iRecycleBinSV.updateRecord(recycleBinVO);
        return ResultBean.ok(null);
    }

    @GetMapping(value = "${apiVersion1}/recycle-bins/{recycleBinId}")
    public ResultBean queryRecycleBin(@PathVariable Integer recycleBinId) {
        return ResultBean.ok(iRecycleBinSV.queryById(recycleBinId));
    }

    @GetMapping(value = "${apiVersion1}/recycle-bins")
    public ResultBean queryRecycleBins(@RequestParam(required = false) String recycleBinName, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return ResultBean.ok(iRecycleBinSV.queryByCondition(recycleBinName, pageNumber, pageSize));
    }

    @PutMapping(value = "${apiVersion1}/recycle-bins/{recycleBinId}/returns")
    public ResultBean returnFile(@PathVariable Integer recycleBinId) throws BusinessException {
        iRecycleBinSV.returnFile(recycleBinId);
        return ResultBean.ok(null);
    }

}
