package com.bttc.HappyGraduation.user.service.impl;

import com.bttc.HappyGraduation.user.constant.UserConstant;
import com.bttc.HappyGraduation.user.constant.CommonConstant;
import com.bttc.HappyGraduation.user.pojo.po.UserPO;
import com.bttc.HappyGraduation.user.pojo.po.VerifyCodeRecordPO;
import com.bttc.HappyGraduation.user.pojo.vo.UserVO;
import com.bttc.HappyGraduation.user.service.interfaces.ISystemUserSV;
import com.bttc.HappyGraduation.user.service.interfaces.IUserSV;
import com.bttc.HappyGraduation.user.service.interfaces.IVerifyCodeRecordSV;
import com.bttc.HappyGraduation.utils.BeanMapperUtil;
//import com.bttc.HappyGraduation.utils.SessionManager;
//import com.bttc.HappyGraduation.utils.UserInfo;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import com.bttc.HappyGraduation.utils.exception.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;

@Service
@Transactional
public class SystemUserSVImpl implements ISystemUserSV {

	@Autowired
	private IUserSV iUserSV;

	@Autowired
	private IVerifyCodeRecordSV iVerifyCodeRecordSV;

	@Override
	public void addUser(@Nonnull UserVO userVO) {
		//参数非空校验
		this.validateUserParam(userVO);
		//验证码校验
		VerifyCodeRecordPO verifyCodeRecordsPO = iVerifyCodeRecordSV.queryByVerifyCodeIdAndEmail(userVO.getVerifyCodeId(), userVO.getEmail());
		if(null == verifyCodeRecordsPO || !userVO.getVerifyCode().equals(verifyCodeRecordsPO.getVerifyCode())) {
			//验证码已失效或错误
			BusinessException.throwBusinessException(ErrorCode.USER_VERIFY_CODE_UNEXSIT);
		}
		//用户名唯一性校验
		if(null != iUserSV.queryByAccountName(userVO.getAccountName())) {
			//用户名已存在
			BusinessException.throwBusinessException(ErrorCode.USER_ALREADY_EXSIT);
		}
		//保存用户信息
		UserPO userPO = iUserSV.addUser(BeanMapperUtil.map(userVO, UserPO.class));
	}

	private void validateUserParam(UserVO userVO) {
		if(userVO == null) {
			//请求参数不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_UNABLE_NULL);
			return;
		}
		//非空校验
		if(StringUtils.isBlank(userVO.getAccountName())) {
			//请求参数 [用户名] 不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, "用户名");
		}
		if(StringUtils.isBlank(userVO.getUserName())) {
			//请求参数 [姓名] 不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, "姓名");
		}
		if(StringUtils.isBlank(userVO.getPassword())) {
			//请求参数 [密码] 不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, "密码");
		}
		if(StringUtils.isBlank(userVO.getMobile())) {
			//请求参数 [手机号码] 不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, "手机号码");
		}
		if(StringUtils.isBlank(userVO.getEmail())) {
			//请求参数 [邮箱] 不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, "邮箱");
		}
		if(userVO.getVerifyCodeId() == null) {
			//请求参数 [验证码编号] 不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, UserConstant.VERIFY_CODE_ID);
		}
		if(StringUtils.isBlank(userVO.getVerifyCode())) {
			//请求参数 [验证码] 不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, "验证码");
		}
	}

	@Override
	public void updateUser(Integer userId, UserVO userVO) {
		if(null == userId || null == userVO) {
			//请求参数不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_UNABLE_NULL);
			return;
		}
        //如果是修改邮箱，需要做验证码校验
        checkEmailVerifyCode(userVO);
		//判断session中的userId和传入的是否一致
//        checkUserIdWithSession(userId);
        //判断旧密码是否正确
        checkPassword(userId,userVO);
		UserPO userPO = BeanMapperUtil.map(userVO, UserPO.class);
		userPO.setUserId(userId);
		//更新用户信息
		UserPO user = iUserSV.updateUser(BeanMapperUtil.map(userVO, UserPO.class));
        //重新设置session信息
//        SessionManager.setUserInfo(user);
	}

