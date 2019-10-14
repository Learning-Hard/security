package io.github.learninghard.security.core.social.controller;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.stereotype.Controller;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-20
 * \* Time: 16:22
 * \* To change this template use File | Settings | File Templates.
 * \* Description: SpringBoot2.X 已经将SpringSocial的自动注解和依赖管理删掉了
 *    这里实现手动注入Spring容器
 *    @see <a href="https://www.oschina.net/translate/spring-boot-2-0-migration-guide?print">
 * \
 */
//@Controller
public class CustConnectController extends ConnectController {
    public CustConnectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        super(connectionFactoryLocator, connectionRepository);
    }
}
