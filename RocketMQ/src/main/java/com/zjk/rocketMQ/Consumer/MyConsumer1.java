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
@RocketMQMessageListener(topic = "topic-1",consumerGroup = "consumer-topic-1")
public class MyConsumer1 implements RocketMQListener<MyEntity> {

    public void onMessage(MyEntity message){
        System.out.println("topic-1,接收数据："+ JSON.toJSONString(message));
    }

}
