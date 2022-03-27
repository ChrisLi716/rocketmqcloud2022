package com.chris.rocketmq.rocketmqcloud2022.service;

import cn.hutool.json.JSONUtil;
import com.chris.rocketmq.rocketmqcloud2022.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author Chris
 * @date 2022-03-26 2:50 PM
 */
/*
@Service
@Slf4j
@RocketMQMessageListener(topic = "${rocketmq.consumer.topic}", selectorType = SelectorType.TAG, selectorExpression =
        "str", consumerGroup = "${rocketmq.consumer.group}")
public class MsgConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("consumer message:{}", message);

    }
}
*/
