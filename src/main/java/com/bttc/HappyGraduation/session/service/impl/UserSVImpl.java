package com.bttc.HappyGraduation.session.service.impl;

import com.alibaba.fastjson.JSON;
import com.bttc.HappyGraduation.session.dao.UserDao;
import com.bttc.HappyGraduation.session.pojo.po.UserPO;
import com.bttc.HappyGraduation.session.pojo.vo.UserVO;
import com.bttc.HappyGraduation.session.service.interfaces.IUserSV;
import com.bttc.HappyGraduation.session.web.SessionManager;
import com.bttc.HappyGraduation.session.web.UserInfo;
import com.bttc.HappyGraduation.common.BeanMapperUtil;
import com.bttc.HappyGraduation.common.DateUtil;
import com.bttc.HappyGraduation.email.service.interfaces.IVerifyCodeRecordSV;
import com.bttc.HappyGraduation.email.pojo.po.VerifyCodeRecordPO;
import com.bttc.HappyGraduation.utils.base.Filter;
import com.bttc.HappyGraduation.utils.base.QueryParams;
import com.bttc.HappyGraduation.utils.constant.CommonConstant;
import com.bttc.HappyGraduation.utils.constant.UserConstant;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import com.bttc.HappyGraduation.utils.exception.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.*;

@Service
@Transactional(rollbackFor=Exception.class)
public class UserSVImpl implements IUserSV {

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(UserSVImpl.class);

	@Autowired
	private IUserSV iUserSV;

	@Autowired
	private UserDao userDao;

	@Autowired
	private IVerifyCodeRecordSV iVerifyCodeRecordSV;

	@Override
	public List<UserVO> queryByConditions(Integer userId, String name, Integer queryType) throws BusinessException {
		List<UserVO> list = new ArrayList<>();
		if (UserConstant.userQueryType.ALL.getValue().equals(queryType)) {
			if (null != userId) {
				list.add(BeanMapperUtil.map(iUserSV.queryByUserId(userId), UserVO.class));
				return list;
			}
			if (StringUtils.isNotBlank(name)) {
				list.addAll(BeanMapperUtil.mapList(iUserSV.queryByName(name),UserPO.class,UserVO.class));
				return list;
			}
			list = BeanMapperUtil.mapList(iUserSV.queryByState(CommonConstant.CommonState.EFFECT.getValue()),UserPO.class,UserVO.class);
//			StringBuffer stringBuffer = new StringBuffer();
//			for (UserVO userVO:list) {
//				StringBuffer nameAndUserName = stringBuffer.append(userVO.getName()).append("(").append(userVO.getUsername()).append(")");
//				userVO.setNameAndUserName(nameAndUserName.toString());
//				stringBuffer.setLength(0);
//			}
			return list;
		}
		if (UserConstant.userQueryType.CURRENT.getValue().equals(queryType)) {
			UserVO userVO = new UserVO();
			try{
				UserInfo userInfo = SessionManager.getUserInfo();
				userVO = BeanMapperUtil.map(userInfo, UserVO.class);
				userVO.setOldPassword("");
				userVO.setPassword("");
				Integer currentUserId = userInfo.getUserId();
			}catch (Exception e) {
				// 演示临时修改，后续恢复
				userVO.setName("请登录");
			}
			if (logger.isInfoEnabled()) {
				logger.info("当前用户信息：" + JSON.toJSONString(userVO));
			}
			list.add(userVO);
		}
		return list;
	}

	@Override
	public void addUser(@Nonnull UserVO userVO) throws BusinessException {
		//参数非空校验
		this.validateUserParam(userVO);
		//验证码校验
		VerifyCodeRecordPO verifyCodeRecordsPO = iVerifyCodeRecordSV.queryByVerifyCodeIdAndEmail(userVO.getVerifyCodeId(), userVO.getEmail());
		if(null == verifyCodeRecordsPO || !userVO.getVerifyCode().equals(verifyCodeRecordsPO.getVerifyCode())) {
			//验证码已失效或错误
			BusinessException.throwBusinessException(ErrorCode.USER_VERIFY_CODE_UNEXSIT);
		}
		//用户名唯一性校验
		if(null != iUserSV.queryByUsername(userVO.getUsername())) {
			//用户名已存在
			BusinessException.throwBusinessException(ErrorCode.USER_ALREADY_EXSIT);
		}
		//保存用户信息
		iUserSV.addUser(BeanMapperUtil.map(userVO, UserPO.class));
	}

