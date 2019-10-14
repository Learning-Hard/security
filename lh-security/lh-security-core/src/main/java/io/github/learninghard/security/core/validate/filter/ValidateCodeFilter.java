package io.github.learninghard.security.core.validate.filter;

import io.github.learninghard.security.core.properties.SecurityConstants;
import io.github.learninghard.security.core.properties.SecurityProperties;
import io.github.learninghard.security.core.validate.exception.ValidateCodeException;
import io.github.learninghard.security.core.validate.service.IValidateCodeProcesser;
import io.github.learninghard.security.core.validate.vo.ValidateCode;
import io.github.learninghard.security.core.validate.vo.ValidateCodeType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-29
 * \* Time: 15:24
 * \* To change this template use File | Settings | File Templates.
 * \* Description:  通用验证码校验器
 * \
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {/* 一次请求只通过一次filter，而不需要重复执行 */
    /**
     * Session操作工具
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 验证码校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * SpringSecurity配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 存储需要进行参数校验的url
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    /**
     * 路径匹配规则
     */
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 初始化bean的时候执行这个方法
     *
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet(){
        /** 图片验证码 */
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getCode().getValidateImageCodeUrl(), ValidateCodeType.IMAGE);
        /** 短信验证码 */
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getValidateSmsCodeUrl(), ValidateCodeType.SMS);
    }

    /**
     * 将配置文件中url分割添加到map
     * @param validateUrl
     * @param validateCodeType
     */
    private void addUrlToMap(String validateUrl, ValidateCodeType validateCodeType) {
        if (StringUtils.isNotBlank(validateUrl)) {
            String[] urls = StringUtils.split(validateUrl, ",");
            for (String url : urls) {
                urlMap.put(validateUrl, validateCodeType);
            }
        }
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeType validateCodeType = getValidateCodeType(new ServletWebRequest(request));
        if (validateCodeType != null) {
            try {
                logger.info("========执行验证码校验逻辑========");
                validate(new ServletWebRequest(request),validateCodeType);
                logger.info("========验证码校验成功========");
            } catch (ValidateCodeException exception) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 根据访问的url到map拿到验证码类型
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        ValidateCodeType validateCodeType = null;
        String requestURI = request.getRequest().getRequestURI();
        for (String url : urlMap.keySet()) {
            if (antPathMatcher.match(url, requestURI)) {
                validateCodeType = urlMap.get(url);
                break;
            }
        }
        return validateCodeType;
    }


    /**
     * 验证码校验逻辑
     * @param request
     * @param validateCodeType
     */
    public void validate(ServletWebRequest request,ValidateCodeType validateCodeType) {

        /** 缓存中的验证码*/
        String sessionKey = IValidateCodeProcesser.SESSION_KEY_PREFIX + validateCodeType.getValue().toUpperCase();
        ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(request, sessionKey);

        /** 请求中的验证码*/
        String codeInRequest;
        try {
            /** 请求中的验证码 */
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),validateCodeType.getValue());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(validateCodeType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(validateCodeType + "验证码不存在");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new ValidateCodeException(validateCodeType + "验证码已过期");
        }

        if (!StringUtils.equalsIgnoreCase(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(validateCodeType + "验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, sessionKey);
    }
}
