package io.github.learninghard.security.core.social.qq.api.impl;

import io.github.learninghard.security.core.properties.SecurityProperties;
import io.github.learninghard.security.core.social.qq.api.QQ;
import io.github.learninghard.security.core.social.qq.api.QQUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.web.client.RestTemplate;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-05
 * \* Time: 11:18
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @author Learning-Hard
 */
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    /**
     * 获取openId的Url
     */
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    /**
     * 获取UserInfo
     */
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    /**
     * 应用id,配置文件读取
     */
    private String appid;

    /**
     * 用户id,URL_GET_OPENID
     */
    private String openid;


    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @param accessToken 走完Oauth拿到token
     * @param appid
     */
    public QQImpl(String accessToken, String appid) {
        /**
         * 对accessToken参数的处理
         * 默认是将token放在请求头中
         * TokenStrategy.ACCESS_TOKEN_PARAMETER 作为参数传递
         */
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.appid = appid;


        String url = String.format(URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);
        log.info("【QQImpl】URL_GET_OPENID={} result={}", url, result);

        this.openid = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    @Autowired
    RestTemplate restTemplate;

    @Override
    public QQUserInfo getUserInfo() {
        String url = String.format(URL_GET_USERINFO,appid, openid);
        String result = getRestTemplate().getForObject(url, String.class);
        log.info("【QQImpl】URL_GET_USER_INFO={} result={}", url, result);
        QQUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(openid);
            return userInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }
}
