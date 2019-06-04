package com.bttc.HappyGraduation.email.service.interfaces;

import com.bttc.HappyGraduation.email.pojo.po.VerifyCodeRecordPO;
import com.bttc.HappyGraduation.email.pojo.vo.VerifyCodeRecordVO;
import com.bttc.HappyGraduation.email.pojo.vo.VerifyCodeValidationVO;
import com.bttc.HappyGraduation.utils.exception.BusinessException;

public interface IVerifyCodeRecordSV {

    Integer sendVerifyCodeByEmail(VerifyCodeRecordVO verifyCodeRecordVO) throws BusinessException;

    VerifyCodeRecordPO queryByVerifyCodeIdAndEmail(Integer verifyCodeId, String email) throws BusinessException;

    void validateEmailByVerifyCode(VerifyCodeValidationVO verifyCodeValidationVO) throws BusinessException;

}
