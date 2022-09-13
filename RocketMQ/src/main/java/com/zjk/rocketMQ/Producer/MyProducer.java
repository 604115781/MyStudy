package com.zjk.rocketMQ.Producer;

import com.zjk.rocketMQ.Entity.MyEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

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
    private static int i=0;
    @RequestMapping("/sendMsg")
    public void sendMsg(){
        rocketMQTemplate.send("topic-1", MessageBuilder.withPayload(new MyEntity("李四",44)).build());
        rocketMQTemplate.convertAndSend("topic-2", "你好啊 张三");
        log.info("第{}条消息发送成功！！",++i);
    }

}
