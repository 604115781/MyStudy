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
@RocketMQMessageListener(topic = "topic-1",consumerGroup = "consumer-topic-1")
public class MyConsumer1 implements RocketMQListener<MyEntity> {

    @Override
    public void onMessage(MyEntity myEntity) {
        log.info("thread name {},topic-1,接收数据：{}",Thread.currentThread().getName(),JSON.toJSONString(myEntity));
    }
}
