package com.zjk.order.Fallback;

import com.zjk.order.Entity.Item;
import com.zjk.order.Feign.ItemFeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/3/11 15:24
 */
@Component
public class ItemServiceFallback implements ItemFeignClient {

    @Override
    public Item queryItemById(@PathVariable("id") Long id) {
        return new Item(null,"服务降级方法queryItemById",null,"服务降级方法queryItemById",null);
    }
}
