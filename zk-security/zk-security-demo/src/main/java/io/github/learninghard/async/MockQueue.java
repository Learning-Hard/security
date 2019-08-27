package io.github.learninghard.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning Hard
 * \* Date: 2019-08-26
 * \* Time: 1:07
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 模拟MQ收发消息
 * \
 */
@Component
public class MockQueue {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 待处理订单
     */
    private String placeOrder;

    /**
     * 完成订单
     */
    private String completeOrder;


    public String getPlaceOrder() {
        return placeOrder;
    }

    /**
     * 模拟接到下单请求
     * @param placeOrder
     */
    public void setPlaceOrder(String placeOrder) {
        new Thread(() -> {
            logger.info("接到下单请求," + placeOrder);
            try {
                /* 模拟订单的处理 */
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.info(e.getMessage(), e);
            }
            this.completeOrder = placeOrder;
            logger.info("下单请求处理完毕" + placeOrder);
        }).start();
        this.placeOrder = placeOrder;
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
