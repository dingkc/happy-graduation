package com.bttc.HappyGraduation.business.friend.constant;

/**
 * @author Dk
 * @date 20:41 2019/4/22.
 */
public class FriendConstant {
    public enum ApproveType {
        /*
         * <p>Title: ApproveType</p>
         * <p>Description: 审批状态</p>
         * @Author: Dk
         * @Date: 2019/4/16 17:40
         **/
        AGREE(1,"同意"),
        NO_PROCESS(2,"未处理");

        private Integer value;
        private String name;

        ApproveType(Integer value, String name) {
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
