package io.github.learninghard.security.core.validate.service;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-02
 * \* Time: 1:35
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 发送短信验证码
 * \
 */
public interface ISmsCodeSender {

    /**
     * 发送短信
     *
     * @param mobile
     * @param code
     */
    void send(String mobile, String code);
}
