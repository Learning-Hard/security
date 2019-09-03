package io.github.learninghard.security.core.validate.filter;

import io.github.learninghard.security.core.properties.SecurityProperties;
import io.github.learninghard.security.core.validate.exception.ValidateCodeException;
import io.github.learninghard.security.core.validate.vo.ValidateCodeType;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-29
 * \* Time: 15:24
 * \* To change this template use File | Settings | File Templates.
 * \* Description:  验证码校验过滤器
 * \
 */
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {/* 一次请求只通过一次filter，而不需要重复执行 */

    /**  Session操作工具  */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**  验证码校验失败处理器  */
    @Setter
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**  SpringSecurity配置信息  */
    @Setter
    private SecurityProperties securityProperties;

    /**  存储需要进行参数校验的url  */
    Set<String> urls = new HashSet<>();

    /**
     * 路径匹配规则
     */
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 初始化bean的时候执行这个方法
     * 将参数中设置需要拦截的url转为集合
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        String[] strings = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getValidateUrl(), ",");
        urls.addAll(Arrays.asList(strings));
        urls.add("/authentication/form");
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /**
         * 校验url和规则是否匹配
         */
        boolean flag = false;
        for (String url : urls){
            if(antPathMatcher.match(url,request.getRequestURI())){
                flag = true;
                break;
            }
        }

        if(flag){
            try {
                //todo 待补充
//                validate(new ServletWebRequest(request));
                logger.info("请求参数中验证码是:"+request.getParameter("validatecode"));
                if(request.getParameter("validatecode").equals((String) sessionStrategy.getAttribute(new ServletWebRequest(request), "SESSION_KEY_VALIDATE_KEY"))){
                    logger.info("验证码校验成功");
                }else{
                    throw new ValidateCodeException("验证码校验失败");
                }
            } catch (ValidateCodeException exception) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 校验哪些url需要需要通过参数校验,url根据配置文件中配置的
     * @param request
     * @return
     */
    private ValidateCodeType validate(ServletWebRequest request) {
        ValidateCodeType validateCodeType = null;
        /* 获取session中的验证码 */
        String validateCode = (String) sessionStrategy.getAttribute(request, ValidateCodeFilter.ALREADY_FILTERED_SUFFIX);
        return null;
    }

}
