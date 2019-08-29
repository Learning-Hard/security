package io.github.learninghard.security.core;

import io.github.learninghard.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-29
 * \* Time: 0:17
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)/*这个注解用来注入配置类*/
public class SecurityCoreConfig {
}
