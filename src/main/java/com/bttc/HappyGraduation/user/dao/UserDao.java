package com.bttc.HappyGraduation.user.dao;

import com.bttc.HappyGraduation.user.pojo.po.UserPO;
import com.bttc.HappyGraduation.utils.datebase.BaseRepository;

import java.util.List;

/**
* <p>Title: UserDao</p>
* <p>Description: </p> 
* @author liuxf6
* @date 2018年7月4日
*/
public interface UserDao extends BaseRepository<UserPO, Integer> {

	UserPO findByAccountNameAndState(String accountName, Integer state);

	UserPO findByUserIdAndState(Integer userId, Integer state);
	
	List<UserPO> findByState(Integer state);

}
