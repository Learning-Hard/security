package io.github.learninghard.security.core.config;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-10
 * \* Time: 11:32
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @author Learning-Hard
 */
public class SecuritySocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    public SecuritySocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    /**
     * 自定义过滤器
     *
     * @param object
     * @param <T>
     * @return
     */
    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessesUrl);
        return (T) filter;
    }
}
