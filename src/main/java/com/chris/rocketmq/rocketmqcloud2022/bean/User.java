package com.chris.rocketmq.rocketmqcloud2022.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Chris
 * @date 2022-03-25 11:24 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @NotNull(message = "name can't be null")
    private String name;
    @Range(max = 100, min = 1, message = "age between 1 and 100")
    private Integer age;
}
