package io.github.learninghard.security.core.validate.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-02
 * \* Time: 1:38
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 验证码通用类[短信,邮件,图片验证码主要部分]
 * \
 */
@Data
@NoArgsConstructor
public class ValidateCode implements Serializable {
    /**
     * 验证码
     */
    private String code;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 指定过期时间
     *
     * @param code       验证码
     * @param expireTime 过期时间
     */
    public ValidateCode(String code, LocalDateTime expireTime) {

        this.code = code;
        this.expireTime = expireTime;
    }

    /**
     * 多长时间后过期
     *
     * @param code    验证码
     * @param seconds 多少秒后过期
     */
    public ValidateCode(String code, Long seconds) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(seconds);
    }

    /**
     * 校验当前时间是否超过过期时间
     *
     * @return
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
