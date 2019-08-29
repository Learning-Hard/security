package io.github.learninghard.security.core.validate.controller;

import io.github.learninghard.security.core.validate.code.ValidateCodeImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-29
 * \* Time: 14:15
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@RestController
@RequestMapping("/validatecode")
public class ValidateCodeController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/image")
    public void image(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /**
         * 解决输出图片显示为乱码的问题
         */
        response.setContentType("image/png;charset=UTF-8");
        ValidateCodeImage validateCode = new ValidateCodeImage(160,40,6,150);
        ServletOutputStream outputStream = response.getOutputStream();
        logger.info("验证码:" + validateCode.getCode());
        validateCode.write(outputStream);
        outputStream.flush();
    }

}
