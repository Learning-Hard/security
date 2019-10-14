package io.github.learninghard.security.browser.controller;

import io.github.learninghard.security.browser.support.SocialUserInfo;
import io.github.learninghard.security.core.properties.SecurityConstants;
import io.github.learninghard.security.core.properties.SecurityProperties;
import io.github.learninghard.security.core.vo.RspResult;
import io.github.learninghard.security.core.vo.ServiceResult;
import io.github.learninghard.security.core.vo.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-08-28
 * \* Time: 23:03
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 身份认证请求方式判断和返回信息控制
 * \
 */
@RestController
public class SecurityController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    /** 配置类 */
    private SecurityProperties securityProperties;

    /**登陆工具类*/
    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    /**
     * 请求缓存
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 作用：
     * 未授权请求html,跳转到登陆页面
     * 非html请求,返回错误提示信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ServiceResult requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /** 根据request和resopnse拿到缓存信息 */
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("请求跳转url:" + targetUrl);
            /* 未授权请求地址为html,跳转到登陆页面 */
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }
        return new ServiceResult().setRSP(StatusCode.CODE_4000.getKey(), "引导用户进入登陆页面", null);
    }

    /**
     * 获取session中的用户对象
     *
     * @param request
     * @return
     */
    @GetMapping("/social/user")
    public ServiceResult getSocialUserInfo(HttpServletRequest request) {
        ServiceResult serviceResult = new ServiceResult();
        SocialUserInfo socialUserInfo = new SocialUserInfo();
        Connection<?> conncetion = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        if (conncetion == null) {
            return serviceResult.setRSP(StatusCode.CODE_3000.getKey(), StatusCode.CODE_3000.getDesc(), null);
        }
        socialUserInfo.setProviderId(conncetion.getKey().getProviderId());
        socialUserInfo.setProviderUserId(conncetion.getKey().getProviderUserId());
        socialUserInfo.setHeadImg(conncetion.getImageUrl());
        socialUserInfo.setNickname(conncetion.getDisplayName());
        serviceResult.setRSP(StatusCode.DEFAULT.getKey(), StatusCode.DEFAULT.getDesc(), socialUserInfo);
        return serviceResult;
    }

    /**
     * Session过期处理请求
     * @return
     */
    @RequestMapping("/session/invalid")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public RspResult sessionInvalid(){
        RspResult result = new RspResult(StatusCode.CODE_4000.getKey(),StatusCode.CODE_4000.getDesc(),null);
        return result;
    }



}
