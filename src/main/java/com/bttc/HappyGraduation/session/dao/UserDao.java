package com.bttc.HappyGraduation.session.dao;


import com.bttc.HappyGraduation.session.pojo.po.UserPO;
import com.bttc.HappyGraduation.utils.base.BaseRepository;

import java.util.Collection;
import java.util.List;

/**
* <p>Title: UserDao</p>
* <p>Description: </p> 
* @author liuxf6
* @date 2018年7月4日
*/
public interface UserDao extends BaseRepository<UserPO, Integer> {

	UserPO findByUsernameAndState(String username, Integer state);

	UserPO findByUserIdAndState(Integer userId, Integer state);

	/**
	 * <p>Title: 查询用户信息</p>
	 * <p>Description: 根据用户姓名模糊查询用户信息</p>
	 * @Author: Dk
	 * @param state : 数据状态
	 * @param name : 用户名称
	 * @return: java.util.List<com.ai.osrdc.frame.session.pojo.po.business.UserPO>
	 * @Date: 2018/12/18 14:21
	 **/
	List<UserPO> findAllByStateAndNameLike(Integer state, String name);

	/**
	 * <p>Title: 查询所有用户信息</p>
	 * <p>Description: 查询所有生效用户信息</p>
	 * @Author: Dk
	 * @param state : 数据状态
	 * @return: java.util.List<com.ai.osrdc.frame.session.pojo.po.business.UserPO>
	 * @Date: 2018/12/18 14:22
	 **/
	List<UserPO> findAllByState(Integer state);

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @Author:songjd
	 * @param userIds :
	 * @param state :
	 * @return: java.util.List<com.ai.osrdc.frame.session.pojo.po.business.UserPO>
	 * @Date: 2019/3/6 9:33
	 */
	List<UserPO> findAllByUserIdInAndState(Collection<Integer> userIds, Integer state);

	/**
	 * <p>Title: </p>
	 * <p>Description: 根据用户编号和名称查询</p>
	 * @Author:songjd
	 * @param userIds :
	 * @param name :
	 * @param state :
	 * @return: java.util.List<com.ai.osrdc.frame.session.pojo.po.business.UserPO>
	 */
	List<UserPO> findAllByUserIdInAndNameLikeAndStateOrderByNameAsc(Collection<Integer> userIds, String name, Integer state);

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @Author:songjd
	 * @param userIds :
	 * @param state :
	 * @return: java.util.List<com.ai.osrdc.frame.session.pojo.po.business.UserPO>
	 * @Date: 2019/3/6 9:33
	 */
	List<UserPO> findAllByUserIdInAndStateOrderByNameAsc(Collection<Integer> userIds, Integer state);

	UserPO findAllByEmailAndState(String email, Integer state);
}
