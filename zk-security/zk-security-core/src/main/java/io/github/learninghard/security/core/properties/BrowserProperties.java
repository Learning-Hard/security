package io.github.learninghard.security.core.properties;

import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-29
 * \* Time: 0:14
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 登陆页配置，记住我功能配置
 * \
 */
@Data
public class BrowserProperties {

    /**
     * 登陆页面默认值
     */
    private String loginPage = "/signIn.html";

    /**
     * 记住我功能时间长度
     * 默认值：5分钟
     */
    private int rememberMeSeconds = 3000;


}
