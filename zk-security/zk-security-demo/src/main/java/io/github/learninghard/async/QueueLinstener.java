package io.github.learninghard.async;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning Hard
 * \* Date: 2019-08-26
 * \* Time: 9:44
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 模拟mq监听器
 * \
 */
public class QueueLinstener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    DeferredResultHolder deferredResultHolder;

    /**
     * 监听spring容器加载完成后执行的操作
     *
     * @param contextRefreshedEvent
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        new Thread(() -> {
            while (true) {
                if (StringUtils.isNotBlank(mockQueue.getCompleteOrder())) {
                    String completeOrder = mockQueue.getCompleteOrder();
                    logger.info("返回订单处理结果:" + completeOrder);
                    deferredResultHolder.getMap().get(completeOrder).setResult("place order success");
                    mockQueue.setCompleteOrder(null);
                }else{
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        logger.info(e.getMessage(),e);
                    }
                }
            }
        });

    }
}
