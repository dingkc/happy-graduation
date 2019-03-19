package com.bttc.HappyGraduation.utils;

import com.bttc.HappyGraduation.session.pojo.po.UserPO;
import com.bttc.HappyGraduation.session.service.interfaces.IUserSV;
import com.bttc.HappyGraduation.utils.constant.UserConstant;
import com.bttc.HappyGraduation.utils.email.component.MailConnection;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import com.bttc.HappyGraduation.utils.exception.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * Created by yuqh3 on 2018/7/5.
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class VerifyCodeRecordSVImpl implements IVerifyCodeRecordSV {

    @Autowired
    VerifyCodeRecordDao verifyCodeRecordDao;

    @Autowired
    private MailConnection mailConnection;

    @Autowired
    private IUserSV iUserSV;

    @Override
    public Integer sendVerifyCodeByEmail(VerifyCodeRecordVO verifyCodeRecordVO) throws BusinessException {
        Optional.ofNullable(verifyCodeRecordVO.getVerifyCodeType()).orElseThrow(() -> new BusinessException(ErrorCode.EMAIL_FAIL));
        UserPO userPO = iUserSV.queryByUsername(verifyCodeRecordVO.getUsername());
        if (!UserConstant.CodeSendType.REGISTER.getValue().equals(verifyCodeRecordVO.getVerifyCodeType())) {
            //判断用户是否不存在
            Optional.ofNullable(userPO).orElseThrow(() -> new BusinessException(ErrorCode.EMAIL_FAIL));
            verifyCodeRecordVO.setMailAccount(userPO.getEmail());
        }
        String verifyCode = VerifyCodeUtils.generateVerifyCode(5);
        VerifyCodeRecordPO verifyCodeRecordPO = BeanMapperUtil.map(verifyCodeRecordVO, VerifyCodeRecordPO.class);
        verifyCodeRecordPO.setVerifyCode(verifyCode);
        //获取当前时间
        Date nowDate = DateUtil.getNowDate();
        Date expireDate = DateUtil.getDateInHarfAnHour();
        verifyCodeRecordPO.setCreateDate(nowDate);
        verifyCodeRecordPO.setExpireDate(expireDate);
        VerifyCodeRecordPO returnPO = verifyCodeRecordDao.save(verifyCodeRecordPO);
        try {
            StringBuilder sb = new StringBuilder();
            String validTime = ",30分钟内有效！";
            String content = null;
            sb.append("【").append("蜂云").append("】");
            switch (verifyCodeRecordVO.getVerifyCodeType()) {
                case 1:
                    content = sb.append("创建账号的验证码是").append(verifyCode).append(validTime).toString();
                    break;
                case 2:
                    content = sb.append("重置密码的验证码是").append(verifyCode).append(validTime).toString();
                    break;
                case 3:
                    content = sb.append("绑定邮箱的验证码是").append(verifyCode).append(validTime).toString();
                    break;
                default :
                    break;
            }
            mailConnection.sendMail(verifyCodeRecordVO.getMailAccount(), "", "蜂云", content,  null);
        } catch (Exception e) {
        	BusinessException.throwBusinessException(ErrorCode.EMAIL_FAIL);
		}
        //返回给前台一个验证码编号
        return returnPO.getVerifyCodeId();
    }

    @Override
    public VerifyCodeRecordPO queryByVerifyCodeIdAndEmail(Integer verifyCodeId, String email) throws BusinessException {
        if(null == verifyCodeId){
            BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL , "验证码编号");
        }
        if(StringUtils.isBlank(email)){
            BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL , "验证码");
        }
        return verifyCodeRecordDao.findByVerifyCodeIdAndEmail(verifyCodeId, email);
    }

    @Override
    public void validateEmailByVerifyCode(VerifyCodeValidationVO verifyCodeValidationVO) throws BusinessException {
        VerifyCodeRecordPO verifyCodeRecordPO = verifyCodeRecordDao.queryById(verifyCodeValidationVO.getVerifyCodeId());
        if(verifyCodeRecordPO == null){
            BusinessException.throwBusinessException(ErrorCode.EMAIL_VERIFY_ERROR);
            return;
        }
        if(verifyCodeRecordPO.getVerifyCode() == null || (!verifyCodeRecordPO.getVerifyCode().equals(verifyCodeValidationVO.getVerifyCode()))){
            BusinessException.throwBusinessException(ErrorCode.VERIFY_CODE_ERROR);
        }
        if (!UserConstant.CodeSendType.RESET.getValue().equals(verifyCodeValidationVO.getVerifyCodeType())) {
            if (verifyCodeRecordPO.getMailAccount() == null || (!verifyCodeRecordPO.getMailAccount().equals(verifyCodeValidationVO.getEmail()))) {
                BusinessException.throwBusinessException(ErrorCode.VERIFY_CODE_ERROR);
            }
        }
    }


}
