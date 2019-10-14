package io.github.learninghard.security.core.validate.service;

import io.github.learninghard.security.core.validate.vo.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-31
 * \* Time: 1:11
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 验证码接口
 * \
 */
public interface IValidateCodeGenerator {

    /**
     * 生成图片验证码
     *
     * @param request
     * @return
     */
    ValidateCode generate(ServletWebRequest request);
}
