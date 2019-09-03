package io.github.learninghard.security.browser.service.impl;///**

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.learninghard.security.core.vo.ServiceResult;
import io.github.learninghard.security.core.vo.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-27
 * \* Time: 22:29
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @author Learning-Hard
 */
@Component("AuthenctiationFailureHandlerImpl")
public class AuthenctiationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        logger.info("登录失败");

        if (true) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new ServiceResult().setRSP(StatusCode.CODE_4000.getKey(), "验证码校验失败", exception.getMessage())));
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }


    }

}
