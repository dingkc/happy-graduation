package com.bttc.HappyGraduation.user.service.impl;

import com.bttc.HappyGraduation.user.dao.VerifyCodeRecordDao;
import com.bttc.HappyGraduation.user.pojo.po.VerifyCodeRecordPO;
import com.bttc.HappyGraduation.user.pojo.vo.VerifyCodeRecordVO;
import com.bttc.HappyGraduation.user.service.interfaces.IVerifyCodeRecordSV;
import com.bttc.HappyGraduation.utils.BeanMapperUtil;
import com.bttc.HappyGraduation.utils.DateUtil;
import com.bttc.HappyGraduation.utils.VerifyCodeUtils;
import com.bttc.HappyGraduation.utils.email.component.MailConnection;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import com.bttc.HappyGraduation.utils.exception.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class VerifyCodeRecordSVImpl implements IVerifyCodeRecordSV {

    @Autowired
    VerifyCodeRecordDao verifyCodeRecordDao;

    @Autowired
    private MailConnection mailConnection;


    @Override
    public Integer sendVerifyCodeByEmail(VerifyCodeRecordVO verifyCodeRecordVO) {
        if (null == verifyCodeRecordVO.getMailAccount() && StringUtils.isNotBlank(verifyCodeRecordVO.getMailAccount())) {
            BusinessException.throwBusinessException(ErrorCode.EMAIL_FAIL);
        }
        if (null == verifyCodeRecordVO.getVerifyCodeType()) {
            BusinessException.throwBusinessException(ErrorCode.EMAIL_FAIL);
        }
        String verifyCode = VerifyCodeUtils.generateVerifyCode(5);
        try {
            StringBuilder sb = new StringBuilder();
            String content = null;
            sb.append("【").append("一站式研发云平台").append("】");
            switch (verifyCodeRecordVO.getVerifyCodeType()) {
                case 1:
                    content = sb.append("创建账号的验证码是").append(verifyCode).append(",30分钟内有效！").toString();
                    break;
                case 2:
                    content = sb.append("重置密码的验证码是").append(verifyCode).append(",30分钟内有效！").toString();
                    break;
                case 3:
                    content = sb.append("绑定邮箱的验证码是").append(verifyCode).append(",30分钟内有效！").toString();
                    break;
            }
            mailConnection.sendMail(verifyCodeRecordVO.getMailAccount(), "", "一站式研发云平台", content, null);
        } catch (Exception e) {
            BusinessException.throwBusinessException(ErrorCode.EMAIL_FAIL);
        }

        VerifyCodeRecordPO verifyCodeRecordPO = BeanMapperUtil.map(verifyCodeRecordVO, VerifyCodeRecordPO.class);
        verifyCodeRecordPO.setVerifyCode(verifyCode);
        //获取当前时间
        Date nowDate = DateUtil.getNowDate();
        Date expireDate = DateUtil.getDateInHarfAnHour();
        verifyCodeRecordPO.setCreateDate(nowDate);
        verifyCodeRecordPO.setExpireDate(expireDate);
        VerifyCodeRecordPO returnPO = verifyCodeRecordDao.save(verifyCodeRecordPO);

        //返回给前台一个验证码编号
        return returnPO.getVerifyCodeId();
    }

    @Override
    public VerifyCodeRecordPO queryByVerifyCodeIdAndEmail(Integer verifyCodeId, String email) {
        if (null == verifyCodeId) {
            BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, "验证码编号");
        }
        if (StringUtils.isBlank(email)) {
            BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, "验证码");
        }
        return verifyCodeRecordDao.findByVerifyCodeIdAndEmail(verifyCodeId, email);
    }

    @Override
    public void validateEmailByVerifyCode(Integer verifyCodeId, String verifyCode, String emaill) {
        VerifyCodeRecordPO verifyCodeRecordPO = verifyCodeRecordDao.queryById(verifyCodeId);
        if (verifyCodeRecordPO == null) {
            BusinessException.throwBusinessException(ErrorCode.EMAIL_VERIFY_ERROR);
            return;
        }
        if (verifyCodeRecordPO.getVerifyCode() == null || (!verifyCodeRecordPO.getVerifyCode().equals(verifyCode))) {
            BusinessException.throwBusinessException(ErrorCode.VERIFY_CODE_ERROR);
        }
        if (verifyCodeRecordPO.getMailAccount() == null || (!verifyCodeRecordPO.getMailAccount().equals(emaill))) {
            BusinessException.throwBusinessException(ErrorCode.VERIFY_CODE_ERROR);
        }

    }


}
