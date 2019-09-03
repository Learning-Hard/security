package io.github.learninghard.security.core.validate.controller;

import io.github.learninghard.security.core.validate.service.IValidateCodeProcesser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-29
 * \* Time: 14:15
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 校验码
 * \
 * @author Learning-Hard
 */
@RestController
@RequestMapping("/validatecode")
public class ValidateCodeController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Map<String, IValidateCodeProcesser> validateCodeProcesserMap;

    /**
     * 发送验证码
     * @param request
     * @param response
     * @param type 根据url判断发送验证码类型，默认是image：图片验证码； sms：是短信验证码
     * @throws Exception
     */
    @GetMapping("/{type}")
    public void sendCode(HttpServletRequest request, HttpServletResponse response,@PathVariable String type) throws Exception {
        //TODO 这里看看有没有优化的方法，避免代码写死
        String processername = type + "codeprocesser";
        IValidateCodeProcesser iValidateCodeProcesser = validateCodeProcesserMap.get(processername);
        if (iValidateCodeProcesser == null) {
            System.out.println("不存在");
            logger.info("处理器" + processername + "不存在");
            return;
        }
        iValidateCodeProcesser.create(new ServletWebRequest(request, response));
    }

}

