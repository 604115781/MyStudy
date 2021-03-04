import Enties.OrderPaidEvent;
import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/3/31 10:36
 */
@Service
@RocketMQMessageListener(topic = "test-topic-2",consumerGroup = "my-consumer_test-topic-2")
public class OrderPaiEventConsumer implements RocketMQListener<OrderPaidEvent> {

    public void onMessage(OrderPaidEvent message){
        System.out.println("接收数据："+ JSON.toJSONString(message));
    }

}
