package com.zjk.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/3/11 11:27
 */
@SpringBootTest(classes = ItemServiceTest.class)
@Import(OrderApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ItemServiceTest {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Test
    public void test(){
        String serviceId = "item";
        for(int i= 0;i<100;i++){
            ServiceInstance serviceInstance = this.loadBalancerClient.choose(serviceId);
            System.out.println("第"+(i+1)+"次："+serviceInstance.getHost()+":"+serviceInstance.getPort());
        }
    }


}

