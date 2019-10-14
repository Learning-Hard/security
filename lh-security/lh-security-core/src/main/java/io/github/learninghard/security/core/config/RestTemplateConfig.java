package io.github.learninghard.security.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning-Hard
 * \* Date: 2019-09-05
 * \* Time: 14:13
 * \* To change this template use File | Settings | File Templates.
 * \* Description: HTTP请求模板类
 * \
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(50000);//单位为ms
        factory.setConnectTimeout(50000);//单位为ms
        return factory;
    }

//    @Bean
//    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
//        return new RestTemplate(factory);
//    }
//
//    @Bean
//    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
//        OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();
//        factory.setReadTimeout(50000);//单位为ms
//        factory.setConnectTimeout(50000);//单位为ms
//        return factory;
//    }
}
