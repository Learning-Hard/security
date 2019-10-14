package io.github.learninghard.security.core.social.qq.properties;

import io.github.learninghard.security.core.social.properties.AbstractSocialProperties;
import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-08
 * \* Time: 17:43
 * \* To change this template use File | Settings | File Templates.
 * \* Description: QQ社交登陆配置
 * \
 */
@Data
public class QQProperties extends AbstractSocialProperties {
    private String providerId = "qq";
}
