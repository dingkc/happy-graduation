package com.bttc.HappyGraduation.session.service.interfaces;


import com.bttc.HappyGraduation.session.pojo.po.UserPO;
import com.bttc.HappyGraduation.session.pojo.vo.UserVO;
import com.bttc.HappyGraduation.utils.exception.BusinessException;

import java.util.List;
import java.util.Map;

/**
 * @Author: Dk
 * @Date: 2019/3/25 23:23
 **/
public interface IUserSV {

	/**
	 * <p>Title: 多条件查询用户</p>
	 * <p>Description: </p>
	 * @Author: Dk
	 * @param userId : 用户id
	 * @param userName : 用户名称（登录名）
	 * @param queryType : 查询类型0所有用户1当前用户
	 * @return: java.util.List<com.ai.osrdc.core.business.user.pojo.vo.UserVO>
	 * @Date: 2019/3/25 23:23
	 **/
	List<UserVO> queryByConditions(Integer userId, String userName, Integer queryType) throws BusinessException;

	/**
	 * <p>Title: addUser</p>
	 * <p>Description: 增加一条用户信息（各字段非空校验，邮箱验证码校验，gitlab账号校验和账号唯一性校验）</p>
	 * @Author: Dk
	 * @param userVO : 用户信息
	 * @return: void
	 * @Date: 2019/3/25 23:24
	 **/
	void addUser(UserVO userVO) throws BusinessException;

	/**
	 * <p>Title: updateUser</p>
	 * <p>Description: 更新用户信息</p>
	 * @Author: Dk
	 * @param userId : 用户id
	 * @param userVO : 用户信息
	 * @return: void
	 * @Date: 2019/3/25 23:24
	 **/
	void updateUser(Integer userId, UserVO userVO) throws BusinessException;

	/**
	 * <p>Title: updateUser</p>
	 * <p>Description: 重置用户密码</p>
	 * @Author: Dk
	 * @param userVO : 用户信息
	 * @return: void
	 * @Date: 2019/3/25 23:25
	 **/
	void updateUser(UserVO userVO) throws BusinessException;

	/**
	 * <p>Title: queryByName</p>
	 * <p>Description: 根据用户中文名查询用户</p>
	 * @Author: Dk
	 * @param name : 用户中文名
	 * @return: java.util.List<com.ai.osrdc.frame.session.pojo.po.business.UserPO>
	 * @Date: 2019/3/25 23:25
	 **/
	List<UserPO> queryByName(String name);

	/**
	 * <p>Title: queryByUserId</p>
	 * <p>Description: 根据用户编号查询用户信息</p>
	 * @Author: Dk
	 * @param userId : 用户编号
	 * @return: com.bttc.HappyGraduation.session.pojo.po.UserPO
	 * @Date: 2019/3/25 23:25
	 **/
	UserPO queryByUserId(Integer userId);

	/**
	 * <p>Title: queryByAccountName</p>
	 * <p>Description: 根据账户名查询用户信息</p>
	 * @Author: Dk
	 * @param username : 账户名
	 * @return: com.bttc.HappyGraduation.session.pojo.po.UserPO
	 * @Date: 2019/3/25 23:26
	 **/
	UserPO queryByUsername(String username);

	/**
	 * <p>Title: addUser</p>
	 * <p>Description: 增加一条用户信息</p>
	 * @Author: Dk
	 * @param userPO : 用户信息
	 * @return: com.bttc.HappyGraduation.session.pojo.po.UserPO
	 * @Date: 2019/3/25 23:26
	 **/
	UserPO addUser(UserPO userPO);

	/**
	 * <p>Title: updateUser</p>
	 * <p>Description: 更新用户信息</p>
	 * @Author: Dk
	 * @param userPO : 用户信息
	 * @return: com.bttc.HappyGraduation.session.pojo.po.UserPO
	 * @Date: 2019/3/25 23:28
	 **/
	UserPO updateUser(UserPO userPO);

	List<UserPO> queryByEmailAndState(String email);

	/**
	 * <p>Title: 查询所有生效用户</p>
	 * <p>Description: </p>
	 * @Author: Dk
	 * @param state : 数据状态
	 * @return: java.util.List<com.ai.osrdc.frame.session.pojo.po.business.UserPO>
	 * @Date: 2018/12/18 12:27
	 **/
	List<UserPO> queryByState(Integer state);

	UserVO queryByEmail(String email);
}
