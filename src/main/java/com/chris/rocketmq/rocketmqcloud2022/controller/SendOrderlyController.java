package com.chris.rocketmq.rocketmqcloud2022.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.db.sql.Order;
import com.chris.rocketmq.rocketmqcloud2022.bean.OrderInfo;
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
@RequestMapping("/order")
@Slf4j
public class SendOrderlyController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private final String topic = "cloud006";
    private final String order_tag = "order";

    /**
     * 发送批量消息
     */
    @PostMapping("/sendOrderMessage")
    public SendResult sendOrderMessage() {
        List<OrderInfo> orderInfos = getOrderInfos();

        List<Message<OrderInfo>> messageList = CollUtil.newArrayList();
        for (OrderInfo orderInfo : orderInfos) {
            Message<OrderInfo> message = MessageBuilder.withPayload(orderInfo).build();
            messageList.add(message);
        }

        SendResult sendResult = rocketMQTemplate.syncSend(String.join(":", topic, order_tag), messageList, 10000);
        log.info("sendOrderMessage sendResult:{}", sendResult);
        return sendResult;
    }

    @PostMapping("/sendOrderMessageOrderly")
    public String sendOrderMessageOrderly() {
        List<OrderInfo> orderInfos = getOrderInfos();
        for (OrderInfo orderInfo : orderInfos) {
            Message<OrderInfo> message = MessageBuilder.withPayload(orderInfo).build();
            SendResult sendResult = rocketMQTemplate.syncSendOrderly(String.join(":", topic, order_tag), message,
                    String.valueOf(orderInfo.getOrderId()));
            log.info("send orderly result:{}", sendResult);
        }
        return "success";
    }

    private List<OrderInfo> getOrderInfos() {
        List<OrderInfo> orderInfos = CollUtil.newArrayList();
        orderInfos.add(new OrderInfo(1L, "create"));
        orderInfos.add(new OrderInfo(2L, "create"));
        orderInfos.add(new OrderInfo(1L, "send"));
        orderInfos.add(new OrderInfo(3L, "create"));
        orderInfos.add(new OrderInfo(2L, "send"));
        orderInfos.add(new OrderInfo(4L, "create"));
        orderInfos.add(new OrderInfo(1L, "pay"));
        orderInfos.add(new OrderInfo(1L, "finish"));
        orderInfos.add(new OrderInfo(3L, "send"));
        orderInfos.add(new OrderInfo(2L, "pay"));
        orderInfos.add(new OrderInfo(4L, "send"));
        orderInfos.add(new OrderInfo(3L, "pay"));
        orderInfos.add(new OrderInfo(2L, "finish"));
        orderInfos.add(new OrderInfo(4L, "pay"));
        orderInfos.add(new OrderInfo(3L, "finish"));
        orderInfos.add(new OrderInfo(4L, "finish"));
        return orderInfos;
    }

}
