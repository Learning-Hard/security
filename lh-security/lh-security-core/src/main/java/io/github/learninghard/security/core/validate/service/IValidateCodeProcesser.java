package io.github.learninghard.security.core.validate.service;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-02
 * \* Time: 16:16
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 验证码处理逻辑
 * \
 */
public interface IValidateCodeProcesser {

    /**
     * 验证码存入session统一前缀
     */
    String SESSION_KEY_PREFIX= "SESSION_KEY_VALIDATE_CODE_";

    /**
     * 这个接口实现功能
     * 1、生成验证码
     * 2、将验证码放入session
     * 3、发送验证码
     */
    void create(ServletWebRequest request) throws Exception;

}
