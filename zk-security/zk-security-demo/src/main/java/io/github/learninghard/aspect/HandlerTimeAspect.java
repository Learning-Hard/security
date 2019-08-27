package io.github.learninghard.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning Hard
 * \* Date: 2019-08-23
 * \* Time: 14:40
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 处理时间切片,这是一个切片类
 * \
 */
//@Aspect
//@Component
public class HandlerTimeAspect {
    Logger logger = LoggerFactory.getLogger(HandlerMethod.class);

    /**
     * Aspect 把当前类标识为一个切面容器
     *
     * Before 标识一个前置增强方法
     * AfterReturning 后置增强
     * AfterThrowing 异常抛出增强
     * After final增强
     * Around 环绕增强
     * DeclareParents 引介增强
     */

    @Around("execution(* io.github.learninghard.controller.*.*(..))")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("Aspect--正在执行...");
        Long start = System.currentTimeMillis();

        Object[] args = pjp.getArgs();
        for (Object object : args) {
            logger.info("Aspect--执行参数为:" + object.toString());
        }
        /**
         * 执行方法
         */
        Object proceed = pjp.proceed();
        logger.info("Aspect--方法执行耗时:" + (System.currentTimeMillis() - start) + "ms");
        logger.info("Aspect--执行完毕...");
        return proceed;
    }
}
