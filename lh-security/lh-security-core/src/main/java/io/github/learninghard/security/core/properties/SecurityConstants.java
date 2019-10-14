package io.github.learninghard.security.core.properties;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-03
 * \* Time: 0:34
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 参数定义常量
 * \
 */
public class SecurityConstants {
    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

    /**
     * 当请求需要身份认证时，默认跳转的url
     */
    public static final String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

    /**
     * 默认的用户名密码登录请求处理url
     */
    public static final String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";

    /**
     * 默认的手机号登录请求处理url
     */
    public static final String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/authentication/mobile";

    /**
     * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_CODE_SMS ="smsCode";

    /**
     * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_CODE_IMAGE ="imageCode";

    /**
     * 验证邮箱验证码时，http请求中默认的携带邮箱验证码信息的参数的名称
     */
    public static final String DEFAULT_PARAMETER_NAME_CODE_EMAIL ="emailCode";

    /**
     * 自动注册新用户默认密码
     */
    public static final String DEFAULT_AUTO_REGISTER_USER_PASSWORD ="123456";

}
