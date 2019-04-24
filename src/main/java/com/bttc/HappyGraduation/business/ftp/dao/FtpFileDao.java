package com.bttc.HappyGraduation.business.ftp.dao;

import com.bttc.HappyGraduation.business.ftp.pojo.po.FtpFilePO;
import com.bttc.HappyGraduation.utils.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @Author: Dk
 * @Date: 2019/3/25 22:59
 **/
public interface FtpFileDao extends BaseRepository<FtpFilePO, Integer> {

    /**
     * <p>Title: queryAllByFtpFileIdAndState</p>
     * <p>Description: 根据文件id查询文件详情</p>
     * @Author: Dk
     * @param ftpFileId : 文件id
     * @param state : 数据状态
     * @return: com.bttc.HappyGraduation.business.ftp.pojo.po.FtpFilePO
     * @Date: 2019/3/28 22:10
     **/
    FtpFilePO queryAllByFtpFileIdAndState(Integer ftpFileId, Integer state);

    List<FtpFilePO> queryAllByStateAndFilePreviewIsNull(Integer state);

    List<FtpFilePO> queryAllByStateAndParentFileIdAndCreatorId(Integer state, Integer parentFileId, Integer userId);

    FtpFilePO queryByFtpFileId(Integer ftpFileId);
}
