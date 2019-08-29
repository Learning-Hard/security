package io.github.learninghard.security.core.properties;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-29
 * \* Time: 0:14
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 登陆页面信息
 * \
 */
public class BrowserProperties {

    /* 登陆页面默认值 */
    private String loginPage = "/signIn.html";

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}
