package io.github.learninghard.security.core.social.properties;

import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-09
 * \* Time: 19:23
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 社交抽象父类,springboot2.X 将这个类删除了
 * \
 */
@Data
public abstract class AbstractSocialProperties {
    private String appId;
    private String appSecret;
}
