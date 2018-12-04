package com.bttc.HappyGraduation.user.dao;

import com.bttc.HappyGraduation.user.pojo.po.VerifyCodeRecordPO;
import com.bttc.HappyGraduation.utils.datebase.BaseRepository;
import org.springframework.data.jpa.repository.Query;

public interface VerifyCodeRecordDao extends BaseRepository<VerifyCodeRecordPO, Integer> {

    @Query(value = "select verify_code_id,mail_account,verify_code,create_date,expire_date\n" +
            " from verify_code_record  where verify_code_id = ?1 and expire_date >= NOW() ", nativeQuery = true)
    VerifyCodeRecordPO queryById(Integer verifyCodeId);

    @Query(value = "select verify_code_id,mail_account,verify_code,create_date,expire_date\n" +
            " from verify_code_record  where verify_code_id = ?1 and mail_account = ?2 and expire_date >= NOW() ", nativeQuery = true)
    VerifyCodeRecordPO findByVerifyCodeIdAndEmail(Integer verifyCodeId, String email);

}
