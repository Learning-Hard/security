package io.github.learninghard.security.core.validate.vo;

import io.github.learninghard.security.core.properties.SecurityConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-30
 * \* Time: 9:21
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 验证码对应关系
 * \
 */
@Getter/* Get方法 */
@AllArgsConstructor /* 全参构造函数 */
public enum ValidateCodeType {

    /**
     * 短信验证码
     */
    SMS {
        @Override
        public String getValue() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getValue() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    },
    /**
     * 邮箱验证码
     */
    EMAIL {
        @Override
        public String getValue() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_EMAIL;
        }
    };

    public abstract String getValue();
}
