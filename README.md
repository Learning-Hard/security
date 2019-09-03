# Spring Security 开发企业级认证与授权




## 一、验证码校验功能实现

### 1、自定义图片验证码校验器

- 实现`IValidateCodeGenerator`接口
- Bean对象名设置为`imageCodeGenerator`, 系统会默认查找imageCodeGenerator对象，找不到就会使用默认的图片验证码生成器


示例：【系统默认实现】
```$java
package io.github.learninghard.security.core.validate.service.impl;

import io.github.learninghard.security.core.properties.SecurityProperties;
import io.github.learninghard.security.core.validate.vo.ImageCode;
import io.github.learninghard.security.core.validate.service.IValidateCodeGenerator;
import lombok.Setter;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-31
 * \* Time: 1:20
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 系统默认图片验证码生成器
 * \
 */

@Compoment("imageCodeGenerator")
public class DefaultImageCodeGenerator implements IValidateCodeGenerator {

    /**
     * SecurityProperties配置类
     */
    @Setter
    private SecurityProperties securityProperties;

    @Override
    public ImageCode generate(ServletWebRequest request) {

        // 图片宽度
        int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width", securityProperties.getCode().getWidth());
        // 图片的高度。
        int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height", securityProperties.getCode().getHeight());
        // 验证码字符个数
        int codeCount = ServletRequestUtils.getIntParameter(request.getRequest(), "codeCount", securityProperties.getCode().getCodeCount());
        // 验证码干扰线数
        int lineCount = ServletRequestUtils.getIntParameter(request.getRequest(), "lineCount", securityProperties.getCode().getLineCount());

        BufferedImage bufferedImage;
        String imageCode = "";

        // 验证码范围,去掉0(数字)和O(拼音)容易混淆的(小写的1和L也可以去掉,大写不用了)
        char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        int x = 0, fontHeight = 0, codeY = 0;
        int red = 0, green = 0, blue = 0;

        x = width / (codeCount + 2);//每个字符的宽度(左右各空出一个字符)
        fontHeight = height - 2;//字体的高度
        codeY = height - 4;

        // 图像buffer
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        // 生成随机数
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体,可以修改为其它的
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
//        Font font = new Font("Times New Roman", Font.ROMAN_BASELINE, fontHeight);
        g.setFont(font);

        for (int i = 0; i < lineCount; i++) {
            // 设置随机开始和结束坐标
            int xs = random.nextInt(width);//x坐标开始
            int ys = random.nextInt(height);//y坐标开始
            int xe = xs + random.nextInt(width / 8);//x坐标结束
            int ye = ys + random.nextInt(height / 8);//y坐标结束

            // 产生随机的颜色值，让输出的每个干扰线的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }

        // randomCode记录随机产生的验证码
        StringBuffer randomCode = new StringBuffer();
        // 随机产生codeCount个字符的验证码。
        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, (i + 1) * x, codeY);
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        // 将四位数字的验证码保存到Session中。
        imageCode = randomCode.toString();
        /* 默认写死5分钟过期 */
        return new ImageCode(bufferedImage, imageCode, (long) securityProperties.getCode().getExpireTimeIn());
    }
}

```


### 参数配置
