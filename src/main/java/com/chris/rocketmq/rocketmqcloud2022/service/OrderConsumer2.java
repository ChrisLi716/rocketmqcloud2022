package com.chris.rocketmq.rocketmqcloud2022.service;

import cn.hutool.json.JSONUtil;
import com.chris.rocketmq.rocketmqcloud2022.bean.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author Chris
 * @date 2022-03-26 2:50 PM
 */

@Service
@Slf4j
@RocketMQMessageListener(topic = "cloud006", selectorType = SelectorType.TAG, selectorExpression = "order",
        consumerGroup = "cloud-consume-group-006", consumeMode = ConsumeMode.ORDERLY)
public class OrderConsumer2 implements RocketMQListener<OrderInfo> {
    @Override
    public void onMessage(OrderInfo orderInfo) {
        log.info("consume orderInfo msg:{}", JSONUtil.toJsonStr(orderInfo));
    }
}

