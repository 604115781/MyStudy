package com.zjk.order.Properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/3/10 10:42
 */
@Data
@Component
@ConfigurationProperties(prefix = "springcloud")
public class OrderProperties {

    private ItemProperties item = new ItemProperties();

}
