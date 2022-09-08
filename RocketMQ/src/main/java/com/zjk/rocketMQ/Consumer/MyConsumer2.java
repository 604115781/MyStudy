package com.zjk.rocketMQ.Consumer;

import com.alibaba.fastjson.JSON;
import com.zjk.rocketMQ.Entity.MyEntity;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/3/31 10:36
 */
@Component
@RocketMQMessageListener(topic = "topic-2",consumerGroup = "consumer-topic-1")
public class MyConsumer2 implements RocketMQListener<String> {

    public void onMessage(String message){
        System.out.println("topic-2,接收数据："+ message);
    }

}
