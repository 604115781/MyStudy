package com.zjk.order.Feign;

import com.zjk.order.Entity.Item;
import com.zjk.order.Fallback.ItemServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author : 张江坤
 * @description:申明这是一个Feign客户端，并且指明服务id
 * 实际开发中ItemFeignClient一般直接继承（extend）服务提供方的接口以避免代码重复
 * @date :2020/3/10 15:46
 */
@FeignClient(value = "item",fallback = ItemServiceFallback.class)
@Service
public interface ItemFeignClient {

    @RequestMapping(value = "/item/{id}",method = RequestMethod.GET)
    Item queryItemById(@PathVariable("id") Long id);

}
