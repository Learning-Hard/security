package io.github.learninghard.security.core.validate.service.impl;

import io.github.learninghard.security.core.validate.service.AbstractValidateCodeProcesser;
import io.github.learninghard.security.core.validate.vo.ImageCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-03
 * \* Time: 0:23
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 图片验证码处理器
 * \
 */
@Component("imagecodeprocesser")
public class ImageCodeProcesser extends AbstractValidateCodeProcesser<ImageCode> {

    @Override
    protected void send(ServletWebRequest request, ImageCode validateCode) throws Exception {
        HttpServletResponse response = request.getResponse();
        response.setContentType("image/jpeg;charset=UTF-8");
        ImageIO.write(validateCode.getImage(), "JPEG", response.getOutputStream());
    }
}
