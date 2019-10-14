package io.github.learninghard.security.core.validate.service.impl;

import io.github.learninghard.security.core.validate.service.ISmsCodeSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-02
 * \* Time: 1:55
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 默认短信验证码发送器
 * \
 */
public class DefaultSmsCodeSender implements ISmsCodeSender {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void send(String mobile, String code) {
        logger.info("向手机号:" + mobile + "发送短信验证码是" + code);

    }
}
