package io.github.learninghard.security.core.properties;

import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-29
 * \* Time: 15:58
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 验证码配置可以兼容【短信,邮箱,图片】
 * \
 */

@Data
public class ValidateCodeProperties {

    /**
     * 验证码通用的配置属性【短信,邮箱,图片】
     */
    private int codeCount = 6;/*验证码长度*/
    private Long expireTimeIn = 180L;/*多长时间过期,单位秒*/

    /**
     * 图片特有的属性
     */
    // 图片的宽度
    private int width = 160;
    // 图片的高度
    private int height = 40;
    // 验证码干扰线数
    private int lineCount = 150;

}
