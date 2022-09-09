package com.zjk.rocketMQ.Consumer;

import com.alibaba.fastjson.JSON;
import com.zjk.rocketMQ.Entity.MyEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/3/31 10:36
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = "topic-2",consumerGroup = "consumer-topic-2")
public class MyConsumer2 implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("topic-2,接收数据：{}",message);
    }
}
