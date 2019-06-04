package com.bttc.HappyGraduation.session.web;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface RbacService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
