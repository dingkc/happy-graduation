package com.bttc.HappyGraduation.user.service.interfaces;

import com.bttc.HappyGraduation.user.pojo.po.UserPO;
import com.bttc.HappyGraduation.user.pojo.vo.UserVO;

import java.util.List;

public interface ISystemUserSV {

	/**
	 * @Author Dk
	 * @Description 增加一条用户信息（各字段非空校验，邮箱验证码校验）
	 * @Date 10:57 2018/12/4
	 * @Param [userVO]
	 * @return void
	 **/
	void addUser(UserVO userVO);

	/**
	 * @Author Dk
	 * @Description 更新用户信息
	 * @Date 10:57 2018/12/4
	 * @Param [userId, userVO]
	 * @return void
	 **/
	void updateUser(Integer userId, UserVO userVO);

	UserPO queryByAccountName(String accountName);

	/**
	 * @Author Dk
	 * @Description 重置用户密码
	 * @Date 10:57 2018/12/4
	 * @Param [userVO]
	 * @return void
	 **/
	void updateUser(UserVO userVO);

	/**
	 * 查询所有用户
	 * @return
	 */
	List<UserVO> getAllUsers();

}
