package com.zjk.order.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zjk.order.Entity.Item;
import com.zjk.order.Feign.ItemFeignClient;
import com.zjk.order.Properties.OrderProperties;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/3/9 16:32
 */
@Service
public class ItemService {

    @Autowired
    private RestTemplate restTemplate;
    //@Value("${springcloud.item.url}")
    //private String itemUrl;

    @Autowired
    OrderProperties orderProperties;

    //@Resource(name="ItemServiceFallback")
    //@Qualifier("ItemFeignClient")
    //@Qualifier("")
    @Autowired
    private ItemFeignClient itemFeignClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    public Item queryItemById(Long id) {
        String itemUrl = "http://item/item/{id}";
        Item result =  this.restTemplate.getForObject(itemUrl, Item.class,id);
        //Item result = itemFeignClient.queryItemById(id);
        System.out.println("订单系统调用商品服务，result:"+result);
        return result;
    }

    //@HystrixCommand(fallbackMethod = "queryItemByIdFallbackMethod")
    public Item queryItemById3(Long id) {
        String itemUrl = "http://item/item/{id}";
        //Item result =  this.restTemplate.getForObject(itemUrl, Item.class,id);
        Item result = itemFeignClient.queryItemById(id);
        System.out.println("**********HystrixCommand queryItemById-线程池名称:"+Thread.currentThread().getName()+"订单系统调用商品服务，result:"+result);
        return result;
    }
    public Item queryItemByIdFallbackMethod(Long id){
        return new Item(id,"查询商品出错！",null,null,null);
    }
}
