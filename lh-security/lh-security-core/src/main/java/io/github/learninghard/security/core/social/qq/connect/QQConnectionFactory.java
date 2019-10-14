package io.github.learninghard.security.core.social.qq.connect;

import io.github.learninghard.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-06
 * \* Time: 9:27
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {
    public QQConnectionFactory(String providerId, String appid, String appSecret) {
        super(providerId, new QQServiceProvider(appid, appSecret), new QQAdapter());
    }
}
