package io.github.learninghard.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-29
 * \* Time: 0:13
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 登陆框架配置信息，统一前缀 zk.security
 * \
 */
@ConfigurationProperties(prefix = "zk.security")/*使用ConfigurationProperties声明配置类*/
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
