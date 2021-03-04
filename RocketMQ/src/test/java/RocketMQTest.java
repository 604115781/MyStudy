import Enties.OrderPaidEvent;
import java.math.BigDecimal;
import javax.annotation.Resource;
import javax.swing.Spring;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/3/31 10:21
 */
@SpringBootTest
public class RocketMQTest {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Test
   public void testRocketMQ(){
       String name = "aaa";
       rocketMQTemplate.convertAndSend("test-topic-1",name);
       rocketMQTemplate.send("test-topic-2", MessageBuilder.withPayload(new OrderPaidEvent("aa,22",new BigDecimal("22"))).build());
       System.out.println("发送成功...");
   }

}
