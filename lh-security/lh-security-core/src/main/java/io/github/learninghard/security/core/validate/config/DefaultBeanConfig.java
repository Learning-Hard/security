package io.github.learninghard.security.core.validate.config;

import io.github.learninghard.security.core.properties.SecurityProperties;
import io.github.learninghard.security.core.validate.service.ISmsCodeSender;
import io.github.learninghard.security.core.validate.service.IValidateCodeGenerator;
import io.github.learninghard.security.core.validate.service.impl.DefaultImageCodeGenerator;
import io.github.learninghard.security.core.validate.service.impl.DefaultSmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-31
 * \* Time: 1:32
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 默认注入对象配置
 * \
 * \ @ConditionalOnMissingBean 判单是否初始化Bean
 * \ 1.根据Bean对象名判断
 * \ 2.根据类型判断
 * \
 */
@Configuration
public class DefaultBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    /** 不存再名为imageCodeGenerator的Bean对象时加载下面配置文件 */
    public IValidateCodeGenerator imageCodeGenerator() {
        /** 加载默认图片验证码生成器 */
        DefaultImageCodeGenerator defaultImageCodeGenerator = new DefaultImageCodeGenerator();
        defaultImageCodeGenerator.setSecurityProperties(securityProperties);
        return defaultImageCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(ISmsCodeSender.class)
    /** 不存ISmsCodeSender的实现类的对象时加载下面配置文件 */
    public ISmsCodeSender iSmsCodeSender() {
        /** 加载默认图片验证码生成器 */
        DefaultSmsCodeSender defaultSmsCodeSender = new DefaultSmsCodeSender();
        return defaultSmsCodeSender;
    }
}
