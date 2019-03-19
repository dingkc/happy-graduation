package com.bttc.HappyGraduation.session.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationProvider provider;  //注入我们自己的AuthenticationProvider
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailHander myAuthenticationFailHander;
    @Autowired
    private RestAuthenticationAccessDeniedHandler restAuthenticationAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/remote/**", "/**.html", "/**.js", "/**.css", "/**/*.png","/**/*.jpg", "/*.ico", "/osrdc/**","/druid/**").permitAll()  //这就表示 /index这个页面不需要权限认证，所有人都可以访问
                .antMatchers(HttpMethod.GET,
                        "/api/*/roles",                         //查询系统角色接口不需要权限验证
                        "/api/*/searchOnline",  //演示临时修改，后期恢复
                        "/api/*/onlineDocumentPictures",  //演示临时修改，后期恢复
                        "/api/*/onlineDocumentInfos/*",  //演示临时修改，后期恢复
                        "/api/*/onlineDocumentInfos-tree",  //演示临时修改，后期恢复
                        "/api/*/onlineDocumentType"  //演示临时修改，后期恢复
                ).permitAll()
                .antMatchers(HttpMethod.POST,
                        "/api/*/verification-codes/validations" ,       //邮箱验证码校验接口不需要权限认证
                        "/api/*/gitlab-users/validations",       //gitlab账号密码校验接口不需要权限认证
                		"/api/*/users", //用户注册接口不需要权限认证
                		"/api/*/verification-codes/sends"        //邮箱验证码发送接口不需要权限认证
                		).permitAll() 
                .antMatchers(HttpMethod.PUT,
                		"/api/*/users/password-resetting").permitAll()//用户密码重置接口不需要权限认证
                .antMatchers(
                        "/actuator/*").permitAll()
                //必须经过认证以后才能访问
                .and()
                .authorizeRequests().anyRequest().authenticated()
                 //自定义403
                .and().logout().logoutUrl("/api/*/users-exit").deleteCookies("JSESSIONID").logoutSuccessUrl("/api/v1/users-exit-success").permitAll()  //配置登出，以及登出成功后，自动跳转路径
                .and().csrf().disable();//关闭打开的csrf（跨域请求伪造）保护
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);


    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(myAuthenticationFailHander);
//        filter.setFilterProcessesUrl("/api/login/account");
        filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/*/login/*", "POST"));
        filter.setPostOnly(true);
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }

}