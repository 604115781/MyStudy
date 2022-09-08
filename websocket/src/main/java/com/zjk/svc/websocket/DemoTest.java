package com.zjk.svc.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoTest {

    int i=0;
    String test ;
    public DemoTest(String a){
        this.test = a;
    }

    @RequestMapping("/test/{str}")
    public synchronized void back(@PathVariable("str") String str) throws IOException {
        //new WebSocket().onMessage(str,null);//str=测试1-1
        System.out.println(Thread.currentThread().getName()+"||"+str);
    }
    private ReentrantLock lock1 = new ReentrantLock(true);
    @RequestMapping("/lock/{lock}")
    public void lockTest(@PathVariable("lock") String lock) throws InterruptedException {
        ConcurrentHashMap<String,String> currentStr = new ConcurrentHashMap<>();

        ThreadPoolExecutor ss = new ThreadPoolExecutor(1,1,1L,TimeUnit.SECONDS,new ArrayBlockingQueue<>(20));
        ss.execute(new Thread(()->{}));
        Future futuer = ss.submit(new Thread(()->{}));
        try {
            List aa = (List)futuer.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        StringBuilder aa = new StringBuilder();
        aa.append("");
        StringBuffer bb = new StringBuffer();
        bb.append("");
    }
    public void testConstruct(){
        System.out.println("ss");
    }
}

