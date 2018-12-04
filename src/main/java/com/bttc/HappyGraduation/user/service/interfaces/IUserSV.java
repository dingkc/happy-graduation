package com.bttc.HappyGraduation.user.service.interfaces;

import com.bttc.HappyGraduation.user.pojo.po.UserPO;

import java.util.List;

/**
* <p>Title: IUserSV</p>
* <p>Description: </p> 
* @author liuxf6
* @date 2018年7月3日
*/
public interface IUserSV {
	
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
	UserPO queryByAccountName(String accountName);
	
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
	 * 查询生效用户
	 * @param state
	 * @return
	 */
	List<UserPO> queryByState(Integer state);

}
