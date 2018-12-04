package com.bttc.HappyGraduation.controller;

import com.bttc.HappyGraduation.user.pojo.vo.UserVO;
import com.bttc.HappyGraduation.user.pojo.vo.VerifyCodeRecordVO;
import com.bttc.HappyGraduation.user.service.interfaces.ISystemUserSV;
import com.bttc.HappyGraduation.user.service.interfaces.IUserSV;
import com.bttc.HappyGraduation.user.service.interfaces.IVerifyCodeRecordSV;
import com.bttc.HappyGraduation.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private IVerifyCodeRecordSV iVerifyCodeRecordSV;

    @Autowired
    private ISystemUserSV iSystemUserSV;

    @Autowired
    private IUserSV iUserSV;

    /**
     * @Author Dk
     * @Description 测试请求
     * @Date 11:02 2018/12/4
     * @Param []
     * @return com.bttc.HappyGraduation.utils.ResultBean
     **/
    @GetMapping(value = "${apiVersion1}/hello")
    public ResultBean sayHello() {
        return ResultBean.ok("Hello world!");
    }

    /**
     * @Author Dk
     * @Description 查询用户信息
     * @Date 11:01 2018/12/4
     * @Param [userId]
     * @return com.bttc.HappyGraduation.utils.ResultBean
     **/
    @GetMapping(value = "${apiVersion1}/users/{userId}")
    public ResultBean getUserById(@PathVariable Integer userId) {
        return ResultBean.ok(iUserSV.queryByUserId(userId));
    }

    /**
     * @Author Dk
     * @Description 查询所有用户
     * @Date 10:59 2018/12/4
     * @Param []
     * @return com.bttc.HappyGraduation.utils.ResultBean
     **/
    @GetMapping(value="${apiVersion1}/users")
    public ResultBean queryAllUser(){
        return ResultBean.ok(iSystemUserSV.getAllUsers());

    }

   /**
    * @Author Dk
    * @Description 注册用户
    * @Date 10:59 2018/12/4
    * @Param [userVO]
    * @return com.bttc.HappyGraduation.utils.ResultBean
    **/
    @PostMapping("${apiVersion1}/users")
    public ResultBean addUser(@RequestBody UserVO userVO) {
        iSystemUserSV.addUser(userVO);
        return ResultBean.ok(null);
    }

    /**
     * @Author Dk
     * @Description 更新用户信息
     * @Date 11:00 2018/12/4
     * @Param [userId, userVO]
     * @return com.bttc.HappyGraduation.utils.ResultBean
     **/
    @PutMapping("${apiVersion1}/users/{userId}")
    public ResultBean updateUser(@PathVariable Integer userId, @RequestBody UserVO userVO) {
        iSystemUserSV.updateUser(userId, userVO);
        return ResultBean.ok(null);
    }

    /**
     * @Author Dk
     * @Description 重置用户密码
     * @Date 11:00 2018/12/4
     * @Param [userVO]
     * @return com.bttc.HappyGraduation.utils.ResultBean
     **/
    @PutMapping("${apiVersion1}/users/password-resetting")
    public ResultBean resetByPassword(@RequestBody UserVO userVO) {
        iSystemUserSV.updateUser(userVO);
        return ResultBean.ok(null);
    }

    /**
     * @Author Dk
     * @Description 发送验证码邮件并保存验证码记录表
     * @Date 11:00 2018/12/4
     * @Param [verifyCodeRecordVO]
     * @return com.bttc.HappyGraduation.utils.ResultBean
     **/
    @PostMapping(value = "${apiVersion1}/verifyCodes")
    public ResultBean sendVerifyCodeByEmail(@RequestBody VerifyCodeRecordVO verifyCodeRecordVO) {
        return ResultBean.ok(iVerifyCodeRecordSV.sendVerifyCodeByEmail(verifyCodeRecordVO));
    }

    /**
     * @Author Dk
     * @Description 验证验证码是否正确
     * @Date 11:00 2018/12/4
     * @Param [verifyCodeId, verifyCode, email]
     * @return com.bttc.HappyGraduation.utils.ResultBean
     **/
    @GetMapping(value = "${apiVersion1}/verifyCodes-validation")
    public ResultBean validateEmailByVerifyCode(@RequestParam Integer verifyCodeId, @RequestParam String verifyCode, @RequestParam String email) {
        iVerifyCodeRecordSV.validateEmailByVerifyCode(verifyCodeId, verifyCode, email);
        return ResultBean.ok(null);
    }

}
