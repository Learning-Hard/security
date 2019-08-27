package io.github.learninghard.config;

import io.github.learninghard.filter.HandlerTimeFilter;
import io.github.learninghard.interceptor.HandlerTimeInteceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning Hard
 * \* Date: 2019-08-23
 * \* Time: 10:31
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 配置类
 * \
 */
//@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    /**
     * 注入Filter过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean registrationFilterBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        HandlerTimeFilter handlerTimeFilter = new HandlerTimeFilter();
        filterRegistrationBean.setFilter(handlerTimeFilter);
        List<String> urlList = new ArrayList<>();
        urlList.add("/*");
        filterRegistrationBean.setUrlPatterns(urlList);
        return filterRegistrationBean;
    }

    /**
     * 注入拦截器
     */
    @Autowired
    private HandlerTimeInteceptor handlerTimeInteceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handlerTimeInteceptor);
    }


}
