package com.nginx.demo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/6/16 10:13
 */
@RestController
public class DemoController {

    @RequestMapping("/log")
    public String log(){
        System.out.println("this is 8088");
        return " this is 8088";
    }

}
