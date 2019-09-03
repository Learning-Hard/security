package io.github.learninghard.security.core.validate.service;

import io.github.learninghard.security.core.validate.exception.ValidateCodeException;
import io.github.learninghard.security.core.validate.vo.ValidateCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-02
 * \* Time: 16:10
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public abstract class AbstractValidateCodeProcesser<C extends ValidateCode> implements IValidateCodeProcesser {
    private Logger logger= LoggerFactory.getLogger(getClass());


    /**
     * Session操作类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 系统启动后，会收集系统中所有的{@link IValidateCodeGenerator} 接口的实现
     */
    @Autowired
    private Map<String, IValidateCodeGenerator> validateCodeGeneratorMap;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        /**
         * * 1、生成验证码
         * * 2、将验证码放入session
         * * 3、发送验证码
         * */
        C validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    /**
     * 根据请求拿到验证码类型
     *
     * @param request
     * @return
     */
    private String getPorcesserType(ServletWebRequest request) {
        return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/validatecode/");
    }

    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    private C generate(ServletWebRequest request) {

        for (String key : validateCodeGeneratorMap.keySet()) {
            logger.info("验证器名为:" + key);
        }

        String generatorName = getPorcesserType(request) + "CodeGenerator";
        IValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorMap.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 保存验证码
     */
    private void save(ServletWebRequest request, ValidateCode validateCode) {
        sessionStrategy.setAttribute(request, IValidateCodeProcesser.SESSION_KEY_PREFIX + getPorcesserType(request), validateCode.getCode());
    }


    /**
     * 发送校验码，由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

}
