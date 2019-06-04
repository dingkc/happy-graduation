package com.bttc.HappyGraduation.session.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//登录失败的
@Component("myAuthenticationFailHander")
public class MyAuthenticationFailHander extends SimpleUrlAuthenticationFailureHandler {
	
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        logger.info("登录失败");
        //以Json格式返回

        Map<String,String> map=new HashMap<>();
        map.put("code", "201");
        map.put("msg", "登录失败");
        map.put("status", "error");
        map.put("type", "account");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(map));

    }
}