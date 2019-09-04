package io.github.learninghard.security.browser.config;

import io.github.learninghard.security.core.config.SmsCodeAuthenticationSecurityConfig;
import io.github.learninghard.security.core.config.ValidateCodeSecurityConfig;
import io.github.learninghard.security.core.config.AbstractChannelSecurityConfig;
import io.github.learninghard.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

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
@Component
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {

    @Autowired
    /** 配置类*/
    private SecurityProperties securityProperties;

    @Autowired
    /** 数据源*/
    private DataSource dataSource;

    @Autowired
    SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    ValidateCodeSecurityConfig validateCodeSecurityConfig;

    /**
     * 指定加密算法,新版本必须指定加密算法，否则会报错
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        /** TODO 这里可以自定义加密算法,如MD5等 */
        return new BCryptPasswordEncoder();
    }

    /**
     * 记住我功能数据源配置
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//        tokenRepository.setCreateTableOnStartup(true); //自动创建数据库的脚本
        return tokenRepository;
    }

    @Resource
    private UserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        applyPasswordAuthenticationConfig(http);
        /**
         * httpbasic:以浏览器弹出框的形式进行认证校验
         * formLogin:以表单页面的方式进行校验
         */
        http.apply(validateCodeSecurityConfig)
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                /* 开启记住我功能 */
                .rememberMe()
                /* 记住我数据源绑定 */
                .tokenRepository(persistentTokenRepository())
                /* 过期时间 */
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                /* 拿到userdetails */
                .userDetailsService(userDetailsService)
                /** rember me配置完毕 */
                .and()
                .authorizeRequests()
                /* 指定请求放行 */
                .antMatchers("/css/**", "/js/**", "/validatecode/**", "/authentication/require", securityProperties.getBrowser().getLoginPage()).permitAll()
                /* 拦截所有请求 */
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }


}