	private void checkPassword(@Nonnull Integer userId,@Nonnull UserVO userVO){
        UserPO userPO = iUserSV.queryByUserId(userId);
        if(StringUtils.isNotBlank(userVO.getPassword())&&StringUtils.isNotBlank(userVO.getOldPassword())&&!userVO.getOldPassword().equals(userPO.getPassword())){
            BusinessException.throwBusinessException(ErrorCode.USER_OLD_PASSWORD_UNCORRECT);
        }
    }

	/*private void checkUserIdWithSession(@Nonnull Integer userId){
        UserInfo userInfo = SessionManager.getUserInfo();
        if(null != userInfo&&!userId.equals(userInfo.getUserId())) {
            //用户异常，请重新登录
            BusinessException.throwBusinessException(ErrorCode.USER_EXCEPTION);
        }
    }*/

	private void checkEmailVerifyCode(@Nonnull UserVO userVO){
        String email = userVO.getEmail();
        Integer verifyCodeId = userVO.getVerifyCodeId();
        String verifyCode = userVO.getVerifyCode();
        if(StringUtils.isNotBlank(email) ^ StringUtils.isNotBlank(verifyCode)) {
            BusinessException.throwBusinessException(ErrorCode.USER_GIT_COMPELETE);
        } else if(StringUtils.isNotBlank(email) && StringUtils.isNotBlank(verifyCode) && null == verifyCodeId){
            //请求参数 [验证码编号] 不能为空
            BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, UserConstant.VERIFY_CODE_ID);
        } else if(StringUtils.isNotBlank(email) && StringUtils.isNotBlank(verifyCode) && null != verifyCodeId) {
            VerifyCodeRecordPO verifyCodeRecordsPO = iVerifyCodeRecordSV.queryByVerifyCodeIdAndEmail(verifyCodeId, email);
            if(null == verifyCodeRecordsPO || !verifyCode.equals(verifyCodeRecordsPO.getVerifyCode())) {
                //验证码已失效或错误
                BusinessException.throwBusinessException(ErrorCode.USER_VERIFY_CODE_UNEXSIT);
            }
        }
    }

	@Override
	public UserPO queryByAccountName(String accountName) {
		if(StringUtils.isBlank(accountName)){
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, "用户名");
		}
		return iUserSV.queryByAccountName(accountName);
	}

	@Override
	public void updateUser(UserVO userVO) {
		if(null == userVO) {
			//请求参数不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_UNABLE_NULL);
			return;
		}
		//非空校验
		String accountName = userVO.getAccountName();
		if(StringUtils.isBlank(accountName)) {
			//请求参数 [用户名] 不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, "用户名");
		}
		String password = userVO.getPassword();
		if(StringUtils.isBlank(password)) {
			//请求参数 [密码] 不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, "密码");
		}
		Integer verifyCodeId = userVO.getVerifyCodeId();
		if(null == verifyCodeId) {
			//请求参数 [验证码编号] 不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, UserConstant.VERIFY_CODE_ID);
		}
		String verifyCode = userVO.getVerifyCode();
		if(StringUtils.isBlank(verifyCode)) {
			//请求参数 [验证码] 不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, "验证码");
			return;
		}
		//根据accountName查询用户信息
		UserPO userPO = iUserSV.queryByAccountName(accountName);
		if(null == userPO) {
			//用户不存在
			BusinessException.throwBusinessException(ErrorCode.USER_NOT_EXIST);
			return;
		}
		String email = userPO.getEmail();
		//验证码校验
		VerifyCodeRecordPO verifyCodeRecordPO = iVerifyCodeRecordSV.queryByVerifyCodeIdAndEmail(verifyCodeId, email);
		if(null == verifyCodeRecordPO || !verifyCode.equals(verifyCodeRecordPO.getVerifyCode())) {
			//验证码已失效或错误
			BusinessException.throwBusinessException(ErrorCode.USER_VERIFY_CODE_UNEXSIT);
		}
		userPO.setPassword(password);
		iUserSV.updateUser(userPO);
	}


	/**
	 * 查询所有用户
	 */
	@Override
	public List<UserVO> getAllUsers() {
		List<UserVO> allUserslist;
		List<UserPO> userPO= iUserSV.queryByState(CommonConstant.CommonState.EFFECT.getValue());
		allUserslist=BeanMapperUtil.mapList(userPO,UserPO.class,UserVO.class);
        return allUserslist;

	}

}
