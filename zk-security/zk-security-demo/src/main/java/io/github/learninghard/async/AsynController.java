package io.github.learninghard.async;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning Hard
 * \* Date: 2019-08-26
 * \* Time: 0:46
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 测试异步请求
 * \
 */
@RestController
public class AsynController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    /**
     * 同步处理请求
     * @throws Exception
     */
    @RequestMapping("/syncorder")
    public String Order() throws Exception {
        logger.info("主线程开始");
        Thread.sleep(1000);
        logger.info("主线程返回");
        return "SUCCESS";
    }


    /**
     * 异步处理请求
     * @throws Exception
     */
    @RequestMapping("/asyncorder")
    public Callable<String> AsyncOrder() throws Exception {
        logger.info("主线程开始");
        Callable<String> callable = new Callable() {
            @Override
            public Object call() throws Exception {
                logger.info("副线程开始");
                Thread.sleep(1000);
                logger.info("副线程返回");
                return "SUCCESS";
            }
        };
        logger.info("主线程返回");
        return callable;
    }

    /**
     * 异步处理请求
     * @throws Exception
     */
    @RequestMapping("/asyncorder1")
    public void AsyncOrder1() {
        logger.info("主线程开始");
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("副线程开始");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("副线程返回");
            }
        }).start();
        logger.info("主线程返回");
    }


    @RequestMapping("/order")
    public DeferredResult<String> order(){
        logger.info("主线程开始");

        String orderNumber = RandomStringUtils.randomNumeric(8);
        mockQueue.setPlaceOrder(orderNumber);

        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber, result);

        logger.info("主线程完毕");

        return result;
    }


}
