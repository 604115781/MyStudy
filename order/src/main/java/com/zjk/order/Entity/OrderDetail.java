package com.zjk.order.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/3/9 16:18
 */
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {

    private String orderId;

    private Item item = new Item();

}
