package io.github.learninghard.security.browser.support;

import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-19
 * \* Time: 22:12
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Data
public class SocialUserInfo {
    private String providerId;
    private String providerUserId;
    private String nickname;
    private String headImg;
}
