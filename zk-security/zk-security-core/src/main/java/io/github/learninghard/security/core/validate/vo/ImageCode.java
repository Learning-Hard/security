package io.github.learninghard.security.core.validate.vo;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-29
 * \* Time: 23:02
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 图形验证码
 * \
 */
@Data
public class ImageCode extends ValidateCode {
    /** 图片 */
    private BufferedImage image;

    /**
     * 指定过期时间
     * @param image 图片流
     * @param code 验证码
     * @param expireTime 过期时间
     */
    public ImageCode(BufferedImage image, String code , LocalDateTime expireTime) {
        super(code,expireTime);
        this.image = image;
    }

    /**
     * 多长时间后过期
     * @param image 图片流
     * @param code 验证码
     * @param seconds 多少秒后过期
     */
    public ImageCode(BufferedImage image, String code ,Long seconds){
        /* 父类构造函数必须在第一行 */
        super(code, seconds);
        this.image = image;
    }
}
