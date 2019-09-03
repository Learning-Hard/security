package io.github.learninghard.security.core.validate.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-30
 * \* Time: 0:00
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 验证码校验异常
 * \
 */
public class ValidateCodeException extends AuthenticationException {
    private static final long serialVersionUID = -7285211528095468156L;
    public ValidateCodeException(String msg) {
        super(msg);
    }
}
