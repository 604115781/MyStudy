package com.zjk.svc.websocket;

import java.io.IOException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoTest {

    @RequestMapping("/test/{str}")
    public String back(@PathVariable("str") String str) throws IOException {
        new WebSocket().onMessage(str,null);//str=测试1-1
        return null;
    }
}
