package com.zjk.rocketMQ.Producer;

import com.zjk.rocketMQ.Entity.MyEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/3/31 10:21
 */
@Component
@Slf4j
@RequestMapping("/myProducer")
public class MyProducer {
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @RequestMapping("/sendMsg")
    public void sendMsg() throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        DefaultMQProducer ss = rocketMQTemplate.getProducer();

        rocketMQTemplate.send("topic-1", MessageBuilder.withPayload(new MyEntity("李四",44)).build());
        SendResult result = rocketMQTemplate.syncSend("topic-1", MessageBuilder.withPayload(new MyEntity("李四",44)).build());
        rocketMQTemplate.asyncSend("topic-1", MessageBuilder.withPayload(new MyEntity("李四", 44)).build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
        rocketMQTemplate.asyncSendOrderly("topic-1", MessageBuilder.withPayload(new MyEntity("李四",44)).build(), "",new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable throwable) {

            }
        },System.currentTimeMillis());
        rocketMQTemplate.receive();
        rocketMQTemplate.receive(Map.class);
        //rocketMQTemplate.convertAndSend("topic-2", "你好啊 张三");
    }
}
