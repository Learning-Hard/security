package io.github.learninghard.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning Hard
 * \* Date: 2019-08-23
 * \* Time: 11:13
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 *
 * @author Learning Hard
 */
@Component
public class HandlerTimeInteceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);

    private final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 预处理 ,方法执行之前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("拦截器--正在执行...");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        /*获取处理当前请求的 handler 信息*/
        logger.info("拦截器--执行类:" + handlerMethod.getBeanType().getName());
        logger.info("拦截器--执行方法:" + handlerMethod.getMethod().getName());
        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
        for (MethodParameter methodParameter : methodParameters) {
            logger.info("拦截器--执行参数是:" + methodParameter.getParameterName());
            /* 可以拿到执行的类、方法、执行的参数名,不能拿到传入参数 */
        }
        threadLocal.set(System.currentTimeMillis());
        return true;
    }

    /**
     * 后处理 ,视图返回之前
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("拦截器--拦截器执行完毕");
    }

    /**
     * 回调完毕 ,视图返回之后
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long start = threadLocal.get();
        logger.info("拦截器--执行时间为" + (System.currentTimeMillis() - start) + "ms");
    }

}
