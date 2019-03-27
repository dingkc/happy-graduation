package com.bttc.HappyGraduation.controller;


import com.bttc.HappyGraduation.session.service.interfaces.IUserSV;
import com.bttc.HappyGraduation.session.pojo.vo.UserVO;
import com.bttc.HappyGraduation.email.service.interfaces.IVerifyCodeRecordSV;
import com.bttc.HappyGraduation.common.ResultBean;
import com.bttc.HappyGraduation.email.pojo.vo.VerifyCodeRecordVO;
import com.bttc.HappyGraduation.email.pojo.vo.VerifyCodeValidationVO;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private IVerifyCodeRecordSV iVerifyCodeRecordSV;

    @Autowired
    private IUserSV iUserSV;

    /**
     * @Author Dk
     * @Description 测试请求
     * @Date 11:02 2018/12/4
     * @Param []
     * @return com.bttc.HappyGraduation.common.ResultBean
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
     * @return com.bttc.HappyGraduation.common.ResultBean
     **/
    @GetMapping(value = "${apiVersion1}/users/{userId}")
    public ResultBean getUserById(@PathVariable Integer userId) {
        return ResultBean.ok(iUserSV.queryByUserId(userId));
    }

//    /**
//     * @Author Dk
//     * @Description 查询所有用户
//     * @Date 10:59 2018/12/4
//     * @Param []
//     * @return com.bttc.HappyGraduation.common.ResultBean
//     **/
//    @GetMapping(value="${apiVersion1}/users")
//    public ResultBean queryAllUser(){
//        return ResultBean.ok(iUserSV.getAllUsers());
//
//    }

   /**
    * @Author Dk
    * @Description 注册用户
    * @Date 10:59 2018/12/4
    * @Param [userVO]
    * @return com.bttc.HappyGraduation.common.ResultBean
    **/
    @PostMapping("${apiVersion1}/users")
    public ResultBean addUser(@RequestBody UserVO userVO) throws BusinessException {
        iUserSV.addUser(userVO);
        return ResultBean.ok(null);
    }

    /**
     * @Author Dk
     * @Description 更新用户信息
     * @Date 11:00 2018/12/4
     * @Param [userId, userVO]
     * @return com.bttc.HappyGraduation.common.ResultBean
     **/
    @PutMapping("${apiVersion1}/users/{userId}")
    public ResultBean updateUser(@PathVariable Integer userId, @RequestBody UserVO userVO) throws BusinessException {
        iUserSV.updateUser(userId, userVO);
        return ResultBean.ok(null);
    }

    /**
     * @Author Dk
     * @Description 重置用户密码
     * @Date 11:00 2018/12/4
     * @Param [userVO]
     * @return com.bttc.HappyGraduation.common.ResultBean
     **/
    @PutMapping("${apiVersion1}/users/password-resetting")
    public ResultBean resetByPassword(@RequestBody UserVO userVO) throws BusinessException {
        iUserSV.updateUser(userVO);
        return ResultBean.ok(null);
    }

    /**
     * @Author Dk
     * @Description 发送验证码邮件并保存验证码记录表
     * @Date 11:00 2018/12/4
     * @Param [verifyCodeRecordVO]
     * @return com.bttc.HappyGraduation.common.ResultBean
     **/
    @PostMapping(value = "${apiVersion1}/verify-codes")
    public ResultBean sendVerifyCodeByEmail(@RequestBody VerifyCodeRecordVO verifyCodeRecordVO) throws BusinessException {
        return ResultBean.ok(iVerifyCodeRecordSV.sendVerifyCodeByEmail(verifyCodeRecordVO));
    }

    /**
     * @Author Dk
     * @Description 验证验证码是否正确
     * @Date 11:00 2018/12/4
     * @Param [verifyCodeId, verifyCode, email]
     * @return com.bttc.HappyGraduation.common.ResultBean
     **/
    @PostMapping(value = "${apiVersion1}/verification-codes/validations")
    public ResultBean validateEmailByVerifyCode(@RequestBody VerifyCodeValidationVO verifyCodeValidationVO) throws BusinessException{
        iVerifyCodeRecordSV.validateEmailByVerifyCode(verifyCodeValidationVO);
        return ResultBean.ok(null);
    }

}
