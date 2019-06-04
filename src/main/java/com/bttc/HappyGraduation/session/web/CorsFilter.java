package com.bttc.HappyGraduation.session.web;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**  
* <p>Title: CorsFilter</p>  
* <p>Description: 解决跨域问题过滤器</p>  
* @author liuxf6  
* @date 2018年4月23日  
*/
@Component
public class CorsFilter implements Filter {
  
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CorsFilter.class);  
  
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest reqs = (HttpServletRequest) req;
  
        response.setHeader("Access-Control-Allow-Origin",reqs.getHeader("Origin"));  
        response.setHeader("Access-Control-Allow-Credentials", "true");  
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");  
        response.setHeader("Access-Control-Max-Age", "3600");  
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        String url = reqs.getRequestURI().substring(reqs.getContextPath().length());
        logger.info("拦截到的url为：" + url);
        chain.doFilter(req, res);  
    }  
    
    @Override
    public void destroy() {
    	
    }
    
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("初始化中");
	}  
}  
