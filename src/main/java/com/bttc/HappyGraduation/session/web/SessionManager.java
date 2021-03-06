package com.bttc.HappyGraduation.session.web;

import com.bttc.HappyGraduation.session.pojo.po.UserPO;
import com.bttc.HappyGraduation.utils.exception.BusinessException;
import com.bttc.HappyGraduation.utils.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;


public class SessionManager {
	
	private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
	
	private SessionManager() {
		 
	}


	public static UserInfo getUserInfo() throws BusinessException {
	 	if("anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
	 		//session失效，请重新登录
	 		BusinessException.throwBusinessException(ErrorCode.SESSION_LOSE);
		}
		return (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	/**
	* <p>Title: setUserInfo</p>
	* <p>Description: 设置session信息</p>
	* @author liuxf6
	* @throws BusinessException 
	* @date 2018年7月13日
	*/
	public static void setUserInfo(UserPO user) throws BusinessException{
		UserInfo userInfo = SessionManager.getUserInfo();
		Field[] fields = user.getClass().getDeclaredFields(); //获取实体类的所有属性，返回Field数组
	    for(int i = 0 ; i < fields.length ; i++) { //遍历所有属性
	    	Field field = fields[i];
	        String name = field.getName(); //获取属性的名字
			try {
				Field declaredField = validateSessionProperty(userInfo, name);
				if(null == declaredField) {
					continue;
				}
				//获取user的get方法
				Method getMethod = user.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
				//获取属性值
				Object value = getMethod.invoke(user);
				//获取userInfo的set方法
				Class<?> typeClass = UserInfo.class.getDeclaredField(name).getType();
				Method setMethod = userInfo.getClass().getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), new Class[] { typeClass });
				setMethod.invoke(userInfo, new Object[] { value });
			} catch (Exception e) {
				if(logger.isErrorEnabled()) {
		    		logger.error(e.getMessage());
		        }
				BusinessException.throwBusinessException(ErrorCode.SESSION_SET_EXCEPTION);
			}
	    }
	}
	
	/**
	* <p>Title: setUserInfo</p>
	* <p>Description: 根据Map设置session信息</p>
	* @author liuxf6
	* @throws BusinessException 
	* @date 2018年7月19日
	*/
	public static void setUserInfo(Map<String, Object> map) throws BusinessException {
		UserInfo userInfo = SessionManager.getUserInfo();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String name = entry.getKey();
			//获取属性值
			Object value = entry.getValue();
			try {
				Field declaredField = validateSessionProperty(userInfo, name);
				if(null == declaredField) {
					continue;
				}
				//获取userInfo的set方法
				Class<?> typeClass = UserInfo.class.getDeclaredField(name).getType();
				Method setMethod = userInfo.getClass().getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), new Class[] { typeClass });
				setMethod.invoke(userInfo, new Object[] { value });
			} catch (Exception e) {
				if(logger.isErrorEnabled()) {
					logger.error(e.getMessage());
				}
				BusinessException.throwBusinessException(ErrorCode.SESSION_SET_EXCEPTION);
			}
		}
	}
	
	/**
	* <p>Title: validateSessionProperty</p>
	* <p>Description: 验证UserInfo中是否含有属性name</p>
	* @author liuxf6
	* @date 2018年8月14日
	*/
	private static Field validateSessionProperty(UserInfo userInfo,String name) {
		Field declaredField = null;
		try {
			declaredField = userInfo.getClass().getDeclaredField(name);
		} catch (Exception e) {
			if(logger.isInfoEnabled()) {
				logger.info("session中没有该属性：" + name);
	        }
		}
		return declaredField;
	}
	/**
	 * <p>Title: setDefaultSchedule</p>
	 * <p>Description: 设置系统默认用户</p>
	 * @author kaiz
	 * @date 2018年9月17日
	 */
	public static void setDefaultUser(){
		UserInfo userInfo = new UserInfo();
		userInfo.setName("蜂云");
		userInfo.setUserId(1);
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userInfo, "123456", null));
	}

	/**
	 * <p>Title: setDefaultGitlab</p>
	 * <p>Description: 设置Gitlab默认用户</p>
	 * @author kaiz
	 * @date 2018年9月17日
	 */
	public static void setDefaultGitlab(){
		UserInfo userInfo = new UserInfo();
		userInfo.setName("自动触发");
		userInfo.setUserId(91);
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userInfo, "123456", null));
	}



}
