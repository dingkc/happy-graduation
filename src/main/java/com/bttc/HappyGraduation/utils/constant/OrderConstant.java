package com.bttc.HappyGraduation.utils.constant;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author kaiz
 * @date 19:17 2019/3/14.
 */
public class OrderConstant {
    private OrderConstant(){}


    public enum OrderState {
        CANCEL(1,"已取消"),
        WAIT_USE(2,"待使用"),
        USED(3,"已使用");

        private Integer value;
        private String name;

        OrderState(Integer value, String name) {
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
