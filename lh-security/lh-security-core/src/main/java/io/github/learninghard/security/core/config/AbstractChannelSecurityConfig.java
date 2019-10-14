package io.github.learninghard.security.core.config;

import io.github.learninghard.security.core.properties.SecurityConstants;
import io.github.learninghard.security.core.session.MyConcurrentSessionControlAuthenticationStrategy;
import io.github.learninghard.security.core.session.MySessionRegistryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-04
 * \* Time: 14:39
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 抽象登陆配置类
 * \
 * @author Learning-Hard
 */
public abstract class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    protected AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    MySessionRegistryImpl mySessionRegistry;


    /**
     * 抽取公共的登陆配置
     * 表单登陆
     * 表单登陆默认地址
     *
     * @param http
     * @throws Exception
     */
    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        /**
         * httpbasic:以浏览器弹出框的形式进行认证校验
         * formLogin:以表单页面的方式进行校验
         */
        http.formLogin()
            /**表单登陆默认页面*/
            .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
            /**表单登陆默认处理请求URL*/
            .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler);
    }
}
