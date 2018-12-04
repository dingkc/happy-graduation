package com.bttc.HappyGraduation.user.service.impl;

import com.bttc.HappyGraduation.user.constant.CommonConstant;
import com.bttc.HappyGraduation.user.dao.UserDao;
import com.bttc.HappyGraduation.user.pojo.po.UserPO;
import com.bttc.HappyGraduation.user.service.interfaces.IUserSV;
import com.bttc.HappyGraduation.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author Dk
 * @Description user service
 * @Date 11:46 2018/12/4
 * @Param
 * @return
 **/
@Service
@Transactional(rollbackFor = Throwable.class)
public class UserSVImpl implements IUserSV {

    @Autowired
    private UserDao userDao;

    @Override
    public UserPO queryByUserId(Integer userId) {
        return userDao.findByUserIdAndState(userId, CommonConstant.CommonState.EFFECT.getValue());
    }

    @Override
    public UserPO queryByAccountName(String accountName) {
        return userDao.findByAccountNameAndState(accountName, CommonConstant.CommonState.EFFECT.getValue());
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
    public List<UserPO> queryByState(Integer state) {
        return userDao.findByState(state);
    }

}
