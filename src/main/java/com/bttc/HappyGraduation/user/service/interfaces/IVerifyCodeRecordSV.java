package com.bttc.HappyGraduation.user.service.interfaces;


import com.bttc.HappyGraduation.user.pojo.po.VerifyCodeRecordPO;
import com.bttc.HappyGraduation.user.pojo.vo.VerifyCodeRecordVO;

/**
 * Created by yuqh3 on 2018/7/5.
 */
public interface IVerifyCodeRecordSV {

    Integer sendVerifyCodeByEmail(VerifyCodeRecordVO verifyCodeRecordVO);

    VerifyCodeRecordPO queryByVerifyCodeIdAndEmail(Integer verifyCodeId, String email);

    void validateEmailByVerifyCode(Integer verifyCodeId, String verifyCode, String email);

}
