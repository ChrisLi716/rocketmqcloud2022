package com.chris.rocketmq.rocketmqcloud2022.controller;

import cn.hutool.core.collection.CollUtil;
import com.chris.rocketmq.rocketmqcloud2022.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Chris
 * @date 2022-03-25 11:30 PM
 */
@RestController
@RequestMapping("/producer")
@Slf4j
public class SendController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.consumer.topic}")
    private String topic;

    private final String str_tag = "str";
    private final String user_tag = "user";

    @PostMapping("/sendMessage")
    public String sendMsg(@RequestParam String input_message) {
        Message<String> message = MessageBuilder.withPayload(input_message).build();
        rocketMQTemplate.send(String.join(":", topic, str_tag), message);
        return "success";
    }

    @PostMapping("/sendUserMessage")
    public String sendMsg(@RequestBody @Validated User user) {
        rocketMQTemplate.convertAndSend(String.join(":", topic, user_tag), user);
        return "success";
    }

    /**
     * 发送同步消息
     */
    @PostMapping("/syncSendUserMessage")
    public SendResult syncSend(@RequestBody @Validated User user) {
        SendResult sendResult = rocketMQTemplate.syncSend(String.join(":", topic, user_tag), user, 10000);
        log.info("syncSend sendResult:{}", sendResult);
        return sendResult;
    }

    /**
     * 发送异步消息
     */
    @PostMapping("/asyncSendUserMessage")
    public void asyncSendUserMessage(@RequestBody @Validated User user) {
        rocketMQTemplate.asyncSend(String.join(":", topic, user_tag), user, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("async send result:{}", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("error happened when send asyn message", throwable);
            }
        });
    }

    /**
     * 发送批量消息
     */
    @PostMapping("/sendMessageInBatch")
    public SendResult sendMessageInBatch() {
        List<Message<String>> msgList = CollUtil.newArrayList();
        for (int i = 0; i < 10; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message<String> msg = MessageBuilder.withPayload("Hello Message in Batch " + i).build();
            msgList.add(msg);
        }
        SendResult sendResult = rocketMQTemplate.syncSend(String.join(":", topic, str_tag), msgList);
        log.info("syncSendMessages sendResult:{}", sendResult);
        return sendResult;
    }

    /**
     * 发送延时消息
     */
    @PostMapping("/sendDelayTimeMessage")
    public SendResult sendDelayTimeMessage() {
        Message<String> msg = MessageBuilder.withPayload("Hello Message sendDelayTimeMessage").build();
        SendResult sendResult = rocketMQTemplate.syncSend(String.join(":", topic, str_tag), msg, 10000, 3);
        log.info("sendDelayTimeMessage sendResult:{}", sendResult);
        return sendResult;
    }

    /**
     * 发单向时消息
     */
    @PostMapping("/sendOneWayMessage")
    public void sendOneWayMessage() {
        Message<String> msg = MessageBuilder.withPayload("Hello Message sendOneWayMessage").build();
        rocketMQTemplate.sendOneWay(String.join(":", topic, str_tag), msg);
    }

}
