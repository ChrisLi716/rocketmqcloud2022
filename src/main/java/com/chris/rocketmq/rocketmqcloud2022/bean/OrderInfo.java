package com.chris.rocketmq.rocketmqcloud2022.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Chris
 * @date 2022-03-27 11:20 AM
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderInfo implements Serializable {

    private Long orderId;
    private String desc;
}
