package com.bttc.HappyGraduation.session.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals("application/json; charset=UTF-8")
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {

            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            HashMap<String,String> userMap = new HashMap();
            try (InputStream is = request.getInputStream()) {
                UserInfo authenticationBean = mapper.readValue(is, UserInfo.class);
                if(request.getRequestURI().contains("login/bomc")){
                    userMap.put("type","bomc");
                }else{
                    userMap.put("password",authenticationBean.getPassword());
                    userMap.put("type","account");
                }
                authRequest = new UsernamePasswordAuthenticationToken(authenticationBean.getUsername(), userMap);
            } catch (IOException e) {
                logger.error(e.getMessage());
                authRequest = new UsernamePasswordAuthenticationToken("", userMap);
            } finally {
                setDetails(request, authRequest);
            }
            return this.getAuthenticationManager().authenticate(authRequest);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }
}