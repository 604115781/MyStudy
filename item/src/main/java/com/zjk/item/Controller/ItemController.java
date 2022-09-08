package com.zjk.item.Controller;


import com.zjk.item.Entity.Item;
import com.zjk.item.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 张江坤
 * @description：商品Controller
 * @date :2020/3/9 13:54
 */
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     *@Description:
     *@Param: [id]
     *@return:Entity.Item
     *@Author: 张江坤
     *@date: 2020/3/9 14:26
     */
    @RequestMapping("item/{id}")
    public Item queryItemById(@PathVariable("id") Long id){
        return itemService.queryItemById(id);
    }
    @RequestMapping("/upstream")
    public void log(@PathVariable("id") Long id){
        System.out.println("this is 8088");
    }

    @RequestMapping("/qstb/saveApasInfo")
    public void log(@RequestParam String xmlStr){
        System.out.println(xmlStr);
    }

}
