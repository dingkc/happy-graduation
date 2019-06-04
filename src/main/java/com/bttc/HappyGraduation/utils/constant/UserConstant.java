package com.bttc.HappyGraduation.utils.constant;

public class UserConstant {

    private UserConstant() {
    }

    public static final String VERIFY_CODE_ID = "验证码编号";

    public static final String SYSTEM_ROLE_ID = "系统角色编号";

    public enum CodeSendType {
        /*
         * <p>Title: 验证码发送类型</p>
         * <p>Description: </p>
         * @Author: Dk
         * @Date: 2018/12/19 12:02
         **/
        REGISTER(1,"创建账号"),
        RESET(2,"重置密码"),
        BINDING(3,"绑定邮箱");

        private Integer value;
        private String name;

        CodeSendType(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    public enum userQueryType {
        /*
         * <p>Title: 查询类型</p>
         * <p>Description: </p>
         * @Author: Dk
         * @Date: 2018/12/19 12:02
         **/
        ALL(0,"查询全部用户"),
        CURRENT(1,"查询当前用户");

        private Integer value;
        private String name;

        userQueryType(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }
}
