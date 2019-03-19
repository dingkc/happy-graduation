package com.bttc.HappyGraduation.session.service.interfaces;


import com.bttc.HappyGraduation.session.pojo.po.UserPO;
import com.bttc.HappyGraduation.session.pojo.vo.UserVO;
import com.bttc.HappyGraduation.utils.exception.BusinessException;

import java.util.List;
import java.util.Map;

/**
* <p>Title: IUserSV</p>
* <p>Description: </p> 
* @author liuxf6
* @date 2018年7月3日
*/
public interface IUserSV {

	/**
	 * <p>Title: 多条件查询用户</p>
	 * <p>Description: </p>
	 * @Author: Dk
	 * @param userId : 用户id
	 * @param userName : 用户名称（登录名）
	 * @param queryType : 查询类型0所有用户1当前用户
	 * @return: java.util.List<com.ai.osrdc.core.business.user.pojo.vo.UserVO>
	 * @Date: 2018/12/19 15:03
	 **/
	List<UserVO> queryByConditions(Integer userId, String userName, Integer queryType) throws BusinessException;

	/**
	 * <p>Title: addUser</p>
	 * <p>Description: 增加一条用户信息（各字段非空校验，邮箱验证码校验，gitlab账号校验和账号唯一性校验）</p>
	 * @author liuxf6
	 * @throws BusinessException
	 * @date 2018年7月4日
	 * @modifier Dk
	 * @date 2018年10月23日
	 */
	void addUser(UserVO userVO) throws BusinessException;

	/**
	 * <p>Title: updateUser</p>
	 * <p>Description: 更新用户信息</p>
	 * @author liuxf6
	 * @throws BusinessException
	 * @date 2018年7月4日
	 */
	void updateUser(Integer userId, UserVO userVO) throws BusinessException;

	/**
	 * <p>Title: updateUser</p>
	 * <p>Description: 重置用户密码</p>
	 * @author liuxf6
	 * @throws BusinessException
	 * @date 2018年7月9日
	 */
	void updateUser(UserVO userVO) throws BusinessException;

	/**
	 * <p>Title: queryByName</p>
	 * <p>Description: 根据用户中文名查询用户</p>
	 * @Author: Dk
	 * @param name : 用户中文名
	 * @return: java.util.List<com.ai.osrdc.frame.session.pojo.po.business.UserPO>
	 * @Date: 2018/12/20 14:07
	 **/
	List<UserPO> queryByName(String name);

	/**
	 * <p>Title: queryByUserId</p>
	 * <p>Description: 根据用户编号查询用户信息</p>
	 * @author liuxf6
	 * @date 2018年7月4日
	 */
	UserPO queryByUserId(Integer userId);

	/**
	 * <p>Title: queryByAccountName</p>
	 * <p>Description: 根据账户名查询用户信息</p>
	 * @author liuxf6
	 * @date 2018年7月4日
	 */
	UserPO queryByUsername(String username);

	/**
	 * <p>Title: addUser</p>
	 * <p>Description: 增加一条用户信息</p>
	 * @author liuxf6
	 * @date 2018年7月4日
	 */
	UserPO addUser(UserPO userPO);

	/**
	 * <p>Title: updateUser</p>
	 * <p>Description: 更新用户信息</p>
	 * @author liuxf6
	 * @date 2018年7月4日
	 */
	UserPO updateUser(UserPO userPO);

	/**
	 * <p>Title: 查询所有用户</p>
	 * <p>Description: 模糊查询用户信息</p>
	 * @Author: Dk
	 * @param state : 数据状态
	 * @param name : 用户名称
	 * @return: java.util.List<com.ai.osrdc.frame.session.pojo.po.business.UserPO>
	 * @Date: 2018/12/18 12:26
	 **/
	List<UserPO> queryByNameAndState(Integer state, String name);

	/**
	 * <p>Title: 查询所有生效用户</p>
	 * <p>Description: </p>
	 * @Author: Dk
	 * @param state : 数据状态
	 * @return: java.util.List<com.ai.osrdc.frame.session.pojo.po.business.UserPO>
	 * @Date: 2018/12/18 12:27
	 **/
	List<UserPO> queryByState(Integer state);

	/**
	 * <p>Title: queryByUserIds</p>
	 * <p>Description: 根据用户编号列表查询用户信息列表</p>
	 * @author kaiz
	 * @date 2018年11月9日
	 */
	Map<Integer,UserPO> queryByUserIds(List<Integer> userIds);

	/**
	 * <p>Title: </p>
	 * <p>Description:根据用户编号列表和用户名字查询 </p>
	 * @Author:songjd
	 * @param userIdList :
	 * @param name :
	 * @return: java.util.List<com.ai.osrdc.frame.session.pojo.po.business.UserPO>
	 * @Date: 2019/3/1 14:45
	 */
	List<UserPO> queryByUserIdsAndUsername(List<Integer> userIdList, String name);
}
