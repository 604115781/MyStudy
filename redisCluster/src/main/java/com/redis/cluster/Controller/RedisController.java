package com.redis.cluster.Controller;

import com.redis.cluster.Entity.MyEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
public class RedisController {

    @Autowired
    private ValueOperations<String, String> valueOperations;
    @Autowired
    private HashOperations<String, String, Object> hashOperations;
    @Autowired
    private SetOperations<String,Object> setOperations;
    @Autowired
    private ZSetOperations<String,Object> zSetOperations;

    @RequestMapping("/hash")
    public void redisHash() {
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

    @RequestMapping("/set")
    public void redisSet() {
        setOperations.add("db","mysql","oracle","mysql","PostgreSQL","redis");
        setOperations.add("db2","mysql","oracle","alibaba","nodb","redis");
        //
        Set<Object> set = setOperations.members("db");
        log.info(set.toString());//[oracle, mysql, PostgreSQL, redis]
        setOperations.remove("db","oracle","mysql");
        Set<Object> set1 = setOperations.members("db");
        log.info("db---{}",set1.toString());//db---[PostgreSQL, redis]
        Set<Object> set2 = setOperations.members("db2");
        log.info("db2---{}",set2.toString());//db2---[oracle, nodb, mysql, redis, alibaba]
        List<String> list = new ArrayList<>();
        list.add("db");
        list.add("db2");
        //第一个集合有其他集合没有的元素，注意，不是所有集合的差集！！！
        Set<Object> diffSet = setOperations.difference(list);
        log.info("diffSet--{}",diffSet.toString());//diffSet--[PostgreSQL]

        Set<Object> intersect = setOperations.intersect(list);
        log.info("intersect--{}",intersect.toString());//第一个集合中在其他集合中的交集 intersect--[redis] 共同好友

        Set<Object> set3 = setOperations.members("db3");
        log.info("db3--{}",set3.toString());//diffSet--[PostgreSQL]
        setOperations.differenceAndStore(list,"db3");//将第一个集合独有的元素存入db3
        set3 = setOperations.members("db3");
        log.info("db3--{}",set3.toString());//diffSet--[PostgreSQL]
        log.info("isMember--{}",setOperations.isMember("db","redis"));//判断集合是否包含value
    }

    @RequestMapping("/zSet")
    public void redisZSet() {
        Set<ZSetOperations.TypedTuple<Object>> set = new HashSet<>();
        set.add(new DefaultTypedTuple("score1",100.0));//两种赋值方式
        set.add(new DefaultTypedTuple("score2",200.0));
        set.add(new DefaultTypedTuple("score4",400.0));
        zSetOperations.add("zset:db1",set);
        zSetOperations.add("zset:db1","score6",600);//两种赋值方式
        zSetOperations.add("zset:db1","score5",500);
        zSetOperations.add("zset:db1","score3",300);
        Set<Object> zset1 = zSetOperations.rangeByScore("zset:db1",200,500);//以分数排序获取分数在20-100的元素集合
        log.info("zset1--{}",zset1.toString());//zset1--[score2, score3, score4, score5]
        Set<Object> zset2 = zSetOperations.range("zset:db1",2,3);//获取从位置2到位置3的元素集合（0开始）
        log.info("zset2--{}",zset2.toString());//zset2--[score3, score4]
        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
        range.gte("score1");
        range.lte("score5");
        Set<Object> zset3 = zSetOperations.rangeByLex("zset:db1",range);//限定value范围，此处注意，不是score范围
        log.info("zset3--{}",zset3.toString());//zset3--[score1, score2, score3, score4, score5]
        RedisZSetCommands.Limit limit = new RedisZSetCommands.Limit();
        limit.offset(2);//从range后的集合坐标2开始（从0开始）
        limit.count(3);//取三个
        Set<Object> zset4 = zSetOperations.rangeByLex("zset:db1",range,limit);
        log.info("zset4--{}",zset4.toString());//zset4--[score3, score4, score5]

        ScanOptions scanOptions = ScanOptions.NONE;
        Cursor<ZSetOperations.TypedTuple<Object>> cursor = zSetOperations.scan("zset:db1",scanOptions);
        cursor.forEachRemaining(item ->{//遍历zSet
            log.info(item.getValue().toString());
            log.info(item.getScore().toString());
        });
    }
}
