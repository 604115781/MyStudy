package com.zjk.rocketMQ.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/3/31 10:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private int age;
}
