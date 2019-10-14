package io.github.learninghard.security.core.social.config;

import io.github.learninghard.security.core.config.SecuritySocialConfigurer;
import io.github.learninghard.security.core.properties.SecurityProperties;
import io.github.learninghard.security.core.social.qq.connect.QQConnectionFactory;
import io.github.learninghard.security.core.social.qq.properties.QQProperties;
import io.github.learninghard.security.core.social.view.MyConnectView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.config.support.JdbcConnectionRepositoryConfigSupport;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-06
 * \* Time: 9:32
 * \* To change this template use File | Settings | File Templates.
 * \* Description: SpringSocical基本配置
 * \
 *
 * @author Learning-Hard
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    /**
     * 该回调方法用来允许应用添加需要支持的社交网络对应的连接工厂的实现。
     *
     * @param connectionFactoryConfigurer
     * @param environment
     */
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        QQProperties qqConfig = securityProperties.getSocial().getQq();
        connectionFactoryConfigurer.addConnectionFactory(new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret()));
    }

    /**
     * 该回调方法返回一个 org.springframework.social.UserIdSource 接口的实现对象，用来惟一标识当前用户。
     *
     * @return
     */
    @Override
    public UserIdSource getUserIdSource() {
        return new UserIdSource() {
            @Override
            public String getUserId() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication == null) {
                    throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
                }
                return authentication.getName();
            }
        };
    }

    /**
     * 该回调方法返回一个 org.springframework.social.connect.UsersConnectionRepository 接口的实现对象，用来管理用户与社交网络服务提供者之间的对应关系
     *
     * @param connectionFactoryLocator
     * @return
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        /** 未注册用户登陆自动注册 */
        if (connectionSignUp != null) {
            jdbcUsersConnectionRepository.setConnectionSignUp(connectionSignUp);
        }
        /** 设置表名前缀 */
        jdbcUsersConnectionRepository.setTablePrefix("zk_");
        return jdbcUsersConnectionRepository;
    }

    /**
     * 修改SocialAuthenticaitonFilter中的默认配置
     *
     * @return
     * @see org.springframework.social.security.SocialAuthenticationFilter
     */
    @Bean
    public SpringSocialConfigurer socialSecurityConfig() {
        /** 自定义社交登陆拦击url */
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        SecuritySocialConfigurer configurer = new SecuritySocialConfigurer(filterProcessesUrl);
        /** 自定义社交登陆注册页面 */
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        return configurer;
    }

    /**
     * 登陆工具类
     *
     * @param connectionFactoryLocator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));

    }

    /**
     *
     * 将ConnectController交给Spring容器管理
     * SpringBoot2.X 已经将SpringSocial的自动注解和依赖管理删掉了
     * @see <a href="https://www.oschina.net/translate/spring-boot-2-0-migration-guide?print">
     * @param connectionFactoryLocator
     * @param connectionRepository
     * @return
     */
    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }

    // 我这里解绑和绑定用的一个对象，以model中有没有connections来区分解绑还是绑定
    @Bean({"connect/qqConnected", "connect/qqConnect"})
    public MyConnectView connectView(){
        return new MyConnectView();
    }

}
