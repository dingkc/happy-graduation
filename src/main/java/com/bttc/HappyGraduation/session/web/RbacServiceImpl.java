package com.bttc.HappyGraduation.session.web;

import com.bttc.HappyGraduation.session.pojo.po.AuthenticationPO;
import com.bttc.HappyGraduation.session.service.interfaces.IAuthenticationSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("rbacService")
public class RbacServiceImpl implements RbacService {
	
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private IAuthenticationSV authenticationSV;
    
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;
        if (principal instanceof UserInfo) { //首先判断先当前用户是否是我们UserDetails对象。
            String code = ((UserInfo) principal).getUsername();
            Set<String> urls = new HashSet<>(); // 数据库读取 //读取用户所拥有权限的所有URL
            List<AuthenticationPO> authenticationPOList = authenticationSV.getAuthByCode(code);
            for(AuthenticationPO value : authenticationPOList){
                urls.add(value.getPath());
            }
            // 注意这里不能用equal来判断，因为有些URL是有参数的，所以要用AntPathMatcher来比较
            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }
}