package io.github.learninghard.security.browser;

import io.github.learninghard.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-27
 * \* Time: 22:29
 * \* To change this template use File | Settings | File Templates.
 * \* Description: SpringSecurity登陆配置
 * \
 *
 * @author Learning-Hard
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired/*配置类*/
    private SecurityProperties securityProperties;

    /**
     * 指定加密算法,新版本必须指定加密算法，否则会报错
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        /**
         * TODO 这里可以自定义加密算法
         */
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * httpbasic:以浏览器弹出框的形式进行认证校验
         * formLogin:以表单页面的方式进行校验
         */
        http.formLogin()
                /*自定义登陆页面*/
                .loginPage("/authentication/require")
                /*登陆处理请求*/
                .loginProcessingUrl("/authentication/form")
                .and()
                .authorizeRequests()
                /* 指定请求放行 */
                .antMatchers("/authentication/require", "/css/**", "/js/**", securityProperties.getBrowser().getLoginPage()).permitAll()
                /* 拦截所有请求 */
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }


}
