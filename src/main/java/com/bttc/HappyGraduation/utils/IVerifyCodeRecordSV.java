package com.bttc.HappyGraduation.utils;

import com.bttc.HappyGraduation.utils.exception.BusinessException;

public interface IVerifyCodeRecordSV {

    Integer sendVerifyCodeByEmail(VerifyCodeRecordVO verifyCodeRecordVO) throws BusinessException;

    VerifyCodeRecordPO queryByVerifyCodeIdAndEmail(Integer verifyCodeId, String email) throws BusinessException;

    void validateEmailByVerifyCode(VerifyCodeValidationVO verifyCodeValidationVO) throws BusinessException;

}
