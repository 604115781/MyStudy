package com.zjk.item.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/3/9 13:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Item {

    private Long id;

    private String title;

    private String pic;

    private String desc;

    private Long price;

}
