package com.zjk.rocketMQ.Consumer;

import com.zjk.rocketMQ.Entity.MyEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/3/31 10:36
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = "topic-1",consumerGroup = "consumer-topic-1",messageModel = MessageModel.BROADCASTING)
public class MyConsumer1 implements RocketMQListener<MyEntity> {

    private static int i = 0;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @SneakyThrows
    @Override
    public void onMessage(MyEntity myEntity) {
        //log.info("thread name {},topic-1,接收数据：{}",Thread.currentThread().getName(),JSON.toJSONString(myEntity));
        Thread.sleep(3000);
        log.info("next one  {}!",++i);
    }
}
