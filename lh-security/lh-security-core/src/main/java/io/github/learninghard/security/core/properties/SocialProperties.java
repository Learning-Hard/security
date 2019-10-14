package io.github.learninghard.security.core.properties;

import io.github.learninghard.security.core.social.qq.properties.QQProperties;
import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-10
 * \* Time: 1:13
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Data
public class SocialProperties {
    /** 授权处理url */
    private String filterProcessesUrl = "/auth";
    /** QQ配置类 */
    private QQProperties qq = new QQProperties();
}
