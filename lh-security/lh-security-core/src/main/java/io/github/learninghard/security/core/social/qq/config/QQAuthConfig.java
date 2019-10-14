
package io.github.learninghard.security.core.social.qq.config;

import io.github.learninghard.security.core.social.qq.properties.QQProperties;
import io.github.learninghard.security.core.properties.SecurityProperties;
import io.github.learninghard.security.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.stereotype.Component;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-10
 * \* Time: 0:43
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */

@Component
public class QQAuthConfig {

    @Autowired
    private SecurityProperties securityProperties;

    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
    }


}
