package io.github.learninghard.security.core.validate.service.impl;

import io.github.learninghard.security.core.properties.SecurityProperties;
import io.github.learninghard.security.core.validate.service.IValidateCodeGenerator;
import io.github.learninghard.security.core.validate.vo.ValidateCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-31
 * \* Time: 1:20
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 系统短信验证码生成器
 * \
 */
@Component("smsCodeGenerator")
public class DefaultSmsCodeGenerator implements IValidateCodeGenerator {

    /**
     * SecurityProperties配置类
     */
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getCodeCount());
        return new ValidateCode(code, (long) securityProperties.getCode().getExpireTimeIn());
    }
}
