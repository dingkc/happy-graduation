package com.bttc.HappyGraduation.user.constant;

/**
* <p>Title: CommonConstant</p>
* <p>Description: 公共常量类</p> 
* @author liuxf6
* @date 2018年7月9日
*/
public class CommonConstant {

	private CommonConstant(){}
	
	//状态
    public enum CommonState {
    	INVALID(0, "失效"),
        EFFECT(1, "生效");
    	
    	private Integer value;
    	private String name;
    	
		CommonState(Integer value, String name) {
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
