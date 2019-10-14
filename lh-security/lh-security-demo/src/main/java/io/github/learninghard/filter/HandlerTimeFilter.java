package io.github.learninghard.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning Hard
 * \* Date: 2019-08-23
 * \* Time: 10:12
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 计算方法处理时间 直接添加Component 注解就可以将过滤器添加到过滤器链
 * \
 */
//@Component
public class HandlerTimeFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(HandlerTimeFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("过滤器--方法处理时间过滤器初始化...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        logger.info("过滤器--方法执行耗时:" + (System.currentTimeMillis() - start) + "ms");
        /**
         * filter 只能得到原始的request和response对象不能获取是哪个controller执行的
         */
    }

    @Override
    public void destroy() {
        logger.info("过滤器--方法处理时间过滤器销毁");
    }
}
