package io.github.learninghard.security.core.validate.service;

import io.github.learninghard.security.core.validate.exception.ValidateCodeException;
import io.github.learninghard.security.core.validate.vo.ImageCode;
import io.github.learninghard.security.core.validate.vo.ValidateCode;
import io.github.learninghard.security.core.validate.vo.ValidateCodeType;
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
 *
 * @author Learning-Hard
 */
public abstract class AbstractValidateCodeProcesser<C extends ValidateCode> implements IValidateCodeProcesser {
    private Logger logger = LoggerFactory.getLogger(getClass());

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
        saveValidateCode(request, validateCode);
        sendValidateCode(request, validateCode);
    }

    /**
     * 根据请求拿到验证码类型
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        ValidateCodeType validateCodeType;
        String type = StringUtils.substringAfter(request.getRequest().getRequestURI(), "/validatecode/").toUpperCase();
        switch (type) {
            case "SMS":
                validateCodeType = ValidateCodeType.SMS;
                break;
            case "IMAGE":
                validateCodeType = ValidateCodeType.IMAGE;
                break;
            case "EMAIL":
                validateCodeType = ValidateCodeType.EMAIL;
                break;
            default:
                validateCodeType = null;
        }
        return validateCodeType;
    }

    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    private C generate(ServletWebRequest request) {
        String generatorName = getValidateCodeType(request).getValue() + "Generator";
        IValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorMap.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.generate(request);
    }

    /**
     * 保存验证码
     */
    private void saveValidateCode(ServletWebRequest request, ValidateCode validateCode) {
        /**
         * 图片验证码中存放了有图片信息,序列化的过程中会出错
         *
         * 操作是去掉图片验证码的图片信息
         */
        if(validateCode instanceof ImageCode){
            ValidateCode SerializableValidateCode = new ValidateCode(validateCode.getCode(),validateCode.getExpireTime());
            validateCode = SerializableValidateCode;
        }
        sessionStrategy.setAttribute(request, IValidateCodeProcesser.SESSION_KEY_PREFIX + getValidateCodeType(request).getValue().toUpperCase(), validateCode);
    }


    /**
     * 发送校验码,由子类具体实现,实现方式不一样
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void sendValidateCode(ServletWebRequest request, C validateCode) throws Exception;

}
