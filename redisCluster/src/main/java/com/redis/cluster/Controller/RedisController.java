package com.redis.cluster.Controller;

import com.redis.cluster.Entity.MyEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class RedisController {

    @Autowired
    private ValueOperations<String, String> valueOperations;
    @Autowired
    private HashOperations<String, String, Object> hashOperations;

    @RequestMapping("/hash")
    public void  redisHash() {
        MyEntity me = new MyEntity();
        me.setName("张三");
        me.setAge(1);
        me.setGender("男");
        hashOperations.put(me.getName(),"age","1");
        hashOperations.put(me.getName(),"gender","男");
        me = new MyEntity();
        me.setName("李四");
        me.setAge(2);
        me.setGender("女");
        hashOperations.put(me.getName(),"age","2");
        hashOperations.put(me.getName(),"gender","女");

        log.info(hashOperations.get("张三","age").toString());
        log.info(hashOperations.get("张三","gender").toString());
        List<String> list = new ArrayList<>();
        list.add("age");
        list.add("gender");
        log.info(hashOperations.multiGet("张三",list).toString());

        Map<String,Object> map= hashOperations.entries("李四");
        log.info(map.toString());
    }
}
