package io.github.learninghard.security.browser.config;

import io.github.learninghard.security.core.config.AbstractChannelSecurityConfig;
import io.github.learninghard.security.core.config.SmsCodeAuthenticationSecurityConfig;
import io.github.learninghard.security.core.config.ValidateCodeSecurityConfig;
import io.github.learninghard.security.core.properties.SecurityConstants;
import io.github.learninghard.security.core.properties.SecurityProperties;
import io.github.learninghard.security.core.session.ExpiredSessionInformationStrategy;
import io.github.learninghard.security.core.session.MyConcurrentSessionControlAuthenticationStrategy;
import io.github.learninghard.security.core.session.MySessionRegistryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.stereotype.Component;

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

    @Autowired
    private SpringSocialConfigurer securitySocialConfigurer;

    @Autowired
    private UserDetailsService userDetailsServiceImpl;

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

    @Autowired
    private SessionRegistry sessionRegistry;


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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        applyPasswordAuthenticationConfig(http);

        http.sessionManagement().sessionAuthenticationStrategy(new MyConcurrentSessionControlAuthenticationStrategy(sessionRegistry));

        /**
         * httpbasic:以浏览器弹出框的形式进行认证校验
         * formLogin:以表单页面的方式进行校验
         */
        http.apply(validateCodeSecurityConfig)
            .and()
            .apply(smsCodeAuthenticationSecurityConfig)
            .and()
            .apply(securitySocialConfigurer)
            .and()
            /** 开启记住我功能 */
            .rememberMe()
            /** 记住我数据源绑定 */
            .tokenRepository(persistentTokenRepository())
            /** 过期时间 */
            .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
            /** 拿到userdetails */
            .userDetailsService(userDetailsServiceImpl)
            /** rember me配置完毕 */
            .and()
            .authorizeRequests()
            /** 指定请求放行 */
            .antMatchers("/css/**", "/js/**", "/validatecode/**", SecurityConstants.DEFAULT_UNAUTHENTICATION_URL, securityProperties.getBrowser().getLoginPage(), securityProperties.getBrowser().getSignUpUrl(), "/user/register","/social/user","/session/invalid").permitAll()
            /** 拦截所有请求 */
            .anyRequest()
            .authenticated()
            .and()
            .csrf().disable();

        //session并发控制过滤器
//        http.addFilterAt(new ConcurrentSessionFilter(mySessionRegistry,sessionInformationExpiredStrategy()),ConcurrentSessionFilter.class);

        /**
         * 控制session登陆个数
         */
        http.sessionManagement()
            .invalidSessionUrl("/session/invalid")
            /** 最大session数量*/
            .maximumSessions(1)
            /** 达到最大session后阻止登陆 */
//            .maxSessionsPreventsLogin(true)
            .maxSessionsPreventsLogin(false)
                /** 设置session */
            .sessionRegistry(sessionRegistry)
            /** session失效的跳转url */
            /** Session过期策略,可以通过实现 @see org.springframework.security.web.session.SessionInformationExpiredStrategy 接口监听，session事件，从而记录登陆 */
            .expiredSessionStrategy(new ExpiredSessionInformationStrategy());
    }


}
