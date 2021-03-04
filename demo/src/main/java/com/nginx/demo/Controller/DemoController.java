package com.nginx.demo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/6/15 17:22
 */
@RestController
public class DemoController {

    @RequestMapping("/log")
    public String log() {
        return "this is 8088";
    }

}
