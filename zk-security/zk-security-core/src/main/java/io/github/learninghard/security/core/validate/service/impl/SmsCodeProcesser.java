package io.github.learninghard.security.core.validate.service.impl;

import io.github.learninghard.security.core.properties.SecurityConstants;
import io.github.learninghard.security.core.validate.service.AbstractValidateCodeProcesser;
import io.github.learninghard.security.core.validate.service.ISmsCodeSender;
import io.github.learninghard.security.core.validate.vo.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-03
 * \* Time: 0:23
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 短信验证码处理器
 * \
 */
@Component("smscodeprocesser")
public class SmsCodeProcesser extends AbstractValidateCodeProcesser<ValidateCode> {

    @Autowired
    ISmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String mobile = ServletRequestUtils.getStringParameter(request.getRequest(), SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);
        smsCodeSender.send(mobile,validateCode.getCode());
    }
}