	private void validateUserParam(UserVO userVO) throws BusinessException {
		if(userVO == null) {
			//请求参数不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_UNABLE_NULL);
			return;
		}
		//非空校验
		if(StringUtils.isBlank(userVO.getUsername())) {
			//请求参数 [用户名] 不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, "用户名");
		}
		if(StringUtils.length(userVO.getUsername())<4 || StringUtils.length(userVO.getUsername())>10){
			//用户名长度校验，长度在4-10
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_INVALID, "用户名");
		}
		if(StringUtils.isBlank(userVO.getName())) {
			//请求参数 [姓名] 不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, "姓名");
		}
		if(StringUtils.length(userVO.getName())>10){
			//姓名长度校验，长度在不可大于10
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_INVALID, "姓名");
		}
		if(StringUtils.isBlank(userVO.getPassword())) {
			//请求参数 [密码] 不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_NULL, "密码");
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

	private void checkPassword(@Nonnull Integer userId,@Nonnull UserVO userVO) throws BusinessException{
		UserPO userPO = iUserSV.queryByUserId(userId);
		if(StringUtils.isNotBlank(userVO.getPassword())&&StringUtils.isNotBlank(userVO.getOldPassword())&&!userVO.getOldPassword().equals(userPO.getPassword())){
			BusinessException.throwBusinessException(ErrorCode.USER_OLD_PASSWORD_UNCORRECT);
		}
	}

	private void checkUserIdWithSession(@Nonnull Integer userId) throws BusinessException{
		UserInfo userInfo = SessionManager.getUserInfo();
		if(null != userInfo&&!userId.equals(userInfo.getUserId())) {
			//用户异常，请重新登录
			BusinessException.throwBusinessException(ErrorCode.USER_EXCEPTION);
		}
	}

	private void checkEmailVerifyCode(@Nonnull UserVO userVO) throws BusinessException{
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
	public void updateUser(Integer userId, UserVO userVO) throws BusinessException {
		if(null == userId || null == userVO) {
			//请求参数不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_UNABLE_NULL);
			return;
		}
		if(StringUtils.length(userVO.getName())>10){
			//姓名长度校验，长度在不可大于10
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_INVALID, "姓名");
		}
		//如果是修改邮箱，需要做验证码校验
		checkEmailVerifyCode(userVO);
		//判断session中的userId和传入的是否一致
		checkUserIdWithSession(userId);
		//判断旧密码是否正确
		checkPassword(userId,userVO);
		UserPO userPO = BeanMapperUtil.map(userVO, UserPO.class);
		userPO.setUserId(userId);
		//更新密码
//		updatePassword(userVO);
		//更新用户信息
		UserPO user = iUserSV.updateUser(BeanMapperUtil.map(userVO, UserPO.class));
		//重新设置session信息
		SessionManager.setUserInfo(user);
	}

	@Override
	public void updateUser(UserVO userVO) throws BusinessException {
		if(null == userVO) {
			//请求参数不能为空
			BusinessException.throwBusinessException(ErrorCode.PARAMETER_UNABLE_NULL);
			return;
		}
		//非空校验
		String username = userVO.getUsername();
		if(StringUtils.isBlank(username)) {
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
		//根据username查询用户信息
		UserPO userPO = iUserSV.queryByUsername(username);
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

	@Override
	public List<UserPO> queryByName(String name) {
		QueryParams<UserPO> queryParams = new QueryParams<>(UserPO.class);
		queryParams.and(Filter.like("name", name));
		return userDao.findAll(queryParams);
	}

	@Override
	public UserPO queryByUserId(Integer userId) {
		return userDao.findByUserIdAndState(userId, CommonConstant.CommonState.EFFECT.getValue());
	}

	@Override
	public UserPO queryByUsername(String username) {
		return userDao.findByUsernameAndState(username, CommonConstant.CommonState.EFFECT.getValue());
	}

	@Override
	public UserPO addUser(UserPO userPO) {
		//获取当前时间
		Date nowDate = DateUtil.getNowDate();
		userPO.setCreateDate(nowDate);
		userPO.setDoneDate(nowDate);
		userPO.setState(CommonConstant.CommonState.EFFECT.getValue());
		return userDao.save(userPO);
	}

	@Override
	public UserPO updateUser(UserPO userPO) {
		//获取当前时间
		Date nowDate = DateUtil.getNowDate();
		userPO.setDoneDate(nowDate);
		return userDao.updateBeans(userPO);
	}
	/**
	 * 查询生效用户
	 */
	@Override
	public List<UserPO> queryByNameAndState(Integer state, String name) {
		return userDao.findAllByStateAndNameLike(state,"%" + name + "%");
	}

	@Override
	public List<UserPO> queryByState(Integer state) {
		return userDao.findAllByState(CommonConstant.CommonState.EFFECT.getValue());
	}

}
